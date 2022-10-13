package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	
	public Room(String name) {
		this.name = name; 
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
	
}