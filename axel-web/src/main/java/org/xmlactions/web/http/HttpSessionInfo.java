/*
 * HttpSessionInfo.java
 *
 * Created on 24 March 2006, 16:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xmlactions.web.http;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author mike
 */
public class HttpSessionInfo
{
	
	private static final Logger logger = LoggerFactory.getLogger(HttpSessionInfo.class);
   
   /**
    * Creates a new instance of HttpSessionInfo
    * Requires lib servlet-3.3.jar
    */
   public HttpSessionInfo()
   {
   }
   
   /**
    * This will return a string containing all the http servlet request
    * parameters and all http session attributes if a session already
    * exists in the http servlet request.
    * @param request is the HttpServletRequest from the servlet
    * @return a string containing all the servlet parameters and all session
    *         attributes.
    */
   public static String sysInfo( HttpServletRequest request)
   {
      //log.debug(this.getClassName() + ".[sysInfo] start");
      StringBuffer sb = new StringBuffer();
      // lets look at the request data
      
      sb.append("[HttpServletRequest] Request Information");
      sb.append("\n - getAuthType:" + request.getAuthType());
      sb.append("\n - getCharacterEncoding:" + request.getCharacterEncoding());
      sb.append("\n - getContentType:" + request.getContentType());
      sb.append("\n - getContentLength:" + request.getContentLength());
      sb.append("\n - getContextPath:" + request.getContextPath());
      sb.append("\n - getPathInfo:" + request.getPathInfo());
      sb.append("\n - getPathTranslated:" + request.getPathTranslated());
      sb.append("\n - getProtocol:" + request.getProtocol());
      sb.append("\n - getQueryString:" + request.getQueryString());
      sb.append("\n - getRemoteAddr:" + request.getRemoteAddr());
      sb.append("\n - getRemoteHost:" + request.getRemoteHost());
      // sb.append("\n - getRemotePort:" + request.getRemotePort());
      sb.append("\n - getRemoteUser:" + request.getRemoteUser());
      sb.append("\n - getRequestURI:"+ request.getRequestURI());
      sb.append("\n - getRequestedSessionId:" + request.getRequestedSessionId());
      sb.append("\n - getScheme:" + request.getScheme());
      sb.append("\n - getServerName:" + request.getServerName());
      sb.append("\n - getServerPort:" + request.getServerPort());
      sb.append("\n - getServletPath:" + request.getServletPath());
      sb.append("\n---");
      /*
      if (request.getContentLength() > 0)
      {
         byte buffer [] = new byte[request.getContentLength()];
         try
         {
            request.getInputStream().read(buffer);
            sb.append("InputStream:" + new String(buffer));
         }
         catch (Exception ex)
         {
            Log.getInstance().error(ex);
         }
      }
      sb.append("\n---");
       */

      sb.append("\n[HttpServletRequest] Request Headers");
      Enumeration<?> enumeration = request.getHeaderNames();
      while (enumeration.hasMoreElements()) {
    	  Object obj = (String)enumeration.nextElement();
    	  if (obj instanceof String) {
    		  Object value = request.getHeader((String)obj);
    		  sb.append("\n - " + obj + ":" + value);
    	  } else {
    		  sb.append("\n - " + obj);
    	  }
      }

      try
      {
         
         if (request == null)
         {
            sb.append("[HttpServletRequest] no request\n");
         }
         else
         {
            sb.append('\n');
            HttpSession session = request.getSession(false);
            Enumeration e = request.getParameterNames();
            while(e.hasMoreElements())
            {
               String key = (String)e.nextElement();
               Object value = request.getParameter(key);
               if ( value instanceof String )
               {
                  sb.append("[HttpServletRequest] parameter [" + key + "]=[" + value + "]\n");
               }
               else
               {
                  sb.append("[HttpServletRequest] parameter [" + key + "]=" + value == null ? "[null]" : "(" + value.getClass().getName() + ")[" + value.toString() + "]\n");
               }
            }
            e = request.getAttributeNames();
            while(e.hasMoreElements())
            {
               String key = (String)e.nextElement();
               Object value = request.getAttribute(key);
               if ( value instanceof String )
               {
                  sb.append("[HttpServletRequest] attribute [" + key + "]=[" + value + "]\n");
               }
               else
               {
                  sb.append("[HttpServletRequest] attribute [" + key + "]=" + (value == null ? "[null]" : "(" + value.getClass().getName()) + ")[" + value.toString() + "]\n");
               }
            }
            if (session == null)
            {
               sb.append("[HttpSession] no session\n");
            }
            else
            {
               e = session.getAttributeNames();
               while (e.hasMoreElements())
               {
                  String key = (String)e.nextElement();
                  Object value = session.getAttribute(key);
                  if ( value instanceof String )
                  {
                     sb.append("       [HttpSession] attribute [" + key + "]=[" + value + "]\n");
                  }
                  else if ( value instanceof java.util.Locale )
                  {
                     java.util.Locale l = (java.util.Locale)value;
                     sb.append("       [HttpSession] attribute [" + key + "]=[" + l.toString() + "]\n");
                  }
                  else
                  {
                     sb.append("       [HttpSession] attribute [" + key + "]=(" + value.getClass().getName() + ")[" + value.toString() + "]\n");
                  }
               }
            }
         }
         return(sb.toString());
      }
      catch (Exception ex)
      {
         logger.error("error getting sysInfo. Cause:" + ex.getMessage(), ex);
         return("error getting sysInfo. Cause:" + ex.getMessage());
      }
      
   }

   
   public static Map<String, Object> getRequestHeaders( HttpServletRequest request)
   {
	   Map<String, Object>map = new HashMap<String,Object>();
	   Enumeration<?> enumeration = request.getHeaderNames();
	   while (enumeration.hasMoreElements()) {
		   Object obj = (String)enumeration.nextElement();
		   if (obj instanceof String) {
			   Object value = request.getHeader((String)obj);
			   map.put(((String)obj).toUpperCase(), value);
		   } else {
			   map.put(("" + obj).toUpperCase(), obj);
		   }
	   }
	   return map;
   }

   
}
