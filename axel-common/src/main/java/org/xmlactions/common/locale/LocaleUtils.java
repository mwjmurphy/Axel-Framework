
package org.xmlactions.common.locale;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LocaleUtils
{
    
    private static final Logger logger = LoggerFactory.getLogger(LocaleUtils.class);

	/**
	 * Gets a resource value from a properties file.
	 * 
	 * @param resource
	 *            the name of the properties file excluding any localization
	 * @param key
	 *            the key used to return a value from the properties file
	 * @return
	 */
	public static String getLocalizedString(String resource, String key)
	{

		try {
			ResourceBundle res = ResourceBundle.getBundle(resource);
			return res.getString(key);
		} catch (Exception ex) {
			// ignore the exception
		    logger.warn(ex.getMessage());
			return "[" + key + "]";
		}
	}

}
