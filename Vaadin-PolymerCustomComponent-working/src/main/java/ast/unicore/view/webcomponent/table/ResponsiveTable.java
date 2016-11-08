package ast.unicore.view.webcomponent.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ast.unicore.view.webcomponent.papercombo.PaperCombo;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

@JavaScript({ "responsive-table-connector.js" })
public class ResponsiveTable extends AbstractJavaScriptComponent {
	private static final long serialVersionUID = 6202396936779824718L;

	@Override
	protected ResponsiveTableState getState() {
		return (ResponsiveTableState) super.getState();
	}

	private Column parseColumn(Object column) {
		if (column instanceof String) {
			return new Column(column.toString());
		} else {
			return (Column) column;
		}

	}

	public ResponsiveTable(Object... columns) {
		List<Map<String, String>> columnList = new ArrayList<>();

		for (Object column : columns) {
			if (column instanceof String) {
				columnList.add(new Column(column.toString()).asMap());
			} else {
				columnList.add(((Column) column).asMap());
			}
		}

		init(columnList);
	}

	protected void init(List<Map<String, String>> columns) {
		getState().columns = columns;
		addHandleClickCallback();
	}

	// public void addColumn(Object col) {
	// Column column = parseColumn(col);
	// this.getState().columns.add(column.asMap());
	// markAsDirty();
	// }

	@SuppressWarnings("serial")
	private void addHandleClickCallback() {
		addFunction("handleIconClick", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				System.out.println(PaperCombo.class.getSimpleName() + "#handleIconClick: ");
			}
		});
	}
}
