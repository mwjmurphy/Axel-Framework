package org.xmlactions.common.collections;
/**
 * Utility class for managing a property key value. Usefull in a list.
 * @author mike
 *
 */
public class PropertyKeyValue {
	private String key;
	private Object value;
	
	public PropertyKeyValue(String key, Object value){
		setKey(key);
		setValue(value);
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
