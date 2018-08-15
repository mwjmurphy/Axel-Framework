
package org.xmlactions.common.text;
import java.text.DecimalFormat;

/**
 * Static Text functionality
 *
 * @author  Mike Murphy, Paul Murphy
 * @version 1.0
 * @since   29 April 2001
 */
public class Text
{
   /**
    * Convert a number to a String with a mininum of 2 digits. i.e.
    * <code>1 = '01' & 10 = '10' & 100 = '100'</code>
    * @param number
    * @return 2 digit string.
    */
   public static String make2DigitNumber(int number)
   {
      if (number < 10)
      {
         return("0" + number);
      }
      return ("" + number);
   }
   
   /**
    * Convert a number to a String with a mininum of size digits.  If the number
    * contains more digits then the size the complete number is returned. i.e.
    * <code>
    * if size = 2 and number = 20 return ("20")
    * if size = 4 and number = 20 return ("0020")
    * if size = 8 and number = 200 return ("00000200")
    * if size = 2 and number = 200 return ("200")
    * </code>
    * @param number is the integer number we want converted to a string
    * @param size is the amount of digets we want in the number.
    * @return 'size' digit string.
    */
   public static String makeNDigitNumber(int number, int size)
   {
      String s = "" + number;
      for (int iLoop = s.length(); iLoop < size; iLoop ++)
      {
         s = "0" + s;
      }
      return (s);
   }
   
   /**
    * Will convert an int to a computer size in terms of Bytes(B), KBytes(K)
    * and MBytes(M)
    */
   public static String longToString(long size)
   {
      String s;
      double f;
      char end = ' ';
      if (size <= 0)
      {
         return("0.00");
      }
      else if (size < 1024)
      {
         s = "" + size + "B";
         return(s);
      }
      else if (size < 1024 * 1000)
      {
         f = (double)size / 1024;
         end = 'K';
      }
      else if (size < (1024 * 1000 * 1000))
      {
         f = (double)size / (1024 * 1000);
         end = 'M';
      }
      else
      {
         f = (double)size / (1024 * 1000 * 1000);
         end = 'G';
      }
      //DecimalFormat myFormatter = new DecimalFormat("###,###.##");
      DecimalFormat myFormatter = new DecimalFormat("###,##0.00");
      s = myFormatter.format(f) + end;
      return(s);
   }
   /**
    * Will convert a long to a string comma with a , seperator every 4th place.
    * i.e. 10000 = 10,000
    */
   public static String longToComma(long size)
   {
      String s;
      
      if (size < 1000)
         return ("" + size);
      
      s = "" + size;
      StringBuffer sb = new StringBuffer();
      /*
       * 10000 = 10,000
       */
      if (s.length()%3 > 0)
      {
         // get 1st characters.
         sb.append(s.substring(0, s.length() % 3));
      }
      boolean pastfirst = false;
      for ( int iLoop = s.length() % 3 ; iLoop < s.length() ; iLoop += 3 )
      {
         if (pastfirst == true) sb.append(",");
         else
         {
            if (s.length() % 3 != 0) sb.append(',');
            pastfirst = true;
         }
         sb.append(s.substring(iLoop, iLoop+3 >= s.length() ? s.length() : iLoop+3));
      }
      return(sb.toString());
   }
   
   /**
    * used to left pad the number we recieve - we must left pad them
    * as some are prefixed with zeros and some are not
    * @param value
    * @param length
    * @return left padded string
    */
   public String leftPadZeros(String value, int length)
   {
      return(leftPad(value, length, '0'));
   }
   
   /**
    * Pad a string to left to make a full string of length.
    * @param value
    * @param length
    * @param padding is the character we want to pad with.
    * @return left padded string
    */
   public static String leftPad(String value, int length, char padding)
   {
      //trim both sides of the string
      String temp = value.trim();
      //left pad to length with zero's
      int stringlength = temp.length();
      String tempvalue = "";
      for (int i = 0; i < length - stringlength; i++)
      {
         //left pad with zeros
         tempvalue = tempvalue + padding;
      }
      
      temp = tempvalue + temp;
      return temp;
   }
   
   public static String stripAndPadDecimal(String value)
   {
      //System.out.println("stripAndPadDecimal 1: "+value);
      String decimalValue = value;
      StringBuffer newValue = new StringBuffer();
      int decimalIndex = decimalValue.indexOf(".");
      int decimalLength = decimalValue.length();
      if(decimalIndex == (decimalLength-1))
      {
         decimalValue += "00";
      }
      if(decimalIndex == (decimalLength-2))
      {
         decimalValue += "0";
      }
      //System.out.println("stripAndPadDecimal 2: "+decimalValue);
      if (decimalValue.length() > 3 && decimalValue.indexOf(".")> -1 )
      {
         newValue.append(decimalValue.substring(0, decimalValue.length() - 3));
         newValue.append(decimalValue.substring(decimalValue.length() - 2));
      }
      else
      {
         //System.out.println("stripAndPadDecimal result1: "+newValue.toString());
         return decimalValue;
      }
      
      //System.out.println("stripAndPadDecimal result2: "+newValue.toString());
      return newValue.toString();
   }
   
