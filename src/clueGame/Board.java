package clueGame;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

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
	private Set<Player> players; 
	char label;
	Room room;
	String playerName; 
	String playerColor; 
	int playerStartRow; 
	int playerStartCol; 
	int numHumanPlayers;
	int numComputerPlayers; 
	private static Board theInstance = new Board();

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
			visited = new HashSet<BoardCell>();
			targets = new HashSet<BoardCell>(); 
			roomMap = new HashMap<Character, Room>();
			doors = new HashSet<BoardCell>();
			players = new HashSet<Player>(); 
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
					player = new HumanPlayer(playerName, playerColor, playerStartRow, playerStartCol, lineInfo[0]); 
					//increment human players counter
					numHumanPlayers++; 
					//once human player is created, add it to set 
					players.add(player); 
				}
				else {
					player = new ComputerPlayer(playerName, playerColor, playerStartRow, playerStartCol, lineInfo[0]); 
					//increment computer players counter 
					numComputerPlayers++; 
					//once computer player is created, add it to list 
					players.add(player); 
				}
			}
			else {
				throw new BadConfigFormatException("Setup text file not written properly, check spelling and spaces"); 
			}
		}
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
				if(cell.length() == 2) {
					switch(cell.charAt(1)) {
					case '#':
						roomMap.get(initial).setLabelCell(currentCell);
						currentCell.setRoomLabel(true);
						break;
					case '*':
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
						if(currentCenter != null) {
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
		tellRoomCellsSecretPassage(); 
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

	public void tellRoomCellsSecretPassage() {
		//if a room has a secret passage, loop through every cell in that room and tell it, it has a secret passage 
		for(Room room : roomMap.values()) {
			if(room.getName().equals("Walkway") || room.getName().equals("Unused")) {
				continue;
			}
			if(room.getCenterCell().hasSecretPassage()) {
				for(BoardCell roomCell : room.getRoomCells()) {
					roomCell.setSecretPassage(room.getSecretPassage()); 
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

	public Set<BoardCell> getTargets() {
		return this.targets;
	}
	
	//skeletons for new methods to complete C20A Clue Players 1 
	public void deal() {
		//some code goes here 
	}
	
	//methods to tell the board how many human and computer players are in the game 
	public int getNumHumanPlayers() {
		return numHumanPlayers; 
	}
	
	public int getNumComputerPlayers() {
		return numComputerPlayers; 
	}
	
	public static void main(String[] args) throws FileNotFoundException, BadConfigFormatException {
		Board test = new Board(); 
		test.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		test.initialize();
		System.out.println(test.getNumHumanPlayers());
	}
	
}

