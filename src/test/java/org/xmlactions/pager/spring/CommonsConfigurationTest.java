package org.xmlactions.pager.spring;


import java.net.URL;
import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.ActionConst;

public class CommonsConfigurationTest extends TestCase {

	private static Logger log = LoggerFactory.getLogger(CommonsConfigurationTest.class);

	private String configFileName = ActionConst.STARTUP_CONFIG;

	/**
	 * Test loading a config file using a ConfigurationFactory. The config file
	 * is an xml file with references to configuration files to load
	 * 
	 * @throws ConfigurationException
	 */
	@SuppressWarnings("unchecked")
	public void testConfigurationFactory() throws ConfigurationException {
		ConfigurationFactory configFactory = new ConfigurationFactory();
		URL url = CommonsConfigurationTest.class.getResource(configFileName);
		assertNotNull("cant find resource:" + configFileName, url);
		configFactory.setConfigurationURL(url);
		Configuration config = configFactory.getConfiguration();
		Iterator<String> iterator = config.getKeys();
		while (iterator.hasNext()) {
			String key = iterator.next();
			log.debug("key:" + key + " value:" + config.getProperty(key));
		}

	}

	/**
	 * Test loading a config file using a ConfigurationFactory then adding it to
	 * a ComposuteConfiguration. The CompositeConfiguration will perform the
	 * placeholder replacement.
	 * 
	 * @throws ConfigurationException
	 */
	@SuppressWarnings("unchecked")
	public void testCompositeConfiguration() throws ConfigurationException {
		ConfigurationFactory configFactory = new ConfigurationFactory();
		URL url = CommonsConfigurationTest.class.getResource(configFileName);
		assertNotNull("cant find resource:" + configFileName, url);
		configFactory.setConfigurationURL(url);
		Configuration config = configFactory.getConfiguration();

		CompositeConfiguration cc = new CompositeConfiguration();
		cc.addConfiguration(config);

		Iterator<String> iterator = cc.getKeys();
		while (iterator.hasNext()) {
			String key = iterator.next();
			log.debug("key:" + key + " value:" + config.getProperty(key));
		}

	}

}
