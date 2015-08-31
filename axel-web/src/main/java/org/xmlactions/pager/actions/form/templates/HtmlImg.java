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

package org.xmlactions.pager.actions.form.templates;

import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;



public class HtmlImg extends HtmlEvents
{

	/*
	 * @deprecated - use HtmlImg(theme) instead
	 */
	public HtmlImg()
	{

		super(HtmlEnum.label_img.getAttributeName());
	}

	public HtmlImg(Theme theme)
	{

		super(HtmlEnum.label_img.getAttributeName());
		setClazz(theme.getValue(ThemeConst.IMG.toString()));
	}
	
	/** Assigns a name to an image. This is used when referencing the image with stylesheets or scripts. However, you should use the id attribute instead. */
	public void setName(String value) {

            put(HtmlEnum.name.getAttributeName(), value);
	}
	 

	/** Specifies a URI/URL of a long description - this can elaborate on a shorter description specified with the alt attribute. */
	public void setLongdesc(String value)
	{

		put(HtmlEnum.longdesc.getAttributeName(), value);

	}

	/** Location of the image. */
	public void setSrc(String value)
	{

		put(HtmlEnum.src.getAttributeName(), value);
	}

	/** Alternate text if no image. */
	public void setAlt(String value)
	{

		put(HtmlEnum.alt.getAttributeName(), value);
	}

	public void setHeight(String value)
	{

		put(HtmlEnum.height.getAttributeName(), value);
	}

	public void setWidth(String value)
	{

		put(HtmlEnum.width.getAttributeName(), value);
	}

    public void setBorder(String value) {
        put(HtmlEnum.border.getAttributeName(), value);
    }

}
