/*
 * XML2ClassInterface.java
 *
 * Created on August 5, 2005, 3:22 PM
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
public interface XML2ClassInterface
{
   /**
    * @param nodeName is the name of the node ie. <element> then nodeName = 'element'.
    * @param parser contains the node itelf
    * @param depth if set true means that the parser has moved in a node. i.e. <root><root1> -> when parser reaches root1 then depth = true;
    */
   public void parseNode(String nodeName, XMLParser parser, boolean depth) throws Exception;
}
