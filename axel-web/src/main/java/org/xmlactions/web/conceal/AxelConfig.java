package org.xmlactions.web.conceal;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.web.PagerWebConst;
import org.xmlactions.web.http.HttpSessionInfo;

/**
 * Provides information on the AXEL Configuration.
 */
public class AxelConfig {

	public static String getAxelConfig(IExecContext execContext) {
		StringBuilder sb = new StringBuilder();
		sb.append("\ncd:" + new File(".").getAbsolutePath());
		sb.append("\nrealPath:" + execContext.getString(ActionConst.WEB_REAL_PATH_BEAN_REF));
		sb.append("\nserverName:" + execContext.getString(PagerWebConst.PAGE_SERVER_NAME));
		sb.append("\nappName:" + execContext.getString(PagerWebConst.PAGE_APP_NAME));
		
		HttpServletRequest request = (HttpServletRequest)execContext.get(PagerWebConst.HTTP_REQUEST);
		if (request != null) {
			sb.append("\n" + HttpSessionInfo.sysInfo(request));
		}

		sb.append(execContext.show());
		return sb.toString();
		
	}
}
