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


package org.xmlactions.pager.actions.email;


import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.email.EMailClientConfig;
import org.xmlactions.email.EMailSend;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.web.PagerWebConst;

/**
 * Send an email containing the name and number of a customer requesting a phone
 * callback.
 */
public class ProcessCallbackPhone extends BaseAction
{

    private String callback_name;
    private String callback_phone;
    private String callback_email;
    private String callback_message;

    private final static Logger log = LoggerFactory.getLogger(ProcessCallbackPhone.class);

	public String execute(IExecContext execContext) throws Exception
	{

		String result = "OK";
		try {
            result = process(execContext);
			if (StringUtils.isEmpty(result)) {
				result = "OK:";
			} else {
				result = "ER:" + result;
			}
		} catch (Exception ex) {
			result = "EX:" + ex.getMessage();
			log.error(ex.getMessage(), ex);
		}
		return result;
	}

    private String process(IExecContext execContext) throws Exception
	{

		Map<String, Object> requestMap = execContext.getNamedMap(PagerWebConst.REQUEST);
		Validate.notNull(requestMap, "Missing [" + PagerWebConst.REQUEST + "] named map from the execContext");
        String email_config_ref = (String) requestMap.get(ClientParamNames.EMAIL_CONFIG_REF);
        String callback_name = (String) requestMap.get(ClientParamNames.CALLBACK_NAME);
        String callback_phone = (String) requestMap.get(ClientParamNames.CALLBACK_PHONE);
        String callback_email = (String) requestMap.get(ClientParamNames.CALLBACK_EMAIL);
        String callback_message = (String) requestMap.get(ClientParamNames.CALLBACK_MESSAGE);

        Validate.notEmpty(email_config_ref, "[" + ClientParamNames.EMAIL_CONFIG_REF + "] not found in ["
				+ PagerWebConst.REQUEST + "] named map from the execContext");

        // Doesn't have to submit a name
        //Validate.notEmpty(callback_name, "[" + ClientParamNames.CALLBACK_NAME + "] not found in ["
        //        + PagerWebConst.REQUEST + "] named map from the execContext");
        
        // Must submit a phone number.
        Validate.notEmpty(callback_phone, "[" + ClientParamNames.CALLBACK_PHONE + "] not found in ["
                + PagerWebConst.REQUEST + "] named map from the execContext");

        return sendEmail(execContext, email_config_ref, callback_name, callback_phone, callback_email, callback_message);
	}

    private String sendEmail(IExecContext execContext, String emailConfigRef, String callbackName, String callbackPhone, String callBackEmail, String callBackMessage) {
        EMailClientConfig emailClientConfig = (EMailClientConfig) execContext.get(emailConfigRef);
        Validate.notNull(emailConfigRef, "No EMailClientConfig has been configured for [" + emailConfigRef + "]");
        try {
            EMailSend.sendEMail(
                    emailClientConfig.getFromAddress(),
                    emailClientConfig.getToAddress(),
                    emailClientConfig.getHost(),
                    emailClientConfig.getUserName(),
                    emailClientConfig.getPassword(),
                    "Message from EMailClientConfig",
                    "Please Contact [" + callbackName + "] on [" + callbackPhone + "] or [" + callBackEmail + "]\n" +
                    		"Message [" + callBackMessage + "]");
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
        
        return "";
    }

    public void setCallback_name(String callback_name) {
        this.callback_name = callback_name;
    }

    public String getCallback_name() {
        return callback_name;
    }

    public void setCallback_phone(String callback_phone) {
        this.callback_phone = callback_phone;
    }

    public String getCallback_phone() {
        return callback_phone;
    }

	public void setCallback_email(String callback_email) {
		this.callback_email = callback_email;
	}

	public String getCallback_email() {
		return callback_email;
	}

	public void setCallback_message(String callback_message) {
		this.callback_message = callback_message;
	}

	public String getCallback_message() {
		return callback_message;
	}

}
