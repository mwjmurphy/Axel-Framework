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



public class HtmlSpan extends Html
{

	public HtmlSpan()
	{
		super(HtmlEnum.label_span.getAttributeName());
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
