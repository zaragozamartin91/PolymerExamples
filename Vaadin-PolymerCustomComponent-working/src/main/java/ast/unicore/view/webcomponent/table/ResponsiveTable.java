package ast.unicore.view.webcomponent.table;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import ast.unicore.view.webcomponent.icons.iron.IronIcon;
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
	 * Crea una nueva tabla a partir de un conjunto de columnas de tipo {@link String}, {@link Column} o {@link IconColumn}.
	 * 
	 * @param columns
	 *            Columnas de la tabla.
	 */
	public ResponsiveTable(Object... columns) {
		List<Map<String, Object>> columnList = new ArrayList<>();

		for (Object col : columns) {
			Column column = parseColumn(col);
			columnList.add(column.toMap());
		}

		getState().columns = columnList;
		addHandleClickCallback();
	}

	/**
	 * Agrega una fila a partir de un conjunto de valores. La cantidad de valores pasados como parametro DEBE SER IGUAL a la cantidad de columnas de la tabla.
	 * Si la tabla tiene columnas de tipo NO TEXTO -> pasar valores vacios como "".
	 * 
	 * @param values
	 *            Valores de la fila. Si un valor de la fila corresponde a una columna de tipo NO TEXTO (por ejemplo columna con ICONOS) entonces asignar un
	 *            valor vacio o dummy como "" o "_".
	 */
	public void addRow(Object... values) {
		Map<String, Object> row = buildRow(values);
		getState().rows.add(row);
		markAsDirty();
	}

	/**
	 * Agrega una fila a partir de un mapa de valores. Las claves deben coincidir con los nombres de las columnas.
	 * 
	 * @param rowMap
	 *            Valores de fila a agregar.
	 */
	public void addRow(Map<String, Object> rowMap) {
		Map<String, Object> rowValues = completeRow(rowMap);

		getState().rows.add(rowValues);
		markAsDirty();
	}

	/**
	 * Completa una fila. A partir de un mapa que representa los valores de una fila se retorna un mapa que contiene valores vacios (NO NULOS) para aquellos
	 * valores faltantes en el mapa original de acuerdo a las columnas de la tabla. Ejemplo: <br/><br/>
	 * 
	 * columnas: nombre, edad -> completeRow({edad:25}) == {nombre:"", edad:25}.
	 * 
	 * @param rowMap
	 *            Valores de fila.
	 * @return Fila "completada" con valores vacios.
	 */
	private Map<String, Object> completeRow(Map<String, Object> rowMap) {
		List<Map<String, Object>> columns = getState().columns;
		Map<String, Object> rowValues = new LinkedHashMap<>(rowMap);

		for (Map<String, Object> column : columns) {
			String colName = Column.fromMap(column).name;
			Object rowValue = rowValues.containsKey(colName) ? rowValues.get(colName) : "";
			rowValues.put(colName, rowValue);
		}

		return rowValues;
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
		 *            Fila que fue clickeada. Los valores de la fila son accedidos como si fuese un mapa donde las claves son los nombres de las columnas.
		 * @param rowIndex
		 *            Indice TEMPORAL de la fila en la tabla (El indice es temporal dado que si la tabla se modifica, el valor del indice de la fila puede
		 *            modificarse).
		 * @param icon
		 *            Icono clickeado.
		 * 
		 * @see IronIcon
		 */
		public void iconClick(Column column, Map<String, Object> row, int rowIndex, IronIcon icon);
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

	/**
	 * Remueve todas las filas de la tabla.
	 */
	public void empty() {
		getState().rows = new ArrayList<>();
		markAsDirty();
	}

	/**
	 * Elimina una fila a partir de su indice en la tabla. Las filas se cuentan desde el 0.
	 * 
	 * @param rowIndex
	 *            Indice de fila a remover.
	 * @return Fila eliminada.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             Si el indice de la fila excede a la cantidad de filas de la tabla.
	 */
	public Map<String, Object> removeRow(int rowIndex) {
		if (rowIndex >= getState().rows.size()) {
			throw new IndexOutOfBoundsException("Indice " + rowIndex + " excede la cantidad de filas de la tabla");
		} else {
			Map<String, Object> deleted = getState().rows.remove(rowIndex);
			markAsDirty();

			return wrapRow(deleted);
		}
	}

	/**
	 * Criterio de eliminacion de filas.
	 * 
	 * @author martin.zaragoza
	 *
	 */
	public static interface RemoveCriteria {
		/**
		 * Determina si un criterio de eliminacion aplica.
		 * 
		 * @param rowValues
		 *            Valores de la fila.
		 * @return true si el criterio de eliminacion aplica, false en caso contrario.
		 */
		boolean matches(Map<String, Object> row);
	}

	/**
	 * Elimina filas a partir de un criterio.
	 * 
	 * @param removeCriteria
	 * @return
	 */
	public List<Map<String, Object>> removeRows(RemoveCriteria removeCriteria) {
		List<Map<String, Object>> rows = getState().rows;
		List<Map<String, Object>> deletedRows = new ArrayList<>();
		List<Map<String, Object>> okRows = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			if (removeCriteria.matches(row)) {
				deletedRows.add(row);
			} else {
				okRows.add(row);
			}
		}

		getState().rows = okRows;
		markAsDirty();
		return deletedRows;
	}

	/**
	 * Obtiene una copia de una fila.
	 * 
	 * @param rowIndex
	 *            Indice de fila a obtener.
	 * @return Copia de fila buscada.
	 */
	public Map<String, Object> getRow(int rowIndex) {
		if (rowIndex >= getState().rows.size()) {
			throw new IndexOutOfBoundsException("Indice " + rowIndex + " es igual o mayor a la cantidad de filas de la tabla");
		}
		return wrapRow(getState().rows.get(rowIndex));
	}

	/**
	 * Obtiene la cantidad de filas de la tabla.
	 * 
	 * @return cantidad de filas de la tabla.
	 */
	public int getRowCount() {
		return getState().rows.size();
	}

	/**
	 * Obtiene filas a partir de un valor de columna.
	 * 
	 * @param columnName
	 *            Nombre de columna.
	 * @param value
	 *            Valor esperado.
	 * @return Filas que cumplen con el criterio.
	 */
	public List<Map<String, Object>> getRowsByColumn(String columnName, Object value) {
		if (columnName == null || value == null) {
			throw new NullPointerException("Nombre de columna o valor nulos");
		}

		List<Map<String, Object>> rows = getState().rows;
		List<Map<String, Object>> matchRows = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			if (value.equals(row.get(columnName))) {
				matchRows.add(wrapRow(row));
			}
		}

		return matchRows;
	}

	/**
	 * Establece un valor de celda.
	 * 
	 * @param rowIndex
	 *            Indice de fila (inicia en 0).
	 * @param columnName
	 *            Nombre de columna.
	 * @param value
	 *            Valor a establecer.
	 * @return Fila modificada.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             Si el indice de fila es igual o superior a cantidad de filas.
	 */
	public Map<String, Object> setCellValue(int rowIndex, String columnName, Object value) {
		Map<String, Object> row = getRow(rowIndex);
		row.put(columnName, value);

		return setRow(rowIndex, row);
	}

	// /**
	// * Modifica una fila.
	// *
	// * @param rowIndex
	// * Indice de fila (comienza de 0).
	// * @param row
	// * Nuevos valores de fila.
	// * @return Fila modificada.
	// *
	// * @throws IndexOutOfBoundsException
	// * Si el indice de fila es igual o superior a cantidad de filas.
	// */
	// public Map<String, Object> setRow(int rowIndex, Map<String, Object> row) {
	// if (rowIndex >= getRowCount()) {
	// throw new IndexOutOfBoundsException("Indice de fila igual o superior a cantidad de filas de la tabla");
	// }
	//
	// getState().rows.set(rowIndex, row);
	// markAsDirty();
	// return wrapRow(row);
	// }

	/**
	 * Modifica una fila.
	 * 
	 * @param rowIndex
	 *            Indice de fila (comienza de 0).
	 * @param values
	 *            Nuevos valores de fila.
	 * @return Fila modificada.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             Si el indice de fila es igual o superior a cantidad de filas.
	 * @throws IllegalArgumentException
	 *             Si cantidad de valores de fila es menor a cantidad de columnas.
	 */
	public Map<String, Object> setRow(int rowIndex, Object... values) {
		Map<String, Object> row = buildRow(values);

		getState().rows.set(rowIndex, row);
		markAsDirty();
		return wrapRow(row);
	}

	/**
	 * Modifica una fila.
	 * 
	 * @param rowIndex
	 *            Indice de fila (comienza de 0).
	 * @param row
	 *            Nuevos valores de fila.
	 * @return Fila modificada.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             Si el indice de fila es igual o superior a cantidad de filas.
	 */
	public Map<String, Object> setRow(int rowIndex, Map<String, Object> row) {
		Map<String, Object> rowValues = completeRow(row);

		getState().rows.set(rowIndex, rowValues);
		markAsDirty();
		return wrapRow(rowValues);
	}

	/**
	 * Envuelve una fila en un mapa.
	 * 
	 * @param row
	 *            Fila a envolver.
	 * @return fila envuelta.
	 */
	protected Map<String, Object> wrapRow(Map<String, Object> row) {
		return new LinkedHashMap<>(row);
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

	/**
	 * Construye un mapa fila a partir de un conjunto de valores.
	 * 
	 * @param values
	 *            Valores a partir de los cuales crear la fila.
	 * @return Nuevo mapa fila.
	 */
	protected Map<String, Object> buildRow(Object... values) {
		List<Map<String, Object>> columns = getState().columns;

		/* si la cantidad de elementos pasados es igual a la cantidad de columnas, entonces se construye la fila facilmente. */
		if (columns.size() == values.length) {
			Map<String, Object> row = new LinkedHashMap<>();
			int i = 0;
			for (Map<String, Object> column : columns) {
				row.put(Column.fromMap(column).name, values[i++]);
			}
			return row;
		} else {
			throw new IllegalArgumentException("Cantidad de elementos de fila invalida!");
		}
	}

	@SuppressWarnings("serial")
	private void addHandleClickCallback() {
		addFunction("handleIconClick", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				JsonObject eventDetail = arguments.getObject(0);
				for (ClickListener clickListener : clickListeners) {
					Column column = Column.fromJsonObject(eventDetail.getObject("column"));

					int rowIndex = new Double(eventDetail.getNumber("rowIndex")).intValue();

					String iconName = eventDetail.getString("icon");
					IronIcon icon = IronIcon.fromString(iconName);

					Map<String, Object> row = getRow(rowIndex);

					clickListener.iconClick(column, row, rowIndex, icon);
				}
			}
		});
	}

	/**
	 * Obtiene todas las columnas de tipo ICONO.
	 * 
	 * @return columnas de tipo ICONO.
	 */
	private List<Map<String, Object>> getIconColumns() {
		List<Map<String, Object>> columns = getState().columns;
		List<Map<String, Object>> iconColumns = new ArrayList<>();

		for (Map<String, Object> column : columns) {
			if (IconColumn.isIconColumn(column)) {
				iconColumns.add(column);
			}
		}

		return iconColumns;
	}

	/**
	 * Obtiene todas las columnas de tipo NORMAL (es decir, sin icono asignado).
	 * 
	 * @return columnas de tipo NORMAL.
	 */
	private List<Map<String, Object>> getRegularColumns() {
		List<Map<String, Object>> columns = getState().columns;
		List<Map<String, Object>> regularColumns = new ArrayList<>();

		for (Map<String, Object> column : columns) {
			if (IconColumn.isRegularColumn(column)) {
				regularColumns.add(column);
			}
		}

		return regularColumns;
	}
}
