function materializeWrap(components) {
	var materializeWrapper = document.createElement('div');
	materializeWrapper.className = "ast-materialize";

	if ($.isArray(components)) {
		components.forEach(function(component) {
			materializeWrapper.appendChild(component);
		});
	} else {
		materializeWrapper.appendChild(components);
	}

	return materializeWrapper;
}