package org.xmlactions.test;

import static org.junit.Assert.*;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.xmlactions.pager.env.EnvironmentAccess;


public class RemotePropertiesTest {
	
	private static final Logger logger = LoggerFactory.getLogger(RemotePropertiesTest.class);

	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runRemoteTests());
	}

	@Test
	public void testRemoteLoad() throws IOException {
		URL url = new URL("http://www.xmlactions.org/schema/menu.xsd");
		assertNotNull(url);
		InputStream is = url.openStream();
		int available = is.available();
		byte [] data = IOUtils.toByteArray(is);
		String s = new String(data);
		logger.debug("read:" + s);
		IOUtils.closeQuietly(is);
	}

	@Test
	public void testRemoteLoadToProperties() throws IOException {
		URL url = new URL("http://www.xmlactions.org/schema/test.properties");
		assertNotNull(url);
		InputStream is = url.openStream();
		Properties props = new Properties();
		props.load(is);
		assertEquals(3, props.size());
		logger.debug("name:" + props.get("name"));
		IOUtils.closeQuietly(is);
	}
	
	
	
}
