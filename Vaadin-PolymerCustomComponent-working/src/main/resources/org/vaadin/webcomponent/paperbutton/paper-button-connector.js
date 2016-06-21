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
org_vaadin_webcomponent_paperbutton_PaperButton = function() {
	var connector = this;
	var e = this.getElement();

	e.innerHTML = '<paper-button raised></paper-button>';

	function component() {
		return e.firstChild;
	}
	
	this.onStateChange = function() {
		console.log("Calling onStateChange!");

		component().disabled = this.getState().buttonDisabled;
		component().innerHTML = this.getState().buttonLabel;
	}
	
	component().addEventListener('click', function(e) {
		console.log("CLICK EVENT TRIGGERED WITH:");
		console.log(e);
		console.log("calling connector.handleClick");
		connector.handleClick();
	});
};
