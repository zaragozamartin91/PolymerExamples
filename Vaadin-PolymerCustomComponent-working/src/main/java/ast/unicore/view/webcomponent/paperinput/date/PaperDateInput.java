package ast.unicore.view.webcomponent.paperinput.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ast.unicore.view.webcomponent.paperinput.InputValidator;
import ast.unicore.view.webcomponent.paperinput.InvalidInputException;
import ast.unicore.view.webcomponent.paperinput.PaperInputState;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

/**
 * Input de fecha.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "paper-date-input-connector.js" })
public class PaperDateInput extends AbstractJavaScriptComponent {
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private List<InputValidator<Date>> validators = new ArrayList<>();

	/**
	 * Crea un nuevo PaperInputDate con un label.
	 * 
	 * @param label
	 *            Label del campo.
	 */
	public PaperDateInput(String label) {
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
	public void setValue(Date value) {
		String dateString = DATE_FORMAT.format(value);
		getState().inputValue = dateString;
		markAsDirty();
	}

	/**
	 * Obtiene el valor del campo.
	 * 
	 * @return Valor del campo.
	 */
	public Date getValue() {
		String strValue = getState().inputValue;
		try {
			return DATE_FORMAT.parse(strValue);
		} catch (ParseException e) {
			throw new IllegalStateException("Valor " + strValue + " no corresponde con una fecha valida", e);
		}
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
	 * Retorna true si el input es valido, false en caso contrario.
	 * 
	 * @return true si el input es valido, false en caso contrario.
	 */
	public boolean isValid() {
		try {
			validate();
			return true;
		} catch (InvalidInputException e) {
			return false;
		}
	}

	/**
	 * Valida el valor del campo usando los validators asignados.
	 * 
	 * @throws InvalidInputException
	 *             En caso que el valor del paper input sea invalido segun los validadores asignados.
	 */
	public void validate() throws InvalidInputException {
		Date inputValue = getValue();
		boolean inputInvalid = getState().inputInvalid;

		if (inputInvalid) {
			throw new InvalidInputException("Input del lado del cliente es invalido!");
		}

		for (InputValidator<Date> inputValidator : validators) {
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
	public PaperDateInput addValidator(InputValidator<Date> newValidator) {
		this.validators.add(newValidator);
		return this;
	}

	protected void addHandleChangeCallback() {
		addFunction("handleChange", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				System.out.println(PaperDateInput.class.getSimpleName() + "#handleChange: " + arguments.getString(0));
				getState().inputValue = arguments.getString(0);
				getState().inputInvalid = arguments.getBoolean(1);
			}
		});
	}
}
