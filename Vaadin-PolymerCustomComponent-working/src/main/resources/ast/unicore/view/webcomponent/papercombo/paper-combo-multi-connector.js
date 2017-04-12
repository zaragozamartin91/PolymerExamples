ast_unicore_view_webcomponent_papercombo_PaperComboMulti = function() {
	var connector = this;
	var element = this.getElement();

	var div = document.createElement('div');
	div.className = 'input-field';
	div.captions = []; // guardo los valores en el div principal
	var select = document.createElement('select');
	div.appendChild(select);
	var nullOption = document.createElement('option');
	var NULL_VALUE = "__INVALID_KEY__";
	div.selectedLabel = undefined;
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

		console.log("ast_unicore_view_webcomponent_papercombo_PaperComboMulti#selected: " + $(select).val());
		connector.handleSelected($(select).val());
	}

	function selectOption(caption) {
		handleValueChange();
	}

	this.onStateChange = function() {
		console.log("ast_unicore_view_webcomponent_papercombo_PaperComboMulti#onStateChange");

		var state = this.getState();

		state.captions = state.captions || [];
		if (arraysDifferent(div.captions, state.captions)) {
			setCaptions(state.captions);
		}
		/*
		 * GUARDO LOS CAPTIONS DE LAS OPCIONES ACTUALES EN EL div PRINCIPAL
		 */
		div.captions = state.captions;

		label.innerHTML = state.dropLabel || "";
		select.disabled = state.dropDisabled;

		/* selectedLabel GUARDA UNICAMENTE LA OPCION SELECCIONADA DESE EL PaperCombo DE VAADIN USANDO setSelected. */
		if (state.selectedLabel) {
			/* div.selectedLabel GUARDA LA ULTIMA OPCION QUE FUE SELECCIONADA MEDIANTE PaperCombo#setSelected */
			if (state.selectedLabel != div.selectedLabel) {
				/* si el valor a marcar como seleccionado es DIFERENTE al seleccionado actualmente entonces se fuerza la seleccion del mismo */
				if ($(select).val() != state.selectedLabel) {
					selectOption(state.selectedLabel);
				}
			}
			/* si state.selectedLabel == NULL_VALUE -> se esta queriendo hacer un clear de la seleccion. Por lo tanto reseteo el div.selectedLabel */
			div.selectedLabel = state.selectedLabel == NULL_VALUE ? undefined : state.selectedLabel;
		}

		/* Habilita el modo de opciones multiples. */
		if (state.multiple) {
			select.setAttribute("multiple", true);
		}

		/*
		 * REINICIA EL SELECT DE MATERIALIZE PARA ADECUARSE AL CAMBIO DE ESTADO
		 */
		$(select).material_select();

		if (state.compactDrop && div.captions.length > 1) {
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

				/*
				 * FUERZO A QUE LA POSICION DE LA LISTA DE OPCIONES SEA 0PX. DE LO CONTRARIO, SI EL COMBO ESTA MUY ABAJO EN EL CONTENIDO, SE ESTABLECE ~
				 * TOP:-300PX LO QUE PROVOCA QUE LAS OPCIONES NO SE VEAN
				 */
				var ul = wrap.querySelector('ul');
				ul.style.top = "0px";

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
