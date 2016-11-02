package ast.unicore.view.webcomponent.paperinput.text;

import ast.unicore.view.webcomponent.paperinput.InvalidInputException;
import ast.unicore.view.webcomponent.paperinput.PaperInputState;

import com.vaadin.annotations.JavaScript;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.TextField;

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
public class PaperTextArea extends AbstractJavaScriptComponent {
	private TextField wrappedField = new TextField();

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
	 * Agrega un listener que espera cambios de valor en el campo.
	 * 
	 * @param valueChangeListener
	 *            Listener a agregar.
	 * @return this.
	 */
	public void addValueChangeListener(final ValueChangeListener valueChangeListener) {
		wrappedField.addValueChangeListener(valueChangeListener);
	}

	/**
	 * Establece el valor del campo.
	 * 
	 * @param value
	 *            Valor nuevo.
	 */
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
	public String getValue() {
		return getState().inputValue;
	}

	/**
	 * Establece si el Input es requerido.
	 * 
	 * @param isRequired
	 *            True si es requerido, false caso contrario.
	 */
	public void setRequired(boolean isRequired) {
		this.getState().inputRequired = isRequired;
		markAsDirty();
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		super.setEnabled(isEnabled);
		getState().inputDisabled = !isEnabled;
		markAsDirty();
	}

	/**
	 * Deshabilita el componente.
	 */
	public void disable() {
		setEnabled(false);
	}

	/**
	 * Habilita el componente.
	 */
	public void enable() {
		setEnabled(true);
	}

	/**
	 * Valida el valor del campo usando los validators asignados.
	 * 
	 * @throws InvalidInputException
	 *             En caso que el valor del paper input sea invalido segun los validadores asignados.
	 */
	public void validate() {
		wrappedField.validate();
	}

	/**
	 * Agrega un validador del contenido.
	 * 
	 * @param newValidator
	 *            Nuevo validador.
	 * @return this.
	 */
	public void addValidator(final Validator validator) {
		wrappedField.addValidator(validator);
	}

	/**
	 * Limpia el campo y lo restaura a su estado original.
	 */
	public void clear() {
		setValue("");
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

	@Override
	protected PaperInputState getState() {
		return (PaperInputState) super.getState();
	}
}
