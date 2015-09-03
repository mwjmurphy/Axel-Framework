package schema;
/**
 @author mike.murphy
  
 \page schema_pager_attributes_xsd AXEL Pager Attributes Schema
 
 \tableofcontents

 \section schema_pager_attributes_xsd Pager Attributes

 This schema defines the attributes used by other AXEL schemas

 Schema:<strong>pager_attributes.xsd</strong>
 
 <hr/>
 
 \htmlonly
 <style>
 .att_name {
 	width:120px;
 }
 .att_type {
 	width:120px;
 	padding-left:10px;
 	border-left:1px solid #a4bcea;
 }
 .att_desc {
 	padding-left:10px;
 	border-left:1px solid #a4bcea;
 }
 </style>
 \endhtmlonly
 
 
 <table border="0">
	<tr>
		<td class="att_name">
			<strong>Attribute Name</strong>
		</td>
		<td class="att_type">
			<strong>Type</strong>
		</td>
		<td class="att_desc">
			<strong>description</strong>  
 		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_x x
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			The presentation x position. Same usage as html in that it can represent percentage, px and any other html applicable format.  
 		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_y y
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			The presentation y position. Same usage as html in that it can represent percentage, px and any other html applicable format.  		
		</td>
 	</tr>
</table>

 \subsection schema_pager_attributes_width width
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			The display width of the form, when shown in a table. The width can also be an equation or a percentage - note the	xsd:string
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_height height
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			The presentation height. Same usage as html in that it can represent percentage, px and any other html applicable format. 
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_id id
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			Used to identify this form if more than	one are set on a page. This can then be used to show or hide this form using the
			pager.js and also makes it available in javascript using getElementById(...)
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_title title
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			Title displayed for this form. If this is empty	no title will be displayed.
		</td>
	</tr>
 </table>

 \subsection schema_pager_attributes_tooltip tooltip
 <table border="0">
 	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			Adds a tooltip to the link when displayed.
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_visible visible
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:boolean
		</td>
		<td class="att_desc">
			Set "false" to hide the form when displayed, will need to call javascript show("id") to display the form.
			<br/>Default value = "true" 
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_storage_config_ref storage_config_ref
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			This is the bean reference to the StorageConfig to use for this action.<br/>
			The StorageConfig is pre-configured and stored in the execution context.<br/>
			The StorageConfig contains the dbConnector, storageContainer and the databaseName. 
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_storage_ref storage_ref
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			This is the bean reference to the storage we want to use. A storage is a pre-loaded definition such as a database
			that includes the meta-data. i.e. db_layout.xml.<br/>
			The storage_ref is used to retrieve the data source from the execContext.<br/>
			This is an optional setting as it may retrieve the value from a parent element such as "listcp".
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_database_name database_name
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			This is the name of the database in the data storage. If no database_name is entered then the first one in the storage definition is used.<br/>
			The table_name attribute is contained in this database.<br/>
			This is an optional setting as it may retrieve the value from a parent element such as "listcp".
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_table_name table_name
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			This is the name of the table in the data source table to search.<br/>
			To limit the fields required for the search the fields may also be specified in this action.<br/> 
			This is an optional setting as it may retrieve the value from a parent element such as "listcp".
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_sql sql
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			This is an sql statement or a reference to a stored	sql statement.<br/>

			This can be used in place of a table_name where you provide the full sql.<br/>

			The sql parameter may be either an sql statement or a reference to an sql stored in the execContext.<br/>

			A replace markers is performed on the sql before it	is sent to the database.  This will replace any of
			the ${...} marker settings configured in the sql with replacement data from the execution context. Which
			enables you to enter parameters into the sql itself.<br/>

			Example: select tb_name.name from tb_name where tb_name.name='${match_this_name}'
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_data_source_ref data_source_ref
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			This is the name of data source that will provide the database connection.<br/>

			This is a reference to a spring configuered bean.<br/>

			The data source is usually configured on the application/web server, if not it can be setup from a spring configuration.<br/>

			@see myDataSource configured in the spring-pager-web-startup.xml<br/>
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_label_position label_position
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			This is the theme name, reference to a pre-loaded theme property file that has the attribute settings for drawing this theme.<br/>
			
			A default theme can be setup in the execContext by setting a property "default_theme_name=ice".  This is usually set in the config/project/web.properties.
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_theme_name theme_name
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			label_position_options
		</td>
		<td class="att_desc">
			Selects how to display the label and field.<br/>
			left = (default)label displayed to left of field.<br/>
			above = label displayed above field.
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_presentation_name presentation_name
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			This is the presentation name for the link
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_presentation_form presentation_form
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			The file name of a presentation form to draw into.<br/>

			A presentation_form will contain replacement markers that will be populated from the execContext.<br/>

			For a list each row from the query will be drawn into this form.<br/>
			Use the ${...} replacement markers to insert the fields from the query into the form.<br/>

			As an example to insert the value for field table.fieldName into the form use ${table.fieldName}.
		</td>
 	</tr>
 </table>

\subsection schema_pager_attributes_header_name header_name
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			This is the presentation name for the header if displayed in a list.
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_expression expression
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			The expression to be evaluated. If this expression evaluates to true then enclosing actions are executed.<br/>
			The expression may contain parameter references using the replacement markers. e.g. ${session:key}.<br/>
			The characters &amp;lt;, &amp;gt; and &amp;amp; must be used in place of their replacement characters &lt;, &gt; and &amp;.
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_uri uri
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			This is the uri (page) that gets called for this link.<br/>
			All input and option fields will be included in the page request.<br/>
			This attribute may be set to "" if the submit attribute	is set "true", this will cause the containing action class
			such as add to hide the add from after a successful submit.
		</td>
 	</tr>
 </table>

 \subsection schema_pager_attributes_display_as display_as
 <table border="0">
	<tr>
		<td class="att_name"/>
		<td class="att_type">
			xsd:string
		</td>
		<td class="att_desc">
			Displays a link as a 'link' or as a 'button'.<br/>
			The	selected theme for a link = INPUT_LINK and for a button = INPUT_BUTTON.
		</td>
 	</tr>
</table>



 */