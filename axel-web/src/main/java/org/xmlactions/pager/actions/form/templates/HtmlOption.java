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


public class HtmlOption extends Html
{

	public HtmlOption()
	{

		super(HtmlEnum.label_option.getAttributeName());
	}

	/** Specifies that this option will be pre-selected when the user first loads the page. */
	public void setSelected(String value)
	{
		put(HtmlEnum.selected.getAttributeName(), value);
	}

	/** Specifies the initial value of the option item. */
	public void setValue(String value) {
          put(HtmlEnum.value.getAttributeName(), value);
	}

	/** Specifies a label to be used as an alternative to the option item's contents. Useful if you'd prefer a shorter, more concise label. */
	public void setLabel(String value) {
            put(HtmlEnum.label.getAttributeName(), value);
	}


}
