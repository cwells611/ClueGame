package clueGame;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

import experiment.TestBoardCell;

public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	//private Map<Character, Character> secretPassages; 
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private Set<BoardCell> doors;
	private static Board theInstance = new Board();
	
    // constructor is private to ensure only one can be created
    private Board() {
           super();
    }
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize(){
    	try {
    		numRows = 0; 
            numColumns = 0; 
            visited = new HashSet<BoardCell>();
            targets = new HashSet<BoardCell>(); 
            roomMap = new HashMap<Character, Room>();
            //secretPassages = new HashMap<Character, Character>();
            doors = new HashSet<BoardCell>();
    		loadSetupConfig(); 
    		loadLayoutConfig(); 
    		//loop through grid and calc adjacencies 
    		for(int row = 0; row < numRows; row++) {
    			for(int col = 0; col < numColumns; col++) {
    				BoardCell cell = new BoardCell(row, col); 
    				calcAdjacencies(cell); 
    			}
    		}
    	}catch(FileNotFoundException e) {
    		System.out.println(e);
    	}catch(BadConfigFormatException e) {
    		System.out.println(e);
    	}
    }
     
	
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException{
		//load in ClueSetup.txt and then add the room character and room name to the map 
		//assign file object to setup file 
		FileReader setupReader = new FileReader(setupConfigFile);  
		//setup scanner
		Scanner setupScanner = new Scanner(setupReader); 
		//loop to read in file data 
		while(setupScanner.hasNextLine()) {
			//reads in line of file  
			String currentLine = setupScanner.nextLine(); 
			//checks to make sure the line is not a comment, if it is, then just continues to read in next line 
			//since that line has no info the program needs  
			if(currentLine.contains("//")) {
				continue;
			}
			//takes each line and splits up line by comma then space
			String[] lineInfo = currentLine.split(", "); 
			//checks to make sure that the first string in the array is either Room or Space, if not, throws error
			if(lineInfo[0].equals("Room") || lineInfo[0].equals("Space")) {
				//converts the string containing the label to a char 
				char label = lineInfo[2].charAt(0); 
				//creates a room out of the room name string contained in lineInfo 
				Room room = new Room(lineInfo[1], label); 
				//adds label and room to the map 
				roomMap.put(label, room); 
			}
			else {
				throw new BadConfigFormatException("Setup text file not written properly, check spelling and spaces"); 
			}
		}
	}
	
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		//load in ClueLayout.txt and then add the room character and room name to the map 
		//assign file object to setup file 
		FileReader layoutReader = new FileReader(layoutConfigFile);  
		//setup scanner
		Scanner layoutScanner = new Scanner(layoutReader); 
		//need to first find the number of rows and cols to as to initialize the grid before we add anything to it
		//creates new reader and scanner for loop that determines numRows and numColumns 
		FileReader numsReader = new FileReader(layoutConfigFile); 
		Scanner numsScanner = new Scanner(numsReader); 
		while(numsScanner.hasNextLine()) {
			String currentLine = numsScanner.nextLine(); 
			numRows++;
			//since the board is a square, we only need to set the numColumns once because each 
			//subsequent line will have the same number of columns 
			//determines the number of columns by seeing how many labels are on each line 
			String[] splitLine = currentLine.split(","); 
			if(numColumns == 0) {
				//since a proper board will have the same number of labels on each row, we only 
				//need to set numColumns once 
				numColumns = splitLine.length; 
			}
			//after numColumns has been set initially in above if-statement, if the length of the 
			//current line is not equal to numColumns, throw an error 
			if(splitLine.length != numColumns) {
				throw new BadConfigFormatException(); 
			}
		}
		//initialize grid 
		this.grid = new BoardCell[numRows][numColumns]; 
		int row = 0;
		while(layoutScanner.hasNextLine()) {
			//create a board cell for each char
			String currentLine = layoutScanner.nextLine(); 
			String[] splitLine = currentLine.split(",");
			int column = 0;
			//used for door setup
			for(String cell : splitLine) {
				//create a new board cell
				char initial = cell.charAt(0);
				BoardCell currentCell = new BoardCell(row, column, initial);
				//if the initial is not a W or an X, the set getIsRoom to true so we know the cell is a room
//				if(initial != 'W' && initial != 'X') {
//					currentCell.setIsRoom(true);
//				}
				
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
						//if a room has a secret passage, then we want to get the center cell of that room 
						//as tell that center cell it was a secret passage 
						Room currentRoom = roomMap.get(cell.charAt(0)); 
						BoardCell currentCenter = currentRoom.getCenterCell(); 
						//if that center cell has been found then just tell that center cell it was a secret passage
						if(currentCenter != null) {
							currentCenter.setSecretPassage(cell.charAt(1));
						}
						//if not then we tell the room that if has a secret passage 
						//and tell it the center cell of the room its secret passage connects to 
						else {
							currentRoom.setSecretPassage(roomMap.get(cell.charAt(1)).getCenterCell());
						}
					}
				}
				//check to see if the set of keys in the map of rooms contains the initial of the current
				//cell, if not throw and error 
				if(!roomMap.containsKey(initial)) {
					throw new BadConfigFormatException(); 
				}
				grid[row][column] = currentCell;
				column++;
			}
			row++;
		}
		
		for(BoardCell door : doors) {
			char adjChar;
			Room adjRoom;
			switch(door.getDoorDirection()) {
			case UP:
				adjChar = grid[door.getRow()-1][door.getCol()].getCharacter();
				adjRoom = roomMap.get(adjChar);
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
		//returns room from map that is associated with the char passed as the parameter 
		return roomMap.get(Room); 
	}
	
	public Room getRoom(BoardCell cell) {
		//gets the label that is associated with the cell passed as the parameter 
		char roomLabel = cell.getCharacter(); 
		//returns room that is associated with the char in the map 
		return roomMap.get(roomLabel); 
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCell(int row, int col) {
		//returns cell that in grid at row, col 
		return grid[row][col]; 
	}
	public Set<BoardCell> getAdjList(int i, int j) {
		return grid[i][j].getAdjList();
	}
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.add(startCell); 
		recursiveCalcTargets(startCell, pathLength);
	}
	
	// method that will determine the possible targets from a certain roll
	public void recursiveCalcTargets(BoardCell startCell, int pathLength) {
		//calculates adjacency list for cell we are currently looking at 
		calcAdjacencies(startCell); 
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
					}else {
						recursiveCalcTargets(adjCell, pathLength - 1);
					}
					visited.remove(adjCell);
				}
			}
		}
	}
	
	public void calcAdjacencies(BoardCell cell) {
		//System.out.println("[" + cell.getRow() + ", " + cell.getCol() + "]");
		//checking if the cell is a room
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
			if(leftCell.getCharacter() == 'W' || leftCell.getCharacter() == 'X') {
				cell.addAdj(leftCell);
			}
			//if the cell is not a walkway or unused space, then it must be a room, if that cell is 
			//a doorway, then we get the center cell of that room and add it to adjList
			else if(cell.isDoorway() == true && cell.getDoorDirection() == DoorDirection.LEFT) {
				Room adjRoom = roomMap.get(leftCell.getCharacter()); 
				BoardCell roomCenter = adjRoom.getCenterCell(); 
				cell.addAdj(roomCenter);
			}
			//if the left cell is a room, but not a doorway, then it does not go in adjList 
		}
		// testing right edge
		if (cell.getCol() != numColumns - 1) {
			BoardCell rightCell = grid[cell.getRow()][cell.getCol() + 1];
			//if the cell to the right is just a walkway or an unused space, then just add to adjList
			if(rightCell.getCharacter() == 'W' || rightCell.getCharacter() == 'X') {
				cell.addAdj(rightCell);
			}
			//if the cell is not a walkway or unused space, then it must be a room, if that cell is 
			//a doorway, then we get the center cell of that room and add it to adjList
			else if(cell.isDoorway() == true && cell.getDoorDirection() == DoorDirection.RIGHT) {
				Room adjRoom = roomMap.get(rightCell.getCharacter()); 
				BoardCell roomCenter = adjRoom.getCenterCell(); 
				cell.addAdj(roomCenter);
			}
			//if the right cell is a room, but not a doorway, then it does not go in adjList 
		}
		// testing top edge
		if (cell.getRow() != 0) {
			BoardCell upperCell = grid[cell.getRow() - 1][cell.getCol()];
			//if the cell above is just a walkway or an unused space, then just add to adjList
			if(upperCell.getCharacter() == 'W' || upperCell.getCharacter() == 'X') {
				cell.addAdj(upperCell);
			}
			//if the cell is not a walkway or unused space, then it must be a room, if that cell is 
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
			if(lowerCell.getCharacter() == 'W' || lowerCell.getCharacter() == 'X') {
				cell.addAdj(lowerCell);
			}
			//if the cell is not a walkway or unused space, then it must be a room, if that cell is 
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
	
//	public static void main(String[] args) {
//		Board theBoard = new Board(); 
//		theBoard = Board.getInstance(); 
//		//has the board read in the config files and setup board based on files 
//		theBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
//		//loads both files even though we are only using one instance of board 
//		theBoard.initialize();
//		
//		theBoard.calcAdjacencies(theBoard.getCell(10, 19));
//		Set<BoardCell> adjList = theBoard.getAdjList(10, 19); 
//		for(BoardCell cell : adjList) {
//			System.out.println(cell.getRow() + ", " + cell.getCol());
//		}
//	}
}
	
