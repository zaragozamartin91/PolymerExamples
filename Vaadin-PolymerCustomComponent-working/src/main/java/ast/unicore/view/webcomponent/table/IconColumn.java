package ast.unicore.view.webcomponent.table;

import java.util.Map;

public class IconColumn extends Column {
	public final String icon;

	public IconColumn(String name, String icon) {
		super(name);
		this.icon = icon;
	}

	@Override
	public Map<String, String> asMap() {
		Map<String, String> map = super.asMap();
		map.put("icon", icon);
		return map;
	}
}
