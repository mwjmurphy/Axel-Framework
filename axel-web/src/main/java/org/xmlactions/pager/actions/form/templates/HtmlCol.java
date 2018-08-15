package org.xmlactions.pager.actions.form.templates;


public class HtmlCol extends Html
{

	public HtmlCol() {

		super(HtmlEnum.label_col.getAttributeName());
	}
	/** Marks an area of the page that a link jumps to. */
	public void setName(String value)
	{

		put(HtmlEnum.name.getAttributeName(), value);
	}

	/** Specifies how many columns to span. */
	public void setSpan(String value)
	{

		put(HtmlEnum.span.getAttributeName(), value);
	}

	/** Width of the column. */
	public void setWidth(String value)
	{

		put(HtmlEnum.width.getAttributeName(), value);
	}

}
