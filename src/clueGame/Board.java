package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private File configFiles; 
	private Scanner configScanner; 
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private static Board theInstance = new Board();
	
    // constructor is private to ensure only one can be created
    private Board() {
           super() ;
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
    		System.out.println("File not found.");
    	}
    }
     
	
	public void loadSetupConfig() throws FileNotFoundException{
		//load in ClueSetup.txt and then add the room character and room name to the map 
		//assign file object to setup file 
		configFiles = new File(setupConfigFile); 
		//setup scanner
		configScanner = new Scanner(configFiles); 
		//loop to read in file data 
		while(configScanner.hasNextLine()) {
			//reads in line of file  
			String currentLine = configScanner.nextLine(); 
			//checks to make sure the line is not a comment, if it is, then just continues to read in next line 
			//since that line has no info the program needs  
			if(currentLine.contains("//")) {
				continue;
			}
			//takes each line and splits up line by comma then space
			String[] lineInfo = currentLine.split(", "); 
			//converts the string containing the label to a char 
			char label = lineInfo[2].charAt(0); 
			//creates a room out of the room name string contained in lineInfo 
			Room room = new Room(lineInfo[1]); 
			//adds label and room to the map 
			roomMap.put(label, room); 
		}
	}
	
	public void loadLayoutConfig() throws FileNotFoundException {
		//load in ClueLayout.txt and then add the room character and room name to the map 
		//assign file object to setup file 
		configFiles = new File(layoutConfigFile); 
		//setup scanner
		configScanner = new Scanner(configFiles); 
		int row = 0;
		while(configScanner.hasNextLine()) {
			//create a board cell for each char
			String currentLine = configScanner.nextLine(); 
			String[] splitLine = currentLine.split(",");
			int column = 0;
			for(String cell : splitLine) {
				//create a new board cell
				char initial = cell.charAt(0);
				BoardCell currentCell = new BoardCell(row, column, initial);
				
				if(cell.length() == 2) {
					switch(cell.charAt(1)) {
					case '#':
						currentCell.setRoomLabel(true);
						break;
					case '*':
						currentCell.setRoomCenter(true);
						break;
					case '^':
					case 'v':
					case '>':
					case '<':
						currentCell.setDoorway(true);
						break;
					default:
						currentCell.setSecretPassage(cell.charAt(1));
					}
					
					grid[row][column] = currentCell;
				}
				column++;
				numColumns++;
			}
			row++;
			numRows++;
		}
	}
	
	public void setConfigFiles(String csvFile, String txtFile) {
		this.layoutConfigFile = csvFile; 
		this.setupConfigFile = txtFile; 
	}
	
	public Room getRoom(char Room) {
		Room room = new Room(); 
		return room;
	}
	
	public Room getRoom(BoardCell cell) {
		Room room = new Room(); 
		return room; 
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCell(int row, int col) {
		return new BoardCell();
	}
}
	
