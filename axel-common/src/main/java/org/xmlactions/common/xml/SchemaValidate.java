
package org.xmlactions.common.xml;
// JAXP packages
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;
import java.net.*;

public class SchemaValidate extends DefaultHandler
{
   /** Constants used for JAXP 1.2 */
   static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
   static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
   static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

   String errorMsg = "";


   static public void main(String[] args) throws Exception
   {
      SchemaValidate s = new SchemaValidate();

      String xmlBuffer = "";

      FileInputStream fstream = new FileInputStream("file.xml");
      byte readBuffer[] = new byte[fstream.available()];
      fstream.read(readBuffer);
      fstream.close();
      xmlBuffer = new String(readBuffer);
      // System.out.println(xmlBuffer);

      try
      {
         System.out.println("validate xmlfile to xsd schema");
         System.out.println(s.validateXMLFileToSchema("file.xml", "file.xsd"));
         System.out.println("validate xmlfile to xsd schema - success");
      }
      catch( Exception e)
      {
         System.out.println(e.getMessage());
      }
      try
      {
         System.out.println("validate xmlbuffer to xsd schema");
         System.out.println(s.validateXMLBufferToSchema(xmlBuffer, "file.xsd"));
         System.out.println("validate xmlbuffer to xsd schema - success");
      }
      catch( Exception e)
      {
         System.out.println(e.getMessage());
      }
      try
      {
         System.out.println("validate xmlfile containing schema");
         System.out.println(s.validateXMLFile("file.xml", false));
         System.out.println("validate xmlfile containing - success");
      }
      catch( Exception e)
      {
         System.out.println(e.getMessage());
      }
      try
      {
         System.out.println("validate xmlbuffer containing schema");
         System.out.println(s.validateXMLBuffer(xmlBuffer, false));
         System.out.println("validate xmlbuffer containing - success");
      }
      catch( Exception e)
      {
         System.out.println(e.getMessage());
      }
      System.exit(0);
   }


   /**
    * validate xml using an xml file name that may include the schema to validate against
    * @param xmlFileName a URL filename of the xml we want to validate
    * @param useSchema true will force using a schema | false will ignore schema
    * @return warning if there was a warning
    * @throws Exception if there was an error
    */
   public String validateXMLFile(String xmlFileName, boolean useSchema)
   throws Exception
   {
      String parserClass = "org.apache.xerces.parsers.SAXParser";
      String validationFeature = "http://xml.org/sax/features/validation";
      String schemaFeature = "http://apache.org/xml/features/validation/schema";
      org.xml.sax.XMLReader r = XMLReaderFactory.createXMLReader(parserClass);
      r.setFeature(validationFeature,useSchema);
      r.setFeature(schemaFeature, useSchema);
      r.setErrorHandler(this);
      r.parse(xmlFileName);

      return(errorMsg);
   }

   /**
    * validate xml using an xml buffer that may include the schema to validate against
    * @param xmlBuffer this is xml buffer we want to validate
    * @param useSchema true will force using a schema | false will ignore schema
    * @return String warning if there was a warning
    * @throws Exception if there was an error
    */
   public String validateXMLBuffer(String xmlBuffer, boolean useSchema)
   throws Exception
   {
      String parserClass = "org.apache.xerces.parsers.SAXParser";
      String validationFeature = "http://xml.org/sax/features/validation";
      String schemaFeature = "http://apache.org/xml/features/validation/schema";

      InputSource inputSource= new InputSource(new StringReader(xmlBuffer));

      org.xml.sax.XMLReader r = XMLReaderFactory.createXMLReader(parserClass);
      r.setFeature(validationFeature,useSchema);
      r.setFeature(schemaFeature, useSchema);
      r.setErrorHandler(this);

      r.parse(inputSource);

      return(errorMsg);
   }

