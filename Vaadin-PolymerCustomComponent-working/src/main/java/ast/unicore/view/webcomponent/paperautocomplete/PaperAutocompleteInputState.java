package ast.unicore.view.webcomponent.paperautocomplete;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class PaperAutocompleteInputState extends JavaScriptComponentState {
	private static final long serialVersionUID = -6254329724659488442L;

	public String inputPlaceholder;
	public String inputValue;
	public boolean inputRequired;
	public boolean disabled;
	public String localCandidates;
}
