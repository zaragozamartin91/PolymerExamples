package ast.unicore.view.webcomponent.imports;

public enum WebImport {
	POLYMER("VAADIN/webcomponents/bower_components/polymer/polymer.html"),
	PAPER_INPUT("VAADIN/webcomponents/bower_components/paper-input/paper-input.html"),
	PAPER_ITEM("VAADIN/webcomponents/bower_components/paper-item/paper-item.html"),
	PAPER_LISTBOX("VAADIN/webcomponents/bower_components/paper-listbox/paper-listbox.html"),
	PAPER_DROPDOWN_MENU("VAADIN/webcomponents/bower_components/paper-dropdown-menu/paper-dropdown-menu.html"),
	PAPER_COMBO("VAADIN/webcomponents/paper-combo.html"),
	PAPER_BUTTON("VAADIN/webcomponents/bower_components/paper-button/paper-button.html"),
	PAPER_STYLES("VAADIN/webcomponents/bower_components/paper-styles/paper-styles.html"),
	PAPER_TEXTAREA("VAADIN/webcomponents/bower_components/paper-input/paper-textarea.html");

	public final String path;

	private WebImport(String path) {
		this.path = path;
	}
}
