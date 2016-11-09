package ast.unicore.view.webcomponent.table;

import java.util.Map;

import elemental.json.JsonObject;

/**
 * Columna clickeable con icono. El id del icono debe corresponder a los iron-icons de polymer.
 * 
 * @see <a href="https://elements.polymer-project.org/elements/iron-icons?view=demo:demo/index.html">iron-icons</a>
 * 
 * @author martin.zaragoza
 *
 */
public class IconColumn extends Column {
	public static final String ICON_KEY = "icon";
	public final String icon;

	/**
	 * Crea una columna de tipo icono con un nombre y un ID de icono.
	 * 
	 * @param name
	 *            Nombre/caption de columna.
	 * @param icon
	 *            Icono de columna.
	 */
	public IconColumn(String name, String icon) {
		super(name);
		this.icon = icon;
	}

	@Override
	public Map<String, String> toMap() {
		Map<String, String> map = super.toMap();
		map.put(ICON_KEY, icon);
		return map;
	}

	/**
	 * Crea un {@link IconColumn} a partir de un mapa con claves {@link Column#NAME_KEY} y {@link IconColumn#ICON_KEY}.
	 * 
	 * @param map
	 *            Mapa a partir del cual crear la columna.
	 * @return Nueva columna de tipo icono.
	 */
	public static IconColumn fromMap(Map<String, String> map) {
		return new IconColumn(map.get(NAME_KEY), map.get(ICON_KEY));
	}

	/**
	 * Crea un {@link IconColumn} a partir de un objeto json con claves {@link Column#NAME_KEY} y
	 * {@link IconColumn#ICON_KEY}.
	 * 
	 * @param jsonObject
	 *            Objeto json a partir del cual crear la columna.
	 * @return Nueva columna de tipo icono.
	 */
	public static IconColumn fromJsonObject(JsonObject jsonObject) {
		return new IconColumn(jsonObject.getString(NAME_KEY), jsonObject.getString(ICON_KEY));
	}
}
