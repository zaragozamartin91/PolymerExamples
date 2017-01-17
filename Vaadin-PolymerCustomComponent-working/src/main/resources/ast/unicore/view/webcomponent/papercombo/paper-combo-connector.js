ast_unicore_view_webcomponent_papercombo_PaperCombo = function() {
	var connector = this;
	var element = this.getElement();

	var div = document.createElement('div');
	div.className = 'input-field';
	div.captions = []; // guardo los valores en el div principal
	var select = document.createElement('select');
	div.appendChild(select);
	var nullOption = document.createElement('option');
	var NULL_VALUE = "__INVALID_KEY__";
	nullOption.value = NULL_VALUE;
	nullOption.innerHTML = " ";
	nullOption.setAttribute("selected", "selected");
	select.appendChild(nullOption);
	var label = document.createElement('label');
	div.appendChild(label);

	var wrap = materializeWrap(div);
	// wrap.style.overflowY = "auto";
	// wrap.style.overflowX = "hidden";
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

	function getOption(caption) {
		if (caption) {
			return select.querySelector('option[value="' + caption + '"]');
		} else {
			return nullOption;
		}
	}

	function selectOption(caption) {
		var option = getOption(caption);
		if (option) {
			option.setAttribute("selected", "selected");
			$(select).material_select();
		} else {
			console.error("Elemento " + caption + " no existe!");
		}
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
			$(select).material_select(); // REINICIA EL SELECT DE MATERIALIZE
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
		this.getState().selectedLabel = this.getState().selectedLabel || NULL_VALUE;
		if (select.value != this.getState().selectedLabel) {
			selectOption(this.getState().selectedLabel);
		}

		// component.dropRequired = this.getState().dropRequired;
		// component.setSelected(this.getState().selectedLabel);
		//
		// if (this.getState().dropdownContentHeight) {
		// component.setDropdownContentHeight(this.getState().dropdownContentHeight);
		// }
	}

	select.onchange = function() {
		console.log("ast_unicore_view_webcomponent_papercombo_PaperCombo#selected: " + select.value);
		connector.handleSelected(select.value);
	};
};
