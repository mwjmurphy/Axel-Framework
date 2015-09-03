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


package org.xmlactions.pager.config;

import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;

public class PagerConstants
{

	public static final String LANG_PAGER_RESOURCE = "config/lang/pager";

	public static final String LANG_KEY_EDIT_RTE = "edit_page.edit_rte";

	public static final String LANG_KEY_EDIT_HTML = "edit_page.edit_html";

	public static final String LANG_KEY_PREVIEW = "edit_page.preview";

	public static final String LANG_KEY_ADD_PAGE = "edit_page.add_page";

	public static final String LANG_KEY_EDIT_RTE_TOOLTIP = "edit_page.edit_rte.tooltip";

	public static final String LANG_KEY_EDIT_HTML_TOOLTIP = "edit_page.edit_html.tooltip";

	public static final String LANG_KEY_PREVIEW_TOOLTIP = "edit_page.preview.tooltip";

	public static final String LANG_KEY_ADD_PAGE_TOOLTIP = "edit_page.add_page.tooltip";

	public static final String LANG_KEY_NEXT = "next";

	public static final String LANG_KEY_PREV = "prev";

	public static final String LANG_KEY_FIRST = "first";

	public static final String LANG_KEY_LAST = "last";

    public static final String LANG_KEY_GO = "go";

    public static final String LANG_KEY_SUBMIT = "submit";

    public static final String LANG_KEY_INDEX = "index";

    public static final String LANG_KEY_UPDATE = "update";

    public static final String LANG_KEY_UPLOAD = "upload";

	public static final String LANG_CALLBACK_PHONE_NAME = "callback_phone.name";

    public static final String LANG_CALLBACK_PHONE_NUMBER = "callback_phone.phone";

    public static final String LANG_CALLBACK_EMAIL_ADDRESS = "callback.email";
    
    public static final String LANG_CALLBACK_MESSAGE = "callback.message";

	public static final String JAVASCRIPT_DO_EDIT_TAB_SELECT = "javascript:do_edit_tab_select('%s');";

	public static final String JAVASCRIPT_DO_RTE_TAB_SELECT = "javascript:do_rte_tab_select('%s');";

	public static final String JAVASCRIPT_DO_PREVIEW_TAB_SELECT = "javascript:do_preview_tab_select('%s');";

	public static final String JAVASCRIPT_DO_ADD_PAGE = "javascript:do_add_page('%s');";
	public static String getLocalizedString(IExecContext execContext, String key)
	{

		return execContext.getLocalizedString(PagerConstants.LANG_PAGER_RESOURCE, key);

	}

	/**
	 * An Ajax call will return this value if the call was successful and the showValidationErrors script should
	 * proceed.
	 */
	public static String JAVASCRIPT_OK_STARTSWITH = "OK:";

	/**
	 * An Ajax call will return this value followed by a list of fields that failed validation. The fields should be
	 * seperated by an amp. Each field should show the field name and the illegal value.<br/>
	 * The illegal fields will then be highlighted on the page. i.e. "tb_user.username=bad&tb_user.password="
	 */
	public static String JAVASCRIPT_ERROR_STARTSWITH = "ER:";

	/**
	 * An Ajax call will return this value followed by a brief exception message when an exception occurs.
	 */
	public static String JAVASCRIPT_EXCEPTION_STARTSWITH = "EX:";

	/**
	 * Used by the pager:list action, each row returned from an xml query is stored into the execContext as a map by
	 * this name.
	 * <p>
	 * The pager:list action uses this to make the row data available to other action scripts such as add_record_link.
	 * </p>
	 */
	public static String ROW_MAP_NAME = ActionConst.ROW_MAP_NAME;
	
	/**
	 * Used by presentation actions such as JSONToPresentationAction and XMLToPresentionAction to store the total row count into the execContext by this name
	 * <p>
	 * The actions uses this to make the row total codata available to other action scripts such as add_record_link.
	 * </p>
	 */	
	public final static String ROW_TOTAL_COUNT = ActionConst.ROW_TOTAL_COUNT;
	
}
