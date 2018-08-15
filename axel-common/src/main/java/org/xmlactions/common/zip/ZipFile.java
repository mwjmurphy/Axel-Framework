/*
 * ZipFile.java
 *
 * Created on 18 February 2006, 11:54
 *
 * Use this class for working on a supposed dual mode of read and write zip file.
 */

package org.xmlactions.common.zip;


import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.zip.ZipEntry;


import org.apache.commons.io.IOExceptionWithCause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.system.JS;
import org.xmlactions.common.theme.Theme;

/**
 *
 * @author mike
 */
public class ZipFile
{
	
	private static final Logger log = LoggerFactory.getLogger(ZipFile.class);
	
	ZipFileWriter zipWriter = null;
	String sourceZipFileName = null;
	/**
	 * Creates a new instance of ZipFile
	 */
	public ZipFile(String sourceZipFileName)
	throws Exception
	{
		openZip(sourceZipFileName, false);
	}
	/**
	 * Open a new or existing zip file.
	 * If source zip file exists then we first need to copy all the files in this zip
	 * file to a new zip file (which we rename at the end).
	 * Will leave the new zip file open for addition of other files, you must call ZipFile.close()
	 * to close the zip file, which will rename the zip file if necessary.
	 * 
	 * @param sourceZipFileName is the name of a new or existing zip file.
	 * @param createNewFile if set true will overwrite any existing file
	 * @throws Exception on detection of an error
	 */ 
	public ZipFile(String sourceZipFileName, boolean createNewFile)
	throws Exception
	{
		openZip(sourceZipFileName, createNewFile);
	}

	/**
	 * Open a new or existing zip file.
	 * If source zip file exists then we first need to copy all the files in this zip
	 * file to a new zip file (which we rename at the end).
	 * Will leave the new zip file open for addition of other files, you must call ZipFile.close()
	 * to close the zip file, which will rename the zip file if necessary.
	 * 
	 * @param sourceZipFileName is the name of a new or existing zip file.
	 * @param createNewFile if set true will overwrite any existing file
	 * @throws Exception on detection of an error
	 */ 
	public void openZip(String sourceZipFileName, boolean createNewFile)
	throws Exception
	{
		this.sourceZipFileName = sourceZipFileName;
		File file = new File(sourceZipFileName);
		if (file.isDirectory() == true)
		{
			throw new Exception ("'" + sourceZipFileName + "' is not a zip file, it's a directory path");
		}
		if (createNewFile == false && file.exists())
		{
			log.debug(JS.getCurrentMethodName_static() + " add to existing ZipFile:" + file.getAbsolutePath());
			File fPath = null;
			String path = file.getParent();
			if (path != null)
			{
				fPath = new File(path);
			}
			file = File.createTempFile("riostl", ".jar", fPath);
			//log.debug("[ZipFile] created temp file '" + file.getAbsolutePath() + "'");
			zipWriter = new ZipFileWriter(file.getAbsolutePath());
			// now copy files from old zip file to new zip file.
			//log.debug("zipReader: sourceFileName:" + sourceZipFileName);
			ZipFileReader zipReader = null;
			try {
				zipReader = new ZipFileReader(sourceZipFileName);
			} catch (Exception ex) {
                throw new IOExceptionWithCause("Error creating ZipFileReader for [" + sourceZipFileName + "]. "
                        + ex.getMessage(), ex);
			}
			copyFiles(zipReader, zipWriter);
			zipReader.close();
		}
		else
		{
			log.debug(JS.getCurrentMethodName_static() + " create new ZipFile:" + file.getAbsolutePath());
			zipWriter = new ZipFileWriter(sourceZipFileName);
		}
	}

	/**
	 * @param pathAndFileName is the location of the file to insert into the zip file
	 * @param entryName is an optional name that will be used instead of the pathAndFileName
	 *                  if it's not null
	 */
	public void addFile(String pathAndFileName, String entryName)
	throws Exception
	{
		addFile(pathAndFileName, entryName, null);
	}

	/**
	 * @param pathAndFileName is the location of the file to insert into the zip file
	 * @param entryName is an optional name that will be used instead of the pathAndFileName
	 *                  if it's not null
	 * @param comment - will be added to the ZipEntry if not null
	 */
	public void addFile(String pathAndFileName, String entryName, String comment)
	throws Exception
	{
		if (zipWriter == null)
		{
			throw new Exception ("[Zipper] unable to add file '" + pathAndFileName + "' to zip file.  Zip file does not exist or is not open!");
		}
		zipWriter.addFile(pathAndFileName, entryName, comment);
	}

	/**
	 * Must call this when zipping is completed.
	 */
	public void closeZip()
	throws Exception
	{
		zipWriter.closeZip();
		if (! zipWriter.zipFileName.equals(this.sourceZipFileName))
		{
			// rename zip file to sourceZipFileName
			File file1 = new File(this.sourceZipFileName);
			if (file1.exists() == true)
			{
				if (file1.delete() == false)
				{
					throw new Exception ("[Zipper] unable to delete file '" + file1.getAbsolutePath() + "'");
				}
			}
			File file2 = new File(zipWriter.zipFileName);
			if (file2.renameTo(file1) == false)
			{
				// failed to rename file...
				throw new Exception("[Zipper] unable to rename '" + zipWriter.zipFileName + "' to '" + this.sourceZipFileName + "'");
			}
			file2 = new File(zipWriter.zipFileName);
			if (file2.delete() == false)
			{
				//throw new Exception ("[ZipFile] unable to delete tmp file '" + file2.getAbsolutePath() + "'");
			}
		}
	}

	public static void copyFiles(ZipFileReader zr, ZipFileWriter zw) throws IOException
	{
		Vector zipEntries = zr.getZipEntries();
		for (int iLoop = 0 ; iLoop < zipEntries.size(); iLoop++)
		{
			ZipEntry ze = (ZipEntry)zipEntries.get(iLoop);
			//log.debug("copying file:" + ze.getName());
			zw.addFile(zr.getInputStream(ze), ze.getName(), ze.getComment());
		}
	}

}
