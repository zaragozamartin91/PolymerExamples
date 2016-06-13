package org.vaadin.webcomponent.chessboard;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.jsoup.nodes.Element;
import org.vaadin.maddon.layouts.MVerticalLayout;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("dawn")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {
	private PaperTextfield paperTextfield;

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new MVerticalLayout();
		setContent(layout);

		Button button = new Button("Add Custom component");

		button.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				paperTextfield = PaperTextfield.newWithLabel("Enter username:");
				paperTextfield.setWidth("100%");
				paperTextfield.setValue("Default");
				paperTextfield.setRequired(true);

//				paperTextfield.addValueChangeListener(new PaperTextfield.ValueChangeListener() {
//					public void valueChange() {
//						Notification.show("Value: " + paperTextfield.getValue());
//					}
//				});

				layout.addComponent(paperTextfield);
			}
		});

		Button stateButton = new Button("Get State", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
//				String paperTextFieldValue = paperTextfield.getValue();
//				Notification.show("paperTextfield.getValue(): " + paperTextFieldValue);
//				layout.addComponent( new Label( paperTextFieldValue ) );
				
				paperTextfield.withStatePerform(new StateAction<PaperTextfieldState>() {
					public void run(PaperTextfieldState state) {
						Notification.show("input value is: " + state.value);
						layout.addComponent( new Label( state.value ) );
					}
				});
			}
		});

		layout.addComponent(stateButton);
		layout.addComponent(button);
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
			// polymer.attr("src", "VAADIN/chessstuff/polymer-platform/platform.js");
			// head.appendChild(polymer);
			// ---------------------------------------------------------------------------------

			// add es6 support for older browsers ----------------------------------------------
			// Element traceur = response.getDocument().createElement("script");
			// traceur.attr("src", "VAADIN/chessstuff/traceur-runtime/traceur-runtime.min.js");
			// head.appendChild(traceur);
			// ---------------------------------------------------------------------------------

			Element webcomponentsScript = response.getDocument().createElement("script");
			webcomponentsScript.attr("src", "VAADIN/chessstuff/bower_components/webcomponentsjs/webcomponents-lite.min.js");
			head.appendChild(webcomponentsScript);

			Element inputButtonLink = response.getDocument().createElement("link");
			inputButtonLink.attr("rel", "VAADIN/chessstuff/input-button.html");
			head.appendChild(inputButtonLink);
		}
	};
}
