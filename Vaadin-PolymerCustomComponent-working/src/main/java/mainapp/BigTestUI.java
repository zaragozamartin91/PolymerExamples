package mainapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.jsoup.nodes.Element;

import ast.unicore.view.webcomponent.icons.iron.IronIcon;
import ast.unicore.view.webcomponent.imports.WebImport;
import ast.unicore.view.webcomponent.paperbutton.PaperButton;
import ast.unicore.view.webcomponent.paperbutton.PaperButton.ClickListener;
import ast.unicore.view.webcomponent.papercheckbox.PaperCheckbox;
import ast.unicore.view.webcomponent.papercombo.PaperCombo;
import ast.unicore.view.webcomponent.paperinput.date.PaperDateInput;
import ast.unicore.view.webcomponent.paperinput.text.PaperTextArea;
import ast.unicore.view.webcomponent.paperinput.text.PaperTextInput;
import ast.unicore.view.webcomponent.table.Column;
import ast.unicore.view.webcomponent.table.IconColumn;
import ast.unicore.view.webcomponent.table.ResponsiveTable;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Theme("dawn")
@SuppressWarnings("serial")
public class BigTestUI extends UI {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private PaperTextInput organizationInput;
	private PaperDateInput paperDateInput;
	private PaperCombo organizationCombo;
	private PaperTextInput disabledInput;
	private PaperCombo peopleCombo;

	private PaperCheckbox marriedCheck;
	private PaperCheckbox boyCheck;
	private PaperCheckbox agreeCheck;

	private ResponsiveTable responsiveTable;

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		setContent(layout);

		PaperButton addComponentsButton = new PaperButton("Agregar componentes");

		addComponentsButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick() {
				organizationCombo = new PaperCombo("Organizaciones");
				layout.addComponent(organizationCombo);
				organizationCombo.addItem("Macro");
				organizationCombo.addItem("Claro", "Empresa de claro");
				organizationCombo.addItem("Movistar", "Empresa movistar");
				organizationCombo.addItem("Geotex", "Empresa geotex");
				organizationCombo.addItem("Geotex", "Empresa geotexxx");
				organizationCombo.addItem("Geotex", "EMPRESA GEOTEX");
				organizationCombo.addItem("Movistar", "Empresa MOVISTAR");
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
				organizationInput.setRequired(true, "Campo no puede ser vacio [REQUERIDO]!");
				organizationInput.setEnabled(false);
				organizationInput.setValue("ACCUSYS");
				layout.addComponent(organizationInput);
				organizationInput.setWidth("60%");
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
						Notification.show("" + event.getProperty().getValue());
					}
				});
				organizationInput.addFocusListener(new FocusListener() {
					@Override
					public void focus(FocusEvent event) {
						Notification.show("FOCUS");
					}
				});

				PaperTextArea paperTextArea = new PaperTextArea("Descripcion empresa");
				paperTextArea.setWidth("100%");
				layout.addComponent(paperTextArea);
				paperTextArea.autoValidate();
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
				paperDateInput.setWidth("100%");

				disabledInput = new PaperTextInput("Disabled!");
				disabledInput.disable();
				// disabledInput.setValue("Hola");
				layout.addComponent(disabledInput);

				final PaperTextInput disabledSetter = new PaperTextInput("Modifica disabled input");
				disabledSetter.addValueChangeListener(new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						// disabledInput.setEnabled(true);
						disabledInput.setValue(event.getProperty().getValue().toString());
					}
				});
				layout.addComponent(disabledSetter);

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

				// PRUEBA RESPONSIVE TABLE
				// ------------------------------------------------------------------------------------------------------

				final PaperTextInput rowDataInput = new PaperTextInput("Ingresa fila como: valor1,valor2,... || clave1:valor1, clave2:valor2, ...");
				rowDataInput.setWidth("50%");
				PaperButton rowAddButton = new PaperButton("Agregar fila", new ClickListener() {
					@Override
					public void buttonClick() {
						try {
							String rowData = rowDataInput.getValue();
							if (rowData.contains(":")) {
								Map<String, Object> rowMap = parseRowAsMap(rowData);
								responsiveTable.addRow(rowMap);
							} else {
								List<Object> values = parseRowAsValues(rowData);
								responsiveTable.addRow(values.toArray());
							}
						} catch (Exception e) {
							Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
						}
					}
				});
				layout.addComponent(rowDataInput);
				layout.addComponent(rowAddButton);
				layout.addComponent(new PaperButton("Vaciar filas", new ClickListener() {
					@Override
					public void buttonClick() {
						responsiveTable.empty();
					}
				}));
				final PaperTextInput rowIndexInput = new PaperTextInput("Indice fila");
				layout.addComponent(rowIndexInput);
				layout.addComponent(new PaperButton("Remover fila", new ClickListener() {
					@Override
					public void buttonClick() {
						try {
							int rowIndex = Integer.parseInt(rowIndexInput.getValue());
							responsiveTable.removeRow(rowIndex);
						} catch (NumberFormatException e) {
							Notification.show("Valor invalido!", Type.ERROR_MESSAGE);
						}
					}
				}));
				layout.addComponent(new PaperButton("Modificar fila", new ClickListener() {
					@Override
					public void buttonClick() {
						try {
							int rowIndex = Integer.parseInt(rowIndexInput.getValue());
							String rowData = rowDataInput.getValue();
							if (rowData.contains(":")) {
								Map<String, Object> rowMap = parseRowAsMap(rowData);
								responsiveTable.setRow(rowIndex, rowMap);
							} else {
								List<Object> values = parseRowAsValues(rowData);
								responsiveTable.setRow(rowIndex, values.toArray());
							}
						} catch (Exception e) {
							Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
						}
					}
				}));

				responsiveTable = new ResponsiveTable("ID", "Name", "Salary", IconColumn.newEmptynamed(IronIcon.EDIT, IronIcon.DELETE));
				layout.addComponent(responsiveTable);
				responsiveTable.setWidth("100%");
				responsiveTable.addRow(1, "Martin", 1200.5, "_");
				responsiveTable.addRow(2, "Julio", 2500.25, "_");
				responsiveTable.addRow(3, "Exequiel", 3750.71, "_");
				responsiveTable.addClickListener(new ResponsiveTable.ClickListener() {
					@Override
					public void iconClick(Column column, Map<String, Object> row, int rowIndex, IronIcon icon) {
						switch (icon) {
							case DELETE:
								responsiveTable.removeRow(rowIndex);
								break;
							case EDIT:
								String rowData = rowDataInput.getValue() == null ? "" : rowDataInput.getValue();
								if (!rowData.isEmpty()) {
									if (rowData.contains(":")) {
										Map<String, Object> rowMap = parseRowAsMap(rowData);
										responsiveTable.setRow(rowIndex, rowMap);
									} else {
										List<Object> values = parseRowAsValues(rowData);
										responsiveTable.setRow(rowIndex, values.toArray());
									}
									Notification.show("GUARDANDO: " + row);
									break;
								}
							default:
								break;
						}
					}
				});
			}
		});

		PaperButton addPopupButton = new PaperButton("Abrir popup", new ClickListener() {
			@Override
			public void buttonClick() {
				UI ui = BigTestUI.getCurrent();
				Window window = new Window("Combo popup");

				PaperCombo combo = new PaperCombo("Opciones");
				for (int i = 0; i < 20; i++) {
					combo.addItem("item_" + i);
				}
				int windowHeightPx = 150;
				// String comboHeight = 200 + "px";
				// combo.settDropdownContentHeight(comboHeight);

				VerticalLayout windowContent = new VerticalLayout(combo);
				windowContent.setMargin(true);
				windowContent.setSpacing(true);
				window.setContent(windowContent);

				window.setWidth("300px");
				window.setHeight(windowHeightPx + "px");
				window.setClosable(true);
				window.center();
				window.setModal(true);
				ui.addWindow(window);
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

		layout.addComponent(addComponentsButton);

		layout.addComponent(addPopupButton);

		layout.addComponent(addComboItemButton);
		addComboItemButton.setWidth("100%");

		layout.addComponent(comboStateButton);
		comboStateButton.setWidth("75%");

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

	private Map<String, Object> parseRowAsMap(String rowData) {
		Map<String, Object> rowMap = new HashMap<>();
		String[] keysAndValues = rowData.split(Pattern.quote(","));
		for (String keyValue : keysAndValues) {
			String[] keyValueSplit = keyValue.split(Pattern.quote(":"));
			rowMap.put(keyValueSplit[0].trim(), keyValueSplit[1].trim());
		}
		return rowMap;
	}

	private List<Object> parseRowAsValues(String rowData) {
		String[] rowValues = rowData.split(Pattern.quote(","));
		List<Object> values = new ArrayList<>();
		for (String value : rowValues) {
			values.add((value == null) ? "" : value.trim());
		}
		return values;
	}

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = BigTestUI.class)
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

			// {
			// Element script = response.getDocument().createElement("script");
			// script.attr("src", "VAADIN/webcomponents/initScript.js");
			// head.appendChild(script);
			// }

			{
				Element script = response.getDocument().createElement("script");
				script.attr("src", "VAADIN/webcomponents/bower_components/webcomponentsjs/webcomponents-lite.min.js");
				head.appendChild(script);
			}

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
