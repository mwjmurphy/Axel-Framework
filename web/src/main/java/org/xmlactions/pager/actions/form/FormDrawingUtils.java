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


import org.apache.commons.lang.StringUtils;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTh;
import org.xmlactions.pager.actions.form.templates.HtmlTr;


public class FormDrawingUtils
{

	/**
	 * Starts a drawing frame (table) , adding width, any hidden fields and title.
	 * 
	 * @param form
	 * @return
	 */
    public static HtmlTable startFrame(FormDrawing form)
	{
    	return startFrame(form, 1);
	}

	/**
	 * Starts a drawing frame (table) , adding width, any hidden fields and title.
	 * 
	 * @param form
	 * @return
	 */
    public static HtmlTable startFrame(FormDrawing form, int colspan)
	{

		HtmlTable table = new HtmlTable();
        table.setId(form.getId());
		// ===
		// build table with headers
		// ===
		if (form.isVisible()) {
            table.setClazz(form.getTheme().getValue(ThemeConst.TABLE.toString()));
		} else {
            table.setClazz(form.getTheme().getValue(ThemeConst.TABLE.toString(), "hide"));
		}
		if (StringUtils.isNotEmpty(form.getWidth())) {
			table.setWidth(form.getWidth());
		}

		// ===
		// Add Any Hidden Fields
		// ===
		for (HtmlInput input : form.getHiddenFields()) {
			table.addChild(input);
		}
		// ===
		// Add Frame Title
		// ===
		if (StringUtils.isNotEmpty(form.getTitle())) {
			HtmlTr tr = table.addTr();
			HtmlTh th = tr.addTh();
			th.setColspan("" + colspan);
			tr.setClazz(form.getTheme().getValue(ThemeConst.BAR.toString()));
			th.setClazz(form.getTheme().getValue(ThemeConst.HEADER.toString()));
			th.setContent(form.getTitle());
		}
		return table;

	}
}
