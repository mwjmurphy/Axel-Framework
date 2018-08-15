package org.xmlactions.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * These method are more focused on accessing resource files requiring a CLass<?> parameter.
 * @author mike
 *
 */
public class ResourceIO {

	/**
	 * Load a String from a file location or a resource location (path or classpath)
	 * @param name - can be fileName or resourceFileName
	 * @return the loaded String
	 */
	public static String loadFileOrResource(Class<?> clas, String name) {
		
		String errorMessage = "Unable to load resource from [" + name + "]";
		InputStream inputStream = null;
		try {
			inputStream = openInputStreamForFileOrResource(clas, name);
			String fileContent = IOUtils.toString(inputStream);
			return fileContent;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new IllegalArgumentException(errorMessage, ex);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * Open InputStream from a file location or a resource location (path or classpath)
	 * <p>
	 * 	<b>You must close the inputstream</b> maybe use <code>IOUtils.closeQuietly(inputStream);</code>
	 * </p>
	 * @param name - can be fileName or resourceFileName
	 * @return the loaded String
	 */
	public static InputStream openInputStreamForFileOrResource(Class<?> clas, String name) {
		
		String errorMessage = "Unable to load resource from [" + name + "]";
		InputStream inputStream = null;
		try {
			if (new File(name).exists() == true) {
				inputStream = new FileInputStream(name);
			} else {
				URL url = clas.getResource(name);
				if (url == null) {
					throw new IllegalArgumentException(errorMessage);
				}
				inputStream = url.openStream();
			}
			return inputStream;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new IllegalArgumentException(errorMessage, ex);
		}
	}
}
