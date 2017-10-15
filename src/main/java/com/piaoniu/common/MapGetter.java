package com.piaoniu.common;

import java.util.Map;
import lombok.AllArgsConstructor;

/**
 * @author code4crafter@gmail.com
 *         Date: 16/6/2
 *         Time: 下午12:27
 */
@AllArgsConstructor
public class MapGetter {

	private Map<String, Object> model;

	public static MapGetter from(Map<String, Object> model){
		return new MapGetter(model);
	}

	public <T> T get(String key) {
		Object value = model.get(key);
		if (value == null) {
			return null;
		}
		return (T) value;
	}

	public <T> T getOrElse(String key,T defaultValue) {
		Object value = model.get(key);
		if (value == null) {
			return defaultValue;
		}
		return (T) value;
	}

	public String getString(String key) {
		Object value = model.get(key);
		if (value == null) {
			return null;
		}
		return String.valueOf(value);
	}
}
