ast_unicore_view_webcomponent_papercheckbox_PaperCheckbox = function() {
	var connector = this;
	var element = this.getElement();

	function createId() {
		return Math.random().toString(36).substring(7);
	}

	var input = document.createElement('input');
	input.type = 'checkbox';
	input.id = createId();
	var label = document.createElement('label');
	label.innerHTML = 'Red';
	label.setAttribute('for', input.id);
	var div = document.createElement('div');
	div.appendChild(input);
	div.appendChild(label);
	div.id = "check";

	element.appendChild(materializeWrap(div));

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_papercheckbox_PaperCheckbox#onStateChange");

		label.innerHTML = this.getState().label;
		input.value = this.getState().checked;
		// component.checked = this.getState().checked;
		// component.label = this.getState().label;
	}

	input.addEventListener("change", function(e) {
		console.log("ast_unicore_view_webcomponent_papercheckbox_PaperCheckbox#change:");
		connector.handleChange(input.value == "true");
	});

	// component.addEventListener("iron-change", function(e) {
	// console.log("ast_unicore_view_webcomponent_papercheckbox_PaperCheckbox#change:");
	// connector.handleChange(component.checked);
	// });
};
