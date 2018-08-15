/**
\mainpage AXEL (Active XML Extension Language)

See the Google+ site <a href="https://plus.google.com/109415438738189235346" rel="publisher">Google+</a>

\tableofcontents

\section intro_sec Introduction

The AXEL project provides a framework for building web sites using xml actions.

The framework which is Java based works naturally inside a web server such as Tomcat.  Parts
of the framework can be used in standalone code outside of a web server such as the mapping
and validation tools.

Implementing the framework on a web server requires:
<ul>
   <li>Download the framework package (.jar) from https://sourceforge.net/projects/axelframework/files/?source=navbar
   <li>Configure your web server following these \ref org_xmlactons_web_server_configuration</li>
</ul>
Once you have it running on a web server you can then start building your axel web pages.

Instructions on the core web page actions are here \ref axel_actions and a list of the
currently supported actions here \ref axel_actions_list

\note The documented list of actions is still in progress.  An alternative
way to see the actions is to look at the schemas for both the core actions and the database actions.
These can be seen at \ref schema_pager_actions_xsd and \ref schema_pager_db_actions_xsd. The full
set of schemas can be seen at \ref org_xmlactions_schemas

To work with one or more databases you should read the documentation on setting up a database at \ref schema_storage_xsd
  
\subsection configuration Configuration
How to configure AXEL to run in a Web Server such as Tomcat see \ref org_xmlactons_web_server_configuration

\subsection main_actions AXEL Actions
How to build AXEL Actions see \ref axel_actions<br/>
List of AXEL Actions see \ref axel_actions_list
 
\subsection schema AXEL Schemas
List of schemas used by AXEL see \ref org_xmlactions_schemas
  
\subsection database Describing a Database for use by AXEL
How to describe your database to AXEL see \ref org_xmlactons_db
  
\subsection language Language Locale
How language locale works see \ref language_locale
  
\subsection mapping Mapping XML and Beans
How XML and Bean mapping works see \ref axel-mapping
  
\subsection todo_list TODO List
@todo complete documentation on action maps
 
 */
