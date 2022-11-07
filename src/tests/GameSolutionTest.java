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
import clueGame.HumanPlayer;
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
	//ACCUSATION TESTS
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
	
	
	//DISPROVE SUGGESTION TESTS
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
		
		//checking that the disproved card is not null
		assertNotEquals(null, disprovedCard);
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
	
	//HANDLE SUGGESTION TESTS
	
//	Suggestion no one can disprove returns null
	@Test
	void noDisproves() {
		//creating 4 players, the human player and 3 cpus
		Player humanPlayer = new HumanPlayer("Human", "color", 0 , 0, "type");
		Player cpu1 = new ComputerPlayer("cpu1", "color", 1, 1, "type");
		Player cpu2 = new ComputerPlayer("cpu2", "color", 2, 2, "type");
		Player cpu3 = new ComputerPlayer("cpu3", "color", 3, 3, "type");
		
		//creating 15 cards, so each player can have 3 cards, and 3 extra cards
		Card roofCard = new Card("Roof", CardType.ROOM);
		Card bedroomCard = new Card("Bedroom", CardType.ROOM);
		Card saunaCard = new Card("Sauna", CardType.ROOM);
		Card atriumCard = new Card("Atrium", CardType.ROOM);
		Card theaterCard = new Card("Theater", CardType.ROOM);
		Card nigelCard = new Card("Nigel Thomas", CardType.PERSON);
		Card bobbyCard = new Card("Bobby Long", CardType.PERSON);
		Card craigCard = new Card("Craig Downs", CardType.PERSON);
		Card ednaCard = new Card("Edna Dickson", CardType.PERSON);
		Card judasCard = new Card("Judas Watkins", CardType.PERSON);
		Card panCard = new Card("Frying pan", CardType.WEAPON);
		Card bladeCard = new Card("Swtich Blade", CardType.WEAPON);
		Card musketCard = new Card("Musket", CardType.WEAPON);
		Card carCard = new Card("Car", CardType.WEAPON);
		Card boulderCard = new Card("Boulder", CardType.WEAPON);
		
		//giving all of the players 3 cards from the deck, so theater judas and boulder will be left over
		humanPlayer.updateHand(roofCard);
		humanPlayer.updateHand(nigelCard);
		humanPlayer.updateHand(panCard);
		cpu1.updateHand(bedroomCard);
		cpu1.updateHand(bobbyCard);
		cpu1.updateHand(bladeCard);
		cpu2.updateHand(saunaCard);
		cpu2.updateHand(craigCard);
		cpu2.updateHand(musketCard);
		cpu3.updateHand(atriumCard);
		cpu3.updateHand(ednaCard);
		cpu3.updateHand(carCard);
		
		//creating an arrayList of the players
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(humanPlayer);
		players.add(cpu1);
		players.add(cpu2);
		players.add(cpu3);
		
		//creating a suggestion with cards that the 3 cards that are not in anyone's hand
		Solution solution = new Solution();
		solution.setRoom(theaterCard);
		solution.setPerson(judasCard);
		solution.setWeapon(boulderCard);
		//the human player suggests the true solution
		Card handleCard = theBoard.handleSuggestion(humanPlayer, players, solution);
		//asserting null, as suggestion is equal to the answer
		assertEquals(handleCard, null);
	}
	
	
