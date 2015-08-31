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


public enum HtmlEnum
{
	label_blank(""),
	label_br("br"),
	label_input("input"),
	label_form("form"),
	label_iframe("iframe"),
	label_table("table"),
	label_tr("tr"),
	label_th("th"),
	label_td("td"),
	label_textarea("textarea"),
	label_a("a"),
	label_button("button"),
	label_img("img"),
	label_div("div"),
	label_span("span"),
	label_pre("pre"),
	label_colgroup("colgroup"),
	label_col("col"),
	label_font("font"),
	label_select("select"),
	label_option("option"),

	/** Specifies a classname for an element */
	clazz("class"),

	/** 
	 * Specifies the text direction for the content in an element
	 * rtl, ltr 	 	
	 */
	dir("dir"),

	/** Specifies a unique id for an element */
	id("id"),

	/** Specifies a language code for the content in an element */
	lang("lang"),

	/** Specifies an inline style for an element */
	style("style"),

	/** Specifies extra information about an element */
	title("title"),

	/** Specifies a language code for the content in an element, in XHTML documents */
	xml_lang("xml_lang"),
	
	/** Specifies where to send the form-data when a form is submitted */
	action("action"),

	/** Specifies the types of files that can be submitted through a file upload */
	accept("accept"),

	/** Specifies the character-sets the server can handle for form-data */
	accept_charset("accept_charset"),
	
	/**
	 * Specifies how form-data should be encoded before sending it to a server<br/>
	 * application/x-www-form-urlencoded<br/>
	 * multipart/form-data<br/>
	 * text/plain<br/>
	 */
	enctype("enctype"),
	
	
	/**
	 * Specifies how to send form-data
	 * 	get
	 * 	post
	 */
	method("method"),
	
	/**
	 * Specifies the name for a form
	 * Specifies the name of an iframe
	 * Marks an area of the page that a link jumps to.
	 */
	name("name"),
	

	/**
	 * @deprecated. Specifies where to open the action URL
	 *              <p>
	 *              _blank <br/>
	 *              _self <br/>
	 *              _parent <br/>
	 *              _top <br/>
	 *              framename <br/>
	 *              </p>
	 */
	target("target"),

	/**
	 * <p>
	 * (input) Specifies the alignment of an image input (only for type="image")
	 * </p><p>
	 * (iframe) Specifies the alignment of an iframe according to surrounding elements
	 * deprecated. Use styles instead.
	 * </p>
	 * <p>
	 * Aligns the content in a table row
	 * right, left, center, justify,char
	 * </p>
	 * <p>
	 * left</br>
	 * right</br>
	 * top</br>
	 * middle</br>
	 * bottom</br>
	 * </p>
	 */
	align("align"),

	/** Specifies an alternate text for an image input (only for type="image") */
	alt("alt"),

	/** Specifies that an input element should be preselected when the page loads (for type="checkbox" or type="radio") */
	checked("checked"),

	/** Specifies that an input element should be disabled when the page loads */
	disabled("disabled"),

	/** Specifies the maximum length (in characters) of an input field (for type="text" or type="password") */
	maxlength("maxlength"),
	
	/** Specifies the pattern to use for an input field. New in html5 */
	pattern("pattern"),

	/** Specifies the placeholder to use for an input field. New in html5 */
	placeholder("placeholder"),

	/** Specifies that an input field should be read-only (for type="text" or type="password") */
	readonly("readonly"),

	/** Specifies that an input field must be set with a value. New in html5 */
	required("required"),
	
	/**
	 * Specifies the width of an input field
	 * Specifies the font size.
	 */
	size("size"),

	/** Specifies the URL to an image to display as a submit button */
	src("src"),

	/**
	 * Specifies the type of an input element
	 * The type of content at the link destination
	 * 
	 * button, checkbox, file, hidden, image, password, radio, reset, submit, text
	 */
	type("type"),

	/** Specifies the value of an input element */
	value("value"),
	
	
	/**
	 * Specifies whether or not to display a border around an iframe
	 * 1 or 0 
	 */
	frameborder("frameborder"),
	
	/**
	 * Specifies the height of an iframe
	 * pixels
	 */
	height("height"),
	
	/**
	 * Specifies a page that contains a long description of the content of an iframe
	 */
	longdesc("longdesc"),

	/**
	 * Specifies the top and bottom margins of an iframe
	 * pixels
	 */ 
	marginheight("marginheight"),
	
	/** 
	 * Specifies the left and right margins of an iframe
	 * pixels
	 */ 
	marginwidth("marginwidth"),

	/**
	 * Specifies whether or not to display scrollbars in an iframe
	 * yes
	 * no
	 * auto
	 */
	scrolling("scrolling"),
	
	/**
	 * Specifies the width of an iframe
	 * pixels
	 */
	width("width"),
	
	/**
	 * Specifies the background color for a table
	 * bgcolor rgb(x,x,x) or #xxxxxx colorname
	 * @deprecated. Use styles instead.
	 */
	bgcolor("bgcolor"),
	
	/**
	 * Specifies the width of the borders around a table
	 * border pixels
	 */
	border("border"),

	/**
	 * Specifies the space between the cell wall and the cell content
	 */
	cellpadding("cellpadding"),

	/**
	 * Specifies the space between cells
	 */
	cellspacing("cellspacing"),
	
	/** Specifies which parts of the outside borders that should be visible
	 * void
	 * above
	 * below
	 * hsides
	 * lhs
	 * rhs
	 * vsides
	 * box
	 * border
	 */
	frame("frame"),
	
	/**
	 * Specifies which parts of the inside borders that should be visible <br/>
	 * none <br/>
	 * groups <br/>
	 * rows <br/>
	 * cols <br/>
	 * all <br/>
	 */
	rules("rules"),

	
	/** Specifies a summary of the content of a table */
	summary("summary"),
	
