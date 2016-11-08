package ast.unicore.view.webcomponent.table;

import java.util.HashMap;
import java.util.Map;

public class Column {
	public final String name;

	public Column(String name) {
		super();
		this.name = name;
	}

	@SuppressWarnings("serial")
	public Map<String, String> asMap() {
		return new HashMap<String, String>() {
			{
				put("name", name);
			}
		};
	}
}
