package clueGame;

public class BadConfigFormatException extends Exception{
	public BadConfigFormatException() {
		super("Error: Files not formatted properly."); 
	}
	
	public BadConfigFormatException(String message) {
		System.out.println(message);
	}

}
