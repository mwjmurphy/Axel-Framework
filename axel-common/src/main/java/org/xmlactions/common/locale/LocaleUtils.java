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
