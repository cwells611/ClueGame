package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class BoadAdjTargetTest {
	//constant variables to hold board size 
	public static final int ROWS = 25; 
	public static final int COLS = 25; 
	private static Board theBoard;


	//method to be run before each test, will create a board, set up the config files, and initialize the board 
	@BeforeAll
	public static void setUp() {
		//since we are only using one instance of this board 
		theBoard = Board.getInstance(); 
		//has the board read in the config files and setup board based on files 
		theBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		//loads both files even though we are only using one instance of board 
		theBoard.initialize();
	}
	

/*
 * ADJACENCY TESTS
 */
	
//	Locations with only walkways as adjacent locations
	@Test
	public void testWalkwayAdjacencies(){
		//test spot (4,2)
		//theBoard.calcAdjacencies(theBoard.getCell(4, 2));
		Set<BoardCell> adjList = theBoard.getAdjList(4,2);
		assertEquals(4, adjList.size());
		assertTrue(adjList.contains(theBoard.getCell(4, 1)));
		assertTrue(adjList.contains(theBoard.getCell(4, 3)));
		assertTrue(adjList.contains(theBoard.getCell(3, 2)));
		assertTrue(adjList.contains(theBoard.getCell(5, 2)));
	}
//	Locations within rooms not center (should have empty adjacency list)
	@Test
	public void testNotCenterRoomAdjacencies() {
		//test spot (10, 0)
		Set<BoardCell> adjList = theBoard.getAdjList(10, 0); 
		assertEquals(0, adjList.size()); 
	}
//	Locations that are at each edge of the board
	@Test
	public void testEdgeAdjacencies(){
		//test spot (10,24)
		Set<BoardCell> adjList = theBoard.getAdjList(10, 24); 
		assertEquals(2, adjList.size()); 
		assertTrue(adjList.contains(theBoard.getCell(11, 24))); 
		assertTrue(adjList.contains(theBoard.getCell(10, 23))); 
	}
//	Locations that are beside a room cell that is not a doorway	
	@Test
	public void testBesideRoomAdjacency() {
		//test spot (24, 8)
		Set<BoardCell> adjList = theBoard.getAdjList(24, 8); 
		assertEquals(2, adjList.size());
		assertTrue(adjList.contains(theBoard.getCell(24, 7))); 
		assertTrue(adjList.contains(theBoard.getCell(24, 9)));
	}
//	Locations that are doorways
	@Test
	public void testDoorwayAdjacency() {
		//test spot (10, 19)
		Set<BoardCell> adjList = theBoard.getAdjList(10, 19); 
		assertEquals(4, adjList.size()); 
		assertTrue(adjList.contains(theBoard.getCell(10, 18)));
		assertTrue(adjList.contains(theBoard.getCell(10, 20))); 
 		assertTrue(adjList.contains(theBoard.getCell(11, 19))); 
 		assertTrue(adjList.contains(theBoard.getCell(5, 21))); 
	}
//	Locations that are connected by secret passage	
	@Test
	public void testSecretPassage() {
		//test being in a room with a secret passage
		//test being at the center cell of the atrium (19, 21)
		Set<BoardCell> adjList = theBoard.getAdjList(19, 21);
		assertEquals(4, adjList.size());
		//the only adjacent cells would be the center cell of the 
		//room that the secret passage connects to, and the doors of the room you are currently in 
		assertTrue(adjList.contains(theBoard.getCell(2, 7)));
		assertTrue(adjList.contains(theBoard.getCell(14, 22)));
		assertTrue(adjList.contains(theBoard.getCell(21, 17)));
		assertTrue(adjList.contains(theBoard.getCell(24, 24)));
	}
	
/*
 * TARGET TESTS
 */
	
//	Targets along walkways, at various distances
	@Test
	public void testWalkwayTargets(){
		Set<BoardCell> targets;
		//roll of 1
		theBoard.calcTargets(theBoard.getCell(24, 0), 1);
		targets = theBoard.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(theBoard.getCell(23, 0)));
		assertTrue(targets.contains(theBoard.getCell(24, 1)));
		
		//roll of 2
		theBoard.calcTargets(theBoard.getCell(24, 0), 2);
		targets = theBoard.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(theBoard.getCell(22, 0)));
		assertTrue(targets.contains(theBoard.getCell(23, 1)));
		assertTrue(targets.contains(theBoard.getCell(24, 2)));
		
		//roll of 3
		theBoard.calcTargets(theBoard.getCell(24, 0), 3);
		targets = theBoard.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(theBoard.getCell(21, 0)));
		assertTrue(targets.contains(theBoard.getCell(23, 0)));
		assertTrue(targets.contains(theBoard.getCell(24, 1)));
		assertTrue(targets.contains(theBoard.getCell(24, 3)));
		assertTrue(targets.contains(theBoard.getCell(23, 2)));
	}
	
