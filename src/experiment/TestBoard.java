package experiment;

import java.util.*;

public class TestBoard {
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> board; 
	
	public TestBoard() {
		super();
		//constructor build board and adds each cell to the board 
		this.targets = new TreeSet<TestBoardCell>();
		this.board = new TreeSet<TestBoardCell>();
		
		
	}
	
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		
	}
	
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell returnCell = new TestBoardCell(row, col); 
		return returnCell;
	}
}
