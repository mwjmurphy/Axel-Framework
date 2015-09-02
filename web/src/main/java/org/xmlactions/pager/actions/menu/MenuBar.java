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


import java.util.ArrayList;
import java.util.List;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;


public class MenuBar {
	private String position; 
	private String left;
	private String top;
	private String bottom;
	private String right;
	private String width;
    private String height;
    private String zindex;

	private List<Menu> menus;
	
	
	public Html mapToHtml(IExecContext execContext, Theme theme) {
		List<String>menuIdList = new ArrayList<String>();
		String [] menuIds = null;
		if (menus != null) {
			for (Menu menu : menus) {
				if (menu.getSubMenu() !=null) {
					if (menu.getSubMenu().getId() != null) {
						menuIdList.add(menu.getSubMenu().getId());
					}
				}
			}
			menuIds = menuIdList.toArray(new String[menuIdList.size()]);
		}
		
		// <div class="menubar" style="position: fixed; left: 200px; width:400px; top: 0px;">
		HtmlDiv div = new HtmlDiv();
        div.setClazz(theme.getValue(ThemeConst.MENUBAR.toString()));
		StringBuilder sb = new StringBuilder();
		if (position != null) {sb.append("position:" + position + ";");}
		if (left != null) {	sb.append("left:" + left + ";");}
		if (top != null) {sb.append("top:" + top + ";");}
		if (bottom != null) {sb.append("bottom:" + bottom + ";");}
		if (right != null) {sb.append("right:" + right + ";");}
        if (width != null) {sb.append("width:" + width + ";");}
        if (height != null) {sb.append("height:" + height + ";");}
        if (zindex != null) {sb.append("z-index:" + zindex + ";");}
		
		div.setStyle(sb.toString());
		
		if (menus != null) {
			for (Menu menu : menus) {
                Html menuHtml = menu.mapToHtml(execContext, theme, menuIds, zindex);
				div.addChild(menuHtml);
			}
		}
		
		return div;
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
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	public List<Menu> getMenus() {
		return menus;
	}

    public String getZindex() {
        return zindex;
    }

    public void setZindex(String zindex) {
        this.zindex = zindex;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
