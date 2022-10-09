package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

class BoardTestsExp {
	//Board attribute to test behavior with 
	TestBoard board; 
	
	@BeforeEach 
	//Before each tests runs, creates new board object with will create a new adjacency lust
	public void createBoard() {
		//creates new board and then populates it with cells 
		board = new TestBoard(); 
		board.populateGrid();
	}
	
	
	/*
	 * ADJACENCY TESTS
	 */
	
	//@Test
	public void adjacentTopLeft() {
		TestBoardCell cell = board.getCell(0,0);
		board.calcAdjacencies(cell);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(0,1)));
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
	}
	
	//@Test
	public void adjacentBottomRight() {
		TestBoardCell cell = board.getCell(3,3);
		board.calcAdjacencies(cell);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertTrue(testList.contains(board.getCell(3,2)));
	}
	
	//@Test
	public void adjacentRightEdge() {
		TestBoardCell cell = board.getCell(1,3);
		board.calcAdjacencies(cell);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(0,3)));
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertTrue(testList.contains(board.getCell(1,2)));
	}
	
	//@Test
	public void adjacentLeftEdge() {
		TestBoardCell cell = board.getCell(2,0);
		board.calcAdjacencies(cell);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertTrue(testList.contains(board.getCell(3,0)));
		Assert.assertTrue(testList.contains(board.getCell(2,1)));
	}
	
	//@Test
	public void adjacentCenter() {
		TestBoardCell cell = board.getCell(2,2);
		board.calcAdjacencies(cell);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertEquals(4, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(2,1)));
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertTrue(testList.contains(board.getCell(1,2)));
		Assert.assertTrue(testList.contains(board.getCell(3,2)));
	}

	
	/*
	 * CALC TARGET
	 */
	
	
	//test empty board, roll 3
	@Test
	public void testRoll3EmptyBoard() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcAdjacencies(cell);
		//we know this works ^^
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		System.out.println("TARGETS SIZE  " + targets.size());
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	//@Test
	//test empty board, roll 6
	public void testRoll6EmptyBoard() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	
	//test occupied, roll 3
	//@Test
	public void testRoll3OccupiedBoard() {
		board.getCell(0, 1).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	//test occupied, roll 4
	//@Test 
	public void testRoll4OccupiedBoard() {
		board.getCell(0, 1).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
	}
	
	//Test roll of 4 with occupied space starting from (3,3)
	//@Test 
	public void testRoll4OccupiedBoardStartingBottomRightCorner() {
		board.getCell(0, 1).setOccupied(true);
		TestBoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
	}
	
	//test room, roll 4
	//@Test
	public void testRoll4Room() {
		TestBoardCell cell = board.getCell(0, 0);
		board.getCell(0, 1).setIsRoom(true);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
	}
	
	//test room, occupied spot that is reachable from roll, roll 4
	//@Test
	public void testRoll4RoomAndReachableOccupiedSpace() {
		TestBoardCell cell = board.getCell(0, 0);
		board.getCell(0, 1).setIsRoom(true);
		board.getCell(2, 0).setOccupied(true);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertFalse(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
	}
	
	
}
