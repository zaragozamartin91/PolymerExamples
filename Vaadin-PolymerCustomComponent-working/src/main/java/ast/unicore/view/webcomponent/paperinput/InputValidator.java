package ast.unicore.view.webcomponent.paperinput;

public interface InputValidator<E> {
	/**
	 * Valida el contenido del campo.
	 * 
	 * @param inputValue
	 *            Contenido del input.
	 * @param clientComponentInputIsInvalid
	 *            True si el componente del lado del cliente dice que el contenido es invalido (por ejemplo,
	 *            estableciendo un criterio mediante {@link PaperTextInput#setPattern}). False en caso contrario.
	 * @throws InvalidInputException
	 *             Si el valor del input es invalido.
	 */
	public void validate(E inputValue, boolean clientComponentInputIsInvalid) throws InvalidInputException;
}