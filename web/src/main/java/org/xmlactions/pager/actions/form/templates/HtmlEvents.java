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

/**
 * Script Events
 */
public class HtmlEvents extends Html
{

	/**
	 * Script Events
	 */
	public HtmlEvents(String label)
	{
		super(label);
	}
	
	/**
	 * Script to be run when a document loads
	 * <p>
	 * Window Events Only valid in body and frameset elements.
	 * </p>
	 */
	public void setOnLoad(String value)
	{

		put(HtmlEnum.onload.getAttributeName(), value);
	}
	
	/**
	 * Script to be run when a document unloads
	 * <p>
	 * Window Events Only valid in body and frameset elements.
	 * </p>
	 */
	public void setOnUnLoad(String value)
	{

		put(HtmlEnum.onunload.getAttributeName(), value);
	}
	/**
	 * Script to be run when the element changes
	 * <p> Only valid in form elements </p>
	 */
	public void setOnChange(String value)
	{

		put(HtmlEnum.onchange.getAttributeName(), value);
	}
	
	/**
	 * Script to be run when the form is submitted
	 * <p> Only valid in form elements </p>
	 */
	public void setOnSubmit(String value)
	{
		put(HtmlEnum.onsubmit.getAttributeName(), value);
	}

	/** 
	 * Script to be run when the form is reset
	 * <p> Only valid in form elements </p>
	 */
	public void setOnReset(String value)
	{

		put(HtmlEnum.onreset.getAttributeName(), value);
	}
	
	/** 
	 * Script to be run when the element is selected
	 * <p> Only valid in form elements </p>
	 */
	public void setOnSelect(String value)
	{

		put(HtmlEnum.onselect.getAttributeName(), value);
	}

	/** 
	 * Script to be run when the element loses focus
	 * <p> Only valid in form elements </p>
	 */
	public void setOnBlur(String value)
	{

		put(HtmlEnum.onblur.getAttributeName(), value);
	}
	
	/** 
	 * Script to be run when the element gets focus
	 * <p> Only valid in form elements </p>
	 */
	public void setOnFocus(String value)
	{

		put(HtmlEnum.onfocus.getAttributeName(), value);
	}

	/**
	 * Keyboard Events
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, and title elements.
	 */
	/**
	 * What to do when key is pressed
	 * <p>Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, and title elements.</p>
	 */
	public void setOnKeyDown(String value)
	{

		put(HtmlEnum.onkeydown.getAttributeName(), value);
	}

	/**
	 * What to do when key is pressed and released
	 * <p>Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, and title elements.</p>
	 */
	public void setOnKeyPress(String value)
	{

		put(HtmlEnum.onkeypress.getAttributeName(), value);
	}

	/**
	 * What to do when key is released
	 * <p>Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, and title elements.</p>
	 */
	public void setOnKeyUp(String value)
	{

		put(HtmlEnum.onkeyup.getAttributeName(), value);
	}

	/**
	 * Mouse Events
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 */

	/**
	 * What to do on a mouse click
	 * <p>
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 * </p>
	 */
	public void setOnClick(String value)
	{

		put(HtmlEnum.onclick.getAttributeName(), value);
	}

	/**
	 * What to do on a mouse double-click
	 * <p>
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 * </p>
	 */
	public void setOnDblClick(String value)
	{

		put(HtmlEnum.ondblclick.getAttributeName(), value);
	}

	/**
	 * What to do when mouse button is pressed
	 * <p>
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 * </p>
	 */
	public void setOnMouseDown(String value)
	{

		put(HtmlEnum.onmousedown.getAttributeName(), value);
	}


	/**
	 * What to do when mouse pointer moves
	 * <p>
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 * </p>
	 */
	public void setOnMouseMove(String value)
	{

		put(HtmlEnum.onmousemove.getAttributeName(), value);
	}

	/**
	 * What to do when mouse pointer moves out of an element
	 * <p>
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 * </p>
	 */
	public void setOnMouseOut(String value)
	{

		put(HtmlEnum.onmouseout.getAttributeName(), value);
	}

	/**
	 * What to do when mouse pointer moves over an element
	 * <p>
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 * </p>
	 */
	public void setOnMouseOver(String value)
	{

		put(HtmlEnum.onmouseover.getAttributeName(), value);
	}

	/**
	 * What to do when mouse button is released
	 * <p>
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 * </p>
	 */
	public void setOnMouseUp(String value)
	{

		put(HtmlEnum.onmouseup.getAttributeName(), value);
	}
	
}
