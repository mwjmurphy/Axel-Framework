package org.xmlactions.common.zip;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.theme.Theme;

/**
 *
 * @author MMURPHY
 */
public class Zipper
{

	private static final Logger log = LoggerFactory.getLogger(Zipper.class);

   /** Creates a new instance of Zipper */
   public Zipper()
   {
   }
   
   /**
    * Checks if a file is a zip file or not
    * @param file is the file to check
    * @return true if is a zip file else false
    */
   public static boolean isZip(File file)
   {
      ZipFileReader zipFileReader = null;
      try
      {
         zipFileReader = new ZipFileReader(file.getAbsolutePath());
         if (zipFileReader.size() > 0)
         {
            return(true);
         }
      }
      catch(Exception ex)
      {
         return(false);
      }
      finally
      {
         if (zipFileReader != null)
         {
            try
            {
               zipFileReader.close();
            }
            catch(Exception ex){}
                    
         }
      }
      return(false);
   }
   /**
    * Returns the number of files in a zip file
    * @param file is the file to check
    * @return number of files in the zip file or 0 if none or not zip file
    */
   public int getFileCount(File file)
   {
      ZipFileReader zipFileReader = null;
      try
      {
         zipFileReader = new ZipFileReader(file.getAbsolutePath());
         return(zipFileReader.size());
      }
      catch(Exception ex)
      {
         return(0);
      }
      finally
      {
         if (zipFileReader != null)
         {
            try
            {
               zipFileReader.close();
            }
            catch(Exception ex){}
                    
         }
      }
   }
   /**
    * Returns the total compressed size of all files in the zip file
    * @param file is the file to check
    * @return the total compressed size of all files in the zip file or 0 if something wrong
    */
   public int getCompressedFileSizes(File file)
   {
      ZipFileReader zipFileReader = null;
      int size = 0;
      try
      {
         zipFileReader = new ZipFileReader(file.getAbsolutePath());
         Vector entries = zipFileReader.getZipEntries();
         for (int iLoop = 0; iLoop < entries.size(); iLoop++)
         {
            ZipEntry ze = (ZipEntry)entries.get(iLoop);
            size += ze.getCompressedSize();
         }
         return(size);
      }
      catch(Exception ex)
      {
         return(0);
      }
      finally
      {
         if (zipFileReader != null)
         {
            try
            {
               zipFileReader.close();
            }
            catch(Exception ex){}
                    
         }
      }
   }
   /**
    * Returns the total uncompressed size of all files in the zip file
    * @param file is the file to check
    * @return the total uncompressed size of all files in the zip file or 0 if something wrong
    */
   public int getUncompressedFileSizes(File file)
   {
      ZipFileReader zipFileReader = null;
      int size = 0;
      try
      {
         zipFileReader = new ZipFileReader(file.getAbsolutePath());
         Vector entries = zipFileReader.getZipEntries();
         for (int iLoop = 0; iLoop < entries.size(); iLoop++)
         {
            ZipEntry ze = (ZipEntry)entries.get(iLoop);
            size += ze.getSize();
         }
         return(size);
      }
      catch(Exception ex)
      {
         return(0);
      }
      finally
      {
         if (zipFileReader != null)
         {
            try
            {
               zipFileReader.close();
            }
            catch(Exception ex){}
                    
         }
      }
   }

   public byte [] compress(byte [] inputData) throws IOException
   {
      // Create the compressor with highest level of compression
      Deflater compressor = new Deflater();
      compressor.setLevel(Deflater.BEST_COMPRESSION);

      // Give the compressor the data to compress
      compressor.setInput(inputData);
      compressor.finish();

      // Create an expandable byte array to hold the compressed data.
      // You cannot use an array that's the same size as the orginal because
      // there is no guarantee that the compressed data will be smaller than
      // the uncompressed data.
      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      // Compress the data
      byte[] buf = new byte[1024];
      while (!compressor.finished())
      {
         int count = compressor.deflate(buf);
         bos.write(buf, 0, count);
      }
      try
      {
         bos.close();
      }
      catch (IOException e)
      {
         throw e;
      }

      // Get the compressed data
      byte[] compressedData = bos.toByteArray();

      return(compressedData);
   }

   /**
    * expand a compressed data byte array to it's original form.
    *
    * @param compressedData is the compressedData to expand
    * @throws Exception
    */
   public byte [] expand(byte [] compressedData) throws DataFormatException, IOException
   {
      // Create the decompressor and give it the data to compress
      Inflater decompressor = new Inflater();
      decompressor.setInput(compressedData);

      // Create an expandable byte array to hold the decompressed data
      ByteArrayOutputStream bos = new ByteArrayOutputStream(compressedData.length);

      // Decompress the data
      byte[] buf = new byte[1024];
      while (!decompressor.finished())
      {
         try
         {
            int count = decompressor.inflate(buf);
            bos.write(buf, 0, count);
         }
         catch (DataFormatException e)
         {
            throw e;
         }
      }
      try
      {
         bos.close();
      }
      catch (IOException e)
      {
         throw e;
      }

      // Get the decompressed data
      byte[] decompressedData = bos.toByteArray();
      return(decompressedData);
   }
}
