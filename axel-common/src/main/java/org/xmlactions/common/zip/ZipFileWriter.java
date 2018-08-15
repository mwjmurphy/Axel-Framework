/*
 * ZipFileWriter.java
 *
 * Created on 07 February 2006, 11:24
 */
 
package org.xmlactions.common.zip;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.xmlactions.common.io.ResourceUtils;



/**
 *
 * @author mike
 *
 * Used for writing files to a zip file.
 * Call
 */
public class ZipFileWriter
{
   ZipOutputStream zipOutputStream;
   String zipFileName;
   
   public ZipFileWriter(String fileName) throws FileNotFoundException
   {
      zipOutputStream = new ZipOutputStream(new FileOutputStream(fileName, false));
      zipFileName = fileName;
   }

   /**
    * Sets a comment to the zipOutputStream.  Not sure what use this is as we cant get it back.
    * TODO how to read back the comment
    * @param comment
    */
   public void setComment(String comment)
   {
      this.zipOutputStream.setComment(comment);
   }
   
   /**
    * Close the zip file.  Once this is called we cant add any more files.
    */
   public void closeZip() throws IOException
   {
      zipOutputStream.close();
   }
   
   public ZipOutputStream getZipOutputStream()
   {
      return(this.zipOutputStream);
   }
   
   /**
    * @param pathAndFileName is the location of the file to insert into the zip file
    * @param entryName is an optional name that will be used instead of the pathAndFileName
    *                  if it's not null
    */
   public void addFile(String pathAndFileName, String entryName) throws FileNotFoundException, IOException
   {
      addFile(pathAndFileName, entryName, null);
   }
   /**
    * @param pathAndFileName is the location of the file to insert into the zip file
    * @param entryName is an optional name that will be used instead of the pathAndFileName
    *                  if it's not null
    */
   public void addFile(String pathAndFileName, String entryName, String comment) throws FileNotFoundException, IOException
   {
      // These are the files to include in the ZIP file
      
      // Create a buffer for reading the files
      byte[] buf = new byte[1024];
 
      // might be a resource or a file
      File file = new File(pathAndFileName);
      InputStream in;
      if (file.exists()) {
    	  in = new FileInputStream(pathAndFileName);
      } else {
    	  in = ResourceUtils.getResourceURL(pathAndFileName).openStream();
      }
      
      // Add ZIP entry to output stream.
      ZipEntry ze = null;
      if (entryName == null)
      {
         ze = new ZipEntry(pathAndFileName);
      }
      else
      {
         ze = new ZipEntry(entryName);
      }
      if (comment != null)
      {
         ze.setComment(comment);
      }
      zipOutputStream.putNextEntry(ze);

      
      // Transfer bytes from the file to the ZIP file
      int len;
      while ((len = in.read(buf)) > 0)
      {
         this.zipOutputStream.write(buf, 0, len);
      }
      
      // Complete the entry
      this.zipOutputStream.closeEntry();
      in.close();
   }
   
   /**
    * @param in - is an InputStream created from the ZipEntry
    * @param entryName - is the full path and file name
    */
   public void addFile(InputStream in, String entryName) throws IOException
   {
      addFile(in, entryName, null);
   }

   /**
    * @param in - is an InputStream created from the ZipEntry
    * @param entryName - is the full path and file name
    * @param comment - will be added to the ZipEntry if not null
    */
   public void addFile(InputStream in, String entryName, String comment) throws IOException
   {
      // These are the files to include in the ZIP file
      
      // Create a buffer for reading the files
      byte[] buf = new byte[1024];
      
      //FileInputStream in = new FileInputStream(pathAndFileName);
      
      // Add ZIP entry to output stream.
      ZipEntry ze = new ZipEntry(entryName);
      if (comment != null)
      {
         ze.setComment(comment);
      }
      zipOutputStream.putNextEntry(new ZipEntry(entryName));
      
      // Transfer bytes from the file to the ZIP file
      int len;
      while ((len = in.read(buf)) > 0)
      {
         this.zipOutputStream.write(buf, 0, len);
      }
      
      // Complete the entry
      this.zipOutputStream.closeEntry();
      in.close();
   }
   
   /**
    * Creates a folder in the zip file
    *
    * @param entryName is the full path and file name
    * @param time is the source creation time
    * @param comment if not null will be added to the folder.
    *
    */
   public void createFolder(String entryName, long time, String comment) throws IOException
   {
      // Add ZIP entry to output stream.
      if (entryName != null && entryName.length() > 0 && entryName.charAt(entryName.length()-1) != '/')
      {
         entryName += "/";
      }
      ZipEntry ze = new ZipEntry(entryName);
      ze.setTime(time);
      if (comment != null)
      {
         ze.setComment(comment);
      }
      zipOutputStream.putNextEntry(ze);
      zipOutputStream.closeEntry();
   }
   
   /**
    * Opens a zip file entry ready for writing
    *
    * @param entryName is the full path and file name
    *
    */
   public void startFile(String entryName, long time) throws IOException
   {
      // Add ZIP entry to output stream.
      ZipEntry ze = new ZipEntry(entryName);
      ze.setTime(time);
      zipOutputStream.putNextEntry(ze);
   }
   
   /**
    * Write data from the InputStream to the zip file entry opened from
    * startFile(...)
    * @param in is an InputStream created from the ZipEntry
    */
   public void write(InputStream in) throws IOException
   {
      // These are the files to include in the ZIP file
      
      // Create a buffer for reading the files
      byte[] buf = new byte[64000];
      
      
      // Transfer bytes from the file to the ZIP file
      int len;
      while ((len = in.read(buf)) > 0)
      {
         this.zipOutputStream.write(buf, 0, len);
      }
      in.close();
   }
   /**
    * Write the buf data to the zip file entry opened from
    * startFile(...)
    * @param buf is the data to write
    * @param size is the size of the buf
    */
   public void write(byte [] buf, int size) throws IOException
   {
      this.zipOutputStream.write(buf, 0, size);
   }
   
   /**
    * Close the zip file entry opened from startFile(...)
    */
   public void closeFile() throws IOException
   {
      // Complete the entry
      this.zipOutputStream.closeEntry();
   }

}
