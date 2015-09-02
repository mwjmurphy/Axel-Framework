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


package org.xmlactions.pager.actions.form;

/**
 * These are the constants we use for getting information between the browser
 * page and the server.
 * 
 * @author mike
 * 
 */
public enum PageConstant
{
	PAGE("page"), ROWS("rows"), ID("id");

	private String value;

	PageConstant(String ref)
	{

		this.value = ref;
	}

	public String toString()
	{

		return value;
	}

	/**
	 * Build a constant, prepending id + _
	 * 
	 * @param id
	 * @param value
	 * @return
	 */
	public static String buildWithID(String id, String value)
	{

		return (id + "_" + value);
	}
}
