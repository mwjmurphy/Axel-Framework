/*
 * JS.java (Java System)
 *
 * Created on 21 July 2006, 10:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xmlactions.common.system;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

import org.xmlactions.common.text.Text;



/**
 * 
 * @author mike
 * 
 *         -- listing properties -- java.runtime.name=Java(TM) 2 Runtime
 *         Environment, Stand...
 *         sun.boot.library.path=C:\dev\java\j2se-1.4.2\jre\bin
 *         java.vm.version=1.4.2_05-b04 java.vm.vendor=Sun Microsystems Inc.
 *         java.vendor.url=http://java.sun.com/ path.separator=;
 *         java.vm.name=Java HotSpot(TM) Client VM file.encoding.pkg=sun.io
 *         user.country=IE sun.os.patch.level=Service Pack 2
 *         java.vm.specification.name=Java Virtual Machine Specification
 *         user.dir=C:\dev\javadev\almur\installer
 *         java.runtime.version=1.4.2_05-b04
 *         java.awt.graphicsenv=sun.awt.Win32GraphicsEnvironment
 *         java.endorsed.dirs=C:\dev\java\j2se-1.4.2\jre\lib\endorsed
 *         os.arch=x86 java.io.tmpdir=C:\DOCUME~1\mike\LOCALS~1\Temp\
 *         line.separator=
 * 
 *         java.vm.specification.vendor=Sun Microsystems Inc. user.variant=
 *         os.name=Windows XP sun.java2d.fontpath=
 *         java.library.path=C:\dev\java\j2se-1.4.2\jre\bin;.;C:\W...
 *         java.specification.name=Java Platform API Specification
 *         java.class.version=48.0
 *         java.util.prefs.PreferencesFactory=java.util.prefs
 *         .WindowsPreferencesFac... os.version=5.1 user.home=C:\Documents and
 *         Settings\mike user.timezone=Atlantic/South_Georgia
 *         java.awt.printerjob=sun.awt.windows.WPrinterJob file.encoding=Cp1252
 *         java.specification.version=1.4 user.name=mike
 *         java.class.path=C:\dev\javadev\almur\installer\lib\am...
 *         java.vm.specification.version=1.0 sun.arch.data.model=32
 *         java.home=C:\dev\java\j2se-1.4.2\jre java.specification.vendor=Sun
 *         Microsystems Inc. user.language=en
 *         awt.toolkit=sun.awt.windows.WToolkit java.vm.info=mixed mode
 *         java.version=1.4.2_05
 *         java.ext.dirs=C:\dev\java\j2se-1.4.2\jre\lib\ext
 *         sun.boot.class.path=C:\dev\java\j2se-1.4.2\jre\lib\rt.jar...
 *         java.vendor=Sun Microsystems Inc. file.separator=\
 *         java.vendor.url.bug=http://java.sun.com/cgi-bin/bugreport...
 *         sun.cpu.endian=little sun.io.unicode.encoding=UnicodeLittle
 *         sun.cpu.isalist=pentium i486 i386
 * 
 */
public class JS {
	/**
	 * Creates a new instance of JS (JavaSystem). Use this for Memory Routines,
	 * Class and Method routines, types and variables etc
	 */
	public JS() {
	}

	public static String getFreeMemory() {
		return (Text.longToString(Runtime.getRuntime().freeMemory()));
	}

	public static String getTotalMemory() {
		return (Text.longToString(Runtime.getRuntime().totalMemory()));
	}

	public static String getMaxMemory() {
		return (Text.longToString(Runtime.getRuntime().maxMemory()));
	}

	public static String getUsedMemory() {
		return (Text.longToString(Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory()));
	}

	/** calls GarbageCollect to free any finished memory */
	public static void cleanMemory() {
		Runtime.getRuntime().gc();
	}

	/**
	 * Return the name of the routine that called getCurrentMethodName
	 * 
	 * @author Johan Kanngard, http://dev.kanngard.net (found on the net in
	 *         2000, don't remember where...)
	 */
	public static String getShortCurrentMethodName_static() {
		Throwable t = new Throwable();
		StackTraceElement[] es = t.getStackTrace();
		StackTraceElement e = es[1];
		// String code = e.getClassName() + "."+ e.getMethodName() + " line[" +
		// e.getLineNumber() +"]";
		String code = e.getMethodName() + "(" + e.getFileName() + ":"
				+ e.getLineNumber() + ")";
		return (code);
	}

