package ast.unicore.view.webcomponent.papercombo;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.annotations.JavaScript;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.TextField;

import elemental.json.JsonArray;

/**
 * Componente de vaadin del lado del servidor representante del componente paper-dropdown-menu de polymer.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "paper-combo-connector.js" })
public final class PaperCombo extends AbstractJavaScriptComponent {
	/**
	 * TextField utilizado para trabajar facilmente con los ValueChange.
	 */
	private TextField wrappedField = new TextField();

	private String selectedItemCaption;
	private Map<String, Object> items;

	/**
	 * Crea un nuevo PaperCombo con un label.
	 * 
	 * @param label
	 *            Label del combo.
	 */
	public PaperCombo(String label) {
		resetItems();
		resetSelectedCaption();

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
	 * Obtiene el valor seleccionado en el combo, Null si no existe valor seleccionado.
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
			setSelectedItemCaption(itemCaption);
			getState().selectedLabel = itemCaption;
			return this;
		}

		throw new RuntimeException("El item " + itemCaption + " no pertenece al combo!");
	}

	/**
	 * Vacía las opciones del combo.
	 */
	public void empty() {
		resetItems();
		resetSelectedCaption();
		getState().captions = new String[] {};
		markAsDirty();
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		super.setEnabled(isEnabled);
		this.getState().dropDisabled = !isEnabled;
		markAsDirty();
	}

	/**
	 * Listener de cambio de valor.
	 * 
	 * @author martin.zaragoza
	 *
	 * @param <DataType>
	 *            Tipo de dato del valor del item seleccionado.
	 */
	public static interface ValueChangeListener<DataType> {
		/**
		 * Accion a ejecutar cuando ocurra un cambio de seleccion en el combo.
		 * 
		 * @param selectedItemCaption
		 *            Caption del item seleccionado.
		 * @param selectedItemValue
		 *            Valor del item seleccionado.
		 */
		void valueChange(String selectedItemCaption, DataType selectedItemValue);
	}

	/**
	 * Agrega un listener de cambio de valor.
	 * 
	 * @param valueChangeListener
	 *            Listener de cambio de valor.
	 * @param <DataType>
	 *            Tipo de dato contenido en el Combo.
	 */
	@SuppressWarnings("unchecked")
	public <DataType> void addValueChangeListener(final ValueChangeListener<DataType> valueChangeListener) {
		wrappedField.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				valueChangeListener.valueChange(selectedItemCaption, (DataType) getValue());
			}
		});
	}

	private void addHandleSelectedCallback() {
		addFunction("handleSelected", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				System.out.println(PaperCombo.class.getSimpleName() + "#handleSelected: " + arguments.getString(0));
				setSelectedItemCaption(arguments.getString(0));
			}
		});
	}

	private void resetItems() {
		items = new HashMap<>();
	}

	private void resetSelectedCaption() {
		setSelectedItemCaption("");
	}

	private void setSelectedItemCaption(String caption) {
		selectedItemCaption = caption;
		wrappedField.setValue(caption);
	}
}
