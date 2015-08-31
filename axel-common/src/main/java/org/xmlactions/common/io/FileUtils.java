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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmlactions.common.io;


import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author MichaelMurphy
 */
public class FileUtils
{
	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

   /**
    * Creates a .tmp file with a unique file name from the source
    * file.
    * @param sourceFile - temp file created from this
    *
    */
   public static File getTempFile(File sourceFile) throws IOException
   {
      // get a temp file
      File file = File.createTempFile(sourceFile.getName(), null, sourceFile.getParentFile());

      log.debug("Temp File '" + file.getAbsolutePath() + "' created");

      return file;
   }

   /**
    * Safely Renames a file.  This is not a thread safe method.
    * @param from
    * @param to
    * @throws java.io.IOException
    */
   public static void rename(File from, File to) throws IOException
   {
      if (to.exists() && to.length() > 0)
      {
         File backupFile = FileUtils.getTempFile(from);
         backupFile.delete();

         if (to.renameTo(backupFile) == false)
         {
            throw new IOException("unable to rename '" + to.getAbsolutePath() + "'" +
                    " to '" + backupFile.getAbsolutePath() + "'.");
         }
         if (from.renameTo(to) == false)
         {
            if (backupFile.renameTo(to) == false)
            {
               throw new IOException("unable to rename '" + backupFile.getAbsolutePath() + "'" +
                       " to '" + to.getAbsolutePath() + "'.  Existing zip is now named '" + backupFile.getAbsolutePath() + "'");
            }
            throw new IOException("unable to rename '" + from.getAbsolutePath() + "'" +
                    " to '" + to.getAbsolutePath() + "'.  Adding files to existing zip failed.");
         }
         backupFile.delete();
      }
      else
      {
         to.delete();
         if (from.renameTo(to) == false)
         {
            throw new IOException("unable to rename '" + from.getAbsolutePath() + "'" +
                    " to '" + to.getAbsolutePath() + "'.");
         }

      }
   }
}
