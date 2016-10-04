package ast.unicore.view.webcomponent.papervaadcalendar;

import com.vaadin.shared.ui.JavaScriptComponentState;

/**
 * Representa el estado actual de un paper-vaad-calendar.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
public class PaperVaadCalendarState extends JavaScriptComponentState {
	public String pickerDate;
	public boolean pickerNarrow;			
	public String pickerLocale;
	public String pickerHeadingFormat;		
	public String pickerMaxDate;			
	public String pickerMinDate;			
	public String pickerResponsiveWidth;	
}
