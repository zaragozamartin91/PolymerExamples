/* Inserta el import to host page, here made dynamically when
 * the server side component is used for the first time. 
 */

/*AGREGAR EL CODIGO DINAMICAMENTE NO ANDA MUY BIEN! ES MEJOR AGREGARLO EN EL SERVLET DE VAADIN!*/

//var polymerElement = document.createElement("link");
//polymerElement.rel = "import";
//polymerElement.href = "VAADIN/webcomponents/bower_components/polymer/polymer.html";
//document.getElementsByTagName("head").item(0).appendChild(polymerElement);
//var paperInputElement = document.createElement("link");
//paperInputElement.rel = "import";
//paperInputElement.href = "VAADIN/webcomponents/bower_components/paper-input/paper-input.html";
//document.getElementsByTagName("head").item(0).appendChild(paperInputElement);
/*
 * La siguiente funcion representa el inicializador del componente de polymer.
 * El nombre debe ser el prefijo de paquete reemplazando '.' por '_' seguido del
 * nombre de la clase de vaadin.
 */
ast_unicore_view_webcomponent_paperinput_text_PaperTextInput = function() {
	var connector = this;
	var element = this.getElement();

	/*
	 * la clase materialize la invente en mytheme.scss para que el estilo se
	 * aplique solo a materialize sin contaminar los demas componentes.
	 */
	// var materializeWrapper = document.createElement('div');
	// materializeWrapper.className = "materialize";
	var div = document.createElement('div');
	div.className = 'input-field';
	var input = document.createElement('input');
	// input.id = "weirdInput";
	input.type = "text";
	input.className = "validate";
	div.appendChild(input);
	var label = document.createElement('label');
	// label.setAttribute('for', 'weirdInput');
	label.innerHTML = "Weird Input";
	div.appendChild(label);

	// materializeWrapper.appendChild(div);

	// element.appendChild(materializeWrapper);
	element.appendChild(materializeWrap(div));

	/*
	 * La siguiente funcion se ejecuta con cada cambio de estado del lado del
	 * servidor.
	 */
	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_paperinput_text_PaperTextInput#onStateChange");

		input.value = this.getState().inputValue;
		label.innerHTML = this.getState().inputLabel;
		if (this.getState().inputValue) {
			label.className = "active";
		}
		// component.required = this.getState().inputRequired;
		// component.errorMessage = this.getState().inputErrorMessage;
		// component.pattern = this.getState().inputPattern;
		input.disabled = this.getState().inputDisabled;
		// component.invalid = this.getState().inputInvalid;
		input.type = this.getState().inputType || "text";

		/*
		 * si el componente no tiene valor asignado o tiene valor vacio ("") y
		 * ademas se encuentra deshabilitado, entonces le asigno un valor para
		 * que el caption
		 */
		// if (!component.value) {
		// if (component.disabled) {
		// component.value = " ";
		// }
		// }
	}

	/* Listener de eventos de foco */
	input.addEventListener("focus", function(e) {
		console.log("ast_unicore_view_webcomponent_paperinput_PaperInput#focus:");
		connector.handleFocus(input.value);
	});

	/* Agrego listener para eventos "change" de paper-input. */
	input.addEventListener("change", function(e) {
		console.log("ast_unicore_view_webcomponent_paperinput_PaperInput#change:");
		connector.handleChange(input.value, input.invalid);
	});
};
