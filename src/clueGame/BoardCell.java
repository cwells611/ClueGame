package clueGame;

import java.util.*;

public class BoardCell{
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean doorway;
	private char secretPassage;
	private Set<BoardCell> adjList;
	
	public BoardCell(int row, int col, char initial) {
		this.row = row;
		this.col = col;
		this.initial = initial;
		roomLabel = false;
		roomCenter = false;
		doorway = false;
	}
	
	
	public void addAdj(BoardCell adj) {
		adjList.add(adj); 
	}
	
	public boolean isDoorway() {
		//TODO change this to not be just false
		return doorway;
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
	
	public char getCharacter() {
		return this.initial;
	}
	
	public void setRoomLabel(boolean label) {
		this.roomLabel = label;
	}
	
	public void setRoomCenter(boolean center) {
		this.roomCenter = center;
	}
	
	public void setDoorway(boolean doorway) {
		this.doorway = doorway;
	}
	
	public void setSecretPassage(char passage) {
		this.secretPassage = passage;
	}
}
