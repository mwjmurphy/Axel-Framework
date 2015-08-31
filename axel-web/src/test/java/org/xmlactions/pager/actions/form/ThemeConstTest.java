
package org.xmlactions.pager.actions.form;

import org.xmlactions.pager.actions.form.ThemeConst;

import junit.framework.TestCase;

public class ThemeConstTest extends TestCase
{

	public void testThemeConst()
	{

		Object obj = ThemeConst.TD;
		assertEquals("td", ThemeConst.TD.toString());
		assertEquals("input.text", ThemeConst.INPUT_TEXT.toString());
	}
}
