package org.xmlactions.common.io;


import java.net.URL;

import org.xmlactions.common.io.ResourceUtils;


import junit.framework.TestCase;

public class ResourceTest extends TestCase {
	public void testGetResourceURL() {
		URL url = ResourceUtils.getResourceURL("/log4j.properties");
		assertNotNull("Unable to locate resource for [" + "log4j.properties"
				+ "]");
	}
}
