ast_unicore_view_webcomponent_papercheckbox_PaperCheckbox = function() {
	var connector = this;
	var element = this.getElement();

	function createId() {
		return "check-" + Math.random().toString(36).substring(7);
	}

	var input = document.createElement('input');
	input.type = 'checkbox';
	input.id = createId();
	var label = document.createElement('label');
	label.setAttribute('for', input.id);

	element.appendChild(materializeWrap([ input, label ]));

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_papercheckbox_PaperCheckbox#onStateChange");

		label.innerHTML = this.getState().label;
		input.checked = this.getState().checked;
		// ver que carajo pasa con el value que se setea como atributo 
		//input.value = this.getState().checked;
	}

	input.addEventListener("change", function(e) {
		console.log("ast_unicore_view_webcomponent_papercheckbox_PaperCheckbox#change:");
		connector.handleChange(input.checked);
	});
};
