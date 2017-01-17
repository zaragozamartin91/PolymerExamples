package ast.unicore.view.webcomponent.paperinput.text;

import java.util.Collection;

import ast.unicore.view.webcomponent.paperinput.AbstractPaperInput;
import ast.unicore.view.webcomponent.paperinput.PaperInputState;

import com.vaadin.annotations.JavaScript;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

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

	/**
	 * Limpia el campo y lo restaura a su estado original.
	 */
	@Override
	public void clear() {
		setValue("");
		setInputValid();
	}

	/**
	 * Establece el tipo de input como password.
	 */
	public void setPasswordType() {
		this.getState().inputType = "password";
		markAsDirty();
	}

	/**
	 * Establece el tipo de input como number.
	 */
	public void setNumberType() {
		this.getState().inputType = "number";
		markAsDirty();
	}

	/**
	 * Establece el tipo de input como texto.
	 */
	public void setTextType() {
		this.getState().inputType = "text";
		markAsDirty();
	}

	@SuppressWarnings({ "unchecked" })
	protected void addHandleFocusCallback() {
		// final Class<?> clazz = this.getClass();
		addFunction("handleFocus", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				// System.out.println(clazz.getSimpleName() + "#handleFocus: " + arguments.getString(0));
				Collection<FocusListener> listeners = (Collection<FocusListener>) wrappedField.getListeners(FocusEvent.class);
				for (FocusListener listener : listeners) {
					listener.focus(new FocusEvent(wrappedField));
				}

			}
		});
	}

	@Override
	protected void addHandleChangeCallback() {
		// final Class<?> clazz = this.getClass();
		addFunction("handleChange", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				// System.out.println(clazz.getSimpleName() + "#handleChange: " + arguments.getString(0) + "#" + arguments.getBoolean(1));
				wrappedField.setValue(arguments.getString(0));
				getState().inputValue = arguments.getString(0);
				// getState().inputInvalid = arguments.getBoolean(1);
			}
		});
	}
}
