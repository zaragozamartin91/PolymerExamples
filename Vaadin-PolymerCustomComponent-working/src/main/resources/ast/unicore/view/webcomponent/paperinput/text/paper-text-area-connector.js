ast_unicore_view_webcomponent_paperinput_text_PaperTextArea = function() {
	var connector = this;
	var element = this.getElement();

	var div = document.createElement('div');
	div.className = 'input-field';
	var input = document.createElement('textarea');
	input.className = "materialize-textarea";
	input.value = "";
	div.appendChild(input);
	var label = document.createElement('label');
	div.appendChild(label);

	element.appendChild(materializeWrap(div));

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_paperinput_text_PaperTextArea#onStateChange");

		if (input.value != this.getState().inputValue) {
			$(input).val(this.getState().inputValue);
			$(input).trigger('autoresize');
		}
		label.innerHTML = this.getState().inputLabel;
		if (this.getState().inputValue) {
			label.className = "active";
		}
		input.disabled = this.getState().inputDisabled;
	}

	/* Listener de eventos de foco */
	input.addEventListener("focus", function(e) {
		console.log("ast_unicore_view_webcomponent_paperinput_text_PaperTextArea#focus:");
		connector.handleFocus(input.value);
	});

	/* Agrego listener para eventos "change" de paper-input. */
	input.addEventListener("change", function(e) {
		console.log("ast_unicore_view_webcomponent_paperinput_text_PaperTextArea#change:");
		connector.handleChange(input.value, input.invalid);
	});
};
