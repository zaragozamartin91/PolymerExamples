ast_unicore_view_webcomponent_papercheckbox_PaperCheckbox = function() {
	var connector = this;
	var element = this.getElement();

	var component = document.createElement('paper-wb-checkbox');
	element.appendChild(component);

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_papercheckbox_PaperCheckbox#onStateChange");

		component.checked = this.getState().checked;
		component.label = this.getState().label;
	}

	component.addEventListener("iron-change", function(e) {
		console.log("ast_unicore_view_webcomponent_papercheckbox_PaperCheckbox#change:");
		connector.handleChange(component.checked);
	});
};
