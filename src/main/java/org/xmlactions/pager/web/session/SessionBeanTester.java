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

package org.xmlactions.pager.web.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.web.session.SessionBean;
import org.xmlactions.web.RequestExecContext;

public class SessionBeanTester {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionBeanTester.class);
	
	public void testSessionBean() {
		IExecContext iExecContext = RequestExecContext.get();
		SessionBean sessionBean = (SessionBean)iExecContext.get("pager.sessionBean");
		logger.debug("sessionBean.value[" + sessionBean.getValue() + "]");
		sessionBean.setValue("This is the new value");
		sessionBean.getMap().put("key" + System.currentTimeMillis() + "", "" + System.currentTimeMillis());
		
		String avalue = iExecContext.getString("akey");
		iExecContext.put("akey", "" + System.currentTimeMillis());
	}

	public String getSessionBeanMapSize() {
		IExecContext iExecContext = RequestExecContext.get();
		SessionBean sessionBean = (SessionBean)iExecContext.get("pager.sessionBean");
		return "Map size:" + sessionBean.getMap().size();
	}

}
