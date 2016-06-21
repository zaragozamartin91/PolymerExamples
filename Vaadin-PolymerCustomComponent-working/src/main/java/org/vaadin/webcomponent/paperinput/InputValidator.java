package org.vaadin.webcomponent.paperinput;


public interface InputValidator {
	@SuppressWarnings("serial")
	public static class InvalidInputException extends Exception {
		public InvalidInputException(String message, Throwable cause) {
			super(message, cause);
		}

		public InvalidInputException(String message) {
			super(message);
		}

	}

	/**
	 * Valida el contenido del campo.
	 * 
	 * @param inputValue
	 *            - Contenido del input.
	 * @param clientComponentInputIsInvalid
	 *            - True si el componente paper-input de Polymer dice que el contenido es invalido (mediante
	 *            {@link PaperInput#setPattern}). False en caso contrario.
	 * @throws InvalidInputException
	 */
	public void validate(String inputValue, boolean clientComponentInputIsInvalid) throws InputValidator.InvalidInputException;
}