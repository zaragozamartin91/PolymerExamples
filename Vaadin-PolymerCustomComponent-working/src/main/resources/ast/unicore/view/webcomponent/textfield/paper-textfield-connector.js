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
ast_unicore_view_webcomponent_textfield_PaperTextfield = function() {
	var connector = this;
	var e = this.getElement();

	e.innerHTML = '<paper-textfield></paper-textfield>';

	function component() {
		return e.firstChild;
	}


	/*
	 * La siguiente funcion se ejecuta con cada cambio de estado del lado del
	 * servidor.
	 */
	this.onStateChange = function() {
		console.log("Calling onStateChange!");

		component().inputValue = this.getState().inputValue;
		component().inputLabel = this.getState().inputLabel;
		component().inputRequired = this.getState().inputRequired;
		component().inputErrorMessage = this.getState().inputErrorMessage;
		component().inputPattern = this.getState().inputPattern;
	}
	
	component().addEventListener('kick', function(e) {
		console.log("init_KICK EVENT TRIGGERED WITH:");
		console.log(e);
		console.log("calling connector.handleKick with: " + e.detail.kickValue);
		connector.handleKick(component().inputValue);
	});
};
