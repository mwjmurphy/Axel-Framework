package org.xmlactions.pager.actions.navigator;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlSpan;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.actions.menu.MenuImage;


public class NavBar {


    private String id;
    private String title;
    private String tooltip;
    private String width;
    private String height;
    private String handler;
    private String zindex;
    private List<Child> children;
	private MenuImage menuImage;

    private AtomicInteger lastId = new AtomicInteger();

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setLastId(int id) {
        lastId.set(id);
    }

    protected String getNextId() {
        return getId() + "_" + lastId.getAndIncrement();
    }

    public Html buildHtml(IExecContext execContext, Theme theme) {
        Html div = buildRoot(execContext, theme);

        for (Child child : getChildren()) {
            Html childHtml = child.buildHtml(this, execContext, theme);
            div.addChild(childHtml);
        }
        return div;

    }

    private HtmlDiv buildRoot(IExecContext execContext, Theme theme) {
        /*
         * <div style="margin-left: 0px">
         *  <table bgcolor="#e7e7fb" class="textbox" width="280">
         *      <tr>
         *          <td height="26">Inspection</td>
         *      </tr>
         *  </table>
         */
        // reset the id;
        setLastId(1);
        HtmlDiv div = new HtmlDiv();
        div.setId("" + getNextId());
        if (zindex != null) {
            div.setStyle("z-index:" + getZindex() + ";");
        }
        HtmlTable table = div.addTable();
        table.setClazz(theme.getValue(ThemeConst.NAVBAR.toString()));
        if (getWidth() != null) {
        	table.setWidth(getWidth());
        }
        HtmlTr tr = table.addTr();
        if (getTooltip() != null) {
        	tr.setTitle(getTooltip());
        }
        HtmlTd td;
        if (getMenuImage() != null) {
            td = tr.addTd();
            td.setContent(getMenuImage().mapToHtml(execContext, theme).toString());
        }
        td = tr.addTd();
        td.setClazz(theme.getValue(ThemeConst.NAV_FONT.toString()));
        td.setContent(title);
        if (getHeight() != null) {
        	td.setHeight(getHeight());
        }
        return div;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getHandler() {
        return handler;
    }

	public void setWidth(String width) {
		this.width = width;
	}

	public String getWidth() {
		return width;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getHeight() {
		return height;
	}

    public void setZindex(String zindex) {
        this.zindex = zindex;
    }

    public String getZindex() {
        return zindex;
    }

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getTooltip() {
		return tooltip;
	}

	public MenuImage getMenuImage() {
		return menuImage;
	}

	public void setMenuImage(MenuImage menuImage) {
		this.menuImage = menuImage;
	}

}
