package org.xmlactions.common.text;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.bsf.BSFException;
import org.apache.commons.lang.text.StrSubstitutor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.scripting.Scripting;

public class TestReplaceMarkers {
	
	private static final Logger logger = LoggerFactory.getLogger(TestReplaceMarkers.class);
	
	@Test
	public void testStrSubstitutor() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("k1", "value of k1");
		
		String text = "${k0} can go here and we can put ${k1} here and nothing ${k2} here and ${k3} here";
		String result = StrSubstitutor.replace(text, map);
		result = ReplaceMarkers.removeUnusedMarkers(result, "null");
		assertEquals("null can go here and we can put value of k1 here and nothing null here and null here", result);
	}
	
	@Test
	public void testNumbers() throws BSFException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("k1", "10");
		
		String text = "var k0 = ${k0}; var k1 = ${k1}; if (null > null) { k0 = 1} else {k0=2} ; if (2 == k0) { \"k0:\" + k0 } else { \"k1:\" + k1 }; ";
		String result = ReplaceMarkers.replace(text, map);
		logger.debug(result);
		Object object = Scripting.getInstance().evaluate(result);
		logger.debug(object.toString());
		assertEquals("k0:2", object);
		
	}
}
