
package org.xmlactions.common.util;

import org.xmlactions.common.util.RioStringUtils;

import junit.framework.TestCase;

public class RioStringUtilsTest extends TestCase
{

	public void testConvertToString()
	{

		assertEquals("", RioStringUtils.convertToString(null));
		assertEquals("1", RioStringUtils.convertToString(1));
		assertEquals("abc", RioStringUtils.convertToString("abc"));
	}
}
