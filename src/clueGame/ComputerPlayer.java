package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	//private ArrayList<Card> seen = new ArrayList<Card>(); 
	private ArrayList<Card> notSeen = new ArrayList<Card>(); 
	private int numWeaponsSeen = 0; 
	private int numPeopleSeen = 0; 
	private int numRoomsSeen = 0; 

	public ComputerPlayer(String name, String color, int startRow, int startCol) {
		super(name, color, startRow, startCol);
	}

	//even though we are creating a suggestion, since the solution class already utilizes creation 
	//of a room, weapon, and person card, we will use this class for our suggestion
	@Override
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
		for(Card card : super.getSeenCards()) {
			//counts the number of weapons seen 
			if(card.getType() == CardType.WEAPON) {
				numWeaponsSeen++; 
			}
			//count number of people seen 
			if(card.getType() == CardType.PERSON) {
				numPeopleSeen++; 
			}
		}
		//determine which cards the computer has and has not seen
		//loop through the deck 
		for(Card card : board.getDeck()) {
			//loop through seen list 
			for(Card seenCard : super.getSeenCards()) {
				//if we have seen the card and the card is a weapon or a person then continue then we want to remove it from the seen list
				//to show that we are already compared it with a card in the deck 
				if((seenCard.equals(card) && card.getType() == CardType.WEAPON) || (seenCard.equals(card) && card.getType() == CardType.PERSON)) {
					super.getSeenCards().remove(seenCard); 
					break; 
				}
				//if we have not seen the card and it's a weapon or a person, then add it to the unseen list 
				else if((!seenCard.equals(card) && card.getType() == CardType.WEAPON) || (!seenCard.equals(card) && card.getType() == CardType.PERSON)) {
					notSeen.add(card); 
					break; 
				}	
				else {
					break; 
				}
			}
		}
		//after we know how many weapons and people we have seen, if either is 5, then we find out the ones we have not seen and 
		//make that the weapon or person in the suggestion 
		if(numWeaponsSeen == 5 || numPeopleSeen == 5) {
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
		//if numWeaponsSeen is not 5 and not 0 because we need to make sure we have see at least one weapon, 
		//then we need to randomly pick from the unseen weapons for the suggestion 
		if(numWeaponsSeen != 5 && numWeaponsSeen != 0) {
			//determines how many weapons we have not seen 
			int numWeaponsNotSeen = 6 - numWeaponsSeen; 
			//creates random object 
			Random random = new Random(); 
			//since the weapons are second in the list, then the list length - 1 will be 
			//the index of the last weapon in the list 
			int randomWeaponIndex = random.nextInt(notSeen.size() - numWeaponsNotSeen) + numWeaponsNotSeen;
			//sets the weapon based on the random index
			suggestion.setWeapon(notSeen.get(randomWeaponIndex));
		}

		//if numPeopleSeen is not 5 and not 0 because we need to make sure we have see at least one person, 
		//then we need to randomly pick from the unseen weapons for the suggestion 
		if(numPeopleSeen != 5 && numPeopleSeen != 0) {
			//determines how many people we have not see
			int numPeopleNotSeen = 6 - numPeopleSeen; 
			//creates random object 
			Random random = new Random(); 
			//since the people are in the first part of the list, we want a random index between 
			//the beginning of the list and the number of people not seen
			int randomPersonIndex = random.nextInt(numPeopleNotSeen); 
			//sets the person based on the random index
			suggestion.setPerson(notSeen.get(randomPersonIndex));
		}
		GameControlPanel.getGCPanel().setGuess("I suggest that " + suggestion.getPerson() + " used a " + suggestion.getWeapon() + " in the " + suggestion.getRoom());
		return suggestion; 
	}

	//the computer player needs to be able to select its next target, so we will return that target given 
	//the set of boardcells that are available targets 
	@Override
	public BoardCell selectTarget(Set<BoardCell> targets, Board board) {
		//loop through the target list 
		for(BoardCell target : targets) {
			//if target is a room and the computer has not seen that room, then return that cell as the selected target of the player
			if(target.getIsRoom()) {
				//loop through the deck in order to find the card that corresponds to the room that is a potential target
				for(Card card : board.getDeck()) {
					if(card.getName().equals(board.getRoom(target).getName())) {
						Card roomCard = card; 
						//if computer has not seen roomCard, then return target 
						if(!super.getSeenCards().contains(roomCard)) {
							return target; 
						}
					}
				}
			}
		}

		if(targets.size() != 0) {
			//if we loop through the whole target list, and there are no rooms, then pick a random cell from target list 
			Random random = new Random(); 
			//generates a random number between 0 and the last element of the set
			int randomTargetCell = random.nextInt(targets.size() - 1); 
			int counter = 0; 
			//loop through set 
			for(BoardCell target : targets) {
				//if the counter is equal to the random number that was generated, return target 
				if(counter == randomTargetCell) {
					return target;
				}
				counter++; 
			}
		}
		return null; 
	}

	@Override
	public Solution doAccusation() {
		Solution accusation = new Solution(); 
		if(numRoomsSeen == 8 && numPeopleSeen == 5 && numWeaponsSeen == 5) {
			for(Card notSeenCard : notSeen) {
				switch(notSeenCard.getType()) {
				case ROOM:
					accusation.setRoom(notSeenCard);
					break; 
				case WEAPON:
					accusation.setWeapon(notSeenCard);
					break; 
				case PERSON:
					accusation.setPerson(notSeenCard);
					break; 
				}
			}
		}
		return accusation; 
	}

	private void makeSuggestion() {
		GameControlPanel.getGCPanel().setGuess("I suggest that Edna used a car in the Atrium");
	}

}
