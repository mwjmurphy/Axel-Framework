package org.xmlactions.pager.actions.navigator;


import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.Text;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.actions.menu.MenuImage;

public class Child {

    private String id;
    private String display; // none | block
    private String title;
    private String tooltip;
    private Options options;
    private List<SubChild> subchildren;
    private String width, height, x;
    private String href;
    private String onclick;
    private String is_allowed;	// if not set true than dont show.
	private MenuImage menuImage;
    
    
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

    public void setSubchildren(List<SubChild> subchildren) {
        this.subchildren = subchildren;
    }

    public List<SubChild> getSubchildren() {
        return subchildren;
    }

    public void setUri(String uri) {
        
        this.href = uri;
    }

    public String getUri() {
        return href;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public Html buildHtml(NavBar root, IExecContext execContext, Theme theme) {
    	String value = execContext.replace(getIs_allowed());
    	if (Text.isFalse(value)) {
    		return null;
    	}
        String childId = root.getNextId();
        Html childHtml = buildChild(execContext, root, childId, theme);
        HtmlDiv div = (HtmlDiv) childHtml.getChildren().get(childHtml.getChildren().size() - 1);
        int index = 1;
        if (getSubchildren() != null) {
            for (SubChild subChild : getSubchildren()) {
                String subChildId = childId + "_" + index++;
                Html subChildHtml = subChild.buildHtml(execContext, root, subChildId, theme);
                div.addChild(subChildHtml);
            }
        }
        return childHtml;

    }

    private HtmlDiv buildChild(IExecContext execContext, NavBar root, String id, Theme theme) {
        HtmlDiv div = new HtmlDiv();
        if (getX() != null) {
            div.setStyle("margin-left: " + getX() + ";");
        }
        HtmlTable table = div.addTable();
        table.setClazz(ThemeConst.NAVCHILD.toString());
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
        if (getHref() != null || getOnclick() != null) {
        	if (getHref() != null) {
        		td.setOnClick("submitLink('" + getHref() + "')");
        	} else {
        		td.setOnClick(getOnclick());
        	}
            td.setStyle("cursor: pointer;");
        } else {
            td.setOnClick("toggleShowHide('" + id + "');");
        }
        td.setClazz(theme.getValue(ThemeConst.NAVCHILD_FONT.toString()));
        if (getHeight() != null) {
        	td.setHeight(getHeight());
        }
        td.setContent(getTitle());

        if (getOptions() != null && getOptions().getOptions().size() > 0) {
        	td = tr.addTd();
            td.setAlign("right");
            if (StringUtils.isNotEmpty(getOptions().getWidth())) {
                td.setWidth(getOptions().getWidth());
            }
            Html html = drawOptions(getOptions().getOptions(), "menu_" + id, theme, getOptions().getWidth());
            td.addChild(html);
        } else {
        	td = tr.addTd();
            if (getHref() != null || getOnclick() != null) {
            	if (getHref() != null) {
            		td.setOnClick("submitLink('" + getHref() + "')");
            	} else {
            		td.setOnClick(getOnclick());
            	}
                td.setStyle("cursor: pointer;");
            }
        }
        // add a link with arrow image pointing right
        if (getSubchildren() != null && getSubchildren().size() > 0) {
            td = tr.addTd();
            td.setAlign("right");
            td.setClazz(theme.getValue(ThemeConst.NAV_DOWN_ARROW.toString()));
            td.setStyle("cursor: pointer;");
            td.setOnClick("toggleShowHide('" + id + "');");
        }
        // add a link with arrow image pointing right
        if (getHref() != null || getOnclick() != null) {
            td = tr.addTd();
            td.setAlign("right");
            td.setClazz(theme.getValue(ThemeConst.NAV_RIGHT_ARROW.toString()));
            if (getHref() != null) {
            	td.setOnClick("submitLink('" + getHref() + "')");
            } else {
            	td.setOnClick(getOnclick());
            }
            td.setStyle("cursor: pointer;");
        }
        HtmlDiv innerDiv = new HtmlDiv();
        innerDiv.setId("" + id);
        if (StringUtils.isNotEmpty(getDisplay())) {
            innerDiv.setStyle("display:" + getDisplay());
        }
        div.addChild(innerDiv);

        return div;
    }

	public void setOptions(Options options) {
		this.options = options;
	}

	public Options getOptions() {
		return options;
	}
	
    private Html drawOptions(List<Option> options, String id, Theme theme, String width) {

		HtmlDiv parentDiv = new HtmlDiv();
        parentDiv.setAlign("left");
        HtmlTable table = new HtmlTable();
        parentDiv.addChild(table);
        table.setClazz(theme.getValue(ThemeConst.TABLE.toString()));
        HtmlTr tr = table.addTr();
        tr.setOnClick("toggleShowHide('" + id + "');");
        tr.setStyle("cursor: pointer;");
        tr.setClazz(theme.getValue(ThemeConst.NAVOPTION.toString())+ " " + theme.getValue(ThemeConst.NAVOPTION_BORDER.toString()));
        HtmlTd td = tr.addTd();
        td.setClazz(theme.getValue(ThemeConst.NAVOPTION_FONT.toString()));
        td.setContent(getOptions().getTitle());
        td = tr.addTd();
        td.setClazz(theme.getValue(ThemeConst.NAV_DOWN_ARROW.toString()));

        HtmlDiv div = parentDiv.addDiv(theme);
        div.setStyle("display:none; position:absolute; ");
        // div.setOnMouseOut("hide('" + id + "');");
        div.setId(id);

        table = div.addTable(theme);
        table.setClazz(theme.getValue(ThemeConst.TABLE.toString()) + " "
                + theme.getValue(ThemeConst.NAVOPTION_BORDER.toString()));
        table.setAlign("right");
        // tr = table.addTr();
        // tr.setClazz(theme.getValue(ThemeConst.BORDER.toString()));
        // HtmlTh th = tr.addTh(theme);
        // th.setClazz(theme.getValue(ThemeConst.HEADER.toString()));
        // th.setContent(getOptions().getTitle());
		for (Option  option : options) {
			tr = table.addTr();
            tr.setClazz(theme.getValue(ThemeConst.NAVOPTION.toString()));
            td = new HtmlTd();
            tr.addChild(td);
            tr.setStyle("cursor: pointer;");
            td.setClazz(theme.getValue(ThemeConst.NAVOPTION_FONT.toString()));
            td.setContent(option.getLabel());
            if (option.getTooltip() != null) {
            	td.setTitle(option.getTooltip());
            }
            if (option.getAlign() != null) {
            	td.setAlign(option.getAlign());
            }
            td.setOnClick("submitLink('" + option.getUri() + "')");
            if (width != null) {
                td.setWidth(width);
            }
		}
		return parentDiv;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getHeight() {
		return height;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getTooltip() {
		return tooltip;
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
			return "true";
		}
		return is_allowed;
	}

	public void setIs_allowed(String is_allowed) {
		this.is_allowed = is_allowed;
	}

	public MenuImage getMenuImage() {
		return menuImage;
	}

	public void setMenuImage(MenuImage menuImage) {
		this.menuImage = menuImage;
	}


}
