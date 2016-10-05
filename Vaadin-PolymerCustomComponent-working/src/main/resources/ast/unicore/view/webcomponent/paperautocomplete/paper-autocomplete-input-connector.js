
ast_unicore_view_webcomponent_paperautocomplete_PaperAutocompleteInput = function() {
	var connector = this;
	var e = this.getElement();

	e.innerHTML = '<paper-autocomplete-input></paper-autocomplete-input>';

	function component() {
		return e.firstChild;
	}

	/*
	 * La siguiente funcion se ejecuta con cada cambio de estado del lado del
	 * servidor.
	 */
	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_paperautocomplete_PaperAutocompleteInput#onStateChange");
		
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
		console.log("ast_unicore_view_webcomponent_paperautocomplete_PaperAutocompleteInput#pt-item-confirmed:");
		console.log(e);
		
		connector.handleConfirmed(component().inputValue);
	});
};
