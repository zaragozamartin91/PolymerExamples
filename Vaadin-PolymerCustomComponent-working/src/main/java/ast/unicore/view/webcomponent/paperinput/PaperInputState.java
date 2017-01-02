package ast.unicore.view.webcomponent.paperinput;

import com.vaadin.shared.ui.JavaScriptComponentState;

import ast.unicore.view.webcomponent.paperinput.date.PaperDateInput;

/**
 * Representa el estado actual de un {@link PaperTextInput}.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
public class PaperInputState extends JavaScriptComponentState {
	public String inputValue;
	public String inputLabel;
	public boolean inputRequired;
	public String inputPattern;
	public String inputErrorMessage;

	public boolean inputDisabled;

	/**
	 * True si el contenido del paper input es invalido.
	 */
	public boolean inputInvalid;

	/**
	 * Anchura para determinados componentes.
	 * 
	 * @see PaperDateInput#setWidth(String)
	 */
	public String inputWidth;
}
