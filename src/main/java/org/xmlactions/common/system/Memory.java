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
 * Memory.java
 *
 * Created on August 15, 2005, 11:15 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.xmlactions.common.system;

/**
 *
 * @author MMURPHY
 */
public class Memory
{
   
   public static void copy(byte[] from, byte[] to, int toOffset, int size)
   {
      for (int iLoop = 0; iLoop < size && iLoop+toOffset < to.length; iLoop++)
      {
         to[iLoop+toOffset] = from[iLoop];
      }
   }
   /** copy bytes from to. wont pass from.length or to.length */
   public static void copy(byte[] from, byte[] to)
   {
      for (int iLoop = 0; iLoop < from.length && iLoop < to.length; iLoop++)
      {
         to[iLoop] = from[iLoop];
      }
   }
}
