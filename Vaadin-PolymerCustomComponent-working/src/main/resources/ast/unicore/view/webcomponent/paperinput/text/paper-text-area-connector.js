ast_unicore_view_webcomponent_paperinput_text_PaperTextArea = function() {
	var connector = this;
	var e = this.getElement();

	e.innerHTML = '<paper-textarea></paper-textarea>';

	function component() {
		return e.firstChild;
	}

	/*
	 * La siguiente funcion se ejecuta con cada cambio de estado del lado del
	 * servidor.
	 */
	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_paperinput_text_PaperTextArea#onStateChange");

		component().value = this.getState().inputValue;
		component().label = this.getState().inputLabel;
		component().required = this.getState().inputRequired;
		component().errorMessage = this.getState().inputErrorMessage;
		component().pattern = this.getState().inputPattern;
		component().disabled = this.getState().inputDisabled;
		component().invalid = this.getState().inputInvalid;

		component().validate();
	}

	/* Listener de eventos de foco */
	component().addEventListener("focus", function(e) {
		console.log("ast_unicore_view_webcomponent_paperinput_text_PaperTextArea#focus:");
		connector.handleFocus(component().value);
	});

	/*
	 * Agrego listener para eventos "change" de paper-input.
	 */
	component().addEventListener("change", function(e) {
		console.log("ast_unicore_view_webcomponent_paperinput_text_PaperTextArea#change:");
		console.log(e);
		connector.handleChange(component().value, component().invalid);
	});
};