//	Targets that allow the user to enter a room
	@Test
	public void testEnterTargets() {
		//test cell with different rolls that will allow player to enter a room 
		//test cell (8,5) with roll 2
		
		Set<BoardCell> targets;
		
		theBoard.calcTargets(theBoard.getCell(8, 5), 2);
		targets = theBoard.getTargets(); 
		assertEquals(5, targets.size()); 
		assertTrue(targets.contains(theBoard.getCell(10, 2))); 
		assertTrue(targets.contains(theBoard.getCell(10, 5))); 
		assertTrue(targets.contains(theBoard.getCell(6, 5))); 
		assertTrue(targets.contains(theBoard.getCell(9, 6))); 
		assertTrue(targets.contains(theBoard.getCell(7, 6)));
		
		//test cell (15, 11) with roll 4
		theBoard.calcTargets(theBoard.getCell(15, 11), 4);
		targets = theBoard.getTargets(); 
		assertEquals(13, targets.size()); 
		assertTrue(targets.contains(theBoard.getCell(10, 10))); 
		assertTrue(targets.contains(theBoard.getCell(20, 11))); 
		assertTrue(targets.contains(theBoard.getCell(15, 7))); 
		assertTrue(targets.contains(theBoard.getCell(16, 14))); 
		assertTrue(targets.contains(theBoard.getCell(16, 10)));
		assertTrue(targets.contains(theBoard.getCell(16, 8)));
		assertTrue(targets.contains(theBoard.getCell(16, 12)));
		assertTrue(targets.contains(theBoard.getCell(14, 8)));
		assertTrue(targets.contains(theBoard.getCell(14, 14)));
		assertTrue(targets.contains(theBoard.getCell(14, 12)));
		assertTrue(targets.contains(theBoard.getCell(15, 13)));
		assertTrue(targets.contains(theBoard.getCell(14, 10)));
		assertTrue(targets.contains(theBoard.getCell(15, 9)));
	}
	
//	Targets calculated when leaving a room without secret passage
	@Test
	public void testLeavingNoPassage() {
		//testing Chapter
		Set<BoardCell> targets;
		//roll of 1
		theBoard.calcTargets(theBoard.getCell(20, 3), 1);
		targets = theBoard.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(theBoard.getCell(21, 6)));
		
		//roll of 3
		theBoard.calcTargets(theBoard.getCell(20, 3), 3);
		targets = theBoard.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(theBoard.getCell(19, 6)));
		assertTrue(targets.contains(theBoard.getCell(23, 6)));
		
		//roll of 5
		theBoard.calcTargets(theBoard.getCell(20, 3), 5);
		targets = theBoard.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(theBoard.getCell(17, 6)));
		assertTrue(targets.contains(theBoard.getCell(23, 4)));
		assertTrue(targets.contains(theBoard.getCell(24, 5)));
		assertTrue(targets.contains(theBoard.getCell(24, 7)));
	}
	
