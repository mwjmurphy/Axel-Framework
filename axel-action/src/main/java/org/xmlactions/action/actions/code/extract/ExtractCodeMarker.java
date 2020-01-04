package org.xmlactions.action.actions.code.extract;

public class ExtractCodeMarker {

	private int startIndex;
	private int endIndex;
	private ExtractCode extractCode;
	
	public ExtractCodeMarker(int startIndex, int endIndex, ExtractCode extractCode) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.extractCode = extractCode;
	}
	
	public String getInnerContent() {
		
		String content = this.extractCode.getInnerContent(startIndex + extractCode.getStartMarkerLength(), endIndex - extractCode.getEndMarkerLength());
		return content;
		
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public ExtractCode getExtractCode() {
		return extractCode;
	}

	public void setExtractCode(ExtractCode extractCode) {
		this.extractCode = extractCode;
	}
	

}



