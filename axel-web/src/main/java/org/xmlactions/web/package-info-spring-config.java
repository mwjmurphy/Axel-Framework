package org.xmlactions.web;

/**
 
 \page org_xmlactons_web_server_spring_configuration Web Server Spring Configuration
 
 \tableofcontents
 
 \section axel_web_server_spring_configuration AXEL Web Server Spring Configuration
 
 See \ref org_xmlactons_web_xml_configuration to configure spring in web.xml. 

 The spring configuration is loaded from an xml file.  In this documentation the file has been set to 
 <i>classpath:/config/spring/axel-spring-pager-web-startup.xml</i> but it can be anywhere or any name that suits your project.
 The file name is set in the <b>web.xml</b> see \ref org_xmlactons_web_xml_configuration for instructions on setting the spring configuration file.
 
  The following is an example of a <b>Spring configuration used by AXEL</b>.  While the example is focused specifically on AXEL related items, anything
  that you can configure in Spring is available in AXEL. 
  
  \code{.xml}
 
	<beans xmlns="http://www.springframework.org/schema/beans"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xmlns:util="http://www.springframework.org/schema/util"
	       xmlns:context="http://www.springframework.org/schema/context"
	       xmlns:jee="http://www.springframework.org/schema/jee"
	       xmlns:aop="http://www.springframework.org/schema/aop"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans
	        http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
	        http://www.springframework.org/schema/context 
	        http://www.springframework.org/schema/context/spring-context-2.5.xsd
	        http://www.springframework.org/schema/jee
	        http://www.springframework.org/schema/jee/spring-jee-2.0.xsd
	        http://www.springframework.org/schema/util
	        http://www.springframework.org/schema/util/spring-util-2.0.xsd">
	        
	      
	   <bean id="pager.execContext" name="pager.execContext" class="org.xmlactions.pager.context.SessionExecContext" scope="session">
			
			<qualifier value="pager.execContext"/>
			
			<constructor-arg>
				<util:list>
					<util:properties location="classpath:config/pager/actions.properties" />
				</util:list>
			</constructor-arg>
	      
			<constructor-arg>
				<util:list>
					<util:properties location="classpath:config/project/web.properties" />
            		<ref bean="application.xml" />
				</util:list>
			</constructor-arg>
	
			<constructor-arg>
				<util:list>
					<ref bean="blackTheme" />
					<ref bean="iceTheme" />
				</util:list>
			</constructor-arg>
		</bean>
	   
		<bean id="application.xml" class="org.apache.commons.configuration.XMLConfiguration">
			<constructor-arg type="java.net.URL" value="classpath:/config/project/application.xml" />
		</bean>

	   
		<bean id="blackTheme" class="org.xmlactions.common.theme.Theme">
			<constructor-arg>
				<util:properties location="${themes.location}/themes/black.properties" />
			</constructor-arg>
		</bean>
	
		<bean id="iceTheme" class="org.xmlactions.common.theme.Theme">
			<constructor-arg>
				<util:properties location="${themes.location}/themes/ice.properties" />
			</constructor-arg>
		</bean>
	
		<bean id="resourceDS" class="org.xmlactions.db.DBConnector" scope="singleton">
			<property name="dataSourceReferenceName" value="jdbc/DatabaseDS" />
		</bean>
		
		<bean id="resourceLocalDS" class="org.xmlactions.db.DBConnector"  scope="singleton">
			<property name="driver" value="oracle.jdbc.driver.OracleDriver" />
			<property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
			<property name="username" value="fred" />
			<property name="password" value="password1" />
		</bean>
	
	</beans>

 \endcode



	Explanation of <b>Spring configuration used by AXEL</b>:
 	<table border="0">
 		<tr>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;"><b>bean-id</b></td>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;"><b>description</b></td>
 		</tr>
 		<tr>
 			<td colspan="3"><hr/></td>
 		</tr>
 		<tr>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">pager.execContext</td>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">This is the primary bean that contains the static data for the application.  This bean <b>MUST</b>
 				be configured for AXEL, <b>if it is not configured AXEL will not start</b>.
 				<hr/>
				<table border="0">
					<tr>
						<td>constructor-arg 1</td>
						<td style="padding-left:10px;border-left:1px solid #a4bcea;">This is a list of one or more <b>actions maps</b> that are used by the project</td>
					</tr>
					<tr>
						<td>constructor-arg 2</td>
						<td style="padding-left:10px;border-left:1px solid #a4bcea;">This is a list of one or more <b>local maps</b> that are used by the project</td>
					</tr>
					<tr>
						<td>constructor-arg 3</td>
						<td style="padding-left:10px;border-left:1px solid #a4bcea;">This is a list of one or more <b>themes</b> that are used by the project</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
 			<td colspan="3" height="20px"></td>
 		</tr>
 		<tr>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">application.xml</td>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">This is a list of properties loaded from an xml file.  They get set in the pager.execContext described above.
 				<hr/>
				<table border="0">
					<tr>
						<td>constructor-arg</td>
						<td style="padding-left:10px;border-left:1px solid #a4bcea;">The location and name of the xml configuration file</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
 			<td colspan="3" height="20px"></td>
 		</tr>
 		<tr>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">blackTheme<br/>iceTheme</td>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">These are themes setup from a properties file.  They get set in the pager.execContext described above.
 				<hr/>
				<table border="0">
					<tr>
						<td>constructor-arg</td>
						<td style="padding-left:10px;border-left:1px solid #a4bcea;">The location and name of the theme properties file</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
 			<td colspan="3" height="20px"></td>
 		</tr>
 		<tr>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">resourceDS</td>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">This is a JNDI connection to a database that has been configured on the Web Server.
 				<hr/>
				<table border="0">
					<tr>
						<td>property name="dataSourceReferenceName"</td>
						<td style="padding-left:10px;border-left:1px solid #a4bcea;">A reference to the jndi resource named jdbc/DatabaseDS</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
 			<td colspan="3" height="20px"></td>
 		</tr>
 		<tr>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">resourceLocalDS</td>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">Sets up a DataSource passing the connection parameters.
 				<hr/>
				<table border="0">
					<tr>
						<td>property name="driver"</td>
						<td style="padding-left:10px;border-left:1px solid #a4bcea;">A reference to the database driver to use for the connection <b>oracle.jdbc.driver.OracleDriver</b></td>
					</tr>
					<tr>
						<td>property name="url"</td>
						<td style="padding-left:10px;border-left:1px solid #a4bcea;">The database connection <b>jdbc:mysql://localhost:3306/mydb</b></td>
					</tr>
					<tr>
						<td>property name="username"</td>
						<td style="padding-left:10px;border-left:1px solid #a4bcea;">The connection username</td>
					</tr>
					<tr>
						<td>property name="password"</td>
						<td style="padding-left:10px;border-left:1px solid #a4bcea;">The connection password</td>
					</tr>
				</table>
			</td>
		</tr>
 	</table>


*/