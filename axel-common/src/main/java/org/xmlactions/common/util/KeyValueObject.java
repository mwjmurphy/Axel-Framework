package org.xmlactions.common.util;

public class KeyValueObject {

	private String key;
	private Object value;
	
	public KeyValueObject() {
		
	};
	public KeyValueObject(String key, Object value) {
		this.key = key;
		this.value = value;
	};
	
	public void setValue(Object value) {
		this.value = value;
	}
	public Object getValue() {
		return value;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
}
