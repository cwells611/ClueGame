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
		board = new TestBoard(); 
	}
	
	
	/*
	 * ADJACENCY TESTS
	 */
	
	@Test
	public void adjacentTopLeft() {
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(0,1)));
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
	}
	
	@Test
	public void adjacentBottomRight() {
		TestBoardCell cell = board.getCell(3,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertTrue(testList.contains(board.getCell(3,2)));
	}
	
	@Test
	public void adjacentRightEdge() {
		TestBoardCell cell = board.getCell(1,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(0,3)));
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertTrue(testList.contains(board.getCell(1,2)));
	}
	
	@Test
	public void adjacentLeftEdge() {
		TestBoardCell cell = board.getCell(2,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertTrue(testList.contains(board.getCell(3,0)));
		Assert.assertTrue(testList.contains(board.getCell(2,1)));
	}
	
	@Test
	public void adjacentCenter() {
		TestBoardCell cell = board.getCell(2,2);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertEquals(3, testList.size());
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
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	@Test
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
	@Test
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
	@Test 
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
	
	//test room, roll 3
	//test room, roll 4
	
}
