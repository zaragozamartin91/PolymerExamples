package ast.unicore.view.webcomponent.papervaadcalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

/**
 * Componente de vaadin del lado del servidor representante del componente paper-vaad-calendar.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "paper-vaad-calendar-connector.js" })
public class PaperVaadCalendar extends AbstractJavaScriptComponent {
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd");

	private Date markedDate;

	public PaperVaadCalendar() {
		addHandleKickCallback();
	}

	@Override
	protected PaperVaadCalendarState getState() {
		return (PaperVaadCalendarState) super.getState();
	}

	/**
	 * Establece la fecha del calendario.
	 * 
	 * @param date
	 *            - Fecha a setear.
	 * @return this.
	 */
	public PaperVaadCalendar date(Date date) {
		this.markedDate = date;
		getState().pickerDate = DATE_FORMATTER.format(this.markedDate);
		return this;
	}

	/**
	 * Establece la fecha como un String. El formato de fecha esperado es yyyy/MM/dd.
	 * 
	 * @param stringDate
	 *            - Fecha con formato String.
	 * @return this.
	 * @throws ParseException
	 *             En caso que el formato sea invalido.
	 */
	public PaperVaadCalendar date(String stringDate) throws ParseException {
		Date parsedDate = DATE_FORMATTER.parse(stringDate);
		return this.date(parsedDate);
	}

	/**
	 * Obtiene la fecha marcada en el calendario.
	 * 
	 * @return - fecha marcada en el calendario.
	 */
	public Date date() throws ParseException {
		return markedDate;
	};

	/**
	 * Establece si el componente debe verse de forma vertical constantemente o no.
	 * 
	 * @param narrow
	 *            - True: el componente debe verse de forma Vertical constantemente. False: si la pantalla es grande ->
	 *            el componente debe apaisarse.
	 * @return this.
	 */
	public PaperVaadCalendar narrow(boolean narrow) {
		getState().pickerNarrow = narrow;
		return this;
	}

	/**
	 * Fuerza a que el componente siempre se vea de forma vertical. Sinonimo de
	 * {@link PaperVaadCalendar#narrow(boolean)} con argumento true.
	 * 
	 * @return this.
	 */
	public PaperVaadCalendar narrow() {
		return narrow(true);
	}

	/**
	 * Establece el locale del formato de fecha a MOSTRAR en el componente. Ej: "es" para espanol, "en" para ingles,
	 * etc.
	 * 
	 * @param locale
	 *            - locale del formato de fecha a MOSTRAR en el componente.
	 * @return this.
	 */
	public PaperVaadCalendar locale(String locale) {
		getState().pickerLocale = locale;
		return this;
	}

	/**
	 * Establece el formato del encabezado del calendario. Ej: "dd D MMM" indica que la fecha de display del componente
	 * es "Ju 2 Dic" para el Jueves 2 de Diciembre.
	 * 
	 * @param headingFormat
	 *            - Formato de encabezado de display del componente paper-vaad-calendar.
	 * @return this.
	 */
	public PaperVaadCalendar headingFormat(String headingFormat) {
		getState().pickerHeadingFormat = headingFormat;
		return this;
	}

	/**
	 * Establece la fecha minima posible a seleccionar. Acepta String o Date.
	 * 
	 * @param date
	 *            - Date o String(yyyy/MM/dd) fecha.
	 * @return this.
	 */
	public PaperVaadCalendar minDate(Object date) {
		if (date.getClass().equals(Date.class)) {
			getState().pickerMinDate = DATE_FORMATTER.format((Date) date);
		} else {
			getState().pickerMinDate = date.toString();
		}

		return this;
	}

	/**
	 * Establece la fecha maxima posible a seleccionar. Acepta String o Date.
	 * 
	 * @param date
	 *            - Date o String(yyyy/MM/dd) fecha.
	 * @return this.
	 */
	public PaperVaadCalendar maxDate(Object date) {
		if (date.getClass().equals(Date.class)) {
			getState().pickerMaxDate = DATE_FORMATTER.format((Date) date);
		} else {
			getState().pickerMaxDate = date.toString();
		}

		return this;
	}

	/**
	 * Establece el ancho maximo de pantalla en pixeles para el cual el componente se muestra en forma vertical.
	 * 
	 * @param pixels
	 *            - ancho maximo de pantalla en pixeles.
	 * @return this.
	 */
	public PaperVaadCalendar responsiveWidth(Integer pixels) {
		this.getState().pickerResponsiveWidth = pixels + "px";
		return this;
	};

	private void addHandleKickCallback() {
		addFunction("handleKick", new JavaScriptFunction() {
			public void call(JsonArray arguments) {
				System.out.println("calling PaperVaadCalendar#handleKick");
				String stringDate = arguments.getString(0);
				System.out.println("with: " + stringDate);

				try {
					markedDate = DATE_FORMATTER.parse(stringDate);
					System.out.println("date: " + markedDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
