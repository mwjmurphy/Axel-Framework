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


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ReplacementHandlerAction;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class PutAction extends BaseAction implements ReplacementHandlerAction
{

	private static Logger log = LoggerFactory.getLogger(PutAction.class);

	private String key;

	public String execute(IExecContext execContext) throws Exception
	{
		Action action = new Action();
		String page = action.processPage(execContext, getContent());
		execContext.put(getKey(), page);
		// execContext.put(getKey(), StrSubstitutor.replace(getContent(), execContext));
		// return 
		return "";
		// return null;
	}

	public String toString()
	{

		return "put [" + getKey() + "] = [" + getContent() + "]";
	}

	public void setKey(String key)
	{

		this.key = key;
	}

	public String getKey()
	{

		return key;
	}

	public Object getReplacementData(IExecContext execContext, Object innerContent) {
		if (StringUtils.isEmpty((String)innerContent)) {
			execContext.put(getKey(), execContext.replace(getContent()));
		} else {
			execContext.put(getKey(), innerContent);
		}
		return null;
	}
}
