package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import experiment.TestBoardCell;

class BoardTestsExp {
	//Board attribute to test behavior with 
	TestBoard board; 
	
	@BeforeEach 
	//Before each tests runs, creates new board object with will create a new adjacency lust
	public void creatBoard() {
		board = new TestBoard(); 
	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
