package ast.unicore.view.webcomponent.papervaadcalendar.dialog;

import ast.unicore.view.webcomponent.papervaadcalendar.PaperVaadCalendarState;

/**
 * Representa el estado actual de un paper-dialog-calendar.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
public class PaperDialogCalendarState extends PaperVaadCalendarState {
	public String inputLabel;
	public String inputValue;
	public String inputErrorMessage;
//	public String buttonLabel;
	public boolean ddmmyyyy;
	public boolean allDisabled;
}
