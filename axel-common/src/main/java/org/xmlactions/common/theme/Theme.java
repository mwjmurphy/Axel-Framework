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


package org.xmlactions.common.theme;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple mechanism that creates themes for building display forms.
 * <p>
 * The themes are loaded from one or more property files and use a themeName:id
 * identifier to build the key to reference the value in the property.
 * </p>
 * 
 * @author mike
 * 
 */
public class Theme
{

	private static final Logger log = LoggerFactory.getLogger(Theme.class);
	
	public static final String DEFAULT_THEME_NAME="default_theme_name";
	
	private String name;
	private Map<String, Object> map;
	
	private Map<String, Theme> themes;


	/**
	 * Creates an instance of Theme with one or more themes
	 * @param map - map of key values
	 */
	public Theme(Map<String, Object> map)
	{
		this.map = map;
		processThemes(map);
	}
	
	/**
	 * Creates an instance of Theme with one or more themes
	 * @param name - the name for the theme
	 * @param map - map of key values
	 */
	public Theme(String name, Map<String, Object> map)
	{
		this.name = name;
		this.map = map;
		processThemes(map);
	}
	
	/**
	 * Creates an instance of Theme with one or more themes
	 * 
	 * @param properties - key values
	 */
	public Theme(Properties properties)
	{
		this.map = new HashMap<String, Object>();
		processThemes(properties);
	}
	
	public void appendTheme(Theme theme) {
		if (themes == null) {
			themes = theme.getThemes();
		} else {
			themes.putAll(theme.getThemes());
			log.debug("" + themes.size());
		}
	}
	private void processThemes(Map<String, Object>map ) {
		for (String key : map.keySet()) {
			int index = key.indexOf('.');
			if (index > 0) {
				Object object = map.get(key);
				addTheme(key.substring(0, index), key.substring(index+1), object);
			}
		}
	}
	
	private void processThemes(Properties properties ) {
		
		for (Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String)entry.getKey();
			int index = key.indexOf('.');
			if (index > 0) {
				addTheme(key.substring(0, index), key.substring(index+1), entry.getValue());
			}
			else {
				map.put(key, entry.getValue());
			}
		}
	}
	
	private void addTheme(String themeName, String key, Object value) {
		if (getThemes().get(themeName) == null)  {
			Map<String, Object> map = new HashMap<String,Object>();
			map.put(key, value);
			Theme theme = new Theme(themeName, map);
			getThemes().put(themeName, theme); 
		} else {
			getThemes().get(themeName).getMap().put(key, value);
		}
	}

	public Map<String, Theme> getThemes() {
		if (themes == null) {
			themes = new HashMap<String, Theme>();
		}
		return themes;
	}

    public Theme getTheme(String themeName) {
        return getThemes().get(themeName);
    }

	public String getValue(String key)
	{
		String value = (String) map.get(key);
		// Validate.notNull(value, "Missing theme value for [" + key + "]");
		if (value == null) {
			log.warn("Missing theme value for [" + getName() + "].[" + key + "]");
		}
		return value;
	}

	public String getValue(String key, String css)
	{
        String value = getValue(key);
		if (value == null) {
			log.warn("Missing theme value for [" + getName() + "].[" + key + "] with css [" + css + "]");
		}
		
		value = addCss(value, css);
		return value;
	}


	/**
	 * Adds additional content to a theme class="xxx" by prepending the new css
	 * before the xxx.
	 * 
	 * @param value
	 *            the initial value returned from getTheme
	 * @param css
	 *            the additional css to prepend to the class info.
	 * @return updated theme value
	 */
	public String addCss(String value, String css)
	{
        if (StringUtils.isEmpty(value)) {
            return css;
        } else if (StringUtils.isEmpty(css)) {
            return value;
        }
        return value + " " + css;

	}

	/**
	 * Adds additional content to a theme class="xxx" by prepending the new css before the xxx.
	 * 
	 * @param value
	 *            the initial value returned from getTheme
	 * @param css
	 *            the additional css to prepend to the class info.
	 * @return updated theme value
	 */
	public String addCssNoClass(String value, String css)
	{

		if (!StringUtils.isEmpty(css)) {
			value = css + " " + value;
		}
		return value;

	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
