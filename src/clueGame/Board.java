package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private static Board theInstance = new Board();
	
    // constructor is private to ensure only one can be created
    private Board() {
           super();
           numRows = 0; 
           numColumns = 0; 
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
    		loadSetupConfig(); 
    		loadLayoutConfig(); 
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
				Room room = new Room(lineInfo[1]); 
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
			for(String cell : splitLine) {
				//create a new board cell
				char initial = cell.charAt(0);
				BoardCell currentCell = new BoardCell(row, column, initial);
				
				if(cell.length() == 2) {
					switch(cell.charAt(1)) {
					case '#':
						roomMap.get(initial).setLabelCell(currentCell);
						currentCell.setRoomLabel(true);
						break;
					case '*':
						roomMap.get(initial).setCenterCell(currentCell);
						currentCell.setRoomCenter(true);
						break;
					case '^':
						currentCell.setDoorDirection(DoorDirection.UP);
						currentCell.setDoorway(true);
						break;
					case 'v':
						currentCell.setDoorDirection(DoorDirection.DOWN);
						currentCell.setDoorway(true);
						break;
					case '>':
						currentCell.setDoorDirection(DoorDirection.RIGHT);
						currentCell.setDoorway(true);
						break;
					case '<':
						currentCell.setDoorDirection(DoorDirection.LEFT);
						currentCell.setDoorway(true);
						break;
					default:
						currentCell.setSecretPassage(cell.charAt(1));
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
}
	
