package ast.unicore.view.webcomponent.papercombo;

public class DuplicateItemException extends RuntimeException {
	private static final long serialVersionUID = -2427410062212499934L;

	public DuplicateItemException() {
	}

	public DuplicateItemException(String message) {
		super(message);
	}

	public DuplicateItemException(Throwable cause) {
		super(cause);
	}

	public DuplicateItemException(String message, Throwable cause) {
		super(message, cause);
	}
}