	/**
	 * Return the name of the routine that called getCurrentMethodName
	 * 
	 * @author Johan Kanngard, http://dev.kanngard.net (found on the net in
	 *         2000, don't remember where...)
	 */
	public static String getCurrentMethodName_static() {
		Throwable t = new Throwable();
		StackTraceElement[] es = t.getStackTrace();
		StackTraceElement e = es[1];
		// String code = e.getClassName() + "."+ e.getMethodName() + " line[" +
		// e.getLineNumber() +"]";
		String code = e.getClassName() + "." + e.getMethodName() + "("
				+ e.getFileName() + ":" + e.getLineNumber() + ")";
		return (code);
	}

	/**
	 * Return the name of the routine that called getCurrentMethodName
	 * 
	 * @author Johan K�nng�rd, http://dev.kanngard.net (found on the net in
	 *         2000, don�t remember where...)
	 */
	public static String getCurrentMethodName_static(int line) {
		Throwable t = new Throwable();
		StackTraceElement[] es = t.getStackTrace();
		StackTraceElement e = es[line];
		// String code = e.getClassName() + "."+ e.getMethodName() + " line[" +
		// e.getLineNumber() +"]";
		String code = e.getClassName() + "." + e.getMethodName() + "("
				+ e.getFileName() + ":" + e.getLineNumber() + ")";
		return (code);
	}

	/**
	 * Return the current stack
	 * 
	 */
	public static String getCurrentStack_static() {
		StringBuffer sb = new StringBuffer();
		Throwable t = new Throwable();
		StackTraceElement[] es = t.getStackTrace();
		for (int iLoop = 1; iLoop < es.length; iLoop++) {
			StackTraceElement e = es[iLoop];
			// String code = e.getClassName() + "."+ e.getMethodName() +
			// " line[" + e.getLineNumber() +"]";
			String code = e.getClassName() + "." + e.getMethodName() + "("
					+ e.getFileName() + ":" + e.getLineNumber() + ")";
			sb.append(code + "\n");
		}
		return (sb.toString());
	}

	/**
	 * Return the name of the routine that called getCurrentMethodName
	 */
	public static String getCurrentMethodName() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(baos);
		(new Throwable()).printStackTrace(pw);
		pw.flush();
		String stackTrace = baos.toString();
		pw.close();

		StringTokenizer tok = new StringTokenizer(stackTrace, "\n");
		String l = tok.nextToken(); // 'java.lang.Throwable'
		l = tok.nextToken(); // 'at ...getCurrentMethodName'
		l = tok.nextToken(); // 'at ...<caller to getCurrentRoutine>'
		// Parse line 3
		// tok = new StringTokenizer(l.trim(), " <(");
		tok = new StringTokenizer(l.trim(), " \n");
		String t = tok.nextToken(); // 'at'
		t = tok.nextToken(); // '...<caller to getCurrentRoutine>'
		return t;
	}

	/** returns the file seperator char for this system. can be a \ or / */
	public static String getFileSeperator() {
		return (System.getProperty("file.separator"));
	}

	/** gets the user.home folder name. */
	public static String getUserHomeFolderName() {
		String userHomeFolder = System.getProperty("user.home");
		if (userHomeFolder != null && userHomeFolder.length() > 0) {
			try {
				userHomeFolder = new File(userHomeFolder).getCanonicalPath();
			} catch (Exception ex) {
				userHomeFolder = System.getProperty("user.home");
			}
			return (userHomeFolder);
		}
		return (null);
	}

	/** gets the java.io.tmpdir folder name. */
	public static String getTempFolderName() {
		String tempFolder = System.getProperty("java.io.tmpdir");
		if (tempFolder != null && tempFolder.length() > 0) {
			try {
				tempFolder = new File(tempFolder).getCanonicalPath();
			} catch (Exception ex) {
				tempFolder = System.getProperty("java.io.tmpdir");
			}
			return (tempFolder);
		}
		return (null);
	}

	/**
	 * Read the complete Java Environment settings into an xml String
	 */
	public static String getAllEnv() {
		StringBuilder sb = new StringBuilder();
		Properties p = System.getProperties();
		// p.list(System.out);
		// Enumeration e = p.elements();
		Enumeration e = p.keys();
		while (e.hasMoreElements() == true) {
			Object o = e.nextElement();
			sb.append(o.toString());
			sb.append('=');
			sb.append(p.get(o.toString()).toString());
			sb.append('\n');
		}
		return (sb.toString());
	}

	public static String getEnv(String envName) {
		return (System.getenv(envName));
	}

	public static String getProperty(String propName) {
		return (System.getProperty(propName));
	}

	/**
	 * Check if this is a path separator. i.e. is '/' or '\'
	 */
	public static boolean isPathSeparatorChar(char c) {
		if (c == '/' || c == '\\'
				|| c == System.getProperty("file.separator").charAt(0)) {
			return (true);
		}
		return (false);
	}

}
