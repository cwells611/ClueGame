package tests;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class FileInitTest {
	//constant variables to hold board size 
	public static final int ROWS = 25; 
	public static final int COLS = 25; 
	private static Board theBoard;
	
	
	//method to be run before each test, will create a board, set up the config files, and initialize the board 
	@BeforeAll
	public static void setBoard() {
		//since we are only using one instatce of this board 
		theBoard.getInstance(); 
		//has the board read in the config files and setup board based on files 
		theBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		//loads both files even tho we are only using one instance of board 
		theBoard.initialize();
	}
	
//	ensure your layout and setup files are loaded correctly (correct number of rooms, test several entries including first and last in file)
	@Test 
	public void testNumRooms() {
		//test to make sure that the correct number of rooms and the correct room names got loaded from files 
		int numRooms = 0;
		//loop through each cell, if that cell is the label cell, then add one to numRooms
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				if(theBoard.getCell(row, col).isLabel()) {
					numRooms++; 
				}
			}
		}
		//check to make sure the correct number of rooms has been loaded
		assertEquals(9, numRooms); 
	}
	
	@Test
	public void testRoomNames() {
		//test to make sure that the correct room names were loaded in based on the labels 
		assertEquals("Chapter", theBoard.getRoom('C').getName()); 
		assertEquals("Game Room", theBoard.getRoom('G').getName());
		assertEquals("Bedroom", theBoard.getRoom('B').getName());
		assertEquals("Lavatory", theBoard.getRoom('L').getName());
		assertEquals("Sauna", theBoard.getRoom('S').getName());
		assertEquals("Theater", theBoard.getRoom('T').getName());
		assertEquals("Atrium", theBoard.getRoom('A').getName());
		assertEquals("Roof", theBoard.getRoom('R').getName());
		assertEquals("Deck", theBoard.getRoom('D').getName());
	}
	
	@Test
	public void testRoomCharacteristicts() {
		//picks some cells in certain rooms, tests to make sure those cells are associated with those rooms 
		BoardCell cell = theBoard.getCell(6, 1); 
		Room room = theBoard.getRoom(cell); 
		//checks to make sure that the room name is correct 
		assertEquals("Roof", room.getName()); 
		//checks to make sure cell is not doorway 
		assertFalse(cell.isDoorway());
		//checks to make sure that the room is an actual room 
		assertTrue(room != null); 
		
		//picks a cell that is not a room 
		cell = theBoard.getCell(3, 0); 
		room = theBoard.getRoom(cell);
		//makes sure that the current cell is not a room 
		assertTrue(room == null); 
		
		//picks a cell that is the center of a room 
		cell = theBoard.getCell(10, 10);
		room = theBoard.getRoom(cell);
		//tests to make sure the center of the room is cell 
		assertTrue(cell.isRoomCenter()); 
		
		//picks a cell that is a doorway 
		cell = theBoard.getCell(1, 10); 
		//tests to make sure that isDoorway is treu 
		assertTrue(cell.isDoorway()); 
		//tests to make sure that the cell is a room 
		assertTrue(room == null); 
	}
	
//	ensure the correct number of rows/columns have been read
	@Test
	public void checkRowsCols() {
		assertEquals(ROWS, theBoard.getNumRows());
		assertEquals(COLS, theBoard.getNumColumns());
	}
//	verify at least one doorway in each direction. Also verify cells that don't contain doorways return false for isDoorway().
	public void testDoorwayDirections() {
		BoardCell cell;
		//testing left doorway
		cell = theBoard.getCell(7, 5);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		//testing left doorway
		cell = theBoard.getCell(21, 17);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		//testing up doorway
		cell = theBoard.getCell(3, 2);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		//testing up doorway
		cell = theBoard.getCell(6, 9);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		//testing a walkway to verify it is not a doorway
		cell = theBoard.getCell(0, 3);
		assertFalse(cell.isDoorway());
		
	}
//	check that the correct number of doors have been loaded.
	@Test 
	public void checkNumDoors() {
		//keeps track of number of doors 
		int doors = 0; 
		//nested for loop that loops through both rows and colums and tests if isDoorway is true 
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				if(theBoard.getCell(row, col).isDoorway()) {
					//if cell is a doorway, add one to doors
					doors++; 
				}
			}
		}
		//tests to make sure doors is equal to number of doorways in layout 
		assertEquals(14, doors); 
	}
//	check some of the cells to ensure they have the correct initial
	public void initialTest() {
		//testing 8 random cells to ensure they have the correct initial
	}
	
//	check that rooms have the proper center cell and label cell.

}
