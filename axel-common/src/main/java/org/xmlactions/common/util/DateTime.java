
package org.xmlactions.common.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.xmlactions.common.text.Text;





/**
 * Date and Time functionality
 * 
 * @author  Mike Murphy, Paul Murphy
 * @version 1.0
 * @since   29 April 2001
 */
public class DateTime
{
   static final int daysInMonth[] =
   {
      31,28,31,30,31,30,31,31,30,31,30,31
   };
   
   /**
    * @since 29 Apr 2001
    * <p>get the number of days in a month
    * @param iMonth ranging from 0-11 not 1-12
    * @param iYear 4 digits as in 2001 and not 01
    * @return number of days in month
    */
   public static int getDaysInMonth(int iMonth, int iYear)
   {
      
      if (iMonth != 1)   // if it's not Feb then get day from array
      {
         return(daysInMonth[iMonth]);
      }
      // now we have to figure out howmany days in Feburary
      if (isLeapYear(iYear))
      {
         return(29);
      }
      // Feburary and not a leap year.
      return(28);
   }
   
   /**
    * @since 29 Apr 2001
    * <p>check if the entered year is a leap year
    * @param iYear 4 digits as in 2001 and not 01
    * @return true if the entered year is a leap year otherwise false.
    */
   public static boolean isLeapYear(int iYear)
   {
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.MONTH, 1 );   // Set month to feb
      calendar.set(Calendar.YEAR, iYear);
      calendar.set(Calendar.DATE, 29);
      if (calendar.get(Calendar.DATE) == 29)
      {
         // we have a leap year and feb = 29
         return(true);
      }
      return(false);
   }
   
   /**
    * @since 29 Apr 2001
    * <p>get the day of the week 1 - 7.  Developer must ensure the validity of
    * the parameters entered.
    * @param iDay ranging from 1-28/31
    * @param iMonth ranging from 0-11 not 1-12
    * @param iYear 4 digits as in 2001 and not 01
    * @return 1 to 7 where 1=Sunday, 2=Monday etc.
    */
   public static int getDayOfWeek(int iDay, int iMonth, int iYear)
   {
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.DATE, iDay );
      calendar.set(Calendar.MONTH, iMonth );
      calendar.set(Calendar.YEAR, iYear);
      return(calendar.get(Calendar.DAY_OF_WEEK));
      
   }
   
   /**
    * @since 30 Apr 2001
    * <p>Change the date by a number of days.
    * @param iDay ranging from 1-28/31
    * @param iMonth ranging from 0-11 not 1-12
    * @param iYear 4 digits as in 2001 and not 01
    * @param iChangeAmount is the number of days to change the date by.
    * @return 1 to 7 where 1=Sunday, 2=Monday etc.
    */
   public static Calendar changeDate(int iDay, int iMonth, int iYear, int iChangeAmount)
   {
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.DATE, iDay );
      calendar.set(Calendar.MONTH, iMonth );
      calendar.set(Calendar.YEAR, iYear);
      calendar.add(calendar.DATE, iChangeAmount);
      return(calendar);
   }
   
   /**
    * @since 30 Apr 2001
    * <p>Change the date by a number of days.
    * @param base is the base date
    * @param iChangeAmount amount to change date by in days
    * @return 1 to 7 where 1=Sunday, 2=Monday etc.
    */
   public static Calendar changeDate(Calendar base, int iChangeAmount)
   {
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.DATE, base.get(Calendar.DATE));
      calendar.set(Calendar.MONTH, base.get(Calendar.MONTH));
      calendar.set(Calendar.YEAR, base.get(Calendar.YEAR));
      calendar.add(calendar.DATE, iChangeAmount);
      return(calendar);
   }
   
   /*
   public  String milliSecondsToDate(long millis)
   {
      Calendar c = Calendar.getInstance();
      c.setTimeInMillis(millis);
      StringBuffer sb = new StringBuffer();
      sb.append(c.get(Calendar.YEAR) + "-");
      sb.append((c.get(Calendar.MONTH) + 1) + "-");
      sb.append(c.get(Calendar.DAY_OF_MONTH) + " ");
      sb.append(c.get(Calendar.HOUR) + ":");
      sb.append(c.get(Calendar.MINUTE) + ":");
      sb.append(c.get(Calendar.SECOND) + ":");
      sb.append(c.get(Calendar.MILLISECOND));
      return(sb.toString());
   }*/
   
   /**
    * gets the current time on milliseconds
    * @return current time in milliseconds
    */
   public static long getDateInMillis()
   {
      return(System.currentTimeMillis());
   }
   
   /**
    * Convert the current time in milliseconds to a string.
    * @return the time converted to a string.
    */
   public static String getDate()
   {
      return(getDate(getDateInMillis()));
      
   }
   /**
    * Convert a time in milliseconds to a string.
    * @param milliseconds is the time in milliseconds.
    * @return the time converted to a string.
    */
   public static String getDate(long milliseconds)
   {
      return(getDate(milliseconds, "-", ":"));
   }
   /**
    * Convert a time in milliseconds to a string.<br/>
    * @param milliseconds is the time in milliseconds.
    * @param dateSeperator is the seperator char to use between year mon day
    * @param timeSeperator is the seperator char to use between hour min sec
    * @return the time converted to a string.
    */
   public static String getDate(long milliseconds, String dateSeperator, String timeSeperator)
   {
      return(getDate(milliseconds, "dd" + dateSeperator + "mo" + dateSeperator + "yy " + 
                                   "hh" + timeSeperator + "mm" + timeSeperator + "ss"));
   }
   /**
    * Convert a time in milliseconds to a string.<br/>
    * The recognised formats are:<br/>
    * 'dd' = day <br/>
    * 'mo' = month <br/>
    * 'MO' = abbreviated month text <br/>
    * 'yy' = year <br/>
    * 'YY' = year (2 digit) <br/>
    * 'hh' = hour <br/>
    * 'mm' = minute <br/>
    * 'ss' = sec <br/>
    *
    * @param milliseconds is the time in milliseconds.
    * @param format is the format of the date we want returned, i.e. dd-MO-yy hh:mm:ss = day number,
    *        MO = month as text etc. 'dd' = day, 'mo' = month, 'MO' = month text
    *       'yy' = year, 'hh' = hour, 'mm' = minute, 'ss' = sec
    * @return the time converted to a string.
    */
   public String out_getDate(long milliseconds, String format)
   {
      //System.out.println("getDate start");
      if (milliseconds <= 0)
      {
         return(""); // no time
      }
      Text text = new Text();
      // dateFormat = "YYYY-MM-DD HH:MM:SS";
      // dateFormat = "YYYYMMDD HHMMSS";
      StringBuffer sb = new StringBuffer();
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(milliseconds);
      for (int iLoop = 0 ; iLoop < format.length(); iLoop+=3)
      {
         String s = format.substring(iLoop, iLoop+2);
         //Log.getInstance().debug(JS.getCurrentMethodName_static() + " format:'" + format + "' s:" + s);
         //System.out.println("format:'" + format + "' s:" + s);
         if (s.equals("dd")) sb.append(text.make2DigitNumber(cal.get(Calendar.DAY_OF_MONTH)));
         else if (s.equals("mo")) sb.append(text.make2DigitNumber(cal.get(Calendar.MONTH) + 1));
         else if (s.equals("MO")) sb.append(getMonthText(cal.get(Calendar.MONTH)));
         else if (s.equals("yy")) sb.append(text.makeNDigitNumber(cal.get(Calendar.YEAR), 4));
         else if (s.equals("hh")) sb.append(text.make2DigitNumber(cal.get(Calendar.HOUR_OF_DAY)));
         else if (s.equals("mm")) sb.append(text.make2DigitNumber(cal.get(Calendar.MINUTE)));
         else if (s.equals("ss")) sb.append(text.make2DigitNumber(cal.get(Calendar.SECOND)));
         if (iLoop+2 < format.length()) sb.append(format.charAt(iLoop+2));
      }
      System.out.println("getDate finish:" + sb.toString());
      return(sb.toString());
   }
   /**
    * Convert a time in string format to milliseconds<br/>
    * The recognised formats are:<br/>
    * 'dd' = day <br/>
    * 'mo' = month <br/>
    * 'MO' = abbreviated month text <br/>
    * 'yy' = year <br/>
    * 'YY' = year (2 digit) <br/>
    * 'hh' = hour <br/>
    * 'mm' = minute <br/>
    * 'ss' = sec <br/>
    * '..' = milli <br/>
    *
    * @param milliseconds is the date we want converted
    * @param outFormat is the format of the date we want returned, i.e. dd-MO-yy hh:mm:ss = day number,
    *        MO = month as text etc. 'dd' = day, 'mo' = month, 'MO' = month text
    *       'yy' = year, 'hh' = hour, 'mm' = minute, 'ss' = sec, '..' = millisecond
    * @return the date converted to milliseconds.
    */
   public static String getDate(long milliseconds, String outFormat)
   {
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(milliseconds);
      StringBuffer sb = new StringBuffer();
      char lastChar = '\0';
      int index = 0;
      for (int iLoop = 0 ; iLoop < outFormat.length(); iLoop++ )
      {
         char c = outFormat.charAt(iLoop);
         if (c == 'd')
         {
            if(lastChar == 'd')
            {
               // we got dd
               //cal.set(cal.DAY_OF_MONTH, Integer.parseInt(date.substring(index-1, index+1)));
               sb.append(Text.make2DigitNumber(cal.get(cal.DAY_OF_MONTH)));
            }
         }
         else if (c == 'm')
         {
            if (lastChar == 'm')
            {
               // we got mm
               // cal.set(cal.MINUTE, Integer.parseInt(date.substring(index-1, index+1)));
               sb.append(Text.make2DigitNumber(cal.get(cal.MINUTE)));
            }
         }
         else if (c == 'o')
         {
            if (lastChar == 'm')
            {
               // we got mo
               //cal.set(cal.MONTH, Integer.parseInt(date.substring(index-1, index+1)) -1 );
               sb.append(Text.make2DigitNumber(cal.get(cal.MONTH) + 1));
            }
         }
         else if (c == 'O')
         {
            if (lastChar == 'M')
            {
               // we got MO
               //cal.set(cal.MONTH, this.getMonthFromAbbText(date.substring(index-1, index+2)));
               sb.append(DateTime.getAbbMonthText(cal.get(cal.MONTH)));
            }
         }
         else if (c == 'y')
         {
            if (lastChar == 'y')
            {
               // we got yy
               //cal.set(cal.YEAR, Integer.parseInt(date.substring(index-1, index+3)));
               sb.append(cal.get(cal.YEAR));
            }
         }
         else if (c == 'Y')
         {
            if (lastChar == 'Y')
            {
               // we got YY
               // cal.set(cal.YEAR, 2000 + Integer.parseInt(date.substring(index-1, index+1)));
               sb.append( ("" + cal.get(cal.YEAR)).substring(2,4));
            }
         }
         else if (c == 'h')
         {
            if (lastChar == 'h')
            {
               // we got hh
               sb.append(Text.make2DigitNumber(cal.get(cal.HOUR_OF_DAY)));
            }
         }
         else if (c == 's')
         {
            if (lastChar == 's')
            {
               // we got ss
               sb.append(Text.make2DigitNumber(cal.get(cal.SECOND)));
            }
         }
         else if (c == '.')
         {
            if (lastChar == '.')
            {
               // we got ..
               sb.append(Text.make2DigitNumber(cal.get(cal.MILLISECOND)));
            }
         }
         else if (! (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9') )
         {
            sb.append(c);
         }
         lastChar = c;
         index++;
      }
      return(sb.toString());
   }
   
   /**
    * Convert a time in string format to milliseconds<br/>
    * The recognised formats are:<br/>
    * 'dd' = day <br/>
    * 'mo' = month <br/>
    * 'MO' = abbreviated month text <br/>
    * 'yy' = year <br/>
    * 'YY' = year (2 digit) <br/>
    * 'hh' = hour <br/>
    * 'mm' = minute <br/>
    * 'ss' = sec <br/>
    * '..' = millisecond <br/>
    *
    * @param date is the date we want converted to milliseconds
    * @param inFormat is the format of the date we want returned, i.e. dd-MO-yy hh:mm:ss = day number,
    *        MO = month as text etc. 'dd' = day, 'mo' = month, 'MO' = month text
    *       'yy' = year, 'hh' = hour, 'mm' = minute, 'ss' = sec, '..' = millisecond
    * @return the date converted to milliseconds.
    */
   public static long getDate(String date, String inFormat)
   {
      Calendar cal = Calendar.getInstance();
      StringBuffer sb = new StringBuffer();
      char lastChar = '\0';
      int index = 0;
      for (int iLoop = 0 ; iLoop < inFormat.length(); iLoop++ )
      {
         char c = inFormat.charAt(iLoop);
         if (c == 'd')
         {
            if(lastChar == 'd')
            {
               // we got dd
               cal.set(cal.DAY_OF_MONTH, Integer.parseInt(date.substring(index-1, index+1)));
            }
         }
         else if (c == 'm')
         {
            if (lastChar == 'm')
            {
               // we got mm
               cal.set(cal.MINUTE, Integer.parseInt(date.substring(index-1, index+1)));
            }
         }
         else if (c == 'o')
         {
            if (lastChar == 'm')
            {
               // we got mo
               cal.set(cal.MONTH, Integer.parseInt(date.substring(index-1, index+1)) -1 );
            }
         }
         else if (c == 'O')
         {
            if (lastChar == 'M')
            {
               // we got MO
               cal.set(cal.MONTH, DateTime.getMonthFromAbbText(date.substring(index-1, index+2)));
               index++;
            }
         }
         else if (c == 'y')
         {
            if (lastChar == 'y')
            {
               // we got yy
               cal.set(cal.YEAR, Integer.parseInt(date.substring(index-1, index+3)));
               index+=2;
            }
         }
         else if (c == 'Y')
         {
            if (lastChar == 'Y')
            {
               // we got YY
               cal.set(cal.YEAR, 2000 + Integer.parseInt(date.substring(index-1, index+1)));
            }
         }
         else if (c == 'h')
         {
            if (lastChar == 'h')
            {
               // we got hh
               cal.set(cal.HOUR_OF_DAY, Integer.parseInt(date.substring(index-1, index+1)));
            }
         }
         else if (c == 's')
         {
            if (lastChar == 's')
            {
               // we got ss
               cal.set(cal.SECOND, Integer.parseInt(date.substring(index-1, index+1)));
            }
         }
         else if (c == '.')
         {
            if (lastChar == '.')
            {
               // we got ss
               cal.set(cal.MILLISECOND, Integer.parseInt(date.substring(index-1, index+1)));
            }
         }
         lastChar = c;
         index++;
      }
      //Log.getInstance().debug(JS.getCurrentMethodName_static() + " date:" + date + " calendar:" + cal.getTime().toString());
      return(cal.getTimeInMillis());
   }
   /**
    * Convert a time in string format to milliseconds<br/>
    * The recognised formats are:<br/>
    * 'dd' = day <br/>
    * 'mo' = month <br/>
    * 'MO' = abbreviated month text <br/>
    * 'yy' = year 4 digit<br/>
    * 'YY' = year (2 digit) <br/>
    * 'hh' = hour <br/>
    * 'mm' = minute <br/>
    * 'ss' = sec <br/>
    *
    * @param date is the date we want converted to milliseconds
    * @param inFormat is the format of the date we want returned, i.e. dd-MO-yy hh:mm:ss = day number,
    *        MO = month as text etc. 'dd' = day, 'mo' = month, 'MO' = month text
    *       'yy' = year, 'hh' = hour, 'mm' = minute, 'ss' = sec
    * @param outFormat is the format of the date we want returned, i.e. dd-MO-yy hh:mm:ss = day number,
    *        MO = month as text etc. 'dd' = day, 'mo' = month, 'MO' = month text
    *       'yy' = year, 'hh' = hour, 'mm' = minute, 'ss' = sec
    * @return the date converted to milliseconds.
    */
   public static String getDate(String date, String inFormat, String outFormat)
   throws Exception
   {
      return (DateTime.getDate(DateTime.getDate(date, inFormat), outFormat));
   }

   /**
    * <p>Get month text.
    * @param monthNum between 0 and 11
    * @return month like January or Feburary
    */
   public static String getMonthText(int monthNum)
   {
      String months[] = {"January", "Feburary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
      if ( monthNum >= 0 && monthNum < 12 )
      {
         return(months[monthNum]);
      }
      return("");
   }
   
   public static String getDayText(int iDayOfWeek)
   {
      String days[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
      if ( iDayOfWeek >= 0 && iDayOfWeek < 7 )
      {
         return(days[iDayOfWeek]);
      }
      return("");
   }
   public static String getAbbDayText(int iDayOfWeek)
   {
      String days[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
      if ( iDayOfWeek >= 0 && iDayOfWeek < 7 )
      {
         return(days[iDayOfWeek]);
      }
      return("");
   }
   
   /**
    * <p>Get abbreviated month text.
    * @param monthNum between 0 and 11
    * @return abbreviated month like JAN or FEB
    */
   public static String getAbbMonthText(int monthNum)
   {
      String months[] = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
      if ( monthNum >= 0 && monthNum < 12 )
      {
         return(months[monthNum]);
      }
      return("");
   }
   
   /**
    * <p>Get month number from abbreviated month text.  This is an ignoreCase comparision.</p>
    * @param month abbreviated format for month like "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
    * @return month number 0 for January or -1 if no match found
    */
   public static int getMonthFromAbbText(String month)
   {
      String months[] = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
      for (int iLoop = 0 ; iLoop < months.length; iLoop++)
      {
         if (months[iLoop].equalsIgnoreCase(month))
            return(iLoop);
      }
      return(-1);
   }
   
   /**
    * <p>Get the database format for storing a date.
    * @param day = day of month
    * @param month = month of year
    * @param year = year
    * @param time = time between 0:0:0 and 23:59:59
    * @return the formatted string entry for date and time<br>
    * i.e. '25 JAN 1958 10:00:00'
    */
   public static String getSQLDateFormat(int day, int month, int year, String time)
   {
      String dateString;
      dateString = "'" + day + " " + getAbbMonthText(month) + " " + year;
      if ( time.length() > 0 )
      {
         dateString += " " + time;
      }
      dateString += "'";
      
      return(dateString);
   }
   
   /**
    * <p>Get the database format for storing a date.
    * @param sDay = day of month
    * @param sMonth = month of year
    * @param sYear = year
    * @param sTime = time between 0:0:0 and 23:59:59
    * @return the formatted string entry for date and time<br>
    * i.e. '25 JAN 1958 10:00:00'
    */
   public static String getSQLDateFormat(String sDay, String sMonth, String sYear, String sTime)
   {
      String dateString;
      dateString = "'" + sDay + " " + getAbbMonthText(Integer.parseInt(sMonth)) + " " + sYear;
      if ( sTime.length() > 0 )
      {
         dateString += " " + sTime;
      }
      dateString += "'";
      
      return(dateString);
   }
   
   /**
    * Validates the a date string.  The format will determine which
    * characters in the date should be numbers.
    *
    * @param date is the date string we want to validate
    * @param format is the expected format of the date string.  The format
    * should contain a 'n' for a number, a 'l' for a letter and a 'x' for anything else
    *
    * @return a string of the error if the formats is not correct else a null if the format is correct.
    */
   public static String validateDate(String date, String format)
   {
      if (format.length() > date.length())
      {
         return("valideDate error: date[" + date + "] is shorter than format string[" + format + "]");
      }
      for (int iLoop = 0; iLoop < format.length(); iLoop++)
      {
         char c = format.charAt(iLoop);
         char d = date.charAt(iLoop);
         if (c == 'n')
         {
            if (d <= '0' && d >= '9')
            {
               return("valideDate error: illegal number char[" + d + "] at index " + iLoop + " in date[" + date + "]");
            }
         }
         else if (c == 'l')
         {
            if (d <= 'a' && d >= 'z' || d <= 'A' && d >= 'Z')
            {
               return("valideDate error: illegal alphabetic char[" + d + "] at index " + iLoop + " in date[" + date + "]");
            }
         }
         else
         {
            if (d <= '0' && d >= '9' ||
                d <= 'a' && d >= 'z' || d <= 'A' && d >= 'Z')
            {
               return("valideDate error: illegal non alphanumberic char[" + d + "] at index " + iLoop + " in date[" + date + "]");
            }
         }
      }
      return(null);
   }
   
   /**
    * <p>Convert a month in string format (January) to a number.
    * @param sMonthOfYear
    * @return month number between 0 and 11 or -1 on error
    * @since 4/13/2001 11:27PM
    */
   public static int getMonthNumber(String sMonthOfYear)
   {
      String[] sMonthText = {"January", "Feburary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
      int iLoop = 0;
      for( iLoop = 0; iLoop < 12; iLoop++ )
      {
         if(sMonthOfYear.equals(sMonthText[iLoop]))
            return iLoop;
      }
      return -1;
   }
   
   /**
    * Convert a millisecond time to HH:MM:SS
    */
   public static String milliTimeToShowTime(long milliTime)
   {
      long hours, mins, secs;
      StringBuffer sb = new StringBuffer();
      milliTime /= 1000;
      hours = milliTime / 3600;
      mins = (milliTime - (hours * 3600)) / 60;
      secs = (milliTime - (hours * 3600) - (mins * 60));
      sb.append("" + Text.make2DigitNumber((int)hours) + ":");
      sb.append("" + Text.make2DigitNumber((int)mins) + ":");
      sb.append("" + Text.make2DigitNumber((int)secs));
      return(sb.toString());
   }

   /**
    * Convert a millisecond time to HH:MM:SS:MMMM
    */
   public String milliTimeToFullShowTime(long milliTime)
   {
      long hours, mins, secs, tics;
      StringBuffer sb = new StringBuffer();
      tics = milliTime % 1000;
      milliTime /= 1000;
      hours = milliTime / 3600;
      mins = (milliTime - (hours * 3600)) / 60;
      secs = (milliTime - (hours * 3600) - (mins * 60));
      sb.append("" + Text.make2DigitNumber((int)hours) + ":");
      sb.append("" + Text.make2DigitNumber((int)mins) + ":");
      sb.append("" + Text.make2DigitNumber((int)secs) + ":");
      sb.append("" + Text.makeNDigitNumber((int)tics, 4));
      return(sb.toString());
   }

   /**
    * @author mmurphy
    * @since 4 April 2006
    * Converts a date String to a datetime class.
    * @param sDate is the date to set format = 'dd-mm-yyyy hh:mm:ss'
    * @return datetime class containing the parsed parts.
    */
   public datetime convertDateStringToDateTime(String sDate)
   {
      StringBuffer day = new StringBuffer();
      StringBuffer mon = new StringBuffer();
      StringBuffer year = new StringBuffer();
      StringBuffer hour = new StringBuffer();
      StringBuffer min = new StringBuffer();
      StringBuffer sec = new StringBuffer();
      
      int t = 0;
      for (int iLoop = 0 ; iLoop < sDate.length(); iLoop++)
      {
         char c = sDate.charAt(iLoop);
         if (c < '0' || c > '9')
         {
            // not a number
            t++;
         }
         else
         {
            // got a number
            if (t == 0)
            {
               day.append(c);
            }
            else if (t == 1)
            {
               mon.append(c);
            }
            else if (t == 2)
            {
               year.append(c);
            }
            else if (t == 3)
            {
               hour.append(c);
            }
            else if (t == 4)
            {
               min.append(c);
            }
            else if (t == 5)
            {
               sec.append(c);
            }
         }
      }
      datetime dt = new datetime(day.toString(), mon.toString(), year.toString(),
                                 hour.toString(), min.toString(), sec.toString());
      return(dt);
   }
   

   public class datetime
   {
      public int iDay, iMonth, iYear, iHour, iMinute, iSecond;
      
      public datetime(int iDay, int iMonth, int iYear, int iHour, int iMinute, int iSecond)
      {
         this.iDay = iDay;
         this.iMonth = iMonth;
         this.iYear = iYear;
         this.iHour = iHour;
         this.iMinute = iMinute;
         this.iSecond = iSecond;
      }
      public datetime(String sDay, String sMonth, String sYear, String sHour, String sMinute, String sSecond)
      {
         Text text = new Text();
         this.iDay = Text.atoi(sDay, 1);
         this.iMonth = Text.atoi(sMonth, 0);
         this.iYear = Text.atoi(sYear, 0);
         this.iHour = Text.atoi(sHour, 0);
         this.iMinute = Text.atoi(sMinute, 1);
         this.iSecond = Text.atoi(sSecond, 1);
      }
   }
   

   /*
   public static void main(String args[])
   throws Exception
   {
      DateTime dt = new DateTime();
      Log.getInstance().debug(JS.getCurrentMethodName_static() + " getDate: 25-jan->XSD-1958 " + dt.getDate("25-jan->XSD-1958", "dd-MO......yy", "yy-mo-dd"));
      long milliseconds;
      milliseconds = dt.getDate("2006-01-25", "yy-mo-dd");
      Log.getInstance().debug(JS.getCurrentMethodName_static() + " date:" + dt.getDate(milliseconds));
      Log.getInstance().debug(JS.getCurrentMethodName_static() + " date:" + dt.getDate(milliseconds,"dd/mo/yy"));
      milliseconds = dt.getDate("1958-01-25", "yy-mo-dd");
      Log.getInstance().debug(JS.getCurrentMethodName_static() + " date:" + dt.getDate(milliseconds));
      Log.getInstance().debug(JS.getCurrentMethodName_static() + " date:" + dt.getDate(milliseconds,"dd/mo/yy"));
      milliseconds = dt.getDate("19580125", "yymodd");
      Log.getInstance().debug(JS.getCurrentMethodName_static() + " date:" + dt.getDate(milliseconds));
      Log.getInstance().debug(JS.getCurrentMethodName_static() + " date:" + dt.getDate(milliseconds,"dd/mo/yy"));
      //Log.getInstance().debug(JS.getCurrentMethodName_static() + " " + dt.getDate("2006-01-25", "yy-mo-dd", "dd-mo-yy"));
      //Log.getInstance().debug(JS.getCurrentMethodName_static() + " " + dt.getDate("25-01-1958", "dd-mo-yy", "dd-mo-yy"));
      //Log.getInstance().debug(JS.getCurrentMethodName_static() + " " + dt.getDate("25011958", "ddmoyy", "dd-mo-yy"));
      //Log.getInstance().debug(JS.getCurrentMethodName_static() + " " + dt.getDate("25011958 12:11:10", "ddmoyy hh:mm:ss", "dd-mo-yy hh:mm:ss"));
      //Calendar cal = Calendar.getInstance();
      //Log.getInstance().debug("getDate:" + dt.getDate(cal.getTimeInMillis(), "dd-MO-yy hh:mm:ss"));
      
      //Log.getInstance().debug("dt '1000' = " + dt.milliTimeToShowTime(1000));
      //Log.getInstance().debug("dt '10000' = " + dt.milliTimeToShowTime(10000));
      //Log.getInstance().debug("dt '100000' = " + dt.milliTimeToShowTime(100000));
      //Log.getInstance().debug("dt '1000000' = " + dt.milliTimeToShowTime(1000000));
      //Log.getInstance().debug("dt '10000000' = " + dt.milliTimeToShowTime(10000000));
      //Log.getInstance().debug("dt '100000000' = " + dt.milliTimeToShowTime(100000000));
       
   }
    */
   
   /**
    * Format the output for date time using SimpleDateFormat<br/>
    * Customized Date and Time Formats Pattern  Output  <br/>
    *  dd.MM.yy  09.04.98  <br/>
    *  yyyy.MM.dd G 'at' hh:mm:ss z  1998.04.09 AD at 06:15:55 PDT  <br/>
    *  EEE, MMM d, ''yy  Thu, Apr 9, '98  <br/>
    *  h:mm a  6:15 PM  <br/>
    *  H:mm  18:15  <br/>
    *  H:mm:ss:SSS  18:15:55:624  <br/>
    *  K:mm a,z  6:15 PM,PDT  <br/>
    *  yyyy.MMMMM.dd GGG hh:mm aaa  1998.April.09 AD 06:15 PM  <br/>
    * 
    * @param datetime
    * @param pattern
    * @return formatted date
    */
   public static String formatDate(long datetime, String pattern)
   {
      Date today;
      String output;
      SimpleDateFormat formatter;

      formatter = new SimpleDateFormat(pattern);
      today = new Date(datetime);
      output = formatter.format(today);
      return(output);
   }
}


