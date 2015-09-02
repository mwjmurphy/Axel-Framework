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

package org.xmlactions.action.stackedpages;

import java.util.ArrayList;
import java.util.List;

import org.xmlactions.action.config.IExecContext;

/**
 * Manages a stack of web pages as they are being processed by HttpPager..
 * <p>
 * This is used by the pager:insert_into action.
 * @See org.xmlactions.pager.actions.InsertIntoAction
 * @See org.xmlactions.web.conceal.HttpPager
 * </p> 
 * @author mike.murphy
 *
 */
public class StackedPage {

	/**
	 * Replacement Marker Key that matches the replacement marker on the page.
	 */
	private String key;
	
	/**
	 * The name of the page that we want to insert into. This page will contain the replacement marker key.
	 */
	private String page;
	
	/** Where the web pages are stored */
	private String path;

	/** Namespace used for pager actions. eg. &lt;pager:action...&gt; */
	private String namespace;

	/** When adding pages we may want to remove the outer html element. */
	private boolean remove_html;	// true or false, yes or no.

	/**
	 * 
	 * @param page
	 * @param key
	 * @param path
	 * @param namespace
	 * @param remove_html
	 */
	public StackedPage(String page, String key, String path, String namespace, boolean remove_html) {
		setPage(page);
		setKey(key);
		setPath(path);
		setNamespace(namespace);
		setRemove_html(remove_html);
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * Adds a StackedPage to the list of Stacked Pages stored into ExecContext.
	 * @param execContext
	 * @param stackedPage
	 */
	public static void addStackedPage(IExecContext execContext, StackedPage stackedPage) {
		@SuppressWarnings("unchecked")
		List<StackedPage> stackedPages = (List<StackedPage>) execContext.get(IExecContext.MAP_STACKED_PAGES);
		if (stackedPages == null) {
			stackedPages = new ArrayList<StackedPage>(); 
		}
		stackedPages.add(stackedPage);
		execContext.put(IExecContext.MAP_STACKED_PAGES, stackedPages);
	}

	/**
	 * Gets and Removes the first stacked page from the list of stacked pages stored in ExecContext.
	 * @param execContext - holds the list of stacked pages.
	 * @return the first stacked page or null if none found
	 */
	public static StackedPage getAndRemoveFirstStackedPage(IExecContext execContext) {
		@SuppressWarnings("unchecked")
		List<StackedPage> stackedPages = (List<StackedPage>) execContext.get(IExecContext.MAP_STACKED_PAGES);
		if (stackedPages != null && stackedPages.size() > 0) {
			StackedPage stackedPage = stackedPages.get(0);
			stackedPages.remove(stackedPage);
			return stackedPage;
		}
		return null;
	}

	/**
	 * Gets the last stacked page from the list of stacked pages stored in ExecContext.
	 * @param execContext - holds the list of stacked pages.
	 * @return the last stacked page or null if none found
	 */
	public static StackedPage getLastStackedPage(IExecContext execContext) {
		@SuppressWarnings("unchecked")
		List<StackedPage> stackedPages = (List<StackedPage>) execContext.get(IExecContext.MAP_STACKED_PAGES);
		if (stackedPages != null && stackedPages.size() > 0) {
			StackedPage stackedPage = stackedPages.get(stackedPages.size()-1);
			return stackedPage;
		}
		return null;
	}

	public boolean isRemove_html() {
		return remove_html;
	}

	public void setRemove_html(boolean remove_html) {
		this.remove_html = remove_html;
	}


}
