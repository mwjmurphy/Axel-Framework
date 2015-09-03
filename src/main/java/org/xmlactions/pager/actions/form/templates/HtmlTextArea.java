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




public class HtmlTextArea extends Html
{

	public HtmlTextArea()
	{
		super(HtmlEnum.label_textarea.getAttributeName());
	}

	public HtmlTextArea(Theme theme)
	{
		super(HtmlEnum.label_textarea.getAttributeName());
		setClazz(theme.getValue(ThemeConst.INPUT_TEXTAREA.toString()));
	}

	public void setName(String value)
	{
		put(HtmlEnum.name.getAttributeName(), value);
	}

	 


	/** Specifies the height of the textarea based on the number of visible lines of text. If there's more text than this allows, users can scroll using the textarea's scrollbars. */
	public void setRows(String value)
	{
		put(HtmlEnum.rows.getAttributeName(), value);
	}

	 


	/** Specifies the width of the textarea based on the number of visible character widths.  */
	public void setCols(String value)
	{
		put(HtmlEnum.cols.getAttributeName(), value);
	}


}
