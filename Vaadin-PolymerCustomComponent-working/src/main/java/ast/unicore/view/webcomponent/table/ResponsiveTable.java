package ast.unicore.view.webcomponent.table;

import java.util.List;
import java.util.Map;

import ast.unicore.view.webcomponent.papercombo.PaperCombo;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

@JavaScript({ "responsive-table-connector.js" })
public class ResponsiveTable extends AbstractJavaScriptComponent {
	private static final long serialVersionUID = 6202396936779824718L;

	@Override
	protected ResponsiveTableState getState() {
		return (ResponsiveTableState) super.getState();
	}

	public ResponsiveTable(List<Map<String, String>> columns) {
		getState().columns = columns;
		markAsDirty();

		addHandleClickCallback();
	}

	private void addHandleClickCallback() {
		addFunction("handleIconClick", new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				System.out.println(PaperCombo.class.getSimpleName() + "#handleIconClick: ");
			}
		});
	}
}
