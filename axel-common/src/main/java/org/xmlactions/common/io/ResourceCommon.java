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


package org.xmlactions.common.io;


import java.io.File;
import java.util.Vector;

import org.xmlactions.common.system.JS;



public class ResourceCommon {

	public static String buildFileName(String path, String fileName) {

		String separator = System.getProperties().getProperty("file.separator");
		return buildFileName(path, fileName, separator);
	}

	public static String buildFileName(String path, String fileName, String separator) {

		if (fileName == null)
			return (path);
		if (path != null && path.length() > 0) {
			if (fileName.length() > 0 && isPathSeparatorChar(fileName.charAt(0))) {
				fileName = fileName.substring(1);
			}
			String name = path;
			if (isPathSeparatorChar(path.charAt(path.length() - 1))) {
				name += fileName;
			} else {
				name += separator + fileName;
			}
			return (name);
		}
		return (fileName);
	}

	public static String getPath(String pathAndFileName) {

		if (new File(pathAndFileName).isDirectory())
			return (pathAndFileName);
		String separator = System.getProperties().getProperty("file.separator");
		if (pathAndFileName != null && pathAndFileName.length() > 0) {
			String name = pathAndFileName;

			for (int iLoop = pathAndFileName.length() - 1; iLoop > -1; iLoop--) {
				if (isPathSeparatorChar(pathAndFileName.charAt(iLoop))) {
					return (pathAndFileName.substring(0, iLoop));
				}
			}
		}
		return (null);// no path
	}

	/**
	 * Returns the file name separated from the path.
	 * 
	 * @param fileName
	 *            is the file name including path.
	 * @return the filename including extension seperated from the path.
	 */
	public String getFileName(String fileName) {

		for (int iLoop = fileName.length() - 1; iLoop >= 0; iLoop--) {
			if (isPathSeparatorChar(fileName.charAt(iLoop))) {
				return (fileName.substring(iLoop + 1));
			}
		}
		return (fileName);
	}

	/**
	 * Check if this is a path separator. i.e. is '/' or '\'
	 */
	public static boolean isPathSeparatorChar(char c) {

		if (c == '/' || c == '\\' || c == System.getProperty("file.separator").charAt(0))
			return (true);
		return (false);
	}

	/**
	 * Will return an array of folder names that are contained inside the path.
	 * As an example c:/dev/folder = [0]=c: [1]=dev [2]=folder. Note that the
	 * path separator char is not included as part of the folder name.
	 * 
	 * The path must not contain a file name or this will also be returned as
	 * part of the path array. i.e c:\dev\folder1 but not
	 * c:\dev\folder1\image.gif
	 * 
	 * @param path
	 *            the fill path i.e. c:/dev/folder1/bin
	 * @return a String array of the path parts.
	 */
	public static String[] getPaths(String path) {

		// first count the path separators
		int count = 0;
		for (int iLoop = 0; iLoop < path.length(); iLoop++) {
			if (isPathSeparatorChar(path.charAt(iLoop)))
				count++;
		}
		if (isPathSeparatorChar(path.charAt(path.length() - 1)))
			count++; // we dont have an ending char so that makes for another
		// folder

		String[] paths = new String[count];

		count = 0;
		int lastPath = 0;
		for (int iLoop = 0; iLoop < path.length(); iLoop++) {
			if (isPathSeparatorChar(path.charAt(iLoop))) {
				paths[count++] = path.substring(lastPath, iLoop);
				lastPath = iLoop + 1;
			}
		}
		if (isPathSeparatorChar(path.charAt(path.length() - 1))) {
			paths[count++] = path.substring(lastPath);
		}

		return (paths);
	}

	public static String getCurrentDirectory() throws Exception {

		File dir = new File(".");
		return (dir.getCanonicalPath());
	}

	/**
	 * returns the file path in parts. i.e. c:\windows\temp\ = c:, windows, temp
	 */
	public static Vector getFilePathParts(File file) {

		Vector paths = new Vector();
		int start = 0;
		String path = file.getPath();
		int i;
		for (i = 0; i < path.length(); i++) {
			if (isPathSeparatorChar(path.charAt(i))) {
				paths.add(path.substring(start, i + 1));
				start = i + 1;
			}
		}
		if (start < i)
			paths.add(path.substring(start));
		return (paths);
	}

	/** returns the parent path same as FILE.getParent() */
	public static String getParent(String path) {

		String[] parts = getPaths(path);
		String parent = "";
		char seperator = getFolderSeperatorChar(path);
		for (int iLoop = 0; iLoop < parts.length; iLoop++) {
			parent += parts[iLoop] + seperator;
		}
		return (parent);
	}

	/** returns the seperator char found in the path can be a / or \ */
	public static char getFolderSeperatorChar(String path) {

		for (int i = 0; i < path.length(); i++) {
			if (isPathSeparatorChar(path.charAt(i))) {
				return (path.charAt(i));
			}
		}
		return (JS.getFileSeperator().charAt(0));
	}

	/**
	 * find the extension part of a file name. Doesn't only consider a three
	 * character extension.
	 * 
	 * @param fileName
	 *            is the file name.
	 * @return the extension part of the file name excluding the '.'. Will
	 *         return null if no extension is found . i.e. "file.nam" returns
	 *         "nam"
	 */
	public static String getExtension(String fileName) {

		for (int iLoop = fileName.length() - 1; iLoop >= 0; iLoop--) {
			if (fileName.charAt(iLoop) == '.') {
				return (fileName.substring(iLoop + 1));
			}
		}
		return (null);
	}

}
