package ast.unicore.view.webcomponent.paperbutton;

import com.vaadin.shared.ui.JavaScriptComponentState;

/**
 * Representa el estado actual de un PaperButton // paper-button.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
public class PaperButtonState extends JavaScriptComponentState {
	public String buttonLabel;
	public Boolean buttonDisabled;
	public String widthToSet = "";
}
