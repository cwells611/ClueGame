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
    		System.out.println("File not found.");
    	}
    }
     
	
	public void loadSetupConfig() throws FileNotFoundException{
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
			if(numColumns == 0) {
				//determines the number of columns by seeing how many labels are on each line 
				String[] splitLine = currentLine.split(","); 
				numColumns = splitLine.length; 
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
	
	public static void main(String[] args) {
		Board test = new Board(); 
		test.setConfigFiles("ClueLayout.csv", "ClueSetup.txt"); 
		test.initialize();
		System.out.println(test.getCell(20, 20).getCharacter());
	}
}
	
