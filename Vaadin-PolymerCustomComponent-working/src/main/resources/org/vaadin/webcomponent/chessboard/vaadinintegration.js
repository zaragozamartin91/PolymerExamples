/* Inject chess-board import to host page, here made dynamically when
 * the server side component is used for the first time. 
 */

/*
 var webcomponents =  document.createElement("script");
 webcomponents.src = "//cdnjs.cloudflare.com/ajax/libs/webcomponentsjs/0.7.21/webcomponents.min.js";
 document.getElementsByTagName("head").item(0).appendChild(webcomponents);
 */

var el = document.createElement("link");
el.rel = "import";
el.href = "VAADIN/chessstuff/input-button.html";
document.getElementsByTagName("head").item(0).appendChild(el);

org_vaadin_webcomponent_chessboard_InputButton = function() {
	var e = this.getElement();
	
	e.innerHTML = "<input-button></input-button>";
};
