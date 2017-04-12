package misc;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

public class GuavaTest {

	@Test
	public void test() {
		Map<String, Object> map = new HashMap<>();
		map.put("uno", "martin");

		ImmutableList<Object> newValues = FluentIterable.from(map.values()).append("julio").toList();

		assertTrue(map.size() == 1);
		assertTrue(newValues.size() == 2);
	}

}
