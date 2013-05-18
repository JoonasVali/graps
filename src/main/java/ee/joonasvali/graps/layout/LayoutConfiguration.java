package ee.joonasvali.graps.layout;

import java.util.HashMap;

public class LayoutConfiguration {
	private HashMap<String, Object> values = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	public <T> T getValue(String key) {
		return (T) values.get(key);
	}
	
	public void setValue(String key, Object value) {
		values.put(key, value);
	}
}
