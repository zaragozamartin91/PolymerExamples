function linkImport(href) {
  var link = document.createElement("link");
  link.rel = "import";
  link.href = href;
  return link;
}

// 1. Load Polymer before any code that touches the DOM.
var script = document.createElement("script");
script.src = "VAADIN/webcomponents/bower_components/webcomponentsjs/webcomponents-lite.min.js";
var head = document.querySelector("head");
head.appendChild(script);

window.addEventListener('WebComponentsReady', function (e) {
  console.log("WebComponentsReady!");

  // import custom elements
  head.appendChild(linkImport("VAADIN/webcomponents/bower_components/paper-button/paper-button.html"));
  head.appendChild(linkImport("VAADIN/webcomponents/bower_components/paper-input/paper-input.html"));
  head.appendChild(linkImport("VAADIN/webcomponents/bower_components/paper-item/paper-item.html"));
  head.appendChild(linkImport("VAADIN/webcomponents/bower_components/paper-listbox/paper-listbox.html"));
  head.appendChild(linkImport("VAADIN/webcomponents/bower_components/paper-dropdown-menu/paper-dropdown-menu.html"));
  head.appendChild(linkImport("VAADIN/webcomponents/paper-combo.html"));
  head.appendChild(linkImport("VAADIN/webcomponents/bower_components/paper-styles/paper-styles.html"));
  head.appendChild(linkImport("VAADIN/webcomponents/bower_components/paper-input/paper-textarea.html"));
  head.appendChild(linkImport("VAADIN/webcomponents/paper-wb-checkbox.html"));
  head.appendChild(linkImport("VAADIN/webcomponents/bower_components/iron-icons/iron-icons.html"));
  head.appendChild(linkImport("VAADIN/webcomponents/bower_components/iron-icons/editor-icons.html"));
  head.appendChild(linkImport("VAADIN/webcomponents/responsive-table-multi.html"));

});