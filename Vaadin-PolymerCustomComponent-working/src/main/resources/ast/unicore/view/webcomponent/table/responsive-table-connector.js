ast_unicore_view_webcomponent_table_ResponsiveTable = function() {
	var connector = this;
	var element = this.getElement();

	var component = document.createElement('responsive-table');
	element.appendChild(component);
	// e.innerHTML = '<responsive-table></responsive-table>';

	function arraysEqual(arr1, arr2) {
		return JSON.stringify(arr1) == JSON.stringify(arr2);
	}

	function arraysDifferent(arr1, arr2) {
		return !arraysEqual(arr1, arr2);
	}

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_table_ResponsiveTable#onStateChange:");

		if (arraysDifferent(this.getState().columns, component.columns)) {
			console.log("Estableciendo columnas:");
			console.log(this.getState().columns);
			component.setColumns(this.getState().columns);
		}

		if (arraysDifferent(this.getState().rows, component.rows)) {
			console.log("Estableciendo filas:");
			console.log(this.getState().rows);
			component.setRows(this.getState().rows);
		}
	}

	component.addEventListener("icon-click", function(e) {
		console.log("ast_unicore_view_webcomponent_table_ResponsiveTable#icon-click:");
		connector.handleIconClick(e.detail);
	});
};
