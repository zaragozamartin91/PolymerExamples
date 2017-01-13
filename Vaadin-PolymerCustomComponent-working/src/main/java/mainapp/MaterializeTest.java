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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ast.unicore.view.webcomponent.paperbutton.PaperButton;
import ast.unicore.view.webcomponent.paperbutton.PaperButton.ClickListener;
import ast.unicore.view.webcomponent.papercombo.PaperCombo;
import ast.unicore.view.webcomponent.paperinput.date.PaperDateInput;
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
		setContent(layout);

		TextField sampleTextfield = new TextField("Prueba");
		sampleTextfield.setWidth("70%");
		layout.addComponent(sampleTextfield);

		PaperButton addComponentsButton = new PaperButton("Agregar componentes");

		Button sampleButton = new Button("sample button");
		layout.addComponent(sampleButton);

		addComponentsButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick() {
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
				// organizationInput.setRequired(true, "Campo no puede ser vacio [REQUERIDO]!");
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

				PaperCombo peopleCombo = new PaperCombo("Persona");
				peopleCombo.addItem("Martin");
				peopleCombo.addItem("Exe");
				peopleCombo.addItem("Mauri");
				peopleCombo.addItem("Roland");
				layout.addComponent(peopleCombo);
				peopleCombo.setWidth("50%");
			}
		});

		layout.addComponent(addComponentsButton);
		addComponentsButton.setWidth("80%");

		layout.setSizeUndefined();
		layout.setWidth("100%");
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
				script.attr("src", "VAADIN/webcomponents/bower_components/materialize/dist/js/materialize.min.js");
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
