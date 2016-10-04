package ast.unicore.view.webcomponent.paperinput;

@SuppressWarnings("serial")
public class InvalidInputException extends Exception {
	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidInputException(String message) {
		super(message);
	}

}