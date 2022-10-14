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
	private boolean isRoom;
	private boolean isOccupied;
	private char secretPassage;
	private Set<BoardCell> adjList;
	
	public BoardCell(int row, int col, char initial) {
		this.row = row;
		this.col = col;
		this.initial = initial;
		isOccupied = false;
		roomLabel = false;
		roomCenter = false;
		doorway = false;
	}
	
	
	public void setDoorDirection(DoorDirection d) {
		this.doorDirection = d;
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
	
	public Set<BoardCell> getAdjList(){
		return this.adjList;
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

	public int getCol() {
		return this.col;
	}
	
	public int getRow() {
		return this.row;
	}


	public boolean getIsRoom() {
		// TODO Auto-generated method stub
		return this.isRoom;
	}


	public void setOccupied(boolean occupied) {
		this.isOccupied = occupied;	
	}


	public boolean getOccupied() {
		// TODO Auto-generated method stub
		return this.isOccupied;
	}
}
