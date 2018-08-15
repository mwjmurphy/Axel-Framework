/*
 * XML2Class.java
 *
 * Created on August 5, 2005, 3:14 PM
 *
 */

package org.xmlactions.common.xml;


/**
 *
 * @author MMURPHY
 */
public abstract class XML2Class implements XML2ClassInterface
{
   
   /**
    * This version of the XML2Class parse doesn't provide depth (how many levels
    * in from root) information for the node.
    * @param parser is the XMLParser for the node to process.
    */
   public void parse(XMLParser parser)
   throws Exception
   {
      try
      {
         byte [] element;
         String name;
         int curPos;
         boolean depth = true;   // we always start by moving in a depth level
         // Log.getInstance().debug("parse: nodeName: " + parser.getNodeNameAsString() + " nextNodeName:" + parser.getNextNodeNameAsString());
         name = parser.getNextNodeNameAsString();
         //Log.getInstance().debug("next node name=" + name);
         //name = parser.getNodeNameAsString();
         while(name != null)
         {
            //Log.getInstance().debug(":" + name);
            curPos = parser.curPos;
            element = parser.getElement(name.getBytes());
            //Log.getInstance().debug("node:" + name + " curPos before:" + curPos + " after:" + parser.curPos + " :" + new String(element));
            //parse(new XMLParser(element));
            parseNode(name, new XMLParser(element), depth);
            depth=false;
            name = parser.getNodeNameAsString();
            //name = parser.getNextNodeNameAsString();
         }
      }
      catch (Exception ex)
      {
         if (parser.getErrorMessage() == null)
         {
            throw ex;
         }
         else
         {
            throw new Exception(parser.getErrorMessage(), ex);
         }
      }
   }
}
