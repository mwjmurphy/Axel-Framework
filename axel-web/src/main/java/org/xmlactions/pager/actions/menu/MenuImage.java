package org.xmlactions.pager.actions.menu;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlImg;


public class MenuImage {
    private String src;
	private String width;
    private String height;
    private String border;
    
	public Html mapToHtml(IExecContext execContext, Theme theme) {
		// <img src="css/chrome-logo.png" height="12"/>
		HtmlImg img = new HtmlImg();
		img.setSrc(getSrc());
		if (height != null) {img.setHeight(height);}
        if (width != null) {img.setWidth(width);}
        if (border != null) {img.setBorder(border);}
        else {img.setBorder("0");}
		return img;
	}

    public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

}
