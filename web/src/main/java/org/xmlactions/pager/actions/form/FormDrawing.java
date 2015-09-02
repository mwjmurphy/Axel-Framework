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


import java.util.List;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.templates.HtmlInput;



public interface FormDrawing
{

	public IExecContext getExecContext();

	public String getId();

	public List<HtmlInput> getHiddenFields();

	public String getWidth();

	public boolean isVisible();

	public String getTitle();

	public Theme getTheme(IExecContext execContext);
	
	/** @deprecated - use getTheme(IExecContext execContext) instead */
	public Theme getTheme();

	public List<Link> getLinks();
}
