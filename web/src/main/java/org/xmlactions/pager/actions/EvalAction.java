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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.scripting.Scripting;

public class EvalAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(EvalAction.class);

	private String expression;
	private String key;

	public String execute(IExecContext execContext) throws Exception
	{
		validate(execContext);
		String output;
		if (StringUtils.isNotBlank(getExpression())) {
			output = "" + Scripting.getInstance().evaluate(execContext.replace(getExpression()));
		} else {
			output = "" + Scripting.getInstance().evaluate(execContext.replace(getContent()));
		}
		log.debug("key:" + getKey() + " expression:" + getExpression() + " output:" + output);
		if (StringUtils.isNotBlank(getKey())) {
			execContext.put(getKey(), output);
			return "";
		}
		return output;
	}
	
	private void validate(IExecContext execContext) {
		if (StringUtils.isBlank(getExpression()) && StringUtils.isBlank(getContent())) {
			throw new IllegalArgumentException("No expression has been set. Set the 'expression' attribute or the element 'content' with an expression to evaluate.");
		}
	}

	public void setKey(String key)
	{

		this.key = key;
	}

	public String getKey()
	{

		return key;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
}
