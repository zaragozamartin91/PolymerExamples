ast_unicore_view_webcomponent_combo_vaadin_VaadinComboBox = function() {
	var connector = this;
	var e = this.getElement();

	e.innerHTML = '<vaadin-combo-box></vaadin-combo-box>';

	function component() {
		return e.firstChild;
	}

	/*
	 * La siguiente funcion se ejecuta con cada cambio de estado del lado del
	 * servidor.
	 */
	this.onStateChange = function() {
		console.log("vaadin-combo-box::Calling onStateChange!");

		var state = this.getState();

		component().value = state.value;
		component().required = state.required;
		component().label = state.label;
		component().disabled = state.disabled;
	}

	this.addItem = function(caption) {
		component().items = component().items || [];
		component().items.push(caption);
	};

	this.setItems = function(items) {
		component().items = items;
	};

	/*
	 * Agrego listener para eventos "selected" de paper-input.
	 */
	component().addEventListener("value-changed", function(e) {
		console.log("vaadin-combo-box::value-changed EVENT TRIGGERED WITH:");
		console.log(e);

		connector.handleValueChanged(e.detail.value);
	});
};
