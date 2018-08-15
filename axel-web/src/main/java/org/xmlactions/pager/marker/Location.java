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
