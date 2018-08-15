package org.xmlactions.common.io;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UriUtils {
	
	private static final Logger log = LoggerFactory.getLogger(UriUtils.class);

	public Map<String, String> getParameters (String uriString) throws URISyntaxException, UnsupportedEncodingException {
		
		Map<String, String> map = new HashMap<String, String>();
		URI uri = new URI(uriString);
		StringBuilder sb = new StringBuilder();
		sb.append("getPath:" + uri.getPath());
		sb.append("\ngetQuery:" + uri.getQuery());
		String [] parts = uri.getQuery().split("&");
		for (String part : parts) {
			String [] ps = part.split("=");
			if (ps.length >= 2) {
				sb.append("\nkey:" + ps[0] + " value:" + ps[1]);
				map.put(ps[0], ps[1]);
			}
		}
		
		sb.append("\ngetRawPath:" + uri.getRawPath());
		sb.append("\ngetRawQuery:" + uri.getRawQuery());
		
		log.debug(sb.toString());
		return map;
	}
}
