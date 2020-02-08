
package org.xmlactions.pager.actions;


import java.io.InvalidObjectException;
import java.net.MalformedURLException;

import junit.framework.TestCase;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.Param;

public class ParamTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(ParamTest.class);

	public void testParam() throws InvalidObjectException, ConfigurationException, MalformedURLException
	{

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);

		IExecContext execContext = (IExecContext) applicationContext.getBean("pager.execContext");

		Param param = new Param();
		param.setValue("my.house");

		String value = (String) param.getResolvedValue(execContext);
		log.debug("value:" + value);

		param.setValue("sys:user.home");

		value = (String) param.getResolvedValue(execContext);
		log.debug("value:" + value);

		param.setValue("code");

		value = (String) param.getResolvedValue(execContext);
		log.debug("value:" + value);

		param.setValue("application.title");

		value = (String) param.getResolvedValue(execContext);
		log.debug("value:" + value);
	}

	public void testParamSplit() throws InvalidObjectException, ConfigurationException, MalformedURLException
	{
		String value = "123=321";
		String [] parts = value.split("=");
		
		assertTrue(parts.length == 2);

		value = "321";
		parts = value.split("=");
		assertTrue(parts.length == 1);

	}
}
