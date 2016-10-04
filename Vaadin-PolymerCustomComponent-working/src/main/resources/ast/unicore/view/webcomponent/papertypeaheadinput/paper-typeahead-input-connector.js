
/*
 * La siguiente funcion representa el inicializador del componente de polymer.
 * El nombre debe ser el prefijo de paquete reemplazando '.' por '_' seguido del
 * nombre de la clase de vaadin.
 */
ast_unicore_view_webcomponent_papertypeaheadinput_PaperTypeAheadInput = function() {
	var connector = this;
	var e = this.getElement();

	e.innerHTML = '<paper-typeahead-input></paper-typeahead-input>';

	function component() {
		return e.firstChild;
	}

	/*
	 * La siguiente funcion se ejecuta con cada cambio de estado del lado del
	 * servidor.
	 */
	this.onStateChange = function() {
		console.log("PaperTypeAheadInput::Calling onStateChange!");
		
		var state = this.getState();
		
		component().required = state.inputRequired;
		component().placeholder = state.inputPlaceholder;
		component().inputValue = state.inputValue;
		component().localCandidates = component().localCandidates || [];
		component().disabled = state.disabled;
	}

	
	this.addItem = function(item) {
		component().localCandidates.push(item);
	};
	
	
	/*
	 * Agrego listener para eventos "selected" de paper-input.
	 */
	component().addEventListener("pt-item-confirmed", function(e) {
		console.log("pt-item-confirmed EVENT TRIGGERED WITH:");
		console.log(e);
		
		connector.handleConfirmed(component().inputValue);
	});
};
