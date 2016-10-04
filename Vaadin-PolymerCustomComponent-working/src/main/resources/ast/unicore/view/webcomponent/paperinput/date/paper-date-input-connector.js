/* Inserta el import to host page, here made dynamically when
 * the server side component is used for the first time. 
 */

/*
 * La siguiente funcion representa el inicializador del componente de polymer.
 * El nombre debe ser el prefijo de paquete reemplazando '.' por '_' seguido del
 * nombre de la clase de vaadin.
 */
ast_unicore_view_webcomponent_paperinput_date_PaperDateInput = function() {
	var connector = this;
	var e = this.getElement();

	e.innerHTML = '<paper-input type="date" auto-validate></paper-input>';

	function component() {
		return e.firstChild;
	}

	/*
	 * La siguiente funcion se ejecuta con cada cambio de estado del lado del
	 * servidor.
	 */
	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_paperinput_date_PaperDateInput#onStateChange:");

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
		console.log("ast_unicore_view_webcomponent_paperinput_date_PaperDateInput#change:");
		console.log(e);
		connector.handleChange(component().value, component().invalid);
	});
};
