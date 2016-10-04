package ast.unicore.view.webcomponent.paperbutton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

/**
 * Componente de vaadin del lado del servidor representante del componente paper-button de polymer.
 * 
 * @author martin.zaragoza
 *
 */
@SuppressWarnings("serial")
@JavaScript({ "paper-button-connector.js" })
public final class PaperButton extends AbstractJavaScriptComponent {
	/**
	 * Manejador de eventos Click.
	 * 
	 * @author martin.zaragoza
	 *
	 */
	public static interface ClickListener {
		public abstract void handleClick();
	}

	private List<ClickListener> listeners = new ArrayList<>();

	/**
	 * Crea un nuevo PaperButton con click listeners.
	 * 
	 * @param clickListeners
	 *            - Listeners de evento click.
	 */
	public PaperButton(String label, ClickListener... clickListeners) {
		getState().buttonLabel = label;
		
		if (clickListeners != null && clickListeners.length > 0) {
			listeners = Arrays.asList(clickListeners);
		}

		addHandleClickCallback();
	}

	/**
	 * Agrega un clickListener.
	 * 
	 * @param listener - clickListener a agregar.
	 * @return this.
	 */
	public PaperButton addListener(ClickListener listener) {
		listeners.add(listener);
		return this;
	}

	@Override
	protected PaperButtonState getState() {
		return (PaperButtonState) super.getState();
	}
	
	@Override
	public void setEnabled(boolean isEnabled) {
		super.setEnabled(isEnabled);
		getState().buttonDisabled = !isEnabled;
		markAsDirty();
	}

	/**
	 * Deshabilita el botón.
	 * 
	 */
	public void disable() {
		setEnabled(false);
	}

	/**
	 * Habilita el botón.
	 * 
	 */
	public void enable() {
		setEnabled(true);
	}

	private void addHandleClickCallback() {
		addFunction("handleClick", new JavaScriptFunction() {
			public void call(JsonArray arguments) {
				System.out.println("calling PaperButton#handleClick");

				for (ClickListener clickListener : listeners) {
					clickListener.handleClick();
				}
			}
		});
	}
}
