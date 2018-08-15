package org.xmlactions.mapping.json;

import static org.junit.Assert.assertEquals;

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

public class TestJSONUtils {

	private static final Logger logger = LoggerFactory.getLogger(TestJSONUtils.class);

	@Test
	public void testJsonObject() throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("name", "Mike");
		JSONObject child = new JSONObject();
		child.put("name", "Fred");
		obj.put("child", child);
		logger.debug(obj.toString());

		String xml = XML.toString(obj);
		logger.debug(xml);

	}

	@Test
	public void testJsonToXml() throws JSONException, IOException {
		String json = ResourceUtils.loadFile("/json/sample/data.json");
		JSONObject obj = new JSONObject(json);
		logger.debug(obj.toString());
		String xml = XML.toString(obj);
		logger.debug(xml);
	}


	@Test
	public void testXmlToJson() throws JSONException, IOException {
		String json = ResourceUtils.loadFile("/json/sample/data.xml");

		JSONObject obj = XML.toJSONObject(json);
		logger.debug(obj.toString());
		String xml = XML.toString(obj);
		logger.debug(xml);
	}

	@Test
	public void testJsonToMap() throws JSONException, IOException {
		String jsonString = ResourceUtils.loadFile("/json/sample/data.json");
		JSONObject jsonObject = new JSONObject(jsonString);
		for (int i = 0 ; ; i++) {
			Map<String, Object> map = JSONUtils.toMap(jsonObject, "vessel_gear_types_category", i);
			if (map != null) {
				logger.debug("index:" + i);
				dumpMap(map);
			} else {
				break;
			}
		}
		logger.debug(jsonObject.toString());
	}

	@Test
	public void testJsonToMap1() throws JSONException, IOException {
		String jsonString = ResourceUtils.loadFile("/json/sample/json_with_array.json");
		JSONObject jsonObject = new JSONObject(jsonString);
		for (int i = 0 ; ; i++) {
			Map<String, Object> map = JSONUtils.toMap(jsonObject, "list", i);
			if (map != null) {
				logger.debug("index:" + i);
				dumpMap(map);
				logger.debug("row_index:" + map.get("row_index"));
				logger.debug("list/PropertySearchResponse:" + map.get("list/PropertySearchResponse"));
				logger.debug("PropertySearchResponse:" + map.get("PropertySearchResponse"));
				
			} else {
				break;
			}
		}
		logger.debug(jsonObject.toString());
	}

	@Test
	public void testJsonToMapNested() throws JSONException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String jsonString = ResourceUtils.loadFile("/json/sample/data.json");
		JSONObject jsonObject = new JSONObject(jsonString);
		Object obj = JSONUtils.get(jsonObject,"vessel_gear_types_category/title_text", 0);
		assertEquals("Surrounding ", obj.toString());
		obj = JSONUtils.get(jsonObject,"vessel_gear_types_category/title_text", 1);
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
	
	private void dumpMap(Map<String, Object> map) {
		Set set = map.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			Object obj = iterator.next();
			logger.debug("obj:" + obj);
		}
	}

}
