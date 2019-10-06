package org.xmlactions.mapping.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.collections.PropertyKeyValue;
import org.xmlactions.common.io.ResourceUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TestGsonUtils {

	private static final Logger logger = LoggerFactory.getLogger(TestGsonUtils.class);

	@Test
	public void testJsonObject() throws JSONException {
		JsonObject obj = new JsonObject();
		obj.addProperty("name", "Mike");
		JsonObject child = new JsonObject();
		child.addProperty("name", "Fred");
		obj.add("child", child);
		logger.debug(obj.toString());

		JSONObject jsonObject = new JSONObject(obj.toString());
		
		String xml = XML.toString(jsonObject);
		logger.debug(xml);
		assertTrue(xml.contains("<child>"));
		assertTrue(xml.contains("<name>Fred</name></child>"));

	}

	@Test
	public void testJsonToMap() throws JSONException, IOException {
		String jsonString = ResourceUtils.loadFile("/json/sample/data.json");
		Gson gson = new Gson();
		JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
    	int newRowCount [] = {0};
		for (int i = 0 ; ; i++) {
			Object o = GsonUtils.toMap(jsonElement, "vessel_gear_types_category", i, newRowCount);
			if (o instanceof Map) {
				Map<String, Object> map = (Map<String, Object>)o;
				if (map != null) {
					logger.debug("index:" + i);
					dumpMap(map);
				} else {
					break;
				}
			} else if (o == null) {
				break;
			}
		}
		logger.debug(jsonElement.toString());
	}

	@Test
	public void testJsonToMap1() throws JSONException, IOException {
		String jsonString = ResourceUtils.loadFile("/json/sample/json_with_array.json");
		Gson gson = new Gson();
		JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
    	int newRowCount [] = {0};
		for (int i = 0 ; ; i++) {
			Object o = GsonUtils.toMap(jsonElement, "list", i, newRowCount);
			if (o instanceof Map) {
				Map<String, Object> map = (Map<String, Object>)o;
				if (map != null) {
					logger.debug("index:" + i);
					dumpMap(map);
					logger.debug("row_index:" + map.get("row_index"));
					logger.debug("list/PropertySearchResponse:" + map.get("list/PropertySearchResponse"));
					logger.debug("PropertySearchResponse:" + map.get("PropertySearchResponse"));
					
				} else {
					break;
				}
			} else if ( o == null) {
				break;
			}
		}
		logger.debug(jsonElement.toString());
	}

	@Test
	public void testJsonToMapNested() throws JSONException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String jsonString = ResourceUtils.loadFile("/json/sample/data.json");
		Gson gson = new Gson();
		JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
		Object obj = GsonUtils.get(jsonElement,"vessel_gear_types_category/title_text", 0);
		assertEquals("Surrounding ", obj.toString());
		obj = GsonUtils.get(jsonElement,"vessel_gear_types_category/title_text", 1);
		assertEquals("Seine", obj.toString());
	}

	@Test
	public void testJsonToMapNested2() throws JSONException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String jsonString = ResourceUtils.loadFile("/json/sample/json_with_array.json");
		JSONObject jsonObject = new JSONObject(jsonString);
		Object obj = JSONUtils.get(jsonObject,"list/PropertySearchResponse", 0);
		// assertEquals("Surrounding ", obj.toString());
		obj = JSONUtils.get(jsonObject,"list/PropertySearchResponse/PropertyResults/PropertyResult/Resort", 1);
		assertEquals("Vilamoura", obj.toString());
	}

	@Test
	public void testJsonToMap2() throws JSONException, IOException {
		String jsonString = ResourceUtils.loadFile("/json/sample/root.json");
		JSONObject jsonObject = new JSONObject(jsonString);
		for (int i = 0 ; ; i++) {
			Map<String, Object> map = JSONUtils.toMap(jsonObject, "root/vessel_gear_types_category", i);
			if (map != null) {
				logger.debug("index:" + i);
				dumpMap(map);
			} else {
				// completed iteration through all path elements
				break;
			}
		}
		logger.debug(jsonObject.toString());
	}
	
	@Test
	public void testJsonValue() throws JSONException, IOException {
		String jsonString = ResourceUtils.loadFile("/json/sample/hotel.json");
		Gson gson = new Gson();
		JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
		Object obj = GsonUtils.get(jsonElement, "location/type");
		assertEquals("Point", obj.toString());
		obj = GsonUtils.get(jsonElement, "location/coordinates");
		assertEquals("13.894", obj.toString());
		obj = GsonUtils.get(jsonElement, "location/coordinates", 0);
		assertEquals("13.894", obj.toString());
		obj = GsonUtils.get(jsonElement, "location/coordinates", 1);
		assertEquals("40.6972", obj.toString());
		obj = GsonUtils.get(jsonElement, "location/coordinates", 2);
		assertEquals(null, obj);
		obj = GsonUtils.get(jsonElement, "location");
		String result = ""+obj;
		assertTrue(result.contains("Point"));
		assertTrue(result.contains("[13.894,40.6972]"));
	}
	
	private void dumpMap(Map<String, Object> map) {
		Set set = map.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			Object obj = iterator.next();
			logger.debug("obj:" + obj);
		}
	}

}
