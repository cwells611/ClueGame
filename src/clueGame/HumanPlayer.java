package clueGame;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, String color, int startRow, int startCol) {
		super(name, color, startRow, startCol);

	}
	@Override
	public Solution doAccusation() {
		SuggestionAccusationPanel saPanel = new SuggestionAccusationPanel(false);
		saPanel.setVisible(true);
		Solution accusation = new Solution();
		Card selectedRoom = null;
		Card selectedPerson = null;
		Card selectedWeapon = null;
		for(Card card : Board.getInstance().getAllCards()) {
			if(card.getName() == saPanel.getSelectedRoom()) {
				selectedRoom = card;
			}
			if(card.getName() == saPanel.getSelectedPerson()) {
				selectedPerson = card;
			}
			if(card.getName() == saPanel.getSelectedWeapon()) {
				selectedWeapon = card;
			}
		}
		accusation.setRoom(selectedRoom);
		accusation.setPerson(selectedPerson);
		accusation.setWeapon(selectedWeapon);
		return accusation;
	}
}
