/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmlactions.common.io;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.xmlactions.common.io.FileUtils;


/**
 * 
 * @author MichaelMurphy
 */
public class FileUtilsTest {
	private static String newFileName = "bin/temp/file.txt";

	public FileUtilsTest() {
	}

	private File createTempFile() throws FileNotFoundException, IOException {
		File file = new File(newFileName);
		if (file.exists()==false) {
			org.apache.commons.io.FileUtils.writeStringToFile(file, "delete this file");
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write("this is a temp file".getBytes());
		fos.close();
		return file;

	}

	/**
	 * Test of getTempFile method, of class FileUtils.
	 */
	@Test
	public void testGetTempFile() throws Exception {
		File file = this.createTempFile();
		File tempFile = FileUtils.getTempFile(file);

		assertTrue(tempFile.getName().toLowerCase().endsWith(".tmp"));

		file.delete();
		tempFile.delete();
	}

	/**
	 * Test of rename method, of class FileUtils.
	 */
	@Test
	public void testRename() throws Exception {
		File from = this.createTempFile();
		File to = FileUtils.getTempFile(from);
		FileUtils.rename(from, to);
		assertFalse(from.exists());
		assertTrue(to.exists());

		// new test can we rename to an already existing file.
		from = this.createTempFile();
		FileUtils.rename(from, to);
		assertFalse(from.exists());
		assertTrue(to.exists());
		to.delete();

	}
}