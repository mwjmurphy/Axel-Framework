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
