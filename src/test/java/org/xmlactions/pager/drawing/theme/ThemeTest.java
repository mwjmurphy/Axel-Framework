
package org.xmlactions.pager.drawing.theme;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;

@ContextConfiguration(locations = { ActionConst.SPRING_STARTUP_CONFIG,
		"/config/spring/test-spring-pager-web-startup.xml" })
public class ThemeTest extends AbstractJUnit38SpringContextTests
{

	private static Logger log = LoggerFactory.getLogger(ThemeTest.class);

	public void testGetTheme()
	{

		IExecContext execContext = (IExecContext) applicationContext
				.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);

		Theme theme = execContext.getThemes().getTheme("riostl");
		assertNotNull("Missing theme property for 'riostl.table'", theme.getValue("table"));

		assertNotNull("Missing theme property for 'riostl.td'", theme.getValue("td"));


	}
}
