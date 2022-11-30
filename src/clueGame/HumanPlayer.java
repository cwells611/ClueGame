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
		Card selectedRoom = saPanel.getSelectedRoom();
		Card selectedPerson = null;
		Card selectedWeapon = null;
		
		System.out.println(selectedRoom.getName());
		System.out.println(selectedPerson.getName());
		System.out.println(selectedWeapon.getName());
		accusation.setRoom(selectedRoom);
		accusation.setPerson(selectedPerson);
		accusation.setWeapon(selectedWeapon);
		return accusation;
	}
}
