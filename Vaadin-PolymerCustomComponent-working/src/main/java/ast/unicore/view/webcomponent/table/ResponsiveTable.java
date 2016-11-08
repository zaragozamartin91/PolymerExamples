package ast.unicore.view.webcomponent.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		for (Object col : columns) {
			Column column = parseColumn(col);
			columnList.add(column.toMap());
		}

		init(columnList);
	}

	@SuppressWarnings("unchecked")
	public void addRow(Object... values) {
		Map<String, Object> row;
		if (values.length == 1 && values[0] instanceof Map) {
			row = (Map<String, Object>) values[0];
			if (row.keySet().size() != getState().columns.size()) {
				throw new RuntimeException("Valores ingresados incorrectos!");
			}
		} else if (getState().columns.size() == values.length) {
			row = buildRow(values);
		} else {
			throw new RuntimeException("Valores ingresados incorrectos!");
		}

		getState().rows.add(row);
		markAsDirty();
	}

	protected Map<String, Object> buildRow(Object... values) {
		Map<String, Object> row = new HashMap<>();
		int i = 0;
		for (Map<String, String> column : getState().columns) {
			row.put(Column.fromMap(column).name, values[i++]);
		}
		return row;
	}

	protected void init(List<Map<String, String>> columns) {
		getState().columns = columns;
		addHandleClickCallback();
	}

	@SuppressWarnings("serial")
	private void addHandleClickCallback() {
		addFunction("handleIconClick", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				System.out.println(ResponsiveTable.class.getSimpleName() + "#handleIconClick: ");
			}
		});
	}
}
