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
	var element = this.getElement();

	var component = document.createElement('paper-input');
	component.type = "date";
	element.appendChild(component);
	// element.innerHTML = '<paper-input type="date"></paper-input>';

	/*
	 * La siguiente funcion se ejecuta con cada cambio de estado del lado del
	 * servidor.
	 */
	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_paperinput_date_PaperDateInput#onStateChange:");

		component.value = this.getState().inputValue;
		component.label = this.getState().inputLabel;
		component.required = this.getState().inputRequired;
		component.errorMessage = this.getState().inputErrorMessage;
		component.pattern = this.getState().inputPattern;
		component.disabled = this.getState().inputDisabled;
		component.invalid = this.getState().inputInvalid;

		component.validate();
	}

	/*
	 * Agrego listener para eventos "change" de paper-input.
	 */
	component.addEventListener("change", function(e) {
		console.log("ast_unicore_view_webcomponent_paperinput_date_PaperDateInput#change:");
		console.log(e);
		connector.handleChange(component.value, component.invalid);
	});
};