	/** Aligns the content in a table row to a character */
	char_("char"),
	
	/**
	 * Sets the number of characters the content will be aligned from the character specified by the char attribute
	 */
	charoff("charoff"),

	/**
	 * Vertical aligns the content in a table row
	 * top<br/>
	 * middle<br/>
	 * bottom<br/>
	 * baseline<br/>
	 */
	valign("valign"),
	
	/**
	 * Defines a way to associate header cells and data cells in a table
	 * col<br/>
	 * colgroup<br/>
	 * row<br/>
	 * rowgroup<br/>
	 */	
	scope("scope"),

	/** Sets the number of rows a cell should span */
	rowspan("rowspan"),
	
	/**
	 * Specifies that the content inside a cell should not wrap
	 * Deprecated. Use styles instead.
	 */
	nowrap("nowrap"),

	/** Sets the number of columns a cell should span */
	colspan("colspan"),

	/**
	 * Categorizes cells
	 * category_name
	 */ 
	axis("axis"),
	/**
	 * Specifies an abbreviated version of the content in a cell
	 */
	abbr("abbr"),

	/**
	 * Specifies the table headers related to a cell headercells'_id
	 */
	headers("headers"),

	/**
	 * Specifies the height of the textarea based on the number of visible lines of text. If there's more text than this
	 * allows, users can scroll using the textarea's scrollbars.
	 */
	rows("rows"),

	/** Specifies the width of the textarea based on the number of visible character widths. */
	cols("cols"),

	/** Specifies the URL of a page or the name of the anchor that the link goes to. */
	href("href"),

	/** Language code of the destination URL */
	hreflang("hreflang"),

	/** Describes the relationship between the current document and the destination URI.
    - alternate
    - appendix
    - bookmark
    - chapter
    - contents
    - copyright
    - glossary
    - help
    - home
    - index
    - next
    - prev
    - section
    - start
    - stylesheet
    - subsection
	 */
	rel("rel"),

	/** Describes a reverse between the destination URI and the current document. Possible values:
    - alternate
    - appendix
    - bookmark
    - chapter
    - contents
    - copyright
    - glossary
    - help
    - home
    - index
    - next
    - prev
    - section
    - start
    - stylesheet
    - subsection
    */
	rev("rev"),

	/** Defines the character encoding of the linked document. */
	charset("charset"),

	/** Specifies how many columns to span. */
	span("span"),

	/** Specifies the font face. */
	face("face"),

	/** Specifies the font color. */
	color("color"),

	/** Specifies that multiple selections can be made. */
	multiple("multiple"),

	/** Specifies that this option will be pre-selected when the user first loads the page. */
	selected("selected"),

	/**
	 * Specifies a label to be used as an alternative to the option item's contents. Useful if you'd prefer a shorter,
	 * more concise label.
	 */
	label("label"),

	/**
	 * Window Events Only valid in body and frameset elements.
	 */
	/**
	 * Script to be run when a document loads
	 */
	onload("onload"),

	/** Script to be run when a document unloads */
	onunload("onunload"),
	
	/**
	 * Form Element Events
	 * Only valid in form elements.
	 */
	/**
	 * Script to be run when the element changes
	 * <p> Only valid in form elements </p>
	 */
	onchange("onchange"),
	
	/**
	 * Script to be run when the form is submitted
	 * <p> Only valid in form elements </p>
	 */
	onsubmit("onsubmit"),

	/** 
	 * Script to be run when the form is reset
	 * <p> Only valid in form elements </p>
	 */
	onreset("onreset"),
	
	/** 
	 * Script to be run when the element is selected
	 * <p> Only valid in form elements </p>
	 */
	onselect("onselect"),

	/** 
	 * Script to be run when the element loses focus
	 * <p> Only valid in form elements </p>
	 */
	onblur("onblur"),
	
	/** 
	 * Script to be run when the element gets focus
	 * <p> Only valid in form elements </p>
	 */
	onfocus("onfocus"),

	/**
	 * Keyboard Events
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, and title elements.
	 */
	/**
	 * What to do when key is pressed
	 * <p>Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, and title elements.</p>
	 */
	onkeydown("onkeydown"),

	/**
	 * What to do when key is pressed and released
	 * <p>Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, and title elements.</p>
	 */
	onkeypress("onkeypress"),

	/**
	 * What to do when key is released
	 * <p>Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, and title elements.</p>
	 */
	onkeyup("onkeyup"),

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
	onclick("onclick"),

	/**
	 * What to do on a mouse double-click
	 * <p>
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 * </p>
	 */
	ondblclick("ondblclick"),

	/**
	 * What to do when mouse button is pressed
	 * <p>
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 * </p>
	 */
	onmousedown("onmousedown"),

	/**
	 * What to do when mouse pointer moves
	 * <p>
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 * </p>
	 */
	onmousemove("onmousemove"),

	/**
	 * What to do when mouse pointer moves out of an element
	 * <p>
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 * </p>
	 */
	onmouseout("onmouseout"),

	/**
	 * What to do when mouse pointer moves over an element
	 * <p>
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 * </p>
	 */
	onmouseover("onmouseover"),

	/**
	 * What to do when mouse button is released
	 * <p>
	 * Not valid in base, bdo, br, frame, frameset, head, html, iframe, meta, param, script, style, title elements.
	 * </p>
	 */
	onmouseup("onmouseup"),
	
	/**
	 * IFrame have this
	 */
	allowtransparency("allowtransparency"),
	;

	private String attributeName;

	HtmlEnum(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public String getAttributeName()
	{

		return this.attributeName;
	}
	public String toString()
	{

		return attributeName;
	}

}
