package mainapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.jsoup.nodes.Element;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.Validator;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ast.unicore.view.webcomponent.paperbutton.PaperButton;
import ast.unicore.view.webcomponent.paperbutton.PaperButton.ClickListener;
import ast.unicore.view.webcomponent.papercheckbox.PaperCheckbox;
import ast.unicore.view.webcomponent.papercombo.NonexistentKeyException;
import ast.unicore.view.webcomponent.papercombo.PaperCombo;
import ast.unicore.view.webcomponent.paperinput.date.PaperDateInput;
import ast.unicore.view.webcomponent.paperinput.text.PaperTextArea;
import ast.unicore.view.webcomponent.paperinput.text.PaperTextInput;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MaterializeTest extends UI {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private PaperTextInput organizationInput;
	private PaperDateInput paperDateInput;

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.addStyleName("overflowy");
		setContent(layout);

		TextField sampleTextfield = new TextField("Prueba");
		sampleTextfield.setWidth("70%");
		layout.addComponent(sampleTextfield);

		PaperButton addComponentsButton = new PaperButton("+");
		addComponentsButton.setAsFloating();
		addComponentsButton.setBackColor("pink lighten-3");

		Button sampleButton = new Button("sample button");
		layout.addComponent(sampleButton);

		addComponentsButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick() {
				PaperButton addPopupButton = new PaperButton("Abrir popup", new ClickListener() {
					@Override
					public void buttonClick() {
						UI ui = MaterializeTest.getCurrent();
						Window window = new Window("Combo popup");
						window.addStyleName("overflowy");

						PaperCombo combo = new PaperCombo("Opciones");
						for (int i = 0; i < 20; i++) {
							combo.addItem("item_" + i);
						}

						// ComboBox combo = new ComboBox("Opciones");
						// for (int i = 0; i < 20; i++) {
						// combo.addItem("item_" + i);
						// }

						int windowHeightPx = 150;

						VerticalLayout windowContent = new VerticalLayout(combo);
						windowContent.setMargin(true);
						windowContent.setSpacing(true);
						windowContent.setSizeFull();
						window.setContent(windowContent);

						window.setWidth("300px");
						window.setHeight(windowHeightPx + "px");
						window.setClosable(true);
						window.center();
						window.setModal(true);
						ui.addWindow(window);
					}
				});

				layout.addComponent(new PaperCheckbox("uno"));
				layout.addComponent(new PaperCheckbox("dos"));
				layout.addComponent(new PaperCheckbox("tres"));

				layout.addComponent(addPopupButton);

				final PaperCombo countryCombo = new PaperCombo("Pais");
				countryCombo.addItem("argentina");
				countryCombo.addItem("brasil");
				countryCombo.addItem("paraguay");
				countryCombo.addItem("uruguay");
				countryCombo.addItem("eeuu");
				countryCombo.addItem("canada");
				countryCombo.addItem("mexico");
				countryCombo.addItem("peru");
				countryCombo.addItem("bolivia");
				countryCombo.addItem("francia");
				layout.addComponent(countryCombo);
				countryCombo.setWidth("75%");

				PaperTextInput countryInput = new PaperTextInput("Seleccione pais");
				countryInput.addValueChangeListener(new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						String caption = event.getProperty().getValue().toString();
						try {
							countryCombo.setSelected(caption);
						} catch (NonexistentKeyException e) {
							Notification.show("elemento " + caption + " no existe!", Type.ERROR_MESSAGE);
						}
					}
				});
				layout.addComponent(countryInput);
				countryInput.setWidth("30%");

				PaperTextInput passwordInput = new PaperTextInput("Ingrese password");
				passwordInput.setPasswordType();
				passwordInput.setWidth("100%");
				passwordInput.addValidator(new Validator() {
					@Override
					public void validate(Object value) throws InvalidValueException {
						if (value.toString().length() < 4) {
							throw new InvalidValueException("Password muy corto");
						}
					}
				});
				passwordInput.autoValidate();

				organizationInput = new PaperTextInput("Nombre organizacion");
				// organizationInput.setRequired(true, "Campo no puede ser vacio
				// [REQUERIDO]!");
				organizationInput.setValue("ACCUSYS");
				organizationInput.setWidth("100%");
				organizationInput.autoValidate();
				organizationInput.addValidator(new Validator() {
					@Override
					public void validate(Object value) throws InvalidValueException {
						Notification.show("Validando: " + value);
						if (value == null || value.toString().isEmpty()) {
							throw new InvalidValueException("Campo no puede ser vacio [VALIDADOR]!");
						}
					}
				});
				organizationInput.addValueChangeListener(new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						Notification.show("CHANGE:" + event.getProperty().getValue());
					}
				});
				organizationInput.addFocusListener(new FocusListener() {
					@Override
					public void focus(FocusEvent event) {
						Notification.show("FOCUS");
					}
				});
				HorizontalLayout inputsLayout = new HorizontalLayout(organizationInput, passwordInput);
				inputsLayout.setWidth("100%");
				inputsLayout.setSpacing(true);
				inputsLayout.setExpandRatio(organizationInput, 2.0f);
				inputsLayout.setExpandRatio(passwordInput, 1.0f);
				layout.addComponent(inputsLayout);

				PaperTextInput disabledInput = new PaperTextInput("Disabled!");
				disabledInput.setValue("Valor");
				disabledInput.disable();
				layout.addComponent(disabledInput);

				// PaperCombo peopleCombo = new PaperCombo("Persona");
				// peopleCombo.addItem("Martin");
				// peopleCombo.addItem("Exe");
				// peopleCombo.addItem("Mauri");
				// peopleCombo.addItem("Roland");
				// layout.addComponent(peopleCombo);
				// peopleCombo.setWidth("50%");

				// PaperTextInput dummyInput = new PaperTextInput("dummy");
				// layout.addComponent(dummyInput);

				PaperTextArea organizationDescription = new PaperTextArea("Descripcion de empresa");
				organizationDescription.setValue(
						"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vehicula luctus venenatis. Sed feugiat, augue ac sagittis semper, nulla libero dapibus turpis, et cursus quam urna et nisl. Integer elementum feugiat turpis eget fringilla. Aliquam viverra nibh ut tincidunt pharetra. Mauris a lacus eget turpis hendrerit elementum at id turpis. Pellentesque sit amet velit aliquam, egestas nulla id, varius lectus. Nam laoreet, nunc id egestas molestie, ligula orci accumsan mi, sed molestie dui magna at neque. Nam urna diam, ultrices nec lacinia a, egestas nec arcu. Maecenas faucibus nulla vel lorem sagittis pellentesque. Etiam venenatis ornare gravida.");
				layout.addComponent(organizationDescription);
				organizationDescription.setWidth("100%");
			}
		});

		layout.addComponent(addComponentsButton);
		// addComponentsButton.setWidth("80%");

		layout.setSizeUndefined();
		layout.setWidth("100%");
		// layout.setSizeFull();
		layout.setMargin(true);
		layout.setSpacing(true);
	}

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MaterializeTest.class)
	public static class Servlet extends VaadinServlet {
		@Override
		protected void servletInitialized() throws ServletException {
			super.servletInitialized();
			getService().addSessionInitListener(new SessionInitListener() {

				@Override
				public void sessionInit(SessionInitEvent event) {
					event.getSession().addBootstrapListener(polymerInjector);
				}
			});
		}
	}

	/**
	 * This injects polymer.js and es6 support polyfils directly into host page.
	 *
	 * Better compatibility and good approach if you have multiple webcomponents in the app.
	 */
	public static BootstrapListener polymerInjector = new BootstrapListener() {

		@Override
		public void modifyBootstrapFragment(BootstrapFragmentResponse response) {
		}

		@Override
		public void modifyBootstrapPage(BootstrapPageResponse response) {
			Element head = response.getDocument().getElementsByTag("head").get(0);

			// add polymer for older browsers
			// -------------------------------------------------
			// Element polymer = response.getDocument().createElement("script");
			// polymer.attr("src",
			// "VAADIN/webcomponents/polymer-platform/platform.js");
			// head.appendChild(polymer);
			// ---------------------------------------------------------------------------------

			// add es6 support for older browsers
			// ----------------------------------------------
			// Element traceur = response.getDocument().createElement("script");
			// traceur.attr("src",
			// "VAADIN/webcomponents/traceur-runtime/traceur-runtime.min.js");
			// head.appendChild(traceur);
			// ---------------------------------------------------------------------------------

			head.prependElement("meta").attr("name", "viewport").attr("content", "width=device-width, initial-scale=1.0");

			{
				Element script = response.getDocument().createElement("script");
				script.attr("src", "VAADIN/webcomponents/bower_components/jquery-3.1.1.min/index.js");
				head.appendChild(script);
			}

			{
				Element script = response.getDocument().createElement("script");
				script.attr("src", "VAADIN/webcomponents/materializeWrap.js");
				head.appendChild(script);
			}

			{
				Element script = response.getDocument().createElement("script");
				script.attr("src", "VAADIN/webcomponents/bower_components/materialize/dist/js/materialize.js");
				head.appendChild(script);
			}

			// {
			// Element link = response.getDocument().createElement("link");
			// link.attr("rel", "stylesheet");
			// link.attr("media", "screen,projection");
			// link.attr("href",
			// "VAADIN/webcomponents/bower_components/materialize/dist/css/materialize.min.css");
			// head.appendChild(link);
			// }

		}
	};
}
