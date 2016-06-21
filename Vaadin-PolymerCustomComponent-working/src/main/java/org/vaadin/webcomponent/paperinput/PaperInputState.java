package org.vaadin.webcomponent.paperinput;

import com.vaadin.shared.ui.JavaScriptComponentState;

/**
 * Representa el estado actual de un PaperTextfield.
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
}
