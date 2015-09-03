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

package org.xmlactions.mapping.bean_to_xml;

/**
 * \page mapping_beans_to_xml Mapping Beans to XML
 * 
 * To map data from Beans to XML first build a mapping configuration file which defines what goes where, then build a test class
 * to validate the mapping.
 * 
 * To help with building the mapping file there is a schema definition available at http://xmlactions.org/schema/bean_to_xml.xsd
 * that defines the elements and attributes used by the mapping file.  This schema is also stored in the axel-shared-schema project.
 * When using tools such as Eclipse or Netbeans, references to the schema will show assistance information on the elements and attributes.
 * The assistance will popup when you hover over an element or attribute or when you press the space bar.
 * 
 * <b>This is a screen shot of the assistance popup using Eclipse</b>.
 * \image html eclipse_tooltip_bean_to_xml_mapping.png
 * 
 * The following mapping example is from the axel-mapping/src/test/resources/mapping/A1_bean_to_xml.xml file which is part of the
 * axel-mapping project. You might use this as a starter for your own mapping file. 
 * 
 * \include axel-mapping/src/test/resources/mapping/A1_bean_to_xml.xml
 * 
 * When <b>your</b> mapping file is ready you can create a test class to test the results. The following code snippet shows how 
 * to call the mapping functionality. Note that this uses the mapping file "A1_bean_to_xml.xml" shown above.
 * 
 * \code
 * 	public void testThree() throws IOException {
 *
 *		// create bean A1
 *		A1 a1 = new A1();
 *
 *		a1.setAnInt(1);		// populate some data
 *		
 *		// create an array of A2 beans
 *		List<A2> a2s = new ArrayList<A2>();
 *
 *		A2 a2 = new A2();	// create bean A2
 *		a2.setAnInt(1);		// populate some data in A2
 *		a2s.add(a2);		// add bean to list
 *		a2 = new A2();		// create bean A2
 *		a2.setAnInt(2);		// populate some data in A2
 *		a2s.add(a2);		// add bean to list
 *		a2 = new A2();		// create bean A2
 *		a2.setAnInt(3);		// populate some data in A2
 *		a2s.add(a2);		// add bean to list
 *		
 *		a1.setErs(a2s);		// add list of A2 beans to A1
 *
 *		// MAPPING CODE - call method to map the beans to xml
 *		String xml = PopulateXmlFromClass.mapBeanToXml(a1, "/mapping/A1_bean_to_xml.xml");
 *
 *		// show the results of the mapping.
 *		log.debug("xml:" + xml);
 *		
 *	}
 * \endcode
 * 
 * The output from the above test "testThree" is
 * 
 * \code
 * <?xml version="1.0" encoding="UTF-8"?>
 * <rio:a1 xmlns:rio = "rio_uri" anInt = "1">
 *     <rio:a2 anInt = "1"/>
 *     <rio:a2 anInt = "2"/>
 *     <rio:a2 anInt = "3"/>
 * </rio:a1>
 * \endcode
 * 
 * The test code includes two beans org.xmlactions.mapping.testclasses.A1 and org.xmlactions.mapping.testclasses.A2.
 * The beans are firstly pre-populated with data, then mapped to xml and the output shows result of the mapping.
 * 
 * This is a copy of the bean_to_xml.xsd from http://xmlactions.org/schema/bean_to_xml.xsd 
 * \include axel-shared-schema/src/main/resource/schema/bean_to_xml.xsd
 *
 *
 * 
 */
