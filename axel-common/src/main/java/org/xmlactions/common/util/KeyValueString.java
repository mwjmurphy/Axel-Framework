package org.xmlactions.common.util;

public class KeyValueString {

	private String key;
	private String value;
	
	public KeyValueString() {
		
	};
	public KeyValueString(String key, String value) {
		this.key = key;
		this.value = value;
	};
	
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
}
