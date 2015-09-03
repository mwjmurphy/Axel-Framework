/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

/*
 * ZipFileExtras.java
 *
 * Created on 23 July 2007, 12:30
 *
 * These zip utils are used for adding and removing files from existing zip files.
 */
package org.xmlactions.common.zip;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import java.util.zip.ZipEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.io.FileUtils;
import org.xmlactions.common.system.JS;
import org.xmlactions.common.theme.Theme;


/**
 *
 * @author mike
 */
public class ZipFileExtras
{

	private static final Logger log = LoggerFactory.getLogger(ZipFileExtras.class);
	
   /** Creates a new instance of ZipFileExtras */
   public ZipFileExtras()
   {
      //
   }


   /**
    * Add files to an existing zip file.
    * @param sourceZipFileName is the existing zip file name
    * @param files is an array of files that we want to add to the existing zip file
    * @param talker is the CallBack.
    * @throws IOException
    */
   public void addFilesToExistingZip(String sourceZipFileName, String[] files) throws IOException
   {
      File sourceZipFile = new File(sourceZipFileName);
      File tempFile;
      ZipFileReader zipFileReader = null;
      ZipFileWriter zipFileWriter = null;
      if (sourceZipFile.length() != 0)
      {
         zipFileReader = new ZipFileReader(sourceZipFileName);
      }
      tempFile = FileUtils.getTempFile(sourceZipFile);
      log.debug("tempFile:" + tempFile.getName());
      zipFileWriter = new ZipFileWriter(tempFile.getAbsolutePath());
      if (sourceZipFile.length() != 0)
      {
         this.deleteFilesFromExistingZip(zipFileReader, zipFileWriter, files);
      }
      this.addFilesToExistingZip(zipFileWriter, files);
      zipFileWriter.closeZip();
      zipFileReader.close();

      FileUtils.rename(tempFile, sourceZipFile);
   }

   /**
    * Add files to an existing zip file.
    * @param zipFile is the existing zip file
    * @param files is an array of files that we want to add to the existing zip file
    * @throw if something goes wrong an exception is thrown.
    */
   private void addFilesToExistingZip(ZipFileWriter zipFileWriter, String[] files) throws FileNotFoundException, IOException
   {
      byte[] buf = new byte[1024];

      // Compress the files
      for (int i = 0; i < files.length; i++)
      {
         File file = new File(files[i]);
         InputStream in = new FileInputStream(files[i]);
         zipFileWriter.startFile(files[i], file.lastModified());
         zipFileWriter.write(in);
         in.close();
      }
   }

   /**
    * Delete files to an existing zip file.  The zipFileWriter is left open so
    * you can add additional files if you need.  You MUST call 
    * this.zipFileWriter.closeZip() when you are finished with the new zip file.
    * @param zipFile is the existing zip file
    * @param files is an array of names that we want to add to the existing zip file
    * @throws IOException
    */
   public void deleteFilesFromExistingZip(File zipFile, String[] files) throws IOException
   {
      ZipFileReader zipFileReader = null;
      ZipFileWriter zipFileWriter = null;
      if (zipFile.length() == 0)
      {
         return;
      }
      File tempFile = FileUtils.getTempFile(zipFile);
      zipFileReader = new ZipFileReader(zipFile.getAbsolutePath());
      zipFileWriter = new ZipFileWriter(tempFile.getAbsolutePath());

      boolean result = this.deleteFilesFromExistingZip(zipFileReader, zipFileWriter, files);
      zipFileReader.close();
      zipFileWriter.closeZip();
      FileUtils.rename(tempFile, zipFile);
   }

   /**
    * Delete files to from existing zip file.
    * @param zipFile is the existing zip file
    * @param files is an array of names that we want to add to the existing zip file
    * @throw if something goes wrong an exception is thrown.
    * @return true if all files were deleted else false is user aborted
    */
   private boolean deleteFilesFromExistingZip(ZipFileReader zipFileReader, ZipFileWriter zipFileWriter, String[] files) throws IOException
   {
      // Log.getInstance().debug(JS.getCurrentMethodName_static() + " deleteFilesFromExistingZip");
      Vector zipEntries = zipFileReader.getZipEntries();

      byte[] buf = new byte[1024];

      for (int iLoop = 0; iLoop < zipEntries.size(); iLoop++)
      {
         ZipEntry ze = (ZipEntry) zipEntries.get(iLoop);
         // Log.getInstance().debug(JS.getCurrentMethodName_static() + " file:" + ze.getName() + " comment:" + ze.getComment());
         String name = ze.getName();
         if (JS.isPathSeparatorChar(name.charAt(name.length() - 1)) == true)
         {
            name = name.substring(0, name.length() - 1);
         }
         boolean notInFiles = true;
         for (int fLoop = 0; fLoop < files.length; fLoop++)
         {
            String delName = files[fLoop];
            if (delName.endsWith("\\") || delName.endsWith("/"))
            {
               delName = delName.substring(0, delName.length() - 1);
               // Log.getInstance().debug(JS.getCurrentMethodName_static() + " comparing names - folder:" + delName + " zip:" + name);
               if (name.startsWith(delName) &&
                       name.length() > delName.length() &&
                       (name.charAt(delName.length()) == '\\' ||
                       name.charAt(delName.length()) == '/'))
               {
                  notInFiles = false;
                  break;
               }
            }
            else
            {
               // Log.getInstance().debug(JS.getCurrentMethodName_static() + " comparing names - file:" + delName + " zip:" + name);
               if (delName.equals(name))
               {
                  notInFiles = false;
                  break;
               }
            }
         }
         if (notInFiles)
         {
            // Add ZIP entry to output stream.
            if (ze.isDirectory())
            {
               zipFileWriter.createFolder(name, ze.getTime(), ze.getComment());
            }
            else
            {
               ZipEntry zeout = new ZipEntry(name);
               zeout.setTime(ze.getTime());
               zeout.setComment(ze.getComment());
               zeout.setExtra(ze.getExtra());
               zipFileWriter.getZipOutputStream().putNextEntry(zeout);
               // Transfer bytes from the ZIP file to the output file
               InputStream is = zipFileReader.getInputStream(ze);
               zipFileWriter.write(is);
               is.close();
               zipFileWriter.closeFile();
               zipFileWriter.getZipOutputStream().closeEntry();
            }
         }
         else  // we're deleting this file
         {
         }
      }
      // Complete the ZIP file
      return (true);
   }
}


