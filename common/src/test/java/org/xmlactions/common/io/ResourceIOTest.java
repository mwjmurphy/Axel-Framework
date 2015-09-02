package org.xmlactions.common.io;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ResourceIOTest {

	@Test
	public void testLoadResource() {
		String name = "/test/files/file.txt";
		String content = ResourceIO.loadFileOrResource(this.getClass(), name);
		assertNotNull(content);
	}
}
