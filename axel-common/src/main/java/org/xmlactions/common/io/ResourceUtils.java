
package org.xmlactions.common.io;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Properties;





import org.apache.commons.lang.Validate;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.xmlactions.common.system.Memory;
import org.xmlactions.common.util.DateTime;


//import java.io.

public class ResourceUtils
{

	public final static String DEFAULT_ISO = "ISO-8859-1";

	public static String loadFile(String fileName) throws IOException
	{

		return (new String(loadBinaryFile(fileName)));
	}

	/**
	 * Load a file and apply the iso value when doing the byte to String
	 * conversion
	 * 
	 * @throws IOException
	 */
	public static String loadFileWithISO(String fileName, String iso) throws IOException
	{

		// String decoded = new String(origBytes, "ISO-8859-1");

		byte[] inputData = loadBinaryFile(fileName);
		if (inputData == null) {
			return (""); // file has no data.
		}
		// String s = new String("billy", iso);
		return (new String(inputData, iso));
	}

	/**
	 * This will load a file assuming it's an XML file, then look for an ISO
	 * declaration and if found will convert the string using that ISO value.
	 * i.e. <?xml version="1.0" encoding="ISO-8859-1"?> will find ISO-9959-1 and
	 * convert the binary data to a String using this iso.
	 * 
	 * @throws IOException
	 */
	public static String loadXMLFileWithISO(String fileName) throws IOException
	{

		// String decoded = new String(origBytes, "ISO-8859-1");

		byte[] inputData = loadBinaryFile(fileName);
		if (inputData == null) {
			return (""); // file has no data.
		}
		String iso = null;
		String s = new String(inputData);
		int encodingIndex = s.indexOf("encoding");
		if (encodingIndex > 0) {
			int isoIndex = s.indexOf("ISO-");
			if (isoIndex < 0) {
				isoIndex = s.indexOf("iso-");
			}
			if (isoIndex < 0) {
				return (s);
			}
			int isoEnd = 0;
			if (isoIndex > encodingIndex) {
				// isoIndex is where the ISO starts, now get the end
				for (int i = isoIndex; i < s.length(); i++) {
					if (s.charAt(i) == ' ' || s.charAt(i) == '\"') {
						isoEnd = i;
						break;
					}
				}
			}
			if (isoEnd > isoIndex && isoEnd < (isoIndex + 20)) {
				iso = s.substring(isoIndex, isoEnd);
				// Log.getInstance().debug(JS.getCurrentMethodName_static() +
				// " iso:" + iso);
			}
		}
		if (iso == null) {
			return (s);
		} else {
			return (new String(inputData, iso));
		}
	}

	public static byte[] loadBinaryFile(String fileName) throws IOException
	{

		byte buffer[] = null;
		InputStream inputStream;
		if (new File(fileName).exists() == true) {
			inputStream = new FileInputStream(fileName);
		} else {
			// see if we can get it as a resource
			inputStream = ResourceUtils.getResourceURL(fileName).openStream();
		}
		if (inputStream != null) {
			try {
				buffer = readInputStream(inputStream);
			} finally {
				inputStream.close();
			}
		} else {

			throw new FileNotFoundException("File '" + fileName + "' does not exist");

		}
		return (buffer);
	}

	public static void saveFile(String fileName, String buffer) throws IOException
	{

		saveBinaryFile(fileName, buffer.getBytes());
	}

	public static void saveBinaryFile(String fileName, byte[] buffer) throws IOException
	{

		FileOutputStream fstream = new FileOutputStream(fileName);
		fstream.write(buffer);
		fstream.close();
	}

	public static void delete(String fileName) throws Exception
	{

		File file = new File(fileName);
		file.delete();
	}

	/**
	 * Deletes all files and subdirectories under dir.
	 * 
	 * @return true if all deletions were successful. If a deletion fails, the
	 *         method stops attempting to delete and returns false.
	 */
	public static boolean deleteDir(File dir)
	{

		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// The directory is now empty so delete it
		return dir.delete();
	}