//	Targets calculated when leaving a room with secret passage
	@Test
	public void testLeavingPassage() {
		Set<BoardCell> targets;
		//test cell (10, 2) with roll 2
		theBoard.calcTargets(theBoard.getCell(10, 2), 2);
		targets = theBoard.getTargets(); 
		assertEquals(4, targets.size()); 
		assertTrue(targets.contains(theBoard.getCell(7, 6))); 
		assertTrue(targets.contains(theBoard.getCell(6, 5))); 
		assertTrue(targets.contains(theBoard.getCell(8, 5))); 
		assertTrue(targets.contains(theBoard.getCell(10, 10))); 
		
		//test cell (2, 13) with roll 3
		theBoard.calcTargets(theBoard.getCell(2, 13), 3);
		targets = theBoard.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(theBoard.getCell(5, 11)));
		assertTrue(targets.contains(theBoard.getCell(6, 12)));
		assertTrue(targets.contains(theBoard.getCell(6, 14)));
		assertTrue(targets.contains(theBoard.getCell(5, 15)));
		assertTrue(targets.contains(theBoard.getCell(16, 12)));  
		assertTrue(targets.contains(theBoard.getCell(15, 13)));  
		assertTrue(targets.contains(theBoard.getCell(16, 14)));  
		assertTrue(targets.contains(theBoard.getCell(24, 10)));  
		assertTrue(targets.contains(theBoard.getCell(24, 12)));  
	}
	
//	Targets that reflect blocking by other players
	//@Test
	public void testPlayerBlock() {
		Set<BoardCell> targets;
		//test cell (12, 17) with occupied cell: (12,18)
		//roll 2
		theBoard.getCell(12, 18).setOccupied(true);
		theBoard.calcTargets(theBoard.getCell(12, 17), 2);
		targets = theBoard.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(theBoard.getCell(10, 17))); 
		assertTrue(targets.contains(theBoard.getCell(11, 18)));
		assertTrue(targets.contains(theBoard.getCell(13, 18)));
		assertTrue(targets.contains(theBoard.getCell(14, 17)));
		assertFalse(targets.contains(theBoard.getCell(12, 18)));
		
		
		//roll 3
		theBoard.getCell(12, 18).setOccupied(true);
		theBoard.calcTargets(theBoard.getCell(12, 17), 3);
		targets = theBoard.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(theBoard.getCell(9, 17))); 
		assertTrue(targets.contains(theBoard.getCell(10, 18)));
		assertTrue(targets.contains(theBoard.getCell(11, 19)));
		assertTrue(targets.contains(theBoard.getCell(13, 19)));
		assertTrue(targets.contains(theBoard.getCell(14, 18)));
		assertTrue(targets.contains(theBoard.getCell(15, 17)));
		assertFalse(targets.contains(theBoard.getCell(12, 18)));
		
		//test cell (5, 7) with roll 3 and cell (7, 6) occupied 
		theBoard.getCell(7, 6).setOccupied(true);
		theBoard.calcTargets(theBoard.getCell(5, 7), 3);
		targets = theBoard.getTargets();
		assertEquals(9, targets.size()); 
		assertTrue(targets.contains(theBoard.getCell(5, 10)));
		assertTrue(targets.contains(theBoard.getCell(5, 4)));
		assertTrue(targets.contains(theBoard.getCell(4, 5)));    
		assertTrue(targets.contains(theBoard.getCell(4, 9)));
		assertTrue(targets.contains(theBoard.getCell(6, 9)));
		assertTrue(targets.contains(theBoard.getCell(5, 8)));  
		assertTrue(targets.contains(theBoard.getCell(6, 7)));  
		assertTrue(targets.contains(theBoard.getCell(6, 5)));  
		assertTrue(targets.contains(theBoard.getCell(5, 6)));  
		assertFalse(targets.contains(theBoard.getCell(7, 6)));
	}
}
