package clueGame;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private Set<BoardCell> doors;
	private ArrayList<Player> players; 
	private ArrayList<String> weapons; 
	private ArrayList<Card> deck;
	private ArrayList<Card> fullDeck; 
	//private ArrayList<Card> solutionDeck; 
	private char label;
	private Room room;
	private String playerName; 
	private String playerColor; 
	private int playerStartRow; 
	private int playerStartCol; 
	private int numHumanPlayers;
	private int numComputerPlayers; 
	private Player humanPlayer; 
	private int numWeapons; 
	private CardType cardType; 
	private Card card; 
	private static Board theInstance = new Board();
	private Solution theAnswer;
	private boolean humanPlayerFinishedTurn = true;
	private int currentPlayerIdx;
	private Player currentPlayer;
	private int roll;
	private int currentPlayerRow;
	private int currentPlayerCol;
	private BoardCell currentPlayerCell;
	private int clickedRow;
	private int clickedCol;
	private boolean clickedOnTarget;
	private Room clickedRoom;
	private BoardCell topLeftRoomCell;
	private BoardCell bottomRightRoomCell;
	private boolean clickedOnRoom;
	private ArrayList<Card> allCards;
	//instance variables that will determine the size of each board cell
	int cellWidth = 0; 
	int cellHeight = 0;
	int xCoord = 0;
	int yCoord = 0; 
	private boolean isHumanPlayer = false; 

	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	//initializes data structures, and reads in board data files 
	public void initialize(){
		try {
			numRows = 0; 
			numColumns = 0; 
			numHumanPlayers = 0; 
			numComputerPlayers = 0; 
			numWeapons = 0; 
			currentPlayerIdx = 0;
			visited = new HashSet<BoardCell>();
			targets = new HashSet<BoardCell>(); 
			roomMap = new HashMap<Character, Room>();
			doors = new HashSet<BoardCell>();
			players = new ArrayList<Player>(); 
			weapons = new ArrayList<String>(); 
			deck = new ArrayList<Card>(); 
			fullDeck = new ArrayList<Card>(); 
			//solution = new ArrayList<Card>(); 
			loadSetupConfig(); 
			loadLayoutConfig(); 
			//loop through grid and run calcAdjacencies for each cell
			for(int row = 0; row < numRows; row++) {
				for(int col = 0; col < numColumns; col++) {
					BoardCell cell = grid[row][col]; 
					calcAdjacencies(cell); 
				}
			}
		}catch(FileNotFoundException | BadConfigFormatException e) {
			System.out.println(e);
		}
	}


	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException{
		//load in ClueSetup.txt and then add the room character and room name to the map 
		FileReader setupReader = new FileReader(setupConfigFile);  
		Scanner setupScanner = new Scanner(setupReader); 
		//loop to read in file data 

		while(setupScanner.hasNextLine()) {
			//reads in line of file  
			String currentLine = setupScanner.nextLine(); 
			//if file has a comment, ignores it and continues to next line 
			if(currentLine.contains("//")) {
				continue;
			}
			//takes each line and splits up line by comma then space
			String[] lineInfo = currentLine.split(", "); 
			//if the first word read in is not Room, Space, Human, or Computer, throw a BadConfigFormatException
			if(lineInfo[0].equals("Room") || lineInfo[0].equals("Space")) {
				//converts the string containing the label to a char 
				label = lineInfo[2].charAt(0); 
				//creates a room out of the room name string contained in lineInfo 
				room = new Room(lineInfo[1], label); 
				//only want to make a card if it is an actual room, not a walkway or unused space
				if(lineInfo[0].equals("Room")) {
					//tell the room what type of card it is
					room.setCardType(CardType.ROOM); 
					//create a card that corresponds to the room and add it to the deck 
					card = new Card(lineInfo[1], room.getCardType());
					//add card to arraylist 
					deck.add(card);
					fullDeck.add(card); 
				}
				//adds label and room to the map 
				roomMap.put(label, room);
			}
			else if(lineInfo[0].equals("Human") || lineInfo[0].equals("Computer")) {
				Player player; 
				playerName = lineInfo[1]; 
				playerColor = lineInfo[2]; 
				playerStartRow = Integer.parseInt(lineInfo[3]);
				playerStartCol = Integer.parseInt(lineInfo[4]); 
				if(lineInfo[0].equals("Human")) {
					player = new HumanPlayer(playerName, playerColor, playerStartRow, playerStartCol); 
					humanPlayer = player; 
					//increment human players counter
					numHumanPlayers++;  
				}
				else {
					player = new ComputerPlayer(playerName, playerColor, playerStartRow, playerStartCol); 
					//increment computer players counter 
					numComputerPlayers++; 
				}
				//tell the player what type of card it is
				player.setCardType(CardType.PERSON); 
				//once human player is created, add it to set 
				players.add(player);
				//create a card that corresponds to the player 
				card = new Card(lineInfo[1], player.getCardType()); 
				//add card to deck 
				deck.add(card); 
				fullDeck.add(card); 
			}
			else if(lineInfo[0].equals("Weapon")) {
				//add weapon to arraylist of weapons
				weapons.add(lineInfo[1]); 
				//tell the board what type of card it is 
				cardType = CardType.WEAPON;  
				//increment numWeapons
				numWeapons++; 
				//create a card that corresponds to the current weapon
				card = new Card(lineInfo[1], cardType); 
				//add card to deck
				deck.add(card); 
				fullDeck.add(card); 
			}
			else {
				throw new BadConfigFormatException("Setup text file not written properly, check spelling and spaces"); 
			}
		} 
		//creating an arraylist of all of the cards, used later
		
		allCards = (ArrayList<Card>) deck.clone();
		currentPlayer = players.get(0);
		//after the deck has been fully loaded in, we want to randomly pick 1 room, 1 weapon, and 1 person that is the solution
		Solution solution = new Solution(); 
		solution.createSolution(deck);
	}

	public ArrayList<Card> getFullDeck() {
		return fullDeck;
	}
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		//load in ClueLayout.txt and then add the room character and room name to the map 
		FileReader layoutReader = new FileReader(layoutConfigFile);  
		Scanner layoutScanner = new Scanner(layoutReader); 
		//calculates rows and cols 
		calcRowCol(); 
		//initializes grid 
		this.grid = new BoardCell[numRows][numColumns]; 
		int row = 0;
		while(layoutScanner.hasNextLine()) {
			String currentLine = layoutScanner.nextLine(); 
			String[] splitLine = currentLine.split(",");
			int column = 0;
			char initial; 
			BoardCell currentCell; 
			for(String cell : splitLine) {
				//create a new board cell
				initial = cell.charAt(0);
				currentCell = new BoardCell(row, column, initial);
				//add cell to set of cells for the room it is in 
				roomMap.get(initial).addRoomCell(currentCell);

				//checks cases for special cells; centers, labels, doors, and secret passages
				if(cell.length() ==  2) {
					switch(cell.charAt(1)) {
					case '#':
						roomMap.get(initial).setLabelCell(currentCell);
						currentCell.setRoomLabel(true);
						break;
					case '*':
						//if the first char in the string is not W or S then tell the cell it is a room
						currentCell.setIsRoom(true);
						roomMap.get(initial).setCenterCell(currentCell);
						currentCell.setRoomCenter(true);
						if(roomMap.get(initial).hasSecretPassage()) {
							//gets the center cell of the room that the secret passage connects
							BoardCell connectingCenter = roomMap.get(initial).getCenterOfConnectingRoom(); 
							//tell the center cell of the room we are in about the secret passage
							currentCell.setSecretPassage(connectingCenter.getCharacter());
						}
						break;
					case '^':
						currentCell.setDoorDirection(DoorDirection.UP);
						currentCell.setDoorway(true);
						doors.add(currentCell);
						break;
					case 'v':
						currentCell.setDoorDirection(DoorDirection.DOWN);
						currentCell.setDoorway(true);
						doors.add(currentCell);
						break;
					case '>':
						currentCell.setDoorDirection(DoorDirection.RIGHT);
						currentCell.setDoorway(true);
						doors.add(currentCell);
						break;
					case '<':
						currentCell.setDoorDirection(DoorDirection.LEFT);
						currentCell.setDoorway(true);
						doors.add(currentCell);
						break;
					default:
						Room currentRoom = roomMap.get(cell.charAt(0)); 
						BoardCell currentCenter = currentRoom.getCenterCell(); 
						//if that center cell has been found then just tell that center cell it has a secret passage
						if(currentCenter !=  null) {
							currentRoom.setSecretPassage(roomMap.get(cell.charAt(1)).getCenterCell(), cell.charAt(1));
							currentCenter.setSecretPassage(cell.charAt(1));
						}
						//if center cell of currentRoom has not been found, then tell the room as a whole it has a secret passage
						else {
							currentRoom.setSecretPassage(roomMap.get(cell.charAt(1)).getCenterCell(), cell.charAt(1));
						}
					}
				}

				//if the map of rooms does not contain the label we are looking at, throw and exception
				if(!roomMap.containsKey(initial)) {
					throw new BadConfigFormatException(); 
				}
				grid[row][column] = currentCell;
				column++;
			}
			row++;
		}
		tellRoomCellsSecretPassageAndRoom(); 
		//calculates adjacencies for all doors, since was not possible until all cells are loaded
		calcDoorAdjacencies(doors);	
	}

	//method to initialize rows and columns 
	public void calcRowCol() throws BadConfigFormatException, FileNotFoundException {
		//loops over file to determine number of rows and cols in board 
		FileReader numsReader = new FileReader(layoutConfigFile); 
		Scanner numsScanner = new Scanner(numsReader); 
		while(numsScanner.hasNextLine()) {
			String currentLine = numsScanner.nextLine(); 
			numRows++;
			//determines the number of columns by seeing how many labels are on each line 
			String[] splitLine = currentLine.split(","); 
			if(numColumns == 0) {
				//since a proper board will have the same number of labels on each row, we only 
				//need to set numColumns once 
				numColumns = splitLine.length; 
			}
			//if there is not the same number of columns in each row, throw an exception
			if(splitLine.length != numColumns) {
				throw new BadConfigFormatException(); 
			}
		}
	}

	public void tellRoomCellsSecretPassageAndRoom() {
		//if a room has a secret passage, loop through every cell in that room and tell it, it has a secret passage 
		for(Room room : roomMap.values()) {
			if(room.getName().equals("Walkway") || room.getName().equals("Unused")) {
				continue;
			}
			//if the center cell is a secret passage, tell every cell in the room it has a secret passage 
			if(room.getCenterCell().hasSecretPassage()) {
				for(BoardCell roomCell : room.getRoomCells()) {
					roomCell.setSecretPassage(room.getSecretPassage()); 
				}
			}
			//if the center cell is a room, tell every cell in that room what room it is 
			if(room.getCenterCell().getIsRoom() == true) {
				for(BoardCell roomCell : room.getRoomCells()) {
					roomCell.setIsRoom(true);
				}
			}
		}
	}

	public void calcDoorAdjacencies(Set<BoardCell> doors) {
		for(BoardCell door : doors) {
			Room adjRoom;
			char adjChar;
			switch(door.getDoorDirection()) { //checks door direction
			case UP:
				//gets character of the cell the door is pointing too
				adjChar = grid[door.getRow()-1][door.getCol()].getCharacter();
				//gets the room of the adjChar
				adjRoom = roomMap.get(adjChar);
				//adds the door to the door set of the room
				adjRoom.addDoor(grid[door.getRow()][door.getCol()]);
				break;
			case DOWN:
				adjChar = grid[door.getRow()+1][door.getCol()].getCharacter();
				adjRoom = roomMap.get(adjChar);
				adjRoom.addDoor(grid[door.getRow()][door.getCol()]);
				break;
			case LEFT:
				adjChar = grid[door.getRow()][door.getCol()-1].getCharacter();
				adjRoom = roomMap.get(adjChar);
				adjRoom.addDoor(grid[door.getRow()][door.getCol()]);
				break;
			case RIGHT:
				adjChar = grid[door.getRow()][door.getCol()+1].getCharacter();
				adjRoom = roomMap.get(adjChar);
				adjRoom.addDoor(grid[door.getRow()][door.getCol()]);
				break;
			}
		}
	}

	public void setConfigFiles(String csvFile, String txtFile) {
		this.layoutConfigFile = csvFile; 
		this.setupConfigFile = txtFile; 
	}

	public Room getRoom(char Room) {
		return roomMap.get(Room); 
	}

	public Room getRoom(BoardCell cell) {
		char roomLabel = cell.getCharacter(); 
		return roomMap.get(roomLabel); 
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCell(int row, int col) {
		return grid[row][col]; 
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return grid[i][j].getAdjList();
	}
	
	public ArrayList<Card> getAllCards(){
		return this.allCards;
	}

	public void calcTargets(BoardCell startCell, int pathLength) {
		//clears both visited and targets list to make sure both are empty before calculating new targets
		visited.clear();
		targets.clear();
		visited.add(startCell); 
		recursiveCalcTargets(startCell, pathLength);
	}

	public void recursiveCalcTargets(BoardCell startCell, int pathLength) {
		for( BoardCell adjCell : startCell.getAdjList()) {
			if(visited.contains(adjCell)) {
				continue;
			}else {
				//if the adjCell is a room, then add the center cell of that room to targets 
				if(adjCell.getIsRoom()) {
					targets.add(adjCell);
				}
				if(!adjCell.getOccupied() && adjCell.getCharacter() != 'X') {
					visited.add(adjCell);
					if(pathLength == 1) {
						targets.add(adjCell);
					}else if(adjCell.isRoomCenter()) {
						targets.add(adjCell);	
					}else {
						recursiveCalcTargets(adjCell, pathLength - 1);
					}
					visited.remove(adjCell);
				}else if(adjCell.getOccupied() && adjCell.isRoomCenter()) { //adds room center to targets, even if occupied
					targets.add(adjCell);
				}
			}
		}
	}

	public void calcAdjacencies(BoardCell cell) {
		if(cell.isRoomCenter()) {
			if(cell.hasSecretPassage()) {
				//gets the other room that the secret passage connects 
				Room otherSecretPassageRoom = roomMap.get(cell.getSecretPassage()); 
				//gets center cell of that other room 
				BoardCell otherCenterCell = otherSecretPassageRoom.getCenterCell(); 
				//adds that cell to the adjList
				cell.addAdj(otherCenterCell);
			}
			//gets the room that we are in 
			Room room  = roomMap.get(cell.getCharacter());
			//gets all the doors that are in that room and adds them to the adjList 
			Set<BoardCell> roomDoors = room.getDoors();
			for( BoardCell door : roomDoors) {
				cell.addAdj(door);
			}
		}
		// testing left edge
		if (cell.getCol() != 0) {
			BoardCell leftCell = grid[cell.getRow()][cell.getCol() - 1];
			//if the cell to the left is just a walkway or an unused space, then just add to adjList
			if(leftCell.getCharacter() == 'W') {
				cell.addAdj(leftCell);
			}
			//if the cell is not a walkway or unused space and if that cell is 
			//a doorway, then we get the center cell of that room and add it to adjList
			else if(cell.isDoorway() == true && cell.getDoorDirection() == DoorDirection.LEFT) {
				Room adjRoom = roomMap.get(leftCell.getCharacter()); 
				BoardCell roomCenter = adjRoom.getCenterCell(); 
				cell.addAdj(roomCenter);
			} 
		}
		// testing right edge
		if (cell.getCol() != numColumns - 1) {
			BoardCell rightCell = grid[cell.getRow()][cell.getCol() + 1];
			//if the cell to the right is just a walkway or an unused space, then just add to adjList
			if(rightCell.getCharacter() == 'W') {
				cell.addAdj(rightCell);
			}
			//if the cell is not a walkway or unused space and if that cell is 
			//a doorway, then we get the center cell of that room and add it to adjList
			else if(cell.isDoorway() == true && cell.getDoorDirection() == DoorDirection.RIGHT) {
				Room adjRoom = roomMap.get(rightCell.getCharacter()); 
				BoardCell roomCenter = adjRoom.getCenterCell(); 
				cell.addAdj(roomCenter);
			}
		}
		// testing top edge
		if (cell.getRow() != 0) {
			BoardCell upperCell = grid[cell.getRow() - 1][cell.getCol()];
			//if the cell above is just a walkway or an unused space, then just add to adjList
			if(upperCell.getCharacter() == 'W') {
				cell.addAdj(upperCell);
			}
			//if the cell is not a walkway or unused space and if that cell is 
			//a doorway, then we get the center cell of that room and add it to adjList
			else if(cell.isDoorway() == true && cell.getDoorDirection() == DoorDirection.UP) {
				Room adjRoom = roomMap.get(upperCell.getCharacter()); 
				BoardCell roomCenter = adjRoom.getCenterCell(); 
				cell.addAdj(roomCenter);
			}
			//if the above cell is a room, but not a doorway, then it does not go in adjList 
		}
		// testing bottom edge
		if (cell.getRow() != numRows - 1) {
			BoardCell lowerCell = grid[cell.getRow() + 1][cell.getCol()];
			//if the cell below is just a walkway or an unused space, then just add to adjList
			if(lowerCell.getCharacter() == 'W') {
				cell.addAdj(lowerCell);
			}
			//if the cell is not a walkway or unused space and if that cell is 
			//a doorway, then we get the center cell of that room and add it to adjList
			else if(cell.isDoorway() == true && cell.getDoorDirection() == DoorDirection.DOWN) {
				Room adjRoom = roomMap.get(lowerCell.getCharacter()); 
				BoardCell roomCenter = adjRoom.getCenterCell(); 
				cell.addAdj(roomCenter);
			}
			//if the below cell is a room, but not a doorway, then it does not go in adjList 
		}

	}

	public void removeSolutionCards(Solution solution) {
		//removed the specified room, person, and weapon from the deck
		deck.remove(solution.getRoom());
		deck.remove(solution.getPerson());
		deck.remove(solution.getWeapon());
	}

	public void shuffleDeck() {
		ArrayList<Card> initDeck = (ArrayList<Card>)deck.clone();
		ArrayList<Card> finalDeck = new ArrayList<Card>();
		Random random = new Random();

		while(initDeck.size() > 0) {
			//picks a random card from the deck
			int randInt = random.nextInt(initDeck.size());
			Card currentCard = initDeck.get(randInt);
			//only adds the card to the finalDeck if the adding position is different from its original position in the deck
			if(deck.indexOf(currentCard) != finalDeck.size()) {
				finalDeck.add(currentCard);
				//removes the selected card from the initial deck until it is empty
				initDeck.remove(currentCard);
			}else {
				continue;
			}

		}
		deck = finalDeck;
	}

	public boolean checkAccusation(Solution accusation) {
		//getting theAnswer cards, and the accusaed cards
		Card correctRoom = theAnswer.getRoom();
		Card correctPerson = theAnswer.getPerson();
		Card correctWeapon = theAnswer.getWeapon();
		Card accusedRoom = accusation.getRoom();
		Card accusedPerson = accusation.getPerson();
		Card accusedWeapon = accusation.getWeapon();
		if(accusedRoom == null || accusedPerson == null || accusedWeapon == null) {
			return false;
		}
		// returns true if and only if all the rooms, people, and weapons match
		if(correctRoom == accusedRoom && correctPerson == accusedPerson && correctWeapon == accusedWeapon) {
			return true;
		}
		return false;
	}

	public Card handleSuggestion(Player suggestingPlayer, ArrayList<Player> players, Solution solution) {
		//get start index
		int startIndex = players.indexOf(suggestingPlayer);
		String guessResult;
		//finding the player who was suggested
		int suggestingPlayerRow = suggestingPlayer.getRow();
		int suggestingPlayerCol = suggestingPlayer.getCol();
		Card suggestedPerson = solution.getPerson();
		Player suggestedPlayer = null;
		for(Player player : players) {
			if(player.getPlayerName().equals(suggestedPerson.getName())) {
				suggestedPlayer = player;
				break;
			}
		}
		//moving the suggestedPlayer to the same location as the suggestingPlayer
		suggestedPlayer.setCanSuggest(true);
		suggestedPlayer.setRow(suggestingPlayerRow);
		suggestedPlayer.setCol(suggestingPlayerCol);
		
		//processing all players
		//loop starts at the next player in line from the suggesting player
		//i.e. the player to the left of the suggesting player
		//ends at the last player left not including the suggesting player
		//i.e. the player to the right of the suggesting player
		for(int i = startIndex + 1; i < startIndex + players.size(); i++) { //loops from the next player from the suggestingPlayer through the players
			//index of the player in the arraylist, so an out of bounds exception is not thrown
			int playersIndex = i % players.size();
			//checking if each player can disprove a card
			Card disprovedCard = players.get(playersIndex).disproveSuggestion(solution.getRoom(), solution.getPerson(), solution.getWeapon());
			if(disprovedCard != null) {
				//returns the disproved card the first time it sees one
				
				if(suggestingPlayer == players.get(0)) {// if the suggesting player is the human player
					//show the disproving card and the player it came from
					guessResult = disprovedCard.getName() + " was disproved by " + players.get(playersIndex).getPlayerName();	
				}else{ //if the suggesting player is a computer player
					//showing that the suggestion was disproven and the player it came from
					//not showing the card itself
					guessResult = "A card was disproved by " + players.get(playersIndex).getPlayerName();
				}
				//TODO draw the guess result in the appropriate color
				GameControlPanel.getGCPanel().SetGuessResult(guessResult);
				return disprovedCard;
			}
		}
		//returns null if a disproved card is never found
		guessResult = "No cards were disproven";
		if(currentPlayer != players.get(0)) { //if a computer player
			//setting that the computer player is ready to make an accusation
			suggestingPlayer.setComputerReady(true);
		}
		GameControlPanel.getGCPanel().SetGuessResult(guessResult);
		return null;
	}

	public Set<BoardCell> getTargets() {
		return this.targets;
	}

	//skeletons for new methods to complete C20A Clue Players 1 
	public void deal() {
		//shuffles the deck before the deal
		shuffleDeck();
		while(deck.size() > 0) {
			//simulates selecting the top card from the deck, and dealing to players in a circle until the deck 
			for( Player player : players) {
				//giving the player the last card in the array
				Card topCard = deck.get(deck.size()-1);
				player.updateHand(topCard);
				deck.remove(topCard);
			}
		}

	}

	//Player getters 
	public int getNumHumanPlayers() {
		return numHumanPlayers; 
	}

	public int getNumComputerPlayers() {
		return numComputerPlayers; 
	}
	public ArrayList<Player> getPlayers() {
		return players; 
	}

	//Weapon getters
	public int getNumWeapons() {
		return numWeapons; 
	}
	public ArrayList<String> getWeapons() {
		return weapons; 
	}

	//Deck and Solution getters
	public ArrayList<Card> getDeck() {
		return this.deck; 
	}

	//theAnswer getters and setters
	public Solution getTheAnswer() {
		return theAnswer;
	}
	public void setTheAnswer(Solution theAnswer) {
		this.theAnswer = theAnswer;
	}
	public BoardCell[][] getGrid() {
		return grid;
	}
	public Set<BoardCell> getDoors() {
		return doors;
	}
	public Map<Character, Room> getRoomMap() {
		return roomMap;
	}
	public Player getHumanPlayer() {
		return humanPlayer;
	}

	public int rollDie() {
		Random randomRoll = new Random(); 
		return randomRoll.nextInt(6) + 1; 
	}

	public void processNextTurn() {
		//at the beginning of each turn we want to assume the human player is not up 
		GameControlPanel.getGCPanel().setGuess("");
		GameControlPanel.getGCPanel().SetGuessResult("");
		if(humanPlayerFinishedTurn) {
			isHumanPlayer = false;

			//updating the current player
			//making sure the current player index is not the last index
			if(currentPlayerIdx == players.size()) {
				//if so, setting it to 0 to go back to the first player in the list
				currentPlayerIdx = 0;
			}
			currentPlayer = players.get(currentPlayerIdx);
			
			currentPlayerRow = currentPlayer.getRow();
			currentPlayerCol = currentPlayer.getCol();
			currentPlayerCell = grid[currentPlayerRow][currentPlayerCol];
			
			//checking if the player is in a room
			if(currentPlayer.canSuggest()) {
				Solution suggestion = createHumanSuggestion();
				if(suggestion != null) {
					Card handledCard = handleSuggestion(currentPlayer, players, suggestion);
					if(handledCard != null) {
						//adding the handled card to the seen list of the player
						currentPlayer.addSeenCard(handledCard);
						currentPlayer.setCanSuggest(false);
						//exit since a suggestion was made
						return;
					}
				}
			}

			roll = rollDie();
			calcTargets(currentPlayerCell, roll);

			//setting the GUI elements in game control panel
			//sets the new player and the roll
			GameControlPanel.getGCPanel().setTurn(currentPlayer, roll);

			//checking to see if the player is the human player
			if(currentPlayer == players.get(0)) {
				isHumanPlayer = true; 
				humanPlayerFinishedTurn = false;
			}else {//if it is a computer player's turn
				//get the board cell of the target selected by the select target method
				BoardCell compTarget = currentPlayer.selectTarget(targets, this);
				//set the row and col of the current player to the row and col of the target cell
				
				//moving the player
				currentPlayerCell.setOccupied(false);
				currentPlayer.setRow(compTarget.getRow());
				currentPlayer.setCol(compTarget.getCol());
				currentPlayerCell = grid[currentPlayer.getRow()][currentPlayer.getCol()]; 
				currentPlayerCell.setOccupied(true);
				//if the CPU is in a room, make a suggestion
				if(compTarget.isRoomCenter()) {
					System.out.println( currentPlayer.getPlayerName() + " just entered a room");
					currentPlayer.createSuggestion(theInstance, roomMap.get(compTarget.getCharacter()));
				}

			}
			//after an entire turn has been processed, we increment the player index 
			currentPlayerIdx++; 
		}
	}

	public void processBoardClick(int x, int y, int width) {
		//seeing if it is  the human player's turn
		if(currentPlayer == players.get(0)) {
			clickedRow = y/width;
			clickedCol = x/width;
			//initially assuming not clicked on target, trying to prove wrong
			clickedOnTarget = false;
			//initially assuming that the player did not click on a room
			clickedOnRoom = false;
			for(BoardCell target : targets) {
				if(target.getRow() == clickedRow && target.getCol() == clickedCol) {
					clickedOnTarget = true;
				}
				if(target.isRoomCenter()) { //checking if a room is clicked on
					//associating the the room center back to the room
					clickedRoom = roomMap.get(target.getCharacter());
					//getting the top left cell from the room, the first cell in the arraylist
					topLeftRoomCell = clickedRoom.getRoomCells().get(0);
					//getting the bottom right cell from the room, the last cell in the arraylist
					bottomRightRoomCell = clickedRoom.getRoomCells().get(clickedRoom.getRoomCells().size()-1);
					//defining the boundaries that can be clicked on for the room
					int leftX = topLeftRoomCell.getCol() * width;
					int rightX = (bottomRightRoomCell.getCol()+1) * width;
					int topY = topLeftRoomCell.getRow() * width;
					int bottomY = (bottomRightRoomCell.getRow()+1) * width;
					//checking to see if the click falls within both the x and y parameters
					if(x >= leftX && x <= rightX && y >= topY && y <= bottomY) {
						clickedOnTarget = true;
						clickedOnRoom = true;
						break;
					}
				}
			}

			if(!clickedOnTarget) {
				JOptionPane.showMessageDialog(BoardPanel.getTheBoardPanel(), "Please select a valid target.", 
						"Invalid Target", JOptionPane.INFORMATION_MESSAGE);
				return;
			}else {
				//moving the player
				if(clickedOnRoom) {
					clickedRow = clickedRoom.getCenterCell().getRow();
					clickedCol = clickedRoom.getCenterCell().getCol();
				}
				currentPlayerCell.setOccupied(false);
				currentPlayer.setRow(clickedRow);
				currentPlayer.setCol(clickedCol);
				currentPlayerCell = grid[clickedRow][clickedCol];
				currentPlayerCell.setOccupied(true);
				//seeing if the player moved to a room
				if(clickedOnRoom) {
					createAndHandleSuggestion();
				}
				//flagging that the human player has finished their turn
				humanPlayerFinishedTurn = true; 
			}
			targets.clear();
		}
	}


	public void setCurrentPlayer(Player player) {
		this.currentPlayer = player; 
	}
	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return this.currentPlayer;
	}
	public int getRoll() {
		// TODO Auto-generated method stub
		return roll;
	}
	public boolean isHumanPlayer() {
		return isHumanPlayer;
	}
	
	public boolean getClickedOnTarget() {
		return this.clickedOnTarget; 		
	}
	public boolean getHumanFinished() {
		return this.humanPlayerFinishedTurn; 		
	}
	
	private Solution createHumanSuggestion() {
		// do not process if it's a computer player's turn
		if(currentPlayer != players.get(0)) {
				return null;
		}
		SuggestionAccusationPanel saPanel = new SuggestionAccusationPanel(true);
		saPanel.setVisible(true);
		//if the suggestion was properly submitted
		if(saPanel.getSelectedPerson() != null && saPanel.getSelectedRoom() != null && saPanel.getSelectedWeapon() != null) {
			Solution suggestion = new Solution();
			Card selectedRoom = null;
			Card selectedPerson = null;
			Card selectedWeapon = null;
			for(Card card : allCards) {
				if(card.getName() == saPanel.getSelectedRoom()) {
					selectedRoom = card;
				}
				if(card.getName() == saPanel.getSelectedPerson()) {
					selectedPerson = card;
				}
				if(card.getName() == saPanel.getSelectedWeapon()) {
					selectedWeapon = card;
				}
			}
			suggestion.setRoom(selectedRoom);
			suggestion.setPerson(selectedPerson);
			suggestion.setWeapon(selectedWeapon);
			GameControlPanel.getGCPanel().setGuess("I suggest that " + selectedPerson.getName() + " used a " + selectedWeapon.getName() + " in the " + selectedRoom.getName() + ".");
			return suggestion;
		}
		return null;
	}
	
	private void createAndHandleSuggestion() {
		// do not process if it's a computer player's turn
		if(currentPlayer != players.get(0)) {
			return;
		}
		Solution suggestion = createHumanSuggestion();
		Card handledCard = handleSuggestion(currentPlayer, players, suggestion);
		if(handledCard != null) {
			//adding the handled card to the seen list of the player
			currentPlayer.addSeenCard(handledCard);
			//after adding card to the player's seen list we need to update the known cards panel 
			KnownCardsPanel.getKCPanel().updateDisplay();
		}
	}
	public void processAccusation() {
		Solution accusation;
		// do not process if it's a computer player's turn
		accusation = currentPlayer.doAccusation();
		if(accusation == null) {
			return;
		}
		boolean correctSol = checkAccusation(accusation);
		if(correctSol) {
			//put up a splash screen saying the game was won
			JOptionPane.showMessageDialog(null, "Congrats! You won the game!");
		}else {
			//if human player
			if(currentPlayer == players.get(0)) {
				JOptionPane.showMessageDialog(null, "You lost the game. Loser!" );
			}else {// if CPU
				players.remove(currentPlayer);
				return;
			}
		}
		JOptionPane.showMessageDialog(null, "The correct solution was " + theAnswer.getRoom().getName() + " " + theAnswer.getPerson().getName() + " " + theAnswer.getWeapon().getName());
		System.exit(0);
	}
}

