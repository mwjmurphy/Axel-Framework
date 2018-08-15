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
