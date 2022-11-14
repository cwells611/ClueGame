package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;

public class KnownCardsPanel extends JPanel {

	/**
	 * Constructor for the panel, it does 90% of the work
	 */

	JPanel addPanel;
	JTextField addText;

	JPanel peoplePanel;
	JPanel roomsPanel;
	JPanel weaponsPanel;

	private ArrayList<Card> inHandCards = new ArrayList<Card>();


	public KnownCardsPanel(Player player)  {
		//Create a layout with 3 rows
		inHandCards = player.getHand();
		
		setLayout(new GridLayout(3,0));
		Border blackline = BorderFactory.createTitledBorder("Known Cards");
		setBorder(blackline);
		peoplePanel = peoplePanel(player);
		add(peoplePanel);
		roomsPanel = roomsPanel(player);
		add(roomsPanel);
		weaponsPanel = weaponsPanel(player);
		add(weaponsPanel);
		//need to add people rooms and weapons panels
	}

	private JPanel peoplePanel(Player player) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		Border blackline = BorderFactory.createTitledBorder("People");
		panel.setBorder(blackline);
		panel.add(inHandLabel());
		setCardTextFields(CardType.PERSON, panel, inHandCards);
		panel.add(seenLabel());
		setCardTextFields(CardType.PERSON, panel, player.getSeenCards());
		return panel;
	}

	private JPanel roomsPanel(Player player) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		Border blackline = BorderFactory.createTitledBorder("Rooms");
		panel.setBorder(blackline);
		panel.add(inHandLabel());
		setCardTextFields(CardType.ROOM, panel, inHandCards);
		panel.add(seenLabel());
		setCardTextFields(CardType.ROOM, panel, player.getSeenCards());
		return panel;
	}

	private JPanel weaponsPanel(Player player) {
		JPanel panel = new JPanel();
		Border blackline = BorderFactory.createTitledBorder("Weapons");
		panel.setLayout(new GridLayout(0, 1));
		panel.setBorder(blackline);
		panel.add(inHandLabel());
		setCardTextFields(CardType.WEAPON, panel, inHandCards);
		panel.add(seenLabel());
		setCardTextFields(CardType.WEAPON, panel, player.getSeenCards());
		return panel;
	}

	private JLabel inHandLabel() {
		JLabel label = new JLabel();
		label.setText("In Hand:");
		return label;
	}

	private JLabel seenLabel() {
		JLabel label = new JLabel();
		label.setText("Seen:");
		return label;
	}



	public void addCard(Card card, Player player) {
		JLabel label = new JLabel();
		label.setText(card.getName());
		switch(card.getType()) {
		case ROOM:
			if(player.getHand().contains(card)) {
				inHandCards.add(card);
				//recall the constructor, now that the arrayList is updated
				roomsPanel = roomsPanel(player);
			}else if(player.getSeenCards().contains(card)) {
				player.addSeenCard(card);
				roomsPanel = roomsPanel(player);
			}
			break;
		case PERSON:
			if(player.getHand().contains(card)) {
				inHandCards.add(card);
				//recall the constructor, now that the arrayList is updated
				peoplePanel = peoplePanel(player);
			}else if(player.getSeenCards().contains(card)) {
				player.addSeenCard(card);
				peoplePanel = peoplePanel(player);
			}
			break;
		case WEAPON:
			if(player.getHand().contains(card)) {
				inHandCards.add(card);
				//recall the constructor, now that the arrayList is updated
				weaponsPanel = weaponsPanel(player);
			}else if(player.getSeenCards().contains(card)) {
				player.addSeenCard(card);
				weaponsPanel = weaponsPanel(player);
			}
			break;
		}
	}
	
	public KnownCardsPanel updateDisplay(Player player, KnownCardsPanel panel) {
		//in order to update the panel we are going to want to re-the constructor to redraw the panel
		panel = new KnownCardsPanel(player); 
		return panel; 
	}

	public void setCardTextFields(CardType type, JPanel panel, ArrayList<Card> vector) {
		if(vector.size() == 0) {
			JTextField none = new JTextField();
			none.setText("None");
			panel.add(none);
		}else {
			for(Card card : vector) {
				if(card.getType() == type) {
					JTextField addLabel = new JTextField();
					addLabel.setText(card.getName());
					panel.add(addLabel);
				}
			}
		}
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Player humanPlayer = new HumanPlayer("Col. Mustard", "orange", 0, 0);
		KnownCardsPanel panel = new KnownCardsPanel(humanPlayer); // create the panel
		JFrame frame = new JFrame(); // create the frame
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 570); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		//Makes 3 people, 3 weapons, and 3 rooms cards for testing purposes 
		Card person1 = new Card("person1", CardType.PERSON); 
		Card person2 = new Card("person2", CardType.PERSON); 
		Card person3 = new Card("person3", CardType.PERSON); 
		Card room1 = new Card("room1", CardType.ROOM); 
		Card room2 = new Card("room2", CardType.ROOM); 
		Card room3 = new Card("room3", CardType.ROOM); 
		Card weapon1 = new Card("weapon1", CardType.WEAPON); 
		Card weapon2 = new Card("weapon2", CardType.WEAPON); 
		Card weapon3 = new Card("weapon3", CardType.WEAPON); 
		//adds 2 rooms and a weapon to the players hand
		humanPlayer.updateHand(room2);
		humanPlayer.updateHand(room3);
		humanPlayer.updateHand(weapon3);
		
		panel = new KnownCardsPanel(humanPlayer); 
		//adds the remaining cards to the players seen list 
		humanPlayer.addSeenCard(person1);
		humanPlayer.addSeenCard(person2);
		humanPlayer.addSeenCard(person3);
		humanPlayer.addSeenCard(room1);
		humanPlayer.addSeenCard(weapon1);
		humanPlayer.addSeenCard(weapon2);
		panel = new KnownCardsPanel(humanPlayer);
		frame.setContentPane(panel);
		
		panel.revalidate();

	}
}