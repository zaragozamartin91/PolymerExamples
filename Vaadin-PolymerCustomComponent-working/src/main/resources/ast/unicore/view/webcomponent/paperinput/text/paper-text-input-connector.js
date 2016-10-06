/* Inserta el import to host page, here made dynamically when
 * the server side component is used for the first time. 
 */

/*AGREGAR EL CODIGO DINAMICAMENTE NO ANDA MUY BIEN! ES MEJOR AGREGARLO EN EL SERVLET DE VAADIN!*/

//var polymerElement = document.createElement("link");
//polymerElement.rel = "import";
//polymerElement.href = "VAADIN/webcomponents/bower_components/polymer/polymer.html";
//document.getElementsByTagName("head").item(0).appendChild(polymerElement);
//var paperInputElement = document.createElement("link");
//paperInputElement.rel = "import";
//paperInputElement.href = "VAADIN/webcomponents/bower_components/paper-input/paper-input.html";
//document.getElementsByTagName("head").item(0).appendChild(paperInputElement);
/*
 * La siguiente funcion representa el inicializador del componente de polymer.
 * El nombre debe ser el prefijo de paquete reemplazando '.' por '_' seguido del
 * nombre de la clase de vaadin.
 */
ast_unicore_view_webcomponent_paperinput_text_PaperTextInput = function() {
	var connector = this;
	var e = this.getElement();

	e.innerHTML = '<paper-input auto-validate></paper-input>';

	function component() {
		return e.firstChild;
	}

	/*
	 * La siguiente funcion se ejecuta con cada cambio de estado del lado del
	 * servidor.
	 */
	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_paperinput_text_PaperTextInput#onStateChange");

		component().value = this.getState().inputValue;
		component().label = this.getState().inputLabel;
		component().required = this.getState().inputRequired;
		component().errorMessage = this.getState().inputErrorMessage;
		component().pattern = this.getState().inputPattern;
		component().disabled = this.getState().inputDisabled;
	}

	/*
	 * Agrego listener para eventos "change" de paper-input.
	 */
	component().addEventListener("change", function(e) {
		console.log("ast_unicore_view_webcomponent_paperinput_PaperInput#change:");
		console.log(e);
		connector.handleChange(component().value, component().invalid);
	});
};
