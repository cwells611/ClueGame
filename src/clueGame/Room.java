package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Room {
	private String name;
	private char label; 
	private BoardCell centerCell;
	private BoardCell labelCell;
	private BoardCell secretPassageConnection;
	private boolean hasSecretPassage; 
	private Set<BoardCell> doors = new HashSet<BoardCell>();
	
	public Room(String name, char label) {
		this.name = name; 
		this.label = label;
	}
	
	public void addDoor(BoardCell door) {
		doors.add(door);
	}
	
	public Set<BoardCell> getDoors(){
		return this.doors;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setLabelCell(BoardCell cell) {
		this.labelCell = cell;
	}
	
	public void setCenterCell(BoardCell cell) {
		this.centerCell = cell;
	}
	
	public BoardCell getLabelCell() {
		return this.labelCell;
	}
	
	public BoardCell getCenterCell() {
		return this.centerCell;
	}
	public boolean hasSecretPassage() {
		return this.hasSecretPassage; 
	}
	public void setSecretPassage(BoardCell centerOfConnectingRoom) {
		this.hasSecretPassage = true; 
		this.secretPassageConnection = centerOfConnectingRoom; 
	}
	public BoardCell getCenterOfConnectingRoom() {
		return this.secretPassageConnection; 
	}
	
}