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
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.EscapeUtils;
import org.xmlactions.common.text.Text;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlA;
import org.xmlactions.pager.actions.form.templates.HtmlBlank;
import org.xmlactions.pager.actions.form.templates.HtmlImg;
import org.xmlactions.pager.actions.form.templates.HtmlSpan;


/**
 * Container class for html links.
 * 
 * @author mike
 * 
 */
public class Link extends BaseAction implements IDraw, ILink, Cloneable
{

	public static final String DISPLAY_AS_BUTTON = "button", DISPLAY_AS_LINK = "link", DISPLAY_AS_MENU = "menu";

	private String id;	// id if stored in the execContext

	private String name; // the display name for the link

	private String tooltip;

	private String href; // where the link goes to
	
	private String target;	// option href target

	private String image;		// can also display an image as part of the link
	
	private String image_pos;	// if there is text this can be used to set the position of the image on the left or the right of the text.
	
	private String border;		// width of border if using an image
	
	private String width;	// may want to specify the width of the link.button 

	private String image_width;	// may want to specify the width of the image
	
	private String image_height;	// may want to specify the height image

	private String display_as; // can be either link or button, default = link.

	private boolean submit; // if set to true then this link is used to perform

	private String actionScript; // this will get set by the class servicing the link

    private boolean remove_crlf; // if set true will remove CRLFs from
                                 // actionText

    private String header;	// if the link is part of a list then this is displayed as the header. 
	// ajax.submission call. @see Add.


	// the ajax.submission service. @see Add

    private String is_allowed;	// if not set true than dont show.

	public static Link newLink(String name)
	{

		Link link = new Link();
		link.setName(name);
		return link;

	}

	public static Link newLink(String name, String display_as)
	{

		Link link = new Link();
		link.setName(name);
		link.setDisplay_as(display_as);
		return link;

	}

	public static Link newLink(String name, String display_as, String tooltip)
	{

		Link link = new Link();
		link.setName(name);
		link.setDisplay_as(display_as);
		link.setTooltip(tooltip);
		return link;

	}

	public static Link newLink(ILink iLink, String name)
	{

		Link link = new Link();

		link.setName(name);

		link.setDisplay_as(iLink.getDisplay_as());

		link.setTooltip(iLink.getTooltip());

		link.setHref(iLink.getHref());

		link.setImage(iLink.getImage());
		
		link.setBorder(iLink.getBorder());

		link.setImage_pos(iLink.getImage_pos());

		link.setImage_width(iLink.getImage_width());
		
		link.setImage_height(iLink.getImage_height());
		
		return link;

	}

	public static Link newLink(ILink iImage, String name, String display_as, String tooltip)
	{

		Link link = new Link();
		link.setName(name);
		link.setDisplay_as(display_as);
		link.setTooltip(tooltip);
		return link;

	}

	public String execute(IExecContext execContext)
	{

		return null;
	}

	public Html draw(IExecContext execContext, Theme theme)
	{

    	String value = execContext.replace(getIs_allowed());
    	if (Text.isFalse(value)) {
    		return new HtmlBlank();
    	}

		
		HtmlA a = new HtmlA();
		
		HtmlSpan span = new HtmlSpan();
		String displayAs = execContext.replace(getDisplay_as());
		if (displayAs.equals(DISPLAY_AS_BUTTON)) {
			a.setClazz(theme.getValue(ThemeConst.INPUT_LINK_BUTTON.toString()));
			span.setClazz(theme.getValue(ThemeConst.INPUT_LINK_BUTTON.toString()));
		} else if (displayAs.equals(DISPLAY_AS_MENU)) {
			a.setClazz(theme.getValue(ThemeConst.INPUT_LINK_MENU.toString()));
			span.setClazz(theme.getValue(ThemeConst.INPUT_LINK_MENU.toString()));
		} else {
			a.setClazz(theme.getValue(ThemeConst.INPUT_LINK.toString()));
			span.setClazz(theme.getValue(ThemeConst.INPUT_LINK_MENU.toString()));
		}
        if (StringUtils.isNotEmpty(getWidth())) {
        	if (span.getStyle() != null) {
        		span.setStyle(span.getStyle() + "; width:" + getWidth() + ";");
        	} else {
        		span.setStyle("width:" + getWidth() + ";");
        	}
        	// span.setWidth(getWidth());
        }
        if (StringUtils.isNotEmpty(getUri())) {
            String uri = execContext.replace(getUri());
            uri = EscapeUtils.escapeUrl(uri);
            a.setHref(uri);
        } else {
            a.setHref("");
        }
		if (isSubmit()) {
            if (StringUtils.isNotEmpty(getActionScript())) {
                String onClick = execContext.replace(getActionScript());
                if (remove_crlf == true) {
                    onClick = onClick.replaceAll("\r", "<br/>");
                    onClick = onClick.replaceAll("\n", "");
                }
                String oc = EscapeUtils.escapeUrl(onClick);
                a.setOnClick(oc);
            }
		}
		
    	HtmlImg htmlImg = buildImage(this);

		if (StringUtils.isNotEmpty(getName())) {
			if (htmlImg != null) {
				if ("right".equalsIgnoreCase(getImage_pos())) {
					String content = execContext.replace(getName());
					content+=htmlImg.toString();
					span.setContent(content);
				} else {
					String content = htmlImg.toString(); 
					content+= execContext.replace(getName());
					span.setContent(content);
				}
			} else {
				span.setContent(execContext.replace(getName()));
			}
        } else if (htmlImg != null) {
        	span.addChild(htmlImg);
        }
		a.addChild(span);
        
        a.setTitle(execContext.replace(getTooltip()));
        
        if (StringUtils.isNotEmpty(getTarget())) {
        	a.setTarget(execContext.replace(getTarget()));
        }
		return a;

	}


