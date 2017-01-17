package ast.unicore.view.webcomponent.paperinput.text;

import ast.unicore.view.webcomponent.paperinput.AbstractPaperInput;
import ast.unicore.view.webcomponent.paperinput.PaperInputState;
import elemental.json.JsonArray;

import java.util.Collection;

import com.vaadin.annotations.JavaScript;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.JavaScriptFunction;

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

	// /**
	// * Agrega un validador del contenido.
	// *
	// * @param newValidator
	// * Nuevo validador.
	// * @return this.
	// */
	// @Override
	// public void addValidator(final Validator validator) {
	// wrappedField.addValidator(validator);
	// }

	// @Override
	// public void autoValidate() {
	// throw new UnsupportedOperationException("Operacion no soportada por " + getClass().getSimpleName());
	// }

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
}
