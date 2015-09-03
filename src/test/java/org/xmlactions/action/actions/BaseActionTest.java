
package org.xmlactions.action.actions;

import junit.framework.TestCase;

import org.slf4j.Logger;

public class BaseActionTest extends TestCase
{

	public void testGetFirstValueFound()
	{

		Echo echo = new Echo();
		echo.setContent("Bla");
		Echo echo1 = new Echo();
		echo1.setContent("Bla1");
		Echo echo2 = new Echo();
		echo2.setContent("Bla2");
		Echo echo3 = new Echo();
		echo3.setContent("Bla3");

		Param param = new Param();
		param.setReplacementContent("replacement content");

		echo.setChild(param);
		param.setParent(echo);
		param.setChild(echo1);
		echo1.setParent(param);
		echo1.setChild(echo2);
		echo2.setParent(echo1);
		echo2.setChild(echo3);
		echo3.setParent(echo2);

		String value = echo3.getFirstValueFound("replacementContent");
		assertEquals("replacement content", value);
		value = echo3.getFirstValueFound("content");
		// will ignore echo3 and move onto echo2 using getFirstValueFound logic
		assertEquals("Bla2", value);
	}
}
