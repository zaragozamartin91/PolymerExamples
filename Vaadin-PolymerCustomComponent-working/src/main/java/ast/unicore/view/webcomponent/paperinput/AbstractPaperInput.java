package ast.unicore.view.webcomponent.paperinput;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.TextField;

import elemental.json.JsonArray;

public abstract class AbstractPaperInput<InputType> extends AbstractJavaScriptComponent {
	private static final long serialVersionUID = 7395010143963838186L;

	protected AtomicBoolean autoValidate = new AtomicBoolean(false);
	protected TextField wrappedField = new TextField();

	/**
	 * Habilita la validacion del campo automatica. La misma se disparara cada vez que el campo cambie de valor. No se lanzaran excepciones.
	 * 
	 * @return this.
	 */
	@SuppressWarnings("serial")
	public void autoValidate() {
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
	 * Agrega un listener de eventos de foco.
	 * 
	 * @param listener
	 *            listener de eventos de foco.
	 */
	public void addFocusListener(FocusListener listener) {
		wrappedField.addFocusListener(listener);
	}

	/**
	 * Establece el valor del campo.
	 * 
	 * @param value
	 *            Valor nuevo.
	 */
	public abstract void setValue(InputType value);

	/**
	 * Obtiene el valor del campo.
	 * 
	 * @return Valor del campo.
	 */
	public abstract InputType getValue();

	/**
	 * Establece si el Input es requerido.
	 * 
	 * @param isRequired
	 *            True si es requerido, false caso contrario.
	 */
	public void setRequired(boolean isRequired, String... errorMessage) {
		this.getState().inputRequired = isRequired;
		if (errorMessage != null && errorMessage.length > 0) {
			setErrorMessage(errorMessage[0]);
		}

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
	@SuppressWarnings("serial")
	public void addValidator(final Validator validator) {
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
	}

	/**
	 * Limpia el campo y lo restaura a su estado original.
	 */
	public abstract void clear();

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

	/**
	 * Marca el input como invalido.
	 */
	protected void setInputValid() {
		getState().inputInvalid = false;
		markAsDirty();
	}

	@SuppressWarnings("serial")
	protected void addHandleChangeCallback() {
		final Class<?> clazz = this.getClass();
		addFunction("handleChange", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				System.out.println(clazz.getSimpleName() + "#handleChange: " + arguments.getString(0) + "#" + arguments.getBoolean(1));
				wrappedField.setValue(arguments.getString(0));
				getState().inputValue = arguments.getString(0);
				// getState().inputInvalid = arguments.getBoolean(1);
			}
		});
	}

	@SuppressWarnings({ "unchecked", "serial" })
	protected void addHandleFocusCallback() {
		final Class<?> clazz = this.getClass();
		addFunction("handleFocus", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				System.out.println(clazz.getSimpleName() + "#handleFocus: " + arguments.getString(0));
				Collection<FocusListener> listeners = (Collection<FocusListener>) wrappedField.getListeners(FocusEvent.class);
				for (FocusListener listener : listeners) {
					listener.focus(new FocusEvent(wrappedField));
				}

			}
		});
	}
}
