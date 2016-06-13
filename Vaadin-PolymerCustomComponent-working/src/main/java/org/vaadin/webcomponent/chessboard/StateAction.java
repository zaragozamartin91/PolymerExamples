package org.vaadin.webcomponent.chessboard;

import com.vaadin.shared.ui.JavaScriptComponentState;

public interface StateAction<E extends JavaScriptComponentState> {
	public void run(E state);
}
