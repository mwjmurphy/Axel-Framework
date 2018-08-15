package org.xmlactions.common.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLUtils {

	/** This is the xmlns value from the xml, may be null */
	public static final String MAP_KEY_XMLNS = "xmlns";
	
	/** This is the list of namespace URIs */
	public static final String MAP_KEY_URIS = "urilist";
	/**
	 * Find any referenced namespaces(uri) in an xml. <b>EXCLUDING XSD</b>
	 * <p>
	 * The xml may not be well formed, may not even be xml.
	 * </p>
	 * <p>
	 * Finds all instances of xmlns:xxx and xmlns=xxx.
	 * </p>
	 * <p>
	 * All namespaces found are stored in a List<String>.
	 * </p>
	 * <p>
	 * The List of found namespaces are stored in the returned
	 * map with the key constant XMLUtils.MAP_KEY_URIS.
	 * </p>
	 * <p>
	 * The xmlns namespace is stored in the map with the key const
	 * XMLUtils.MAP_KEY_XMLNS.
	 * </p>
	 * @param xml the xml that may contain the xmlns pattern
	 * 
	 * @return A map with the list of found namespaces.
	 * 
	 * @see #MAP_KEY_URIS - retrieve the list of namespace uris with this key
	 * @see #MAP_KEY_XMLNS - retrieve the xmlns= uri with this key
	 */
	public static final Map<String, Object> findNameSpaces(String xml) {
		List<String>nameSpaces = new ArrayList<String>();

		Map<String, Object> map = new HashMap<String, Object>();

		int len = "xmlns:".length();
		int index = 0;
		while ((index = xml.indexOf("xmlns:", index)) >= 0) {
			index += len;
			String nameSpace = getNameSpace(xml, index);
			if (nameSpace != null) {
				String xmlns = findUriFor(xml, nameSpace);
				if (xmlns != null && isAxelNS(xmlns)) {
					nameSpaces.add(nameSpace);
				}
			}
		}

		String xmlns = findUriFor(xml, "xmlns");
		if (xmlns != null) {
			map.put(MAP_KEY_XMLNS, xmlns);
		}

		
		// ===
		// FIXME - kludge around xmlns="...riostl...", all elements are to be processed
		// ===
		if (findRiostlAsNonNamespace(xml) == true) {
			nameSpaces.add("");// process non namespace'd elements
		}

		map.put(MAP_KEY_URIS,nameSpaces);
		return map;
	}
	
	private static String getNameSpace(String xml, int index) {
		for (int loop = index; loop < xml.length() ; loop++){
			char c= xml.charAt(loop);
			if (XMLUtils.isWhiteSpace(xml.charAt(loop)) || c == '=') {
				return (xml.substring(index, loop));
			}
		}
		return null;
	}

	/**
	 * Checks if a char is a whitespace.
	 * <p>
	 * whitespace = ' ' || 0x09 || 0x0a || 0x0d
	 * </p>
	 * @param b - the char to check
	 * @return true if char is a whitespace else false if not
	 */
	public static boolean isWhiteSpace(char b) {
		// TAB LF CR
		if (b == ' ' || b == 0x09 || b == 0x0a || b == 0x0d) {
			return (true);
		}
		return (false);
	}
	
	/**
	 * check if we should process non namespace'd elements as pager elements
	 * kludge around xmlns="...riostl...", 
	 * targetNamespace="http://www.riostl.com/schema"
	 * schemaLocation="http://www.riostl.com/schema/...
	 * 
	 */
	private static boolean findRiostlAsNonNamespace(String xml){
		//if (findFor(xml, "xmlns=")) 
		//	return true;
		if (findFor(xml, "targetNamespace") != null) 
			return true;
		else if (findFor(xml, "xmlns=") != null)
			return true;
		//else if (findFor(xml, "schemaLocation")) 
		//	return true;
		else return false;
	}

	private static String findFor(String xml, String pattern) {
		String ref = findUriFor(xml,pattern);
		if (ref != null) {
			String uri = ref.toLowerCase();
			if (isAxelNS(uri)) {
				return ref;
			}
		}
		return null;
	}

	private static String findUriFor(String xml, String pattern) {
		int index = xml.indexOf(pattern);
		if (index > -1) {
			index += pattern.length();
		}
		int start = -1, end = -1;
		for (; index > -1 && index < xml.length(); index++) {
			char c = xml.charAt(index);
			if (c == '=' || isWhiteSpace(c)) {
				continue;
			}
			if (c == '\"') {
				if (start == -1) {
					start = index;
					end = xml.indexOf('"', index+1);
					break;
				}
			} else {
				// got a character that wasn't a whitespace or wasn't a = or wasn't a ".
				break;
			}
		}
		if (start > -1 && end > -1) {
			String ref = xml.substring(start+1, end);
			return ref;
		}
		return null;
	}
	
	private static String findUriFor(String xml, String pattern, int fromIndex) {
		int start = -1, end = -1;
		for (int index = fromIndex ; index < xml.length(); index++) {
			char c = xml.charAt(index);
			if (c == '=' || isWhiteSpace(c)) {
				continue;
			}
			if (c == '\"') {
				if (start == -1) {
					start = index;
				} else {
					end = index;
				}
			} else {
				// got a character that wasn't a whitespace or wasn't a = or wasn't a ".
				break;
			}
		}
		if (start > -1 && end > -1) {
			String ref = xml.substring(start+1, end);
			return ref;
		}
		return null;
	}
	
	private static boolean isAxelNS(String ns) {
		if (ns.indexOf("uxml.net") >= 0 || 
			ns.indexOf("xmlactions.org") >= 0 || 
			ns.indexOf("jdhtml.com") >= 0 || 
			ns.indexOf("riostl") >= 0) {
			return true;
		}
		return false;
	}
}
