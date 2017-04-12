package ast.unicore.view.webcomponent.papercombo;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class PaperComboState extends JavaScriptComponentState {
	private static final long serialVersionUID = 8687943111324023873L;

	public String selectedLabel;
	public String dropLabel;
	public boolean dropRequired;
	public boolean dropDisabled;
	public String[] captions;
	public String dropdownContentHeight = "300px";
	/**
	 * Booleano que determina si el drop debe mostrarse en modo compacto (es decir, acompañado de un scroll).
	 */
	public boolean compactDrop = false;

	/**
	 * Determina si el combo debe permitir selecciones multiples.
	 */
	public boolean multiple = false;
}
