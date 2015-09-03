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

package org.xmlactions.pager.actions.escaping;

import java.io.IOException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.mapping.BeanToXmlAction;

public class EscapeAction extends CommonFormFields {

    private static Logger log = LoggerFactory.getLogger(BeanToXmlAction.class);
    
    private static final String actionName = "escape";

	/** Reference to the data we want to un-escape */
    private String ref_key;

    /** Which type of un-escaping to we want to do */
    private String format = "html";

    /** Where we store the result of the unescaping - may be null. */
    private String key;


    public String execute(IExecContext execContext) throws Exception {

        validate(execContext);

        String data = process(execContext);
        if (StringUtils.isNotEmpty(getKey())) {
        	execContext.put(getKey(), data);
        	return "";
        } else {
        	return data;
        }
    }

    public void validate(IExecContext execContext) {
        if (StringUtils.isEmpty(getRef_key())) {
            throw new IllegalArgumentException("Missing ref_key attribute in " + actionName + ". This is required and must reference a key in the execContext that contains the data.");
        }
        if (StringUtils.isEmpty(getFormat())) {
            throw new IllegalArgumentException("Missing format attribute in " + actionName + ". This is required and must be set to one of 'html', 'xml', 'java', 'javascript', 'csv'");
        }
    }


    private String process(IExecContext execContext) throws IOException {
    	String escapedData = null;
    	String data = execContext.getString(getRef_key());
    	if (StringUtils.isEmpty(data)) {
            throw new IllegalArgumentException("The ref_key [" + getRef_key() + "] does not contain any value in the execContext in " + actionName);
    	}
    	if (getFormat().equals("html")) {
    		escapedData = StringEscapeUtils.escapeHtml(data);
    	} else if (getFormat().equals("xml")) {
    		escapedData = StringEscapeUtils.escapeXml(data);
    	} else if (getFormat().equals("java")) {
    		escapedData = StringEscapeUtils.escapeJava(data);
    	} else if (getFormat().equals("javascript")) {
    		escapedData = StringEscapeUtils.escapeJavaScript(data);
    	} else if (getFormat().equals("csv")) {
    		escapedData = StringEscapeUtils.escapeCsv(data);
    	}
        return escapedData;
    }





    public String getRef_key() {
		return ref_key;
	}

	public void setRef_key(String ref_key) {
		this.ref_key = ref_key;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


}