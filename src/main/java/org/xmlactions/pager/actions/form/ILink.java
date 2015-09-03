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
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlImg;



/**
 * For supporters of image drawing. i.e. Link, AddRecordLink, DeleteRecordLink, UpdateRecordLink.
 * 
 * @author mike
 * 
 */
public interface ILink
{

	public void setTooltip(String tooltip);
	
	public String getTooltip();

	public String getHref();

	public void setHref(String href);

	public void setDisplay_as(String display_as);
	
	public String getDisplay_as();

	/** can also display an image as part of the link */
	public void setImage(String image);
	
	/** can also display an image as part of the link */
	public String getImage();
	
	/** width of border if using an image */
	public void setBorder(String border);

	/** width of border if using an image */
	public String getBorder();

	/** if there is text this can be used to set the position of the image on the left or the right of the text. */
	public void setImage_pos(String image_pos);

	/** if there is text this can be used to set the position of the image on the left or the right of the text. */
	public String getImage_pos();

	/** may want to specify the width of the link.button */
	public void setImage_width(String image_width);
	
	/** may want to specify the width of the link.button */
	public String getImage_width();
	
	/** may want to specify the height image */
	public void setImage_height(String image_height);

	/** may want to specify the height image */
	public String getImage_height();
	
	
}
