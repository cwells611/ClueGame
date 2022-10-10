package clueGame;

import java.util.*;

public class BoardCell{
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	
	public void addAdj(BoardCell adj) {
		
	}
	
	public BoardCell getCell(int row, int col) {
		return this;
	}
	
	public boolean isDoorway() {
		//TODO change this to not be just false
		return false;
	}
	
	public DoorDirection getDoorDirection() {
		return this.doorDirection;
	}
	
	public boolean isLabel() {
		return roomLabel;
	}
	
	public boolean isRoomCenter() {
		return roomCenter;
	}
	
	public char getSecretPassage() {
		return this.secretPassage;
	}
	
}
