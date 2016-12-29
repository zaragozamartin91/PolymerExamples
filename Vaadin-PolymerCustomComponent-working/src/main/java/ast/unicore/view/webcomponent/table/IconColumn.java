package ast.unicore.view.webcomponent.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ast.unicore.view.webcomponent.icons.iron.IronIcon;
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
	private IconColumn(String name, IronIcon... icons) {
		super(name);
		List<String> stringIcons = new ArrayList<>();
		for (IronIcon ironIcon : icons) {
			stringIcons.add(ironIcon.name);
		}
		this.icons = joinStrings(",", stringIcons.toArray(new String[0]));
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put(ICONS_KEY, icons);
		return map;
	}

	/**
	 * Crea una columna tipo icono con un nombre determinado.
	 * 
	 * @param name
	 *            Nombre de columna.
	 * @param icons
	 *            Iconos de la columna.
	 * @return columna tipo icono con un nombre determinado.
	 */
	public static IconColumn newNamed(String name, IronIcon... icons) {
		return new IconColumn(name, icons);
	}

	/**
	 * Crea una nueva columna tipo icono con nombre vacio.
	 * 
	 * @param icons
	 *            Iconos de la columna.
	 * @return nueva columna tipo icono con nombre vacio.
	 */
	public static IconColumn newEmptynamed(IronIcon... icons) {
		return new IconColumn(" ", icons);
	}

	public static boolean isIconColumn(Object obj) {
		if (obj instanceof IconColumn) {
			return true;
		}

		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).containsKey(ICONS_KEY);
		}

		return false;
	}

	public static boolean isRegularColumn(Object obj) {
		return !isIconColumn(obj);
	}

	// /**
	// * Crea un {@link IconColumn} a partir de un objeto json con claves {@link Column#NAME_KEY} y {@link IconColumn#ICONS_KEY}.
	// *
	// * @param jsonObject
	// * Objeto json a partir del cual crear la columna.
	// * @return Nueva columna de tipo icono.
	// */
	// public static IconColumn fromJsonObject(JsonObject jsonObject) {
	// JsonArray iconsJsonArray = jsonObject.getArray(ICONS_KEY);
	// int iconCount = iconsJsonArray.length();
	// List<String> icons = new ArrayList<>();
	//
	// for (int i = 0; i < iconCount; i++) {
	// icons.add(iconsJsonArray.getString(i));
	// }
	//
	// return new IconColumn(jsonObject.getString(NAME_KEY), icons.toArray(new String[0]));
	// }

	/**
	 * Concatena un conjunto de strings para que sea uno solo.
	 * 
	 * @param delim
	 *            Delimitador a utilizar.
	 * @param elems
	 *            Elementos a concatenar.
	 * @return String con elementos separados por 'delim'. Si no hay elementos a concatenar o es nulo, entonces se retorna vacio.
	 */
	private String joinStrings(String delim, String... elems) {
		if (elems == null || elems.length == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder(elems[0]);
		if (elems.length > 0) {
			for (int i = 1; i < elems.length; i++) {
				Object elem = elems[i];
				sb.append("," + elem);
			}
		}

		return sb.toString();
	}
}
