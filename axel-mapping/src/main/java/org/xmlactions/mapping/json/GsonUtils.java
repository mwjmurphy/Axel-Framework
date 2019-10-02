package org.xmlactions.mapping.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author mike
 * @see package-info.java for documentation 
 */
public class GsonUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(GsonUtils.class);

	/**
	 * 
	 * @param jsonElement - This is the JsonElement we want to map
	 * @param path - This is the target path
	 * @param index - an index we want from the path.
	 * @return a map containing the data or null if the index was out of range.
	 */
	public static Map<String, Object> toMap(JsonElement je, String path, int index) {

		String seperator = "/";
		JsonElement jsonElement = je;
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (index < 0) {
			// bad programming if index < 0
			return null;
		}
		
		if (path == null || path.length() == 0)  {
			toMap(jsonElement, "", "", map);
			return map;
		}
		
		String paths[] = path.split(seperator);

		String workingPath = null;
		for (int i = 0 ; i < paths.length; i++){
			String name = paths[i];
			if (workingPath == null) {
				workingPath = name;
			} else {
				workingPath += seperator + name;
			}

			if (jsonElement.isJsonObject()) {
				jsonElement = getPathObject(jsonElement.getAsJsonObject(), name);
				// toMap(jsonElement, workingPath, "", map);
			} else if (jsonElement.isJsonArray()) {
				jsonElement = getPathObject(jsonElement.getAsJsonArray(), name);
				// toMap(jsonElement, workingPath, "", map);
			} else if (jsonElement.isJsonPrimitive()) {
				// cant handle this no key/value.
			}
		}
		// now should have an array that goes as far as index
		if (jsonElement.isJsonArray()) {
			// loop until we reach index
			JsonArray jsonArray = jsonElement.getAsJsonArray();
			if (jsonArray.size() >= index) {
				jsonElement = jsonArray.get(index);
				toMap(jsonElement, "", "", map);
			} else {
				return null;
			}
		}
		
		return map;
	}

	public static JsonElement getPathObjectDot(JsonElement je, String path, int index) {
		return getPathObjectDot(je, path);
	}
	public static JsonElement getPathObjectDot(JsonElement je, String path) {
		return getPathObject(je, path, ".");
	}

	public static JsonElement getPathObjectSlash(JsonElement je, String path, int index) {
		return getPathObjectSlash(je, path);
	}
	public static JsonElement getPathObjectSlash(JsonElement je, String path) {
		return getPathObject(je, path, "/");
	}
	
	private static JsonElement getPathObject(JsonElement je, String path, String seperator) {
		JsonElement jsonElement = je;
		if (path == null || path.length() == 0) {
			return jsonElement;
		}
		
		String paths[] = path.split(seperator);

		String workingPath = null;
		for (int i = 0 ; i < paths.length; i++){
			String name = paths[i];
			if (workingPath == null) {
				workingPath = name;
			} else {
				workingPath += seperator + name;
			}

			if (jsonElement.isJsonObject()) {
				jsonElement = getPathObject(jsonElement.getAsJsonObject(), name);
			} else if (jsonElement.isJsonArray()) {
				return getPathObject(jsonElement.getAsJsonArray(), name);
			} else if (jsonElement.isJsonPrimitive()) {
				return jsonElement.getAsJsonPrimitive();
			}
		}
		return jsonElement;
	}
	
	public static JsonElement getPathObject(JsonArray jsonArray, String name) {
		for (JsonElement jsonElement : jsonArray) {
			if (jsonElement.isJsonObject()) {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				if (jsonObject.has(name)) {
					return jsonObject.get(name);
				}
			} else if (jsonElement.isJsonArray()) {
				return getPathObject(jsonElement.getAsJsonArray(), name);
			} else if (jsonElement.isJsonPrimitive()) {
				return jsonElement.getAsJsonPrimitive();
			}
		}
		return null;	// not found
	}

	public static JsonElement getPathObject(JsonObject jsonObject, String name) {
		if (jsonObject.has(name)) {
			return jsonObject.get(name);
		}
		return null;	// not found
	}

	private static void toMap(JsonElement jsonElement, String path, String seperator, Map<String, Object> map) {
		if (jsonElement.isJsonObject()) {
			toMap(jsonElement.getAsJsonObject(), path, seperator, map);
		} else if (jsonElement.isJsonArray()) {
			toMap(jsonElement.getAsJsonArray(), path, seperator, map);
		} else if (jsonElement.isJsonPrimitive()) {
			// cant do anything with this as we've got no key/value
		}
	}
	
	private static void toMap(JsonObject jsonObject, String path, String seperator, Map<String, Object> map) {
		Set<Entry<String,JsonElement>> set = jsonObject.entrySet();
		for (Entry<String, JsonElement> entry : set) {
			map.put(path + seperator + entry.getKey(), entry.getValue());
		}
	}

	private static void toMap(JsonArray jsonArray, String path, String seperator, Map<String, Object> map) {
		for (JsonElement jsonElement : jsonArray) {
			if (jsonElement.isJsonObject()) {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				Set<Entry<String,JsonElement>> set = jsonObject.entrySet();
				for (Entry<String, JsonElement> entry : set) {
					map.put(path + seperator + entry.getKey(), entry.getValue());
				}
			} else if (jsonElement.isJsonArray()) {
				toMap(jsonElement.getAsJsonArray(), path, seperator, map);
			} else if (jsonElement.isJsonPrimitive()) {
				// cant do anything with this as we've got no key/value
			}
		}		
	}

	private static Object getObject(JSONObject jsonObject, String name) {
		Object obj = jsonObject.get(name);
		return obj;
	}

	public static Object get(JSONObject jsonObject, String path) {
		return (get(jsonObject, path, 0));
	}

	public static Object get(Object jObject, String path, int index) {

		if (path.length() == 0) {
			return jObject;
		}
		String paths[] = path.split("/");
		String workingPath = null;
		try {
			for (int i = 0 ; i < paths.length; i++){
				String name = paths[i];
				workingPath = name;
				if (jObject instanceof JSONArray) {
					JSONArray jsonArray = (JSONArray) jObject;
					int counter = 0;
					for (int iLoop = 0 ; iLoop < jsonArray.length(); iLoop++) {
						jObject = jsonArray.get(iLoop);
						if (jObject instanceof JSONObject) {
							JSONObject jsonObject = (JSONObject) jObject;
							if (jsonObject.has(workingPath)) {
								counter++;
								if (counter > index) {
									break;
									//Object o = jsonObject.get(workingPath);
									//return o;
								}
							}
						}
					}
				}
				if (jObject instanceof JSONObject) {
					JSONObject jsonObject = (JSONObject) jObject;
					jObject = jsonObject.get(workingPath);
				}
			}
			return jObject;
		} catch (Exception ex) {
			throw new IllegalArgumentException("jObject[" + jObject + "] path[" + path + "] index[" + index + "] workingPath[" + workingPath + "]", ex);
		}
	}

	public static Object get(String jsonString, String path, int index) {
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			return get(jsonObject, path, index);
		} catch (Exception ex) {
			logger.info("get error: jsonString:[{}] path:[{}] index[{}]", jsonString, path, index);
			return null;
			//throw new IllegalArgumentException("get error: jsonString:[" + jsonString + "] path:[" + path + "] index[" + index + "]", ex);
		}
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
