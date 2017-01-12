package ast.unicore.view.webcomponent.table;

import java.util.LinkedHashMap;
import java.util.Map;

public class Row extends LinkedHashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3235107851646945108L;

	public Row() {
		super();
	}

	public Row(int initialCapacity, float loadFactor, boolean accessOrder) {
		super(initialCapacity, loadFactor, accessOrder);
	}

	public Row(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public Row(int initialCapacity) {
		super(initialCapacity);
	}

	public Row(Map<? extends String, ? extends Object> m) {
		super(m);
	}

}
