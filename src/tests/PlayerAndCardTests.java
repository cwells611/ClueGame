package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
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
		assertEquals(1, theBoard.getNumHumanPlayers()); 
	}
	
	@Test 
	void testNumComputerPlayers() {
		assertEquals(5, theBoard.getNumComputerPlayers()); 
	}
	
	@Test 
	void testNumWeaponsAndWeapons() {
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
		ArrayList<Card> deck = theBoard.getDeck(); 
		assertEquals(21, deck.size()); 
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
		Solution solution = new Solution(deck); 
		assertEquals(CardType.PERSON, solution.getSolution().get(0).getType()); 
		assertEquals(CardType.WEAPON, solution.getSolution().get(1).getType()); 
		assertEquals(CardType.ROOM, solution.getSolution().get(2).getType()); 
	}
	

}
