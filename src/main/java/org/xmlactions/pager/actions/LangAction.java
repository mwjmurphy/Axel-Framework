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


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

/**
 * An action to get a locale for a key.  This ends up using the ExecContext.getString("lang:" + key);
 * 
 * @author mike.murphy
 *
 */
public class LangAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(LangAction.class);

	private String key;

	public String execute(IExecContext execContext) throws Exception
	{
		
		String substituted = execContext.getString(getKey());
		if (substituted == null) {
			substituted = getKey();
		}
		substituted = "" + execContext.getString("lang:" + substituted);
		return substituted;
	}

	public String toString()
	{

		return "get [" + getKey() + "]";
	}

	public void setKey(String key)
	{

		this.key = key;
	}

	public String getKey()
	{

		return key;
	}

}
