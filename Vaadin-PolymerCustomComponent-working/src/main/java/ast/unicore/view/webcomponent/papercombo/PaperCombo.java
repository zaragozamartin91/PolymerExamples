package ast.unicore.view.webcomponent.papercombo;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	 * __INVALID_KEY__
	 */
	public static final String INVALID_KEY = "__INVALID_KEY__";

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
	 * @throws InvalidKeyException
	 *             Cuando la clave/caption del item a agregar sea nula o igual a {@link PaperCombo#INVALID_KEY}.
	 */
	public PaperCombo addItem(String itemCaption, Object item) throws InvalidKeyException /* , DuplicateItemException */ {
		if (itemCaption == null || INVALID_KEY.equals(itemCaption)) {
			throw new InvalidKeyException("La clave " + itemCaption + " es invalida!");
		}

		// if (items.containsKey(itemCaption)) {
		// throw new DuplicateItemException("La clave " + itemCaption + " ya esta contenida dentro del combo!");
		// }

		items.put(itemCaption, item);
		getState().captions = items.keySet().toArray(new String[0]);
		markAsDirty();

		return this;
	}

	/**
	 * Establece todos los items del combo.
	 * 
	 * @param items
	 *            Nuevos items del combo.
	 * @return this.
	 */
	public PaperCombo setItems(Map<String, Object> items) {
		this.items = new LinkedHashMap<>(items);
		getState().captions = items.keySet().toArray(new String[0]);
		markAsDirty();

		return this;
	}

	/**
	 * Agrega un item al combo. El caption se tomara como el {@link Object#toString()} del item a agregar.
	 * 
	 * @param item
	 *            Item a agregar.
	 */
	public PaperCombo addItem(Object item) {
		this.addItem(item.toString(), item);
		return this;
	}

	/**
	 * Obtiene el valor seleccionado en el combo, Null si no existe valor seleccionado.
	 * 
	 * @return valor seleccionado en el combo.
	 */
	@SuppressWarnings("unchecked")
	public <E> E getValue() {
		return (E) items.get(selectedItemCaption);
	}

	/**
	 * Establece el item seleccionado a partir de un caption.
	 * 
	 * @param itemCaption
	 *            Caption del item a marcar como seleccionado.
	 * @return this.
	 * @throws NonexistentKeyException
	 *             Si el caption no corresponde con ningun item del combo.
	 */
	public PaperCombo setSelected(String itemCaption) throws NonexistentKeyException {
		if (items.keySet().contains(itemCaption)) {
			setSelectedItemCaption(itemCaption);
			getState().selectedLabel = itemCaption;
			markAsDirty();
		} else {
			throw new NonexistentKeyException("El item " + itemCaption + " no pertenece al combo!");
		}

		return this;
	}

	/**
	 * Establece el item seleccionado.
	 * 
	 * @param item
	 *            Item a establecer como seleccionado.
	 * @return this.
	 */
	public PaperCombo setSelectedByItem(Object item) {
		Set<Entry<String, Object>> entries = items.entrySet();
		for (Entry<String, Object> entry : entries) {
			if (item.equals(entry.getValue())) {
				this.setSelected(entry.getKey());
				return this;
			}
		}

		throw new NonexistentKeyException("El item " + item + " no pertenece al combo!");
	}

	/**
	 * Limpia el contenido del combo.
	 * 
	 * @return this.
	 */
	public PaperCombo clear() {
		String itemCaption = INVALID_KEY;
		setSelectedItemCaption(itemCaption);
		getState().selectedLabel = itemCaption;
		markAsDirty();

		return this;
	}

	/**
	 * Vacía las opciones del combo.
	 */
	public PaperCombo empty() {
		resetItems();
		resetSelectedCaption();
		getState().captions = new String[] {};
		markAsDirty();

		return this;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		super.setEnabled(isEnabled);
		this.getState().dropDisabled = !isEnabled;
		// markAsDirty();
	}

	/**
	 * Establece la altura del listado de opciones del combo.
	 * 
	 * @param height
	 *            Altura del listado, por ejemplo: "300px".
	 */
	public void settDropdownContentHeight(String height) {
		getState().dropdownContentHeight = height;
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
	public static abstract class ValueChangeListener<DataType> {
		/**
		 * Accion a ejecutar cuando ocurra un cambio de seleccion en el combo. Si {@link PaperCombo#INVALID_KEY} se encuentra seleccionado, el metodo no se
		 * ejecutara.
		 * 
		 * @param selectedItemCaption
		 *            Caption del item seleccionado.
		 * @param selectedItemValue
		 *            Valor del item seleccionado.
		 */
		public abstract void valueChange(String selectedItemCaption, DataType selectedItemValue);

		void change(String selectedItemCaption, DataType selectedItemValue) {
			if (INVALID_KEY.equals(selectedItemCaption)) {
				return;
			} else {
				valueChange(selectedItemCaption, selectedItemValue);
			}
		}
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
				valueChangeListener.change(selectedItemCaption, (DataType) getValue());
			}
		});
	}

	private void addHandleSelectedCallback() {
		addFunction("handleSelected", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				// System.out.println(PaperCombo.class.getSimpleName() + "#handleSelected: " + arguments.getString(0));
				setSelectedItemCaption(arguments.getString(0));
			}
		});
	}

	private void resetItems() {
		items = new LinkedHashMap<>();
	}

	private void resetSelectedCaption() {
		setSelectedItemCaption(INVALID_KEY);
	}

	private void setSelectedItemCaption(String caption) {
		selectedItemCaption = caption;
		wrappedField.setValue(caption);
	}
}
