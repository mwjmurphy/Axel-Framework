package org.xmlactions.web;

import java.util.List;

public class HttpParam {

    private String key;
    private Object value;

    private boolean active = true;

    public HttpParam(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
    
    public static HttpParam getHttpParam(List<HttpParam> list, String key) {
    	
    	for (HttpParam param : list) {
    		if (param.getKey().equals(key)) {
    			return param;
    		}
    	}
    	return null;
    }
    
    public static Object getHttpParamValue(List<HttpParam> list, String key) {
    	
    	for (HttpParam param : list) {
    		if (param.getKey().equals(key)) {
    			return param.getValue();
    		}
    	}
    	return null;
    }
    
    public String toString() {
    	return key + " = " + value;
    }
}
