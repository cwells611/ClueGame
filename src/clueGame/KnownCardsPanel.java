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
	Player player;
	private ArrayList<Card> inHandCards = new ArrayList<Card>();
	private static KnownCardsPanel KCPanel = new KnownCardsPanel(); 

	private KnownCardsPanel() {
		player = Board.getInstance().getHumanPlayer(); 

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
	}

	public static KnownCardsPanel getKCPanel() {
		return KCPanel; 
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

	public void updateDisplay() {
		//in order to update the panel we are going to want to re-the constructor to redraw the panel
		this.KCPanel.remove(roomsPanel);
		this.KCPanel.remove(peoplePanel);
		this.KCPanel.remove(weaponsPanel);
		this.KCPanel.revalidate();
		setLayout(new GridLayout(3,0));
		Border blackline = BorderFactory.createTitledBorder("Known Cards");
		setBorder(blackline);
		peoplePanel = peoplePanel(player);
		add(peoplePanel);
		roomsPanel = roomsPanel(player);
		add(roomsPanel);
		weaponsPanel = weaponsPanel(player);
		add(weaponsPanel);
	}

	public void setCardTextFields(CardType type, JPanel panel, ArrayList<Card> vector) {
		boolean b = false;
		//checking if any of the cards in the vector match the given type
		for(Card card : vector) {
			if(card.getType() == type) {
				b = true;
				break;
			}
		}
		if(vector.size() == 0 || b == false) {
			JTextField none = new JTextField();
			none.setText("None");
			none.setEditable(false);
			panel.add(none);
		}else {
			for(Card card : vector) {
				if(card.getType() == type) {
					JTextField addLabel = new JTextField();
					addLabel.setText(card.getName());
					//loop through the list of player 
					for(Player player : Board.getInstance().getPlayers()) {
						//see if player's hand contains card 
						if(player.getHand().contains(card)) {
							//set the background color of the label to that player's color 
							addLabel.setBackground(player.getColor());
						}
					}
					addLabel.setEditable(false);
					panel.add(addLabel);
				}
			}
		}
	}
}