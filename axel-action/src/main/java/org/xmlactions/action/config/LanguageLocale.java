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

package org.xmlactions.action.config;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * This class manages the language locale.
 * <p>
 *   Using an entry on the page such as ${lang:key} you can retrieve the language value for that key. 
 * </p> 
 * <p>
 * 	The replacement pattern is <b>"lang:key:resouce:language:country:variant"</b>
 *  <br/> where:
 *  <ul>
 *  <li>"lang" - states that we want a locale language replacement.  This is a required field.</li>
 * 	<li>"key" - is the reference to the property key that we want the replacement value for. This is a required field.</li>
 * 	<li>"resource" - is the name of the resource file to use. This is an optional field and if not set the default language file will be used.</li>
 * 	<li>"language" - is the language to use. Example: "en" (English), "ja" (Japanese), "kok" (Konkani). This is an optional field and if not set the default locale language is used.</li>
 * 	<li>"country" - is the country to use. Example: "US" (United States), "FR" (France), "029" (Caribbean). This is an optional field and if not set the default locale language is used.</li>
 * 	<li>"variant" - is the language variant we want to use. Example: "polyton" (Polytonic Greek), "POSIX". This is an optional field and if not set the default locale language is used.</li>
 *  </ul>
 * </p>
 * <p>
 *  Example:
 *  <br/> ${lang:application.title} - Will use the default language properties file and get the value for <b>application.title</b>
 *  <br/> ${lang:application.title:jms_services} - Will use the language properties file <b>jms_services</b> and get the value for <b>application.title</b>
 * </p>
 * <p>
 * A default language file is setup for the key <b>default_locale_file</b>. Usually this is configured in the web.properties file loaded by the spring configuration. 
 * </p>
 * 
 * 
 * @author mike.murphy
 *
 */
public class LanguageLocale {
	
	private String key; 
	private String resource;
	private String language;
	private String country;
	private String variant;
	
	private static final String seperatorChar = ":";

	/**
	 * Get a value for key from a language (locale) file.
	 * <p>
	 * The replacementPattern format is "lang:key:resouce:language:country:variant"
	 * <br/>or "lang:key" uses the default resource file and the default locale
	 * <br/>or "lang:key:resource" uses the resource file and the default locale
	 * <br/>or "lang:key::language" uses the default resource file and the language locale
	 * </p>
	 * 
	 * @param replacementPattern - the replacement pattern that was entered within the StrSubstitution
	 * @return the value for key or null if not found.
	 * 
	 */
	public Object getLang(IExecContext execContext, String replacementPattern)
	{
		boolean hasLanguage = false;
		boolean hasCountry= false;
		boolean hasVariant= false;

		String parts[] = replacementPattern.split(seperatorChar);
		Validate.notEmpty(parts[1],"Missing key value for [" + replacementPattern + "]");
		key = parts[1];
		if (parts.length > 2 ) {
			resource = parts[2];
		}
		if ( StringUtils.isEmpty(resource)) {
			resource = execContext.getString(IExecContext.DEFAULT_LOCALE_FILE);
			Validate.notEmpty(resource,"No default locale file has been set for [" + IExecContext.DEFAULT_LOCALE_FILE + "] in [" + replacementPattern + "]");
		} else {
			// try if resource is a key to a different value
			String mappedResource = execContext.getString(resource);
			if (mappedResource != null) {
				resource = mappedResource;
			}
		}
		if (parts.length > 3 ) {
			language = parts[3];
			if (language != null) {
				hasLanguage = true;
			}
		}
		if (parts.length > 4 ) {
			country = parts[4];
			if (country != null) {
				hasCountry = true;
			}
		}
		if (parts.length > 5 ) {
			variant = parts[5];
			if (variant != null) {
				hasVariant=true;
			}
		}
		Locale locale = null;
		if ( hasLanguage && ! (hasCountry | hasVariant))
		{
			locale = new Locale(language);
			Locale.setDefault(locale);
		}
		else if ( hasLanguage && hasCountry && ! hasVariant)
		{
			locale = new Locale(language, country);
			Locale.setDefault(locale);
		}
		else if ( hasLanguage && hasCountry && hasVariant)
		{
			locale = new Locale(language, country, variant);
			Locale.setDefault(locale);
		}
		if (locale != null) {
			Locale defaultLocale = Locale.getDefault();
			try {
				Locale.setDefault(locale);
				return execContext.getLocalizedString(resource, key);
			} finally {
				Locale.setDefault(defaultLocale);
			}
		} else {
			return execContext.getLocalizedString(resource, key);
		}
	}
}
