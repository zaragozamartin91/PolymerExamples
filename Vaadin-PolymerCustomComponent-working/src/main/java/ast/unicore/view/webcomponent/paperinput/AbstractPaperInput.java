package ast.unicore.view.webcomponent.paperinput;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.TextField;

import elemental.json.JsonArray;

public abstract class AbstractPaperInput<InputT> extends AbstractJavaScriptComponent implements Validatable {
	private static final long serialVersionUID = 7395010143963838186L;

	protected AtomicBoolean autoValidate = new AtomicBoolean(false);
	protected TextField wrappedField = new TextField();

	private Validator requiredValidator;

	/**
	 * Habilita la validacion del campo automatica. La misma se disparara cada vez que el campo cambie de valor. No se lanzaran excepciones.
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
						setInputInvalid();
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
	public abstract void setValue(InputT value);

	/**
	 * Obtiene el valor del campo.
	 * 
	 * @return Valor del campo.
	 */
	public abstract InputT getValue();

	/**
	 * Establece si el Input es requerido.
	 * 
	 * @param isRequired
	 *            True si es requerido, false en caso contrario.
	 * @param errorMessage
	 *            [OPCIONAL] Mensaje de error a mostrar luego de validar el campo.
	 */
	public synchronized void setRequired(boolean isRequired, String... errorMessage) {
		final String errMsg = (errorMessage == null || errorMessage.length == 0) ? "" : errorMessage[0];

		if (isRequired) {
			Validator newValidator = new Validator() {
				private static final long serialVersionUID = -1162906328902819970L;

				@Override
				public void validate(Object value) throws InvalidValueException {
					if (value == null || value.toString().isEmpty()) {
						throw new InvalidValueException(errMsg);
					}
				}
			};
			if (requiredValidator == null) {
				this.addValidator(newValidator);
			} else {
				this.removeValidator(requiredValidator);
			}
			requiredValidator = newValidator;
		} else {
			if (requiredValidator != null) {
				// si seteo al campo como NO requerido y YA existe un validador, entonces remuevo el validador existente.
				removeValidator(requiredValidator);
				requiredValidator = null;
			}
		}
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
	@Override
	public void validate() {
		try {
			wrappedField.validate();
			setInputValid();
		} catch (InvalidValueException e) {
			String errMsg = buildSimpleErrorMessage(e);
			setErrorMessage(errMsg);
			setInputInvalid();
			throw e;
		}
	}

	/**
	 * Construye un mensaje de error complejo a partir de todas las causas de error de validacion del campo.
	 * 
	 * @param e
	 *            {@link InvalidInputException} a partir de la cual construir el mensaje de error.
	 * @return mensaje de error complejo a partir de todas las causas de error de validacion del campo.
	 */
	protected String buildFullErrorMessage(InvalidValueException e) {
		if (e.getMessage() == null) {
			StringBuilder errorMessageBuilder = new StringBuilder();

			InvalidValueException[] causes = e.getCauses();
			if (causes == null) {
				return "";
			}

			for (int i = 0; i < causes.length; i++) {
				boolean notLast = (i + 1) < causes.length;
				InvalidValueException cause = causes[i];
				errorMessageBuilder.append(buildFullErrorMessage(cause));
				if (notLast) {
					errorMessageBuilder.append(" & ");
				}
			}

			return errorMessageBuilder.toString();
		} else {
			return e.getMessage();
		}
	}

	/**
	 * Construye un mensaje de error simple teniendo en cuenta unicamente una de las causas de error de validacion del campo.
	 * 
	 * @param e
	 *            {@link InvalidInputException} a partir de la cual construir el mensaje de error.
	 * @return mensaje de error simple teniendo en cuenta unicamente una de las causas de error de validacion del campo.
	 */
	protected String buildSimpleErrorMessage(InvalidValueException e) {
		if (e.getMessage() == null) {
			InvalidValueException[] causes = e.getCauses();
			if (causes == null) {
				return "";
			} else {
				return buildSimpleErrorMessage(causes[0]);
			}
		} else {
			return e.getMessage();
		}
	}

	@Override
	public void addValidator(final Validator validator) {
		wrappedField.addValidator(validator);
	}

	@Override
	public void removeValidator(Validator validator) {
		wrappedField.removeValidator(validator);
	}

	@Override
	public void removeAllValidators() {
		wrappedField.removeAllValidators();
	}

	@Override
	public Collection<Validator> getValidators() {
		return wrappedField.getValidators();
	}

	@Override
	public boolean isValid() {
		return this.getState().inputInvalid == false;
	}

	@Override
	public boolean isInvalidAllowed() {
		return true;
	}

	@Override
	public void setInvalidAllowed(boolean invalidValueAllowed) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("No se permite modificar el comportamiento de validacion del componente");
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
	 * Marca el input como INVALIDO.
	 */
	protected void setInputInvalid() {
		getState().inputInvalid = true;
		markAsDirty();
	}

	/**
	 * Marca el input como VALIDO.
	 */
	protected void setInputValid() {
		getState().inputInvalid = false;
		markAsDirty();
	}

	@SuppressWarnings("serial")
	protected void addHandleChangeCallback() {
		// final Class<?> clazz = this.getClass();
		addFunction("handleChange", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				// System.out.println(clazz.getSimpleName() + "#handleChange: " + arguments.getString(0));
				wrappedField.setValue(arguments.getString(0));
				getState().inputValue = arguments.getString(0);
			}
		});
	}
}