	public static File[] getFileList(String fileLocation, String filter) throws Exception
	{

		File dir = new File(fileLocation);
		// only try the upload if the directory we specify exists
		if (!dir.exists()) {
			throw new IOException("No such directory:" + fileLocation);
		}
		File[] files = dir.listFiles(new SimpleFileFilter(filter));
		if (files.length <= 0) {
			throw new FileNotFoundException("No files found in '" + fileLocation + "' matching '" + filter + "'");
		}
		return (files);
	}

	public static File[] getFileList(String fileLocation) throws Exception
	{

		File dir = new File(fileLocation);
		// only try the upload if the directory we specify exists
		if (!dir.exists()) {
			throw new IOException("No such directory:" + fileLocation);
		}
		File[] files = dir.listFiles();
		return (files);
	}

	public static void setLastModified(String fileName, long modifiedTime) throws Exception
	{

		File file = new File(fileName);
		file.setLastModified(modifiedTime);
	}

	/**
	 * Rename a file to a new file name and path
	 * 
	 * @param fileNameAndPath
	 *            is the existing file
	 * @param newFileNameAndPath
	 *            is what we rename it to
	 * @param overWrite
	 *            if set true will overwrite an existing file, false wont.
	 * @return true if successfull or false if not
	 */
	public static boolean renameFile(String fileNameAndPath, String newFileNameAndPath, boolean overWrite)
	{

		File file = new File(fileNameAndPath);
		if (file.exists() == false) {
			return (false); // source file does not exist
		}
		File newFile = new File(newFileNameAndPath);
		if (overWrite == false && newFile.exists() == true) {
			return (false); // unable to overwrite existing file
		}
		if (newFile.exists()) {
			if (newFile.delete() == false) {
				return (false); // Can't delete file
			}
		}
		if (file.renameTo(newFile) == false) {
			// Copy file
			try {
				copyFile(file, newFile);
				if (file.delete() == false) {
					return (false); // can't delete it
				}
			} catch (Exception ex) {
				return (false); // not copied
			}
		}
		if (newFile.setLastModified(file.lastModified()) == false) {
			return (false);
		}
		return (true); // whew
	}

	/**
	 * Appends file2 to file1
	 * 
	 * @param fileName1
	 *            is the name of the file to append file2 onto. If file1 does
	 *            not exist it is created.
	 * @param fileName2
	 *            is the name of an existing file to append to file1. if file2
	 *            does not exist an exception is thrown
	 * @return true if successfull or false if not
	 */
	public static boolean appendFile(String fileName1, String fileName2) throws Exception
	{

		File file = new File(fileName2);
		if (file.exists() == false) {
			throw new Exception("[appendFile] File '" + fileName2 + "' does not exist");
		}
		FileOutputStream fstream = new FileOutputStream(fileName1, true);
		byte[] buffer = loadBinaryFile(fileName2);
		fstream.write(buffer);
		fstream.close();
		return (true);
	}

	/**
	 * Appends file2 to file1 and stores the size of file1 at the end as an 10
	 * byte ascii number. i.e. "0000000234"
	 * 
	 * @param fileName1
	 *            is the name of an existing file to append to file2
	 * @param fileName2
	 *            is the name of the file to append file1 onto. If file2 does
	 *            not exist it is created.
	 * @return true if successfull or false if not
	 */
	public static boolean appendFileWithSize(String fileName1, String fileName2) throws Exception
	{

		File file2 = new File(fileName2);
		File file1 = new File(fileName1);
		if (file2.exists() == false) {
			throw new Exception("[appendFileWithSize] File '" + fileName2 + "' does not exist");
		}
		byte sizebuf[] = new byte[10];
		String size = "" + file1.length();
		int iLoop;
		for (iLoop = 0; iLoop < (10 - size.length()); iLoop++) {
			sizebuf[iLoop] = '0'; // pad zero's from left
		}
		for (int pLoop = 0; iLoop < 10; iLoop++, pLoop++) {
			sizebuf[iLoop] = (byte) size.charAt(pLoop);
		}
		FileOutputStream fstream = new FileOutputStream(fileName1, true);
		byte[] buffer = loadBinaryFile(fileName2);
		fstream.write(buffer);
		fstream.write(sizebuf);
		fstream.close();
		// Log.getInstance().debug("bufsize:" + new String(sizebuf));
		return (true);
	}

