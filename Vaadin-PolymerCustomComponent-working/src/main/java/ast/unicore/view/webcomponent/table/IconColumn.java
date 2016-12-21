package ast.unicore.view.webcomponent.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import elemental.json.JsonArray;
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
	public static final String ICONS_KEY = "icons";
	public final String icons;

	/**
	 * Crea una columna de tipo icono con un nombre y un ID de icono.
	 * 
	 * @param name
	 *            Nombre/caption de columna.
	 * @param icons
	 *            Iconos de la columna.
	 */
	public IconColumn(String name, String... icons) {
		super(name);
		this.icons = StringUtils.join(icons, ",");
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put(ICONS_KEY, icons);
		return map;
	}

	/**
	 * Crea un {@link IconColumn} a partir de un objeto json con claves {@link Column#NAME_KEY} y {@link IconColumn#ICONS_KEY}.
	 * 
	 * @param jsonObject
	 *            Objeto json a partir del cual crear la columna.
	 * @return Nueva columna de tipo icono.
	 */
	public static IconColumn fromJsonObject(JsonObject jsonObject) {
		JsonArray iconsJsonArray = jsonObject.getArray(ICONS_KEY);
		int iconCount = iconsJsonArray.length();
		List<String> icons = new ArrayList<>();

		for (int i = 0; i < iconCount; i++) {
			icons.add(iconsJsonArray.getString(i));
		}

		return new IconColumn(jsonObject.getString(NAME_KEY), icons.toArray(new String[0]));
	}
}
