function materializeWrap(component) {
	var materializeWrapper = document.createElement('div');
	materializeWrapper.className = "ast-materialize";
	materializeWrapper.appendChild(component);
	return materializeWrapper; 
}