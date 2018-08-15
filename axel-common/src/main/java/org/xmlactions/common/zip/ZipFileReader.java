/*
 * ZipFileReader.java
 *
 * Created on 07 February 2006, 09:40
 *
 */

package org.xmlactions.common.zip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;

/**
 * 
 * 
 * @author mike
 * 
 *         Used for reading files from a zip file. Call
 *         ZipFileReader(sourceZipFileName) Call
 *         zipReader.saveFile(sourceFileName, targetFileName)
 */
public class ZipFileReader extends java.util.zip.ZipFile {
	/**
	 * Creates a new instance of ZipFileReader
	 */
	public ZipFileReader(String fileName) throws IOException {
		super(fileName);
	}

	public ZipFileReader(File zipFile) throws IOException {
		super(zipFile);
	}

	public Vector<String> getFileList() {
		Vector<String> entries = new Vector();
		Enumeration e = this.entries();
		while (e.hasMoreElements()) {
			Object obj = e.nextElement();
			ZipEntry ze = (ZipEntry) obj;
			// Log.getInstance().debug(JS.getCurrentMethodName_static() +
			// " comment:" + ze.getName() + ":" + ze.getComment());
			// System.out.println("entry name:" + ze.getName());
			entries.add(ze.getName());
		}
		return (entries);
	}

	public Vector<ZipEntry> getZipEntries() {
		Vector entries = new Vector();
		Enumeration e = this.entries();
		while (e.hasMoreElements()) {
			Object obj = e.nextElement();
			ZipEntry ze = (ZipEntry) obj;
			// Log.getInstance().debug(JS.getCurrentMethodName_static() +
			// " comment:" + ze.getName() + ":" + ze.getComment());
			// System.out.println("entry name:" + ze.getName());
			entries.add(ze);
		}
		return (entries);
	}

	/**
	 * @param source
	 *            is the full zip source file name including path
	 * @param target
	 *            is the fill path and file name for the save.
	 * @throws Exception
	 *             if something goes wrong.
	 */
	public void extractFile(String source, String target)
			throws FileNotFoundException, IOException {
		ZipEntry ze = this.getEntry(source);
		if (ze == null) {
			throw new IOException("unable to find '" + source
					+ "' in zip file '" + this.getName() + "'");
		}
		extractFile(ze, target);
	}

	/**
	 * @param ze
	 *            - ZipEntry we want to extract
	 * @param target
	 *            is the fill path and file name for the save.
	 * @throws Exception
	 *             if something goes wrong.
	 */
	public void extractFile(ZipEntry ze, String target)
			throws FileNotFoundException, IOException {
		File f = new File(target);
		if (ze.isDirectory() == true) {
			f.mkdirs();
		} else {
			if (f.getParentFile().exists() == false) {
				f.getParentFile().mkdirs();
			}
			OutputStream out = new FileOutputStream(target);

			InputStream in = this.getInputStream(ze);

			// Transfer bytes from the ZIP file to the output file
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			// Close the streams
			out.close();
			in.close();
		}
	}

}