//	Suggestion only suggesting player can disprove returns null
	@Test
	void onlySuggestingPlayerCanDisprove() {
		//creating 4 players, the human player and 3 cpus
		Player humanPlayer = new HumanPlayer("Human", "color", 0 , 0, "type");
		Player cpu1 = new ComputerPlayer("cpu1", "color", 1, 1, "type");
		Player cpu2 = new ComputerPlayer("cpu2", "color", 2, 2, "type");
		Player cpu3 = new ComputerPlayer("cpu3", "color", 3, 3, "type");

		//creating 14 cards, so each player can have 3 cards, and 2 extra cards
		Card roofCard = new Card("Roof", CardType.ROOM);
		Card bedroomCard = new Card("Bedroom", CardType.ROOM);
		Card saunaCard = new Card("Sauna", CardType.ROOM);
		Card atriumCard = new Card("Atrium", CardType.ROOM);
		Card theaterCard = new Card("Theater", CardType.ROOM);
		Card nigelCard = new Card("Nigel Thomas", CardType.PERSON);
		Card bobbyCard = new Card("Bobby Long", CardType.PERSON);
		Card craigCard = new Card("Craig Downs", CardType.PERSON);
		Card ednaCard = new Card("Edna Dickson", CardType.PERSON);
		Card judasCard = new Card("Judas Watkins", CardType.PERSON);
		Card panCard = new Card("Frying pan", CardType.WEAPON);
		Card bladeCard = new Card("Swtich Blade", CardType.WEAPON);
		Card musketCard = new Card("Musket", CardType.WEAPON);
		Card carCard = new Card("Car", CardType.WEAPON);

		//giving all of the players 3 cards from the deck, so theater and judas will be left over
		humanPlayer.updateHand(roofCard);
		humanPlayer.updateHand(nigelCard);
		humanPlayer.updateHand(panCard);
		cpu1.updateHand(bedroomCard);
		cpu1.updateHand(bobbyCard);
		cpu1.updateHand(bladeCard);
		cpu2.updateHand(saunaCard);
		cpu2.updateHand(craigCard);
		cpu2.updateHand(musketCard);
		cpu3.updateHand(atriumCard);
		cpu3.updateHand(ednaCard);
		cpu3.updateHand(carCard);

		//creating an arrayList of the players
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(humanPlayer);
		players.add(cpu1);
		players.add(cpu2);
		players.add(cpu3);

		//creating a solution with the 2 unused cards, and the weapon that the suggesting human player has
		//only the human player would know that the pan card cannot be in the true answer
		Solution solution = new Solution();
		solution.setRoom(theaterCard);
		solution.setPerson(judasCard);
		solution.setWeapon(panCard);
		//the human player suggests the true solution
		Card handleCard = theBoard.handleSuggestion(humanPlayer, players, solution);
		//asserting null, because the human is guessing only cards that are either in the solution or in the human's hand
		assertEquals(handleCard, null);
	}
//	Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
	@Test
	void onlyHumanCanDisprove(){
		//creating 4 players, the human player and 3 cpus
		Player humanPlayer = new HumanPlayer("Human", "color", 0 , 0, "type");
		Player cpu1 = new ComputerPlayer("cpu1", "color", 1, 1, "type");
		Player cpu2 = new ComputerPlayer("cpu2", "color", 2, 2, "type");
		Player cpu3 = new ComputerPlayer("cpu3", "color", 3, 3, "type");

		//creating 14 cards, so each player can have 3 cards, and 2 extra cards
		Card roofCard = new Card("Roof", CardType.ROOM);
		Card bedroomCard = new Card("Bedroom", CardType.ROOM);
		Card saunaCard = new Card("Sauna", CardType.ROOM);
		Card atriumCard = new Card("Atrium", CardType.ROOM);
		Card theaterCard = new Card("Theater", CardType.ROOM);
		Card nigelCard = new Card("Nigel Thomas", CardType.PERSON);
		Card bobbyCard = new Card("Bobby Long", CardType.PERSON);
		Card craigCard = new Card("Craig Downs", CardType.PERSON);
		Card ednaCard = new Card("Edna Dickson", CardType.PERSON);
		Card judasCard = new Card("Judas Watkins", CardType.PERSON);
		Card panCard = new Card("Frying pan", CardType.WEAPON);
		Card bladeCard = new Card("Swtich Blade", CardType.WEAPON);
		Card musketCard = new Card("Musket", CardType.WEAPON);
		Card carCard = new Card("Car", CardType.WEAPON);

		//giving all of the players 3 cards from the deck, so theater and judas will be left over
		humanPlayer.updateHand(roofCard);
		humanPlayer.updateHand(nigelCard);
		humanPlayer.updateHand(panCard);
		cpu1.updateHand(bedroomCard);
		cpu1.updateHand(bobbyCard);
		cpu1.updateHand(bladeCard);
		cpu2.updateHand(saunaCard);
		cpu2.updateHand(craigCard);
		cpu2.updateHand(musketCard);
		cpu3.updateHand(atriumCard);
		cpu3.updateHand(ednaCard);
		cpu3.updateHand(carCard);

		//creating an arrayList of the players
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(humanPlayer);
		players.add(cpu1);
		players.add(cpu2);
		players.add(cpu3);

		//creating a solution with cards that the 3 cards that are not in anyone's hand
		Solution solution = new Solution();
		solution.setRoom(theaterCard);
		solution.setPerson(judasCard);
		solution.setWeapon(panCard);
		//cpu1 is suggesting the solution with theater judas and pan, human player has pan
		Card handleCard = theBoard.handleSuggestion(cpu1, players, solution);
		//asserting that the handled card is the panCard, which was held by the human
		assertEquals(panCard, handleCard);
	}
	
