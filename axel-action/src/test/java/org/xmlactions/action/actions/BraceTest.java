package org.xmlactions.action.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test processing oc code between {{...}}
 * @author mikem
 *
 */
public class BraceTest {
	
	private final String html = "<head>{{this is where the code goes}}";
	
	private final String nestedHtml = "<head>{{{{this is where the code goes}}}}";
	String content;
	
	@Test
	public void testGetStartBrace() {
		content = html;
		int startIndex = getStartBraces(0);
		assertEquals(6, startIndex);
	}
	
	@Test
	public void testGetEndBrace() {
		content = html;
		int endIndex = getEndBraces(8);
		assertEquals(35, endIndex);
	}
	
	@Test
	public void testGetBetweenBraces() {
		content = html;
		int startIndex = 0;
		while ((startIndex = getStartBraces(startIndex)) >= 0) {
			assertEquals(6, startIndex);
			
			startIndex++;
		}
		assertEquals(-1, startIndex);
	}
	
	@Test
	public void testGetNestedStartBrace() {
		content = nestedHtml;
		int startIndex = getStartBraces(0);
		assertEquals(6, startIndex);
	}
	
	@Test
	public void testGetNestedEndBrace() {
		content = nestedHtml;
		int endIndex = getEndBraces(8);
		assertEquals(39, endIndex);
	}
	
	private int getStartBraces(int indexFrom) {
		for (int index = indexFrom ; index < content.length(); index++) {
			if (content.charAt(index) == '{') {
				if (content.charAt(index+1) == '{') {
					return index;
				}
			}
		}
		return -1;
	}

	private int getEndBraces(int indexFrom) {
		int nested = 0;
		for (int index = indexFrom ; index < content.length()-1; index++) {
			char c = content.charAt(index);
			if (c == '}') {
				if (content.charAt(index+1) == '}') {
					nested--;
					if (nested < 0) {
						return index;
					}
					index++;
				}
			}
			else if (c == '{') {
				if (content.charAt(index+1) == '{') {
					nested++;
					index++;
				}
			}
		}
		return -1;
	}

}