	public static Properties loadProperties(String fileName) throws Exception
	{

		Properties properties = new Properties();
		properties.load(new FileInputStream(fileName));
		return (properties);
	}

	public static byte[] readInputStream(InputStream inputStream) throws IOException
	{

		int size = inputStream.available();
		int amountRead = 0;
		if (size > 0) {
			byte[] inBuffer = new byte[size];
			byte[] data = new byte[size];
			int dataIndex = 0;
			while (dataIndex < size) {
				amountRead = inputStream.read(inBuffer, 0, size);
				Memory.copy(inBuffer, data, dataIndex, amountRead);
				dataIndex += amountRead;
			}
			return (data);
		} else {
			return (null);
		}
	}

	public static byte[] blockReadInputStream(InputStream inputStream, int sizeToRead) throws Exception
	{

		int size = inputStream.available();
		if (size > sizeToRead) {
			size = sizeToRead; // only read this much data at a time
		}
		int amountRead = 0;
		if (size > 0) {
			byte[] inBuffer = new byte[size];
			byte[] data = new byte[size];
			int dataIndex = 0;
			while (dataIndex < size) {
				amountRead = inputStream.read(inBuffer, 0, size);
				new Memory().copy(inBuffer, data, dataIndex, amountRead);
				dataIndex += amountRead;
			}
			return (data);
		} else {
			return (null);
		}
	}

	/**
	 * backup a file.
	 * 
	 * @param sourceFileName
	 *            is the file we want to backup.
	 * @param destFileName
	 *            is the new name of the file, can contain a new path also.
	 * @param forceOverwrite
	 *            if set true will backup the source file over an existing
	 *            destination file.
	 */
	public static void backupFile(String sourceFileName, String destFileName, boolean forceOverwrite) throws Exception
	{

		File sourceFile = new File(sourceFileName);
		if (sourceFile.exists() == false) {
			throw new Exception("Source File '" + sourceFileName + "' does not exist");
		}
		if (forceOverwrite == false) {
			File destFile = new File(destFileName);
			if (destFile.exists() == false) {
				throw new Exception("Destination File '" + destFileName
						+ "' exists.  Cannot overwrite unless the 'forceOverwrite' is set");
			}
		}
		// copy source file to destination file
		copyFile(sourceFileName, destFileName);
	}

	private static final int FILE_COPY_BLOCK_SIZE = 10000;

	/**
	 * Copy a FileInputStream to a FileOutputStream.
	 * 
	 * @param fis
	 *            is the FileInputStream to copy from.
	 * @param fos
	 *            is the FileOutputStream to copy to.
	 * @throws exception
	 *             if something goes wrong.
	 */
	public static void copyFileStreams(FileInputStream fis, FileOutputStream fos) throws Exception
	{

		try {
			int size = fis.available();
			for (int iLoop = 0; iLoop < size; iLoop += FILE_COPY_BLOCK_SIZE) {
				byte[] data = blockReadInputStream(fis, FILE_COPY_BLOCK_SIZE);
				fos.write(data);
			}
		} finally {
			fis.close();
			fos.close();
		}
	}

	/**
	 * copy a file.
	 * 
	 * @param sourceFileName
	 *            is the file we want to copy.
	 * @param destFileName
	 *            is the name of the copy to file, can contain a new path also.
	 */
	public static void copyFile(String sourceFileName, String destFileName) throws Exception
	{

		FileInputStream fis = new FileInputStream(sourceFileName);
		FileOutputStream fos = new FileOutputStream(destFileName);
		copyFileStreams(fis, fos);
	}

