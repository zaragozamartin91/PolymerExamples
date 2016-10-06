package ast.unicore.view.webcomponent.papercombo;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class PaperComboState extends JavaScriptComponentState {
	private static final long serialVersionUID = 8687943111324023873L;

	public String selectedLabel;
	public String dropLabel;
	public boolean dropRequired;
	public boolean dropDisabled;
	public String[] captions;
}
