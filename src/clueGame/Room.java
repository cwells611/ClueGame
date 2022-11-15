package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

public class Room {
	private String name;
	private char label; 
	private BoardCell centerCell;
	private BoardCell labelCell;
	private BoardCell secretPassageConnection;
	private boolean hasSecretPassage; 
	private char secretPassage; 
	private Set<BoardCell> doors;
	private Set<BoardCell> roomCells; 
	private CardType cardType; 

	public Room(String name, char label) {
		this.name = name; 
		this.label = label;
		roomCells = new HashSet<BoardCell>(); 
		doors = new HashSet<BoardCell>();
	}

	//door getters and setters
	public void addDoor(BoardCell door) {
		doors.add(door);
	}

	public Set<BoardCell> getDoors(){
		return this.doors;
	}

	//name getter 
	public String getName() {
		return this.name;
	}

	//center cell getters and setters
	public void setCenterCell(BoardCell cell) {
		this.centerCell = cell;
	}
	public BoardCell getCenterCell() {
		return this.centerCell;
	}

	//label cell getters and setters
	public BoardCell getLabelCell() {
		return this.labelCell;
	}

	public void setLabelCell(BoardCell cell) {
		this.labelCell = cell;
	}

	//secret passage getters and setters 
	public boolean hasSecretPassage() {
		return this.hasSecretPassage; 
	}

	public void setSecretPassage(BoardCell centerOfConnectingRoom, char passage) {
		this.hasSecretPassage = true; 
		this.secretPassage = passage; 
		this.secretPassageConnection = centerOfConnectingRoom; 
	}

	public char getSecretPassage() {
		return this.secretPassage; 
	}

	public BoardCell getCenterOfConnectingRoom() {
		return this.secretPassageConnection; 
	}

	//room cell getters and setters 
	public void addRoomCell(BoardCell cell) {
		roomCells.add(cell); 
	}

	public Set<BoardCell> getRoomCells() {
		return this.roomCells; 
	}

	//CardType setters and getters
	public void setCardType(CardType card) {
		this.cardType = card; 
	}
	public CardType getCardType() {
		return this.cardType;
	}

	public void draw(Graphics g, int row, int col) {
		String label = this.name; 
		g.setColor(Color.blue);
		g.drawString(label, row, col);
	}
}