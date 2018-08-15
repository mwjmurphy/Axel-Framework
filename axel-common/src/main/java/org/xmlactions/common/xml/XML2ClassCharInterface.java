/*
 * XML2ClassCharInterface.java
 *
 * Created on 2nd February, 2007, 10:07 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.xmlactions.common.xml;

/**
 * 
 * @author MMURPHY
 */
public interface XML2ClassCharInterface {
	/**
	 * @param nodeName
	 *            is the name of the node ie. <element> then nodeName =
	 *            'element'.
	 * @param parser
	 *            contains the node itelf
	 * @param depth
	 *            if set true means that the parser has moved in a node. i.e.
	 *            <root><root1> -> when parser reaches root1 then depth = true;
	 */
	public void parseNode(String nodeName, XMLParserChar parser, boolean depth);

}
