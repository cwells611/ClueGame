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
	//@Test
	public void testWalkwayAdjacencies(){
		//test spot (4,2)
		Set<BoardCell> adjList = theBoard.getAdjList(4,2);
		assertEquals(4, adjList.size());
		assertTrue(adjList.contains(theBoard.getCell(4, 1)));
		assertTrue(adjList.contains(theBoard.getCell(4, 3)));
		assertTrue(adjList.contains(theBoard.getCell(3, 2)));
		assertTrue(adjList.contains(theBoard.getCell(5, 2)));
	}
//	Locations within rooms not center (should have empty adjacency list)
	//@Test
	public void testNotCenterRoomAdjacencies() {
		//test spot (0,10)
		Set<BoardCell> adjList = theBoard.getAdjList(0, 10); 
		assertEquals(0, adjList.size()); 
	}
//	Locations that are at each edge of the board
	//@Test
	public void testEdgeAdjacencies(){
		//test spot (10,24)
		Set<BoardCell> adjList = theBoard.getAdjList(1, 24); 
		assertEquals(3, adjList.size()); 
		assertTrue(adjList.contains(theBoard.getCell(7, 23))); 
		assertTrue(adjList.contains(theBoard.getCell(11, 24))); 
		assertTrue(adjList.contains(theBoard.getCell(10, 23))); 
	}
//	Locations that are beside a room cell that is not a doorway	
	//@Test
	public void testBesideRoomAdjacency() {
		//test spot (24, 8)
		Set<BoardCell> adjList = theBoard.getAdjList(24, 8); 
		assertEquals(2, adjList.size());
		assertTrue(adjList.contains(theBoard.getCell(24, 7))); 
		assertTrue(adjList.contains(theBoard.getCell(24, 9)));
	}
//	Locations that are doorways
	//@Test
	public void testDoorwayAdjacency() {
		//test spot (7,5)
		Set<BoardCell> adjList = theBoard.getAdjList(7, 5); 
		assertEquals(4, adjList.size()); 
		assertTrue(adjList.contains(theBoard.getCell(10, 2)));
		assertTrue(adjList.contains(theBoard.getCell(6, 5))); 
 		assertTrue(adjList.contains(theBoard.getCell(8, 5))); 
 		assertTrue(adjList.contains(theBoard.getCell(7, 6))); 
	}
//	Locations that are connected by secret passage	
	//@Test
	public void testSecretPassage() {
		//test spot (21, 23)
		Set<BoardCell> adjList = theBoard.getAdjList(21, 23); 
		assertEquals(2, adjList.size());
		assertTrue(adjList.contains(theBoard.getCell(20, 22)));
		assertTrue(adjList.contains(theBoard.getCell(3, 8)));
	}
	
/*
 * TARGET TESTS
 */
	
//	Targets along walkways, at various distances
	//@Test
	public void testWalkwayTargets(){
		
	}
	
//	Targets that allow the user to enter a room
	//@Test
	public void testEnterTargets() {
		
	}
	
//	Targets calculated when leaving a room without secret passage
	//@Test
	public void testLeavingNoPassage() {
		
	}
	
//	Targets calculated when leaving a room with secret passage
	//@Test
	public void testLeavingPassage() {
		
	}
	
//	Targets that reflect blocking by other players
	//@Test
	public void testPlayerBlock() {
		
	}
}
