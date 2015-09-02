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


package org.xmlactions.pager.actions.ajax.handler;


import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.CodeAction;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.web.PagerWebConst;

/**
 * Process an Ajax Code Call
 * 
 * @param call
 *            - This is the code that will get called from the submit
 * 
 */
public class CodeCallHandler extends BaseAction
{

	private final static Logger log = LoggerFactory.getLogger(CodeCallHandler.class);

	public String execute(IExecContext execContext) throws Exception
	{
		String result = "OK:";
        String call = execContext.getString(PagerWebConst.REQUEST + ":" + ClientParamNames.CODE_CALL);
        Validate.notEmpty(call, "[" + ClientParamNames.CODE_CALL + "] not found in ["
                + PagerWebConst.REQUEST + "] named map from the execContext");

        CodeAction codeAction = new CodeAction();
        codeAction.setCall(call);
        try {
            result = codeAction.execute(execContext);
        } catch (Exception ex) {
        	result = "EX:" + ex.getMessage();
        }
        
        return result;
	}

}
