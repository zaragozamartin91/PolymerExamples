package org.vaadin.webcomponent.papervaadcalendar;

import com.vaadin.shared.ui.JavaScriptComponentState;

/**
 * Representa el estado actual de un PaperButton // paper-button.
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
