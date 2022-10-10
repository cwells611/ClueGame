package tests;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

import static org.junit.Assert.*;

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
		BoardCell cell;
		
		cell = theBoard.getCell(8, 3);
		assertEquals('R', cell.getCharacter());
		
		cell = theBoard.getCell(15, 12);
		assertEquals('W', cell.getCharacter());
		
		cell = theBoard.getCell(9, 15);
		assertEquals('X', cell.getCharacter());
		
		cell = theBoard.getCell(8, 20);
		assertEquals('D', cell.getCharacter());
		
		cell = theBoard.getCell(18, 23);
		assertEquals('A', cell.getCharacter());
		
		cell = theBoard.getCell(1, 12);
		assertEquals('L', cell.getCharacter());
		
		cell = theBoard.getCell(11, 12);
		assertEquals('R', cell.getCharacter());
		
		cell = theBoard.getCell(23, 10);
		assertEquals('R', cell.getCharacter());
	}
	
//	check that rooms have the proper center cell and label cell.
//	implement @BeforeEach method or @BeforeAll
}
