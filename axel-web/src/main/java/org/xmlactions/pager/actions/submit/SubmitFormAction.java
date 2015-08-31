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

package org.xmlactions.pager.actions.submit;


import org.apache.commons.lang.Validate;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlForm;
import org.xmlactions.pager.actions.form.templates.HtmlInput;

/**
 * @param call
 *            - This is the code that will get called from the submit
 * @param title
 *            - if present then this will be the title
 * 
 * @author mike.murphy
 * 
 */
public class SubmitFormAction extends BaseAction {

    /**
     * This is the code that will get called from the submit
     */
    private String call;
    /**
     * This is the page that will get called after the call is processed.
     */
    private String page;
    
    /**
     * A unique id for the form, needed to capture the inputs.
     */
    private String id;

    @Override
    public String execute(IExecContext execContext) throws Exception {
		validate("submit_form");

		// setTheme(execContext.getThemes().getTheme(getTheme_name(execContext)));
        return buildPresentation(execContext).toString();
    }

    private Html buildPresentation(IExecContext execContext) {

        HtmlForm form = new HtmlForm();
        form.setId(getId());
        //form.setAction("submit_form_handler.ajax");
        form.setAction(getPage());
        form.setOnSubmit("return showValidationErrors(processCodeCall(captureInputsFromElement('" + getId() + "')))");
        form.setMethod("post");
        addHiddenFields(form);
        form.setContent(getContent());
        
		//if (getLinks().size() > 0) {
		//	form.addChild(DrawHTMLHelper.buildLinksAndButtons(execContext, this, getTheme(), "right"));
		//}
        
        return form;
    }


	private void addHiddenFields(Html html) {
        HtmlInput input = new HtmlInput();
        input.setName(ClientParamNames.CODE_CALL);
        input.setType("hidden");
        input.setValue(call);
        html.addChild(input);
        input = new HtmlInput();
        input.setName(ClientParamNames.PAGE);
        input.setType("hidden");
        input.setValue(page);
        html.addChild(input);
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }


    public String getId() {
    	return id;
    }
	public void setId(String id) {
		this.id = id;
	}

	private void validate(String errMsg)
	{
		Validate.notEmpty(getId(), "id attribute has not been set - " + errMsg);
	    Validate.notEmpty(getCall(), "call attribute has not been set - " + errMsg);
	    Validate.notNull(getPage(), "page attribute has not been set - " + errMsg);
	}

}
