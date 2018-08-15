package org.xmlactions.pager.actions.form.templates;

import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;



public class HtmlTd extends HtmlEvents
{

	public HtmlTd()
	{
		super(HtmlEnum.label_td.getAttributeName());
	}
	
    public HtmlTd(Theme theme) {
        super(HtmlEnum.label_td.getAttributeName());
        setClazz(theme.getValue(ThemeConst.TD.toString()));
    }

    public HtmlTd(Theme theme, ThemeConst themeConst) {
        super(HtmlEnum.label_td.getAttributeName());
        setClazz(theme.getValue(themeConst.toString()));
    }

	public void setAbbr(String value) {
		put(HtmlEnum.abbr.getAttributeName(), value);
	}
	
	
	public void setAlign(String value) {
		put(HtmlEnum.align.getAttributeName(), value);
	}
	
	
	public void setAxis(String value) {
		put(HtmlEnum.axis.getAttributeName(), value);
	}
	
	
	public void setBgColor(String value) {
		put(HtmlEnum.bgcolor.getAttributeName(), value);
	}
	
	
	public void setChar(String value) {
		put(HtmlEnum.char_.getAttributeName(), value);
	}

	public void setCharoff(String value)
	{

		put(HtmlEnum.charoff.getAttributeName(), value);
	}
	
	public void setColspan(String value)
	{

		put(HtmlEnum.colspan.getAttributeName(), value);
	}

	public void setHeaders(String value)
	{

		put(HtmlEnum.headers.getAttributeName(), value);
	}

	public void setHeight(String value)
	{

		put(HtmlEnum.height.getAttributeName(), value);
	}

	public void setNowrap(String value)
	{

		put(HtmlEnum.nowrap.getAttributeName(), value);
	}

	public void setRowspan(String value)
	{

		put(HtmlEnum.rowspan.getAttributeName(), value);
	}
	
	public void setScope(String value)
	{

		put(HtmlEnum.scope.getAttributeName(), value);
	}
	
	public void setValign(String value)
	{

		put(HtmlEnum.valign.getAttributeName(), value);
	}
	
	public void setWidth(String value)
	{

		put(HtmlEnum.width.getAttributeName(), value);
	}
	


}
