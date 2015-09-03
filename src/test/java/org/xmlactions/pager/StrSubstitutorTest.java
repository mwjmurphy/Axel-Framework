package org.xmlactions.pager;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;


public class StrSubstitutorTest extends TestCase {

	private static Logger log = LoggerFactory.getLogger(StrSubstitutorTest.class);
	public void testSimple()
	{
		Map <String, String> map = new HashMap<String, String>();
		map.put("a", "b");
		log.debug("result:" + StrSubstitutor.replace("${a}", map));
		
		
	}
}
