package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;

class PlayerAndCardTests {
	//constant variables to hold board size 
	public static final int ROWS = 25; 
	public static final int COLS = 25; 
	private static Board theBoard;

	@BeforeAll
	public static void setUp() {
		//since we are only using one instance of this board 
		theBoard = Board.getInstance(); 
		//has the board read in the config files and setup board based on files 
		theBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		//loads both files even though we are only using one instance of board 
		theBoard.initialize();
	}
	
	@Test
	void testNumHumanPlayers() {
		assertEquals(1, theBoard.getNumHumanPlayers()); 
	}
	
	@Test 
	void testNumComputerPlayers() {
		assertEquals(5, theBoard.getNumComputerPlayers()); 
	}

}
