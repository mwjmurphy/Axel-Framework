package org.xmlactions.pager.actions.form.templates;


@SuppressWarnings("serial")
public class HtmlColgroup extends Html
{
	public HtmlColgroup() {
		super(HtmlEnum.label_colgroup.getAttributeName());
	}

	/** Specifies how many columns to span. */
	public void setSpan(String value) {

		put(HtmlEnum.span.getAttributeName(), value);
	}


	/** Width of the column. */
	public void setWidth(String value) {

		put(HtmlEnum.width.getAttributeName(), value);
	}
}
