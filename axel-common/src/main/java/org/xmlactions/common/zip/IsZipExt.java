package org.xmlactions.common.zip;

import org.xmlactions.common.io.ResourceCommon;

public class IsZipExt {
	   /** check if this is a zip file by looking at the file extensions. */
	   public static boolean isZip(String fileName)
	   {
	      String ext = ResourceCommon.getExtension(fileName);
	      if (ext.equalsIgnoreCase("zip") ||
	              ext.equalsIgnoreCase("rar") ||
	              ext.equalsIgnoreCase("tar") ||
	              ext.equalsIgnoreCase("war") ||
	              ext.equalsIgnoreCase("ear") ||
	              ext.equalsIgnoreCase("jar") ||
	              ext.equalsIgnoreCase("zep"))
	      {
	         return(true);
	      }
	      return(false);
	   }

}
