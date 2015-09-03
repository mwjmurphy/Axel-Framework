/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.xmlactions.common.text;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.text.StrSubstitutor;

public class ReplaceMarkers {

	/**
	 * This will firstly replace any markers within the text with values found in the map and secondly
	 * remove any unset markers within the text. An unset marker is ${...}. This usually means that the
	 * StrSubstutior was not able to find a value for the marker and so left it unchanged.
	 * 
	 * Any unset markers will be replaced with "null"
	 * 
	 * @param text
	 * @return the text updated with the replacement markers
	 */
	public static String replace(String text, Map<String,Object> map) {
		text = StrSubstitutor.replace(text, map);
		text = removeUnusedMarkers(text, "null");
		return text;
	}

	/**
	 * This will firstly replace any markers within the text with values found in the map and secondly
	 * remove any unset markers within the text. An unset marker is ${...}. This usually means that the
	 * StrSubstutior was not able to find a value for the marker and so left it unchanged.
	 * 
	 * Any unset markers will be replaced with the missingReplaceValue.
	 * @param text
	 * @return the text updated with the replacement markers
	 */
	public static String replace(String text, Map<String,Object> map, Object missingReplacementValue) {
		text = StrSubstitutor.replace(text, map);
		text = removeUnusedMarkers(text, missingReplacementValue);
		return text;
	}

	/**
	 * This will remove unset markers within the text. An unset marker is ${...}. This usually
	 * means that the StrSubstutior was not able to find a value for the marker and so left it
	 * unchanged.
	 * 
	 * This method will simply replace any ${...} that it finds in the text with a null.
	 * @param text
	 * @return the text with the replacement markers removed.
	 */
	public static String removeUnusedMarkers(String text, Object missingReplacementValue) {
		String [] markers = findMarkers(text);
		if (markers.length > 0) {
			Map<String,Object> map = new HashMap<String,Object>();
			for (String marker : markers) {
				map.put(marker, missingReplacementValue);
			}
			text = StrSubstitutor.replace(text, map);
		}
		return text;
	}
	
	private static String []  findMarkers(String text){
		StringBuilder sbMarkers = new StringBuilder();
		int index = 0;
		while ((index = findMarker(sbMarkers, text, index)) >= 0) {
			// logger.debug(sbMarkers.toString());
		}
		if (sbMarkers.length() > 0) {
			return sbMarkers.toString().split(",");
		} else {
			return new String[0];
		}
		
	}
	
	private static int findMarker(StringBuilder markers, String text, int from) {
		int index = text.indexOf("${", from);
		if (index >= 0) {
			int to = text.indexOf("}", from+2);
			if (to >= 0) {
				if (markers.length() > 0) {
					markers.append(',');
				}
				markers.append(text.substring(index+2, to));
				return to;
			}
		}
		return -1;
	}

}
