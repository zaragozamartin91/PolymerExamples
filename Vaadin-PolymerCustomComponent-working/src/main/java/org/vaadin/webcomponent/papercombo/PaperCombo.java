package org.vaadin.webcomponent.papercombo;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

/**
 * Componente de vaadin del lado del servidor representante del componente paper-dropdown-menu de polymer.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "paper-combo-connector.js" })
public final class PaperCombo extends AbstractJavaScriptComponent {

	/**
	 * Crea un nuevo PaperCombo con un label.
	 * 
	 */
	public PaperCombo(String label,boolean dropRequired) {
		getState().dropLabel = label;
		getState().dropRequired = dropRequired;
		
		addHandleSelectedCallback();
	}

	@Override
	protected PaperComboState getState() {
		return (PaperComboState) super.getState();
	}
	
	public void addItem(String caption){
		callFunction("addItem", caption);
	};

	private void addHandleSelectedCallback() {
		addFunction("handleSelected", new JavaScriptFunction() {
			public void call(JsonArray arguments) {
				System.out.println("calling PaperCombo#handleSelected with: " + arguments.getString(0));
				getState().selectedLabel = arguments.getString(0);
			}
		});
	}
}
