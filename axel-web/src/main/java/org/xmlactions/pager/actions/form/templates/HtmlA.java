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



@SuppressWarnings("serial")
public class HtmlA extends HtmlEvents
{
	
	public HtmlA() {
		super(HtmlEnum.label_a.getAttributeName());
	}

	/** Marks an area of the page that a link jumps to. */
	public void setName(String value) {
		put(HtmlEnum.name.getAttributeName(), value);
	}

	/** Specifies the URL of a page or the name of the anchor that the link goes to. */
	public void setHref(String value) {
		put(HtmlEnum.href.getAttributeName(), value);
	}

	/** Language code of the destination URL */
	public void setHreflang(String value)
	{
		put(HtmlEnum.hreflang.getAttributeName(), value);
	}


	/** The type of content at the link destination */
	public void setType(String value)
	{
		put(HtmlEnum.type.getAttributeName(), value);

	}

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
	public void setRel(String value) {
		put(HtmlEnum.rel.getAttributeName(), value);
	}

	 

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
	    - subsection */
	public void setRev(String value) {
            put(HtmlEnum.rev.getAttributeName(), value);
	}

	/** Defines the character encoding of the linked document. */
	public void setCharset(String value) {

		put(HtmlEnum.charset.getAttributeName(), value);

	}

	 

}
