ast_unicore_view_webcomponent_paperbutton_PaperButton = function() {
	var connector = this;
	var element = this.getElement();

	var button = document.createElement('button');
	element.appendChild(materializeWrap(button));
	

	this.onStateChange = function() {
		console
				.log("ast_unicore_view_webcomponent_paperbutton_PaperButton#onStateChange");

		button.className = this.getState().buttonStyle;

		button.innerHTML = this.getState().buttonLabel;
		var className = button.className.replace("disabled", "").trim();
		if (this.getState().buttonDisabled) {
			className += " disabled";
		}
		button.className = className;

		button.style.width = this.getState().widthToSet;
		if (this.getState().widthToSet) {
			button.style.padding = "0px";
		}
	};
	
	button.addEventListener(
					'click',
					function(e) {
						console
								.log("ast_unicore_view_webcomponent_paperbutton_PaperButton#click:");
						connector.handleClick();
					});

};
