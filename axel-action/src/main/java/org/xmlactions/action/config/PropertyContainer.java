
package org.xmlactions.action.config;


import java.net.URL;
import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.io.ResourceUtils;

/**
 * Utilises the Apache Configuration library.
 * 
 * @author mike.murphy
 *
 */
public class PropertyContainer
{

	private static Logger log = LoggerFactory.getLogger(PropertyContainer.class);

	private CompositeConfiguration compositeConfiguration;

	public PropertyContainer(CompositeConfiguration compositeConfiguration)
	{

		this.compositeConfiguration = compositeConfiguration;
	}

	public CompositeConfiguration getConfiguration()
	{

		return compositeConfiguration;
	}

	/**
	 * Add a properties configuration file to the CompositeConfiguration
	 * 
	 * @param fileName
	 *            the file name of the configuration file in url format
	 * @return CompositeConfiguration
	 * @throws ConfigurationException
	 */
	public CompositeConfiguration addPropertyFile(String fileName) throws ConfigurationException
	{

		URL url = ResourceUtils.getResourceURL(fileName);
		log.debug("Adding Properties from:" + url.getFile());
		PropertiesConfiguration config = new PropertiesConfiguration(url);
		compositeConfiguration.addConfiguration(config);
		return compositeConfiguration;
	}

	/**
	 * Add an xml configuration file to the CompositeConfiguration
	 * 
	 * @param fileName
	 *            the file name of the configuration file in url format
	 * @return
	 * @throws ConfigurationException
	 */
	public CompositeConfiguration addXmlFile(String fileName) throws ConfigurationException
	{

		log.debug("fileName:" + fileName);
		URL url = PropertyContainer.class.getResource(fileName);
		Validate.notNull(url, "Missing configuration file [" + fileName + "]");
		XMLConfiguration config = new XMLConfiguration(url);
		compositeConfiguration.addConfiguration(config);
		return compositeConfiguration;
	}

	/**
	 * Add a list of configuration files to the CompositeConfiguration.
	 * <p>
	 * The extension of the file names determines the type of configuration to
	 * create for the file.
	 * </p>
	 * <p>
	 * Supports .properties and .xml configuration files.
	 * </p>
	 * TODO add handlers for additional property files.
	 * 
	 * @param fileName
	 *            the file name of the configuration file in url format
	 * @return
	 * @throws ConfigurationException
	 * @throws ConfigurationException
	 */
	public CompositeConfiguration addFileList(List<String> fileNames) throws ConfigurationException
	{

		Validate.notNull(fileNames, "Null list of file names");
		for (String fileName : fileNames) {
			// don't permanently change case of fileName, may not work on UNIX.
			if (fileName.toLowerCase().endsWith(".xml")) {
				addXmlFile(fileName);
			} else if (fileName.toLowerCase().endsWith(".properties")) {
				addPropertyFile(fileName);
			} else {
				throw new ConfigurationException("Invalid file name {" + fileName + "]");
			}
		}
		return compositeConfiguration;

	}

	/**
	 * This makes it possible to add a new configuration using a spring bean
	 * factory, the factory is not really a factory
	 */
	public CompositeConfiguration addConfiguration(Configuration config)
	{

		compositeConfiguration.addConfiguration(config);
		return compositeConfiguration;
	}

	/**
	 * This makes it possible to add a new configuration using a spring bean
	 * factory, the factory is not really a factory
	 * 
	 * @throws ConfigurationException
	 */
	public CompositeConfiguration addConfigurationFile(String configFile) throws ConfigurationException
	{

		URL url = PropertyContainer.class.getResource(configFile);
		Validate.notNull(url, "Missing configuration file [" + configFile + "]");
		ConfigurationFactory cf = new ConfigurationFactory(url.getFile());
		compositeConfiguration.addConfiguration(cf.getConfiguration());
		return compositeConfiguration;
	}

	/**
	 * This makes it possible to add a key/value using a spring bean factory,
	 * the factory is not really a factory
	 * 
	 * @param key
	 * @param object
	 * @return PropertyContainer
	 */
	public PropertyContainer addObject(String key, Object object)
	{

		compositeConfiguration.setProperty(key, object);
		return this;
	}

	public Object get(String key)
	{
		return compositeConfiguration.getProperty(key);
	}

	public String getString(String key)
	{
		return compositeConfiguration.getString(key);
	}
}
