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


@SuppressWarnings("serial")
public class HtmlSelect extends Html
{

	public HtmlSelect()
	{
		super(HtmlEnum.label_select.getAttributeName());
	}

	/** Assigns a name to the select list. */
	public void setName(String value)
	{

		put(HtmlEnum.name.getAttributeName(), value);
	}

	/** Specifies the number of rows to be visible at the same time. */
	public void setSize(String value)
	{

		put(HtmlEnum.size.getAttributeName(), value);

	}

	/** Specifies that multiple selections can be made. */
	public void setMultiple(String value)
	{

		put(HtmlEnum.multiple.getAttributeName(), value);
	}


}
