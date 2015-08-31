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


package org.xmlactions.pager.actions;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.Html;
import org.xmlactions.common.text.Text;

public class InsertAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(InsertAction.class);

	/** Name of file we want to load */
	private String page;

	/** Where the web pages are stored */
	private String path;

	/** Namespace used for pager actions. eg. &lt;pager:action...&gt; */
	private String namespace;
	
	/** When adding pages we may want to remove the outer html element. */
	private boolean remove_html = true;	// true or false, yes or no.

	public String execute(IExecContext execContext) throws Exception
	{

		if (path == null) {
			path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
		}
		if (namespace == null) {
			namespace = (String) execContext.get(ActionConst.PAGE_NAMESPACE_BEAN_REF);
			if (namespace == null || namespace.trim().length() == 0) {
				namespace = new String(ActionConst.DEFAULT_PAGER_NAMESPACE[0]);
			}
		}
		Action action = new Action(path, page, namespace);
		String page = action.processPage(execContext);
		if (isRemove_html()) {
			page = Html.removeOuterHtml(page);
		}
		return page;
	}
	
	public String getPage()
	{

		return page;
	}

	public void setPage(String page)
	{

		this.page = page;
	}

	public String getNamespace()
	{

		return namespace;
	}

	public void setNamespace(String namespace)
	{

		this.namespace = namespace;
	}

	public String getPath()
	{

		return path;
	}

	public void setPath(String path)
	{

		this.path = path;
	}

	public boolean isRemove_html() {
		return remove_html;
	}

	public void setRemove_html(boolean remove_html) {
		this.remove_html = remove_html;
	}

}
