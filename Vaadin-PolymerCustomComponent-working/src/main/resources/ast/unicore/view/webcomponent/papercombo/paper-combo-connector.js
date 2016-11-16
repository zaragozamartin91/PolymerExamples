console.log("PAPER-COMBO CONNECTOR DEFINED!!!");

ast_unicore_view_webcomponent_papercombo_PaperCombo = function() {
	var connector = this;
	var element = this.getElement();

	// e.innerHTML = '<paper-combo></paper-combo>';
	var component = document.createElement('paper-combo');
	element.appendChild(component);

	console.log('PAPER-COMBO CONNECTOR COMPONENT DEFINED!!!');

	// function component() {
	// return e.firstChild;
	// }

	// element.onStateChange = function() {
	// console.log("ast_unicore_view_webcomponent_papercombo_PaperCombo#onStateChange");
	//
	// component.dropRequired = this.getState().dropRequired;
	// component.dropLabel = this.getState().dropLabel;
	// component.dropDisabled = this.getState().dropDisabled;
	// component.setCaptions(this.getState().captions);
	// component.setSelected(this.getState().selectedLabel);
	// }
	//	
	// component.onStateChange = function() {
	// console.log("ast_unicore_view_webcomponent_papercombo_PaperCombo#onStateChange");
	//
	// component.dropRequired = this.getState().dropRequired;
	// component.dropLabel = this.getState().dropLabel;
	// component.dropDisabled = this.getState().dropDisabled;
	// component.setCaptions(this.getState().captions);
	// component.setSelected(this.getState().selectedLabel);
	// }

	/*
	 * La siguiente funcion se ejecuta con cada cambio de estado del lado del
	 * servidor.
	 */
	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_papercombo_PaperCombo#onStateChange");

		component.dropRequired = this.getState().dropRequired;
		component.dropLabel = this.getState().dropLabel;
		component.dropDisabled = this.getState().dropDisabled;
		component.setCaptions(this.getState().captions);
		component.setSelected(this.getState().selectedLabel);
	}

	/*
	 * Agrego listener para eventos "selected" de paper-input.
	 */
	component.addEventListener("selected", function(e) {
		console.log("ast_unicore_view_webcomponent_papercombo_PaperCombo#selected:");
		console.log(e);

		connector.handleSelected(component.selectedLabel);
	});
};
