package org.xmlactions.test;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.web.RequestExecContext;
import org.xmlactions.web.conceal.CreateUserParams;


public class SelectTheme {

	public String selectTheme() {
		IExecContext execContext = RequestExecContext.get();
		String theme_name = execContext.getString("request:theme_name");
    	execContext.put(IExecContext.SELECTED_THEME_NAME, theme_name);
    	execContext.put("persistence:" + IExecContext.SELECTED_THEME_NAME, theme_name);
    	return "OK:";
	}
}
