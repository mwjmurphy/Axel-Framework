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


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.number.IntegerUtils;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTextArea;
import org.xmlactions.pager.actions.form.templates.HtmlTh;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.config.PagerConstants;
import org.xmlactions.pager.drawing.html.DrawHTMLHelper;
import org.xmlactions.web.PagerWebConst;


/**
 * Builds a page edit form. (a textarea input)
 * <p>
 * Allows to create, edit and save pages
 * </p>
 * 
 * @author mike
 * 
 */
public class EditPage extends CommonFormFields implements FormDrawing, IStorageFormAction // extends BaseFormAction
{

	private static final Logger log = LoggerFactory.getLogger(EditPage.class);

	private String page_name;
	
	private boolean can_add_page=false;

	/** Can use RTE rich text editor */
	private boolean can_rte=true;

	private String pageName;

	private int cols, rows;

	private IExecContext execContext;

	public String execute(IExecContext execContext)
	{

		validateAndSetup(execContext);

		if (StringUtils.isEmpty(page_name)) {
			pageName = PagerWebConst.getPageName(execContext);
		} else {
			pageName = (String) execContext.get(page_name);
			if (StringUtils.isEmpty(pageName)) {
				pageName = page_name;
			}
		}
		if (StringUtils.isEmpty(pageName)) {
			throw new IllegalArgumentException("page_name [" + page_name + "] has not been set.");
		}

		log.debug("pageName:" + pageName);

		if (pageName.startsWith(":")) {
			pageName = pageName.substring(1);
		}

		String page;
		try {
			page = Action.loadPage((String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF), pageName);
            execContext.put(ActionConst.NO_STR_SUBST, ActionConst.NO_STR_SUBST);
		} catch (Exception ex) {
			page = ex.getMessage();
			// throw new IllegalArgumentException(ex.getMessage(), ex);
		}
		String presentationPage = buildPresentation(execContext, pageName, page, getTheme(execContext)).toString();
//        execContext.put(ActionConst.NO_STR_SUBST, null);
		return presentationPage;
	}

	private HtmlDiv buildPresentation(IExecContext execContext, String pageName, String page, Theme theme)
	{
    	HtmlDiv rootDiv = new HtmlDiv(theme);
    	rootDiv.setClazz(theme.getValue(ThemeConst.EMPTY.toString()));
    	rootDiv.setId(getId());
		HtmlTable root = FormDrawingUtils.startFrame(this);
		rootDiv.addChild(root);

		// Editing Page Name
		HtmlTr tr = root.addTr(theme);
		HtmlTh th = tr.addTh(theme);
		th.setContent("Editing:<input readonly=\"true\" name=\"" + ClientParamNames.PAGE_NAME + "\" value=\""
				+ pageName + "\"/>");

		// links
		root.addChild(buildLinks(execContext, theme));

		// edit
		tr = root.addTr(theme);
		HtmlTd td = tr.addTd(theme);
		HtmlTable htmlTable = td.addTable(theme);
		tr = htmlTable.addTr(theme);
		td = tr.addTd(theme);
		td.setWidth(getWidth());
		td.setHeight(getHeight());
		td.setValign("top");

		// Html Edit
		HtmlDiv div = td.addDiv(theme);
		div.setId(getId() + "_html_div");
		div.setStyle("display:block;");
		HtmlTextArea textArea = new HtmlTextArea();
		div.addChild(textArea);
		textArea.setClazz(theme.getValue(ThemeConst.PAGE_EDIT.toString()));
		textArea.setName(ClientParamNames.PAGE_CONTENT);
		textArea.setId(getId() + "_html");

		if (getCols() == 0) {
			int c = IntegerUtils.createInteger(getWidth(), "Unable to convert width value [" + getWidth()
					+ "] to cols");
			c = c / 5;
			textArea.setCols("" + c);
		} else {
			textArea.setCols("" + getCols());
		}
		if (getRows() == 0) {
			int r = IntegerUtils.createInteger(getHeight(), "Unable to convert height value [" + getHeight()
					+ "] to rows");
			r = r / 12;
			textArea.setRows("" + r);
		} else {
			textArea.setRows("" + getRows());
		}
		textArea.setContent(page);

		// Preview
		div = td.addDiv(theme);
		div.setId(getId() + "_preview");
		div.setStyle("display:none;");

		// Rich Text Edit
		div = td.addDiv(theme);
		div.setId(getId() + "_rte_div");
		div.setStyle("display:none;");
		textArea = new HtmlTextArea();
		div.addChild(textArea);
		textArea.setClazz(theme.getValue(ThemeConst.PAGE_EDIT.toString(), "ckeditor"));
		textArea.setStyle("display:none");
		textArea.setName(ClientParamNames.PAGE_CONTENT + "_rte");
		textArea.setId(getId() + "_rte");
		if (getCols() == 0) {
			int c = IntegerUtils
					.createInteger(getWidth(), "Unable to convert width value [" + getWidth() + "] to cols");
			c = c / 5;
			textArea.setCols("" + c);
		} else {
			textArea.setCols("" + getCols());
		}
		if (getRows() == 0) {
			int r = IntegerUtils.createInteger(getHeight(), "Unable to convert height value [" + getHeight()
					+ "] to rows");
			r = r / 12;
			textArea.setRows("" + r);
		} else {
			textArea.setRows("" + getRows());
		}
		textArea.setContent("paste content here");

		return rootDiv;
	}

