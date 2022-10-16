package clueGame;

public class Room extends BoardCell{
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private boolean hasSecretPassage;
	private char secretPassage;
	
	
	public Room(int row, int col, char initial, String name) {
		super(row, col, initial);
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
	
	public void setSecretPassage(char passage) {
		this.secretPassage = passage;
	}
	
	public boolean hasSecretPassage() {
		return this.hasSecretPassage;
	}
	
	public char getSecretPassage() {
		return this.secretPassage;
	}
	
}