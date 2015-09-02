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


import java.util.List;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlTable;


public class SubMenu {
	private String id; 
	private String position;
	private String left;
	private String top;
	private String bottom;
	private String right;
	private String width;
	private String height;
	private List<SubMenuItem> subMenuItems;

	//<div id="home.menu"  style="position: fixed; left: 220px; display:none" onmouseout="hide('home.menu')">
	//<table class="table sub_menu" width="100px">
	//	<tr class="menu-font" onclick="show('about_us.menu')">
	//      <td align="left"><img src="css/chrome-logo.png" height="12"/></td>
	//		<td align="left">one</td>
	//	</tr>
	public Html mapToHtml(IExecContext execContext, Theme theme, String zindex) {
		HtmlDiv div = new HtmlDiv();
		div.setId(id);
		StringBuilder sb = new StringBuilder();
		if (position != null) {sb.append("position:" + position + ";");}
		if (left != null) {	sb.append("left:" + left + ";");}
		if (top != null) {sb.append("top:" + top + ";");}
		if (bottom != null) {sb.append("bottom:" + bottom + ";");}
		if (right != null) {sb.append("right:" + right + ";");}
        if (zindex != null) {sb.append("z-index:" + zindex + ";");}
		sb.append("display:none;");
		div.setStyle(sb.toString());
		div.setOnMouseOut("hide('" + id + "')");
		
		HtmlTable table = div.addTable();
		table.setClazz(theme.getValue(ThemeConst.SUBMENU.toString()));
		if (width != null) {table.setWidth(width);}
		
		if (subMenuItems != null) {
			for (SubMenuItem subMenuItem : subMenuItems ) {
                Html tr = subMenuItem.mapToHtml(execContext, theme, zindex);
				table.addChild(tr);
			}
		}

		return div;
		
	}

	public void setSubMenuItems(List<SubMenuItem> subMenuItems) {
		this.subMenuItems = subMenuItems;
	}

	public List<SubMenuItem> getSubMenuItems() {
		return subMenuItems;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getBottom() {
		return bottom;
	}

	public void setBottom(String bottom) {
		this.bottom = bottom;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
   
}