	/**
	 * copy a file.
	 * 
	 * @param sourceFileName
	 *            is the file we want to copy.
	 * @param destFile
	 *            is the destination file to copy to.
	 */
	public static void copyFile(String sourceFileName, File destFile) throws Exception
	{

		FileInputStream fis = new FileInputStream(sourceFileName);
		FileOutputStream fos = new FileOutputStream(destFile);
		copyFileStreams(fis, fos);
	}

	/**
	 * copy a file.
	 * 
	 * @param sourceFile
	 *            is the file we want to copy.
	 * @param destFile
	 *            is the destination file to copy to.
	 */
	public static void copyFile(File sourceFile, File destFile) throws Exception
	{

		try {
			FileInputStream fis = new FileInputStream(sourceFile);
			FileOutputStream fos = new FileOutputStream(destFile);
			copyFileStreams(fis, fos);
		} catch (Exception ex) {
			throw new Exception("Unable to copy file '" + sourceFile.getAbsolutePath() + "' to '"
					+ destFile.getAbsolutePath() + "'", ex);
		}
		if (destFile.setLastModified(sourceFile.lastModified()) == false) {
			throw new Exception("Unable to change lastModified time on file:" + destFile.getAbsolutePath() + "\nfrom:"
					+ DateTime.getDate(destFile.lastModified(), "dd-MO-yy hh:mm:ss") + " to:"
					+ DateTime.getDate(sourceFile.lastModified(), "dd-MO-yy hh:mm:ss"));
		}
	}


	/**
	 * mkdir for folderName and set the lastModifiedDate to lastModifiedDate
	 * 
	 * @return -1 if unable to mkdir, -2 if unable to setLastModified, 0 if ok
	 * 
	 */
	public static int mkdir(String folderName, long lastModifiedDate)
	{

		File file = new File(folderName);
		int result = mkdir(file);
		if (result != 0) {
			return (result);
		}
		if (file.setLastModified(lastModifiedDate) == false) {
			return (-2);
		}
		return (0);
	}

	/**
	 * mkdir for folderName and set the lastModifiedDate to lastModifiedDate
	 * 
	 * @return -1 if unable to mkdir, -2 if unable to setLastModified, 0 if ok
	 */
	public static int mkdir(File folder)
	{

		if (folder.exists() == false) {
			if (folder.mkdir() == false) {
				return (-1);
			}
		}
		return (0);
	}

	/**
	 * mkdirs for folderName
	 * 
	 * @return -1 if unable to mkdir, -2 if unable to setLastModified, 0 if ok
	 */
	public static int mkdirs(File folder)
	{

		if (folder.exists() == false) {
			if (folder.mkdirs() == false) {
				return (-1);
			}
		}
		return (0);
	}

	/**
	 * creates a new folder named "yyyy-MM-dd" and appends this onto the
	 * pathName, then creates the folder.
	 * 
	 * @param pathName
	 *            is the path to the folder.
	 * @return null if unable to create the folder else the fill path including
	 *         new folder name
	 */
	public static String mkdirsToday(String pathName)
	{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(Calendar.getInstance().getTime());
		String folderName = ResourceCommon.buildFileName(pathName, today);
		File folder = new File(folderName);
		if (folder.exists() == false) {
			if (folder.mkdirs() == false) {
				return (null);
			}
		}
		return (folder.getAbsolutePath()); // folder created.
	}

	public static void sortFiles(File[] files)
	{

		Arrays.sort(files, new FileComparator());
	}

	private static class FileComparator implements Comparator
	{

		private final Collator c = Collator.getInstance();

