/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmlactions.common.zip;



import java.io.File;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.zip.Zipper;

import junit.framework.TestCase;

/**
 * 
 * @author MichaelMurphy
 */
public class ZipperTest extends TestCase {
	
	private static final Logger logger = LoggerFactory.getLogger(ZipperTest.class);

	private static String zipFile = "/test/files/files.zip";
	private static byte[] inputData = "this is the input data1111111111111111122222222222222222222222233333333333333333"
			.getBytes();

	/**
	 * Test of isZip method, of class Zipper.
	 * @throws FileNotFoundException 
	 */
	public void testIsZip() throws FileNotFoundException {
		File file = new File(getZipFile());
		logger.debug("file:" + file.getAbsolutePath());
		
		assertTrue(file.exists() && file.isFile());
		assertTrue(Zipper.isZip(file));
		file = new File("nosuchfile.zip");
		assertFalse(Zipper.isZip(file));
	}

	/**
	 * Test of getFileCount method, of class Zipper.
	 * @throws FileNotFoundException 
	 */
	public void testGetFileCount() throws FileNotFoundException {
		File file = new File(getZipFile());
		Zipper instance = new Zipper();
		assertEquals(5, instance.getFileCount(file));
	}

	/**
	 * Test of getCompressedFileSizes method, of class Zipper.
	 * @throws FileNotFoundException 
	 */
	public void testGetCompressedFileSizes() throws FileNotFoundException {
		File file = new File(getZipFile());
		Zipper instance = new Zipper();
        assertEquals(202, instance.getCompressedFileSizes(file));
	}

	/**
	 * Test of getUncompressedFileSizes method, of class Zipper.
	 * @throws FileNotFoundException 
	 */
	public void testGetUncompressedFileSizes() throws FileNotFoundException {
		File file = new File(getZipFile());
		Zipper instance = new Zipper();
        assertEquals(205, instance.getUncompressedFileSizes(file));
	}

	/**
	 * Test of compress method, of class Zipper.
	 */
	public void testCompressExpand() throws Exception {
		Zipper instance = new Zipper();
		byte[] compressed = instance.compress(inputData);
		byte[] expanded = instance.expand(compressed);
		boolean matches = true;
		for (int i = 0 ; i < inputData.length; i++) {
			if (inputData[i] != expanded[i]) {
				matches = false;
				break;
			}
		}
		assertTrue("Compressed to Expanded data does not match input", matches);
	}
	
	public static String getZipFile() throws FileNotFoundException {
		String filePath = ResourceUtils.getFile(zipFile).getAbsolutePath();
		return filePath;
	}

}