package org.xmlactions.common.xml;

/**
 * Throw this when an bad xml buffer is encountered
 */
public class BadXMLException extends Exception
{
   public BadXMLException(String message)
   {
      super(message);
   }

   public BadXMLException(String message, Exception cause)
   {
      super(message,cause);
   }
}
