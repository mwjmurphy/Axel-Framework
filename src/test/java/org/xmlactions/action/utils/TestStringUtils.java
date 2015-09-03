package org.xmlactions.action.utils;

import junit.framework.TestCase;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;

public class TestStringUtils extends TestCase {

	public void testReplace() {
		IExecContext execContext = new NoPersistenceExecContext(null, null, null);
		String value = StringUtils.replace(execContext, "replace:value:regex:replacement");
		assertEquals("value", value);

		value = StringUtils.replace(execContext, "replace:v a l u e: :+");
		assertEquals("v+a+l+u+e", value);
	}
}
