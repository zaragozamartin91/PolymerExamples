package ast.unicore.view.webcomponent.papertypeaheadinput;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

/**
 * Componente de vaadin del lado del servidor representante del componente paper-typeahead-input de polymer. Funciona
 * como un ComboBox con autocompletar.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "paper-typeahead-input-connector.js" })
public final class PaperTypeAheadInput extends AbstractJavaScriptComponent {
	private String selectedValue = "";
	private Map<String, Object> items = new HashMap<>();

	/**
	 * Crea un nuevo PaperTypeAheadInput con un label y .
	 */
	public PaperTypeAheadInput(String label) {
		getState().inputPlaceholder = label;

		addHandleConfirmedCallback();
	}

	@Override
	protected PaperTypeAheadInputState getState() {
		return (PaperTypeAheadInputState) super.getState();
	}

	/**
	 * Marca al componente como required.
	 * 
	 * @return this.
	 */
	public PaperTypeAheadInput required() {
		getState().inputRequired = true;
		return this;
	}

	/**
	 * Agrega un item.
	 * 
	 * @param itemCaption
	 *            - Clave/Caption del item.
	 * @param item
	 *            - Item propiamente dicho.
	 * @return this.
	 */
	public PaperTypeAheadInput addItem(String itemCaption, Object item) {
		callFunction("addItem", itemCaption);
		items.put(itemCaption, item);
		return this;
	}

	/**
	 * Agrega un item. El caption y el valor del item son el mismo.
	 * 
	 * @param itemCaption
	 *            - Caption y valor del item.
	 * @return this.
	 */
	public PaperTypeAheadInput addItem(String itemCaption) {
		return this.addItem(itemCaption, itemCaption);
	}

	/**
	 * Obtiene el valor seleccionado en el componente.
	 * 
	 * @return valor seleccionado en el componente.
	 */
	public Object getValue() {
		return items.get(selectedValue);
	}

	/**
	 * Establece el item seleccionado.
	 * 
	 * @param itemCaption
	 *            - Caption del item a marcar como seleccionado en el componente cliente.
	 * @return this.
	 */
	public PaperTypeAheadInput setSelected(String itemCaption) {
		if (items.keySet().contains(itemCaption)) {
			this.selectedValue = itemCaption;
			getState().inputValue = itemCaption;
			return this;
		}

		throw new RuntimeException("El item " + itemCaption + " no pertenece al input!");
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		if (visible) {
			Set<Entry<String, Object>> entries = items.entrySet();
			for (Entry<String, Object> entry : entries) {
				callFunction("addItem", entry.getKey());
			}
		}
	}
	

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		getState().disabled = !enabled;
		markAsDirty();
	}

	private void addHandleConfirmedCallback() {
		addFunction("handleConfirmed", new JavaScriptFunction() {
			public void call(JsonArray arguments) {
				System.out.println("calling PaperTypeAheadInput#handleConfirmed with: " + arguments.getString(0));
				selectedValue = arguments.getString(0);
			}
		});
	}
}