   /**
    * validate xml using an xml file name and an xsd file name
    * @param xmlFileName a URL filename of the xml we want to validate
    * @param xsdFileName a URL filename of the schema we want to validate against
    * @return String warning if there was a warning
    * @throws Exception if there was an error
    */
   public String validateXMLFileToSchema(String xmlFileName, String xsdFileName)
   throws Exception
   {
      // Create a JAXP SAXParserFactory and configure it
      SAXParserFactory spf = SAXParserFactory.newInstance();

      // Set namespaceAware to true to get a parser that corresponds to
      // the default SAX2 namespace feature setting.  This is necessary
      // because the default value from JAXP 1.0 was defined to be false.
      spf.setNamespaceAware(true);

      // Validation part 1: set whether validation is on
      spf.setValidating(true);

      // Create a JAXP SAXParser
      SAXParser saxParser = spf.newSAXParser();

      // Validation part 2a: set the schema language if necessary
      saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

      // Validation part 2b: Set the schema source, if any.  See the JAXP
      // 1.2 maintenance update specification for more complex usages of
      // this feature.
      saxParser.setProperty(JAXP_SCHEMA_SOURCE, new File(xsdFileName));

      // Get the encapsulated SAX XMLReader
      org.xml.sax.XMLReader xmlReader = saxParser.getXMLReader();

      // Set the ContentHandler of the XMLReader
      xmlReader.setContentHandler(new SchemaValidate());

      // Set an ErrorHandler before parsing
      //xmlReader.setErrorHandler(new errorHandler());
      xmlReader.setErrorHandler(this);

      errorMsg = "";

      // Tell the XMLReader to parse the XML document
      // xmlReader.parse(convertToFileURL(xmlFileName));
      xmlReader.parse(xmlFileName);

      return(errorMsg);
   }

   /**
    * validate xml using an xml buffer and an xsd file name
    * @param xmlBuffer this is xml buffer we want to validate
    * @param xsdFileName a URL filename of the schema we want to validate against
    * @return String warning if there was a warning
    * @throws Exception if there was an error
    */
   public String validateXMLBufferToSchema(String xmlBuffer, String xsdFileName)
   throws Exception
   {
      InputSource inputSource= new InputSource(new StringReader(xmlBuffer));

      // Create a JAXP SAXParserFactory and configure it
      SAXParserFactory spf = SAXParserFactory.newInstance();

      // Set namespaceAware to true to get a parser that corresponds to
      // the default SAX2 namespace feature setting.  This is necessary
      // because the default value from JAXP 1.0 was defined to be false.
      spf.setNamespaceAware(true);

      // Validation part 1: set whether validation is on
      spf.setValidating(true);

      // Create a JAXP SAXParser
      SAXParser saxParser = spf.newSAXParser();

      // Validation part 2a: set the schema language if necessary
      saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

      // Validation part 2b: Set the schema source, if any.  See the JAXP
      // 1.2 maintenance update specification for more complex usages of
      // this feature.
      saxParser.setProperty(JAXP_SCHEMA_SOURCE, new File(xsdFileName));

      // Get the encapsulated SAX XMLReader
      org.xml.sax.XMLReader xmlReader = saxParser.getXMLReader();

      // Set the ContentHandler of the XMLReader
      xmlReader.setContentHandler(new SchemaValidate());

      // Set an ErrorHandler before parsing
      //xmlReader.setErrorHandler(new errorHandler());
      xmlReader.setErrorHandler(this);

      errorMsg = "";

      // Tell the XMLReader to parse the XML document
      xmlReader.parse(inputSource);

      return(errorMsg);                 // This will contain warning text or nothing
   }

   public void warning(SAXParseException e)
   throws SAXException
   {
      //System.out.println("Warning: ");
      printInfo(e, "Warning");
   }

   public void error(SAXParseException e)
   throws SAXException
   {
      //System.out.println("Error: ");
      printInfo(e, "Error");
      throw new SAXException(errorMsg);
   }

   public void fatalError(SAXParseException e)
   throws SAXException
   {
      //System.out.println("Fatal error: ");
      printInfo(e, "Fatal");
      throw new SAXException(errorMsg);
   }

   private void printInfo(SAXParseException e, String fatality)
   {
      setErrorMessage("<SAXParseException level=\"" + fatality + "\">" +
                      "<PublicID>" + e.getPublicId() + "</PublicID>" +
                      "<SystemID>" + e.getSystemId() + "</SystemID>" +
                      "<LineNumber>" + e.getLineNumber() + "</LineNumber>" +
                      "<ColumnNumber>" + e.getColumnNumber() + "</ColumnNumber>" +
                      "<Message><![CDATA[" + e.getMessage() + "]]></Message>" +
                      "</SAXParseException>");

      /*
      System.out.println("3.");
      System.out.println("   Public ID: "+e.getPublicId());
      System.out.println("   System ID: "+e.getSystemId());
      System.out.println("   Line number: "+e.getLineNumber());
      System.out.println("   Column number: "+e.getColumnNumber());
      System.out.println("   Message: "+e.getMessage());
       */
   }

   private void setErrorMessage(String msg)
   {
      errorMsg += msg;
   }
}
