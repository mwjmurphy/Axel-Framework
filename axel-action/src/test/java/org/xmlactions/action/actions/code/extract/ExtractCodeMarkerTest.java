package org.xmlactions.action.actions.code.extract;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ExtractCodeMarkerTest {

	private final String html = "<head>{{{this is where the code goes}}}";

	@Test
	public void testFirstMarker() {
		ExtractCode extractCode = new ExtractCode(html);
		int startMarker = extractCode.getStartBraces(0);
		int endMarker = extractCode.getEndBraces(startMarker+extractCode.getStartMarkerLength());
		ExtractCodeMarker extractCodeMarker = new ExtractCodeMarker(startMarker, endMarker, extractCode);
		String content = extractCodeMarker.getInnerContent();
		assertTrue(content.startsWith("this"));
		assertTrue(content.endsWith("goes"));
	}
}
