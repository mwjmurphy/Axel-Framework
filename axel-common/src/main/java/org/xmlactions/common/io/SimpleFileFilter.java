/*
 * SimpleFileFilter.java
 *
 * Created on July 22, 2005, 11:50 AM
 *
 */

package org.xmlactions.common.io;


import java.io.File;
import java.io.FileFilter;


/**
 *
 * @author MMURPHY
 */
public class SimpleFileFilter implements FileFilter
{
   
   /** Creates a new instance of SimpleFileFilter */
   
   String filter = null;
   boolean filesOnly;
   boolean directoriesOnly;
   boolean includeHidden = true;
   
   public SimpleFileFilter(String filter)
   {
      this.filter = filter != null ? filter.toUpperCase() : filter;
      this.filesOnly = false;
      this.directoriesOnly = false;
   }
   public SimpleFileFilter(String filter, boolean includeHidden)
   {
      this.filter = filter != null ? filter.toUpperCase() : filter;
      this.filesOnly = false;
      this.directoriesOnly = false;
      this.includeHidden = includeHidden;
   }
   
   public SimpleFileFilter(String filter, boolean filesOnly, boolean directoriesOnly)
   {
      this.filter = filter != null ? filter.toUpperCase() : filter;
      this.filesOnly = filesOnly;
      this.directoriesOnly = directoriesOnly;
   }
   
   public boolean accept(File file)
   {
      //Log.getInstance().debug(JS.getCurrentMethodName_static() + " file:" + file.getAbsolutePath());
      if (includeHidden == false && file.isHidden())
      {
         return(false); // ignore hidden files
      }
      if(file.isFile() && this.directoriesOnly == false)
      {
         if (filter == null || filter.length() == 0)
         {
            return true;
         }
         if(file.getName().toUpperCase().indexOf(filter) >= 0)
         {
            return true;
         }
      }
      else if (file.isDirectory() && this.filesOnly == false)
      {
         return(true);
      }
      return false;
   }
   
   private boolean doCallBack(File file)
   {
      return(true);
   }
}
