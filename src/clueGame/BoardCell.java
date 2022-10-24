package clueGame;

import java.util.*;

public class BoardCell{
	private int row;
	private int col;
	private char character;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean doorway;
	private boolean isRoom;
	private boolean isOccupied;
	private boolean hasSecretPassage;
	private char secretPassage;
	private Set<BoardCell> adjList;
	
	public BoardCell(int row, int col, char initial) {
		this.row = row;
		this.col = col;
		this.character = initial;
		this.isOccupied = false;
		this.roomLabel = false;
		this.roomCenter = false;
		this.doorway = false;
		this.isRoom = false; 
		this.hasSecretPassage = false; 
		this.doorDirection = null; 
		adjList = new HashSet<BoardCell>(); 
	}
	
	//door getters and setters 
	public void setDoorDirection(DoorDirection d) {
		this.doorDirection = d;
	}
	
	public DoorDirection getDoorDirection() {
		return this.doorDirection;
	}
	
	public boolean isDoorway() {
		return doorway;
	}
	
	public void setDoorway(boolean doorway) {
		this.doorway = doorway;
	}
	
	//label getters and setters 
	public boolean isLabel() {
		return roomLabel;
	}
	
	public void setRoomLabel(boolean label) {
		this.roomLabel = label;
	}
	
	//room center getters and setters 
	public boolean isRoomCenter() {
		return roomCenter;
	}
	
	public void setRoomCenter(boolean center) {
		this.roomCenter = center;
	}
	
	//secret passage getters and setters 
	public boolean hasSecretPassage() {
		return this.hasSecretPassage;
	}
	
	public char getSecretPassage() {
		return this.secretPassage;
	}
	
	public void setSecretPassage(char passage) {
		this.hasSecretPassage = true;
		this.secretPassage = passage;
	}
	
	//character getter 
	public char getCharacter() {
		return this.character;
	}
	
	//adj cell getters and setters 
	public void addAdj(BoardCell adj) {
		adjList.add(adj); 
	}
	
	public Set<BoardCell> getAdjList(){
		return this.adjList;
	}

	//row and col getters 
	public int getCol() {
		return this.col;
	}
	
	public int getRow() {
		return this.row;
	}
	
	//room getters and setters 
	public void setIsRoom(boolean room) {
		this.isRoom = room; 
	}

	public boolean getIsRoom() {
		return this.isRoom;
	}

	//occupied getters and setters 
	public void setOccupied(boolean occupied) {
		this.isOccupied = occupied;	
	}


	public boolean getOccupied() {
		return this.isOccupied;
	}
}
