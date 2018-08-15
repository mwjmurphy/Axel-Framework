package org.xmlactions.pager.actions.form.templates;

import org.xmlactions.common.theme.Theme;



public class HtmlDiv extends HtmlEvents
{

	public HtmlDiv()
	{
		super(HtmlEnum.label_div.getAttributeName());
	}

	public HtmlDiv(Theme theme)
	{

		super(HtmlEnum.label_div.getAttributeName());
		// setClazz(theme.getThemeForClass(ThemeConst.DIV.toString()));
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
