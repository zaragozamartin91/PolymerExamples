package org.vaadin.webcomponent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.jsoup.nodes.Element;
import org.vaadin.maddon.layouts.MVerticalLayout;
import org.vaadin.webcomponent.paperbutton.PaperButton;
import org.vaadin.webcomponent.paperbutton.PaperButton.ClickListener;
import org.vaadin.webcomponent.papercombo.PaperCombo;
import org.vaadin.webcomponent.paperinput.InputValidator.InvalidInputException;
import org.vaadin.webcomponent.paperinput.PaperInput;
import org.vaadin.webcomponent.papertypeaheadinput.PaperTypeAheadInput;
import org.vaadin.webcomponent.papervaadcalendar.PaperVaadCalendar;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("dawn")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {
	private PaperInput paperTextfield;
	private PaperCombo paperCombo;
	private PaperVaadCalendar paperVaadCalendar;
	private PaperInput disabledInput;
	private PaperTypeAheadInput typeAheadInput;

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new MVerticalLayout();
		setContent(layout);

		PaperButton button = new PaperButton("Add Custom component");

		layout.addComponentAttachListener(new ComponentAttachListener() {
			public void componentAttachedToContainer(ComponentAttachEvent event) {
				System.out.println("Component was attached!");
			}
		});

		button.addListener(new ClickListener() {
			public void handleClick() {
				paperTextfield = new PaperInput("Nombre organizacion");
				paperTextfield.setPattern("[a-zA-Z ]+");
				paperTextfield.setErrorMessage("Nombre de organizacion invalido!");
				paperTextfield.setRequired(true);
				layout.addComponent(paperTextfield);
				paperTextfield.setWidth("100%");
				paperTextfield.enableDefaultClientComponentValidator();
				
				typeAheadInput = new PaperTypeAheadInput("Seleccione pais");
				typeAheadInput.addItem("Argentina");
				typeAheadInput.addItem("Brasil");
				typeAheadInput.addItem("Chile");
				typeAheadInput.addItem("Peru");
				typeAheadInput.addItem("Bolivia");
				layout.addComponent(typeAheadInput);

				disabledInput = new PaperInput("Disabled!");
				disabledInput.disable();
				layout.addComponent(disabledInput);

				paperCombo = new PaperCombo("Organizaciones");
				layout.addComponent(paperCombo);
				paperCombo.addItem("Macro");
				paperCombo.addItem("Claro", "Empresa de claro");
				paperCombo.addItem("Movistar", "Empresa movistar");
				paperCombo.addItem("Geotex", "Empresa geotex");

				try {
					paperVaadCalendar = new PaperVaadCalendar().date("2016/06/01").locale("es").headingFormat("ddd, D MMM").minDate("2016/05/25")
							.maxDate("2016/07/17").responsiveWidth(560);
					layout.addComponent(paperVaadCalendar);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});

		final PaperButton addComboItemButton = new PaperButton("Agregar organizacion", new ClickListener() {
			@Override
			public void handleClick() {
				// Notification.show(paperTextfield.getValue());
				paperCombo.addItem(paperTextfield.getValue());
			}
		});

		PaperButton comboStateButton = new PaperButton("Estado de Combo", new ClickListener() {
			@Override
			public void handleClick() {
				Object value = paperCombo.getValue();
				Notification.show(value == null ? "NOTHING" : value.toString());
			}
		});

		PaperButton validateButton = new PaperButton("Validar", new ClickListener() {
			@Override
			public void handleClick() {
				try {
					paperTextfield.validate();
				} catch (InvalidInputException e) {
					Notification.show("Contenido invalido::" + e.getMessage(), Type.ERROR_MESSAGE);
				}
			}
		});
		
		PaperButton typeAheadValueButton = new PaperButton("Valor de typeAhead", new ClickListener() {
			@Override
			public void handleClick() {
				Object value = typeAheadInput.getValue();
				Notification.show(value == null ? "NOTHING" : value.toString());
			}
		});

		PaperButton toggleVisible = new PaperButton("Cambiar visible", new ClickListener() {
			@Override
			public void handleClick() {
				paperTextfield.setVisible(!paperTextfield.isVisible());
				disabledInput.setVisible(!disabledInput.isVisible());
				paperCombo.setVisible(!paperCombo.isVisible());
				paperVaadCalendar.setVisible(!paperVaadCalendar.isVisible());
				typeAheadInput.setVisible(!typeAheadInput.isVisible());
			}
		});
		
		PaperButton toggleEnabled = new PaperButton("Cambiar habilitado", new ClickListener() {
			@Override
			public void handleClick() {
				paperTextfield.setEnabled(!paperTextfield.isEnabled());
				disabledInput.setEnabled(!disabledInput.isEnabled());
				paperCombo.setEnabled(!paperCombo.isEnabled());
				paperVaadCalendar.setEnabled(!paperVaadCalendar.isEnabled());
				typeAheadInput.setEnabled(!typeAheadInput.isEnabled());				
			}
		});

		layout.addComponent(addComboItemButton);
		layout.addComponent(comboStateButton);
		layout.addComponent(button);
		layout.addComponent(validateButton);
		layout.addComponent(toggleVisible);
		layout.addComponent(toggleEnabled);
		layout.addComponent(typeAheadValueButton);

	}

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class)
	public static class Servlet extends VaadinServlet {
		@Override
		protected void servletInitialized() throws ServletException {
			super.servletInitialized();
			getService().addSessionInitListener(new SessionInitListener() {

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

			// add polymer for older browsers -------------------------------------------------
			// Element polymer = response.getDocument().createElement("script");
			// polymer.attr("src", "VAADIN/webcomponents/polymer-platform/platform.js");
			// head.appendChild(polymer);
			// ---------------------------------------------------------------------------------

			// add es6 support for older browsers ----------------------------------------------
			// Element traceur = response.getDocument().createElement("script");
			// traceur.attr("src", "VAADIN/webcomponents/traceur-runtime/traceur-runtime.min.js");
			// head.appendChild(traceur);
			// ---------------------------------------------------------------------------------

			{
				Element script = response.getDocument().createElement("script");
				script.attr("src", "VAADIN/webcomponents/bower_components/webcomponentsjs/webcomponents-lite.min.js");
				head.appendChild(script);
			}

			{
				Element link = response.getDocument().createElement("link");
				link.attr("rel", "import");
				link.attr("href", "VAADIN/webcomponents/paper-textfield.html");
				head.appendChild(link);
			}

			{
				Element link = response.getDocument().createElement("link");
				link.attr("rel", "import");
				link.attr("href", "VAADIN/webcomponents/bower_components/polymer/polymer.html");
				head.appendChild(link);
			}

			{
				Element link = response.getDocument().createElement("link");
				link.attr("rel", "import");
				link.attr("href", "VAADIN/webcomponents/bower_components/paper-input/paper-input.html");
				head.appendChild(link);
			}

			{
				Element link = response.getDocument().createElement("link");
				link.attr("rel", "import");
				link.attr("href", "VAADIN/webcomponents/bower_components/paper-item/paper-item.html");
				head.appendChild(link);
			}

			{
				Element link = response.getDocument().createElement("link");
				link.attr("rel", "import");
				link.attr("href", "VAADIN/webcomponents/bower_components/paper-listbox/paper-listbox.html");
				head.appendChild(link);
			}

			{
				Element link = response.getDocument().createElement("link");
				link.attr("rel", "import");
				link.attr("href", "VAADIN/webcomponents/bower_components/paper-dropdown-menu/paper-dropdown-menu.html");
				head.appendChild(link);
			}

			{
				Element link = response.getDocument().createElement("link");
				link.attr("rel", "import");
				link.attr("href", "VAADIN/webcomponents/paper-combo.html");
				head.appendChild(link);
			}

			{
				Element link = response.getDocument().createElement("link");
				link.attr("rel", "import");
				link.attr("href", "VAADIN/webcomponents/bower_components/paper-button/paper-button.html");
				head.appendChild(link);
			}

			{
				Element link = response.getDocument().createElement("link");
				link.attr("rel", "import");
				link.attr("href", "VAADIN/webcomponents/paper-vaad-calendar.html");
				head.appendChild(link);
			}

			{
				Element link = response.getDocument().createElement("link");
				link.attr("rel", "import");
				link.attr("href", "VAADIN/webcomponents/bower_components/paper-typeahead-input/paper-typeahead-input.html");
				head.appendChild(link);
			}
		}
	};
}
