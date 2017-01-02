/* Inserta el import to host page, here made dynamically when
 * the server side component is used for the first time. 
 */

ast_unicore_view_webcomponent_paperinput_date_PaperDateInput = function() {
	/* funcion que chequea el soporte del browser de determinado tipo de input. */
	function checkInput(type) {
		var input = document.createElement("input");
		input.setAttribute("type", type);
		return input.type == type;
	}

	var connector = this;
	var element = this.getElement();

	/*
	 * si el <input type="date" .../> es soportado, entonces se crea un
	 * calendario de ese tipo. De lo contrario se crea un paper-dialog-calendar.
	 */
	var component;
	if (checkInput('date')) {
		component = document.createElement('paper-input');
		component.type = "date";
	} else {
		component = document.createElement('paper-dialog-calendar');
		component.isDialogCalendar = true;
	}

	element.appendChild(component);

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_paperinput_date_PaperDateInput#onStateChange:");

		if (component.isDialogCalendar) {
			component.setValue(this.getState().inputValue);
			component.allDisabled = this.getState().inputDisabled;
			if (this.getState().inputWidth) {
				component.setWidth(this.getState().inputWidth);
			}
		} else {
			component.value = this.getState().inputValue;
			component.label = this.getState().inputLabel;
			component.pattern = this.getState().inputPattern;
			component.required = this.getState().inputRequired;
			component.disabled = this.getState().inputDisabled;
			component.invalid = this.getState().inputInvalid;
		}

		component.errorMessage = this.getState().inputErrorMessage;

		// component.validate();
	}

	/*
	 * Agrego listener para eventos "change" de paper-input /
	 * paper-dialog-calendar.
	 */
	component.addEventListener("change", function(e) {
		console.log("ast_unicore_view_webcomponent_paperinput_date_PaperDateInput#change:");
		connector.handleChange(component.value);
	});
};
