package org.xmlactions.pager.actions.form.templates;

import org.xmlactions.pager.actions.form.templates.HtmlInput;

import junit.framework.TestCase;


public class HtmlInputTest extends TestCase
{

	public void testFullInput()
	{

		String expectedResult = "<input accept=\"accept\" align=\"align\" alt=\"alt\" checked=\"checked\" maxlength=\"maxlength\" name=\"name\" readonly=\"readonly\" size=\"size\" src=\"src\" type=\"type\" value=\"value\" />";
		HtmlInput input = new HtmlInput();
		input.setAccept("accept");
		input.setAlign("align");
		input.setAlt("alt");
		input.setChecked("checked");
		input.setDisabled("disabled");
		input.setMaxlength("maxlength");
		input.setName("name");
		input.setReadonly("readonly");
		input.setSize("size");
		input.setSrc("src");
		input.setType("type");
		input.setValue("value");
		assertTrue(input.toString().indexOf("accept=\"accept\"") >= 0);
		assertTrue(input.toString().indexOf("align=\"align\"") >= 0);
		assertTrue(input.toString().indexOf("alt=\"alt\"") >= 0);
		assertTrue(input.toString().indexOf("checked=\"checked\"") >= 0);
		assertTrue(input.toString().indexOf("readonly=\"readonly\"") >= 0);
		assertTrue(input.toString().indexOf("src=\"src\"") >= 0);
		assertTrue(input.toString().indexOf("value=\"value\"") >= 0);
	}

	public void testNameInput()
	{

		String expectedResult = "<input name=\"name\" />";
		HtmlInput input = new HtmlInput();
		input.setName("name");
		assertEquals(expectedResult, input.toString());
	}
}
