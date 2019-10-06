package org.xmlactions.mapping.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

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
	 * @param newRowCount - we use this to pass back the new index - check does it match the index passed in???
	 * @return a map containing the data or null if the index was out of range.
	 */
	public static Object toMap(JsonElement je, String path, int index, int [] newRowCount) {

		newRowCount[0] = 0;
		String seperator = "/";
		JsonElement jsonElement = je;
		
		Map<String, Object> map = new HashMap<String, Object>();

		if (path == null || path.length() == 0)  {
			toMap(jsonElement, "value",  map);
			return map;
		}
		
		String paths[] = path.split(seperator);

		String name = path;
		for (int i = 0 ; i < paths.length; i++){
			name = paths[i];

			if (jsonElement.isJsonObject()) {
				jsonElement = getPathObject(jsonElement.getAsJsonObject(), name);
			} else if (jsonElement.isJsonArray()) {
				JsonArray jsonArray = jsonElement.getAsJsonArray();
				if (jsonArray.size() > index) {
					jsonElement = jsonArray.get(index);
					newRowCount[0] = index;
				} else {
					return null;
				}
				if (jsonElement.isJsonArray()) {
					jsonElement = getPathObject(jsonElement.getAsJsonArray(), name);
				} else if (jsonElement.isJsonObject()) {
					jsonElement = getPathObject(jsonElement.getAsJsonObject(), name);
				} else {
					return null;
				}
			} else if (jsonElement.isJsonPrimitive()) {
				// cant handle this no key/value.
				return null;
			}
		}
		// now should have an array that goes as far as index
		if (jsonElement.isJsonArray()) {
			JsonArray jsonArray = jsonElement.getAsJsonArray();
			if (index < 0) {
				return jsonArray.toString();
			} else {
				// loop until we reach index
				if (jsonArray.size() > index) {
					newRowCount[0] = index;
					jsonElement = jsonArray.get(index);
					if (jsonElement.isJsonPrimitive()) {
						return jsonElement.getAsString();
					} else if (jsonElement.isJsonObject()) {
						map = toMap(jsonElement.getAsJsonObject());
						return map;
					} else if (jsonElement.isJsonArray()) {
						return jsonElement.getAsJsonArray();
					}
					return jsonElement;
				} else {
					return null;
				}
			}
		} else if (jsonElement.isJsonObject()) {
			toMap(jsonElement.getAsJsonObject(), map);
		} else if (jsonElement.isJsonPrimitive() ) {
			return jsonElement.getAsJsonPrimitive().getAsString();
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

		for (int i = 0 ; i < paths.length; i++){
			String name = paths[i];

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

	public static void toMap(JsonElement jsonElement, String path, Map<String, Object> map) {
		if (jsonElement.isJsonObject()) {
			toMap(jsonElement.getAsJsonObject(), map);
		} else if (jsonElement.isJsonArray()) {
			map.put(path, jsonElement);
		} else if (jsonElement.isJsonPrimitive()) {
			map.put(path, jsonElement.getAsString());
		}
	}
	
	private static void toMap(JsonObject jsonObject, Map<String, Object> map) {
		Set<Entry<String,JsonElement>> set = jsonObject.entrySet();
		for (Entry<String, JsonElement> entry : set) {
			if (entry.getValue().isJsonPrimitive()) {
				map.put(entry.getKey(), entry.getValue().getAsString());
			} else {
				map.put(entry.getKey(), entry.getValue());
			}
		}
	}

	private static Map<String, Object> toMap(JsonObject jsonObject) {
		Map<String, Object> map = new HashMap<String, Object>();
		Set<Entry<String,JsonElement>> set = jsonObject.entrySet();
		for (Entry<String, JsonElement> entry : set) {
			if (entry.getValue().isJsonPrimitive()) {
				map.put(entry.getKey(), entry.getValue().getAsString());
			} else {
				map.put(entry.getKey(), entry.getValue());
			}
		}
		return map;
	}

//	private static void toMap(JsonArray jsonArray, String path, Map<String, Object> map) {
//		for (JsonElement jsonElement : jsonArray) {
//			if (jsonElement.isJsonObject()) {
//				JsonObject jsonObject = jsonElement.getAsJsonObject();
//				Set<Entry<String,JsonElement>> set = jsonObject.entrySet();
//				for (Entry<String, JsonElement> entry : set) {
//					if (entry.getValue().isJsonPrimitive()) {
//						map.put(entry.getKey(), entry.getValue().getAsString());
//					} else {
//						map.put(entry.getKey(), entry.getValue());
//					}
//				}
//			} else if (jsonElement.isJsonArray()) {
//				toMap(jsonElement.getAsJsonArray(), path, map);
//			} else if (jsonElement.isJsonPrimitive()) {
//				if (path == null || path.length() == 0) {
//					map.put("value", jsonElement.getAsString());
//				} else {
//					map.put(path, jsonElement.getAsString());
//				}
//			}
//		}		
//	}
//	private static Object getObject(JsonElement je, String name) {
//		if (je.isJsonArray()) {
//			JsonArray jsonArray = je.getAsJsonArray();
//			for (JsonElement jsonElement : jsonArray) {
//				if (jsonElement.isJsonObject()) {
//					JsonObject jsonObject = jsonElement.getAsJsonObject();
//					if (jsonObject.has(name)) {
//						return jsonObject.get(name);
//					}
//				}
//			}
//		}
//		if (je.isJsonObject()) {
//			JsonObject jsonObject = je.getAsJsonObject();
//			if (jsonObject.has(name)) {
//				return jsonObject.get(name);
//			}
//		}
//		if (je.isJsonPrimitive()) {
//			return null;
//			// JsonPrimitive jsonPrimitive = je.getAsJsonPrimitive();
//			// return jsonPrimitive;
//			
//		}
//		return null;
//	}

	public static Object get(JsonElement jsonElement, String path) {
		return (get(jsonElement, path, 0));
	}

	public static Object get(JsonElement jsonElement, String path, int index) {

		if (path.length() == 0) {
			return jsonElement;
		}
		String paths[] = path.split("/");
		String workingPath = null;
		boolean processedArray = false;
		try {
			for (int i = 0 ; i < paths.length; i++){
				String name = paths[i];
				workingPath = name;
				if (jsonElement.isJsonArray()) {
					JsonArray jsonArray = jsonElement.getAsJsonArray();
					int counter = 0;
					for (int iLoop = 0 ; iLoop < jsonArray.size(); iLoop++) {
						jsonElement = jsonArray.get(iLoop);
						if (jsonElement.isJsonObject()) {
							JsonObject jsonObject = jsonElement.getAsJsonObject();
							if (jsonObject.has(name)) {
								jsonElement = jsonObject.get(name);
								counter++;
								if (counter > index) {
									processedArray = true;
									break;
								}
							}
						}
					}
				} else if (jsonElement.isJsonObject()) {
					JsonObject jsonObject = jsonElement.getAsJsonObject();
					if (jsonObject.has(name)) {
						jsonElement = jsonObject.get(name);
					} else {
						return null;
					}
				} else if (jsonElement.isJsonPrimitive()) {
					// what to do here
				}
			}
			if (processedArray == false && jsonElement.isJsonArray()) {
				JsonArray ja = jsonElement.getAsJsonArray();
				int size = ja.size();
				if (size > index) {
					jsonElement = ja.get(index);
				} else {
					return null;	// outside index
				}
			}
			if (jsonElement.isJsonPrimitive()) {
				return jsonElement.getAsString();
			}
			return jsonElement;
		} catch (Exception ex) {
			throw new IllegalArgumentException("jObject[" + jsonElement + "] path[" + path + "] index[" + index + "] workingPath[" + workingPath + "]", ex);
		}
	}

	public static Object get(String jsonString, String path, int index) {
		try {
			Gson gson = new Gson();
			JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
			return get(jsonElement, path, index);
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
