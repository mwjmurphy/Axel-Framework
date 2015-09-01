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

package org.xmlactions.action;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.common.xml.XMLParserChar;

public class ActionMarkers {

	private static Logger log = LoggerFactory.getLogger(ActionMarkers.class);

	/**
	 * This method gets a list of all the replacement sections in the page. Use
	 * this to build a list of the replacement objects. The replacement objects
	 * may already be in memory is session data or may have been passed back by
	 * the html request or may need to be retrieved using code.
	 * 
	 * @param page
	 *            is the base page that may contain sections that we want to
	 *            replace with other data.
	 * @param nameSpaces
	 *            Name spaces used by this xml
	 * @param context
	 * @param actionMapName
	 * @return a list of all the replacement section names that is used to build
	 *         the replacement objects.
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws BadXMLException
	 * @throws an
	 *             exception if something goes wrong and we want to notify the
	 *             caller.
	 */
	public List<ReplacementMarker> getReplacementList(String page, char [][]nameSpaces, IExecContext context, String actionMapName)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, BadXMLException {

		ArrayList<ReplacementMarker> list = new ArrayList<ReplacementMarker>();

		XMLParserChar xmlParser = new XMLParserChar(page.toCharArray(), false);
		String elementAsString;
		while ((elementAsString = xmlParser.getNextNodeWithNSAsString(nameSpaces)) != null) {

			// log.debug("found element [" + elementAsString + "]");
			int start, end;
			start = xmlParser.getCurPos() - elementAsString.length();
			end = xmlParser.getCurPos();
			ReplacementMarker rm = new ReplacementMarker(start, end, elementAsString, nameSpaces, context, actionMapName);

            if (rm.getAction() != null) {
                list.add(rm);
                // We may not have an action.
                if (!StringUtils.isEmpty(rm.getAction().getContent())) {
                    rm.setNestedMarkers(getReplacementList(rm.getAction().getContent(),
                            nameSpaces,
                            context,
                            actionMapName));
                }
			}
		}
		if (xmlParser.getErrorMessage() != null) {
			throw new BadXMLException(xmlParser.getErrorMessage());
			// log.error(xmlParser.getErrorMessage());
		}
		return (list);
	}

	/**
	 * This method gets a list of all the replacement sections in the page. Use
	 * this to build a list of the replacement objects. The replacement objects
	 * may already be in memory is session data or may have been passed back by
	 * the html request or may need to be retrieved using code.
	 * 
	 * @param page
	 *            is the base page that may contain sections that we want to
	 *            replace with other data.
	 * @return a list of all the replacement section names that is used to build
	 *         the replacement objects.
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws an
	 *             exception if something goes wrong and we want to notify the
	 *             caller.
	 */
	public List<ReplacementMarker> getReplacementList(String page, IExecContext context, String actionMapName)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		ArrayList<ReplacementMarker> list = new ArrayList<ReplacementMarker>();

		XMLParserChar xmlParser = new XMLParserChar(page.toCharArray(), false);

		// get the first node name
		String nodeName = xmlParser.getNodeNameAsString();
		if (nodeName != null) {

			String elementAsString = xmlParser.getNodeAsString(nodeName);

			while (elementAsString != null) {

				// log.debug("found element [" + elementAsString + "]");
				int start, end;
				start = xmlParser.getCurPos() == 0 ? 0 : xmlParser.getCurPos() - elementAsString.length();
				end = xmlParser.getCurPos();
				ReplacementMarker rm = new ReplacementMarker(start, end, elementAsString, context, actionMapName);

				// use the "." to cheat and skip the first element which would
				// cause
				// a never ending loop
				rm.setNestedMarkers(getReplacementList("." + elementAsString.substring(1), context, actionMapName));

				list.add(rm);

				// get the next node name
				nodeName = xmlParser.getNextNodeNameAsString();
				if (nodeName != null) {
					elementAsString = xmlParser.getNodeAsString(nodeName);
				} else {
					elementAsString = null;
				}
			}
		}
		if (xmlParser.getErrorMessage() != null) {
			log.error(xmlParser.getErrorMessage());
		}
		return (list);
	}
}
