package ast.unicore.view.webcomponent.paperinput.text;

import ast.unicore.view.webcomponent.paperinput.AbstractPaperInput;
import ast.unicore.view.webcomponent.paperinput.PaperInputState;

import com.vaadin.annotations.JavaScript;
import com.vaadin.data.Validator;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

/**
 * Componente de vaadin del lado del servidor representante del componente paper-textarea de polymer.
 * 
 * @author martin.zaragoza
 * 
 * @see PaperInputState
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "paper-text-area-connector.js" })
public class PaperTextArea extends AbstractPaperInput<String> {

	/**
	 * Crea una nueva instancia con un label.
	 * 
	 * @param label
	 *            Label del campo.
	 */
	public PaperTextArea(String label) {
		getState().inputValue = "";
		getState().inputLabel = label;

		addHandleChangeCallback();
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

	/**
	 * Limpia el campo y lo restaura a su estado original.
	 */
	@Override
	public void clear() {
		setValue("");
		setInputValid();
	}

	/**
	 * Agrega un validador del contenido.
	 * 
	 * @param newValidator
	 *            Nuevo validador.
	 * @return this.
	 */
	@Override
	public void addValidator(final Validator validator) {
		wrappedField.addValidator(validator);
	}

	@Override
	public void autoValidate() {
		throw new UnsupportedOperationException("Operacion no soportada por " + getClass().getSimpleName());
	}

	protected void addHandleChangeCallback() {
		addFunction("handleChange", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				System.out.println(PaperTextArea.class.getSimpleName() + "#handleChange: " + arguments.getString(0) + "#" + arguments.getBoolean(1));
				wrappedField.setValue(arguments.getString(0));
				getState().inputValue = arguments.getString(0);
				// getState().inputInvalid = arguments.getBoolean(1);
			}
		});
	}
}
