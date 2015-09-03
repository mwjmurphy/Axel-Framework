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

package org.xmlactions.common.xml;

public class XMLXPathParser {

	/**
	 * Get the element path.
	 * <p>
	 * Example 1<br/>
	 * 	xml = <root><row index="1" data="This is the data"/></root><br/>
	 *  xpath = root/row[@data]<br/>
	 *  path = root/row<br/>
	 *  attribute = data<br/>
	 * Example 2<br/>
	 * 	xml = <root><row index="1" data="This is the data"/></root><br/>
	 *  xpath = root/row<br/>
	 *  path = root/row<br/>
	 *  attribute = null<br/>
	 * </p>
	 * @param path
	 * @return the element path
	 * 
	 */
	public static String getPath(String path) {
		String [] paths = path.split("/");
		String lastPath = paths[paths.length-1];
		
		int index;
		if ((index = lastPath.indexOf('[')) > 0) {
			paths[paths.length-1] = lastPath.substring(0, index);
		}
		
		StringBuilder sb = new StringBuilder();
		for (index = 0 ; index < paths.length; index++) {
			if (index > 0) {
				sb.append('/');
			}
			sb.append(paths[index]);
		}
		return sb.toString();
			
	}
	
	
	/**
	 * Get the attribute name if there is one.
	 * <p>
	 * Example 1<br/>
	 * 	xml = <root><row index="1" data="This is the data"/></root><br/>
	 *  xpath = root/row[@data]<br/>
	 *  path = root/row<br/>
	 *  attribute = data<br/>
	 * Example 2<br/>
	 * 	xml = <root><row index="1" data="This is the data"/></root><br/>
	 *  xpath = root/row<br/>
	 *  path = root/row<br/>
	 *  attribute = null<br/>
	 * </p>
	 * @param path
	 * @return the attribute key name
	 * 
	 */
	public static String getAttribute(String path) {
		
		int indexFrom, indexTo;
		if ((indexFrom = path.indexOf("[")) > -1 && (indexTo = path.indexOf("]")) > -1) {
			String key = path.substring(indexFrom+1, indexTo);
			if (key.startsWith("@")) {
				key = key.substring(1);
			}
			return key;
		}
		return null;
		
	}
	
}
