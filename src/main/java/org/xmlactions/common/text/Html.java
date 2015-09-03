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

package org.xmlactions.common.text;

import org.apache.commons.lang.StringUtils;

public class Html {

    /** Content Types that we may return to browser.  These are set in the HttpPager class */
    public final static String	CONTENT_TYPE_KEY = "content_type",
    							CONTENT_TYPE_HTML = "text/html;charset=UTF-8",
    							CONTENT_TYPE_XML = "text/xml;charset=UTF-8",
    							CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

	
	private String contentType = null;
	
	/**
	 * This will remove the html element from a page. The inner content of the html element is returned.
	 * 
	 * If no html start and end is found then the page us untouched.
	 * 
	 * @param page
	 * @return the inner content of the html element.
	 */
	public static String removeOuterHtml(String page) {
		String startHtmlPattern = "<html";
		String endHtmlPattern = "</html>";
		String html = page.toLowerCase();
		int startHtmlIndex = html.indexOf(startHtmlPattern);
		if (startHtmlIndex >= 0) {
			int indexFrom = page.indexOf('>', startHtmlIndex);
			if (indexFrom >= 0) {
				int indexTo = html.lastIndexOf(endHtmlPattern);
				if (indexTo >= 0) {
					page = page.substring(indexFrom+1, indexTo);
				}
			}
		}
		return page;
	}

	/**
	 * This will remove the json or xml element from a page. The inner content of the json or xml element is returned.
	 * 
	 * If it removes a json or xml element then it will set the content type to reflect the file mime type.
	 * 
	 * If no json/xml start and end is found then the page is untouched.
	 * 
	 * @param page
	 * @return the inner content of the json/xml element.
	 */
	public String removeOuterJsonOrXml(String page) {
		return _removeOuterJsonOrXml(page);
	}
	
	/**
	 * This will remove the json or xml element from a page. The inner content of the json or xml or html element is returned.
	 * 
	 * If it removes a json or xml element then it will set the content type to reflect the file mime type.
	 * 
	 * If no json/xml start and end is found then the page is untouched.
	 * 
	 * @param page
	 * @return the inner content of the json/xml element.
	 */
	private String _removeOuterJsonOrXml(String page) {
		String startJsonPattern = "<json";
		String endJsonPattern = "</json>";
		String startXmlPattern = "<xml";
		String endXmlPattern = "</xml>";
		String html = page.toLowerCase();
		int startIndex = html.indexOf(startJsonPattern);
		if (startIndex >= 0) {
			int indexFrom = page.indexOf('>', startIndex);
			if (indexFrom >= 0) {
				int indexTo = html.lastIndexOf(endJsonPattern);
				if (indexTo >= 0) {
					if (page.length() > indexTo) {
						if (StringUtils.isWhitespace(page.substring(indexTo + endJsonPattern.length()))) {
							page = page.substring(indexFrom+1, indexTo);
							setContentType(CONTENT_TYPE_JSON);
						}
					} else {
						page = page.substring(indexFrom+1, indexTo);
						setContentType(CONTENT_TYPE_JSON);
					}
				}
			}
		} else {
			startIndex = html.indexOf(startXmlPattern);
			if (startIndex >= 0) {
				int indexFrom = page.indexOf('>', startIndex);
				if (indexFrom >= 0) {
					int indexTo = html.lastIndexOf(endXmlPattern);
					if (indexTo >= 0) {
						if (page.length() > indexTo) {
							if (StringUtils.isWhitespace(page.substring(indexTo + endXmlPattern.length()))) {
								page = page.substring(indexFrom+1, indexTo);
								setContentType(CONTENT_TYPE_XML);
							}
						} else {
							page = page.substring(indexFrom+1, indexTo);
							setContentType(CONTENT_TYPE_XML);
						}
					}
				}
			}
		}
		return page;
	}

	/**
	 * This will remove the json or xml or html element from a page. The inner content of the json or xml or html element is returned.
	 * 
	 * If it removes a json or xml element then it will set the content type to reflect the file mime type.
	 * 
	 * If no json/xml/html start and end is found then the page is untouched.
	 * 
	 * @param page
	 * @return the inner content of the json/xml/html element.
	 */
	public String removeOuterJsonOrXmlOrHtml(String page) {
		String startJsonPattern = "<json";
		String endJsonPattern = "</json>";
		String startXmlPattern = "<xml";
		String endXmlPattern = "</xml>";
		String startHtmlPattern = "<html";
		String endHtmlPattern = "</html>";
		String html = page.toLowerCase();
		int startIndex = html.indexOf(startJsonPattern);
		if (startIndex >= 0) {
			int indexFrom = page.indexOf('>', startIndex);
			if (indexFrom >= 0) {
				int indexTo = html.lastIndexOf(endJsonPattern);
				if (indexTo >= 0) {
					if (page.length() > indexTo) {
						if (StringUtils.isWhitespace(page.substring(indexTo + endJsonPattern.length()))) {
							page = page.substring(indexFrom+1, indexTo);
							setContentType(CONTENT_TYPE_JSON);
						}
					} else {
						page = page.substring(indexFrom+1, indexTo);
						setContentType(CONTENT_TYPE_JSON);
					}
				}
			}
		} else {
			startIndex = html.indexOf(startXmlPattern);
			if (startIndex >= 0) {
				int indexFrom = page.indexOf('>', startIndex);
				if (indexFrom >= 0) {
					int indexTo = html.lastIndexOf(endXmlPattern);
					if (indexTo >= 0) {
						if (page.length() > indexTo) {
							if (StringUtils.isWhitespace(page.substring(indexTo + endXmlPattern.length()))) {
								page = page.substring(indexFrom+1, indexTo);
								setContentType(CONTENT_TYPE_XML);
							}
						} else {
							page = page.substring(indexFrom+1, indexTo);
							setContentType(CONTENT_TYPE_XML);
						}
					}
				}
			} else {
				startIndex = html.indexOf(startHtmlPattern);
				if (startIndex >= 0) {
					int indexFrom = page.indexOf('>', startIndex);
					if (indexFrom >= 0) {
						int indexTo = html.lastIndexOf(endHtmlPattern);
						if (indexTo >= 0) {
							if (page.length() > indexTo) {
								if (StringUtils.isWhitespace(page.substring(indexTo + endHtmlPattern.length()))) {
									page = page.substring(indexFrom+1, indexTo);
									//setContentType(CONTENT_TYPE_HTML);
								}
							} else {
								page = page.substring(indexFrom+1, indexTo);
								//setContentType(CONTENT_TYPE_HTML);
							}
						}
					}
				}
				
			}
		}
		return page;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


}
