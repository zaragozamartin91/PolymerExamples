package ast.unicore.view.webcomponent.paperinput.text;

import ast.unicore.view.webcomponent.paperinput.AbstractPaperInput;
import ast.unicore.view.webcomponent.paperinput.PaperInputState;

import com.vaadin.annotations.JavaScript;

/**
 * Componente de vaadin del lado del servidor representante del componente paper-input de polymer.
 * 
 * @author martin.zaragoza
 * 
 * @see PaperInputState
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "paper-text-input-connector.js" })
public class PaperTextInput extends AbstractPaperInput<String> {
	/**
	 * Crea un nuevo PaperInput con un label.
	 * 
	 * @param label
	 *            Label del campo.
	 */
	public PaperTextInput(String label) {
		getState().inputValue = "";
		getState().inputLabel = label;

		addHandleChangeCallback();
		addHandleFocusCallback();
	}

	/**
	 * Establece el valor del campo.
	 * 
	 * @param value
	 *            Valor nuevo.
	 */
	@Override
	public void setValue(String value) {
		getState().inputValue = value;
		wrappedField.setValue(value);
		markAsDirty();
	}

	/**
	 * Obtiene el valor del campo.
	 * 
	 * @return Valor del campo.
	 */
	@Override
	public String getValue() {
		return getState().inputValue;
	}

	// /**
	// * Establece el patron de validacion del campo como una expresion regular. Si el contenido del input no cumple con
	// * la expresion regular, se lo marcara como invalido.
	// *
	// * @param pattern
	// * Expresion regular de validacion del campo. Ej: [A-Za-z]+ es expresion regular de caracteres
	// * alfabeticos SIN espacios ni numeros.
	// */
	// public void setPattern(String pattern) {
	// this.getState().inputPattern = pattern;
	// markAsDirty();
	// }

	/**
	 * Limpia el campo y lo restaura a su estado original.
	 */
	@Override
	public void clear() {
		setValue("");
		setInputValid();
	}
}
