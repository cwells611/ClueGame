package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, String color, int startRow, int startCol, String type) {
		super(name, color, startRow, startCol, type);
	}
	
	//even though we are creating a suggestion, since the solution class already utilizes creation 
	//of a room, weapon, and person card, we will use this class for our suggestion
	public Solution createSuggestion(Board board, Room currentRoom) {
		//creates new solution object for suggestion 
		Solution suggestion = new Solution(); 
		//calls createSoltion that will randomly create suggestion based on deck 
		suggestion.createSolution(board.getDeck());
		//gets the card in the deck associated with the room 
		for(Card card : board.getDeck()) {
			if(card.getName().equals(currentRoom.getName())) {
				//sets the room in the randomly generated suggestion to be the current room 
				suggestion.setRoom(card); 
			}
		}
		return suggestion; 
	}
	
	//the computer player needs to be able to select its next target, so we will return that target given 
	//the set of boardcells that are available targets 
	public BoardCell selectTarget(Set<BoardCell> targets) {
		return null;
	}


}