//	Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
	@Test
	void twoPlayersCanDisprove() {
		//creating 4 players, the human player and 3 cpus
		Player humanPlayer = new HumanPlayer("Human", "color", 0 , 0, "type");
		Player cpu1 = new ComputerPlayer("cpu1", "color", 1, 1, "type");
		Player cpu2 = new ComputerPlayer("cpu2", "color", 2, 2, "type");
		Player cpu3 = new ComputerPlayer("cpu3", "color", 3, 3, "type");

		//creating 13 cards, so each player can have 3 cards, and 1 extra card
		Card roofCard = new Card("Roof", CardType.ROOM);
		Card bedroomCard = new Card("Bedroom", CardType.ROOM);
		Card saunaCard = new Card("Sauna", CardType.ROOM);
		Card atriumCard = new Card("Atrium", CardType.ROOM);
		Card nigelCard = new Card("Nigel Thomas", CardType.PERSON);
		Card bobbyCard = new Card("Bobby Long", CardType.PERSON);
		Card craigCard = new Card("Craig Downs", CardType.PERSON);
		Card ednaCard = new Card("Edna Dickson", CardType.PERSON);
		Card panCard = new Card("Frying pan", CardType.WEAPON);
		Card bladeCard = new Card("Swtich Blade", CardType.WEAPON);
		Card musketCard = new Card("Musket", CardType.WEAPON);
		Card carCard = new Card("Car", CardType.WEAPON);
		Card boulderCard = new Card("Boulder", CardType.WEAPON);

		//giving all of the players 3 cards from the deck, so boulder will be left over
		humanPlayer.updateHand(roofCard);
		humanPlayer.updateHand(nigelCard);
		humanPlayer.updateHand(panCard);
		cpu1.updateHand(bedroomCard);
		cpu1.updateHand(bobbyCard);
		cpu1.updateHand(bladeCard);
		cpu2.updateHand(saunaCard);
		cpu2.updateHand(craigCard);
		cpu2.updateHand(musketCard);
		cpu3.updateHand(atriumCard);
		cpu3.updateHand(ednaCard);
		cpu3.updateHand(carCard);

		//creating an arrayList of the players
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(humanPlayer);
		players.add(cpu1);
		players.add(cpu2);
		players.add(cpu3);

		//creating a solution with cpu2's sauna room card and cpu3's edna person card 
		Solution solution = new Solution();
		solution.setRoom(saunaCard);
		solution.setPerson(ednaCard);
		solution.setWeapon(boulderCard);
		//the human player suggests the true solution
		Card handleCard = theBoard.handleSuggestion(humanPlayer, players, solution);
		//ensuring that the returnCard is cpu2's sauna room card, because cpu2 is the first in line to get checked
		assertEquals(handleCard, saunaCard);
	}
}
