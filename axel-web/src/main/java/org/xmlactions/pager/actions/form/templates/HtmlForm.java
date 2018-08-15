package org.xmlactions.pager.actions.form.templates;


public class HtmlForm extends HtmlEvents
{

	public HtmlForm()
	{

		super(HtmlEnum.label_form.getAttributeName());
	}

	public void setAction(String value)
	{

		put(HtmlEnum.action.getAttributeName(), value);
	}

	public void setAccept(String value)
	{
	
		put(HtmlEnum.accept.getAttributeName(), value);
	}

	public void setAccept_charset(String value)
	{
	
		put(HtmlEnum.accept_charset.getAttributeName(), value);
	}

	public void setEnctype(String value)
	{
	
		put(HtmlEnum.enctype.getAttributeName(), value);
	}

	public void setMethod(String value)
	{
	
		put(HtmlEnum.method.getAttributeName(), value);
	}

	public void setName(String value)
	{
	
		put(HtmlEnum.name.getAttributeName(), value);
	}
	
	public void setTarget(String value)
	{
	
		put(HtmlEnum.target.getAttributeName(), value);
	}

}
