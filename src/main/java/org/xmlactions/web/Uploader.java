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

package org.xmlactions.web;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;

//import org.xmlactions.db.DBField;

/**
 * Use this class to upload files from the client browser.
 * 
 * TODO complete this class copying the source from riostl.com Uploader.java
 * @author MichaelMurphy
 */
public class Uploader
{
   private static Logger log = LoggerFactory.getLogger(Uploader.class);
   public final static String UPLOAD = "upload_";

}
