/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmlactions.common.zip;


import java.util.Vector;
import java.util.zip.ZipEntry;

import org.xmlactions.common.zip.ZipFileReader;

import junit.framework.TestCase;



/**
 *
 * @author MichaelMurphy
 */
public class ZipFileReaderTest extends TestCase{


   /**
    * Test of getFileList method, of class ZipFileReader.
    */
   public void testGetFileList()
   {
      try
      {
         ZipFileReader instance = new ZipFileReader(ZipperTest.getZipFile());
         Vector result = instance.getFileList();
         instance.close();
         assertEquals(5, result.size());
      }
      catch (Exception ex)
      {
         fail(ex.getMessage());
      }
   }

   /**
    * Test of getZipEntries method, of class ZipFileReader.
    */
   public void testGetZipEntries()
   {
      try
      {
         ZipFileReader instance = new ZipFileReader(ZipperTest.getZipFile());
         Vector result = instance.getZipEntries();
         instance.close();
         assertEquals(5, result.size());
      }
      catch (Exception ex)
      {
         fail(ex.getMessage());
      }
   }

   /**
    * Test of extractFile method, of class ZipFileReader.
    */
   public void testExtractFile_String_String() throws Exception
   {
      try
      {
         ZipFileReader instance = new ZipFileReader(ZipperTest.getZipFile());
         String source = "file1.txt";
         String target = "resources/temp/out.file1.txt";
         instance.extractFile(source, target);
         instance.close();
      }
      catch (Exception ex)
      {
         fail(ex.getMessage());
      }
   }

   /**
    * Test of extractFile method, of class ZipFileReader.
    */
   public void testExtractFile_ZipEntry_String() throws Exception
   {
      try
      {
         ZipFileReader instance = new ZipFileReader(ZipperTest.getZipFile());
         String source = "file1.txt";
         ZipEntry ze = instance.getEntry(source);
         String target = "resources/temp/out.file2.txt";
         instance.extractFile(ze, target);
         instance.close();
      }
      catch (Exception ex)
      {
         fail(ex.getMessage());
      }
   }

}