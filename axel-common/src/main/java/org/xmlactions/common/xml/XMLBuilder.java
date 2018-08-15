
package org.xmlactions.common.xml;

import java.util.Vector;

public class XMLBuilder
{
   
   // private static String header = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";
   private static String header = "<?xml version=\"1.0\"?>";
   private StringBuffer xml = null;
   
   /**
    * Create an instance of XMLBuilder
    * @param wantHeader if set true includes <code>&lt;?xml version=&quot;1.0&quot;?></code>
    */
   public XMLBuilder(boolean wantHeader)
   {
      if (wantHeader == true)
      {
         xml = new StringBuffer(header);
      }
      else
      {
         xml = new StringBuffer();
      }
   }
   
   /**
    * Adds a new element leaving the end open so you can add attributes. Looks
    * like '&lt;elementName '
    * @param elementName is the name of the element.
    */   
   public void addOpenElement(String elementName)
   {
      xml.append('<');
      xml.append(elementName);
      xml.append(' ');
   }
   
   /**
    * Adds a closed element. Looks like '&lt;elementName&gt;'
    * @param elementName is the name of the element.
    */   
   public void addClosedElement(String elementName)
   {
      xml.append('<');
      xml.append(elementName);
      xml.append('>');
   }
   
   /**
    * Adds a new element with this content and closes the element. Looks like
    * '&lt;elementName&gt;content&lt;/elementName&gt;
    * @param elementName is the element name
    * @param content is the element content.
    */
   public void addElementAndContent(String elementName, String content)
   {
      addClosedElement(elementName);
      addContent(content);
      closeElement(elementName);
      /*
      xml.append('<');
      xml.append(elementName);
      xml.append('>');
      xml.append(content == null ? "" : content);
      xml.append("</");
      xml.append(elementName);
      xml.append(">");
       */
   }
   
   
   /**
    * Adds an attribute to an open element.  Looks like 'name="value"'
    * @param name is the attribute name
    * @param value is the value for the attribute.
    */
   public void addAttribute(String name, String value)
   {
      xml.append(name);
      xml.append('=');
      xml.append('"');
      xml.append(value);
      xml.append('"');
      xml.append(' ');
   }
   
   
   /**
    * Closes and element by appending '/&gt;'
    */
   public void endElement()
   {
      xml.append('/');
      xml.append('>');
   }
   
   /**
    * Closes an open element by appending '&gt;'
    */
   public void closeOpenElement()
   {
      xml.append('>');
   }
   /**
    * Closes and element by appending '/&gt;'
    */
   public void closeElement()
   {
      xml.append('/');
      xml.append('>');
   }
   
   /**
    * Closes and element by appending '&lt;/elementName&gt;'
    * @param elementName is the close element name.
    */
   public void closeElement(String elementName)
   {
      xml.append('<');
      xml.append('/');
      xml.append(elementName);
      xml.append('>');
   }
   
   /**
    * Appends a char to the xml.
    * @param c is the char to append.
    */
   public void append(char c)
   {
      xml.append(c);
   }
   /**
    * Appends a String to the xml.
    * @param s is the String to append.
    */
   public void append(String s)
   {
      xml.append(s);
   }
   /**
    * Appends a StringBuffer to the xml.
    * @param sb is the StringBuffer to append.
    */
   public void append(StringBuffer sb)
   {
      xml.append(sb);
   }
   
   /**
    * Adds the content to the xml.
    * @param content is the content to append.
    */
   public void addContent(String content)
   {
      xml.append(content == null ? "" : content);
   }
   
   /**
    * Adds the content to the xml surrounding it with the CDATA construct.
    * @param content is the content to append.
    */
   public void addContentWithCDATA(String content)
   {
      xml.append("<![CDATA[");
      xml.append(content);
      xml.append("]]>");
   }
   
   /**
    * Returns the xml as a byte array
    * @return the xml as a byte array.
    */
   public byte [] getBytes()
   {
      return(new String(xml).getBytes());
   }
   
   /**
    * Returns the xml as a string
    * @return the xml as a string.
    */
   public String toString()
   {
      return(xml.toString());
   }
   
   /**
    * Adds a line feed to the xml
    */
   public void addLineFeed()
   {
      xml.append("\n");
   }
   
   /** 
    * Adds a number of spaces to the xml
    * @param spaces is the number of spaces to add.
    */
   public void indent(int spaces)
   {
      for (int iLoop = 0 ; iLoop < spaces; iLoop++)
      {
         xml.append(' ');
      }
   }
   
   /**
    * Adds the cpu time to the xml
    * @param identifier is an attribute 'identifier' to add
    * @param startTime is the StartTime in milliseconds attribute
    * @param endTime is the endTime in milliseconds attribute value
    * Looks like '&lt;CPUTime identifier=&quot;identifier&quot;
    *                         format=&quot;milliseconds&quot;
    *                         startTime=&quot;startTime&quot;
    *                         endTime=&quot;endTime&quot;
    *                         totalTime=&quot;hh:mm:ss:ii&quot;/&gt;
    *
    * 
    */
   public XMLBuilder buildCPUTime(String identifier, long startTime, long endTime)
   {
      XMLBuilder xb = new XMLBuilder(false);
      xb.addOpenElement("CPUTime");
      xb.addAttribute("identifier", identifier);
      xb.addAttribute("format", "milliseconds");
      xb.addAttribute("startTime", "" + startTime);
      xb.addAttribute("endTime", "" + endTime);
      long tTime = endTime-startTime;
      long milliseconds = tTime%1000;
      long seconds = (tTime/1000)%60;
      long minutes = (tTime/1000/60)%60;
      long hours = (tTime/1000/60/60)%60;
      xb.addAttribute("totalTime", "" + hours + ":" + minutes + ":" + seconds + ":" + milliseconds);
      xb.endElement();
      return(xb);
   }
   
}
