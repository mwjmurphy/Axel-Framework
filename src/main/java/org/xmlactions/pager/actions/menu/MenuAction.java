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

package org.xmlactions.pager.actions.menu;


import java.io.File;
import java.io.IOException;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.navigator.MapXmlToBean;

public class MenuAction extends CommonFormFields {

	private String menu_file;
	
	public String execute(IExecContext execContext) throws Exception {
		validate(execContext);

		setTheme(execContext.getThemes().getTheme(getTheme_name(execContext)));
		
		Html html = processAction(execContext);
		
		if (html == null) {
			return null;
		}
		return html.toString();
		
	}
	
	private Html processAction(IExecContext execContext) throws IOException {
		Html html = null;
        String realPath = execContext.getString(ActionConst.WEB_REAL_PATH_BEAN_REF);
        File file = new File(realPath, execContext.replace(getMenu_file()));
        String xml = ResourceUtils.loadFile(file.getAbsolutePath());
        MapXmlToBean mapXmlToBean = new MapXmlToBean("/config/mapping/menu_to_bean.xml");
        MenuBar menuBar = (MenuBar) mapXmlToBean.map(xml);
        
        html = menuBar.mapToHtml(execContext, getTheme(execContext));
		
		return html;
	}
	
	private void validate(IExecContext execContext) {
		if (StringUtils.isEmpty(getMenu_file())) {
			throw new IllegalArgumentException("Missing 'menu_file' attribute from 'menu'");
		}
	}
	public void setMenu_file(String menu_file) {
		this.menu_file = menu_file;
	}
	public String getMenu_file() {
		return menu_file;
	}

	
}
