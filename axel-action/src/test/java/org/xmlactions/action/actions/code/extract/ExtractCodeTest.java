package org.xmlactions.action.actions.code.extract;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExtractCodeTest {

	private final String html = "<head>{{this is where the code goes}}";
	
	private final String nestedHtml = "<head>{{{{{{this is where the code goes}}}}}}";

	private final String nestedHtml2 = "<head>#{{#{{this is where the code goes}}#}}#";
	
	@Test
	public void testGetStartBrace() {
		ExtractCode extractCode = new ExtractCode(html);
		extractCode.setStartCodeSection("{{".toCharArray());
		extractCode.setEndCodeSection("}}".toCharArray());
		int startIndex = extractCode.getStartBraces(0);
		assertEquals(6, startIndex);
	}
	
	@Test
	public void testGetEndBrace() {
		ExtractCode extractCode = new ExtractCode(html);
		extractCode.setStartCodeSection("{{".toCharArray());
		extractCode.setEndCodeSection("}}".toCharArray());
		int endIndex = extractCode.getEndBraces(8);
		assertEquals(37, endIndex);
	}
	
	@Test
	public void testGetBetweenBraces() {
		ExtractCode extractCode = new ExtractCode(html);
		extractCode.setStartCodeSection("{{".toCharArray());
		extractCode.setEndCodeSection("}}".toCharArray());
		int startIndex = 0;
		while ((startIndex = extractCode.getStartBraces(startIndex)) >= 0) {
			assertEquals(6, startIndex);
			
			startIndex++;
		}
		assertEquals(-1, startIndex);
	}
	
	@Test
	public void testGetNestedStartBrace() {
		ExtractCode extractCode = new ExtractCode(nestedHtml);
		extractCode.setStartCodeSection("{{{".toCharArray());
		extractCode.setEndCodeSection("}}}".toCharArray());
		int startIndex = extractCode.getStartBraces(0);
		assertEquals(6, startIndex);
	}
	
	@Test
	public void testGetNestedEndBrace() {
		ExtractCode extractCode = new ExtractCode(nestedHtml);
		extractCode.setStartCodeSection("{{{".toCharArray());
		extractCode.setEndCodeSection("}}}".toCharArray());
		int endIndex = extractCode.getEndBraces(9);
		assertEquals(45, endIndex);
	}
	
	@Test
	public void testGetNestedEndBrace2() {
		ExtractCode extractCode = new ExtractCode(nestedHtml2);
		extractCode.setStartCodeSection("#{{".toCharArray());
		extractCode.setEndCodeSection("}}#".toCharArray());
		int endIndex = extractCode.getEndBraces(9);
		assertEquals(45, endIndex);
	}
	

}
