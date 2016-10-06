package ast.unicore.view.webcomponent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.jsoup.nodes.Element;

import ast.unicore.view.webcomponent.imports.WebImport;
import ast.unicore.view.webcomponent.paperbutton.PaperButton;
import ast.unicore.view.webcomponent.paperbutton.PaperButton.ClickListener;
import ast.unicore.view.webcomponent.papercombo.PaperCombo;
import ast.unicore.view.webcomponent.paperinput.InvalidInputException;
import ast.unicore.view.webcomponent.paperinput.date.PaperDateInput;
import ast.unicore.view.webcomponent.paperinput.text.PaperTextArea;
import ast.unicore.view.webcomponent.paperinput.text.PaperTextInput;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("dawn")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private PaperTextInput paperInput;
	private PaperDateInput paperInputDate;
	private PaperCombo paperCombo;
	// private PaperVaadCalendar paperVaadCalendar;
	private PaperTextInput disabledInput;

	// private PaperAutocompleteInput typeAheadInput;

	// private VaadinComboBox vaadinComboBox;

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		setContent(layout);

		PaperButton addComponentsButton = new PaperButton("Agregar componentes");

		layout.addComponentAttachListener(new ComponentAttachListener() {
			@Override
			public void componentAttachedToContainer(ComponentAttachEvent event) {
				System.out.println("Componente agregado");
			}
		});

		addComponentsButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick() {
				// typeAheadInput = new
				// PaperAutocompleteInput("Seleccione pais");
				// typeAheadInput.addItem("Argentina");
				// typeAheadInput.addItem("Brasil");
				// typeAheadInput.addItem("Chile");
				// typeAheadInput.addItem("Peru");
				// typeAheadInput.addItem("Bolivia");
				// layout.addComponent(typeAheadInput);
				//
				// PaperAutocompleteInput peopleAutocomplete = new
				// PaperAutocompleteInput("Personas")
				// .addItem("martin", new Person("martin", 1234)).addItem("exe",
				// new Person("exe", 45678))
				// .addItem("julio", new Person("julio", 9654));
				// layout.addComponent(peopleAutocomplete);

				paperCombo = new PaperCombo("Organizaciones");
				layout.addComponent(paperCombo);
				paperCombo.addItem("Macro");
				paperCombo.addItem("Claro", "Empresa de claro");
				paperCombo.addItem("Movistar", "Empresa movistar");
				paperCombo.addItem("Geotex", "Empresa geotex");
				paperCombo.setWidth("50%");

				PaperCombo peopleCombo = new PaperCombo("PERSONAS");
				peopleCombo.addItem("Martin");
				peopleCombo.addItem("Mateo", "Hermano");
				peopleCombo.addItem("Hector", "Padre");
				layout.addComponent(peopleCombo);
				peopleCombo.setWidth("100%");

				paperInput = new PaperTextInput("Nombre organizacion");
				paperInput.setPattern("[a-zA-Z ]+");
				paperInput.setErrorMessage("Nombre de organizacion invalido!");
				paperInput.setRequired(true);
				paperInput.setValue("ACCUSYS");
				layout.addComponent(paperInput);
				paperInput.setWidth("60%");
				paperInput.enableDefaultClientComponentValidator();

				PaperTextArea paperTextArea = new PaperTextArea("Descripcion empresa");
				paperTextArea.setWidth("100%");
				layout.addComponent(paperTextArea);

				paperInputDate = new PaperDateInput("Fecha de cumpleaños");
				layout.addComponent(paperInputDate);
				try {
					paperInputDate.setValue(DATE_FORMAT.parse("2016-03-21"));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

				disabledInput = new PaperTextInput("Disabled!");
				disabledInput.disable();
				layout.addComponent(disabledInput);

			}
		});

		final PaperButton addComboItemButton = new PaperButton("Agregar organizacion", new ClickListener() {
			@Override
			public void buttonClick() {
				// Notification.show(paperTextfield.getValue());
				paperCombo.addItem(paperInput.getValue());
			}
		});

		PaperButton comboStateButton = new PaperButton("Estado de Combo", new ClickListener() {
			@Override
			public void buttonClick() {
				Object value = paperCombo.getValue();
				Notification.show(value == null ? "NOTHING" : value.toString());
			}
		});

		PaperButton validateButton = new PaperButton("Validar", new ClickListener() {
			@Override
			public void buttonClick() {
				try {
					paperInput.validate();
					System.out.println("PaperInputDate value: " + paperInputDate.getValue());
				} catch (InvalidInputException e) {
					Notification.show("Contenido invalido::" + e.getMessage(), Type.ERROR_MESSAGE);
				}
			}
		});

		// PaperButton typeAheadValueButton = new
		// PaperButton("Valor de typeAhead", new ClickListener() {
		// @Override
		// public void handleClick() {
		// Object value = typeAheadInput.getValue();
		// Notification.show(value == null ? "NOTHING" : value.toString());
		// }
		// });

		PaperButton toggleVisible = new PaperButton("Cambiar visible", new ClickListener() {
			@Override
			public void buttonClick() {
				paperInput.setVisible(!paperInput.isVisible());
				disabledInput.setVisible(!disabledInput.isVisible());
				paperCombo.setVisible(!paperCombo.isVisible());
				// paperVaadCalendar.setVisible(!paperVaadCalendar.isVisible());
				// typeAheadInput.setVisible(!typeAheadInput.isVisible());
			}
		});

		PaperButton toggleEnabled = new PaperButton("Cambiar habilitado", new ClickListener() {
			@Override
			public void buttonClick() {
				paperInput.setEnabled(!paperInput.isEnabled());
				disabledInput.setEnabled(!disabledInput.isEnabled());
				paperCombo.setEnabled(!paperCombo.isEnabled());
				// paperVaadCalendar.setEnabled(!paperVaadCalendar.isEnabled());
				// typeAheadInput.setEnabled(!typeAheadInput.isEnabled());
			}
		});

		layout.addComponent(addComboItemButton);
		addComboItemButton.setWidth("100%");

		layout.addComponent(comboStateButton);
		comboStateButton.setWidth("75%");

		layout.addComponent(addComponentsButton);
		// addComponentsButton.setClickShortcut(KeyCode.ENTER);

		layout.addComponent(validateButton);
		layout.addComponent(toggleVisible);

		Button lameButton = new Button("LAME BUTTON", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("LAME PRESSED!");
			}
		});
		lameButton.setClickShortcut(KeyCode.ENTER);
		layout.addComponent(lameButton);
		lameButton.setVisible(false);

		layout.addComponent(toggleEnabled);

		layout.setSizeUndefined();
		layout.setWidth("100%");
		layout.setMargin(true);
		layout.setSpacing(true);
	}

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class)
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
	 * Better compatibility and good approach if you have multiple webcomponents
	 * in the app.
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

			{
				Element script = response.getDocument().createElement("script");
				script.attr("src", "VAADIN/webcomponents/bower_components/webcomponentsjs/webcomponents-lite.min.js");
				head.appendChild(script);
			}

			// {
			// Element link = response.getDocument().createElement("link");
			// link.attr("rel", "import");
			// link.attr("href",
			// "VAADIN/webcomponents/bower_components/vaadin-core-elements/vaadin-core-elements.html");
			// head.appendChild(link);
			// }

			WebImport[] webImports = WebImport.values();
			for (WebImport webImport : webImports) {
				Element link = response.getDocument().createElement("link");
				link.attr("rel", "import");
				link.attr("href", webImport.path);
				head.appendChild(link);
			}
		}
	};

	static class Person {
		public final String name;
		public final int id;

		public Person(String name, int id) {
			super();
			this.name = name;
			this.id = id;
		}
	}
}