		public int compare(Object o1, Object o2)
		{

			if (o1 == o2) {
				return 0;
			}

			File f1 = (File) o1;
			File f2 = (File) o2;

			if (f1.isDirectory() && f2.isFile()) {
				return -1;
			}
			if (f1.isFile() && f2.isDirectory()) {
				return 1;
			}
			return c.compare(f1.getName().toUpperCase(), f2.getName().toUpperCase());
		}
	}

	/**
	 * Returns a tempory folder where tempory files can be stored. This folder
	 * will be marked for deletion when system exits. If the system doesn;t exit
	 * cleanly then the folder will not be deleted. The tempory folder is
	 * created inside the user home
	 * 
	 * @param appendFolderName
	 *            is the folder to be created inside the tempory folder
	 *            location.
	 */
	public static File getTempFolder(String appendFolderName)
	{

		String folder = System.getProperty("user.home");
		File file = new File(ResourceCommon.buildFileName(folder, appendFolderName));
		file.mkdirs();
		file.deleteOnExit();
		return (file);
	}

    /**
     * Creates a new file
     * 
     * @param prefix
     *            is the prefix used for the file name must be at least 3
     *            characters
     * @param suffix
     *            is the file extension and may be null in which case '.tmp' is
     *            used
     * @param path
     *            is the folder path where the file will be created
     * @returns the new file
     * @throws exception
     *             if something goes wrong.
     */
	public static File createTempFile(String prefix, String suffix, String path) throws Exception
	{

		File file = File.createTempFile(prefix, suffix, new File(path));
		return (file);
	}

	/**
	 * Get a URL for a giver resource. The resource must exist or an exception
	 * is caused.
	 * 
	 * @param resourceName
	 * @return the URL of the resource.
	 */
	public static URL getResourceURL(String resourceName)
	{
		URL resource = ResourceUtils.class.getResource(resourceName);
		if (resource == null) {
			try {
				resource = new URL(resourceName);
			} catch (MalformedURLException e) {
				// pass on. resource == null will be caught later.
			}
		}
		Validate.notNull(resource, " URL [" + resourceName + "] not found.");
		return resource;
	}

	/**
	 * Get a URL for a given resource. The resource must exist or an exception
	 * is caused.
	 * 
	 * @param resourceName
	 * @return the URL of the resource.
	 */
	public static URL getResourceURL(ApplicationContext appContext, String resourceName, Class<?> clas) {
		URL resource = null;
		try {
			if (appContext != null ) {
					resource = appContext.getResource(resourceName).getURL();
			} else {
				ClassPathResource cpr = new ClassPathResource(resourceName);
				resource = cpr.getURL();
			}
			
			if (resource == null) {
				resource = ResourceUtils.class.getResource(resourceName);
				if (resource == null) {
					resource = clas.getResource(resourceName);
				}
			}
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException("Unable to load resource [" + resourceName + "]", ex);
		}
		Validate.notNull(resource, "Unable to load resource [" + resourceName + "]");
		
		return resource;
    }

	/**
	 * Gets a URL getFile replacing all %20 characters with spaces
	 */
	public static File getFile(URL resource)
	{

		return new File(resource.getFile().replace("%20", " "));
	}

	/**
	 * Gets a URL(String) getFile replacing all %20 characters with spaces
	 */
	public static File getFile(String resource)
	{
		URL url = getResourceURL(resource);

		return getFile(url);
	}

    /**
     * Gets an InputStream from a File or from a Resource
     * 
     * @param resourceFileName
     *            a reference to the file or resource.
     * 
     * @return an Open InputStream, you close this when your done.
     * @throws IOException
     */
    public static InputStream getInputStream(String resourceFileName) throws IOException {
        InputStream is;
        File file = new File(resourceFileName);
        if (!file.exists()) {
            URL url = getResourceURL(resourceFileName);
            if (url == null) {
            	Validate.notNull(url, "Resource [" + resourceFileName + "] not found");
            }
            is = url.openStream();
        } else {
            is = new FileInputStream(file);
        }
        return is;
    }

}
