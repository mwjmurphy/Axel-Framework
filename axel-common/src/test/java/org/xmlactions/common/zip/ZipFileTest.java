/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmlactions.common.zip;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.zip.ZipFile;
import org.xmlactions.common.zip.ZipFileReader;
import org.xmlactions.common.zip.ZipFileWriter;



/**
 *
 * @author MichaelMurphy
 */
public class ZipFileTest {

   public static String newZipFileName = "target/newzipfile.zip";
   public static String fileName = "/test/files/file.txt";


   /**
    * Test of openZip method, of class ZipFile.
    */
   @Test
   public void testOpenZip() throws Exception
   {
      boolean createNewFile = false;
      ZipFile instance = new ZipFile(ZipperTest.getZipFile());
      assertNotNull(instance);
      instance.closeZip();
   }

   /**
    * Test of addFile method, of class ZipFile.
    */
   @Test
   public void testAddFile() throws Exception
   {
      ZipFile instance = new ZipFile(newZipFileName, true);
      File file = ResourceUtils.getFile(fileName);
      instance.addFile(file.getAbsolutePath(), "file.txt1");
      instance.closeZip();
   }

   /**
    * Test of closeZip method, of class ZipFile.
    */
   @Test
   public void testCloseZip() throws Exception
   {
      ZipFile instance = new ZipFile(newZipFileName, false);
      instance.closeZip();
   }

   /**
    * Test of copyFiles method, of class ZipFile.
    */
   @Test
   public void testCopyFiles() throws Exception
   {
      ZipFileReader zr = new ZipFileReader(ZipperTest.getZipFile());
      ZipFileWriter zw = new ZipFileWriter(newZipFileName);
      ZipFile.copyFiles(zr, zw);
      zr.close();
      zw.closeZip();
   }
}