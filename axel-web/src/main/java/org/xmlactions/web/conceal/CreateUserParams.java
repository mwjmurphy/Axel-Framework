package org.xmlactions.web.conceal;

import org.xmlactions.action.config.IExecContext;

public class CreateUserParams {

    public CreateUserParams(IExecContext execContext) {
    	String value = execContext.getString("persistence:" + IExecContext.SELECTED_THEME_NAME);
    	if (value == null) {
    		value = execContext.getString(IExecContext.DEFAULT_THEME_NAME);
    	}
    	execContext.put(IExecContext.SELECTED_THEME_NAME, value);
    	execContext.put("persistence:" + IExecContext.SELECTED_THEME_NAME, value);
    }
    
}
