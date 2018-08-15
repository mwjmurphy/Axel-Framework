/*******************************************************************************
 *
 *                Copyright (c) 1998-2002 almur company.
 *                          All Rights Reserved
 *
 *        This Is unpublished proprietary source code of the almur company.
 *
 *******************************************************************************
 *
 * $Revision: 1.1.1.1 $
 * $Author: mike $
 * $Date: 2005/10/24 16:36:57 $
 * $Source: C:/cvsroot/dev/javadev/amco/almursdk/almur/sdk/utils/http/HttpSessionManager.java,v $
 ******************************************************************************/

package org.xmlactions.web.http;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;



/**
 * <p>Handles http session attributes.  These are used to maintain
 * a record of session parameters such as the user login name, the
 * password, if the login was successful and variables related to
 * a statefull session</p>
 *
 * @see org.xmlactions.sdk.utils.http.HttpProfileSessionManager
 *
 * @author Michael Murphy
 * @since  22 July 2002
 */

public class HttpSessionManager extends HashMap implements HttpSessionBindingListener
{
	
	private static final Logger logger = LoggerFactory.getLogger(HttpSessionManager.class);
	   
	
   /**
    * @since 12 July 2001
    * <p/>This gets called when we setAttribute(this) to the session.
    */
   public void valueBound(HttpSessionBindingEvent event)
   {
      logger.debug("valueBound");
   }



   /**
    * @since 12 July 2001
    * <p/>This gets called when the session unbinds this object.  Usually
    * when the session is closed or another setAttribute call is made.
    */
   public void valueUnbound(HttpSessionBindingEvent event)
   {
      // Here we can free up any session related stuff.
      logger.debug("valueUnbound:");
   }



   /**
    * store an entry into the Hashmap and update it back into the session
    * @param request this is where we store our session variables
    * @param key this is the HashMap Key indentifier
    * @param value this is the Key value
    */
   public static void store(HttpServletRequest request, String key, Object value)
   {
      HttpSessionManager userInfo = getUserInfo(request);
      userInfo.put(key, value);
      putUserInfo(request, userInfo);
   }


   /**
    * retrieve an entry from the Hashmap and return it to the user
    * @param request this is where we store our session variables
    * @param key this is the HashMap Key indentifier
    * @return the key value as an Object
    */
   public static Object retrieve(HttpServletRequest request, String key)
   {
      HttpSession session = request.getSession(true);
      HttpSessionManager userInfo = getUserInfo(request);
      return(userInfo.get(key));
   }


   /**
    * This gets the session information related to this session from the HTTP Session.
    * Our intention is to store all the information about this user into the session,
    * making it possible to provide a statefull connection between the service and the
    * client.
    */
   public static HttpSessionManager getUserInfo(HttpServletRequest request)
   {
      HttpSession session = request.getSession(true);
      HttpSessionManager userInfo = (HttpSessionManager)session.getAttribute("UserInfo");
      // Logging stuff
      if ( userInfo != null )
      {
         logger.debug(userInfo.toString());
      }
      else
      {
         logger.debug("no userInfo");
         // emm no hashmap, must be first time called
         userInfo = new HttpSessionManager();
         putUserInfo(request, userInfo);
      }
      return (userInfo);
   }

   /**
    * This stores the user information back into the http session.  This is part of the
    * statefull process remembering the client information such as login name and password,
    * user id etc.
    * @param request the HttpServletRequest that contains our
    * HttpSessionManager with the HashMap information
    * @param userInfo is the HttpSessionManager we want stored in the http session.
    */
   public static void putUserInfo(HttpServletRequest request, HttpSessionManager userInfo)
   {
      // HttpSession session = request.getSession(true);
      HttpSession session = request.getSession();
      session.setAttribute("UserInfo", userInfo);
   }



   /**
    * This will return an xml list of all entries in the hashmap
    * @return the XML list of all entries in the hashmap
    */
   public String toString()
   {
      String back = "<HttpSessionManager>";

      Set set = entrySet();
      Iterator i = set.iterator();
      while ( i.hasNext() )
      {
         Map.Entry me = (Map.Entry)i.next();
         back += "<entry><key>" + me.getKey() + "</key><value>" + me.getValue() + "</value></entry>";
      }
      back += "</HttpSessionManager>";
      return(back);
   }
}
