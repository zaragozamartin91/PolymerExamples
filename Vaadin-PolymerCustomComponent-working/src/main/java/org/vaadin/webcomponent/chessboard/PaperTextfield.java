package org.vaadin.webcomponent.chessboard;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

/**
 * Componente de vaadin del lado del servidor representante del componente input-button de polymer.
 * 
 * El archivo de javascript vinculado debe encontrarse en org/vaadin/webcomponent/chessboard/vaadinintegration.js.
 * 
 * @author martin.zaragoza
 *
 */
@JavaScript({ "paper-textfield-connector.js" })
public final class PaperTextfield extends AbstractJavaScriptComponent {
	private StateAction<PaperTextfieldState> stateAction;

	public static PaperTextfield newWithLabel(String label) {
		PaperTextfield paperTextfield = new PaperTextfield();
		paperTextfield.callFunction("newWithLabel", label);

		return paperTextfield;
	}

	public void setValue(String value) {
		getState().value = value;
	}

	public String getValue() {
		System.out.println("Calling change...");
		callFunction("change");
		System.out.println("returning getState().value:");
		return getState().value;
	}

	public void setRequired(boolean isRequired) {
		callFunction("setRequired", isRequired);
	}

	public void setPattern(String pattern) {
		callFunction("setPattern", pattern);
	}

	@Override
	protected PaperTextfieldState getState() {
		return (PaperTextfieldState) super.getState();
	}

	public synchronized void withStatePerform(StateAction<PaperTextfieldState> stateAction) {
		this.stateAction = stateAction;
		callFunction("change");
	}

	public PaperTextfield() {
		addFunction("doUpdate", new JavaScriptFunction() {
			@Override
			public synchronized void call(JsonArray arguments) {
				String newValue = arguments.getString(0);
				System.out.println("Calling doUpdate with: " + newValue);
				getState().value = newValue;

				stateAction.run(getState());
			}
		});
	}

	// public void change() {
	// callFunction("change");
	// }
}
