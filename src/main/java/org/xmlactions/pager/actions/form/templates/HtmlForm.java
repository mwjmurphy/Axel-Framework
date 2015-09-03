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


public class HtmlForm extends HtmlEvents
{

	public HtmlForm()
	{

		super(HtmlEnum.label_form.getAttributeName());
	}

	public void setAction(String value)
	{

		put(HtmlEnum.action.getAttributeName(), value);
	}

	public void setAccept(String value)
	{
	
		put(HtmlEnum.accept.getAttributeName(), value);
	}

	public void setAccept_charset(String value)
	{
	
		put(HtmlEnum.accept_charset.getAttributeName(), value);
	}

	public void setEnctype(String value)
	{
	
		put(HtmlEnum.enctype.getAttributeName(), value);
	}

	public void setMethod(String value)
	{
	
		put(HtmlEnum.method.getAttributeName(), value);
	}

	public void setName(String value)
	{
	
		put(HtmlEnum.name.getAttributeName(), value);
	}
	
	public void setTarget(String value)
	{
	
		put(HtmlEnum.target.getAttributeName(), value);
	}

}
