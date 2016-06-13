/* Inject import to host page, here made dynamically when
 * the server side component is used for the first time. 
 */

var el = document.createElement("link");
el.rel = "import";
el.href = "VAADIN/chessstuff/paper-textfield.html";
document.getElementsByTagName("head").item(0).appendChild(el);

/*
 * La siguiente funcion representa el inicializador del componente de polymer.
 * El nombre debe ser el prefijo de paquete reemplazando '.' por '_' seguido del
 * nombre de la clase de vaadin.
 */
org_vaadin_webcomponent_chessboard_PaperTextfield = function() {
	console.log("Calling: org_vaadin_webcomponent_chessboard_PaperTextfield");

	var connector = this;
	var e = this.getElement();

	function component() {
		return e.firstChild;
	}


	this.newWithLabel = function(label) {
		e.innerHTML = '<paper-textfield input-label="' + label + '" ></paper-textfield>';
	};

	this.getValue = function() {
		console.log("component().inputValue: " + component().inputValue);
		console.log("this.getState(): ");
		console.log(this.getState());
		this.getState().value = component().inputValue;
		return component().inputValue;
	};

	this.setValue = function(newValue) {
		console.log("setValue: " + newValue);
		this.getState().value = component().inputValue;
		component().inputValue = newValue;
	};

	this.setRequired = function(isRequired) {
		component().inputRequired = isRequired;
	};

	this.setPattern = function(pattern) {
		component().inputPattern = pattern;
	};

	this.change = function() {
		console.log("This is: ");
		console.log(connector);
		console.log("Calling change with: ");
		console.log(component());
		console.log("e is:");
		console.log(e);
		console.log("component().inputValue is:" + component().inputValue);
		console.log("component().getValue is:" + component().getValue);
		console.log("component().setValue is:" + component().setValue);
		this.getState().value = component().inputValue;
		connector.doUpdate(component().inputValue);
	};

};
