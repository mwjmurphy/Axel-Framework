package org.xmlactions.pager.actions.form.templates;



public class HtmlSpan extends Html
{

	public HtmlSpan()
	{
		super(HtmlEnum.label_span.getAttributeName());
	}
	public void setAlign(String value) {
		put(HtmlEnum.align.getAttributeName(), value);
	}

	public void setValign(String value)
	{

		put(HtmlEnum.valign.getAttributeName(), value);
	}

	public void setWidth(String value)
	{

		put(HtmlEnum.width.getAttributeName(), value);
	}

	public void setHeight(String value)
	{

		put(HtmlEnum.height.getAttributeName(), value);
	}

}
