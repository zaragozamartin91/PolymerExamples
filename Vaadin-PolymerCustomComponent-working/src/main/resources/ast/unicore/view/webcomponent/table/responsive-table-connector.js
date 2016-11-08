ast_unicore_view_webcomponent_table_ResponsiveTable = function() {
	var connector = this;
	var e = this.getElement();

	e.innerHTML = '<responsive-table></responsive-table>';

	function component() {
		return e.firstChild;
	}

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_table_ResponsiveTable#onStateChange");

		console.log(this.getState().columns);
	}

	component().addEventListener("icon-click", function(e) {
		console.log("ast_unicore_view_webcomponent_table_ResponsiveTable#icon-click:");
		connector.handleIconClick(e.detail);
	});
};
