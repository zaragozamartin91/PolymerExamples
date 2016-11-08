package ast.unicore.view.webcomponent.table;

import java.util.Map;

public class IconColumn extends Column {
	public final String icon;

	public IconColumn(String name, String icon) {
		super(name);
		this.icon = icon;
	}

	@Override
	public Map<String, String> toMap() {
		Map<String, String> map = super.toMap();
		map.put("icon", icon);
		return map;
	}

	public static IconColumn fromMap(Map<String, String> map) {
		return new IconColumn(map.get("name"), map.get("icon"));
	}
}
