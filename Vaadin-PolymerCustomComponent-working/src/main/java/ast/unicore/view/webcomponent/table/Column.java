package ast.unicore.view.webcomponent.table;

import java.util.HashMap;
import java.util.Map;

import elemental.json.JsonObject;

/**
 * Columna de tabla responsiva.
 * 
 * @author martin.zaragoza
 *
 */
public class Column {
	/**
	 * name
	 */
	public static final String NAME_KEY = "name";
	/**
	 * Nombre de la columna.
	 */
	public final String name;

	public Column(String name) {
		super();
		this.name = name;
	}

	/**
	 * Retorna una representacion de la columna como un mapa.
	 * 
	 * @return representacion de la columna como un mapa.
	 */
	@SuppressWarnings("serial")
	public Map<String, Object> toMap() {
		return new HashMap<String, Object>() {
			{
				put(NAME_KEY, name);
			}
		};
	}

	/**
	 * Crea una columna de tabla responsiva a partir de un mapa con clave {@link Column#NAME_KEY}.
	 * 
	 * @param map
	 *            Mapa a partir del cual crear la columna.
	 * @return columna de tabla responsiva.
	 */
	public static Column fromMap(Map<String, Object> map) {
		return new Column(map.get(NAME_KEY).toString());
	}

	/**
	 * Crea una columna de tabla responsiva a partir de un objeto json que contenga una clave {@link Column#NAME_KEY}.
	 * 
	 * @param jsonObject
	 *            Objeto json a partir del cual construir la columna.
	 * @return columna de tabla responsiva.
	 */
	public static Column fromJsonObject(JsonObject jsonObject) {
		return new Column(jsonObject.getString(NAME_KEY));
	}
}
