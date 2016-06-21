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
public final class PaperTextfield_bak3 extends AbstractJavaScriptComponent {
	private String value;
	
	/**
	 * Crea un nuevo PaperTextField con un label.
	 * 
	 * @param label
	 *            - Label del campo.
	 * @return nuevo PaperTextField.
	 */
	public static PaperTextfield_bak3 newWithLabel(String label) {
		return new PaperTextfield_bak3(label);
	}

	/**
	 * Crea un nuevo PaperTextField con un label.
	 * 
	 * @param label
	 *            - Label del campo.
	 */
	public PaperTextfield_bak3(final String label) {
		beforeClientResponse(true);
		
		callFunction("init", label);
		
		addHandleKickCallback();
	}


	/**
	 * Establece el valor del campo.
	 * 
	 * @param value
	 *            - Valor a setear.
	 */
	public void setValue(String value) {
		callFunction("setValue", value);
	}

	/**
	 * Obtiene el valor del campo.
	 * 
	 * @return - Valor del campo.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Establece si el Input es requerido.
	 * 
	 * @param isRequired
	 *            - True si es requerido, false caso contrario.
	 */
	public void setRequired(boolean isRequired) {
		callFunction("setRequired", isRequired);
	}

	/**
	 * Establece mensaje de error de validacion del campo.
	 * 
	 * @param errMsg
	 *            - Mensaje a obtener.
	 */
	public void setErrorMessage(String errMsg) {
		callFunction("setErrorMessage", errMsg);
	}

	/**
	 * Establece el patron de validacion del campo como una expresion regular.
	 * 
	 * @param pattern
	 *            - Expresion regular de validacion del campo. Ej: [A-Za-z]+ es expresion regular de caracteres
	 *            alfabeticos SIN espacios ni numeros.
	 */
	public void setPattern(String pattern) {
		callFunction("setPattern", pattern);
	}

	public void callGetValue() {
		callFunction("getValue");
	}
	
	private void addHandleKickCallback() {
		addFunction("handleKick", new JavaScriptFunction() {
			public void call(JsonArray arguments) {
				System.out.println("calling PaperTextfield#handleKick with: " + arguments.getString(0));
				value = arguments.getString(0);
			}
		});
	}
}
