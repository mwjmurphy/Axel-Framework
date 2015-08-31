
package org.xmlactions.action.config;


import java.io.InvalidObjectException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.ActionConsts;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.locale.LocaleUtils;
import org.xmlactions.common.theme.Theme;


public class ExecContextTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(ExecContextTest.class);
	
	private static final String [] configFiles = {
			"/config/spring/spring-config.xml"
		};
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);
	

	public void testCreate() throws InvalidObjectException, ConfigurationException, MalformedURLException
	{
		
		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		log.debug("actions.k1:" + execContext.getAction(null, "k1"));
		log.debug("actions.k4:" + execContext.getAction(null, "k4"));
		log.debug("actions.k5:" + execContext.getAction(null, "k5"));
		log.debug("actions.echo:" + execContext.getAction( null, "echo"));
		// ExecContext execContext = new NoPersistenceExecContext();
		execContext.setApplicationContext(applicationContext);
		execContext.put("newKey1", "New Key Value 1");
		execContext.put("newKey2", "replace ${newKey1}");
		assertEquals("New Key Value 1", execContext.getString("newKey1"));
		assertEquals("replace New Key Value 1", execContext.getString("newKey2"));
		log.debug("newKey2:" + execContext.get("newKey2"));
		log.debug("k1:" + execContext.get("k1"));
		log.debug("echo:" + execContext.get("echo"));
		log.debug("execContext.test2:" + execContext.get("test2"));
		// get another instance of execContext and see if the properties added from above code are still there
		execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
		log.debug("newKey2:" + execContext.get("newKey2"));
		log.debug("k1:" + execContext.get("k1"));
		log.debug("actions.k1:" + execContext.getAction(null, "k1"));
	}

	public void testMapNameAndKey() throws InvalidObjectException, ConfigurationException, MalformedURLException
	{

		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
		execContext.put("newKey1", "New Key Value 1");
		execContext.put("newKey2", "replace ${newKey1}");
		execContext.put("session:key1", "this is session:key1 value");
		execContext.put("session:key2", "session:key2 = [${newKey2}]");
		assertEquals("New Key Value 1", execContext.getString("newKey1"));
		assertEquals("replace New Key Value 1", execContext.getString("newKey2"));
		log.debug("newKey2:" + execContext.get("newKey2"));
		assertEquals("this is session:key1 value", execContext.get("session:key1"));
		assertEquals("session:key2 = [replace New Key Value 1]", execContext.get("session:key2"));
	}

	public void testPersistenceMap()
	{

		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
		
		execContext.persist("p-key", "p-value");
		assertEquals("p-value", execContext.get("persistence:p-key"));
	}

	public void testXmlConfig()
	{

		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
		
		assertTrue(execContext.getString("application.title").contains("Pager -"));
	}

	public void testLocalization()
	{

		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
		
		String value = execContext.getLocalizedString("config/lang/test", "key");
		assertEquals("value", value);

		value = execContext.getLocalizedString("config/lang/test", "key1");
		assertEquals("This is the value for key 1", value);

		value = execContext.getLocalizedString("config/lang/test", "twoline");
		assertEquals("We have a two line", value);

		value = execContext.getLocalizedString("config/lang/test", "newline");
		assertTrue(value.equals("Tenemos una\nnueva línea") || value.equals("We have a\n new line"));
		
		
		value = execContext.getString("lang:newline:config/lang/test");
		assertTrue(value.equals("Tenemos una\nnueva línea") || value.equals("We have a\n new line"));

		try {
			value = execContext.getString("lang:newline");
		} catch (IllegalArgumentException ex) {
			// expected
		}

		execContext.put(IExecContext.DEFAULT_LOCALE_FILE, "config/lang/test");
		value = execContext.getString("lang:newline::");
		assertTrue(value.equals("Tenemos una\nnueva línea") || value.equals("We have a\n new line"));

		execContext.put(IExecContext.DEFAULT_LOCALE_FILE, "config/lang/test");
		value = execContext.getString("lang:newline::ES");
		assertTrue("value = [" + value + "]", value.contains("Tenemos una"));
	}

    public void testLang() {

        String error = LocaleUtils.getLocalizedString(ActionConsts.UXML_ACTION_LANG_FILE_NAME,
                ActionConsts.LANG_KEY_NO_THEME_SET);
        error = String.format(error, ExecContext.DEFAULT_THEME_MAP);
        assertNotNull(error);

    }

    public void testThemes() {

		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
    	Theme theme = execContext.getThemes();
    	Theme blue = theme.getTheme("blue");
    	assertNotNull(blue);
    	Theme common = theme.getTheme("common");
    	assertNotNull(common);
    	Theme gray = theme.getTheme("gray");
    	assertNotNull(gray);
    	assertEquals("blue.blue", common.getValue("fgcolor"));
    	assertEquals("blue.red", common.getValue("bgcolor"));

    }

	public void testDuplicate() throws InvalidObjectException, ConfigurationException, MalformedURLException
	{
		
		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		log.debug("actions.k1:" + execContext.getAction(null, "k1"));
		log.debug("actions.k4:" + execContext.getAction(null, "k4"));
		log.debug("actions.k5:" + execContext.getAction(null, "k5"));
		log.debug("actions.echo:" + execContext.getAction( null, "echo"));
		// ExecContext execContext = new NoPersistenceExecContext();
		execContext.setApplicationContext(applicationContext);
		execContext.put("newKey1", "New Key Value 1");
		execContext.put("newKey2", "replace ${newKey1}");
		assertEquals("New Key Value 1", execContext.getString("newKey1"));
		assertEquals("replace New Key Value 1", execContext.getString("newKey2"));
		log.debug("newKey2:" + execContext.get("newKey2"));
		log.debug("k1:" + execContext.get("k1"));
		log.debug("echo:" + execContext.get("echo"));
		log.debug("execContext.test2:" + execContext.get("test2"));
		IExecContext dest = new NoPersistenceExecContext(null,null);
		execContext.copyTo(dest);
		log.debug("src.size:" + execContext.size() + " dest.size:" + execContext.size());
	}

    public void testAddMap() {
		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		Map<String, Object>map = new HashMap<String,Object>();
		map.put("k1", "value k1");
		execContext.put("demapkey:k1", "value k1");
		execContext.put("demapkey:demap", map);
		Object obj = execContext.get("demapkey");
		log.debug("demapkey = " + obj);
		log.debug("demapkey:demap = " + execContext.get("demapkey:demap"));
		log.debug("demapkey:k1 = " + execContext.get("demapkey:k1"));
    	
    }
    
    
    public void testSameKeys() {
		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.put("k1", "value of k1 1st");
		execContext.put("k1", "value of k1 2nd");
		Object k1 = execContext.get("k1");
		assertEquals("value of k1 2nd", k1);
    	
    }
}
