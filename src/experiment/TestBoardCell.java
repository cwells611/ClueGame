package experiment;

import java.util.*;

public class TestBoardCell {
	//attributes that define the row and column of the current board cell
	private int row;
	private int col;
	//set to contain this cells adjacency list 
	private Set<TestBoardCell> adjList;
	//boolean that determines says if a cell is occupied by another player
	private boolean occupiedSpace;
	//boolean to determine is there is a room next to the player 
	private boolean isRoom; 
	
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		this.occupiedSpace = false;
		this.isRoom = false; 
		this.adjList = new TreeSet<TestBoardCell>();
	}
	
	//setter setter to add a cell to this cells adjacency list
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	//getter that returns adjacency list for current sell
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	//setter to indicate if a cell is occupied by another player 
	public void setOccupied(boolean occupied) {
		
	}
	
	//getter that returns boolean based on if a cell is occupied by another player 
	public boolean getOccupied() {
		return this.occupiedSpace;
	}
	
	//setter to indicate if there is a room next to the player
	public void setIsRoom (boolean isRoom) {
			
	}
	//getter for the row of a cell
	public int getRow() {
		return this.row;
	}
	
	//getter for the column of a cell
	public int getCol() {
		return this.col;
	}
}
