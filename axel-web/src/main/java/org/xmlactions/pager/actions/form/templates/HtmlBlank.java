package org.xmlactions.pager.actions.form.templates;



@SuppressWarnings("serial")
public class HtmlBlank extends HtmlEvents
{
	public HtmlBlank() {
		super(HtmlEnum.label_blank.getAttributeName());
	}
	
	public String toString() {
		return "";	// an empty string
	}
}
