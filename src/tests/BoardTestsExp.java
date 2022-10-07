package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import experiment.TestBoardCell;

class BoardTestsExp {
	//Board attribute to test behavior with 
	TestBoard board; 
	
	@BeforeEach 
	//Before each tests runs, creates new board object with will create a new adjacency lust
	public void createBoard() {
		board = new TestBoard(); 
	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	public void adjacentTopLeft() {
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0,1)));
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertEquals(2, testList.size());
	}
	
	public void adjacentBottomRight() {
		TestBoardCell cell = board.getCell(3,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertTrue(testList.contains(board.getCell(3,2)));
		Assert.assertEquals(2, testList.size());
	}
	
	public void adjacentRightEdge() {
		TestBoardCell cell = board.getCell(1,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0,3)));
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertTrue(testList.contains(board.getCell(1,2)));
		Assert.assertEquals(3, testList.size());
	}
	
	public void adjacentLeftEdge() {
		TestBoardCell cell = board.getCell(2,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertTrue(testList.contains(board.getCell(3,0)));
		Assert.assertTrue(testList.contains(board.getCell(2,1)));
		Assert.assertEquals(3, testList.size());
	}
	
	public void adjacentCenter() {
		TestBoardCell cell = board.getCell(2,2);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2,1)));
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertTrue(testList.contains(board.getCell(1,2)));
		Assert.assertTrue(testList.contains(board.getCell(3,2)));
		Assert.assertEquals(3, testList.size());
	}

}
