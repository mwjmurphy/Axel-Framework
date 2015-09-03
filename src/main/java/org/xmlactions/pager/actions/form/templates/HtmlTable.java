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


public class HtmlTable extends HtmlEvents
{

	public HtmlTable()
	{
		super(HtmlEnum.label_table.getAttributeName());
	}
	public HtmlTable(Theme theme, String themeKey) {
		super(HtmlEnum.label_table.getAttributeName());
		setClazz(theme.getValue(themeKey));
	}
	public void setAlign(String value) {
		put(HtmlEnum.align.getAttributeName(), value);
	}
	
	public void setBgColor(String value) {
		put(HtmlEnum.bgcolor.getAttributeName(), value);
	}
	
	
	public void setBorder(String value) {
		put(HtmlEnum.border.getAttributeName(), value);
	}

	public void setCellpadding(String value) {
		put(HtmlEnum.cellpadding.getAttributeName(), value);
	}

	public void setCellspacing(String value) {
		put(HtmlEnum.cellspacing.getAttributeName(), value);
	}

	public void setFrame(String value) {
		put(HtmlEnum.frame.getAttributeName(), value);
	}

	public void setRules(String value) {
		put(HtmlEnum.rules.getAttributeName(), value);
	}

	public void setSummary(String value) {
		put(HtmlEnum.summary.getAttributeName(), value);
	}

	public void setWidth(String value)
	{

		put(HtmlEnum.width.getAttributeName(), value);
	}

}
