package experiment;

import java.util.Set;

public class TestBoard {

	private Set<TestBoardCell> targets;
	public TestBoard() {
		super();
	}
	
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		targets = new Set<TestBoardCell>();
	}
	
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return TestBoardCell(row, col);
	}
}
