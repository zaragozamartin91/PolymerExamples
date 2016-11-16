//package mainapp;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//
//import org.jsoup.nodes.Element;
//
//import ast.unicore.view.webcomponent.imports.WebImport;
//import ast.unicore.view.webcomponent.paperbutton.PaperButton;
//import ast.unicore.view.webcomponent.paperbutton.PaperButton.ClickListener;
//import ast.unicore.view.webcomponent.papercombo.PaperCombo;
//
//import com.vaadin.annotations.Theme;
//import com.vaadin.annotations.VaadinServletConfiguration;
//import com.vaadin.server.BootstrapFragmentResponse;
//import com.vaadin.server.BootstrapListener;
//import com.vaadin.server.BootstrapPageResponse;
//import com.vaadin.server.SessionInitEvent;
//import com.vaadin.server.SessionInitListener;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.server.VaadinServlet;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//
//@Theme("dawn")
//@SuppressWarnings("serial")
//public class SmallTestUI extends UI {
//	private VerticalLayout mainLayout = new VerticalLayout();
//
//	@Override
//	protected void init(VaadinRequest request) {
//		mainLayout.setMargin(true);
//		this.setContent(mainLayout);
//
//		VerticalLayout invisibleLayout = new VerticalLayout(new PaperCombo(""));
//		mainLayout.addComponent(invisibleLayout);
//		invisibleLayout.setVisible(false);
//
//		PaperButton componentAddButton = new PaperButton("Agregar componentes", new ClickListener() {
//			@Override
//			public void buttonClick() {
//				PaperCombo paperCombo = new PaperCombo("Personas");
//				mainLayout.addComponent(paperCombo);
//				paperCombo.addItem("Martin");
//				paperCombo.addItem("Julio");
//				paperCombo.addItem("Exe");
//			}
//		});
//		componentAddButton.setWidth("60%");
//
//		mainLayout.addComponent(componentAddButton);
//	}
//
//	@WebServlet(value = "/*", asyncSupported = true)
//	@VaadinServletConfiguration(productionMode = false, ui = SmallTestUI.class)
//	public static class Servlet extends VaadinServlet {
//		@Override
//		protected void servletInitialized() throws ServletException {
//			super.servletInitialized();
//			getService().addSessionInitListener(new SessionInitListener() {
//
//				@Override
//				public void sessionInit(SessionInitEvent event) {
//					event.getSession().addBootstrapListener(polymerInjector);
//				}
//			});
//		}
//	}
//
//	/**
//	 * This injects polymer.js and es6 support polyfils directly into host page.
//	 *
//	 * Better compatibility and good approach if you have multiple webcomponents in the app.
//	 */
//	public static BootstrapListener polymerInjector = new BootstrapListener() {
//
//		@Override
//		public void modifyBootstrapFragment(BootstrapFragmentResponse response) {
//		}
//
//		@Override
//		public void modifyBootstrapPage(BootstrapPageResponse response) {
//			Element head = response.getDocument().getElementsByTag("head").get(0);
//
//			// add polymer for older browsers
//			// -------------------------------------------------
//			// Element polymer = response.getDocument().createElement("script");
//			// polymer.attr("src", "VAADIN/webcomponents/polymer-platform/platform.js");
//			// head.appendChild(polymer);
//			// ---------------------------------------------------------------------------------
//
//			// add es6 support for older browsers
//			// ----------------------------------------------
//			// Element traceur = response.getDocument().createElement("script");
//			// traceur.attr("src", "VAADIN/webcomponents/traceur-runtime/traceur-runtime.min.js");
//			// head.appendChild(traceur);
//			// ---------------------------------------------------------------------------------
//
//			head.prependElement("meta").attr("name", "viewport").attr("content", "width=device-width, initial-scale=1.0");
//
//			{
//				Element script = response.getDocument().createElement("script");
//				script.attr("src", "VAADIN/webcomponents/bower_components/webcomponentsjs/webcomponents.js");
//				head.appendChild(script);
//			}
//
//			{
//				Element script = response.getDocument().createElement("script");
//				script.attr("src", "VAADIN/webcomponents/bower_components/jquery-3.1.1.min/index.js");
//				head.appendChild(script);
//			}
//
//			// {
//			// Element link = response.getDocument().createElement("link");
//			// link.attr("rel", "import");
//			// link.attr("href",
//			// "VAADIN/webcomponents/bower_components/vaadin-core-elements/vaadin-core-elements.html");
//			// head.appendChild(link);
//			// }
//
//			WebImport[] webImports = WebImport.values();
//			for (WebImport webImport : webImports) {
//				Element link = response.getDocument().createElement("link");
//				link.attr("rel", "import");
//				link.attr("href", webImport.path);
//				head.appendChild(link);
//			}
//		}
//	};
//
//	static class Person {
//		public final String name;
//		public final int id;
//
//		public Person(String name, int id) {
//			super();
//			this.name = name;
//			this.id = id;
//		}
//	}
//
// }
