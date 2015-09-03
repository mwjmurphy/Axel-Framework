
package org.xmlactions.action.actions;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.actions.ActionUtils;
import org.xmlactions.action.config.ExecContext;


public class ActionUtilsTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(ActionUtilsTest.class);

	private static ExecContext execContext;
	
	private static final String [] configFiles = {
		"/config/spring/file-persistence-spring-config.xml"
	};
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);

	public void setUp()
	{
		execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
	}

    public void testIfStrSubstutitor()
	{

		execContext.put("named.map:k1", "value of k1");
		execContext.put("named.map:true", true);
		execContext.put("aaa:bbb", true);
		assertEquals(false, ActionUtils.evaluateExpression(execContext, "1 == '${aaa:bbb}'"));
		assertEquals(false, ActionUtils.evaluateExpression(execContext, "1 == '' + ${aaa:bbb}"));
		assertEquals(true, ActionUtils.evaluateExpression(execContext, "true == ${aaa:bbb}"));
		assertEquals(true, ActionUtils.evaluateExpression(execContext, "true == ${named.map:true}"));
		assertEquals("should be 'value of k1' for [" + execContext.get("named.map:k1") + "]", true, ActionUtils
				.evaluateExpression(execContext, "'value of k1' == '${named.map:k1}'"));

	}

	public void testConvertExpressionAmps()
	{

		String s = "s &gt; 0";
		s = ActionUtils.convertExpressionAmps(s);
		assertEquals("s > 0", s);

		s = "s &lt; 0";
		s = ActionUtils.convertExpressionAmps(s);
		assertEquals("s < 0", s);

		s = "s &amp; 0";
		s = ActionUtils.convertExpressionAmps(s);
		assertEquals("s & 0", s);

		s = "s &gt; 0 &amp; s &lt; 0";
		s = ActionUtils.convertExpressionAmps(s);
		assertEquals("s > 0 & s < 0", s);

		s = "s > 0 & s < 0";
		s = ActionUtils.convertExpressionAmps(s);
		assertEquals("s > 0 & s < 0", s);

	}
}
