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


package org.xmlactions.pager.actions;


import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.web.HttpParam;

public class LogAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(LogAction.class);
	private String key;

	public String execute(IExecContext execContext)
	{
		String output;
		if (StringUtils.isNotBlank(getContent())) {
			output = execContext.replace(getContent());
		} else if (StringUtils.isBlank(key)) {
			output = dump(execContext);
		} else {
			output = dump(execContext, key);
		}
		log.debug(output);
		return output;
	}
	
	private String dump(IExecContext execContext) {
		return dumpMap(execContext, "execContext");
	}

	private String dump(IExecContext execContext, String key) {
		Object obj = execContext.get(key);
		if (obj == null) {
			obj = execContext.getNamedMap(key);
		}
		if (obj != null) {
			if (obj instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) obj;
				return dumpMap(map, key);
			} else if (obj instanceof List) {
				List<?> list = (List<?>)obj;
				return dumpList(list, key);
			} else if (obj instanceof Theme) {
				Theme theme = (Theme)obj;
				return dumpTheme(theme, key);
			}
		}
		return key + ":" + obj;
	}
	
	private String dumpMap(Map<String, Object> map, String mapName) {
		StringBuilder sb = new StringBuilder();
		if (map != null) {
			Set<Entry<String, Object>> entrySet = map.entrySet();
			Iterator<Entry<String, Object>> iterator = entrySet.iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				sb.append(mapName);
				sb.append(":");
				sb.append(entry.getKey());
				sb.append(" = ");
				sb.append(entry.getValue());
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	
	private String dumpList(List<?> list, String listName) {
		
		StringBuilder sb = new StringBuilder();
		if (list != null) {
			for(Object obj : list) {
				sb.append(listName);
				sb.append(" = ");
				sb.append(obj);
			}
		}
		return sb.toString();
	}

	private String dumpTheme(Theme theme, String keyName) {
		StringBuilder sb = new StringBuilder();
		if (theme != null) {
			sb.append(keyName);
			sb.append(":");
			if (theme.getName() != null) {
				sb.append(theme.getName());
			}
			sb.append("\n");
			sb.append(dumpMap(theme.getMap(), keyName));
	
			Set<Entry<String, Theme>> entrySet = theme.getThemes().entrySet();
			Iterator<Entry<String, Theme>> iterator = entrySet.iterator();
			while (iterator.hasNext()) {
				Entry<String, Theme> entry = iterator.next();
				Theme childTheme = entry.getValue();
				sb.append(dumpTheme(childTheme, entry.getKey()));
				sb.append("\n");
			}
		}
		return sb.toString();
		
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

}
