/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmlactions.common.zip;



import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;
import org.xmlactions.common.zip.ZipFile;
import org.xmlactions.common.zip.ZipFileExtras;
import org.xmlactions.common.zip.ZipFileReader;
import org.xmlactions.common.zip.ZipFileWriter;

import junit.framework.TestCase;


/**
 * 
 * @author MichaelMurphy
 */
public class ZipFileExtrasTest extends TestCase {

	private static String newFileName = "/test/files/addfile.txt";

	public ZipFileExtrasTest() {
	}


	/**
	 * Creates a zip file by copying the existing test zip 'files.zip'
	 * 
	 * @throws java.io.IOException
	 */
	private void createZipFile() throws IOException {
		ZipFileReader zr = new ZipFileReader(ResourceUtils.getFile(ZipperTest.getZipFile()));
		ZipFileWriter zw = new ZipFileWriter(ResourceUtils.getFile(ZipFileTest.newZipFileName).getAbsolutePath());
		ZipFile.copyFiles(zr, zw);
		zr.close();
		zw.closeZip();

	}

	/**
	 * Test of addFilesToExistingZip method, of class ZipFileExtras.
	 */
	public void testAddFilesToExistingZip() throws IOException {
		createZipFile();
		ZipFileExtras instance = new ZipFileExtras();
	    File file = ResourceUtils.getFile(ZipFileTest.fileName);
	    FileUtils.writeStringToFile(file, "Some data added to the file");
		String[] files = new String[1];
		//files[0] = ResourceUtils.getFile(ResourceUtils.getURL(this.newFileName)).getAbsolutePath();
		files[0] = file.getAbsolutePath();
		instance.addFilesToExistingZip(ZipFileTest.newZipFileName, files);
		// TODO validate that file has been added
		ZipFileReader zfr = new ZipFileReader(ZipFileTest.newZipFileName);
		assertEquals(6, zfr.getZipEntries().size());
		zfr.close();

	}

	/**
	 * Test of deleteFilesFromExistingZip method, of class ZipFileExtras.
	 */
	public void testDeleteFilesFromExistingZip() throws Exception {
		createZipFile();
	    File file = ResourceUtils.getFile(ZipFileTest.fileName);
	    FileUtils.writeStringToFile(file, "Some data added to the file");
		String[] files = new String[1];
		//files[0] = ResourceUtils.getFile(this.newFileName).getAbsolutePath();
		files[0] = file.getAbsolutePath();
		ZipFileExtras instance = new ZipFileExtras();

		instance.addFilesToExistingZip(ZipFileTest.newZipFileName, files);
		ZipFileReader zfr = new ZipFileReader(ZipFileTest.newZipFileName);
		assertEquals(6, zfr.getZipEntries().size());
		zfr.close();

		instance.deleteFilesFromExistingZip(
				new File(ZipFileTest.newZipFileName), files);
		zfr = new ZipFileReader(ZipFileTest.newZipFileName);
		assertEquals(5, zfr.getZipEntries().size());
		zfr.close();
	}
}