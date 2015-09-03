/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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
