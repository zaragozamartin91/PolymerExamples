package ast.unicore.view.webcomponent.paperbutton;

import com.vaadin.shared.ui.JavaScriptComponentState;

/**
 * Representa el estado de un PaperButton // paper-button.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
public class PaperButtonState extends JavaScriptComponentState {
	public String buttonLabel;
	public Boolean buttonDisabled;
	public String widthToSet = "";

	/* LAS SIGUIENTES PROPIEDADES SE AGREGAN PARA MODIFICAR EL ESTILO DEL BOTON. ESTAS PROPIEDADES CORRESPONDEN AL class DEL <button> */
	public String buttonStyle = "btn waves-effect waves-light white";
}
