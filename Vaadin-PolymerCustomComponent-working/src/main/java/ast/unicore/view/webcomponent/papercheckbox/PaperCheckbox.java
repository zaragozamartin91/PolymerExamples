package ast.unicore.view.webcomponent.papercheckbox;

import com.vaadin.annotations.JavaScript;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

/**
 * Representa un checkbox de polymer.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "paper-checkbox-connector.js" })
public class PaperCheckbox extends AbstractJavaScriptComponent {
	CheckBox wrappedCheckBox = new CheckBox();

	/**
	 * Nuevo checkbox con label determinado y valor inicial false.
	 * 
	 * @param label
	 *            Label a establecer.
	 */
	public PaperCheckbox(String label) {
		this(label, false);
	}

	/**
	 * Nuevo checkbox con label y valor inicial determinados.
	 * 
	 * @param label
	 *            Label del checkbox.
	 * @param value
	 *            Valor inicial.
	 * @param listeners
	 *            Listeners de cambio de valor.
	 */
	public PaperCheckbox(String label, boolean value, ValueChangeListener... listeners) {
		getState().label = label;
		getState().checked = value;
		wrappedCheckBox.setValue(value);

		wrappedCheckBox.setImmediate(true);
		addHandleChangeCallback();
		for (ValueChangeListener listener : listeners) {
			addValueChangeListener(listener);
		}
	}

	/**
	 * Listener de cambio de valor.
	 * 
	 * @author martin.zaragoza
	 *
	 */
	public static interface ValueChangeListener {
		/**
		 * Accion a realizar cuando el checkbox cambia de valor.
		 * 
		 * @param value
		 *            Valor nuevo del checkbox.
		 */
		void change(boolean value);
	}

	/**
	 * Agrega un listener de cambio de valor.
	 * 
	 * @param valueChangeListener
	 *            Listener de cambio de valor.
	 * @return this.
	 */
	public PaperCheckbox addValueChangeListener(final ValueChangeListener valueChangeListener) {
		wrappedCheckBox.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				valueChangeListener.change(wrappedCheckBox.getValue());
			}
		});
		return this;
	}

	/**
	 * Valor actual del checkbox.
	 * 
	 * @return Valor actual del checkbox.
	 */
	public boolean getValue() {
		return wrappedCheckBox.getValue();
	}

	/**
	 * Establece el valor del checkbox.
	 * 
	 * @param value
	 *            valor del checkbox.
	 * @return this.
	 */
	public PaperCheckbox setValue(boolean value) {
		getState().checked = value;
		wrappedCheckBox.setValue(value);
		return this;
	}

	@Override
	protected PaperCheckboxState getState() {
		return (PaperCheckboxState) super.getState();
	}

	protected void addHandleChangeCallback() {
		addFunction("handleChange", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				wrappedCheckBox.setValue(arguments.getBoolean(0));
			}
		});
	}
}
