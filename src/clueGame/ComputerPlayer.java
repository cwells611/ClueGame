package clueGame;

import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player{
	private ArrayList<Card> seen = new ArrayList<Card>(); 
	private ArrayList<Card> notSeen = new ArrayList<Card>(); 
	private int numWeaponsSeen = 0; 
	private int numPeopleSeen = 0; 

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
				break; 
			}
		}
		//loops through seen list 
		for(Card card : seen) {
			//counts the number of weapons seen 
			if(card.getType() == CardType.WEAPON) {
				numWeaponsSeen++; 
			}
			//count number of people seen 
			if(card.getType() == CardType.PERSON) {
				numPeopleSeen++; 
			}
		}
		//after we know how many weapons and people we have seen, if either is 5, then we find out the ones we have not seen and 
		//make that the weapon or person in the suggestion 
		if(numWeaponsSeen == 5 || numPeopleSeen == 5) {
			//loop through the deck 
			for(Card card : board.getDeck()) {
				//loop through seen list 
				for(Card seenCard : seen) {
					//if we have seen the card and the card is a weapon or a person then continue then we want to remove it from the seen list
					//to show that we are already compared it with a card in the deck 
					if((seenCard.equals(card) && card.getType() == CardType.WEAPON) || (seenCard.equals(card) && card.getType() == CardType.PERSON)) {
						seen.remove(seenCard); 
						break; 
					}
					//if we have not seen the card and it's a weapon or a person, then add it to the unseen list 
					if((!seenCard.equals(card) && card.getType() == CardType.WEAPON) || (!seenCard.equals(card) && card.getType() == CardType.PERSON)) {
						notSeen.add(card); 
						break; 
					}	
					else {
						break; 
					}
				}
			}
			//after everything that been looped through, since we know that only 1 weapon or 1 person should be 
			//in the unSeen list, then we loop through the unseen list and if that card type is a 
			//weapon then we add it to the suggestion 
			for(Card unSeen : notSeen) {
				if(unSeen.getType() == CardType.WEAPON) {
					suggestion.setWeapon(unSeen);
				}
				if(unSeen.getType() == CardType.PERSON) {
					suggestion.setPerson(unSeen);
				}
			}
		}
		return suggestion; 
	}
	
	//the computer player needs to be able to select its next target, so we will return that target given 
	//the set of boardcells that are available targets 
	public BoardCell selectTarget(Set<BoardCell> targets) {
		return null;
	}
	
	//method to add card to players seen list 
	public void addSeenCard(Card seenCard) {
		seen.add(seenCard); 
	}


}
