
/*
 * La siguiente funcion representa el inicializador del componente de polymer.
 * El nombre debe ser el prefijo de paquete reemplazando '.' por '_' seguido del
 * nombre de la clase de vaadin.
 */
ast_unicore_view_webcomponent_papercombo_PaperCombo = function() {
	var connector = this;
	var e = this.getElement();

	e.innerHTML = '<paper-combo></paper-combo>';

	function component() {
		return e.firstChild;
	}

	/*
	 * La siguiente funcion se ejecuta con cada cambio de estado del lado del
	 * servidor.
	 */
	this.onStateChange = function() {
		console.log("PaperCombo::Calling onStateChange!");
		
		component().dropRequired = this.getState().dropRequired;
		component().dropLabel = this.getState().dropLabel;
		component().dropDisabled = this.getState().dropDisabled;
	}

	
	this.addItem = function(caption) {
		component().addItem(caption);
	};
	
	/*
	 * Agrego listener para eventos "selected" de paper-input.
	 */
	component().addEventListener("selected", function(e) {
		console.log("PaperCombo::SELECTED EVENT TRIGGERED WITH:");
		console.log(e);
		
		connector.handleSelected(component().selectedLabel);
	});
};
