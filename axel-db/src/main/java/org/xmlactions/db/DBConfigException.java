
package org.xmlactions.db;

/**
 *
 * @author mike
 */
public class DBConfigException extends Throwable
{
   public DBConfigException(String msg)
   {
      super(msg);
   }
   public DBConfigException(String msg, Throwable ex)
   {
      super(msg, ex);
   }
}
