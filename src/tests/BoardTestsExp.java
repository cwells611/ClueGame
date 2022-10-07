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

}
