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




public class HtmlInput extends HtmlEvents
{	

	public HtmlInput()
	{
		super(HtmlEnum.label_input.getAttributeName());
	}

	public HtmlInput(Theme theme)
	{
		super(HtmlEnum.label_input.getAttributeName());
		setClazz(theme.getValue(ThemeConst.INPUT_TEXT.toString()));
	}

	public void setAccept(String value)
	{

		put(HtmlEnum.accept.getAttributeName(), value);
	}

	public void setAlign(String value)
	{

		put(HtmlEnum.align.getAttributeName(), value);
	}

	public void setAlt(String value)
	{

		put(HtmlEnum.alt.getAttributeName(), value);
	}

	public void setChecked(String value)
	{

		put(HtmlEnum.checked.getAttributeName(), value);
	}

	public void setMaxlength(String value)
	{

		put(HtmlEnum.maxlength.getAttributeName(), value);
	}

	public void setName(String value)
	{

		put(HtmlEnum.name.getAttributeName(), value);
	}

	public void setReadonly(String value)
	{

		put(HtmlEnum.readonly.getAttributeName(), value);
	}

	public void setSize(String value)
	{

		put(HtmlEnum.size.getAttributeName(), value);
	}

	public void setSrc(String value)
	{

		put(HtmlEnum.src.getAttributeName(), value);
	}

	public void setType(String value)
	{

		put(HtmlEnum.type.getAttributeName(), value);
	}

	public void setValue(String value)
	{
		put(HtmlEnum.value.getAttributeName(), value);
	}


}
