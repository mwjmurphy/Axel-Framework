package org.xmlactions.common.collections;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xmlactions.common.collections.MapUtils;

import junit.framework.TestCase;

public class TestMapUtils extends TestCase {

	public void testLoadPropertiesToMap() throws IOException{
		Map<String, Object> map = MapUtils.loadMapFromProperties("/maputils/actions_db.properties");
		assertNotNull(map);
		assertTrue(map.size() > 0);
	}
	
	public void testNullToMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("f1", "f1");
		map.put("f2", null);
		map.put(null, null);

	}
}