    public String getUri() {

        return href;
    }

    public String getUri(IExecContext execContext) {
        String uri = execContext.replace(getUri());
        uri = EscapeUtils.escapeUrl(uri);
        return uri;
    }

	public void setUri(String uri)
	{

		this.href = uri;
	}

	public String getName()
	{

		return name;
	}

	public void setName(String name)
	{

		this.name = name;
	}

	public void setSubmit(boolean submit)
	{

		this.submit = submit;
	}

	public boolean isSubmit()
	{

		return submit;
	}

	public void setActionText(String actionText)
	{

		this.actionScript = actionText;
	}

	public String getActionText()
	{

		return actionScript;
	}

	public void setActionScript(String actionScript)
	{

		this.actionScript = actionScript;
	}

	public String getActionScript()
	{

		return actionScript;
	}

	public void setDisplay_as(String display_as)
	{

		this.display_as = display_as;
	}

	public String getDisplay_as()
	{

		return display_as != null ? display_as : "link";
	}

	public void setTooltip(String tooltip)
	{

		this.tooltip = tooltip;
	}

	public String getTooltip()
	{

		return tooltip;
	}

    public boolean isRemove_crlf() {
        return remove_crlf;
    }

    public void setRemove_crlf(boolean removeCrlf) {
        remove_crlf = removeCrlf;
    }

	public void setHeader(String header) {
		this.header = header;
	}

	public String getHeader() {
		return header;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getHref() {
		return href;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTarget() {
		return target;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getWidth() {
		return width;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getBorder() {
		return border;
	}

	public void setImage_pos(String image_pos) {
		this.image_pos = image_pos;
	}

	public String getImage_pos() {
		return image_pos;
	}

	public void setImage_width(String image_width) {
		this.image_width = image_width;
	}

	public String getImage_width() {
		return image_width;
	}

	public void setImage_height(String image_height) {
		this.image_height = image_height;
	}

	public String getImage_height() {
		return image_height;
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
	
	public Object clone()
    {
        try
	    {
        	return super.clone();
        }
	    catch( CloneNotSupportedException e )
	    {
	    	return null;
        }
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	/**
	 * build an HtmlImg
	 * 
	 * @return an HtmlImg object created from the associated class atributes.
	 */
	public static HtmlImg buildImage(ILink iImage) {
    	HtmlImg htmlImg = null;
		if (StringUtils.isNotEmpty(iImage.getImage())) {
	    	htmlImg = new HtmlImg();
	    	htmlImg.setSrc(iImage.getImage());
	    	if (iImage.getImage_width() != null) {
	    		htmlImg.setWidth(iImage.getImage_width());
	    	}
	    	if (iImage.getImage_height() != null) {
	    		htmlImg.setHeight(iImage.getImage_height());
	    	}
	    	if (iImage.getBorder() != null) {
	    		htmlImg.setBorder(iImage.getBorder());
	    	}
		}
		return htmlImg;
	}
	
}