   /**
    * Convert a string to a number.  Will return defaultValue if not a valid number.
    * @param number is the number in string format
    * @param defaultValue is returned if an exception is thrown
    * @return the number as an integer.
    *
    */
   public static int atoi(String number, int defaultValue)
   {
      try
      {
         return(Integer.parseInt(number));
      }
      catch (Exception ex)
      {
         return(defaultValue);
      }
   }
   
   public static String [] addString(String [] array, String newString)
   {
      String [] newArray = new String[array.length+1];
      int iLoop = 0;
      for (; iLoop < array.length; iLoop++)
      {
         newArray[iLoop] = array[iLoop];
      }
      newArray[iLoop] = newString;
      return(newArray);
   }
   public static String [] removeString(String [] array, int index)
   {
      String [] newArray = new String[array.length-1];
      int at = 0;
      for (int iLoop = 0 ; iLoop < array.length; iLoop++)
      {
         if (iLoop != index)
         {
            newArray[at++] = array[iLoop];
         }
      }
      return(newArray);
   }
   
   /**
    * Remove all characters matching 'removeThis' from the source String.  As an
    * example remove("000,000,000", ',') = "000000000"
    * @param source is the source String
    * @param removeThis is the character we want removed from the source.  Remember
    * all instance of the character 'removeThis' are removed.
    */
   public static String remove(String source, char removeThis)
   {
      int pos = 0, index = 0;
      String out = "";
      while (pos < source.length())
      {
         pos = source.indexOf(removeThis, index);
         if (pos >= 0)
         {
            out += source.substring(index,pos);
            index = pos+1;
         }
         else
         {
            out += source.substring(index);
            return(out);
         }
      }
      return(source);
   }
   /**
     Checks if the value is true.  If the value is "true" or "1" or != "0" it's true.
    * Any other value including null is considered false.
    * @value is the string to check for a true of false.
    * @return true if value is 'true' or != 0
    */
   public static boolean isTrue(String value)
   {
      try
      {
         if (value == null || value.length() == 0)
         {
            return(false); // considered not true
         }
         if (value.equalsIgnoreCase("true") || Integer.parseInt(value) != 0)
         {
            return(true);
         }
      }
      catch (Exception ex)
      {}; // don't do anything, considered not true
      return(false);
   }
   /**
    * Checks if the value is false.  If the value is "false" or == "0" it's false.
    * Any other value including null is considered not false.
    * @value is the string to check for a true of false.
    * @return true if value is 'false' or == 0
    */
   public static boolean isFalse(String value)
   {
      try
      {
         if (value == null || value.length() == 0)
         {
            return(false); // not false
         }
         if (value.equalsIgnoreCase("false") || Integer.parseInt(value) == 0)
         {
            return(true);
         }
      }
      catch (Exception ex)
      {}; // don't do anything, considered not false
      return(false);
   }
   
   /**
    * will try and determine if value is true of false.
    * @return true if value = 'true' or > 0 and false if value = 'false' or 0
    */
   public static boolean getBoolValue(String value)
   {
      if (Text.isTrue(value)) return(true);
      if (Text.isFalse(value)) return(false);
      return(false); // donno what it is so assuming false.
   }
   
   
   /**
    * This takes a double and formats it with ',' 1000s seperator and ensures
    * 2 decimal places.
    */
   public static String doubleToMoneyString(double d)
   {
      DecimalFormat df = new DecimalFormat( "#,###,##0.00#" );
      return(df.format(d));
   }
   
   /**
    * This takes a money string and removes any ',' 1000s seperator.
    */
   public static String moneyToDoubleString(String d)
   {
      // remove any , 1000 seperators
      StringBuffer sb = new StringBuffer();
      for (int iLoop = 0; iLoop < d.length(); iLoop++)
      {
         if (d.charAt(iLoop) != ',')
            sb.append(d.charAt(iLoop));
      }
      return(sb.toString());
   }
   
   /**
    * This takes a money string and removes any ',' 1000s seperator and then
    * returns it as a double.
    */
   public static double moneyToDouble(String s)
   {
      String ss = Text.moneyToDoubleString(s);
      double d = Double.parseDouble(ss);
      return(d);
   }
   
   /**
    * Trim a String. Wont fail, checks if String is not null before trimming.
    * @param src - the String to trim, may be null.
    * @return trimmed src or null if src is null
    */
   public static String trim(String src) {
	   if (src != null)
		   return src.trim();
	   return  null;
   }
   
   
	/**
	 * skip over white space including ' ' 0x0a and 0x0d
	 */
   public static boolean isWhiteSpaceChar(char c) {
	   if (c == ' ' || c == 0x09 || c == 0x0a || c == 0x0d) {
		   return true;
	   } else {
		   return false;
	   }
   }
}
