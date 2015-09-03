/**
 @author mike.murphy
  
 \page schema_storage_xsd AXEL Storage Schema
 
 \tableofcontents

 Definition of the AXEL Database Schema.
 
 This schema is used to define one or more databases so that AXEL can
  - build sql to access the data
  - build on screen forms for data input
  - build presentation tables for data viewing

 AXEL provides a tool that will build the database definition directly from the database. See the following test code.
 \code
 	@Test
	public void testGetMetaData() throws SQLException {
        DataSource ds = getDriverManagerDataSource(dataSourceName);
        QueryMetaData qmd = new QueryMetaData();
        DatabaseEntry databaseEntry = qmd.getDatabase(ds, null, "FLIX");
        MetaDataToXml mdtx = new MetaDataToXml();
        XMLObject xo = mdtx.convertToXml(databaseEntry, MetaDataToXml.FORCE_LOWER_CASE);
        logger.debug(xo.mapXMLObject2XML(xo));
	}
 \endcode
  
 \section axel_schema_storage_xsd AXEL Storage Schema Described

 Schema:<strong>storage.xsd</strong>

 <hr/>
 
 \htmlonly
 <style>
 .ele_name {
 	swidth:120px;
 	padding-left:20px;
 }
 .ele_required {
 	swidth:120px;
 	padding-left:10px;
 	border-left:1px solid #a4bcea;
 }
 .ele_desc {
 	padding-left:10px;
 	border-left:1px solid #a4bcea;
 }
 .att_name {
 	swidth:120px;
 	padding-left:40px;
 }
 .att_type {
 	swidth:120px;
 	padding-left:10px;
 	border-left:1px solid #a4bcea;
 }
 .att_desc {
 	padding-left:10px;
 	border-left:1px solid #a4bcea;
 }
 </style>
 \endhtmlonly
 
 
 \subsection schema_storage_storage element:storage
 <table border="0">
	<tr>
		<td colspan="3">
			The Root element containing one or more repositories such as Database.<br/>			
			Future versions may contain xml resources, file properties and resources...
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Elements</b></td>
	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_database
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			May have one or more database definitions
 		</td>
 	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">
			name
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			The storage name
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_database element:database
 <table border="0">
	<tr>
		<td colspan="3">
			A database definition
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Elements</b></td>
	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_db_specific
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			May have one or more database specific code elements
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_pk_create
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			May have one or more primary key creation elements
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_sql
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			May have one or more custom sql statements
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_function
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			May have one or more sql insert functions
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_table
		</td>
		<td class="ele_required">
			required
		</td>
		<td class="ele_desc">
			must have one or more table definitions
 		</td>
 	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">
			name
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			The database name
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_db_specific db_specific
 <table border="0">
	<tr>
		<td colspan="3">
			Required when database specific code is needed.
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Elements</b></td>
	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_pk_create
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			May have one or more primary key creation elements
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_sql
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			May have one or more custom sql statements
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_function
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			May have one or more sql insert functions
 		</td>
 	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">
			name
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			identifier for this db_specific element.<br/>
			
			This identifier is set in the configuration and is used to determine which db_specific elements should be used,.  
 		</td>
 	</tr>
 </table>


 \subsection schema_storage_pk_create pk_create
 <table border="0">
	<tr>
		<td colspan="3">
			How to create primary keys
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">
			name
		</td>
		<td class="att_type">
			xsd:string<br/>required
		</td>
		<td class="att_desc">
			identifier for this primary key creation
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			sql
		</td>
		<td class="att_type">
			xsd:string<br/>required
		</td>
		<td class="att_desc">
			sql used to create primary key
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_sql sql
 <table border="0">
	<tr>
		<td colspan="3">
			Used when hand made sql is required
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">
			name
		</td>
		<td class="att_type">
			xsd:string<br/>required
		</td>
		<td class="att_desc">
			identifier for this sql
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			sql
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			The hand made sql. Note that str replacements using the ${...} may be included in the sql.<br/>

			This attribute is only optional if the sql is set in the content of the sql element instead.<br/>
			
			If no sql is set an exception will be thrown when you try to access it.
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_function function
 <table border="0">
	<tr>
		<td colspan="3">
			Used when hand made sql is required
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">
			name
		</td>
		<td class="att_type">
			xsd:string<br/>required
		</td>
		<td class="att_desc">
			identifier for this function
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			sql
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			The function sql. Note that str replacements using the ${...} may be included in the sql<br/>
			and<br/>
			${p1} is used to insert the field name.
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_table table
 <table border="0">
	<tr>
		<td colspan="3">
			The definition of a database table.
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Elements</b></td>
	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_table_fields
		</td>
		<td class="ele_required">
			required
		</td>
		<td class="ele_desc">
			A reference to a group of elements that can be children of this element
 		</td>
 	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">
			name
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			The table name - must match the database table name
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			alias
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			This is used instead of the table name when building the query field outputs.<br/>

			When the system is building an SQL query the table name is placed before the field name to create a unique identifier. eg. "table.fieldname".<br/>

			This is also used when building the identifier for the "as". eg."select table.fieldname <b>as</b> table_fieldname from"...<br/>

			If this is set then the alias value will replace the table name for the output. eg. "select table.fieldname <b>as</b> alias_fieldname from"...<br/>

			If this is set to an empty string than the table name will not be used for the output. eg. "select table.fieldname <b>as</b> fieldname from"...
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_table_fields table_fields
 <table border="0">
	<tr>
		<td colspan="3">
			Implies an inclusion of a list of field elements for a table.
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Elements</b></td>
	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_pk
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a primary key field
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_fk
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a foreign key field
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_text
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a text field
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_textarea
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a textarea field
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_link
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a link field
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_password
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a password field
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_image
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a image field
 		</td>
 	</tr>
 	<tr>
		<td class="ele_name">
			\ref schema_storage_int
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a int'eger field
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_timestamp
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a timestamp field
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_datetime
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a datetime field
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_date
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a date field
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_timeofday
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a timeofday field
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_binary
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a binary field
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_select
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a select field
 		</td>
 	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_table_path
		</td>
		<td class="ele_required">
			optional
		</td>
		<td class="ele_desc">
			A reference to a table_path
 		</td>
 	</tr>
</table>

 \subsection schema_storage_pk pk
 <table border="0">
	<tr>
		<td colspan="3">
			A primary key definition.
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">
			name
		</td>
		<td class="att_type">
			xsd:string<br/>required
		</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			alias
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
            to simplify the reference to the name. As an example if you have a primary key
            field that has a name of "table_name_id" you can replace it with a simple "id"
            then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			presentation_name
		</td>
		<td class="att_type">
			xsd:string<br/>required
		</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			function_ref
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			If we want to call a function in the sql.<br/>
			
			The function StrSubstution to replace ${p1} with the table.fieldname.<br/>
			
			Example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			pk_ref
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			Reference to a primary key creator.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			regex
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			A Java Regular Expression used to validate data before storing into a database.
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_fk fk
 <table border="0">
	<tr>
		<td colspan="3">
            A foreign key definition. Which is a reference to another table in the database.
            A foreign key may contain referencing fields from the referenced table that can
            be displayed with the parent table. 
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Elements</b></td>
	</tr>
	<tr>
		<td class="ele_name">
			\ref schema_storage_table_fields
		</td>
		<td class="ele_required">
			required
		</td>
		<td class="ele_desc">
			List of fields, one or more which may be included within the foreign key. These fields 
			must match those in the referencing table, the presentation_name and length may differ.
 		</td>
 	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">
			name
		</td>
		<td class="att_type">
			xsd:string<br/>required
		</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			alias
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
			to simplify the reference to the name. As an example if you have a primary key
			field that has a name of "table_name_id" you can replace it with a simple "id"
			then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			presentation_name
		</td>
		<td class="att_type">
			xsd:string<br/>required
		</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			tooltip
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			A tooltip. Usually shown on mouse hover.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			foreign_table
		</td>
		<td class="att_type">
			xsd:string<br/>required
		</td>
		<td class="att_desc">
			The name of the table that this key references
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			foreign_table_alias
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			The foreign_table_alias is used TO set the alias table name in the "from" list.<br/>
			
			Example "select t.id from table as t"<br/>

			In the above example the table is renamed to t and its fields may be referenced using t.fieldname.<br/>

			The foreign_table_alias is <b>required</b> if you need to retrieve multiple sets of data from a table using different where clauses.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			foreign_key
		</td>
		<td class="att_type">
			xsd:string<br/>required
		</td>
		<td class="att_desc">
			The field in the referenced table that this key references.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			editable
		</td>
		<td class="att_type">
			xsd:boolean<br/>optional
		</td>
		<td class="att_desc">
			Set true to allow editing of this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			mandatory
		</td>
		<td class="att_type">
			xsd:boolean<br/>optional
		</td>
		<td class="att_desc">
			Set true to mark this field as mandatory when editing.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			where
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			A where is used to select the data returned from a select.<br/>
			
			In this location the where can fine tune the result of the query, applying it specifically to the foreign table.<br/>

			Do not provide the 'where' syntax for the where clause. Instead only provide the conditions of the where clause.<br/>

			Example:<br />
			&nbsp;&nbsp;&nbsp; tb1.id=tb2.id<br />
			&nbsp;&nbsp;&nbsp; or<br />
			&nbsp;&nbsp;&nbsp; tb1.name like 'fred'<br />
			&nbsp;&nbsp;&nbsp; or<br />
			&nbsp;&nbsp;&nbsp; tb1.id=tb2.id and tb1.name like 'fred'<br />
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			function_ref
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			If we want to call a function in the sql.<br/>
			
			The function StrSubstution to replace ${p1} with the table.fieldname.<br/>
			
			Example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			regex
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			A Java Regular Expression used to validate data before storing into a database.
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_text text
 <table border="0">
	<tr>
		<td colspan="3">
			A definition for a text field.  This is displayed as a single line. see \ref schema_storage_textarea for multiple line display.
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">
			name
		</td>
		<td class="att_type">
			xsd:string<br/>required
		</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			alias
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
			to simplify the reference to the name. As an example if you have a primary key
			field that has a name of "table_name_id" you can replace it with a simple "id"
			then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			presentation_name
		</td>
		<td class="att_type">
			xsd:string<br/>required
		</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			presentation_width
		</td>
		<td class="att_type">
			xsd:string<br/>required
		</td>
		<td class="att_desc">
			The display width of the field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			length
		</td>
		<td class="att_type">
			xsd:integer<br/>required
		</td>
		<td class="att_desc">
			The storage size of the field in the database
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			tooltip
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			A tooltip. Usually shown on mouse hover.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			editable
		</td>
		<td class="att_type">
			xsd:boolean<br/>optional
		</td>
		<td class="att_desc">
			Set true to allow editing of this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			mandatory
		</td>
		<td class="att_type">
			xsd:boolean<br/>optional
		</td>
		<td class="att_desc">
			Set true to mark this field as mandatory when editing.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			unique
		</td>
		<td class="att_type">
			xsd:boolean<br/>optional
		</td>
		<td class="att_desc">
			Set true to mark this field as unique.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			function_ref
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			If we want to call a function in the sql.<br/>
			
			The function StrSubstution to replace ${p1} with the table.fieldname.<br/>
			
			Example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
 		</td>
 	</tr>
	<tr>
		<td class="att_name">
			regex
		</td>
		<td class="att_type">
			xsd:string<br/>optional
		</td>
		<td class="att_desc">
			A Java Regular Expression used to validate data before storing into a database.
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_textarea textarea
 <table border="0">
	<tr>
		<td colspan="3">
			 A definition for a text area field.  This is displayed on multiple lines. see \ref schema_storage_text for a single line display.
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">alias</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
			to simplify the reference to the name. As an example if you have a primary key
			field that has a name of "table_name_id" you can replace it with a simple "id"
			then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_width</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The display width of the field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_height</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The display height of the field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">length</td>
		<td class="att_type">xsd:integer<br/>required</td>
		<td class="att_desc">
			The storage size of the field in the database
 		</td>
 	</tr>
	<tr>
		<td class="att_name">want_html_edit</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			If this is set true then a html edit page is displayed when editing this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">tooltip</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A tooltip. Usually shown on mouse hover.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">editable</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to allow editing of this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">mandatory</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to mark this field as mandatory when editing.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">unique</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to mark this field as unique.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">function_ref</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			If we want to call a function in the sql.<br/>
			
			The function StrSubstution to replace ${p1} with the table.fieldname.<br/>
			
			Example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
 		</td>
 	</tr>
	<tr>
		<td class="att_name">regex</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A Java Regular Expression used to validate data before storing into a database.
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_password password
 <table border="0">
	<tr>
		<td colspan="3">
			 A definition for a password field.
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">alias</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
			to simplify the reference to the name. As an example if you have a primary key
			field that has a name of "table_name_id" you can replace it with a simple "id"
			then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_width</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The display width of the field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">length</td>
		<td class="att_type">xsd:integer<br/>required</td>
		<td class="att_desc">
			The storage size of the field in the database
 		</td>
 	</tr>
	<tr>
		<td class="att_name">tooltip</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A tooltip. Usually shown on mouse hover.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">editable</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to allow editing of this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">mandatory</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to mark this field as mandatory when editing.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">function_ref</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			If we want to call a function in the sql.<br/>
			
			The function StrSubstution to replace ${p1} with the table.fieldname.<br/>
			
			Example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
 		</td>
 	</tr>
	<tr>
		<td class="att_name">regex</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A Java Regular Expression used to validate data before storing into a database.
 		</td>
 	</tr>
 </table>


 \subsection schema_storage_link link
 <table border="0">
	<tr>
		<td colspan="3">
			 A definition for a link (href).
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">alias</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
			to simplify the reference to the name. As an example if you have a primary key
			field that has a name of "table_name_id" you can replace it with a simple "id"
			then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_width</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The display width of the field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">length</td>
		<td class="att_type">xsd:integer<br/>required</td>
		<td class="att_desc">
			The storage size of the field in the database
 		</td>
 	</tr>
	<tr>
		<td class="att_name">tooltip</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A tooltip. Usually shown on mouse hover.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">editable</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to allow editing of this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">mandatory</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to mark this field as mandatory when editing.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">function_ref</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			If we want to call a function in the sql.<br/>
			
			The function StrSubstution to replace ${p1} with the table.fieldname.<br/>
			
			Example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
 		</td>
 	</tr>
	<tr>
		<td class="att_name">regex</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A Java Regular Expression used to validate data before storing into a database.
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_image image
 <table border="0">
	<tr>
		<td colspan="3">
			 A definition for a image (img).
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">alias</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
			to simplify the reference to the name. As an example if you have a primary key
			field that has a name of "table_name_id" you can replace it with a simple "id"
			then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_width</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The display width of the field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">length</td>
		<td class="att_type">xsd:integer<br/>required</td>
		<td class="att_desc">
			The storage size of the field in the database
 		</td>
 	</tr>
	<tr>
		<td class="att_name">max_width</td>
		<td class="att_type">xsd:integer<br/>optional</td>
		<td class="att_desc">
			The max allowed width of the image
 		</td>
 	</tr>
	<tr>
		<td class="att_name">max_height</td>
		<td class="att_type">xsd:integer<br/>optional</td>
		<td class="att_desc">
			The max allowed height of the image.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">tooltip</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A tooltip. Usually shown on mouse hover.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">editable</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to allow editing of this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">mandatory</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to mark this field as mandatory when editing.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">function_ref</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			If we want to call a function in the sql.<br/>
			
			The function StrSubstution to replace ${p1} with the table.fieldname.<br/>
			
			Example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
 		</td>
 	</tr>
	<tr>
		<td class="att_name">regex</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A Java Regular Expression used to validate data before storing into a database.
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_int int
 <table border="0">
	<tr>
		<td colspan="3">
			 A definition for an integer field.
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">alias</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
			to simplify the reference to the name. As an example if you have a primary key
			field that has a name of "table_name_id" you can replace it with a simple "id"
			then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_width</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The display width of the field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">tooltip</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A tooltip. Usually shown on mouse hover.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">editable</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to allow editing of this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">mandatory</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to mark this field as mandatory when editing.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">function_ref</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			If we want to call a function in the sql.<br/>
			
			The function StrSubstution to replace ${p1} with the table.fieldname.<br/>
			
			Example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
 		</td>
 	</tr>
	<tr>
		<td class="att_name">regex</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A Java Regular Expression used to validate data before storing into a database.
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_binary binary
 <table border="0">
	<tr>
		<td colspan="3">
			A definition for a checkbox field. This works with a database binary field type. And can display as a checkbox.
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">alias</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
			to simplify the reference to the name. As an example if you have a primary key
			field that has a name of "table_name_id" you can replace it with a simple "id"
			then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_width</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The display width of the field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">tooltip</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A tooltip. Usually shown on mouse hover.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">editable</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to allow editing of this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">mandatory</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to mark this field as mandatory when editing.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">function_ref</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			If we want to call a function in the sql.<br/>
			
			The function StrSubstution to replace ${p1} with the table.fieldname.<br/>
			
			Example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
 		</td>
 	</tr>
	<tr>
		<td class="att_name">pattern</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			Sets the acceptable binary pattern for false/true, no/yes, N/Y, 0/1.<br/>

			Note the false value must be first followed by the seperator / followed by the true value.<br/>

			The first entry in the pattern is always considered the false value and the second entry in
			the pattern is always considered the true value.<br/>

			The seperator between the false and true value is the / character.<br/>
			
			Any leading or trailing whitespace is removed.<br/>
			
			Example:<br/>
				<ul>
					<li>Y/N</li>
                 	<li>true/false</li>
					<li>1/0</li>
					<li>X/Y</li>
				</ul>
				<br/>
				This is an optional attribute and if not used the default pattern is true/false
		</td>
 	</tr>
 </table>

 \subsection schema_storage_timestamp timestamp
 <table border="0">
	<tr>
		<td colspan="3">
			A definition for a timestamp field.  This is displayed as a single line. The value of timestamp differs between databases.
            <br/>mysql format: YYYY-MM-DD HH:MM:SS
            <br/>mssql format: unique number (possibly system time)
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">alias</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
			to simplify the reference to the name. As an example if you have a primary key
			field that has a name of "table_name_id" you can replace it with a simple "id"
			then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_width</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The display width of the field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">tooltip</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A tooltip. Usually shown on mouse hover.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">editable</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to allow editing of this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">mandatory</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to mark this field as mandatory when editing.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">function_ref</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			If we want to call a function in the sql.<br/>
			
			The function StrSubstution to replace ${p1} with the table.fieldname.<br/>
			
			Example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
 		</td>
 	</tr>
	<tr>
		<td class="att_name">regex</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A Java Regular Expression used to validate data before storing into a database.
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_datetime datetime
 <table border="0">
	<tr>
		<td colspan="3">
			A definition for a datetime field.  This is displayed as a single line. The value of datetime is YYYY-MM-DD HH:MM:SS
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">alias</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
			to simplify the reference to the name. As an example if you have a primary key
			field that has a name of "table_name_id" you can replace it with a simple "id"
			then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_width</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The display width of the field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">tooltip</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A tooltip. Usually shown on mouse hover.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">editable</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to allow editing of this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">mandatory</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to mark this field as mandatory when editing.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">function_ref</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			If we want to call a function in the sql.<br/>
			
			The function StrSubstution to replace ${p1} with the table.fieldname.<br/>
			
			Example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
 		</td>
 	</tr>
	<tr>
		<td class="att_name">regex</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A Java Regular Expression used to validate data before storing into a database.
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_date date
 <table border="0">
	<tr>
		<td colspan="3">
			A definition for a date field.  This is displayed as a single line. The value of datetime is YYYY-MM-DD
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">alias</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
			to simplify the reference to the name. As an example if you have a primary key
			field that has a name of "table_name_id" you can replace it with a simple "id"
			then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_width</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The display width of the field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">tooltip</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A tooltip. Usually shown on mouse hover.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">editable</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to allow editing of this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">mandatory</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to mark this field as mandatory when editing.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">function_ref</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			If we want to call a function in the sql.<br/>
			
			The function StrSubstution to replace ${p1} with the table.fieldname.<br/>
			
			Example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
 		</td>
 	</tr>
	<tr>
		<td class="att_name">regex</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A Java Regular Expression used to validate data before storing into a database.
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_timeofday timeofday
 <table border="0">
	<tr>
		<td colspan="3">
			A definition for a timeofday field.  This is displayed as a single line. The value of datetime is HH:MM:SS or HH:MM
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">alias</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
			to simplify the reference to the name. As an example if you have a primary key
			field that has a name of "table_name_id" you can replace it with a simple "id"
			then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_width</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The display width of the field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">tooltip</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A tooltip. Usually shown on mouse hover.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">editable</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to allow editing of this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">mandatory</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to mark this field as mandatory when editing.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">function_ref</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			If we want to call a function in the sql.<br/>
			
			The function StrSubstution to replace ${p1} with the table.fieldname.<br/>
			
			Example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
 		</td>
 	</tr>
	<tr>
		<td class="att_name">regex</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A Java Regular Expression used to validate data before storing into a database.
 		</td>
 	</tr>
 </table>


 \subsection schema_storage_select select
 <table border="0">
	<tr>
		<td colspan="3">
			Multi Option Selection used by a radio button display with single or multi select option.
            <br/>            
            Use for entries such as "Y,N" or "Y,N,U" or "Yes,No,Maybe,Perhaps" or "true,false" or "Fast Car;Slow Car;"
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The name of the field, must match the table field name.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">alias</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			An alias is used in place of the name when you need to shorten the name and / or
			to simplify the reference to the name. As an example if you have a primary key
			field that has a name of "table_name_id" you can replace it with a simple "id"
			then when you want to reference the field just use the "id".
 		</td>
 	</tr>
	<tr>
		<td class="att_name">presentation_name</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			Name used when displaying this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">tooltip</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A tooltip. Usually shown on mouse hover.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">editable</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to allow editing of this field.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">mandatory</td>
		<td class="att_type">xsd:boolean<br/>optional</td>
		<td class="att_desc">
			Set true to mark this field as mandatory when editing.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">regex</td>
		<td class="att_type">xsd:string<br/>optional</td>
		<td class="att_desc">
			A Java Regular Expression used to validate data before storing into a database.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">select</td>
		<td class="att_type">\ref schema_storage_select_options<br/>optional<br/>default:single</td>
		<td class="att_desc">
			Can select a single or multiple select set of radio buttons or check boxes.
 		</td>
 	</tr>
 </table>


 \subsection schema_storage_table_path table_path
 <table border="0">
	<tr>
		<td colspan="3">
			A table path is used to identify relationship tables.  It identifies which tables are
            linked in the relationship.  The relationship can be a 'OneToOne', 'OneToMany', 'ManyToOne'
            and 'ManyToMany'.
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Attributes</b></td>
	</tr>
	<tr>
		<td class="att_name">table_a</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The name of one of the foreign tables that are referenced from this table.
 		</td>
 	</tr>
	<tr>
		<td class="att_name">table_b</td>
		<td class="att_type">xsd:string<br/>required</td>
		<td class="att_desc">
			The name of one of the foreign tables that are referenced from this table
 		</td>
 	</tr>
 </table>

 \subsection schema_storage_select_options select_options
 <table border="0">
	<tr>
		<td colspan="3">
			Can select a single or multiple select set of radio buttons or check boxes.
		<td>
	</tr>
	<tr>
		<td colspan="3"><b>Restriction</b></td>
	</tr>
	<tr>
		<td class="att_name">enumeration</td>
		<td class="att_type">xsd:string<</td>
		<td class="att_desc">
			single
 		</td>
 	</tr>
	<tr>
		<td class="att_name">enumeration</td>
		<td class="att_type">xsd:string<</td>
		<td class="att_desc">
			multiple
 		</td>
 	</tr>
 </table>


 = = = = = = = = = = = =
 
  \code{.xml}
<?xml version="1.0" encoding="UTF-8"?>
<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema"
   xmlns="http://www.xmlactions.org/storage"
   targetNamespace="http://www.xmlactions.org/storage"
   attributeFormDefault="unqualified"
   elementFormDefault="qualified">

	<xsd:element name="storage">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
                  The Root element containing one or more repositories such as Database.
                  ]]></xsd:documentation>
			<xsd:documentation><![CDATA[
                  Future versions may contain xml resources, file properties and resources...
                  ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:sequence>
				<xsd:element ref="database" minOccurs="0" maxOccurs="unbounded">
					<xsd:annotation>
						<xsd:documentation>
							A Database definition.
                        </xsd:documentation>
					</xsd:annotation>
				</xsd:element>
			</xsd:sequence>
			<xsd:attribute name="name" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						The storage name
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="database">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
                  A Database definition. 
               ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:sequence>
				<xsd:element ref="db_specific" minOccurs="0" maxOccurs="unbounded">
					<xsd:annotation>
						<xsd:documentation>
							When database specific code is required.
                         </xsd:documentation>
					</xsd:annotation>
				</xsd:element>
				<xsd:element ref="pk_create" minOccurs="0" maxOccurs="unbounded">
					<xsd:annotation>
						<xsd:documentation>
							How to create primary keys.
                         </xsd:documentation>
					</xsd:annotation>
				</xsd:element>
				<xsd:element ref="sql" minOccurs="0" maxOccurs="unbounded">
					<xsd:annotation>
						<xsd:documentation>
							May be used when hand made sql is required.
                         </xsd:documentation>
					</xsd:annotation>
				</xsd:element>
				<xsd:element ref="function" minOccurs="0" maxOccurs="unbounded">
					<xsd:annotation>
						<xsd:documentation>
							Used to insert functions into the sql
                         </xsd:documentation>
					</xsd:annotation>
				</xsd:element>
				<xsd:element ref="table" minOccurs="1" maxOccurs="unbounded">
					<xsd:annotation>
						<xsd:documentation>
							The definition of a database table.
                        </xsd:documentation>
					</xsd:annotation>
				</xsd:element>
			</xsd:sequence>
			<xsd:attribute name="name" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						The database name
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="db_specific">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
                  Required when database specific code is required.
               ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:sequence>
				<xsd:element ref="pk_create" minOccurs="0" maxOccurs="unbounded">
					<xsd:annotation>
						<xsd:documentation>
							How to create primary keys.
                              </xsd:documentation>
					</xsd:annotation>
				</xsd:element>
				<xsd:element ref="sql" minOccurs="0" maxOccurs="unbounded">
					<xsd:annotation>
						<xsd:documentation>
							May be used when hand made sql is required.
                         </xsd:documentation>
					</xsd:annotation>
				</xsd:element>
				<xsd:element ref="function" minOccurs="0" maxOccurs="unbounded">
					<xsd:annotation>
						<xsd:documentation>
							Used to insert functions into the sql
                         </xsd:documentation>
					</xsd:annotation>
				</xsd:element>
			</xsd:sequence>
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						identifier for this db_specific element. 
                    </xsd:documentation>
					<xsd:documentation>
						This identifier is set in the configuration and is used
						to determine which db_specific elements
						should be used,.  
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="pk_create">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
                  How to create primary keys
                  ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						identifier for this primary key creation
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="sql" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						sql used to create primary key
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="sql">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
                  Used when hand made sql is required
                  ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType mixed="true">
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						identifier for this sql
                     </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="sql" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						The hand made sql. Note that str replacements using the ${...} may be included in the sql.
					</xsd:documentation>
					<xsd:documentation>
						This attribute is only optional if the sql is set in the content of the
						sql element instead.
					</xsd:documentation>
					<xsd:documentation>
						If no sql is set an exception will be thrown when you try to access it.
                     </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="function">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
                  Used when hand made sql is required
                  ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						identifier for this function
                     </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="sql" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The function sql. Note that str replacements using the ${...} may be included in the sql
						and
						${p1} is used to insert the field name.
                     </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="table">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
                  The definition of a database table.
                  ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:group ref="table_fields" />
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The table name - must match the database table name
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="alias" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						This is used instead of the table name when building the query
						field
						outputs.
						<p>
							When the system is building an SQL query the table name is placed
							before the field name to create a unique
							identifier. eg.
							"table.fieldname".
                            </p>
						<p>
							This is also used when building the identifier for the "as". eg.
							"select table.fieldname
							<b>as</b>
							table_fieldname from"...
						</p>
						<p>
							If this is set then the alias value will replace the table name
							for
							the output. eg. "select table.fieldname
							<b>as</b>
							alias_fieldname from"...
						</p>
						<p>
							If this is set to an empty string than the table name will not be
							used for
							the output. eg. "select table.fieldname
							<b>as</b>
							fieldname from"...
						</p>
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>


	<xsd:element name="pk">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
            A primary key definition. 
            ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The name of the field, must match the table field name.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
			<xsd:attribute name="presentation_name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						Name used when displaying the primary key.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="function_ref" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						If we want to call a function in the sql.
                    </xsd:documentation>
					<xsd:documentation>
						The function StrSubstution to replace ${p1} with the table.fieldname.
                    </xsd:documentation>
					<xsd:documentation>
						example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="pk_ref" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Regerence to a primary key creator.
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="regex" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A Java Regular Expression used to validate data
						beforing storing into a database.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="fk">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
            A foreign key definition. Which is a references to another table in the database.
            A foreign key may contain referencing fields from the referenced table that can
            be displayed with the parent table. 
            ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:sequence>
				<xsd:annotation>
					<xsd:documentation>
						List of fields, one or more which may be included within the foreign
						key. These
						fields must match
						those in the referencing table, the presentation_name
						and length
						may differ.
                    </xsd:documentation>
				</xsd:annotation>
				<xsd:group ref="table_fields" />
			</xsd:sequence>
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The name of the field, must match the table field name.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
			<xsd:attribute name="presentation_name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						Name used when displaying the primary key.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="tooltip" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A tooltip. Usually shown on mouse hover.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="foreign_table" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The name of the table that this key references
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="foreign_table_alias" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						The foreign_table_alias is used TO set the alias table name in the
						"from" list.
					</xsd:documentation>
					<xsd:documentation>
						Example "select t.id from table as t"
                            </xsd:documentation>
					<xsd:documentation>
						In the above example the table is renamed to t and its fields may be
						referenced
						using t.fieldname.
					</xsd:documentation>
					<xsd:documentation>
						The foreign_table_alias is
						<b>required</b>
						if you need to retrieve multiple sets of data from a table using
						different where clauses.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="foreign_key" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The field in the reference table that this key references.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="editable" type="xsd:boolean" default="true" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to allow editing of this field.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="mandatory" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to mark this field as mandatory when editing.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="where" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A where is used to select the data returned from a select.
						In this location the where can fine
						tune the result of the
						query, applying it specifically to the foreign table.
					</xsd:documentation>
					<xsd:documentation>
						Do not provide the 'where' syntax for the where clause.
						Instead only provide the conditions of
						the where clause.
					</xsd:documentation>
					<xsd:documentation>
						example:
						<br />
						tb1.id=tb2.id
						<br />
						or
						<br />
						tb1.name like 'fred'
						<br />
						or
						<br />
						tb1.id=tb2.id and tb1.name like 'fred'
						<br />
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="function_ref" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						If we want to call a function in the sql.
                    </xsd:documentation>
					<xsd:documentation>
						The function StrSubstution to replace ${p1} with the table.fieldname.
                    </xsd:documentation>
					<xsd:documentation>
						example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="regex" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A Java Regular Expression used to validate data
						beforing storing into a database.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="text">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
            A definition for a text field.  This is displayed as a single line. @See textfield for
            multiple line display. 
            ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The name of the field, must match the table
						field name.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
			<xsd:attribute name="presentation_name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						Name used when displaying the primary key.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="presentation_width" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The display width of the field.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="length" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The storage size of the field in the database
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="editable" type="xsd:boolean" default="true" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to allow editing of this field.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="mandatory" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to mark this field as mandatory when
						editing.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="unique" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to mark this field as unique.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="tooltip" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A tooltip. Usually shown on mouse hover.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="function_ref" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						If we want to call a function in the sql.
                    </xsd:documentation>
					<xsd:documentation>
						The function StrSubstution to replace ${p1} with the table.fieldname.
                    </xsd:documentation>
					<xsd:documentation>
						example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="regex" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A Java Regular Expression used to validate data
						beforing storing into a database.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="textarea">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
            A definition for a text area field.  This is displayed on multiple lines. @See text for
            a single line display. 
            ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The name of the field, must match the table field name.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
			<xsd:attribute name="presentation_name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						Name used when displaying the primary key.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="presentation_width" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The display width of the field.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="length" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The storage size of the field in the database
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="presentation_height" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The display height of the field.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="want_html_edit" type="xsd:boolean" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						If this is set true then a html edit page is displayed when editing
						this field.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="editable" type="xsd:boolean" default="true" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to allow editing of this field.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="mandatory" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to mark this field as mandatory when editing.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="unique" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to mark this field as unique.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="tooltip" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A tooltip. Usually shown on mouse hover.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="function_ref" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						If we want to call a function in the sql.
                    </xsd:documentation>
					<xsd:documentation>
						The function StrSubstution to replace ${p1} with the table.fieldname.
                    </xsd:documentation>
					<xsd:documentation>
						example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="regex" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A Java Regular Expression used to validate data
						beforing storing into a database.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="password">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
            A definition for a password field.
            ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The name of the field, must match the table field name.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
			<xsd:attribute name="presentation_name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						Name used when displaying the primary key.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="presentation_width" type="xsd:integer">
				<xsd:annotation>
					<xsd:documentation>
						The display width of the field.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="length" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The storage size of the field in the database
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="editable" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to allow editing of this field.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="mandatory" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to mark this field as mandatory when editing.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="tooltip" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A tooltip. Usually shown on mouse hover.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="function_ref" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						If we want to call a function in the sql.
                    </xsd:documentation>
					<xsd:documentation>
						The function StrSubstution to replace ${p1} with the table.fieldname.
                    </xsd:documentation>
					<xsd:documentation>
						example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="regex" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A Java Regular Expression used to validate data
						beforing storing into a database.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="link">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
            A definition for a link (href).
            ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The name of the field, must match the table field name.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
			<xsd:attribute name="presentation_name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						Name used when displaying the primary key.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="presentation_width" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The display width of the field.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="length" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The storage size of the field in the database
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="editable" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to allow editing of this field.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="mandatory" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to mark this field as mandatory when editing.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="tooltip" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A tooltip. Usually shown on mouse hover.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="function_ref" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						If we want to call a function in the sql.
                    </xsd:documentation>
					<xsd:documentation>
						The function StrSubstution to replace ${p1} with the table.fieldname.
                    </xsd:documentation>
					<xsd:documentation>
						example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="regex" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A Java Regular Expression used to validate data
						beforing storing into a database.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="image">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
            A definition for a image (img).
            ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The name of the field, must match the table field name.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
			<xsd:attribute name="presentation_name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						Name used when displaying the primary key.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="presentation_width" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The display width of the field.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="length" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The storage size of the field in the database
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="max_width" type="xsd:integer" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						The max allowed width of the image.
                                </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="max_height" type="xsd:integer" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						The max allowed height of the image.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="editable" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to allow editing of this field.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="mandatory" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to mark this field as mandatory when editing.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="tooltip" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A tooltip. Usually shown on mouse hover.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="function_ref" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						If we want to call a function in the sql.
                    </xsd:documentation>
					<xsd:documentation>
						The function StrSubstution to replace ${p1} with the table.fieldname.
                    </xsd:documentation>
					<xsd:documentation>
						example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="regex" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A Java Regular Expression used to validate data
						beforing storing into a database.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="int">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
            A definition for an integer field.
            ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
            <xsd:attribute name="name" type="xsd:string" use="required">
                <xsd:annotation>
                    <xsd:documentation>
                        The name of the field, must match the table field name.
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
			<xsd:attribute name="presentation_name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						Name used when displaying the primary key.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="presentation_width" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The display width of the field.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="editable" type="xsd:boolean" default="true" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to allow editing of this field.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="mandatory" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to mark this field as mandatory when editing.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="tooltip" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A tooltip. Usually shown on mouse hover.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="function_ref" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						If we want to call a function in the sql.
                    </xsd:documentation>
					<xsd:documentation>
						The function StrSubstution to replace ${p1} with the table.fieldname.
                    </xsd:documentation>
					<xsd:documentation>
						example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="regex" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A Java Regular Expression used to validate data
						beforing storing into a database.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

   <xsd:element name="binary">
      <xsd:annotation>
         <xsd:documentation><![CDATA[
            A definition for a checkbox field. This works with a database
            binary field type. And can be display as a checkbox.
            ]]></xsd:documentation>
      </xsd:annotation>
      <xsd:complexType>
         <xsd:attribute name="name" type="xsd:string" use="required">
            <xsd:annotation>
               <xsd:documentation>
                  The name of the field, must match the table field name.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
         <xsd:attribute name="presentation_name" type="xsd:string" use="required">
            <xsd:annotation>
               <xsd:documentation>
                  Name used when displaying the checkbox key.
                        </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="presentation_width" type="xsd:integer" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  The display width of the field.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="editable" type="xsd:boolean" default="true" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  Set true to allow editing of this field.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="mandatory" type="xsd:boolean" default="false" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  Set true to mark this field as mandatory when editing.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="tooltip" type="xsd:string" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  A tooltip. Usually shown on mouse hover.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="function_ref" type="xsd:string" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  If we want to call a function in the sql.
               </xsd:documentation>
               <xsd:documentation>
                  The function StrSubstution to replace ${p1} with the table.fieldname.
               </xsd:documentation>
               <xsd:documentation>
                  example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="pattern" type="xsd:string" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  <p>
                     Sets the acceptable binary pattern for false/true, no/yes, N/Y, 0/1.
                  </p>
                  <p>
                     Note the false value must be first followed by the seperator / followed
                     by the true value.
                  </p>
                  <p>
                     The first entry in the pattern is always considered the false
                     value and the second entry in the pattern is always considered
                     the true value.
                  </p>
                  <p>
                     The seperator between the false and true value is the / character.
                  </p>
                  <p>
                     Any leading or trailing whitespace is removed.
                  </p> 
                  <p>
                     Example:<br/>
                     <ul>
                        <li>Y/N</li>
                        <li>true/false</li>
                        <li>1/0</li>
                        <li>X/Y</li>
                     </ul>
                  </p>
                  <p>
                     This is an optional attribute and if not used the default pattern
                     is true/false
                  </p>
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
      </xsd:complexType>
   </xsd:element>

	<xsd:element name="timestamp">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
            A definition for a timestamp field.  This is displayed as a single line. The
            value of timestamp differs between databases. 
            mysql format: YYYY-MM-DD HH:MM:SS
            mssql format: unique number (possibly system time)
            ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The name of the field, must match the table field name.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
			<xsd:attribute name="presentation_name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						Name used when displaying the primary key.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="presentation_width" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The display width of the field.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="editable" type="xsd:boolean" default="true" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to allow editing of this field.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="mandatory" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to mark this field as mandatory when editing.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="tooltip" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A tooltip. Usually shown on mouse hover.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="function_ref" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						If we want to call a function in the sql.
                    </xsd:documentation>
					<xsd:documentation>
						The function StrSubstution to replace ${p1} with the table.fieldname.
                    </xsd:documentation>
					<xsd:documentation>
						example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="regex" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A Java Regular Expression used to validate data
						beforing storing into a database.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="datetime">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
            A definition for a datetime field.  This is displayed as a single line. The
            value of datetime is YYYY-MM-DD HH:MM:SS
            ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The name of the field, must match the table field name.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
			<xsd:attribute name="presentation_name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						Name used when displaying the primary key.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="presentation_width" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The display width of the field.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="editable" type="xsd:boolean" default="true" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to allow editing of this field.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="mandatory" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to mark this field as mandatory when editing.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="tooltip" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A tooltip. Usually shown on mouse hover.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="function_ref" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						If we want to call a function in the sql.
                    </xsd:documentation>
					<xsd:documentation>
						The function StrSubstution to replace ${p1} with the table.fieldname.
                    </xsd:documentation>
					<xsd:documentation>
						example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="regex" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A Java Regular Expression used to validate data
						beforing storing into a database.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

	<xsd:element name="date">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
            A definition for a date field.  This is displayed as a single line. The
            value of datetime is YYYY-MM-DD
            ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:attribute name="name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The name of the field, must match the table field name.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
			<xsd:attribute name="presentation_name" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						Name used when displaying the primary key.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="presentation_width" type="xsd:integer" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The display width of the field.
                        </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="editable" type="xsd:boolean" default="true" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to allow editing of this field.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="mandatory" type="xsd:boolean" default="false" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						Set true to mark this field as mandatory when editing.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="tooltip" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A tooltip. Usually shown on mouse hover.
                              </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="function_ref" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						If we want to call a function in the sql.
                    </xsd:documentation>
					<xsd:documentation>
						The function StrSubstution to replace ${p1} with the table.fieldname.
                    </xsd:documentation>
					<xsd:documentation>
						example to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="regex" type="xsd:string" use="optional">
				<xsd:annotation>
					<xsd:documentation>
						A Java Regular Expression used to validate data
						beforing storing into a database.
					</xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

   <xsd:element name="timeofday">
      <xsd:annotation>
         <xsd:documentation><![CDATA[
            A definition for a timeofday field.  This is displayed as a single line. The
            value of datetime is HH:MM:SS or HH:MM
            ]]></xsd:documentation>
      </xsd:annotation>
      <xsd:complexType>
         <xsd:attribute name="name" type="xsd:string" use="required">
            <xsd:annotation>
               <xsd:documentation>
                  The name of the field, must match the table field name.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
         <xsd:attribute name="presentation_name" type="xsd:string" use="required">
            <xsd:annotation>
               <xsd:documentation>
                  Name used when displaying the primary key.
                        </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="presentation_width" type="xsd:integer" use="required">
            <xsd:annotation>
               <xsd:documentation>
                  The display width of the field.
                        </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="editable" type="xsd:boolean" default="true" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  Set true to allow editing of this field.
                              </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="mandatory" type="xsd:boolean" default="false" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  Set true to mark this field as mandatory when editing.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="tooltip" type="xsd:string" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  A tooltip. Usually shown on mouse hover.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="function_ref" type="xsd:string" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  If we want to call a function in the sql.
                  <br/>
                  The function StrSubstution to replace ${p1} with the table.fieldname.
                  <br/>
                  example to_char(${p1}, 'HH24:MI.SS')
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="regex" type="xsd:string" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  A Java Regular Expression used to validate data
                  beforing storing into a database.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
      </xsd:complexType>
   </xsd:element>

   <xsd:element name="select">
      <xsd:annotation>
         <xsd:documentation><![CDATA[
            Multi Option Selection used by a radio button display with single
            or multi select option.
            <br/>            
            Use for entries such as "Y,N" or "Y,N,U" or "Yes,No,Maybe,Perhaps" or "true,false"
            or "Fast Car;Slow Car;"
            ]]></xsd:documentation>
      </xsd:annotation>
      <xsd:complexType>
         <xsd:attribute name="name" type="xsd:string" use="required">
            <xsd:annotation>
               <xsd:documentation>
                  The name of the field, must match the table field name.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
            <xsd:attribute name="alias" type="xsd:string" use="optional">
                <xsd:annotation>
                    <xsd:documentation>
                        An alias is used in place of the name when you need to shorten the name and / or
                        to simplify the reference to the name. As an example if you have a primary key
                        field that has a name of "table_name_id" you can replace it with a simple "id"
                        then when you want to reference the field just use the "id".
                    </xsd:documentation>
                </xsd:annotation>
            </xsd:attribute>
         <xsd:attribute name="presentation_name" type="xsd:string" use="required">
            <xsd:annotation>
               <xsd:documentation>
                  Name used when displaying the primary key.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="editable" type="xsd:boolean" default="true" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  Set true to allow editing of this field.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="mandatory" type="xsd:boolean" default="false" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  Set true to mark this field as mandatory when editing.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="tooltip" type="xsd:string" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  A tooltip. Usually shown on mouse hover.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="regex" type="xsd:string" use="optional">
            <xsd:annotation>
               <xsd:documentation>
                  A Java Regular Expression used to validate data
                  beforing storing into a database.
               </xsd:documentation>
            </xsd:annotation>
         </xsd:attribute>
         <xsd:attribute name="select" type="select_options" use="optional" default="single"/>
      </xsd:complexType>
   </xsd:element>

   <xsd:group name="table_fields">
      <xsd:annotation>
         <xsd:documentation>
            Implies an inclusion for the list of field elements for a table.
         </xsd:documentation>
      </xsd:annotation>
      <xsd:sequence>
         <xsd:choice minOccurs="0" maxOccurs="unbounded">
            <xsd:element ref="pk" minOccurs="0" />
            <xsd:element ref="fk" minOccurs="0" />
            <xsd:element ref="text" minOccurs="0" />
            <xsd:element ref="textarea" minOccurs="0" />
            <xsd:element ref="link" minOccurs="0" />
            <xsd:element ref="password" minOccurs="0" />
            <xsd:element ref="image" minOccurs="0" />
            <xsd:element ref="int" minOccurs="0" />
            <xsd:element ref="timestamp" minOccurs="0" />
            <xsd:element ref="datetime" minOccurs="0" />
            <xsd:element ref="date" minOccurs="0" />
            <xsd:element ref="timeofday" minOccurs="0" />
            <xsd:element ref="binary" minOccurs="0" />
            <xsd:element ref="select" minOccurs="0" />
            <xsd:element ref="table_path" minOccurs="0" />
         </xsd:choice>
      </xsd:sequence>
   </xsd:group>


	<xsd:element name="table_path">
		<xsd:annotation>
			<xsd:documentation><![CDATA[
            A table path is used to identify relationship tables.  It identifies which tables are
            linked in the relationship.  The relationship can be a 'OneToOne', 'OneToMany', 'ManyToOne'
            and 'ManyToMany'.
            ]]></xsd:documentation>
		</xsd:annotation>
		<xsd:complexType>
			<xsd:attribute name="table_b" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The name of one of the foreign tables that are referenced from this
						table.
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
			<xsd:attribute name="table_a" type="xsd:string" use="required">
				<xsd:annotation>
					<xsd:documentation>
						The name of one of the foreign tables that are referenced from this
						table.
                    </xsd:documentation>
				</xsd:annotation>
			</xsd:attribute>
		</xsd:complexType>
	</xsd:element>

   <xsd:simpleType name="select_options">
      <xsd:annotation>
         <xsd:documentation>
            Can select a single or multiple select set of radio buttons or check boxes.
         </xsd:documentation>
      </xsd:annotation>
      <xsd:restriction base="xsd:string">
         <xsd:enumeration value="single" />
         <xsd:enumeration value="multiple" />
      </xsd:restriction>
   </xsd:simpleType>
   
</xsd:schema>

  \endcode

*/