package ast.unicore.view.webcomponent.combo.vaadin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

/**
 * Componente de vaadin del lado del servidor representante del componente paper-dropdown-menu de polymer.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "vaadin-combo-box-connector.js" })
public final class VaadinComboBox extends AbstractJavaScriptComponent {
	private String selectedItemCaption = "";
	private Map<String, Object> items = new HashMap<>();

	/**
	 * Crea un nuevo PaperCombo con un label.
	 * 
	 * @param label
	 *            - Label del combo.
	 */
	public VaadinComboBox(String label) {
		getState().label = label;

		addHandleValueChanged();
	}

	@Override
	protected VaadinComboBoxState getState() {
		return (VaadinComboBoxState) super.getState();
	}

	public void setItems(List<String> items) {
		this.items = new HashMap<>();
		for (String item : items) {
			this.items.put(item, item);
		}
		callFunction("setItems", items.toArray(new String[0]));
	}
	
	public void setItems(Map<String, Object> items) {
		this.items = new HashMap<>(items);
		callFunction("setItems", items.keySet().toArray(new String[0]));
	}
	
	/**
	 * Agrega un item al combo.
	 * 
	 * @param itemCaption
	 *            - Clave/Caption del item.
	 * @param item
	 *            - Item propiamente dicho.
	 */
	public void addItem(String itemCaption, Object item) {
		callFunction("addItem", itemCaption);
		items.put(itemCaption, item);
	}

	/**
	 * Agrega un item al combo. El caption y el valor del item son el mismo.
	 * 
	 * @param itemCaption
	 *            - Caption y valor del item.
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
	 *            - Caption del item a marcar como seleccionado.
	 * @return this.
	 */
	public VaadinComboBox setSelected(String itemCaption) {
		if (items.keySet().contains(itemCaption)) {
			this.selectedItemCaption = itemCaption;
			getState().value = itemCaption;
			
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
		this.getState().disabled = !isEnabled;
		markAsDirty();
	}

	private void addHandleValueChanged() {
		addFunction("handleValueChanged", new JavaScriptFunction() {
			public void call(JsonArray arguments) {
				System.out.println("calling VaadinComboBox#handleValueChanged with: " + arguments.getString(0));
				// getState().selectedLabel = arguments.getString(0);
				selectedItemCaption = arguments.getString(0);
			}
		});
	}
}
