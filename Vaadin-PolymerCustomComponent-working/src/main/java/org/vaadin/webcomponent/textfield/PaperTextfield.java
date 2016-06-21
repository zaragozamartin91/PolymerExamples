package org.vaadin.webcomponent.textfield;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

/**
 * Componente de vaadin del lado del servidor representante del componente input-button de polymer.
 * 
 * El archivo de javascript vinculado debe encontrarse en org/vaadin/webcomponent/chessboard/vaadinintegration.js.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "paper-textfield-connector.js" })
public final class PaperTextfield extends AbstractJavaScriptComponent {
	/**
	 * Crea un nuevo PaperTextField con un label.
	 * 
	 * @param label
	 *            - Label del campo.
	 * @return nuevo PaperTextField.
	 */
	public static PaperTextfield newWithLabel(String label) {
		return new PaperTextfield(label);
	}

	/**
	 * Crea un nuevo PaperTextField con un label.
	 * 
	 * @param label
	 *            - Label del campo.
	 */
	public PaperTextfield(final String label) {
		getState().inputValue = "";
		getState().inputLabel = label;

		addHandleKickCallback();
	}

	@Override
	protected PaperTextfieldState getState() {
		return (PaperTextfieldState) super.getState();
	}

	/**
	 * Establece el valor del campo.
	 * 
	 * @param value
	 *            - Valor a setear.
	 */
	public void setValue(String value) {
		getState().inputValue = value;
		markAsDirty();
	}

	/**
	 * Obtiene el valor del campo.
	 * 
	 * @return - Valor del campo.
	 */
	public String getValue() {
		return getState().inputValue;
	}

	/**
	 * Establece si el Input es requerido.
	 * 
	 * @param isRequired
	 *            - True si es requerido, false caso contrario.
	 */
	public void setRequired(boolean isRequired) {
		this.getState().inputRequired = isRequired;
		markAsDirty();
	}

	/**
	 * Establece mensaje de error de validacion del campo.
	 * 
	 * @param errMsg
	 *            - Mensaje a obtener.
	 */
	public void setErrorMessage(String errMsg) {
		this.getState().inputErrorMessage = errMsg;
		markAsDirty();
	}

	/**
	 * Establece el patron de validacion del campo como una expresion regular.
	 * 
	 * @param pattern
	 *            - Expresion regular de validacion del campo. Ej: [A-Za-z]+ es expresion regular de caracteres
	 *            alfabeticos SIN espacios ni numeros.
	 */
	public void setPattern(String pattern) {
		this.getState().inputPattern = pattern;
		markAsDirty();
	}

	public void callGetValue() {
		callFunction("getValue");
	}
	
	private void addHandleKickCallback() {
		addFunction("handleKick", new JavaScriptFunction() {
			public void call(JsonArray arguments) {
				System.out.println("calling PaperTextfield#handleKick with: " + arguments.getString(0));
				getState().inputValue = arguments.getString(0);
			}
		});
	}
}
