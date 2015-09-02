package org.xmlactions.pager.actions.escaping;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;

public class TestEscape {

	@Test
	public void testEscape() throws Exception {
		EscapeAction escape = new EscapeAction();
		escape.setFormat("html");
		escape.setRef_key("html");
		String data = "\" &";
		IExecContext execContext = new NoPersistenceExecContext(null, null);
		execContext.put("html", data);
		String result = escape.execute(execContext);
		assertEquals("&quot; &amp;", result);
	}

}
