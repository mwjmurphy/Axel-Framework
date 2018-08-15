/*
 * XMLParser.java
 *
 * Created on June 17, 2005, 10:12 PM
 *
 */

package org.xmlactions.common.xml;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *
 * @author MMURPHY
 */
public class XMLParser extends XMLReader
{
   private static Logger log = LoggerFactory.getLogger(XMLParser.class);
   
   /** Creates a new instance of XMLParser */
   public XMLParser()
   {
      super();
   }
   
   /** Creates a new instance of XMLParser */
   public XMLParser(byte [] buffer, boolean debug)
   {
      super(buffer);
      super.debug = debug;
   }
   /** Creates a new instance of XMLParser */
   public XMLParser(byte [] buffer)
   {
      super(buffer);
   }
   
   public String getNextNodeNameAsString()
   {
      int pos = this.curPos;
      this.curPos++;
      String s = getNameOfNode();
      this.curPos = pos;
      if (s == null)
      {
         return(null);
      }
      return(s);
   }
   
   public String getNodeNameAsString()
   {
      int pos = this.curPos;
      String s = getNameOfNode();
      this.curPos = pos;
      if (s == null)
      {
         return(null);
      }
      return(s);
   }
   
   protected String getNameOfNode()
   {
      StringBuffer sb = new StringBuffer();
      //Log.getInstance().debug("XML[" + this.getCurPos() + "]:" + this.toString());
      if (this.findStartElement() > -1)
      {
         this.skipWhiteSpace();
         int iLoop = 0;
         while( true )
         {
            try
            {
               byte b = read();
               //Log.getInstance().debug("b:" + (char)b);
               if (this.isWhiteSpace(b) == true)
               {
                  break;
               }
               else if (iLoop == 0 && (b == '/' || b == '?'))
               {
                  this.findStartElement();
                  this.skipWhiteSpace();
                  iLoop=0;
                  continue;
               }
               else if (b == '/' || b == '>')
               {
                  break;
               }
               sb.append((char)b);
               iLoop++;
            }
            catch (EndOfBufferException e)
            {
               return(null);
            }
            catch (Exception ex)
            {
               error.append(this.getClass().getName() + ".getNextNode Exception:" + ex.getMessage());
               return(null);
            }
         }
      }
      else
      {
         return(null);
      }
      //Log.getInstance().debug("found nodeName:" + sb);
      return(sb.toString());
   }
   
   public String getAttributeNameAsString()
   {
      return(getNameOfAttribute());
   }
   
   /**
    * This assumes that the next text will be either the element name (preceded by <)
    * or the next attribute name.
    */
   protected String getNameOfAttribute()
   {
      int markPos = this.getCurPos();
      StringBuffer sb = new StringBuffer();
      this.skipWhiteSpace();
      while( true )     // read until we get an attribute or end of element
      {
         try
         {
            byte b = read();
            if (b == '<')
            {
               // We are at the beginning of the element
               this.skipWhiteSpace();  // move to element name
               this.skipXMLName();  // skip element name
               this.skipWhiteSpace();  // move to attribut name if one exists
            }
            else if (this.isXMLNameChar(b) == false)
            {
               curPos--;
               break;
            }
            else
            {
               sb.append((char)b);
            }
         }
         catch (EndOfBufferException e)
         {
            this.setCurPos(markPos);
            return(null);
         }
         catch (Exception ex)
         {
            this.setCurPos(markPos);
            error.append(this.getClass().getName() + ".getNameOfAttribute Exception:" + ex.getMessage());
            return(null);
         }
      }
      if (sb.length() > 0)
      {
         return(sb.toString());
      }
      this.setCurPos(markPos);
      return(null);
   }
   
   public XMLAttribute getNextAttribute()
   {
      //XMLAttribute att
      String name = getNameOfNextAttribute();
      int newPos = this.curPos;
      // Log.getInstance().debug("\n============\n" + JS.getCurrentMethodName_static() + " 1. curPos:" + this.curPos + " " + name);
      if (name == null || name.length() == 0)
         return(null);
      // @todo must fix this to get the value for this named attribute if two attributes with the same name exist
      //Object value = this.getAttributeValue(name);
      Object value = this.getAttributeValue();
      if (newPos > this.curPos)
      {
         //Log.getInstance().debug(JS.getCurrentMethodName_static() + " 2.a curPos:" + this.curPos + " value:" + value);
         //return(new XMLAttribute(name, value));
         return(null);// this is a kludge to stop us looping forever when we have two attributes with the same name.
      }
      //Log.getInstance().debug(JS.getCurrentMethodName_static() + " 2. curPos:" + this.curPos + " value:" + value);
      return(new XMLAttribute(name, value));
   }
   
   /**
    * This assumes that the next text will be either the element name (preceded by <)
    * or the next attribute name.
    */
   protected String getNameOfNextAttribute()
   {
      //int markPos = this.getCurPos();
      StringBuffer sb = new StringBuffer();
      this.skipWhiteSpace();
      while( true )     // read until we get an attribute or end of element
      {
         try
         {
            byte b = read();
            if (b == '<')
            {
               // We are at the beginning of the element
               this.skipWhiteSpace();  // move to element name
               this.skipXMLName();  // skip element name
               this.skipWhiteSpace();  // move to attribut name if one exists
            }
            else if (this.isXMLNameChar(b) == false)
            {
               curPos--;
               break;
            }
            else
            {
               sb.append((char)b);
            }
         }
         catch (EndOfBufferException e)
         {
            //this.setCurPos(markPos);
            return(null);
         }
         catch (Exception ex)
         {
            //this.setCurPos(markPos);
            error.append(this.getClass().getName() + ".getNameOfAttribute Exception:" + ex.getMessage());
            return(null);
         }
      }
      if (sb.length() > 0)
      {
         return(sb.toString());
      }
      //this.setCurPos(markPos);
      return(null);
   }
   
   /**
    * @return the list of attributes for the current node in the format
    * name="name" att1="content of att1" etc.
    */
   public String getAttributeList()
   {
      String attName;
      StringBuffer sb = new StringBuffer();
      while( (attName = getAttributeNameAsString()) != null)
      {
         //log.debug("attName[" + curPos + "]:" + attName);
         String value = getAttributeValue(attName);
         if (value != null)
         {
            // log.debug("value[" + curPos + "]:" + value);
            sb.append(attName);
            sb.append("=\"");
            sb.append(value);
            sb.append("\" ");
            //Log.getInstance().debug("parser.curPos:" + parser.getCurPos());
            //Log.getInstance().debug("atts:" + sb.toString());
         }
      }
      if (sb.length() > 0)
      {
         return(sb.toString());
      }
      return("");
   }
   /**
    * @return an XMLBuilder.XMLObject.Attribute Vector of attributes for
    * the current node in the format
    */
   public Vector<XMLAttribute> getAttributes()
   {
      Vector<XMLAttribute> attributes = new Vector<XMLAttribute>();
      XMLAttribute att;
      while( (att = this.getNextAttribute()) != null)
      {
         attributes.add(att);
      }
      
      return(attributes);
   }
   
}
