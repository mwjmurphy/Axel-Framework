package org.xmlactions.pager.actions.escaping;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;

public class TestUnEscape {

	@Test
	public void testEscape() throws Exception {
		UnEscapeAction unescape = new UnEscapeAction();
		unescape.setFormat("html");
		unescape.setRef_key("html");
		String data = "&quot; &amp;";
		IExecContext execContext = new NoPersistenceExecContext(null, null);
		execContext.put("html", data);
		String result = unescape.execute(execContext);
		assertEquals("\" &", result);
	}

}
