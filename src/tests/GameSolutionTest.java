package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSolutionTest {
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
	}
	
	//necessary to initialize before each, as deck size must be different between tests
	@BeforeEach
	public void initialize() {
		theBoard.initialize();
	}
	//Accusation tests
	@Test
	void checkCorrectAccusation() {
		//creating a solution, and dealing out the deck
		ArrayList<Card> deck = theBoard.getDeck();
		Solution solution = new Solution();
		solution.createSolution(deck);
		theBoard.removeSolutionCards(solution);
		theBoard.deal();
		//creating variables for the correct cards
		Card correctRoom = solution.getRoom();
		Card correctPerson = solution.getPerson();
		Card correctWeapon = solution.getWeapon();
		//creating the correct accusation
		Solution accusation = new Solution();
		accusation.setPerson(correctPerson);
		accusation.setRoom(correctRoom);
		accusation.setWeapon(correctWeapon);
		//checking the accusation matches the solution
		assertTrue(theBoard.checkAccusation(accusation));
	}
	
	@Test
	void checkAccusationWrongRoom() {
		//creating a solution
		ArrayList<Card> deck = theBoard.getDeck();
		Solution solution = new Solution();
		solution.createSolution(deck);
		//creating variables for the correct cards
		Card correctRoom = solution.getRoom();
		Card correctPerson = solution.getPerson();
		Card correctWeapon = solution.getWeapon();
		Card wrongRoom = new Card();
		
		theBoard.removeSolutionCards(solution);
		//getting a room from the deck, which will not be in the solution
		for(int i = 0; i < deck.size(); i++) {
			Card currentCard = deck.get(i);
			if(currentCard.getType() == CardType.ROOM) {
				wrongRoom = currentCard;
				break;
			}
		}
		//dealing out the deck
		theBoard.deal();
		
		//creating the correct accusation
		Solution accusation = new Solution();
		accusation.setPerson(correctPerson);
		accusation.setRoom(wrongRoom);
		accusation.setWeapon(correctWeapon);
		//checking the accusation matches the solution
		assertFalse(theBoard.checkAccusation(accusation));
	}
	
	@Test
	void checkAccusationWrongPerson() {
		//creating a solution
		ArrayList<Card> deck = theBoard.getDeck();
		Solution solution = new Solution();
		solution.createSolution(deck);
		//creating variables for the correct cards
		Card correctRoom = solution.getRoom();
		Card correctPerson = solution.getPerson();
		Card correctWeapon = solution.getWeapon();
		Card wrongPerson = new Card();
		
		theBoard.removeSolutionCards(solution);
		//getting a room from the deck, which will not be in the solution
		for(int i = 0; i < deck.size(); i++) {
			Card currentCard = deck.get(i);
			if(currentCard.getType() == CardType.PERSON) {
				wrongPerson = currentCard;
				break;
			}
		}
		//dealing out the deck
		theBoard.deal();
		
		//creating the correct accusation
		Solution accusation = new Solution();
		accusation.setPerson(wrongPerson);
		accusation.setRoom(correctRoom);
		accusation.setWeapon(correctWeapon);
		//checking the accusation matches the solution
		assertFalse(theBoard.checkAccusation(accusation));
	}
	
	@Test
	void checkAccusationWrongWeapon() {
		//creating a solution
		ArrayList<Card> deck = theBoard.getDeck();
		Solution solution = new Solution();
		solution.createSolution(deck);
		//creating variables for the correct cards
		Card correctRoom = solution.getRoom();
		Card correctPerson = solution.getPerson();
		Card correctWeapon = solution.getWeapon();
		Card wrongWeapon = new Card();
		
		theBoard.removeSolutionCards(solution);
		//getting a room from the deck, which will not be in the solution
		for(int i = 0; i < deck.size(); i++) {
			Card currentCard = deck.get(i);
			if(currentCard.getType() == CardType.WEAPON) {
				wrongWeapon = currentCard;
				break;
			}
		}
		//dealing out the deck
		theBoard.deal();
		
		//creating the correct accusation
		Solution accusation = new Solution();
		accusation.setPerson(correctPerson);
		accusation.setRoom(correctRoom);
		accusation.setWeapon(wrongWeapon);
		//checking the accusation matches the solution
		assertFalse(theBoard.checkAccusation(accusation));
	}
	
	
	//Disprove suggestion tests
	@Test
	void disproveOneMatch() {
		//creating the musketCard, will be in the player's hand and will be suggested
		Card musketCard = new Card("Musket", CardType.WEAPON);
		
		//creating a default player
		Player defaultPlayer = new ComputerPlayer("Default", "color", 0, 0, "type");
		
		//creating 3 cards to go in the default player's hand, including the musket card
		Card cardInHand1 = musketCard;
		Card cardInHand2 = new Card("Car", CardType.WEAPON);
		Card cardInHand3 = new Card("Edna Dickson", CardType.PERSON);
		
		//updating the player with the given cards
		defaultPlayer.updateHand(cardInHand1);
		defaultPlayer.updateHand(cardInHand2);
		defaultPlayer.updateHand(cardInHand3);
		
		//creating a suggestion, including the musket card
		Card suggestionRoom = new Card("Roof", CardType.ROOM);
		Card suggestionPerson = new Card("Bobby Long", CardType.PERSON);
		Card suggestionWeapon = musketCard;
		
		//creating the disproved card
		Card disprovedCard = defaultPlayer.disproveSuggestion(suggestionRoom, suggestionPerson, suggestionWeapon);
		
		//testing that the disproved card is the same card that was in the player's hand
		assertEquals(cardInHand1, disprovedCard);
		//testing that the disproved card is the suggested weapon
		assertEquals(suggestionWeapon, disprovedCard);
		
	}
	
	@Test
	void disproveMoreThanOneMatch() {
		//creating the musketCard, will be in the player's hand and will be suggested
		Card musketCard = new Card("Musket", CardType.WEAPON);
		//creating the ednaCard, will be in the player's hand and will be suggested
		Card ednaCard = new Card("Edna Dickson", CardType.PERSON);
		//creating a default player
		Player defaultPlayer = new ComputerPlayer("Default", "color", 0, 0, "type");

		//creating 3 cards to go in the default player's hand, including the musket and edna cards
		Card cardInHand1 = musketCard;
		Card cardInHand2 = new Card("Car", CardType.WEAPON);
		Card cardInHand3 = ednaCard;

		//updating the player with the given cards
		defaultPlayer.updateHand(cardInHand1);
		defaultPlayer.updateHand(cardInHand2);
		defaultPlayer.updateHand(cardInHand3);

		//creating a suggestion, including the musket and edna cards
		Card suggestionRoom = new Card("Roof", CardType.ROOM);
		Card suggestionPerson = ednaCard;
		Card suggestionWeapon = musketCard;

		//creating the disproved card
		Card disprovedCard = defaultPlayer.disproveSuggestion(suggestionRoom, suggestionPerson, suggestionWeapon);

		//testing that the disproved card is one of the cards in the player's hand
		assertTrue(cardInHand1 == disprovedCard || cardInHand3 == disprovedCard);
		//testing that the disproved card one of the suggested cards
		assertTrue(suggestionWeapon == disprovedCard || suggestionPerson == disprovedCard);
	}
	
	@Test
	void disproveNoMatch() {
		//creating a default player
		Player defaultPlayer = new ComputerPlayer("Default", "color", 0, 0, "type");

		//creating 3 cards to go in the default player's hand, including the musket card
		Card cardInHand1 = new Card("Atrium", CardType.ROOM);
		Card cardInHand2 = new Card("Car", CardType.WEAPON);
		Card cardInHand3 = new Card("Edna Dickson", CardType.PERSON);

		//updating the player with the given cards
		defaultPlayer.updateHand(cardInHand1);
		defaultPlayer.updateHand(cardInHand2);
		defaultPlayer.updateHand(cardInHand3);

		//creating a suggestion, including the musket card
		Card suggestionRoom = new Card("Roof", CardType.ROOM);
		Card suggestionPerson = new Card("Bobby Long", CardType.PERSON);
		Card suggestionWeapon = new Card("Musket", CardType.WEAPON);

		//creating the disproved card
		Card disprovedCard = defaultPlayer.disproveSuggestion(suggestionRoom, suggestionPerson, suggestionWeapon);
		//testing that the disprovedCard is null
		assertEquals(null, disprovedCard);
	}
	
	//Handle suggestion tests
	
}
