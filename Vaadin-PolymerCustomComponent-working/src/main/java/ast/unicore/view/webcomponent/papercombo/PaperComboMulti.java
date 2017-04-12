package ast.unicore.view.webcomponent.papercombo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Joiner;
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
@JavaScript({ "paper-combo-multi-connector.js" })
public final class PaperComboMulti extends AbstractJavaScriptComponent {
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
	public PaperComboMulti(String label) {
		resetItems();
		resetSelectedCaption();

		getState().dropLabel = label;
		getState().multiple = true;

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
	 *             Cuando la clave/caption del item a agregar sea nula o igual a {@link PaperComboMulti#INVALID_KEY}.
	 */
	public PaperComboMulti addItem(String itemCaption, Object item) throws InvalidKeyException /* , DuplicateItemException */ {
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
	 * Elimina un item del combo.
	 * 
	 * @param item
	 *            El item propiamente dicho.
	 * @return retorna el item eliminado, o null en caso de no encontrarlo.
	 * @throws InvalidKeyException
	 *             Cuando la clave/caption del item es nula o igual a {@link PaperComboMulti#INVALID_KEY}.
	 */
	public Object removeByItem(Object item) throws InvalidKeyException {
		String itemCaption;
		try {
			itemCaption = getItemCaption(item);
		} catch (NonexistentKeyException e) {
			itemCaption = null;
		}

		Object deleteItem = items.remove(itemCaption);
		getState().captions = items.keySet().toArray(new String[0]);

		return deleteItem;
	}

	/**
	 * Elmina un item del combo.
	 * 
	 * @param itemCaption
	 *            Clave/caption del item.
	 * @return retorna el ítem elminado, o null en caso de no encontrarlo.
	 * @throws InvalidKeyException
	 *             Cuando la clave/caption es nula o igual a {@link PaperComboMulti#INVALID_KEY}.
	 */
	public Object remove(String itemCaption) throws InvalidKeyException {
		if (itemCaption == null || INVALID_KEY.equals(itemCaption)) {
			throw new InvalidKeyException("La clave " + itemCaption + " es invalida!");
		}

		Object deletedItem = items.remove(itemCaption);
		boolean itemRemoved = deletedItem != null;
		if (itemRemoved) {
			getState().captions = items.keySet().toArray(new String[0]);
			this.clear();
		}

		return deletedItem;
	}

	/**
	 * Establece todos los items del combo.
	 * 
	 * @param items
	 *            Nuevos items del combo.
	 * @return this.
	 */
	public PaperComboMulti setItems(Map<String, Object> items) {
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
	public PaperComboMulti addItem(Object item) {
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
	public PaperComboMulti setSelected(String itemCaption) throws NonexistentKeyException {
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
	 * Obtiene un item a partir de su clave/caption.
	 * 
	 * @param item
	 *            Item a buscar.
	 * @return Clave/caption del item dentro del combo.
	 * @throws NonexistentKeyException
	 *             Si el item no se encuentra en el combo.
	 */
	public String getItemCaption(Object item) {
		Set<Entry<String, Object>> entries = items.entrySet();
		for (Entry<String, Object> entry : entries) {
			if (item.equals(entry.getValue())) {
				return entry.getKey();
			}
		}

		throw new NonexistentKeyException("El item " + item + " no pertenece al combo!");
	}

	/**
	 * Establece el item seleccionado.
	 * 
	 * @param item
	 *            Item a establecer como seleccionado.
	 * @return this.
	 */
	public PaperComboMulti setSelectedByItem(Object item) {
		String itemCaption = getItemCaption(item);
		this.setSelected(itemCaption);
		return this;
	}

	/**
	 * Limpia el contenido del combo.
	 * 
	 * @return this.
	 */
	public PaperComboMulti clear() {
		String itemCaption = INVALID_KEY;
		setSelectedItemCaption(itemCaption);
		getState().selectedLabel = itemCaption;
		markAsDirty();

		return this;
	}

	/**
	 * Vacía las opciones del combo.
	 */
	public PaperComboMulti empty() {
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
	 * Establece el modo del drop del combo.
	 * 
	 * @param compactMode
	 *            true para desplegar el combo con un scroll vertical (util para combos con demasiadas opciones a mostrarse DENTRO DE UN POPUP). False para que
	 *            el combo intente desplegar todas las opciones sin scroll vertical (util para combos con pocas opciones o para combos en pantallas regulares).
	 */
	public void setCompactMode(boolean compactMode) {
		this.getState().compactDrop = compactMode;
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
		 * Accion a ejecutar cuando ocurra un cambio de seleccion en el combo. Si {@link PaperComboMulti#INVALID_KEY} se encuentra seleccionado, el metodo no se
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
				JsonArray argArray = arguments.getArray(0);
				int captionCount = argArray.length();
				List<String> captions = new ArrayList<>();
				for (int i = 0; i < captionCount; i++) {
					captions.add(argArray.getString(i));
				}
				setSelectedItemCaption(captions.isEmpty() ? "" : Joiner.on(',').skipNulls().join(captions));
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
