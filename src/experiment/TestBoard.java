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
		//initializes grid to a board of size 4x4 for testing
		this.grid = new TestBoardCell[4][4]; 
	}
	
	//method to populate grid with cells 
	public void populateGrid() {
		for(int col = 0; col < 4; col++) {
			for(int row = 0; row < 4; row++) {
				TestBoardCell cell = new TestBoardCell(row, col); 
				grid[row][col] = cell; 
			}
		}
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
		//returns cell at the specified row and col within grid 
		return grid[row][col]; 
	}
	
	public void calcAdjacencies(TestBoardCell cell) {
		System.out.println(cell.getCol());
		System.out.println(cell.getRow());
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
	
	public static void main(String[] args) {
		TestBoard board = new TestBoard();
		board.populateGrid();
		TestBoardCell cell = board.getCell(0,0);
		board.calcAdjacencies(cell);
		System.out.println(cell.getAdjList());
	}
	
}