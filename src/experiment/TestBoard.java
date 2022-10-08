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
	
	public void calcAdjacencies(TestBoardCell cell) {
		//testing left edge
		if(cell.getCol() != 0) {
			TestBoardCell leftCell = new TestBoardCell(cell.getRow(), cell.getCol()-1);
			cell.addAdjacency(leftCell);
		}
		//testing right edge
		if(cell.getCol() != COLS-1) {
			TestBoardCell rightCell = new TestBoardCell(cell.getRow(), cell.getCol()+1);
			cell.addAdjacency(rightCell);
		}
		//testing top edge
		if(cell.getRow() != 0) {
			TestBoardCell upperCell = new TestBoardCell(cell.getRow()-1, cell.getCol());
			cell.addAdjacency(upperCell);
		}
		//testing top edge
		if(cell.getRow() != ROWS-1) {
			TestBoardCell lowerCell = new TestBoardCell(cell.getRow()+1, cell.getCol());
			cell.addAdjacency(lowerCell);
		}
	}
	
}