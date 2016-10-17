ast_unicore_view_webcomponent_papercheckbox_PaperCheckbox = function() {
	var connector = this;
	var e = this.getElement();

	e.innerHTML = '<paper-wb-checkbox></paper-wb-checkbox>';

	function component() {
		return e.firstChild;
	}

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_papercheckbox_PaperCheckbox#onStateChange");

		component().checked = this.getState().checked;
		component().label = this.getState().label;
	}

	component().addEventListener("iron-change", function(e) {
		console.log("ast_unicore_view_webcomponent_papercheckbox_PaperCheckbox#change:");
		connector.handleChange(component().checked);
	});
};
