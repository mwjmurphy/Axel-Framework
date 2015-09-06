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

/*
 * XML2Class.java
 *
 * Created on August 5, 2005, 3:14 PM
 *
 */

package org.xmlactions.common.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author MMURPHY
 */
public abstract class XML2ClassChar implements XML2ClassCharInterface {
	private static Logger log = LoggerFactory.getLogger(XML2ClassChar.class);

	/**
	 * This version of the XML2Class parse doesn't provide depth (how many
	 * levels in from root) information for the node.
	 * 
	 * @param parser
	 *            is the XMLParser for the node to process.
	 */
	public void parse(XMLParserChar parser) {
		char[] element;
		String name;
		boolean depth = true; // we always start by moving in a depth level
		// Log.getInstance().debug("parse: nodeName: " +
		// parser.getNodeNameAsString() + " nextNodeName:" +
		// parser.getNextNodeNameAsString());
		name = parser.getNextNodeNameAsString();
		// Log.getInstance().debug("next node name=" + name);
		// name = parser.getNodeNameAsString();
		while (name != null) {
			element = parser.getElement(name.toCharArray());
			// Log.getInstance().debug("node:" + name + " curPos before:" +
			// curPos + " after:" + parser.curPos + " :" + new String(element));
			// parse(new XMLParser(element));
			try {
				parseNode(name, new XMLParserChar(element), depth);
			} catch (Exception ex) {
				throw new IllegalArgumentException("Error processing xml for element [" + name + "]. Closing element for " + name + " not found, is the XML well former?. " + ex.getMessage() +
						"\nxml:" + new String (parser.buffer), ex);
			}
			depth = false;
			name = parser.getNodeNameAsString();
			// name = parser.getNextNodeNameAsString();
		}
	}

	/**
	 * This version of the XML2Class parse doesn't provide depth (how many
	 * levels in from root) information for the node.
	 * 
	 * @param parser
	 *            is the XMLParser for the node to process.
	 * @param nameSpace
	 *            only process elements with this namespace
	 * 
	 */
	public void parse(XMLParserChar parser, String nameSpace) {
		char[] element;
		String name;
		boolean depth = true; // we always start by moving in a depth level
		while ((name = parser.getNextNodeNameAsString()) != null) {
			if (name.startsWith(nameSpace + ":")) {
				// log.debug("got element:" + name);
				element = parser.getElement(name.toCharArray());
				parseNode(name, new XMLParserChar(element), depth);
				depth = false;
			} else {
				parser.setCurPos(parser.getCurPos() + 1);
			}
		}
	}
	/**
	 * This version of the XML2Class parse doesn't provide depth (how many
	 * levels in from root) information for the node.
	 * 
	 * @param parser
	 *            is the XMLParser for the node to process.
	 * @param nameSpaces
	 *            only process elements with these namespaces
	 * 
	 */
	public void parse(XMLParserChar parser, char [][]nameSpaces) {
		char[] element;
		String name;
		boolean depth = true; // we always start by moving in a depth level

		XMLParserChar reader;
		while ((reader = parser.getNextNodeWithNS(nameSpaces)) != null) {
			name = reader.getNameOfNode();
			parseNode(name, reader, depth);
			depth = false;
		}
	}
}
