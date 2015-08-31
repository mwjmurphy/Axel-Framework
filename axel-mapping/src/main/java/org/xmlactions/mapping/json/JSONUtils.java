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

package org.xmlactions.mapping.json;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mike
 * @see package-info.java for documentation 
 */
public class JSONUtils {

	private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);

	/**
	 * 
	 * @param jsonObject - This is the JSONObject we want to map
	 * @param path - This is the target path
	 * @param index - an index we want from the path.
	 * @return a map contaning the data or null if the index was out of range.
	 */
	public static Map<String, Object> toMap(JSONObject jsonObject, String path, int index) {
		
		if (index < 0) {
			// bad programming if index < 0
			return null;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// get any root attributes
		for (String name: JSONObject.getNames(jsonObject)){
			Object value = jsonObject.get(name);
			if (! (value instanceof JSONObject || value instanceof JSONArray)) {
				map.put(name, value);
			}
		}
		// get the json at this path
		Object obj = getPathObject(jsonObject, path, map);
		if (obj instanceof  JSONObject) {
			if (index > 0) {
				map = null;	// as we got back a JSONObject and not an array we must have processed the single item.
			} else {
				map.put(path, obj);
				for (String name: JSONObject.getNames((JSONObject)obj)){
					Object data = ((JSONObject)obj).get(name);
					map.put(name, data);
					map.put(path + "/" + name, data);
				}
			}
		} else if (obj instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)obj;
			if (index >= jsonArray.length()) {
				return null;
			}
			Object value = jsonArray.get(index);
			map.put("row_index", index+1);	// humanise the index starting at 1 not 0
			if (value instanceof JSONObject) {
				for (String name: JSONObject.getNames((JSONObject)value)){
					Object data = ((JSONObject)value).get(name);
					map.put(name, data);
					map.put(path + "/" + name, data);
				}
			} else if (value instanceof JSONArray) {
				map.put(path, value);
			} else {
				map.put(path, value);
			}
		} else {
			map.put(path, obj);
		}
		return map;
	}

	private static Object getPathObject(JSONObject jsonObject, String path, Map<String, Object> map) {
		String paths[] = path.split("/");
		Object obj = null;
		String workingPath = null;
		for (int i = 0 ; i < paths.length; i++){
			String name = paths[i];
			if (workingPath == null) {
				workingPath = name;
			} else {
				workingPath += "/" + name;
			}
			obj = jsonObject.get(name);
			if (obj instanceof JSONObject) {
				jsonObject = (JSONObject) obj;
				// Add all attributes found on way up
				for (String att: JSONObject.getNames((JSONObject)obj)){
					Object data = ((JSONObject)obj).get(att);
					if (! (data instanceof JSONObject || data instanceof JSONArray)) {
						// append all path attributes
						if (workingPath == null) {
							map.put(att, data);
						} else {
							map.put(workingPath + "/" + att, data);
						}
					}
				}
			} else {
				if (i == paths.length-1) {
					break;
				} else {
					throw new IllegalArgumentException("Invalid Path [" + path + "] no valid object found!");
				}
			}
		}
		return obj;
	}
	
	private static Object getObject(JSONObject jsonObject, String name) {
		Object obj = jsonObject.get(name);
		return obj;
	}
	
	
	private static String escape(String xml) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0, length = xml.length(); i < length; i++) {
			char c = xml.charAt(i);
			switch (c) {
			case '&':
				sb.append("&amp;");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();

	}


	public static JSONObject mapXmlToJson(String xml, boolean escape) {
		JSONObject jsonObject = null;		
		if (escape) {
			String excapedXml = escape(xml);
			jsonObject = XML.toJSONObject(excapedXml);
		} else {
			jsonObject = XML.toJSONObject(xml);
		}
		return jsonObject;
	}
}
