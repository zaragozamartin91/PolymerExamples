ast_unicore_view_webcomponent_table_ResponsiveTable = function() {
	var connector = this;
	var element = this.getElement();

	var component = document.createElement('responsive-table-multi');
	element.appendChild(component);

//	function arraysEqual(arr1, arr2) {
//		return JSON.stringify(arr1) == JSON.stringify(arr2);
//	}
//
//	function arraysDifferent(arr1, arr2) {
//		return !arraysEqual(arr1, arr2);
//	}

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_table_ResponsiveTable#onStateChange:");

		component.setColumns(this.getState().columns);

		var ID_KEY = "__iD__";
		var rows = this.getState().rows; 
//		rows.forEach(function(item){
//			delete item[ID_KEY];
//		});
		
		/*Si las filas del lado del server son distintas a las del componente de polymer, vuelvo a establecerlas.*/
		if (rows.length != component.rows.length) {
			console.log("Estableciendo filas:");
			console.log(this.getState().rows);
			component.setRows(this.getState().rows);
		}
		
//		var newRow = this.getState().newRow;
//		if(newRow) {
//			component.addRow(newRow);
//			connector.newRowEnd();
//		}
		
		var updateRowIndexes = this.getState().updateRowIndexes;
		if(updateRowIndexes.length > 0) {
			updateRowIndexes.forEach(function(index){
				component.setRow(index, this.getState().updateRow);
			});
			connector.updateEnd();			
		}
	}
	
	this.addRow = function(newRow) {
		component.addRow(newRow);
	};

	component.addEventListener("icon-click", function(e) {
		console.log("ast_unicore_view_webcomponent_table_ResponsiveTable#icon-click:");
		connector.handleIconClick(e.detail);
	});
};
