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
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Room;
import clueGame.Solution;

public class ComputerAITest {
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

	//tests that handle computer player creating suggestion 
	@Test 
	public void checkRoomAndCurrentLocation() {
		//want to make sure that the room the computer player is basing its suggestion on matches the current
		//location of the computer player 
		//gets the deck of cards 
		ArrayList<Card> deck = theBoard.getDeck(); 
		//creates new computer player 
		ComputerPlayer test = new ComputerPlayer("Bobby Long", "Purple", 3, 0, "Computer"); 
		//sets the position of the player in the bedroom 
		BoardCell playerPosition = theBoard.getCell(20, 11); 
		//get the room the player is in from the board using the computer position
		Room currentRoom = theBoard.getRoom(playerPosition); 
		//now that the computer is in a room, we are going to create a suggestion 
		Solution computerSuggestion = test.createSuggestion(theBoard, currentRoom); 
		//get the card from the deck that is associated with the room the player is in  
		for(Card card : deck) {
			if(card.getName().equals(currentRoom.getName())) {
				//once we have found the card associated with the room the player in is
				//test to make sure the room in the suggestion is the same as the room the player is in 
				assertEquals(computerSuggestion.getRoom(), card); 
			}
		}
	}

	@Test
	public void ifOneWeaponNotSeenThenSelected() {
		//if the computer has seen 5 out of the 6 weapons, then it puts that weapon into its suggestion
		//gets the deck of cards 
		ArrayList<Card> deck = theBoard.getDeck(); 
		//creates new computer player 
		ComputerPlayer test = new ComputerPlayer("Bobby Long", "Purple", 3, 0, "Computer"); 
		//sets the position of the player in the bedroom 
		BoardCell playerPosition = theBoard.getCell(20, 11); 
		//get the room the player is in from the board using the computer position
		Room currentRoom = theBoard.getRoom(playerPosition); 
		//adds 5 out of the 6 weapons to the computer's seen list 
		test.addSeenCard(deck.get(15));
		test.addSeenCard(deck.get(16));
		test.addSeenCard(deck.get(17));
		test.addSeenCard(deck.get(19));
		test.addSeenCard(deck.get(20));
		//creates new suggestion 
		Solution computerSuggestion = test.createSuggestion(theBoard, currentRoom); 
		//check to make sure that the weapon in the suggestion is the only one the computer has not seen
		assertEquals(computerSuggestion.getWeapon(), deck.get(18)); 
	}
	
	@Test
	public void ifOnePersonNotSeenThenSelected() {
		//if the computer has seen 5 out of the 6 people, then it puts that person into its suggestion
		//gets the deck of cards 
		ArrayList<Card> deck = theBoard.getDeck(); 
		//creates new computer player 
		ComputerPlayer test = new ComputerPlayer("Bobby Long", "Purple", 3, 0, "Computer"); 
		//sets the position of the player in the bedroom 
		BoardCell playerPosition = theBoard.getCell(20, 11); 
		//get the room the player is in from the board using the computer position
		Room currentRoom = theBoard.getRoom(playerPosition); 
		//adds 5 out of the 6 people to the computer's seen list 
		test.addSeenCard(deck.get(9));
		test.addSeenCard(deck.get(10));
		test.addSeenCard(deck.get(12));
		test.addSeenCard(deck.get(13));
		test.addSeenCard(deck.get(14));
		//creates new suggestion 
		Solution computerSuggestion = test.createSuggestion(theBoard, currentRoom); 
		//check to make sure that the person in the suggestion is the only one the computer has not seen
		assertEquals(computerSuggestion.getPerson(), deck.get(11)); 
	}
	
	@Test 
	public void ifMultipleWeaponsNotSeenOnePickedRandomly() {
		//if the computer has not seen all but 1 weapon, then it picks randomly from the weapons it has not seen 
		//and adds that to the suggestion 
		//gets the deck of cards 
		ArrayList<Card> deck = theBoard.getDeck(); 
		//creates new computer player 
		ComputerPlayer test = new ComputerPlayer("Bobby Long", "Purple", 3, 0, "Computer"); 
		//sets the position of the player in the bedroom 
		BoardCell playerPosition = theBoard.getCell(20, 11); 
		//get the room the player is in from the board using the computer position
		Room currentRoom = theBoard.getRoom(playerPosition); 
		//adds 3 out of the 6 weapons to the computers seen list 
		test.addSeenCard(deck.get(15));
		test.addSeenCard(deck.get(17));
		test.addSeenCard(deck.get(20));
		//creates new suggestion 
		Solution computerSuggestion = test.createSuggestion(theBoard, currentRoom);
		//check to make sure that the weapon in the suggestion is not one of the ones that it has seen 
		assertNotEquals(computerSuggestion.getWeapon(), deck.get(15)); 
		assertNotEquals(computerSuggestion.getWeapon(), deck.get(17)); 
		assertNotEquals(computerSuggestion.getWeapon(), deck.get(20));
		//if we know that it is not equal to any of the cards we have seen then we can assume it is one of the 
		//weapon cards we have not seen
	}
}
