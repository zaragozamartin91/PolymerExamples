ast_unicore_view_webcomponent_table_ResponsiveTable = function() {
	var connector = this;
	var e = this.getElement();

	e.innerHTML = '<responsive-table></responsive-table>';

	function component() {
		return e.firstChild;
	}

	function arraysEqual(arr1, arr2) {
		return JSON.stringify(arr1) == JSON.stringify(arr2);
	}

	function arraysDifferent(arr1, arr2) {
		return !arraysEqual(arr1, arr2);
	}

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_table_ResponsiveTable#onStateChange:");
		console.log(this.getState().columns);
		console.log(this.getState().rows);

		if (arraysDifferent(this.getState().columns, component().columns)) {
			component().setColumns(this.getState().columns);
		}

		if (arraysDifferent(this.getState().rows, component().rows)) {
			component().setRows(this.getState().rows);
		}
	}

	component().addEventListener("icon-click", function(e) {
		console.log("ast_unicore_view_webcomponent_table_ResponsiveTable#icon-click:");
		connector.handleIconClick(e.detail);
	});
};
