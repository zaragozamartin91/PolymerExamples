package ast.unicore.view.webcomponent.papervaadcalendar.dialog;

import java.text.ParseException;
import java.util.Date;

import ast.unicore.view.webcomponent.papervaadcalendar.PaperVaadCalendar;

import com.vaadin.annotations.JavaScript;

/**
 * Componente de vaadin del lado del servidor representante del componente paper-dialog-calendar.
 * Calendario con paper-input y boton que despliega un paper-vaad-calendar dentro de un paper-dialog.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "paper-dialog-calendar-connector.js" })
public final class PaperDialogCalendar extends PaperVaadCalendar {
	public PaperDialogCalendar() {
		super();
	}
	
	public PaperDialogCalendar(String inputLabel) {
		super();
		getState().inputLabel = inputLabel;
	}

	@Override
	protected PaperDialogCalendarState getState() {
		return (PaperDialogCalendarState) super.getState();
	}
	
	/**
	 * Establece el mensaje de error a mostrar en caso de formato de fecha incorrecto.
	 * 
	 * @param errMsg - Mensaje de error.
	 * @return this.
	 */
	public PaperDialogCalendar errorMessage(String errMsg) {
		getState().errorMessage = errMsg;
		markAsDirty();
		return this;
	}
	
	/**
	 * Label del paper-input sobre el cual se introduce la fecha.
	 * 
	 * @param label - Label.
	 * @return this.
	 */
	public PaperDialogCalendar inputLabel(String label) {
		getState().inputLabel = label;
		markAsDirty();
		return this;
	}
	
	/**
	 * Indica que el input debe manejar fechas con formato dd/MM/yyyy en vez de yyyy/MM/dd.
	 * 
	 * @return this.
	 */
	public PaperDialogCalendar ddmmyyyy(boolean ddmmyyyy) {
		getState().ddmmyyyy = ddmmyyyy;
		markAsDirty();
		return this;
	}
	
	@Override
	public void setEnabled(boolean isEnabled) {
		super.setEnabled(isEnabled);
		this.getState().allDisabled = !isEnabled;
		markAsDirty();
	}

	@Override
	public PaperDialogCalendar date(Date date) {
		return (PaperDialogCalendar) super.date(date);
	}

	@Override
	public PaperDialogCalendar date(String stringDate) throws ParseException {
		return (PaperDialogCalendar) super.date(stringDate);
	}

	@Override
	public Date date() throws ParseException {
		return super.date();
	}

	@Override
	public PaperDialogCalendar narrow(boolean narrow) {
		return (PaperDialogCalendar) super.narrow(narrow);
	}

	@Override
	public PaperDialogCalendar narrow() {
		return (PaperDialogCalendar) super.narrow();
	}

	@Override
	public PaperDialogCalendar locale(String locale) {
		return (PaperDialogCalendar) super.locale(locale);
	}

	@Override
	public PaperDialogCalendar headingFormat(String headingFormat) {
		return (PaperDialogCalendar) super.headingFormat(headingFormat);
	}

	@Override
	public PaperDialogCalendar minDate(Object date) {
		return (PaperDialogCalendar) super.minDate(date);
	}

	@Override
	public PaperDialogCalendar maxDate(Object date) {
		return (PaperDialogCalendar) super.maxDate(date);
	}

	@Override
	public PaperDialogCalendar responsiveWidth(Integer pixels) {
		return (PaperDialogCalendar) super.responsiveWidth(pixels);
	}	
}
