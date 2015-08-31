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


package org.xmlactions.pager.actions.script;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.scripting.Scripting;
import org.xmlactions.common.text.XmlCData;

/**
 * Takes 2 parameters
 * 1) content of the script action element which is the script to execute
 * 1) attribute key to store result if there is any back into execContext - Optional
 * @author mike.murphy
 *
 */
public class ScriptAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(ScriptAction.class);

	private String key;					// if we want to put the result back into the execContext

	public String execute(IExecContext execContext) throws Exception
	{
		Object result = Scripting.getInstance().evaluate(execContext.replace(XmlCData.removeCData(getContent())));
		if (StringUtils.isBlank(getKey())) {
			return result.toString();
		} else {
			execContext.put(getKey(), result);
			return "";
		}
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
