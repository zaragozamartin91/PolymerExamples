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
	div.selectedLabel = NULL_VALUE;
	nullOption.value = NULL_VALUE;
	nullOption.innerHTML = " ";
	nullOption.setAttribute("selected", "selected");
	nullOption.setAttribute("disabled", "disabled");
	select.appendChild(nullOption);
	var label = document.createElement('label');
	div.appendChild(label);

	var wrap = materializeWrap(div);
	// wrap.style.overflowY = "auto";
	// wrap.style.overflowX = "hidden";
	element.appendChild(wrap);

	/* VARIABLES UTILIZADAS PARA EL COMPACT DROP --------------------- */
	var drop = false;
	var prevInputHeight = undefined;
	var inputSelectDropdown = undefined;
	var heightToSet = "200px";
	/* --------------------------------------------------------------- */

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

	function resetOptions() {
		select.innerHTML = "";
		select.appendChild(nullOption);
	}

	function setCaptions(captions) {
		resetOptions();

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

	function handleValueChange() {
		if (drop) {
			inputSelectDropdown.style.height = prevInputHeight;
			drop = false;
		}

		console.log("ast_unicore_view_webcomponent_papercombo_PaperCombo#selected: " + select.value);
		connector.handleSelected(select.value);
	}

	function selectOption(caption) {
		var option = getOption(caption);
		if (option) {
			// option.setAttribute("selected", "selected");
			var valueChange = select.value != caption;
			select.value = caption;
			if (valueChange) {
				handleValueChange();
			}
		} else {
			console.error("Elemento " + caption + " no existe!");
		}
	}

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_papercombo_PaperCombo#onStateChange");

		this.getState().captions = this.getState().captions || [];
		if (arraysDifferent(div.captions, this.getState().captions)) {
			setCaptions(this.getState().captions);
		}
		/*
		 * GUARDO LAS OPCIONES ACTUALES EN EL div PRINCIPAL
		 */
		div.captions = this.getState().captions;

		label.innerHTML = this.getState().dropLabel || "";
		select.disabled = this.getState().dropDisabled;
		
		/* selectedLabel GUARDA UNICAMENTE LA OPCION SELECCIONADA DESE EL PaperCombo DE VAADIN USANDO setSelected. */
		if (this.getState().selectedLabel) {
			/* div.selectedLabel GUARDA LA ULTIMA OPCION QUE FUE SELECCIONADA MEDIANTE PaperCombo#setSelected */
			if(this.getState().selectedLabel != div.selectedLabel) {
				if (select.value != this.getState().selectedLabel) {
					selectOption(this.getState().selectedLabel);
				}
				div.selectedLabel = this.getState().selectedLabel;
			}
		}

		/*
		 * REINICIA EL SELECT DE MATERIALIZE PARA ADECUARSE AL CAMBIO DE ESTADO
		 */
		$(select).material_select();

		if (this.getState().compactDrop) {
			/*
			 * EL MODO COMPACT DROP IMPLICA QUE EL COMBO NO HARA OVERFLOW SINO QUE AL ABRIRLO CRECERA EN TAMAÑO MOSTRANDO UN SCROLLBAR VERTICAL PARA SELECCIONAR
			 * UNA OPCION. AL SELECCIONAR UNA OPCION DETERMINADA, EL COMPONENTE SE ACHICARA.
			 */
			wrap.style.overflow = "auto";
			inputSelectDropdown = wrap.querySelector('input.select-dropdown');
			prevInputHeight = inputSelectDropdown.style.height;
			inputSelectDropdown.addEventListener('focus', function() {
				if (drop) {
					return;
				}
				inputSelectDropdown.style.height = heightToSet;
				drop = true;
			});
			inputSelectDropdown.addEventListener('blur', function() {
				/*
				 * RETRASO EL CAMBIO DE ALTURA EN EL COMBO DADO QUE EL EVENTO blur SE DISPARA ANTES QUE EL EVENTO valueChange
				 */
				setTimeout(function() {
					if (drop) {
						inputSelectDropdown.style.height = prevInputHeight;
						drop = false;
					}
				}, 750);
			});
		}
	}

	select.onchange = handleValueChange;
};
