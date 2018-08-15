package org.xmlactions.pager.actions.menu;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTr;


public class Menu {
	private String href;
	private String onclick;
	private String name; 
	private String position;
	private String left;
	private String top;
	private String bottom;
	private String right;
	private String width;
	private String tooltip;
	
	private MenuImage menuImage;
	private SubMenu subMenu;
	
	public Html mapToHtml(IExecContext execContext, Theme theme, String [] menuIds, String zindex) {
		//<div style="position: fixed; left: 220px; top: 1px" onmouseover="hideall(['home.menu','about_us.menu']);show('home.menu')">
		//  <table class="table menu" width="100px">
		//	  <tr class="menu-font" align="right" >
		//	    <td align="left"><img src="css/chrome-logo.png" height="12"/></td>
		//      <td>home</td>
		//      <td align="right"><div class="menu_down_arrow"/></td>
		//    </tr>
		//  </table>
		// ...
		HtmlDiv div = new HtmlDiv();
		StringBuilder sb = new StringBuilder();
		if (position != null) {sb.append("position:" + position + ";");}
		if (left != null) {	sb.append("left:" + left + ";");}
		if (top != null) {sb.append("top:" + top + ";");}
		if (bottom != null) {sb.append("bottom:" + bottom + ";");}
        if (right != null) {sb.append("right:" + right + ";");}
        if (zindex != null) {sb.append("z-index:" + zindex + ";");}
		div.setStyle(sb.toString());
		sb = new StringBuilder();
		sb.append("hideall([");
		boolean moreThanOnce = false;
		for (String menuName : menuIds) {
			if (moreThanOnce == true) {
				sb.append(',');
			}
			sb.append("'" + menuName + "'");
			moreThanOnce = true;
		}
		sb.append("]);");
		if(getSubMenu() != null) {
			sb.append("show('" + getSubMenu().getId() + "');");
		}
		div.setOnMouseOver(sb.toString());

		if (getHref() != null) {
			div.setOnClick("submitLink('" + getHref() + "')");
		} else if (getOnclick() != null) {
			div.setOnClick(getOnclick());
		}

		HtmlTable table = div.addTable();
		table.setClazz(theme.getValue(ThemeConst.MENU_TABLE.toString().toString()));
		if (width != null) {table.setWidth(width);}
		HtmlTr tr = table.addTr();
		tr.setClazz(theme.getValue(ThemeConst.MENU_FONT.toString().toString()));
		tr.setAlign("right");
		if (getTooltip() != null) {
			tr.setTitle(getTooltip());
		}
		// <td align="left"><img src="css/chrome-logo.png" height="12"/></td>
		HtmlTd td = tr.addTd();
		td.setAlign("left");
		if (menuImage != null) {
			td.setContent("" + menuImage.mapToHtml(execContext, theme));
		}
		// <td>home</td>
		td = tr.addTd();
		td.setClazz(theme.getValue(ThemeConst.MENU_FONT.toString().toString()));
		td.setContent(getName());
		
		// <td align="right"><div class="menu_down_arrow"/></td>
		td = tr.addTd();
		td.setAlign("right");
		if (getSubMenu() != null) {
			HtmlDiv divImg = new HtmlDiv();
			divImg.setClazz(theme.getValue(ThemeConst.MENU_DOWN_ARROW.toString()));
			td.setContent(divImg.toString());
		}
		
		//<div id="home.menu"  style="position: fixed; left: 220px; display:none" onmouseout="hide('home.menu')">
		//<table class="table sub_menu" width="100px">
		//	<tr class="menu-font" onclick="show('about_us.menu')">
		//      <td align="left"><img src="css/chrome-logo.png" height="12"/></td>
		//		<td align="left">one</td>
		//	</tr>
		if (getSubMenu() != null) {
            Html subDiv = subMenu.mapToHtml(execContext, theme, zindex);
			div.addChild(subDiv);
		}
		
		
		return div;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getUri() {
		return getHref();
	}
	public void setUri(String uri) {
		setHref(uri);
	}
	public void setMenuImage(MenuImage menuImage) {
		this.menuImage = menuImage;
	}
	public MenuImage getMenuImage() {
		return menuImage;
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
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getHref() {
		return href;
	}

}
