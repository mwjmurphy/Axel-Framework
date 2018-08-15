package org.xmlactions.pager.web.session;

import java.util.HashMap;

public class SessionBean {
	
	private String value = "Any Value";
	private HashMap<String, String> map = new HashMap<String, String>();

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}
}
