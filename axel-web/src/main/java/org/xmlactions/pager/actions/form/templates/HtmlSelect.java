package org.xmlactions.pager.actions.form.templates;


@SuppressWarnings("serial")
public class HtmlSelect extends Html
{

	public HtmlSelect()
	{
		super(HtmlEnum.label_select.getAttributeName());
	}

	/** Assigns a name to the select list. */
	public void setName(String value)
	{

		put(HtmlEnum.name.getAttributeName(), value);
	}

	/** Specifies the number of rows to be visible at the same time. */
	public void setSize(String value)
	{

		put(HtmlEnum.size.getAttributeName(), value);

	}

	/** Specifies that multiple selections can be made. */
	public void setMultiple(String value)
	{

		put(HtmlEnum.multiple.getAttributeName(), value);
	}


}
