/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmlactions.common.zip;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import java.util.zip.ZipEntry;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.zip.ZipFileReader;
import org.xmlactions.common.zip.ZipFileWriter;

import junit.framework.TestCase;

/**
 * 
 * @author MichaelMurphy
 */
public class ZipFileWriterTest extends TestCase {

	public ZipFileWriterTest() {
	}

	public static void setUpClass() throws Exception {
	}

	public static void tearDownClass() throws Exception {
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	/**
	 * Test of setComment method, of class ZipFileWriter.
	 */
	@Test
	public void testSetComment() {
		try {
			String comment = "";
			ZipFileWriter instance = new ZipFileWriter(
					ZipFileTest.newZipFileName);
			instance.addFile(ZipFileTest.fileName, "file.txt", "a comment");
			instance.setComment("where does this comment go?");
			instance.closeZip();
			ZipFileReader zfr = new ZipFileReader(ZipFileTest.newZipFileName);
			assertEquals(1, zfr.getZipEntries().size());
			Vector<ZipEntry> entries = zfr.getZipEntries();
			ZipEntry ze = entries.get(0);
			assertEquals("a comment", ze.getComment());
			zfr.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	/**
	 * Test of closeZip method, of class ZipFileWriter.
	 */
	public void testCloseZip() throws FileNotFoundException, IOException {
		ZipFileWriter instance = new ZipFileWriter(ZipFileTest.newZipFileName);
		instance.addFile(ZipFileTest.fileName, "file.txt");
		instance.closeZip();
		try {
			instance.addFile(ZipFileTest.fileName, "file.txt");
		} catch (IOException ex) {
			// ok, expected this because the zip is closed.
		} catch (Throwable t) {
			fail(t.getMessage());
		}
	}

	/**
	 * Test of getZipOutputStream method, of class ZipFileWriter.
	 */
	public void testGetZipOutputStream() throws FileNotFoundException,
			IOException {
		ZipFileWriter instance = new ZipFileWriter(ZipFileTest.newZipFileName);
		instance.addFile(ZipFileTest.fileName, "file.txt", "a comment");
		assertNotNull(instance.getZipOutputStream());
		instance.closeZip();
	}

	/**
	 * Test of addFile method, of class ZipFileWriter.
	 */
	public void testAddFile() throws Exception {
		ZipFileWriter instance = new ZipFileWriter(ZipFileTest.newZipFileName);
		instance.addFile(ZipFileTest.fileName, "file.txt", "a comment");
		instance.closeZip();
		ZipFileReader zfr = new ZipFileReader(ZipFileTest.newZipFileName);
		assertEquals(1, zfr.getZipEntries().size());
		zfr.close();
	}

	/**
	 * Test of addFile method, of class ZipFileWriter.
	 */
	public void testAddInputStream() throws Exception {
		ZipFileWriter instance = new ZipFileWriter(ZipFileTest.newZipFileName);
	    File file = ResourceUtils.getFile(ZipFileTest.fileName);
	    FileUtils.writeStringToFile(file, "Some data added to the file");
		InputStream inputStream = new FileInputStream(file.getAbsolutePath());
		instance.addFile(inputStream, "file.txt", "a comment");
		instance.closeZip();
		ZipFileReader zfr = new ZipFileReader(ZipFileTest.newZipFileName);
		assertEquals(1, zfr.getZipEntries().size());
		zfr.close();
		inputStream.close();
	}

	/**
	 * Test of createFolder method, of class ZipFileWriter.
	 */
	public void testCreateFolder() throws Exception {
		ZipFileWriter instance = new ZipFileWriter(ZipFileTest.newZipFileName);
		instance.addFile(ZipFileTest.fileName, "file.txt", "a comment");
		instance.createFolder("aFolderName", System.currentTimeMillis(),
				"and this is a comment");
		instance.closeZip();
		ZipFileReader zfr = new ZipFileReader(ZipFileTest.newZipFileName);
		assertEquals(2, zfr.getZipEntries().size());
		zfr.close();
	}

	/**
	 * Test of startFile method, of class ZipFileWriter.
	 */
	public void testStartFile() throws Exception {
		ZipFileWriter instance = new ZipFileWriter(ZipFileTest.newZipFileName);
	    File file = ResourceUtils.getFile(ZipFileTest.fileName);
	    FileUtils.writeStringToFile(file, "Some data added to the file");
		InputStream inputStream = new FileInputStream(file.getAbsolutePath());

		instance.startFile("AFile", System.currentTimeMillis());
		instance.write(inputStream);
		instance.closeFile();
		instance.closeZip();

		inputStream.close();

		ZipFileReader zfr = new ZipFileReader(ZipFileTest.newZipFileName);
		assertEquals(1, zfr.getZipEntries().size());
		zfr.close();
	}

	/**
	 * Test of write method, of class ZipFileWriter.
	 */
	public void testWrite_InputStream() throws Exception {
		ZipFileWriter instance = new ZipFileWriter(ZipFileTest.newZipFileName);
	    File file = ResourceUtils.getFile(ZipFileTest.fileName);
	    FileUtils.writeStringToFile(file, "Some data added to the file");
		InputStream inputStream = new FileInputStream(file.getAbsolutePath());

		instance.startFile("AFile", System.currentTimeMillis());
		instance.write(inputStream);
		instance.closeFile();
		instance.closeZip();

		inputStream.close();

		ZipFileReader zfr = new ZipFileReader(ZipFileTest.newZipFileName);
		assertEquals(1, zfr.getZipEntries().size());
		zfr.close();

	}

	/**
	 * Test of write method, of class ZipFileWriter.
	 */
	public void testWrite_byteArr_int() throws Exception {
		ZipFileWriter instance = new ZipFileWriter(ZipFileTest.newZipFileName);

		instance.startFile("AFile", System.currentTimeMillis());
		instance.write("Hello World!!!".getBytes(),
				"Hello World!!!".getBytes().length);
		instance.closeFile();
		instance.closeZip();

		ZipFileReader zfr = new ZipFileReader(ZipFileTest.newZipFileName);
		assertEquals(1, zfr.getZipEntries().size());
		zfr.close();
	}

	/**
	 * Test of closeFile method, of class ZipFileWriter.
	 */
	public void testCloseFile() throws Exception {
		ZipFileWriter instance = new ZipFileWriter(ZipFileTest.newZipFileName);

		instance.startFile("AFile", System.currentTimeMillis());
		instance.write("Hello World!!!".getBytes(),
				"Hello World!!!".getBytes().length);
		instance.closeFile();
		instance.closeZip();

		ZipFileReader zfr = new ZipFileReader(ZipFileTest.newZipFileName);
		assertEquals(1, zfr.getZipEntries().size());
		zfr.close();
	}
}