package org.xmlactions.pager.actions.form.templates;

import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;




public class HtmlTh extends HtmlEvents
{

	public HtmlTh()
	{
		super(HtmlEnum.label_th.getAttributeName());
	}
	
	public HtmlTh(Theme theme)
	{
		super(HtmlEnum.label_th.getAttributeName());
		setClazz(theme.getValue(ThemeConst.HEADER.toString()));
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
