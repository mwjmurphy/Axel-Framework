package org.xmlactions.pager.actions.form.templates;

import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;



public class HtmlTr extends HtmlEvents
{

	public HtmlTr()
	{
		super(HtmlEnum.label_tr.getAttributeName());
	}

	public HtmlTr(Theme theme)
	{
		super(HtmlEnum.label_tr.getAttributeName());
		setClazz(theme.getValue(ThemeConst.ROW.toString()));
	}

	public void setAlign(String value) {
		put(HtmlEnum.align.getAttributeName(), value);
	}
	
	public void setBgColor(String value) {
		put(HtmlEnum.bgcolor.getAttributeName(), value);
	}
	
	public void setChar(String value) {
		put(HtmlEnum.char_.getAttributeName(), value);
	}
	
	public void setCharoff(String value) {
		put(HtmlEnum.charoff.getAttributeName(), value);
	}
	
	public void setVAlign(String value) {
		put(HtmlEnum.valign.getAttributeName(), value);
	}

	public void setHeight(String value)
	{

		put(HtmlEnum.height.getAttributeName(), value);

	}


}
