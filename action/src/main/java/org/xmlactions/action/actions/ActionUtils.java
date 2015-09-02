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

package org.xmlactions.action.actions;


import org.apache.bsf.BSFException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.scripting.Scripting;
import org.xmlactions.common.text.ReplaceMarkers;

public class ActionUtils
{

	private static final Logger log = LoggerFactory.getLogger(ActionUtils.class);

	public static String evaluateCalculation(IExecContext execContext, String exp) throws BSFException
	{
		String script = null;
		script = convertExpressionAmps(StrSubstitutor.replace(exp, execContext));
        // log.debug("expression:" + exp + " script:" + script);
		Object result = Scripting.getInstance().evaluate(script);
		return ConvertUtils.convert(result, Integer.class).toString();
	}

	/**
	 * Evaluates a JavaScript expression.
	 * 
	 * @param context
	 * @param exp
	 * @return true of expression evaluates to true or false if expression
	 *         evaluates to false or has an expression exception
	 */
	public static boolean evaluateExpression(IExecContext execContext, String exp)
	{

		String script = null;
		try {
			//script = convertExpressionAmps(execContext.replace(exp));
			script = convertExpressionAmps(ReplaceMarkers.replace(exp, execContext));
			
			if (log.isDebugEnabled()) {
				log.debug("expression:" + exp + " script:" + script);
			}
			Object result = Scripting.getInstance().evaluate(script);
			if (result instanceof Boolean)
				return ((Boolean) result).booleanValue();
			else
				return new Boolean((String) result).booleanValue();
		} catch (BSFException ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		} catch (Exception ex) {
			log.error(ex.getMessage() + "\nin script:" + script);
		}
		return false;
	}

	public static String convertExpressionAmps(String in)
	{

		in = in.replaceAll("&lt;", "<");
        // log.debug("in:" + in);
		in = in.replaceAll("&gt;", ">");
        // log.debug("in:" + in);
		in = in.replaceAll("&amp;", "&");
        // log.debug("in:" + in);
		return in;
	}
}
