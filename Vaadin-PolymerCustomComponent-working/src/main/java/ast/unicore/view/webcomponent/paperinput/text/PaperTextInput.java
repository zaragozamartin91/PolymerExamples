package ast.unicore.view.webcomponent.paperinput.text;

import java.util.concurrent.atomic.AtomicBoolean;

import ast.unicore.view.webcomponent.paperinput.InvalidInputException;
import ast.unicore.view.webcomponent.paperinput.PaperInputState;

import com.vaadin.annotations.JavaScript;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.TextField;

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
public class PaperTextInput extends AbstractJavaScriptComponent {
	private AtomicBoolean autoValidate = new AtomicBoolean(false);
	private TextField wrappedField = new TextField();

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
	}

	/**
	 * Habilita la validacion del campo automatica. La misma se disparara cada vez que el campo cambie de valor. No se
	 * lanzaran excepciones.
	 * 
	 * @return this.
	 */
	public PaperTextInput autoValidate() {
		if (autoValidate.compareAndSet(false, true)) {
			wrappedField.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					try {
						validate();
					} catch (Exception e) {
					}
				}
			});
		}
		return this;
	}

	/**
	 * Agrega un listener que espera cambios de valor en el campo.
	 * 
	 * @param valueChangeListener
	 *            Listener a agregar.
	 * @return this.
	 */
	public PaperTextInput addValueChangeListener(final ValueChangeListener valueChangeListener) {
		wrappedField.addValueChangeListener(valueChangeListener);
		return this;
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
	public PaperTextInput addValidator(final Validator validator) {
		wrappedField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				try {
					validator.validate(value);
				} catch (InvalidValueException e) {
					setErrorMessage(e.getMessage());
					setInputInvalid();
					throw e;
				}
			}
		});

		return this;
	}

	/**
	 * Limpia el campo.
	 */
	public void clear() {
		setValue("");
	}

	protected void addHandleChangeCallback() {
		addFunction("handleChange", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				System.out.println(PaperTextInput.class.getSimpleName() + "#handleChange: " + arguments.getString(0) + "#" + arguments.getBoolean(1));
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

	/**
	 * Establece mensaje de error de validacion del campo.
	 *
	 * @param errMsg
	 *            Mensaje a obtener.
	 */
	protected void setErrorMessage(String errMsg) {
		this.getState().inputErrorMessage = errMsg;
		markAsDirty();
	}

	/**
	 * Marca el input como invalido.
	 */
	protected void setInputInvalid() {
		getState().inputInvalid = true;
		markAsDirty();
	}
}
