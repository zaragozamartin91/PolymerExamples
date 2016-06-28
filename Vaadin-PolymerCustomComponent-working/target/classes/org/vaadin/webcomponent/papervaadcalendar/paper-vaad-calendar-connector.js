org_vaadin_webcomponent_papervaadcalendar_PaperVaadCalendar = function() {
	var connector = this;
	var e = this.getElement();

	e.innerHTML = '<paper-vaad-calendar></paper-vaad-calendar>';

	function component() {
		return e.firstChild;
	}

	function yyyymmdd(date) {
		var yyyy = date.getFullYear().toString();
		var mm = (date.getMonth() + 1).toString(); // getMonth() is zero-based
		var dd = date.getDate().toString();
		return yyyy + "/" + (mm[1] ? mm : "0" + mm[0]) + "/" + (dd[1] ? dd : "0" + dd[0]); // padding
	}

	this.onStateChange = function() {
		console.log("Calling onStateChange!");

		var state = this.getState();

		if(state.pickerDate){
			console.log("state.pickerDate: " + state.pickerDate);
			component().pickerDate = new Date(state.pickerDate)
			console.log("component().pickerDate: " + component().pickerDate);
		}
		
		//component().pickerDate = new Date(state.pickerDate) || component().pickerDate;
		component().pickerNarrow = state.pickerNarrow;

		component().pickerLocale = state.pickerLocale || component().pickerLocale;
		component().pickerHeadingFormat = state.pickerHeadingFormat || component().pickerHeadingFormat;
		
		if(state.pickerMaxDate) {
			component().pickerMaxDate = new Date(state.pickerMaxDate);
		}
		
		if(state.pickerMinDate) {
			component().pickerMinDate = new Date(state.pickerMinDate);
		}
		
		component().pickerResponsiveWidth = state.pickerResponsiveWidth || component().pickerResponsiveWidth;
	}

	component().addEventListener('kick', function(e) {
		console.log("KICK EVENT TRIGGERED WITH:");
		console.log(e);
		console.log("calling connector.handleKick with: " + yyyymmdd(component().pickerDate));
		connector.handleKick( yyyymmdd(component().pickerDate) );
	});
};
