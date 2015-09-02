
package org.xmlactions.pager.actions.form;



import junit.framework.TestCase;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.form.FileViewerAction;

public class FileViewerActionTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(FileViewerActionTest.class);

	// private static Map<String, Object> execContext;
	IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {
					ActionConst.SPRING_STARTUP_CONFIG, "classpath*:/config/spring/test-spring-pager-web-startup.xml" });
			execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);
		}
	}

	public void tearDown()
	{

		execContext = null;
	}

	public void testTextFile() throws Exception
	{

		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "src/test/resource");
		FileViewerAction fileViewer = new FileViewerAction();
		fileViewer.setTheme_name("riostl");
		fileViewer.setPath("/config");
		fileViewer.setFile_name("project/web.properties");
		fileViewer.setShow_line_nos(false);
		String page = fileViewer.execute(execContext);
		assertTrue(page.indexOf("align=\"right\"") < 0);
		//log.debug("page:" + page);
	}

	public void testEscapedXmlFile() throws Exception
	{

		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "src/test/resource");
		FileViewerAction fileViewer = new FileViewerAction();
		fileViewer.setTheme_name("riostl");
		fileViewer.setPath("/config/project");
		fileViewer.setFile_name("addresses.xml");
		String page = fileViewer.execute(execContext);
		// confirm that we have line no's
		assertTrue(page.indexOf("align=\"left\"") >= 0);
		// confirm that we have escaped html
		assertTrue(page.indexOf("&lt;addresses&gt;") >= 0);
		
		//log.debug("page:" + page);
	}
	public void testNonEscapedXmlFile() throws Exception
	{

		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "src/test/resource");
		FileViewerAction fileViewer = new FileViewerAction();
		fileViewer.setTheme_name("riostl");
		fileViewer.setPath("/config/project");
		fileViewer.setFile_name("addresses.xml");
		fileViewer.setEscape_content(false);
		String page = fileViewer.execute(execContext);
		// confirm that we have escaped html
		assertTrue(page.indexOf("<addresses>") >= 0);
		
		//log.debug("page:" + page);
	}

	public void testXmlFormatFile() throws Exception
	{
		String xml =
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<addresses>"
			+ "    <address>"
			+ "         <street>main street</street>"
			+ "        <city>baltinglass</city>"
			+ "        <county>wicklow</county>"
			+ "        <country>ireland</country>"
			+ "    </address>"
			+ "</addresses>";

		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "src/test/resource");
		xml = StringEscapeUtils.escapeHtml(xml);
		execContext.put("xml_content", xml);
		FileViewerAction fileViewer = new FileViewerAction();
		fileViewer.setRef("xml_content");
		fileViewer.setEscape_content(true);
		fileViewer.setFormat_xml_content(true);
		String page = fileViewer.execute(execContext);
		// confirm that we have escaped html
		// assertTrue(page.indexOf("<addresses>") >= 0);
		
		log.debug("page:" + page);
	}

}
