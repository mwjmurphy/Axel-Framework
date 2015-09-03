
package org.xmlactions.pager.config;


import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.PropertyContainer;

public class PropertyContainerTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(PropertyContainerTest.class);

	private String fileName = ActionConst.STARTUP_CONFIG;

	public void testCreate()
	{

		CompositeConfiguration cc = new CompositeConfiguration();
		PropertyContainer propertyContainer = new PropertyContainer(cc);
		assertNotNull(propertyContainer);
	}

	public void testAddPropertyFile() throws ConfigurationException
	{

		String configFileName = ActionConst.CONFIGS_LOCATION + "actions.properties";
		CompositeConfiguration cc = new CompositeConfiguration();
		PropertyContainer propertyContainer = new PropertyContainer(cc);
		propertyContainer.addPropertyFile(configFileName);
		assertTrue(propertyContainer.getConfiguration().isEmpty() == false);
		assertEquals("org.xmlactions.pager.actions.CodeAction", propertyContainer.get("code"));
	}

	public void testAddXmlFile() throws ConfigurationException
	{

		String configFileName = ActionConst.CONFIGS_LOCATION + "application.xml";
		CompositeConfiguration cc = new CompositeConfiguration();
		PropertyContainer propertyContainer = new PropertyContainer(cc);
		propertyContainer.addXmlFile(configFileName);
		assertTrue(propertyContainer.getConfiguration().isEmpty() == false);
		Iterator<String> iterator = propertyContainer.getConfiguration().getKeys();
		while (iterator.hasNext()) {
			String key = iterator.next();
			log.debug("key:" + key + " value:" + propertyContainer.getString(key));
		}
		assertEquals("Pager", propertyContainer.get("application.name"));
		assertEquals("${application.name} - ${application.version}", propertyContainer.get("application.title"));
		assertTrue(propertyContainer.getString("application.title").contains("Pager - "));
	}

	public void testAddConfiguration() throws ConfigurationException
	{

		String configFileName = ActionConst.CONFIGS_LOCATION + "actions.properties";
		CompositeConfiguration cc = new CompositeConfiguration();
		PropertyContainer propertyContainer = new PropertyContainer(cc);

		URL url = PropertyContainerTest.class.getResource(configFileName);
		Validate.notNull(url, "Missing configuration file [" + configFileName + "]");
		PropertiesConfiguration config = new PropertiesConfiguration(url);

		propertyContainer.addConfiguration(config);
		assertTrue(propertyContainer.getConfiguration().isEmpty() == false);
		assertEquals("org.xmlactions.pager.actions.CodeAction", propertyContainer.get("code"));
	}

	public void testAddConfigurationFactory() throws ConfigurationException
	{

		String configFileName = ActionConst.CONFIGS_LOCATION + "startup-config.xml";
		CompositeConfiguration cc = new CompositeConfiguration();
		PropertyContainer propertyContainer = new PropertyContainer(cc);

		ConfigurationFactory configFactory = new ConfigurationFactory();
		URL url = PropertyContainerTest.class.getResource(configFileName);
		assertNotNull("cant find resource:" + configFileName, url);
		configFactory.setConfigurationURL(url);
		Configuration config = configFactory.getConfiguration();
		Iterator<String> iterator = config.getKeys();
		while (iterator.hasNext()) {
			String key = iterator.next();
			log.debug("key:" + key + " value:" + config.getProperty(key));
		}

		propertyContainer.addConfiguration(config);
		assertTrue(propertyContainer.getConfiguration().isEmpty() == false);
		assertEquals("org.xmlactions.pager.actions.CodeAction", propertyContainer.get("code"));
		assertTrue(propertyContainer.getString("application.title").contains("Pager - "));
	}

	public void testAddFileList() throws ConfigurationException
	{

		String file1 = ActionConst.CONFIGS_LOCATION + "actions.properties";
		String file2 = ActionConst.CONFIGS_LOCATION + "application.xml";
		CompositeConfiguration cc = new CompositeConfiguration();
		PropertyContainer propertyContainer = new PropertyContainer(cc);

		List<String> list = new ArrayList<String>();
		list.add(file1);
		list.add(file2);

		propertyContainer.addFileList(list);
		assertTrue(propertyContainer.getConfiguration().isEmpty() == false);
		assertEquals("org.xmlactions.pager.actions.CodeAction", propertyContainer.get("code"));
		assertTrue(propertyContainer.getString("application.title").contains("Pager - "));

	}

}
