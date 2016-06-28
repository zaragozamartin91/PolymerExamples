package org.vaadin.webcomponent.paperinput;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.webcomponent.paperinput.InputValidator.InvalidInputException;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

/**
 * Componente de vaadin del lado del servidor representante del componente paper-input de polymer.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "paper-input-connector.js" })
public final class PaperInput extends AbstractJavaScriptComponent {
	private List<InputValidator> validators = new ArrayList<>();
	private boolean defaultValidatorEnabled = false;

	/**
	 * Crea un nuevo PaperInput con un label.
	 * 
	 * @param label
	 *            - Label del campo.
	 */
	public PaperInput(String label) {
		getState().inputValue = "";
		getState().inputLabel = label;

		addHandleChangeCallback();
	}

	@Override
	protected PaperInputState getState() {
		return (PaperInputState) super.getState();
	}

	/**
	 * Establece el valor del campo.
	 * 
	 * @param value
	 *            - Valor a setear.
	 */
	public void setValue(String value) {
		getState().inputValue = value;
		markAsDirty();
	}

	/**
	 * Obtiene el valor del campo.
	 * 
	 * @return - Valor del campo.
	 */
	public String getValue() {
		return getState().inputValue;
	}

	/**
	 * Establece si el Input es requerido.
	 * 
	 * @param isRequired
	 *            - True si es requerido, false caso contrario.
	 */
	public void setRequired(boolean isRequired) {
		this.getState().inputRequired = isRequired;
		markAsDirty();
	}

	/**
	 * Establece mensaje de error de validacion del campo.
	 * 
	 * @param errMsg
	 *            - Mensaje a obtener.
	 */
	public void setErrorMessage(String errMsg) {
		this.getState().inputErrorMessage = errMsg;
		markAsDirty();
	}

	/**
	 * Establece el patron de validacion del campo como una expresion regular. Si el contenido del input no cumple con
	 * la expresion regular, se lo marcara como invalido.
	 * 
	 * @param pattern
	 *            - Expresion regular de validacion del campo. Ej: [A-Za-z]+ es expresion regular de caracteres
	 *            alfabeticos SIN espacios ni numeros.
	 */
	public void setPattern(String pattern) {
		this.getState().inputPattern = pattern;
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
	 */
	public void validate() throws InvalidInputException {
		boolean inputRequired = getState().inputRequired;
		String inputValue = getValue() == null ? "" : getValue();
		boolean inputInvalid = getState().inputInvalid || (inputRequired && inputValue.isEmpty());

		for (InputValidator inputValidator : validators) {
			inputValidator.validate(inputValue, inputInvalid);
		}
	}

	/**
	 * Agrega un validador del contenido.
	 * 
	 * @param newValidator
	 *            - Nuevo validador.
	 * @return
	 */
	public PaperInput addValidator(InputValidator newValidator) {
		this.validators.add(newValidator);
		return this;
	}

	/**
	 * Habilita la validacion por defecto del contenido del componente cliente.
	 */
	public void enableDefaultClientComponentValidator() {
		if (defaultValidatorEnabled) {
			return;
		}

		this.defaultValidatorEnabled = true;

		this.addValidator(new InputValidator() {
			public void validate(String inputValue, boolean clientComponentInputIsInvalid) throws InvalidInputException {
				if (clientComponentInputIsInvalid) {
					throw new InvalidInputException(getState().inputErrorMessage);
				}
			}
		});
	}

	private void addHandleChangeCallback() {
		addFunction("handleChange", new JavaScriptFunction() {
			public void call(JsonArray arguments) {
				System.out.println("calling PaperInput#handleChange with: " + arguments.getString(0) + "#" + arguments.getBoolean(1));
				getState().inputValue = arguments.getString(0);
				getState().inputInvalid = arguments.getBoolean(1);
			}
		});
	}
}
