package org.xmlactions.common.io;


import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import org.junit.Test;
import org.xmlactions.common.io.ResourceUtils;







import junit.framework.TestCase;

public class UriUtilsTest extends TestCase {
	
	@Test
	public void testGetResourceURL() throws URISyntaxException, UnsupportedEncodingException {
		UriUtils uriUtils = new UriUtils();
		uriUtils.getParameters("http://localhost:8080/site/page/index.html?p1=100&p2=fred+was+here&x&=y");
	}
}
