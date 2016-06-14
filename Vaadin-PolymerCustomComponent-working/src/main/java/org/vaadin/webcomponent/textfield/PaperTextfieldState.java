package org.vaadin.webcomponent.textfield;

import com.vaadin.shared.ui.JavaScriptComponentState;

/**
 * Representa el estado actual de un PaperTextfield.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
public class PaperTextfieldState extends JavaScriptComponentState {
	public String inputValue;
	public String inputLabel;
	public Boolean inputRequired;
	public String inputPattern;
	public String inputErrorMessage;
}
