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
		for(Card card : inHandCards) {
			if(card.getType() == CardType.PERSON) {
				JLabel addLabel = new JLabel();
				addLabel.setText(card.getName());
				panel.add(addLabel);
			}
		}
		panel.add(seenLabel());
		//loops through player's seen cards and if it is a person, add it to panel 
		for(Card card : player.getSeenCards()) {
			if(card.getType() == CardType.PERSON) {
				JLabel addLabel = new JLabel(); 
				addLabel.setText(card.getName());
				panel.add(addLabel); 
			}
		}
		return panel;
	}

	private JPanel roomsPanel(Player player) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		Border blackline = BorderFactory.createTitledBorder("Rooms");
		panel.setBorder(blackline);
		panel.add(inHandLabel());
		for(Card card : inHandCards) {
			if(card.getType() == CardType.ROOM) {
				JLabel addLabel = new JLabel();
				addLabel.setText(card.getName());
				panel.add(addLabel);
			}
		}
		panel.add(seenLabel());
		//loops through player's seen cards and if it is a room, add it to panel 
		for(Card card : player.getSeenCards()) {
			if(card.getType() == CardType.ROOM) {
				JLabel addLabel = new JLabel(); 
				addLabel.setText(card.getName());
				panel.add(addLabel); 
			}
		}
		return panel;
	}

	private JPanel weaponsPanel(Player player) {
		JPanel panel = new JPanel();
		Border blackline = BorderFactory.createTitledBorder("Weapons");
		panel.setLayout(new GridLayout(0, 1));
		panel.setBorder(blackline);
		panel.add(inHandLabel());
		for(Card card : inHandCards) {
			if(card.getType() == CardType.WEAPON) {
				JLabel addLabel = new JLabel();
				addLabel.setText(card.getName());
				panel.add(addLabel);
			}
		}
		panel.add(seenLabel());
		//loops through player's seen cards and if it is a weapon, add it to panel 
		for(Card card : player.getSeenCards()) {
			if(card.getType() == CardType.WEAPON) {
				JLabel addLabel = new JLabel(); 
				addLabel.setText(card.getName());
				panel.add(addLabel); 
			}
		}
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
			}
		case PERSON:
			break;
		case WEAPON:
			break;
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




	}
}