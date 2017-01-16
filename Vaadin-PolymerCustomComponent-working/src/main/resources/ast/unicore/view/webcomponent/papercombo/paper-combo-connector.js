ast_unicore_view_webcomponent_papercombo_PaperCombo = function() {
	var connector = this;
	var element = this.getElement();

	// $(element).append('<div class="ast-materialize"><div class="input-field
	// col s12"><select><option value="" disabled selected>Choose your
	// option</option><option value="1">Option 1</option> <option
	// value="2">Option 2</option> <option value="3">Option 3</option> </select>
	// <label>Materialize Select</label> </div></div>');

	var div = document.createElement('div');
	div.className = 'input-field';
	div.captions = [];
	var select = document.createElement('select');
	div.appendChild(select);
	var label = document.createElement('label');
	div.appendChild(label);

	// var component = document.createElement('paper-combo');
	var wrap = materializeWrap(div);
	wrap.style.overflowY = "auto";
	wrap.style.overflowX = "hidden";
	element.appendChild(wrap);

	function arraysEqual(arr1, arr2) {
		return JSON.stringify(arr1) == JSON.stringify(arr2);
	}

	function arraysDifferent(arr1, arr2) {
		return !arraysEqual(arr1, arr2);
	}

	function buildOption(caption) {
		var option = document.createElement('option');
		option.value = caption;
		option.innerHTML = caption;
		return option;
	}

	function setCaptions(captions) {
		captions.forEach(function(caption) {
			var option = buildOption(caption);
			select.appendChild(option);
		});
	}

	/*
	 * La siguiente funcion se ejecuta con cada cambio de estado del lado del
	 * servidor.
	 */
	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_papercombo_PaperCombo#onStateChange");

		this.getState().captions = this.getState().captions || [];
		if (arraysDifferent(div.captions, this.getState().captions)) {
			setCaptions(this.getState().captions);
			$(select).material_select();
			// var inputSelectDropdown =
			// wrap.querySelector('input.select-dropdown');
			// var prevHeight = wrap.style.height;
			// var drop = false;
			// inputSelectDropdown.addEventListener('focus', function() {
			// if (drop) {
			// return;
			// }
			// wrap.style.height = "300px";
			// });
			// inputSelectDropdown.addEventListener('blur', function() {
			// wrap.style.height = prevHeight;
			// });
		}
		div.captions = this.getState().captions;

		label.innerHTML = this.getState().dropLabel || "";
		select.disabled = this.getState().dropDisabled;

		// component.dropRequired = this.getState().dropRequired;
		// component.dropLabel = this.getState().dropLabel;
		// component.dropDisabled = this.getState().dropDisabled;
		// component.setCaptions(this.getState().captions);
		// component.setSelected(this.getState().selectedLabel);
		//
		// if (this.getState().dropdownContentHeight) {
		// component.setDropdownContentHeight(this.getState().dropdownContentHeight);
		// }
	}

	select.onchange = function() {
		console.log("ast_unicore_view_webcomponent_papercombo_PaperCombo#selected:");
		connector.handleSelected(select.value);
	};
};
