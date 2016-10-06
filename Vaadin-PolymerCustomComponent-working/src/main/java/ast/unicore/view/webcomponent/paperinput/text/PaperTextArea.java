package ast.unicore.view.webcomponent.paperinput.text;

import java.util.ArrayList;
import java.util.List;

import ast.unicore.view.webcomponent.paperinput.InputValidator;
import ast.unicore.view.webcomponent.paperinput.InvalidInputException;
import ast.unicore.view.webcomponent.paperinput.PaperInputState;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
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
public class PaperTextArea extends AbstractJavaScriptComponent {
	private List<InputValidator<String>> validators = new ArrayList<>();
	private boolean defaultValidatorEnabled = false;

	/**
	 * Crea un nuevo PaperInput con un label.
	 * 
	 * @param label
	 *            Label del campo.
	 */
	public PaperTextArea(String label) {
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
	 *            Valor nuevo.
	 */
	public void setValue(String value) {
		getState().inputValue = value;
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

	/**
	 * Establece mensaje de error de validacion del campo.
	 * 
	 * @param errMsg
	 *            Mensaje a obtener.
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
	 *            Expresion regular de validacion del campo. Ej: [A-Za-z]+ es expresion regular de caracteres
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
	 *             En caso que el valor del paper input sea invalido segun los validadores asignados.
	 */
	public void validate() throws InvalidInputException {
		boolean inputRequired = getState().inputRequired;
		String inputValue = getValue() == null ? "" : getValue();
		boolean inputInvalid = getState().inputInvalid || (inputRequired && inputValue.isEmpty());

		for (InputValidator<String> inputValidator : validators) {
			inputValidator.validate(inputValue, inputInvalid);
		}
	}

	/**
	 * Agrega un validador del contenido.
	 * 
	 * @param newValidator
	 *            Nuevo validador.
	 * @return this.
	 */
	public PaperTextArea addValidator(InputValidator<String> newValidator) {
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

		this.addValidator(new InputValidator<String>() {
			@Override
			public void validate(String inputValue, boolean clientComponentInputIsInvalid) throws InvalidInputException {
				if (clientComponentInputIsInvalid) {
					throw new InvalidInputException(getState().inputErrorMessage);
				}
			}
		});
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
				System.out.println(PaperTextArea.class.getSimpleName() + "#handleChange: " + arguments.getString(0) + "#" + arguments.getBoolean(1));
				getState().inputValue = arguments.getString(0);
				getState().inputInvalid = arguments.getBoolean(1);
			}
		});
	}
}
