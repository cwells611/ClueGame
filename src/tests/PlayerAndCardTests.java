package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;

class PlayerAndCardTests {
	//constant variables to hold board size 
	public static final int ROWS = 25; 
	public static final int COLS = 25; 
	private static Board theBoard;

	@BeforeAll
	public static void setUp() {
		//since we are only using one instance of this board 
		theBoard = Board.getInstance(); 
		//has the board read in the config files and setup board based on files 
		theBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		//loads both files even though we are only using one instance of board 
		theBoard.initialize();
	}
	
	@Test
	void testNumHumanPlayers() {
		ArrayList<Player> players = theBoard.getPlayers(); 
		//we want to make sure that only 1 human player is created 
		assertEquals(1, theBoard.getNumHumanPlayers()); 
		//also want to make sure that the correct person is assigned as the human player 
		//loop through list of players and if the player type is human, make sure the name associated is the human 
		///player is Nigel Thomas 
		int humanIndex = 0; 
		for(Player player : players) {
			if(player.getPlayerType().equals("Human")) {
				humanIndex = players.indexOf(player);  
			}
		}
		assertEquals("Nigel Thomas", players.get(humanIndex).getPlayerName()); 
	}
	
	@Test 
	void testNumComputerPlayers() {
		ArrayList<Player> players = theBoard.getPlayers(); 
		//we want to test that 5 computer players are created 
		assertEquals(5, theBoard.getNumComputerPlayers()); 
		//also want to make sure that the correct person is assigned as the human player 
		//loop through list of players and if the player type is computer, make sure the names associated with the computers 
		//are correct 
		/*
		 * int computer1 = 0; int computer2 = 0; int computer3 = 0; int computer4 = 0;
		 * int computer5 = 0;
		 */
		
	}
	
	@Test 
	void testNumWeaponsAndWeapons() {
		//We will have 6 different weapins 
		ArrayList<String> weapons = theBoard.getWeapons(); 
		assertEquals(6, theBoard.getNumWeapons()); 
		assertEquals("Frying Pan", weapons.get(0)); 
		assertEquals("Switch Blade", weapons.get(1)); 
		assertEquals("Musket", weapons.get(2));
		assertEquals("Bow and Arrow", weapons.get(3));
		assertEquals("Boulder", weapons.get(4));
		assertEquals("Car", weapons.get(5)); 
	}
	
	@Test
	void testNumCards() {
		//our deck of cards will consists of 9 room, 6 players, and 6 weapons, so we want to make sure that the deck size is 
		//21 to show all cards were loaded in 
		ArrayList<Card> deck = theBoard.getDeck(); 
		assertEquals(21, deck.size()); 
		//since out ClueSetup.txt is written such that the laodSetup method reads in the room, then the people, then the weapons
		//so the cards are added into the deck in that order so we want to test to make sure that all the card types are properly loaded in
		assertEquals(CardType.ROOM, deck.get(0).getType()); 
		assertEquals(CardType.ROOM, deck.get(1).getType()); 
		assertEquals(CardType.ROOM, deck.get(2).getType()); 
		assertEquals(CardType.ROOM, deck.get(3).getType()); 
		assertEquals(CardType.ROOM, deck.get(4).getType()); 
		assertEquals(CardType.ROOM, deck.get(5).getType()); 
		assertEquals(CardType.ROOM, deck.get(6).getType()); 
		assertEquals(CardType.ROOM, deck.get(7).getType()); 
		assertEquals(CardType.ROOM, deck.get(8).getType()); 
		assertEquals(CardType.PERSON, deck.get(9).getType()); 
		assertEquals(CardType.PERSON, deck.get(10).getType()); 
		assertEquals(CardType.PERSON, deck.get(11).getType()); 
		assertEquals(CardType.PERSON, deck.get(12).getType()); 
		assertEquals(CardType.PERSON, deck.get(13).getType()); 
		assertEquals(CardType.PERSON, deck.get(14).getType()); 
		assertEquals(CardType.WEAPON, deck.get(15).getType()); 
		assertEquals(CardType.WEAPON, deck.get(16).getType()); 
		assertEquals(CardType.WEAPON, deck.get(17).getType()); 
		assertEquals(CardType.WEAPON, deck.get(18).getType()); 
		assertEquals(CardType.WEAPON, deck.get(19).getType()); 
		assertEquals(CardType.WEAPON, deck.get(20).getType()); 
	}
	
	@Test 
	public void testSolutionCards() {
		//we want to make sure that the ArrayList that contains the solution cards has a Person, a Weapon, and a Room
		ArrayList<Card> deck = theBoard.getDeck(); 
		Solution solution = new Solution();
		solution.createSolution(deck);
		assertEquals(CardType.PERSON, solution.getSolution().get(0).getType()); 
		assertEquals(CardType.WEAPON, solution.getSolution().get(1).getType()); 
		assertEquals(CardType.ROOM, solution.getSolution().get(2).getType()); 
	}
	
	@Test
	public void testRemoveSolution() {
		ArrayList<Card> deck = theBoard.getDeck();
		Solution solution = new Solution();
		solution.createSolution(deck); 
		Card solutionRoom = solution.getRoom();
		Card solutionPerson = solution.getPerson();
		Card solutionWeapon = solution.getWeapon();
		deck = theBoard.removeSolutionCards(solution, deck);
		assertEquals(18, deck.size());
		for(Card card : deck) {
			//testing each card in the deck by type, and checking that the solution card is not in the deck
			if(card.getType() == CardType.PERSON) {
				assertNotEquals(card.getName(), solutionPerson.getName());
			}
			if(card.getType() == CardType.ROOM) {
				assertNotEquals(card.getName(), solutionRoom.getName());
			}
			if(card.getType() == CardType.WEAPON) {
				assertNotEquals(card.getName(), solutionWeapon.getName());
			}
		}
	}
}
