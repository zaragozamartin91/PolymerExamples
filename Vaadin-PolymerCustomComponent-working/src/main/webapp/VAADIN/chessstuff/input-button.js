((function(scope) {
	console.log("Running script!");

	var owner;
	if (HTMLImports && !HTMLImports.useNative) {
		owner = document._currentScript.ownerDocument;
	} else {
		owner = document.currentScript.ownerDocument;
	}
	
	console.log("owner:");
	console.log(owner);

	var input = owner.querySelector('#paper-input-name');
	var button = owner.querySelector('#paper-button-sayHello');
	var greeting = owner.getElementById("greeting");

	console.log("input:");
	console.log(input);
	
	console.log("button:");
	console.log(button);
	
	console.log("greeting:");
	console.log(greeting);

	button.addEventListener('click', function() {
		console.log("Clicked!");
		greeting.textContent = 'Hello, ' + input.value;
	});

	console.log("Script run ended!");

}))(window);
