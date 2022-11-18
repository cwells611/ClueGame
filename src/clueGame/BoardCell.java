package clueGame;

import java.awt.Color;
import java.awt.Graphics;
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
	private int doorXOffset;
	private int doorYOffset;

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

	//door offset getters
	public int getDoorYOffset() {
		return doorYOffset;
	}

	public int getDoorXOffset() {
		return doorXOffset;
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

	//row and col getters and setters
	public int getCol() {
		return this.col;
	}
	public void setCol(int newCol) {
		this.col = newCol; 
	}

	public int getRow() {
		return this.row;
	}
	public void setRow(int newRow) {
		this.row = newRow; 
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

	//method to allow each board cell to draw itself 
	//parameters will be the graphics object to be able to call paint method, 
	//the width of the cell, the height of the cell, and the offset 
	public void draw(Graphics g, int width, int height, int xOffset, int yOffset) {
		//with all the info the board cell needs passed in as parameters, we can just call 
		//drawRect to draw the rectangle at the coordinates (xOffset, yOffset) with a width 
		//of width and a height of height 

		//check for walkways and unused spaces 
		if(this.character ==  'W') {
			g.setColor(Color.yellow);
			g.fillRect(xOffset, yOffset, width, height);
			g.setColor(Color.black);
			g.drawRect(xOffset, yOffset, width, height);
		}
		if(this.character ==  'X') {
			g.setColor(Color.black);
			g.fillRect(xOffset, yOffset, width, height);
		}

		//check if its a room, have no lines between cells 
		if(this.isRoom) {
			g.setColor(Color.gray);
			g.fillRect(xOffset, yOffset, width, height);
		}
	}
}
