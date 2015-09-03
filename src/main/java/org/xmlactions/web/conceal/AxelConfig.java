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
