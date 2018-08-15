package org.xmlactions.ee.actions;

import java.util.HashMap;
import java.util.Map;

public class TablePrefit {

	private String tableName;
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public Object put(String key, Object value) {
		return map.put(key, value);
	}
	
	public Object get(String key) {
		return map.get(key);
	}
	
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	
}
