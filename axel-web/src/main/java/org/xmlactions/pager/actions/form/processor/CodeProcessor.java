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


package org.xmlactions.pager.actions.form.processor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.CodeAction;
import org.xmlactions.pager.actions.Param;

/**
 * Converts a pre_processor or post_processor to a CodeAction and executes the call.
 * @author mike.murphy
 *
 */
public class CodeProcessor
{

	private static Logger log = LoggerFactory.getLogger(CodeProcessor.class);

	private String call; // the package and method to call
	private Map<String,String> params;

	public String callCode(IExecContext execContext) throws Exception
	{
        CodeAction codeAction = new CodeAction();
        codeAction.setCall(call);
        if (params != null) {
	        for (int index = 1; ; index++) {
	        	String value = (String)params.get("" + index);
	        	if (value != null) {
		        	Param param = new Param();
		        	param.setValue(value);
		        	codeAction.setParam(param);
	        	} else {
	        		break;
	        	}
	        }
        }
        return codeAction.execute(execContext);
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("code call [" + getCall() + "]");
		return sb.toString();
	}

	public void setCall(String call)
	{
		this.call = call;
	}

	public String getCall()
	{
		return call;
	}
	
	public void addParam(String key, String value) {
		if (params == null) {
			params = new HashMap<String,String>();
		}
		params.put(key,value);
	}

}
