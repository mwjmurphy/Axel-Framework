
package org.xmlactions.common.xml;

/**
 * Throw This exception when we reach the end of the buffer
 */
public class EndOfBufferException extends Exception
{
   public EndOfBufferException(String message)
   {
      super(message);
   }

   public EndOfBufferException(String message, Exception cause)
   {
      super(message,cause);
   }
}
