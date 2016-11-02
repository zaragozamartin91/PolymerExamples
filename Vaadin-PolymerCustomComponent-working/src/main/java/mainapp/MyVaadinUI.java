package mainapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.jsoup.nodes.Element;

import ast.unicore.view.webcomponent.imports.WebImport;
import ast.unicore.view.webcomponent.paperbutton.PaperButton;
import ast.unicore.view.webcomponent.paperbutton.PaperButton.ClickListener;
import ast.unicore.view.webcomponent.papercheckbox.PaperCheckbox;
import ast.unicore.view.webcomponent.papercombo.PaperCombo;
import ast.unicore.view.webcomponent.paperinput.date.PaperDateInput;
import ast.unicore.view.webcomponent.paperinput.text.PaperTextArea;
import ast.unicore.view.webcomponent.paperinput.text.PaperTextInput;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("dawn")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private PaperTextInput organizationInput;
	private PaperDateInput paperDateInput;
	private PaperCombo organizationCombo;
	private PaperTextInput disabledInput;
	private PaperCombo peopleCombo;

	private PaperCheckbox marriedCheck;
	private PaperCheckbox boyCheck;
	private PaperCheckbox agreeCheck;

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
				organizationCombo = new PaperCombo("Organizaciones");
				layout.addComponent(organizationCombo);
				organizationCombo.addItem("Macro");
				organizationCombo.addItem("Claro", "Empresa de claro");
				organizationCombo.addItem("Movistar", "Empresa movistar");
				organizationCombo.addItem("Geotex", "Empresa geotex");
				organizationCombo.setWidth("100%");
				PaperCombo.ValueChangeListener<String> comboValueChangeListener = new PaperCombo.ValueChangeListener<String>() {
					@Override
					public void valueChange(String caption, String value) {
						Notification.show(caption + " seleccionado. Valor: " + value);
					}
				};
				organizationCombo.addValueChangeListener(comboValueChangeListener);
				organizationCombo.setSelected("Geotex");

				peopleCombo = new PaperCombo("PERSONAS");
				peopleCombo.addItem("Martin");
				peopleCombo.addItem("Mateo", "Hermano");
				peopleCombo.addItem("Hector", "Padre");
				layout.addComponent(peopleCombo);
				peopleCombo.setWidth("50%");
				peopleCombo.addValueChangeListener(comboValueChangeListener);

				organizationInput = new PaperTextInput("Nombre organizacion");
				// organizationInput.setPattern("[a-zA-Z ]+");
				// organizationInput.setErrorMessage("Nombre de organizacion invalido!");
				organizationInput.setRequired(true);
				organizationInput.setValue("ACCUSYS");
				layout.addComponent(organizationInput);
				organizationInput.setWidth("60%");

				organizationInput.autoValidate();
				organizationInput.addValidator(new Validator() {
					@Override
					public void validate(Object value) throws InvalidValueException {
						Notification.show("Validando: " + value);
						if (value == null || value.toString().isEmpty()) {
							throw new InvalidValueException("Campo no puede ser vacio!");
						}
					}
				});
				organizationInput.addValueChangeListener(new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						Notification.show("" + event.getProperty().getValue());
					}
				});

				PaperTextArea paperTextArea = new PaperTextArea("Descripcion empresa");
				paperTextArea.setWidth("100%");
				layout.addComponent(paperTextArea);
				paperTextArea.addValidator(new Validator() {
					@Override
					public void validate(Object value) throws InvalidValueException {
						if (value == null || value.toString().length() < 10) {
							throw new InvalidValueException("Longitud de descripcion muy pequeña!");
						}
					}
				});
				paperTextArea.addValueChangeListener(new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						Notification.show("" + event.getProperty().getValue());
					}
				});
				paperTextArea.setRequired(true, "Campo no puede ser vacio!");

				paperDateInput = new PaperDateInput("Fecha de cumpleaños");
				layout.addComponent(paperDateInput);
				try {
					paperDateInput.setValue(DATE_FORMAT.parse("2016-03-21"));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				paperDateInput.addValueChangeListener(new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						Notification.show("" + event.getProperty().getValue());
					}
				});

				disabledInput = new PaperTextInput("Disabled!");
				disabledInput.disable();
				layout.addComponent(disabledInput);

				marriedCheck = new PaperCheckbox("Casado");
				marriedCheck.addValueChangeListener(new PaperCheckbox.ValueChangeListener() {
					@Override
					public void change(boolean value) {
						Notification.show("" + value);
					}
				});
				marriedCheck.setValue(true);
				layout.addComponent(marriedCheck);

				boyCheck = new PaperCheckbox("Chico", true);
				layout.addComponent(boyCheck);

				agreeCheck = new PaperCheckbox("De acuerdo", false, new PaperCheckbox.ValueChangeListener() {
					@Override
					public void change(boolean value) {
						System.out.println("agreeCheck cambio a " + value);
					}
				});
				layout.addComponent(new HorizontalLayout(agreeCheck, new PaperButton("PaperCheckbox#getValue()", new PaperButton.ClickListener() {
					@Override
					public void buttonClick() {
						Notification.show("De acuerdo: " + agreeCheck.getValue());
					}
				})));

			}
		});

		final PaperButton addComboItemButton = new PaperButton("Agregar organizacion", new ClickListener() {
			@Override
			public void buttonClick() {
				// Notification.show(paperTextfield.getValue());
				organizationCombo.addItem(organizationInput.getValue());
			}
		});

		PaperButton comboStateButton = new PaperButton("PaperCombo#getValue()", new ClickListener() {
			@Override
			public void buttonClick() {
				String value = organizationCombo.getValue();
				Notification.show(value == null ? "NOTHING" : value);
			}
		});

		PaperButton validateButton = new PaperButton("PaperTextInput#validate()", new ClickListener() {
			@Override
			public void buttonClick() {
				try {
					organizationInput.validate();
				} catch (Exception e) {
					Notification.show("Valor invalido!", Type.ERROR_MESSAGE);
				}
			}
		});

		PaperButton toggleVisible = new PaperButton("Cambiar visible", new ClickListener() {
			@Override
			public void buttonClick() {
				organizationInput.setVisible(!organizationInput.isVisible());
				disabledInput.setVisible(!disabledInput.isVisible());
				organizationCombo.setVisible(!organizationCombo.isVisible());
			}
		});

		PaperButton toggleEnabled = new PaperButton("Cambiar habilitado", new ClickListener() {
			@Override
			public void buttonClick() {
				organizationInput.setEnabled(!organizationInput.isEnabled());
				disabledInput.setEnabled(!disabledInput.isEnabled());
				organizationCombo.setEnabled(!organizationCombo.isEnabled());
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

		PaperButton clearCombosButton = new PaperButton("PaperCombo#empty()", new ClickListener() {
			@Override
			public void buttonClick() {
				organizationCombo.empty();
				peopleCombo.empty();
			}
		});
		layout.addComponent(clearCombosButton);

		// TextField textField = new TextField("LAME");
		// textField.addValueChangeListener(new ValueChangeListener() {
		// @Override
		// public void valueChange(ValueChangeEvent event) {
		// System.out.println("LAME CHANGED VALUE!");
		// }
		// });
		// textField.setValue("NEW VALUE");

		layout.addComponent(new PaperButton("PaperCombo#clear()", new ClickListener() {
			@Override
			public void buttonClick() {
				organizationCombo.clear();
				peopleCombo.clear();
			}
		}));

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
