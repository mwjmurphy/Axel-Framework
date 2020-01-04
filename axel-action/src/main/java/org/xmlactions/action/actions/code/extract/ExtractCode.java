package org.xmlactions.action.actions.code.extract;

/**
 * extraction of code between {{...}}
 * @author mikem
 *
 */
public class ExtractCode {
	
	private char[] startCodeSection = new char[] {'{','{','{'};
	private char[] endCodeSection = new char[] {'}','}','}'};
	
	private final String content;
	
	
	public ExtractCode(final String content) {
		this.content = content;
	}
	
	public void setStartCodeSection(char [] startCodeSection) {
		this.startCodeSection = startCodeSection;
	}
	
	public void setEndCodeSection(char [] endCodeSection) {
		this.endCodeSection = endCodeSection;
	}
	
	public int getStartMarkerLength() {
		return startCodeSection.length;
	}
	
	public int getEndMarkerLength() {
		return endCodeSection.length;
	}
	
	public String getInnerContent(int startIndex, int endIndex) {
		return this.content.substring(startIndex, endIndex);
	}
	 
	/**
	 * @param indexFrom - an index into the content string where we want to start looking for the start code sections
	 * @return the new index of the start code section passed the startCodeSection.
	 */
	public int getStartBraces(int indexFrom) {
		for (int index = indexFrom ; index < content.length(); index++) {
			if (content.charAt(index) == startCodeSection[0]) {
				if (content.charAt(index+1) == startCodeSection[1]) {
					return index;
				}
			}
		}
		return -1;
	}

	/**
	 * @param indexFrom - an index into the content string where we want to start looking for the end code sections
	 * @return the new index of the end code section before the endCodeSection.
	 */
	public int getEndBraces(int indexFrom) {
		int nested = 0;
		for (int index = indexFrom ; index < content.length()-endCodeSection.length+1; index++) {
			char c = content.charAt(index);
			if (c == endCodeSection[0]) {
				if (content.charAt(index+1) == endCodeSection[1]) {
					if (endCodeSection.length > 2) {
						if (content.charAt(index+2) == endCodeSection[2]) {
							nested--;
							if (nested < 0) {
								return index + getEndMarkerLength();
							}
							index+=2;
						}
					} else {
						nested--;
						if (nested < 0) {
							return index + getEndMarkerLength();
						}
						index+=1;
					}
				}
			}
			else if (c == startCodeSection[0]) {
				if (content.charAt(index+1) == startCodeSection[1]) {
					if (startCodeSection.length > 2) {
						if (content.charAt(index+2) == startCodeSection[2]) {
							nested++;
							index+=2;
						}
					} else {
						nested++;
						index+=1;
					}
				}
			}
		}
		return -1;
	}

}
