package ast.unicore.view.webcomponent.paperbutton;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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
	private Button wrappedButton = new Button();

	/**
	 * Manejador de eventos Click.
	 * 
	 * @author martin.zaragoza
	 *
	 */
	public static interface ClickListener {
		public abstract void buttonClick();
	}

	/**
	 * Crea un nuevo PaperButton con click listeners.
	 * 
	 * @param label
	 *            Label del boton.
	 * @param clickListeners
	 *            Listeners de evento click.
	 */
	public PaperButton(String label, ClickListener... clickListeners) {
		wrappedButton.setEnabled(true);
		getState().buttonLabel = label;

		if (clickListeners != null && clickListeners.length > 0) {
			for (ClickListener clickListener : clickListeners) {
				this.addClickListener(clickListener);
			}
		}

		addHandleClickCallback();
	}

	/**
	 * Establece el label del boton.
	 * 
	 * @param label
	 *            Texto del boton.
	 */
	public void setLabel(String label) {
		getState().buttonLabel = label;
	};

	/**
	 * Agrega un clickListener.
	 * 
	 * @param listener
	 *            clickListener a agregar.
	 * @return this.
	 */
	public PaperButton addClickListener(final ClickListener listener) {
		wrappedButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				listener.buttonClick();
			}
		});

		return this;
	}

	// FIXME : NO FUNCIONA.
	// /**
	// * Agrega un atajo de presion de boton.
	// *
	// * @param keyCode
	// * Tecla a presionar.
	// * @param modifiers
	// * [the (optional) modifiers for invoking the shortcut, null for
	// * none].
	// *
	// */
	// public void setClickShortcut(int keyCode, int... modifiers) {
	// wrappedButton.setClickShortcut(keyCode, modifiers);
	// }

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

	@Override
	public void setHeight(float height, Unit unit) {
		throw new UnsupportedOperationException("Operacion no soportada!");
	}

	@Override
	public void setWidth(float width, Unit unit) {
		super.setWidth(width, unit);
		getState().widthToSet = "" + width + unit.getSymbol();
		markAsDirty();
	}

	@Override
	public void setHeight(String height) {
		throw new UnsupportedOperationException("Operacion no soportada!");
	}

	private void addHandleClickCallback() {
		addFunction("handleClick", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				// System.out.println(PaperButton.class.getSimpleName() + "#handleClick");
				wrappedButton.click();
			}
		});
	}
}
