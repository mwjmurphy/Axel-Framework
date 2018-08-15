package org.xmlactions.db.actions;

import org.xmlactions.db.actions.Binary;

import junit.framework.TestCase;

public class BinaryTest extends TestCase {
	
	public void testIsTrue() {
		Binary binary = new Binary();
		binary.setPattern("false/true");
		boolean isTrue = binary.isTrue("false");
		assertFalse(isTrue);
		isTrue = binary.isTrue("true");
		assertTrue(isTrue);
		isTrue = binary.isTrue("True");
		assertTrue(isTrue);
	}

	
}
