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

package org.xmlactions.mapping.xml_to_bean;
/**
 * \page mapping_xml_to_beans Mapping XML to Beans
 * 
 * To map data from XML to Beans first build a mapping configuration file which defines what goes where, then build a test class
 * to validate the mapping.
 * 
 * To help with building the mapping file there is a schema definition available at http://xmlactions.org/schema/xml_to_beans.xsd
 * that defines the elements and attributes used by the mapping file.  This schema is also stored in the axel-shared-schema project.
 * When using tools such as Eclipse or Netbeans, references to the schema will show assistance information on the elements and attributes.
 * The assistance will popup when you hover over an element or attribute or when you press the space bar.
 * 
 * <b>This is a screen shot of the assistance popup using Eclipse</b>.
 * \image html eclipse_tooltip_xml_to_bean_mapping.png
 *  
 * The following mapping example is from the axel-mapping/src/test/resources/mapping/A1_bean_to_xml.xml file which is part of the
 * axel-mapping project. You might use this as a starter for your own mapping file. 
 * 
 * \include axel-mapping/src/test/resources/mapping/A1_xml_to_bean.xml
 * 
 * When <b>your</b> mapping file is ready you can create a test class to test the results. The following code snippet shows how 
 * to call the mapping functionality. Note that this uses the mapping file "A1_xml_to_bean.xml" shown above.
 * 
 * \code
 * public void testMapping() {
 *    // the xml to map to beans
 *    String xml = 
 *      "<a1 anInt = \"1\" aDouble=\"1.01\"" +
 *      " aString=\"This is a String\"" +
 *      " bigInt=\"999999999\"" +
 *      " aLong=\"1002\"" +
 *      " timestamp=\"2011-08-15 01:22\"" +
 *      " date=\"2011-08-16 01:22\"" +
 *      " sqldate=\"2011-08-17 02:33\">" + 
 *      "  Content of A1" + 
 *      "  <a2>content of a2</a2>" + 
 *      "</a1>";
 *     
 *     // create instance of mapping class.
 *     PopulateClassFromXml pop = new PopulateClassFromXml();
 *     
 *     // perform the mapping storing the result in Object clas
 *     Object clas = pop.mapXmlToBean("/mapping/A1_xml_to_bean.xml", xml);
 *     
 *     // copy the resultant clas to it's derived class a1. 
 *     A1 a1 = (A1) clas;
 *     
 *     // Lets see what we got
 *     log.debug("\n" + a1.toString(""));
 * }
 * \endcode
 * 
 * The output from the above test "testMapping" is
 * 
 * \code
 * anInt:1
 * aLong:1002
 * aDouble:1.01
 * aString:This is a String
 * bigInt:999999999
 * timestamp:2011-08-15 01:22:00.0
 * date:Tue Aug 16 01:22:00 GMT 2011
 * sqldate:2011-08-17
 * content:Content of A1
 * a2:null 
 * \endcode
 * 
 * The test code input xml
 * \code
 * <a1
 *     anInt = "1"
 *     aDouble = "1.01"
 *     aString = "This is a String"
 *     bigInt = "999999999"
 *     aLong = "1002"
 *     timestamp = "2011-08-15 01:22"
 *     date = "2011-08-16 01:22"
 *     sqldate = "2011-08-17 02:33">
 *     Content of A1
 *     <a2>content of a2</a2>
 * </a1>
 * \endcode
 * 
 * This is a copy of the xml_to_bean.xsd from http://xmlactions.org/schema/xml_to_bean.xsd 
 * \include axel-shared-schema/src/main/resource/schema/xml_to_bean.xsd
 * 
 * 
 */
