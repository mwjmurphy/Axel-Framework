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

package org.xmlactions.pager.marker;

public class Location {

	private int start;
	private int width;
	private String replacementContent;
	
	Location(int start, int width, String replacementContent)
	{
		setStart(start);
		setWidth(width);
		setReplacementContent(replacementContent);
	}

	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getEnd()
	{
		return getStart() + getWidth();
	}
	
	public int getDifference()
	{
		return getReplacementContent().length() - getWidth();
	}
	public String getReplacementContent() {
		return replacementContent;
	}
	
	public void setReplacementContent(String replacementContent) {
		this.replacementContent = replacementContent;
	}
}
