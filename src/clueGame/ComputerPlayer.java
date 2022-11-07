package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, String color, int startRow, int startCol, String type) {
		super(name, color, startRow, startCol, type);
	}
	
	//even though we are creating a suggestion, since the solution class already utilizes creation 
	//of a room, weapon, and person card, we will use this class for our suggestion
	public Solution createSuggestion(Room currentRoom) {
		
		return null; 
	}
	
	//the computer player needs to be able to select its next target, so we will return that target given 
	//the set of boardcells that are available targets 
	public BoardCell selectTarget(Set<BoardCell> targets) {
		return null;
	}

}
