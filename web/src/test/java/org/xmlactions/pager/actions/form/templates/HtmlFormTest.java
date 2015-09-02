package org.xmlactions.pager.actions.form.templates;

import org.xmlactions.pager.actions.form.templates.HtmlForm;
import org.xmlactions.pager.actions.form.templates.HtmlInput;

import junit.framework.TestCase;


public class HtmlFormTest extends TestCase
{

	public void testForm()
	{

		String expectedResult = "<form id=\"id\" name=\"name\" />";
		HtmlForm form = new HtmlForm();
		form.setName("name");
		form.setId("id");
		assertTrue(form.toString().contains("<form "));
		assertTrue(form.toString().contains("id=\"id\""));
		assertTrue(form.toString().contains("name=\"name\""));
	}

	public void testFormWithInput()
	{

		String expectedResult = "<form id=\"id\" name=\"name\" ><input id=\"id\" name=\"name\" /></form>";
		HtmlForm form = new HtmlForm();
		form.setName("name");
		form.setId("id");
		HtmlInput input = new HtmlInput();
		input.setName("name");
		input.setId("id");
		form.addChild(input);
		assertTrue(form.toString().contains("<form "));
		assertTrue(form.toString().contains("id=\"id\""));
		assertTrue(form.toString().contains("name=\"name\""));
		assertTrue(form.toString().contains("<input "));
	}
}
