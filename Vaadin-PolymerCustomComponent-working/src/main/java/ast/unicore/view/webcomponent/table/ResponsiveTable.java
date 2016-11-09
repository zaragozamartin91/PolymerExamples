package ast.unicore.view.webcomponent.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;
import elemental.json.JsonObject;

/**
 * Tabla responsiva desarrollada en HTML5 + Polymer.
 * 
 * @author martin.zaragoza
 *
 */
@JavaScript({ "responsive-table-connector.js" })
public class ResponsiveTable extends AbstractJavaScriptComponent {
	private static final long serialVersionUID = 6202396936779824718L;

	private List<ClickListener> clickListeners = new ArrayList<>();

	/**
	 * Crea una nueva tabla a partir de un conjunto de columnas de tipo {@link String}, {@link Column} o
	 * {@link IconColumn}.
	 * 
	 * @param columns
	 *            Columnas de la tabla.
	 */
	public ResponsiveTable(Object... columns) {
		List<Map<String, String>> columnList = new ArrayList<>();

		for (Object col : columns) {
			Column column = parseColumn(col);
			columnList.add(column.toMap());
		}

		init(columnList);
	}

	/**
	 * Agrega una fila a partir de un mapa de valores. Las claves deben coincidir con los nombres de las columnas.
	 * 
	 * @param rowMap
	 *            Valores de fila a agregar.
	 */
	public void addRow(Map<String, Object> rowMap) {
		Set<Entry<String, Object>> rowEntries = rowMap.entrySet();
		for (Entry<String, Object> rowEntry : rowEntries) {
			rowEntry.setValue(rowEntry.getValue() == null ? "" : rowEntry.getValue());
		}

		getState().rows.add(rowMap);
		markAsDirty();
	}

	/**
	 * Agrega una fila a partir de un conjunto de valores. La cantidad de valores pasados como parametro DEBE SER IGUAL
	 * a la cantidad de columnas de la tabla. Si la tabla tiene columnas de tipo ICONO -> pasar valores vacios como ""
	 * 
	 * @param values
	 */
	public void addRow(Object... values) {
		Map<String, Object> row;
		if (getState().columns.size() == values.length) {
			row = buildRow(values);
		} else {
			throw new RuntimeException("Valores ingresados incorrectos!");
		}

		addRow(row);
	}

	/**
	 * Listener de eventos de click sobre el icono de una fila de la tabla responsiva.
	 * 
	 * @author martin.zaragoza
	 *
	 */
	public static interface ClickListener {
		/**
		 * Accion a realizar al clickear un icono de la tabla.
		 * 
		 * @param column
		 *            Columna que fue clickeada.
		 * @param row
		 *            Fila que fue clickeada. Los valores de la fila son accedidos como si fuese un mapa donde las
		 *            claves son los nombres de las columnas.
		 */
		public void iconClick(Column column, JsonObject row);
	}

	/**
	 * Agrega un listener de eventos de click sobre un icono de la tabla.
	 * 
	 * @param clickListener
	 *            Listener a agregar.
	 */
	public void addClickListener(ClickListener clickListener) {
		clickListeners.add(clickListener);
	}

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
				JsonObject eventDetail = arguments.getObject(0);
				for (ClickListener clickListener : clickListeners) {
					Column column = Column.fromJsonObject(eventDetail.getObject("column"));
					clickListener.iconClick(column, eventDetail.getObject("row"));
				}
			}
		});
	}
}
