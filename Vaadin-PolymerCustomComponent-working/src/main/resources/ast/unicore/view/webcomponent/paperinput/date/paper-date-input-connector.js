/* Inserta el import to host page, here made dynamically when
 * the server side component is used for the first time. 
 */

ast_unicore_view_webcomponent_paperinput_date_PaperDateInput = function() {
	/* funcion que chequea el soporte del browser de determinado tipo de input. */
	// function dateInputSupported(type) {
	// var input = document.createElement("input");
	// input.setAttribute("type", type);
	// return input.type == type;
	// }
	var connector = this;
	var element = this.getElement();

	/*
	 * si el <input type="date" .../> es soportado, entonces se crea un
	 * calendario de ese tipo. De lo contrario se crea un paper-dialog-calendar.
	 */
	var component = document.createElement('input');
	component.className = "datepicker";
	component.type = "date";
	element.appendChild(materializeWrap(component));

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_paperinput_date_PaperDateInput#onStateChange:");

		component.placeholder = this.getState().inputLabel;

		/* EL DATE PICKER DE MATERIALIZE ESTA BASADO EN http://amsul.ca/pickadate.js/date */
		var $input = $(component).pickadate({
			selectMonths : true, // Creates a dropdown to control month
			selectYears : 15,
			format: 'dd/mm/yyyy',
			closeOnSelect: true,
			onSet: function(context) {
				if(context.select) {
					var newValue = new Date(context.select); // context.select contiene el TIMESTAMP de la nueva fecha seleccionada
					var newValueAsString = newValue.toISOString().slice(0,10); // obtengo una fecha en formato yyyy-mm-dd
					connector.handleChange(newValueAsString);
					this.close();
				}
			}
		});
		
		var picker = $input.pickadate('picker');
		
		if(this.getState().inputValue) {
			picker.set('select', this.getState().inputValue, { format: 'yyyy-mm-dd' })
		}
		
		// component.value = this.getState().inputValue;
		// component.label = this.getState().inputLabel;
		// component.pattern = this.getState().inputPattern;
		// component.required = this.getState().inputRequired;
		// component.disabled = this.getState().inputDisabled;
		// component.invalid = this.getState().inputInvalid;

		component.errorMessage = this.getState().inputErrorMessage;
	};
};
