package org.xmlactions.action.config;


import java.util.Iterator;

import junit.framework.TestCase;


import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.PropertyContainer;

public class TestPropertyContainer extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(TestPropertyContainer.class);
	
	public void testPropertyContainer() throws ConfigurationException {
		PropertyContainer propertyContainer = new PropertyContainer(new CompositeConfiguration());
		propertyContainer.addXmlFile("/config/pager/application.xml");
		String value = propertyContainer.getString("application.name");
		log.debug("value:" + value);
		value = propertyContainer.getString("application");
		log.debug("value:" + value);
		Iterator <String>iterator = propertyContainer.getConfiguration().getKeys();
		while (iterator.hasNext()) {
			String key = iterator.next();
			log.debug("key:" + key);
			
		}
	}
}