	private HtmlTr buildLinks(IExecContext execContext, Theme theme)
	{

		List<Link> mandatoryLinks = buildMandatoryLinks(execContext);
		boolean foundSubmitLink = false;
		for (Link link : getLinks()) {
			if (link.isSubmit()) {
				link.setActionScript("return(showValidationErrors(doSavePage('" + getId() + "')));");
				foundSubmitLink = true;
				if (StringUtils.isEmpty(link.getUri())) {
					link.setUri("javascript:hide('" + getId() + "');");
				}
			}
		}
		if (foundSubmitLink == false) {
			Validate.isTrue(false, "No 'submit' link has been set for this EditPage action.");
		}
		getLinks().addAll(0, mandatoryLinks);
		return DrawHTMLHelper.buildLinksAndButtons(execContext, this, theme, "left");
	}

	private List<Link> buildMandatoryLinks(IExecContext execContext)
	{

		List<Link> list = new ArrayList<Link>();
		Link link = Link.newLink(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_KEY_EDIT_HTML),
				getDisplay_as(), PagerConstants.getLocalizedString(execContext,
						PagerConstants.LANG_KEY_EDIT_HTML_TOOLTIP));
		link.setUri(String.format(PagerConstants.JAVASCRIPT_DO_EDIT_TAB_SELECT, getId()));
		list.add(link);

		if (isCan_rte()) {
			link = Link.newLink(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_KEY_EDIT_RTE),
					getDisplay_as(), PagerConstants.getLocalizedString(execContext,
							PagerConstants.LANG_KEY_EDIT_RTE_TOOLTIP));
			link.setUri(String.format(PagerConstants.JAVASCRIPT_DO_RTE_TAB_SELECT, getId()));
			list.add(link);
		}

		link = Link.newLink(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_KEY_PREVIEW),
				getDisplay_as(), PagerConstants
						.getLocalizedString(execContext, PagerConstants.LANG_KEY_PREVIEW_TOOLTIP));
		link.setUri(String.format(PagerConstants.JAVASCRIPT_DO_PREVIEW_TAB_SELECT, getId()));
		list.add(link);

		if (isCan_add_page()) {
			link = Link.newLink(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_KEY_ADD_PAGE),
					getDisplay_as(), PagerConstants.getLocalizedString(execContext,
							PagerConstants.LANG_KEY_ADD_PAGE_TOOLTIP));
			link.setUri(String.format(PagerConstants.JAVASCRIPT_DO_ADD_PAGE, getId()));
			list.add(link);
		}

		return list;

	}

	public String toString()
	{

		return "echo [" + getContent() + "]";
	}

	public void setPage_name(String page_name)
	{

		this.page_name = page_name;
	}

	public String getPage_name()
	{

		return page_name;
	}


	public void setCols(int cols)
	{

		this.cols = cols;
	}

	public int getCols()
	{

		return cols;
	}

	public void setRows(int rows)
	{

		this.rows = rows;
	}

	public int getRows()
	{

		return rows;
	}

	public IExecContext getExecContext()
	{
		return execContext;
	}

	public List<HtmlInput> getHiddenFields()
	{
		List<HtmlInput> inputs = new ArrayList<HtmlInput>();
		inputs.add(buildHiddenInput(ClientParamNames.PAGE_NAME, pageName));
		return inputs;
	}

	public void validateStorage(String errMsg)
	{
		// TODO Auto-generated method stub
	}

	public void validateAndSetup(IExecContext execContext)
	{

		this.execContext = execContext;
		setTheme( execContext.getThemes().getTheme(getTheme_name(execContext)));
	}

	public void setCan_add_page(boolean can_add_page) {
		this.can_add_page = can_add_page;
	}

	public boolean isCan_add_page() {
		return can_add_page;
	}

	public void setCan_rte(boolean can_rte) {
		this.can_rte = can_rte;
	}

	public boolean isCan_rte() {
		return can_rte;
	}

}
