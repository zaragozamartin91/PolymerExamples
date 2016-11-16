/* Inserta el import to host page, here made dynamically when
 *  NOTA: ES CONVENIENTE CARGAR LOS ENCABEZADOS DESDE EL SERVLET DE VAADIN Y NO DESDE EL SCRIPT CONECTOR!
 */
//var el = document.createElement("link");
//el.rel = "import";
//el.href = "VAADIN/webcomponents/paper-textfield.html";
//document.getElementsByTagName("head").item(0).appendChild(el);
/*
 * La siguiente funcion representa el inicializador del componente de polymer.
 * El nombre debe ser el prefijo de paquete reemplazando '.' por '_' seguido del
 * nombre de la clase de vaadin.
 */
ast_unicore_view_webcomponent_paperbutton_PaperButton = function() {
	var connector = this;
	var element = this.getElement();

	var component = document.createElement('paper-button');
	component.raised = true;
	element.appendChild(component);

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_paperbutton_PaperButton#onStateChange");

		component.disabled = this.getState().buttonDisabled;
		component.innerHTML = this.getState().buttonLabel;
		component.style.width = this.getState().widthToSet;
	}

	component.addEventListener('click', function(e) {
		console.log("ast_unicore_view_webcomponent_paperbutton_PaperButton#click:");
		connector.handleClick();
	});
};
