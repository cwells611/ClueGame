package experiment;

import java.util.Set;

public class TestBoard {
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> board; 
	
	public TestBoard() {
		super();
		//constructor build board and adds each cell to the board 
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
