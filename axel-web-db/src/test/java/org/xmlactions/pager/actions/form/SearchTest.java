
package org.xmlactions.pager.actions.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.env.EnvironmentAccess;

public class SearchTest
{

	private static Logger log = LoggerFactory.getLogger(SearchTest.class);

	private static IExecContext execContext;

	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
		setUp();
	}

	public void setUp()
	{

		if (execContext == null) {
			//ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {
			//		ActionConst.SPRING_STARTUP_CONFIG, "classpath*:config/spring/test-spring-pager-web-startup.xml" });
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {
					"classpath:config/spring/test-spring-pager-web-startup.xml" });
			execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);
		}
	}

	@Test
	public void testSql() throws Exception {

		for (String beanName : execContext.getApplicationContext().getBeanDefinitionNames()) {
			log.debug("beanName:" + beanName);
		}
		Map<String,Object> map = (Map<String,Object>)execContext.getActionMaps().get("default_action_map");
		for (String key : map.keySet()) {
			log.debug("actions.key:" + key);
		}
		String page = "<body>\n"
			+ " <pager:search storage_config_ref=\"storageConfig\" table_name=\"two\" theme_name=\"blue\">\n"
			+ "  <pager:field_list>\n" //
			+ "    <pager:field name=\"two.id\"/>\n" //
			+ "    <pager:field name=\"two.name\"/>\n"//
			+ "    <pager:field name=\"two.address\">\n"//
			+ "       <pager:populator_sql ref=\"lookup_list_key1\"/>\n"//
			+ "    </pager:field>\n"
			+ "  </pager:field_list>\n"
			+ " </pager:search>\n" + "</body>";
		Action action = new Action("/pages", "static page", "pager");
		String newPage = action.processPage(execContext, page);
		log.debug("newPage:" + newPage);

		assertTrue(newPage.contains("<option"));
	}

	@Test
	public void testCode() throws Exception {

		for (String beanName : execContext.getApplicationContext().getBeanDefinitionNames()) {
			log.debug("beanName:" + beanName);
		}
		Map<String,Object> map = (Map<String,Object>)execContext.getActionMaps().get("default_action_map");
		for (String key : map.keySet()) {
			log.debug("actions.key:" + key);
		}
		String page = "<body>\n"
			+ " <pager:search storage_config_ref=\"storageConfig\" table_name=\"two\" theme_name=\"blue\">\n"
			+ "  <pager:field_list>\n" //
			+ "    <pager:field name=\"two.id\"/>\n" //
			+ "    <pager:field name=\"two.name\"/>\n"//
			+ "    <pager:field name=\"two.address\">\n"//
			+ "       <pager:populator_code>\n"//
			+ "          <pager:code call=\"org.xmlactions.pager.actions.form.SearchTestCode.getOptions\"/>\n"//
			+ "       </pager:populator_code>\n"//
			+ "    </pager:field>\n"
			+ "  </pager:field_list>\n"
			+ " </pager:search>\n"	//
			+ "</body>";
		Action action = new Action("/pages", "static page", "pager");
		String newPage = action.processPage(execContext, page);
		log.debug("newPage:" + newPage);
		assertTrue(newPage.contains("<option"));
	}

	@Test
	public void testParse()	throws Exception {

		for (String beanName: execContext.getApplicationContext().getBeanDefinitionNames()) {
			log.debug("beanName:" + beanName);
		}
		Map<String,Object> map = (Map<String,Object>)execContext.getActionMaps().get("default_action_map");
		for (String key : map.keySet()) {
			log.debug("actions.key:" + key);			
		}
		String page = "<body>\n"
			+ " <pager:search storage_config_ref=\"storageConfig\" table_name=\"tb_two\" theme_name=\"blue\">\n"
			+ "  <pager:field_list>\n" //
			+ "    <pager:field name=\"id\"/>\n" //
			+ "    <pager:field name=\"name\"/>\n"//
			+ "    <pager:field name=\"address\"/>\n"//
			+ "  </pager:field_list>\n" //
			+ " </pager:search>\n" + "</body>";
		Action action = new Action("/pages", "static page", "pager");
		String newPage = action.processPage(execContext, page);
		log.debug("newPage:" + newPage);

		String expected = "<body>\n"
			// + " <table id=\"null\" class=\"table\" ><input name=\"storage.config.ref\" value=\"storageConfig\" type=\"hidden\" /><input name=\"table.name\" value=\"tb_two\" type=\"hidden\" /><tr class=\"row\" ><td class=\"cell\" ><table class=\"table\" ><tr class=\"row\" ><th align=\"left\" class=\"header\" >Name</th><td class=\"cell\" ><input maxlength=\"32\" name=\"tb_two.name\" value=\"\" class=\"editbox\" type=\"text\" size=\"32\" /></td></tr><tr class=\"row\" ><th align=\"left\" class=\"header\" >Address</th><td class=\"cell\" ><input maxlength=\"32\" name=\"tb_two.address\" value=\"\" class=\"editbox\" type=\"text\" size=\"32\" /></td></tr></table></td></tr><tr style=\"row\" ><td style=\"cell\" align=\"right\" ><table style=\"table\" ><tr style=\"row\" /></table></td><td style=\"cell\" /></tr></table>\n"
			+ " <table id=\"null\" class=\"table\" ><input name=\"storage.config.ref\" value=\"storageConfig\" type=\"hidden\" ></input><input name=\"table.name\" value=\"tb_two\" type=\"hidden\" ></input><tr class=\"row\" ><td class=\"cell\" ><table class=\"table\" ><tr class=\"row\" ><th align=\"left\" class=\"header\" >Name</th><td class=\"cell\" ><input maxlength=\"32\" name=\"tb_two.name\" value=\"\" class=\"editbox\" type=\"text\" size=\"32\" ></input></td></tr><tr class=\"row\" ><th align=\"left\" class=\"header\" >Address</th><td class=\"cell\" ><input maxlength=\"32\" name=\"tb_two.address\" value=\"\" class=\"editbox\" type=\"text\" size=\"32\" ></input></td></tr></table></td></tr><tr class=\"row\" ><td align=\"right\" class=\"cell\" ><table class=\"table\" ><tr class=\"row\" ><td class=\"cell\" ></td></tr></table></td></tr></table>\n"
			+ "</body>";
		log.debug("newPage:" + newPage);
		assertEquals(expected, newPage);
	}
}
