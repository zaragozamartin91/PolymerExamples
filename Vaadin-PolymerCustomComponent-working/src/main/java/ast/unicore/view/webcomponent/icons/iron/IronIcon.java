package ast.unicore.view.webcomponent.icons.iron;

import java.util.NoSuchElementException;

/**
 * Iconos de https://elements.polymer-project.org/elements/iron-icons?view=demo:demo/index.html.
 * 
 * @author martin.zaragoza
 *
 */
public enum IronIcon {
	SAVE("save"),
	CHECK("check"),
	BUILD("build"),
	CLEAR("clear"),
	MENU("menu"),
	INPUT("input"),
	EDIT("editor:mode-edit"),
	DELETE("delete"),
	SOCIAL_GROUP("social:group"),
	GROUP_WORK("group-work"),
	ALARM("alarm");

	public final String name;

	IronIcon(String name) {
		this.name = name;
	}

	/**
	 * Obtiene un IronIcon a partir de su atributo "name".
	 * 
	 * @param iconName
	 *            Nombre de icono a obtener.
	 * @return Icono encontrado.
	 * @throws NoSuchElementException
	 *             Si el nombre del icono no corresponde a ninguno de los iconos soportados.
	 */
	public static IronIcon fromString(String iconName) {
		IronIcon[] ironIcons = IronIcon.values();
		for (IronIcon ironIcon : ironIcons) {
			if (ironIcon.name.equals(iconName)) {
				return ironIcon;
			}
		}

		throw new NoSuchElementException("Icono " + iconName + " no existe!");
	}
}
