package mainapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.jsoup.nodes.Element;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
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
import ast.unicore.view.webcomponent.style.PaperStyle;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MaterializeTest extends UI {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private PaperTextInput organizationInput;
	private PaperDateInput paperDateInput;

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.addStyleName(PaperStyle.OVERFLOWY.name);
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
				Collection<?> options = Arrays.asList(new String[] { "uno", "dos", "tres" });
				final ComboBox sampleCombo = new ComboBox("Sample combo", options);
				PaperButton removeFirstItemButton = new PaperButton("Remove first item", new PaperButton.ClickListener() {
					@Override
					public void buttonClick() {
						try {
							Object firstItemId = sampleCombo.getItemIds().iterator().next();
							sampleCombo.removeItem(firstItemId);
						} catch (UnsupportedOperationException e) {
							Notification.show("No hay mas items", Type.ERROR_MESSAGE);
						}
					}
				});
				PaperButton getSampleComboValueButton = new PaperButton("Get value", new PaperButton.ClickListener() {
					@Override
					public void buttonClick() {
						Notification.show("" + sampleCombo.getValue());
					}
				});
				layout.addComponent(new HorizontalLayout(sampleCombo, removeFirstItemButton, getSampleComboValueButton));

				PaperButton undefinedSizePopupButton = new PaperButton("Undefined size popup", new PaperButton.ClickListener() {
					@Override
					public void buttonClick() {
						UI ui = MaterializeTest.getCurrent();
						// Window popupWindow = new Window("Undefined size popup");
						//
						// VerticalLayout itemsLayout = new VerticalLayout();
						// itemsLayout.setWidth("100%");
						// VerticalLayout windowContent = new VerticalLayout(itemsLayout);
						// windowContent.setSizeFull();
						//
						// for (int i = 0; i < 20; i++) {
						// PaperTextInput paperInput = new PaperTextInput("dato " + i);
						// itemsLayout.addComponent(paperInput);
						// }
						//
						// popupWindow.setContent(windowContent);
						// popupWindow.setWidth("50%");
						// popupWindow.setHeightUndefined();
						// popupWindow.center();
						// ui.addWindow(popupWindow);

						Window dialog = new Window("Dialog");
						dialog.center();
						ui.addWindow(dialog);

						VerticalLayout layout = new VerticalLayout();
						dialog.setContent(layout);
						layout.setSizeFull();
						layout.setSpacing(true);
						layout.setMargin(true);

						HorizontalLayout actions = new HorizontalLayout();
						actions.setSpacing(true);
						actions.addComponent(new Button("Add"));
						actions.addComponent(new Button("Close"));

						FormLayout form__ = new FormLayout();
						// VerticalLayout form = new VerticalLayout();

						FormLayout form = new FormLayout();

						form.setWidth(form__.getWidth(), form__.getWidthUnits());
						form.setHeight(form__.getHeight(), form__.getHeightUnits());
						for (int i = 0; i < 20; i++) {
							// form.addComponent(new Select("Foo"));
							// form.addComponent(new Select("Bar"));
							PaperCombo paperCombo = new PaperCombo("Foo");
							paperCombo.addItem("One");
							paperCombo.addItem("Two");
							paperCombo.addItem("Three");
							paperCombo.addItem("Four");
							paperCombo.addItem("five");
							paperCombo.addItem("six");
							paperCombo.setCompactMode(true);
							form.addComponent(paperCombo);
						}

						layout.addComponent(form);
						layout.addComponent(new Select("Competence"));
						layout.addComponent(actions);

						// Added this line and the layout started working
						layout.setSizeUndefined();
					}
				});
				layout.addComponent(undefinedSizePopupButton);

				PaperButton complexPopupButton = new PaperButton("complex popup", new PaperButton.ClickListener() {
					@Override
					public void buttonClick() {
						UI ui = MaterializeTest.getCurrent();
						Window popupWindow = new Window("Complex popup");

						final VerticalLayout headerLayout = new VerticalLayout(new Label("HEADER"));
						// headerLayout.setMargin(true);
						final VerticalLayout footerLayout = new VerticalLayout(new Label("FOOTER"));
						// footerLayout.setMargin(true);
						footerLayout.setSpacing(true);

						final VerticalLayout contentLayout = new VerticalLayout();
						VerticalLayout inputsLayout = new VerticalLayout();
						for (int i = 0; i < 20; i++) {
							PaperTextInput paperInput = new PaperTextInput("dato " + i);
							inputsLayout.addComponent(paperInput);
						}
						inputsLayout.addStyleName("avoid-overflow");
						contentLayout.addComponent(inputsLayout);
						contentLayout.setMargin(true);

						PaperCombo combo = new PaperCombo("Opciones");
						for (int i = 0; i < 40; i++) {
							combo.addItem("item_" + i);
						}
						combo.setWidth("100%");
						combo.setCompactMode(true);
						// combo.addStyleName("overflow-recursive-auto");
						contentLayout.addComponent(combo);

						Table table = new Table("My table");
						table.addContainerProperty("Numeric column", Integer.class, null);
						table.addContainerProperty("String column", String.class, null);
						table.addContainerProperty("Date column", Date.class, null);
						table.addItem(new Object[] { new Integer(100500), "this is first", new Date() }, new Integer(1));
						table.addItem(new Object[] { new Integer(100501), "this is second", new Date() }, new Integer(2));
						table.addItem(new Object[] { new Integer(100502), "this is third", new Date() }, new Integer(3));
						table.addItem(new Object[] { new Integer(100503), "this is forth", new Date() }, new Integer(4));
						table.addItem(new Object[] { new Integer(100504), "this is fifth", new Date() }, new Integer(5));
						table.addItem(new Object[] { new Integer(100505), "this is sixth", new Date() }, new Integer(6));
						table.setWidth("100%");
						table.setPageLength(5);
						// contentLayout.addComponent(table);

						// XXX: place the center layout into a panel, which allows scrollbars
						final Panel contentPanel = new Panel(contentLayout);
						contentPanel.setSizeFull();

						// XXX: add the panel instead of the layout
						final VerticalLayout mainLayout = new VerticalLayout(headerLayout, contentPanel, footerLayout);
						mainLayout.setSizeFull();
						mainLayout.setExpandRatio(contentPanel, 1);

						popupWindow.setContent(mainLayout);
						popupWindow.setModal(true);
						popupWindow.center();
						popupWindow.setWidth("300px");
						popupWindow.setHeight("60%");
						popupWindow.setResizable(false);

						// popupWindow.addStyleName("overflow-force-visible");
						// popupWindow.addStyleName("overflow-recursive-visible");

						ui.addWindow(popupWindow);
					}
				});
				layout.addComponent(complexPopupButton);

				paperDateInput = new PaperDateInput("fecha nacimiento");
				layout.addComponent(paperDateInput);
				paperDateInput.addValueChangeListener(new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						Notification.show(String.format("Fecha: %s", event.getProperty().getValue().toString()));
					}
				});
				paperDateInput.setValue(Calendar.getInstance().getTime());

				PaperButton getDateValueButton = new PaperButton("Obtener valor fecha", new ClickListener() {
					@Override
					public void buttonClick() {
						Notification.show(String.format("Fecha: %s", paperDateInput.getValue().toString()));
					}
				});
				layout.addComponent(getDateValueButton);

				PaperButton addPopupButton = new PaperButton("Abrir popup", new ClickListener() {
					@Override
					public void buttonClick() {
						UI ui = MaterializeTest.getCurrent();
						Window window = new Window("Combo popup");
						window.addStyleName(PaperStyle.OVERFLOWY.name);

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
				layout.addComponent(addPopupButton);

				layout.addComponent(new PaperCheckbox("uno", true, new PaperCheckbox.ValueChangeListener() {
					@Override
					public void change(boolean value) {
						Notification.show("" + value);
					}
				}));
				layout.addComponent(new PaperCheckbox("dos"));
				layout.addComponent(new PaperCheckbox("tres"));

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
				countryCombo.setWidth("100%");
				countryCombo.addValueChangeListener(new PaperCombo.ValueChangeListener<String>() {
					@Override
					public void valueChange(String selectedItemCaption, String selectedItemValue) {
						Notification.show(String.format("Seleccion: %s -> %s", selectedItemCaption, selectedItemValue));
					}
				});
				PaperButton getCountryComboValueButton = new PaperButton("Obtener valor", new PaperButton.ClickListener() {
					@Override
					public void buttonClick() {
						String value = countryCombo.getValue() == null ? "NULL" : countryCombo.getValue().toString();
						Notification.show(String.format("Valor del combo: %s", value));
					}
				});
				HorizontalLayout countryComboLayout = new HorizontalLayout(countryCombo, getCountryComboValueButton);
				countryComboLayout.setWidth("100%");
				countryComboLayout.setComponentAlignment(getCountryComboValueButton, Alignment.MIDDLE_CENTER);
				countryComboLayout.setExpandRatio(countryCombo, 1f);
				countryComboLayout.setSpacing(true);
				layout.addComponent(countryComboLayout);

				PaperButton countryComboDisable = new PaperButton("deshabilitar combo", new PaperButton.ClickListener() {
					@Override
					public void buttonClick() {
						countryCombo.setEnabled(false);
					}
				});
				PaperButton countryComboEnable = new PaperButton("Habilitar combo", new PaperButton.ClickListener() {
					@Override
					public void buttonClick() {
						countryCombo.setEnabled(true);
					}
				});
				layout.addComponent(new HorizontalLayout(countryComboDisable, countryComboEnable));

				PaperTextInput countrySelectInput = new PaperTextInput("Seleccione pais");
				countrySelectInput.addValueChangeListener(new ValueChangeListener() {
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
				countrySelectInput.setWidth("100%");
				PaperTextInput countryDeleteInput = new PaperTextInput("Eliminar pais");
				countryDeleteInput.addValueChangeListener(new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						String caption = event.getProperty().getValue().toString();
						if (countryCombo.remove(caption) == null) {
							Notification.show("elemento " + caption + " no existe!", Type.ERROR_MESSAGE);
						}
					}
				});
				countryDeleteInput.setWidth("100%");
				HorizontalLayout countrySelectAndDeleteLayout = new HorizontalLayout(countrySelectInput, countryDeleteInput);
				countrySelectAndDeleteLayout.setWidth("100%");
				countrySelectAndDeleteLayout.setSpacing(true);
				layout.addComponent(countrySelectAndDeleteLayout);

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
				script.attr("src", "VAADIN/webcomponents/bower_components/jquery-observe/jquery-observe.js");
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
