/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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
