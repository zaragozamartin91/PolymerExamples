package ast.unicore.view.webcomponent.paperinput.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ast.unicore.view.webcomponent.paperinput.AbstractPaperInput;

import com.vaadin.annotations.JavaScript;
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
public class PaperDateInput extends AbstractPaperInput<Date> {
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static Date asDate(String str) throws ParseException {
		return DATE_FORMAT.parse(str);
	}

	public static String asString(Date date) {
		return DATE_FORMAT.format(date);
	}

	/**
	 * Crea una nueva instancia con un label.
	 * 
	 * @param label
	 *            Label del campo.
	 */
	public PaperDateInput(String label) {
		getState().inputValue = "";
		getState().inputLabel = label;

		addHandleChangeCallback();
	}

	/**
	 * Establece el valor del campo.
	 * 
	 * @param value
	 *            Valor nuevo.
	 */
	@Override
	public void setValue(Date value) {
		String dateString = asString(value);
		getState().inputValue = dateString;
		markAsDirty();
	}

	/**
	 * Obtiene el valor del campo.
	 * 
	 * @return Valor del campo.
	 */
	@Override
	public Date getValue() {
		String strValue = getState().inputValue;
		try {
			return asDate(strValue);
		} catch (ParseException e) {
			throw new IllegalStateException("Valor " + strValue + " no corresponde con una fecha valida", e);
		}
	}

	@Override
	public void setWidth(String width) {
		super.setWidth(width);
		getState().inputWidth = width;
		markAsDirty();
	}

	@Override
	protected void addHandleChangeCallback() {
		addFunction("handleChange", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				// System.out.println(PaperDateInput.class.getSimpleName() + "#handleChange: " + arguments.getString(0));
				wrappedField.setValue(arguments.getString(0));
				getState().inputValue = arguments.getString(0);
			}
		});
	}

	/**
	 * Limpia el campo y lo restaura a su estado original.
	 */
	@Override
	public void clear() {
		setValue(Calendar.getInstance().getTime());
		setInputValid();
	}
}
