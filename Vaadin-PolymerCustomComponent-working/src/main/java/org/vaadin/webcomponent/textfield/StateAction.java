package org.vaadin.webcomponent.textfield;

import com.vaadin.shared.ui.JavaScriptComponentState;

public interface StateAction<E extends JavaScriptComponentState> {
	public void run(E state);
}
