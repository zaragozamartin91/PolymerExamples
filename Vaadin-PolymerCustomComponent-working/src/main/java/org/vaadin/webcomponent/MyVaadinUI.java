package org.vaadin.webcomponent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.jsoup.nodes.Element;
import org.vaadin.maddon.layouts.MVerticalLayout;
import org.vaadin.webcomponent.paperbutton.PaperButton;
import org.vaadin.webcomponent.paperbutton.PaperButton.ClickListener;
import org.vaadin.webcomponent.papercombo.PaperCombo;
import org.vaadin.webcomponent.paperinput.InputValidator.InvalidInputException;
import org.vaadin.webcomponent.paperinput.PaperInput;

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
				paperTextfield = new PaperInput("Cuil");
				paperTextfield.setPattern("[a-zA-Z ]+");
				paperTextfield.setErrorMessage("Nombre de organizacion invalido!");
				paperTextfield.setRequired(true);
				layout.addComponent(paperTextfield);
				paperTextfield.setWidth("100%");
				paperTextfield.enableDefaultClientComponentValidator();
				
				PaperInput disabledInput = new PaperInput("Disabled!");
				disabledInput.disable();
				layout.addComponent(disabledInput);

				paperCombo = new PaperCombo("Organizations", true);
				layout.addComponent(paperCombo);
				paperCombo.addItem("Macro");
			}
		});

		PaperButton stateButton = new PaperButton("Add Item", new ClickListener() {
			@Override
			public void handleClick() {
				// Notification.show(paperTextfield.getValue());
				paperCombo.addItem(paperTextfield.getValue());
			}
		});

		PaperButton validateButton = new PaperButton("Validate", new ClickListener() {
			@Override
			public void handleClick() {
				try {
					paperTextfield.validate();
				} catch (InvalidInputException e) {
					Notification.show("Contenido invalido::" + e.getMessage(), Type.ERROR_MESSAGE);
				}
			}
		});
		
		PaperButton toggleVisible = new PaperButton("Toggle Visible", new ClickListener() {
			@Override
			public void handleClick() {
				paperTextfield.setVisible( !paperTextfield.isVisible() );
			}
		});

		layout.addComponent(stateButton);
		layout.addComponent(button);
		layout.addComponent(validateButton);
		layout.addComponent(toggleVisible);

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
		}
	};
}
