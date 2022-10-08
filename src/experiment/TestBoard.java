package experiment;

import java.util.*;

public class TestBoard {
	private Set<TestBoardCell> targets;
	private TestBoardCell grid[][];
	private Set<TestBoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	
	public TestBoard() {
		super();
		//constructor build board and adds each cell to the board 
		this.targets = new TreeSet<TestBoardCell>();
		//this.board = new TreeSet<TestBoardCell>();
	}
	
	//method that will determine the possible targets from a certain roll
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		
	}
	
	//returns list of possible targets
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	//returns specific cell within board
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell returnCell = new TestBoardCell(row, col); 
		return returnCell;
	}
	
}