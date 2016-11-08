package ast.unicore.view.webcomponent.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class ResponsiveTableState extends JavaScriptComponentState {
	private static final long serialVersionUID = 2679795547270688900L;

	public List<Map<String, String>> columns = new ArrayList<>();

	// public List<Map<String, Object>> rows = new ArrayList<>();
}
