package ast.unicore.view.webcomponent.papercombo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

/**
 * Componente de vaadin del lado del servidor representante del componente
 * paper-dropdown-menu de polymer.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "paper-combo-connector.js" })
public final class PaperCombo extends AbstractJavaScriptComponent {
	private String selectedItemCaption = "";
	private Map<String, Object> items = new HashMap<>();

	/**
	 * Crea un nuevo PaperCombo con un label.
	 * 
	 * @param label
	 *            Label del combo.
	 */
	public PaperCombo(String label) {
		getState().dropLabel = label;

		addHandleSelectedCallback();
	}

	@Override
	protected PaperComboState getState() {
		return (PaperComboState) super.getState();
	}

	/**
	 * Agrega un item al combo.
	 * 
	 * @param itemCaption
	 *            Clave/Caption del item.
	 * @param item
	 *            Item propiamente dicho.
	 */
	public void addItem(String itemCaption, Object item) {
		items.put(itemCaption, item);
		getState().captions = items.keySet().toArray(new String[0]);
		markAsDirty();
	}

	/**
	 * Agrega un item al combo. El caption y el valor del item son el mismo.
	 * 
	 * @param itemCaption
	 *            Caption y valor del item.
	 */
	public void addItem(String itemCaption) {
		this.addItem(itemCaption, itemCaption);
	}

	/**
	 * Obtiene el valor seleccionado en el combo.
	 * 
	 * @return valor seleccionado en el combo.
	 */
	public Object getValue() {
		return items.get(selectedItemCaption);
	}

	/**
	 * Establece el item seleccionado.
	 * 
	 * @param itemCaption
	 *            Caption del item a marcar como seleccionado.
	 * @return this.
	 */
	public PaperCombo setSelected(String itemCaption) {
		if (items.keySet().contains(itemCaption)) {
			this.selectedItemCaption = itemCaption;
			getState().selectedLabel = itemCaption;
			return this;
		}

		throw new RuntimeException("El item " + itemCaption + " no pertenece al combo!");
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
	public void setEnabled(boolean isEnabled) {
		super.setEnabled(isEnabled);
		this.getState().dropDisabled = !isEnabled;
		markAsDirty();
	}

	private void addHandleSelectedCallback() {
		addFunction("handleSelected", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				System.out.println(PaperCombo.class.getSimpleName() + "#handleSelected with: " + arguments.getString(0));
				// getState().selectedLabel = arguments.getString(0);
				selectedItemCaption = arguments.getString(0);
			}
		});
	}
}
