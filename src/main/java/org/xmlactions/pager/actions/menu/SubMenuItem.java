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

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTr;


public class SubMenuItem {
    private String name; 
    private String href;
    private String onclick;
    private String confirm;
    private String tooltip;
	private MenuImage menuImage;
	private SubMenu subMenu;
	
	//	<tr class="menu-font" onclick="show('about_us.menu')">
	//      <td align="left"><img src="css/chrome-logo.png" height="12"/></td>
	//		<td align="left">one</td>
	//	</tr>
    public Html mapToHtml(IExecContext execContext, Theme theme, String zindex) {
		HtmlTr tr = new HtmlTr();
        tr.setClazz(theme.getValue(ThemeConst.SUBMENU_FONT.toString()) + " " + theme.getValue(ThemeConst.SUBMENUITEM.toString()));
        tr.setStyle("cursor: pointer;");
        if (tooltip != null) {
            tr.setTitle(tooltip);
        }
		if (getHref() != null) {
            StringBuilder sb = new StringBuilder();
            if (confirm != null) {
                sb.append("var answer = " + execContext.replace(confirm) + "; if (answer) {submitLink('" + execContext.replace(getHref()) + "');}");
            } else {
                sb.append("submitLink('" + execContext.replace(getHref()) + "')");
            }
            tr.setOnClick(sb.toString());
		} else if (getOnclick() != null) {
            tr.setOnClick(getOnclick());
		}
		if (getSubMenu() != null) {
			tr.setOnMouseOver("show('" + getSubMenu().getId() + "')");
			tr.setOnMouseOut("hide('" + getSubMenu().getId() + "')");
		}
		HtmlTd td = tr.addTd();
		td.setAlign("left");
		if (menuImage != null) {
			td.addChild(menuImage.mapToHtml(execContext, theme));
		}
        // name
		td = tr.addTd();
		td.setAlign("left");
		td.setContent(getName());
        // arrow
		td = tr.addTd();
		td.setAlign("right");
		if (getSubMenu() != null) {
            // arrow
            HtmlDiv divImg = new HtmlDiv();
            divImg.setClazz(theme.getValue(ThemeConst.MENU_RIGHT_ARROW.toString()));
            td.setContent(divImg.toString());

            // sub menu items
            td = tr.addTd();
            Html html = getSubMenu().mapToHtml(execContext, theme, zindex);
            td.addChild(html);
        } else {
            td = tr.addTd();
            td.setContent("");
		}

		return tr;
	}
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUri() {
		return getHref();
	}
	public void setUri(String uri) {
		setHref(uri);
	}
	public MenuImage getMenuImage() {
		return menuImage;
	}
	public void setMenuImage(MenuImage menuImage) {
		this.menuImage = menuImage;
	}

	public void setSubMenu(SubMenu subMenu) {
		this.subMenu = subMenu;
	}

	public SubMenu getSubMenu() {
		return subMenu;
	}

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getConfirm() {
        return confirm;
    }

	public void setHref(String href) {
		this.href = href;
	}

	public String getHref() {
		return href;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOnclick() {
		return onclick;
	}
}
