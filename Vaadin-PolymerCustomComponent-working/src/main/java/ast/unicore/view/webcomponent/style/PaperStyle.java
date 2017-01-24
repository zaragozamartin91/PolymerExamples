package ast.unicore.view.webcomponent.style;

/**
 * Estilos a utilizar con componentes paper (por ejemplo usando materialize).
 * 
 * @author martin.zaragoza
 *
 */
public enum PaperStyle {
	/**
	 * Estilo que marca a un contenedor de vaadin (sea un Window o un Layout) de forma tal que todo su contenido se muestre en caso de overflow.
	 */
	OVERFLOWY("overflowy");

	/**
	 * Nombre de la clase que determina el estilo.
	 */
	public final String name;

	private PaperStyle(String name) {
		this.name = name;
	}
}
