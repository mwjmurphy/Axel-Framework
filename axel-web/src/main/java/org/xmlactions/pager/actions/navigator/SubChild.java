package org.xmlactions.pager.actions.navigator;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.Text;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTr;


public class SubChild {

    private String id;
    private String content;
    private String width, height, x;
    private String align = "center";
    private String tooltip;
    private String href;
    private String onclick;
    private String is_allowed;	// if set false than dont show.
    

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Html buildHtml(IExecContext execContext, NavBar root, String id, Theme theme) {
        return buildSubChild(execContext, root, id, theme);
    }

    private HtmlDiv buildSubChild(IExecContext execContext, NavBar root, String id, Theme theme) {
    	if (Text.isFalse(execContext.replace(getIs_allowed()))) {
    		return null;
    	}
        /*
         * <div style="margin-left: 20px">
         *  <table class="textbox" width="240">
         *      <tr>
         *          <td>DEP</td> <td>2012-01-16 10:30</td> <td>IEHEL</td>
         *      </tr>
         *  </table>
         * </div>
         */
        HtmlDiv div = new HtmlDiv();
        if (getX() != null) {
            div.setStyle("margin-left: " + getX() + ";");
        }
        div.setId(id);
        HtmlTable table = div.addTable();
        table.setClazz(theme.getValue(ThemeConst.NAVSUBCHILD.toString()));
        if (getWidth() != null) {
            table.setWidth(getWidth());
        }
        HtmlTr tr = table.addTr();
        HtmlTd td = tr.addTd();
        td.setClazz(theme.getValue(ThemeConst.NAVSUBCHILD_FONT.toString()));
        if (getHeight() != null) {
            td.setHeight(getHeight());
        }
        if (getHref() != null || getOnclick() != null) {
        	td.setClazz(theme.getValue(ThemeConst.NAVSUBCHILD_FONT.toString()));
        	if (getHref() != null) {
        		td.setOnClick("submitLink('" + getHref() + "')");
        	} else {
        		td.setOnClick(getOnclick());
        	}
        	td.setContent(content);
        } else {
        	td.setContent(content);
        }
        if (getAlign() != null) {
        	td.setAlign(getAlign());
        }
        if (getTooltip() != null) {
        	td.setTitle(getTooltip());
        }
        td.setStyle("cursor: pointer;");
        return div;
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

    public void setX(String x) {
        this.x = x;
    }

    public String getX() {
        return x;
    }

	public void setAlign(String align) {
		this.align = align;
	}

	public String getAlign() {
		return align;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setUri(String uri) {
		this.href = uri;
	}

	public String getUri() {
		return href;
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

	public String getIs_allowed() {
		if (is_allowed == null) {
			return "true";	// default value
		}
		return is_allowed;
	}

	public void setIs_allowed(String is_allowed) {
		this.is_allowed = is_allowed;
	}

}
