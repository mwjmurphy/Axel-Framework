/**
 @author mike.murphy
  
 \page schema_pager_db_actions_xsd AXEL Pager Database Actions Schema
 
 \tableofcontents

 \section schema_pager_db_actions_xsd Pager Database Actions

 Definition of the core database actions currently supported by AXEL

 Schema:<strong>pager_db_actions.xsd</strong>
  
  \code{.xml}
	<xsd:schema
	   targetNamespace="http://www.xmlactions.org/pager_db_actions"
	   xmlns="http://www.xmlactions.org/pager_db_actions"
	   xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	   xmlns:pager="http://www.xmlactions.org/pager_actions"
	   attributeFormDefault="unqualified"
	   elementFormDefault="qualified"
	>
	
	   <xsd:import namespace="http://www.xmlactions.org/pager_actions" schemaLocation="pager_actions.xsd"/>
	   <!-- <xsd:include schemaLocation="pager_actions.xsd" /> -->
	   <xsd:include schemaLocation="pager_types.xsd" />
	   <xsd:include schemaLocation="pager_attributes.xsd" />
	
	   <xsd:element name="search">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Builds a search record ption on screen for the user to enter the details of the fields.
	            ]]></xsd:documentation>
	         <xsd:documentation><![CDATA[
	            After the user enters the search details and selects the 'go' link a query is created from 
	            the selected field list and the response is presented back on screen.
	            ]]></xsd:documentation>
	         <xsd:documentation><![CDATA[
	            @see listcp and list actions. 
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="field_list" minOccurs="0" />
	            <xsd:element ref="pager:link" minOccurs="0" />
	            <xsd:element ref="pager:button" minOccurs="0" />
	            <xsd:element ref="pager:popup" minOccurs="0" />
	         </xsd:sequence>
	         <xsd:attribute ref="id" use="required" />
	         <xsd:attribute ref="title" use="optional" />
	         <xsd:attribute ref="visible" use="optional" />
	         <xsd:attribute ref="table_name" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Is optional if provided by a parent action. 
		            </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="theme_name" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Is optional if the theme is provided by a parent action. 
			            </xsd:documentation>
	               <xsd:documentation>
	                  Or set in a configuration file using the key 'default_theme_name'.
	                  i.e. default_theme_name=blue 
			            </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="label_position" use="optional" />
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="add">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Builds an add record presentation on screen for the user to enter
	            the details of the fields.
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="pre_processes" minOccurs="0" />
	            <xsd:element ref="field_list" minOccurs="0" />
	            <xsd:element ref="post_processes" minOccurs="0" />
	            <xsd:element ref="pager:link" minOccurs="0" />
	            <xsd:element ref="pager:button" minOccurs="0" />
	         </xsd:sequence>
	         <xsd:attribute ref="id" use="required" />
	         <xsd:attribute ref="title" use="optional" />
	         <xsd:attribute ref="visible" use="optional" />
	         <xsd:attribute ref="storage_config_ref" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Is optional if the storage_config_ref is provided by a parent action. 
			            </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="table_name" use="required" />
	         <xsd:attribute ref="theme_name" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Is optional if the theme is provided by a parent action. 
			            </xsd:documentation>
	               <xsd:documentation>
	                  Or set in a configuration file using the key 'default_theme_name'.
	                  i.e. default_theme_name=blue 
			            </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="label_position" use="optional" />
	         <xsd:attribute ref="presentation_form" use="optional" />
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="add_record">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Inserts a record into the database,
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="field_list" minOccurs="1" maxOccurs="1"/>
	         </xsd:sequence>
	         <xsd:attribute ref="storage_config_ref" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Is optional if the storage_config_ref is provided by a parent action. 
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="table_name" use="required" />
	         <xsd:attribute name="key" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  If you set the key to a value in the configuration then
	                  the PK value of the new inserted record will be stored
	                  in the execContext using this key.  It can be retrieved
	                  from the execContext using ${key}. 
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="add_record_link">
	      <xsd:annotation>
	         <xsd:documentation>
	            Adds a record to a storage table when user clicks on the link/button.
	            </xsd:documentation>
	         <xsd:documentation>
	            This action is only available from a list action.
	            </xsd:documentation>
	         <xsd:documentation>
	            Substitution markers may be used in the field content to dynamically populate the fields.
	            </xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="field" minOccurs="0">
	               <xsd:annotation>
	                  <xsd:documentation>
	                     Field elements are used to provide the data inserted to new records.
	    	    	    	</xsd:documentation>
	                  <xsd:documentation>
	                     Substitution markers may be used to dynamically populate the fields.
	    	    	    	</xsd:documentation>
	                  <xsd:documentation>
	                     Any query row data is stored in the execContext as a named map. The map name
	                     is "row". To access any of the attributes returned in the query use
	                     "row:tb_name.field_name"
	    	    	    	</xsd:documentation>
	               </xsd:annotation>
	            </xsd:element>
	         </xsd:sequence>
	         <xsd:attribute ref="id" use="required" />
	         <xsd:attribute ref="presentation_name" use="required" />
	         <xsd:attribute ref="header_name" use="required" />
	         <xsd:attribute name="href" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the href that gets called for this action.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="display_as" use="optional" default="link" />
	         <!-- <xsd:attribute name="submit" type="pt:true_false_options" use="optional" default="false"> <xsd:annotation> 
	            <xsd:documentation> Set this optional attribute to "true" if this link is to service an ajax.submission for 'add', 'update' 
	            or 'delete'. If no links have this attribute set for one of the ajax.submission calls then an exception will be thrown. If 
	            more than one link have this attribute set for one of the ajax.submission calls then the first one in the list is selected. 
	            The href attribute will be ignored of this attribute is set true. </xsd:documentation> </xsd:annotation> </xsd:attribute> -->
	         <xsd:attribute ref="tooltip" use="optional" />
	         <xsd:attribute ref="table_name" use="optional" />
	         <xsd:attribute name="image" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>src for image file</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="image_width" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>Use this to specify the width of an image</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="image_height" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>Use this to specify the height of an image</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="border" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>Sets the border if using an image</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="image_pos" type="left_right_options" use="optional" default="left">
	            <xsd:annotation>
	               <xsd:documentation>If using text which side does the image appear on, left or right</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="update_record_link">
	      <xsd:annotation>
	         <xsd:documentation>
	            Updates a record to a storage table when user clicks on the link/button.
	            </xsd:documentation>
	         <xsd:documentation>
	            This action is only available from a list action.
	            </xsd:documentation>
	         <xsd:documentation>
	            Substitution markers may be used in the field content to dynamically populate the fields.
	            </xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="field" minOccurs="0">
	               <xsd:annotation>
	                  <xsd:documentation>
	                     Field elements are used to provide the data updated to existing records.
	    	    	    	</xsd:documentation>
	                  <xsd:documentation>
	                     Substitution markers may be used to dynamically populate the fields.
	    	    	    	</xsd:documentation>
	                  <xsd:documentation>
	                     Any query row data is stored in the execContext as a named map. The map name
	                     is "row". To access any of the attributes returned in the query use
	                     "row:tb_name.field_name"
	    	    	    	</xsd:documentation>
	               </xsd:annotation>
	            </xsd:element>
	         </xsd:sequence>
	         <xsd:attribute ref="id" use="required" />
	         <xsd:attribute ref="presentation_name" use="required" />
	         <xsd:attribute ref="header_name" use="required" />
	         <xsd:attribute name="href" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the href that gets called for this action.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="display_as" use="optional" default="link" />
	         <!-- <xsd:attribute name="submit" type="pt:true_false_options" use="optional" default="false"> <xsd:annotation> 
	            <xsd:documentation> Set this optional attribute to "true" if this link is to service an ajax.submission for 'add', 'update' 
	            or 'delete'. If no links have this attribute set for one of the ajax.submission calls then an exception will be thrown. If 
	            more than one link have this attribute set for one of the ajax.submission calls then the first one in the list is selected. 
	            The href attribute will be ignored of this attribute is set true. </xsd:documentation> </xsd:annotation> </xsd:attribute> -->
	         <xsd:attribute ref="tooltip" use="optional" />
	         <xsd:attribute ref="table_name" use="required" />
	         <xsd:attribute name="pk_value" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is required for the where clause to select the row that
	                  should be updated.
	                  <br />
	                  This pk value is matched to the table primary key.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="confirm_message" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  If this is not null then the message will be presented to the operator before update of the record.
	               </xsd:documentation>
	               <xsd:documentation>
	                  The message may use replacement markers for substitution with ExecContext data.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="is_allowed" type="xsd:boolean" use="optional" default="true">
	            <xsd:annotation>
	               <xsd:documentation>
	                  if set true then this subchild is drawn.  Used by authorisation restrictions.
	                  <p>
	                  	if this is not set true then by default it will be drawn.
	                  </p>
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="image" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>src for image file</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="image_width" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>Use this to specify the width of an image</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="image_height" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>Use this to specify the height of an image</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="border" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>Sets the border if using an image</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="image_pos" type="left_right_options" use="optional" default="left">
	            <xsd:annotation>
	               <xsd:documentation>If using text which side does the image appear on, left or right</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="delete_record_link">
	      <xsd:annotation>
	         <xsd:documentation>
	            Deletes a record from a storage table when user clicks on the link/button.
	         </xsd:documentation>
	         <xsd:documentation>
	            This action is only available from a list action.
	            </xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="field" minOccurs="0" />
	         </xsd:sequence>
	         <xsd:attribute ref="presentation_name" use="required" />
	         <xsd:attribute ref="header_name" use="required" />
	         <xsd:attribute name="href" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the href that gets called for this action.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="display_as" use="optional" />
	         <xsd:attribute ref="tooltip" use="optional" />
	         <xsd:attribute name="confirm_message" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  If this is not null then the message will be presented to the operator before deletion of the record.
	               </xsd:documentation>
	               <xsd:documentation>
	                  The message may use replacement markers for substitution with ExecContext data.  
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="pk_value" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Set to the Primary Key value of the row that we want to delete from the table.
	               </xsd:documentation>
	               <xsd:documentation>
	                  If not set then the system will use the primary key for this record.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="table_name" use="optional" />
	         <xsd:attribute name="is_allowed" type="xsd:boolean" use="optional" default="true">
	            <xsd:annotation>
	               <xsd:documentation>
	                  if set true then this subchild is drawn.  Used by authorisation restrictions.
	                  <p>
	                  	if this is not set true then by default it will be drawn.
	                  </p>
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="image" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>src for image file</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="image_width" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>Use this to specify the width of an image</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="image_height" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>Use this to specify the height of an image</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="border" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>Sets the border if using an image</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="image_pos" type="left_right_options" use="optional" default="left">
	            <xsd:annotation>
	               <xsd:documentation>If using text which side does the image appear on, left or right</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="view">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            View an existing record
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="field_list" minOccurs="0" />
	            <xsd:element ref="pager:link" minOccurs="0" />
	            <xsd:element ref="pager:button" minOccurs="0" />
	         </xsd:sequence>
	         <xsd:attribute ref="id" use="required" />
	         <xsd:attribute ref="title" use="optional" />
	         <xsd:attribute ref="visible" use="optional" />
	         <xsd:attribute ref="table_name" use="required" />
	         <xsd:attribute name="pk_value" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the pk (primary key) value used to match the record
	                  in the table.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="storage_config_ref" use="required" />
	         <xsd:attribute ref="theme_name" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Is optional if the theme is provided by a parent action. 
			            </xsd:documentation>
	               <xsd:documentation>
	                  Or set in a configuration file using the key 'default_theme_name'.
	                  i.e. default_theme_name=blue 
			            </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="label_position" use="optional" default="left" />
	         <xsd:attribute ref="presentation_name" use="required" />
	         <xsd:attribute name="presentation_form" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The file name of a presentation form that data will be drawn into.
	               </xsd:documentation>
	               <xsd:documentation>
	                  Each result of the query will be drawn into this form.
	                  <br />
	                  Use the ${...} markers to insert the fields from the query into the form.
	                  <br />
	               </xsd:documentation>
	               <xsd:documentation>
	                  As an example to insert the value for field table.fieldName into the form
	                  use ${row:table.fieldName}.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="edit">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Edits an existing record
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="pre_processes" minOccurs="0" />
	            <xsd:element ref="field_list" minOccurs="0" />
	            <xsd:element ref="post_processes" minOccurs="0" />
	            <xsd:element ref="pager:link" minOccurs="0" />
	            <xsd:element ref="pager:button" minOccurs="0" />
	         </xsd:sequence>
	         <xsd:attribute ref="id" use="required" />
	         <xsd:attribute ref="title" use="optional" />
	         <xsd:attribute ref="visible" use="optional" />
	         <xsd:attribute ref="table_name" use="required" />
	         <xsd:attribute name="pk_value" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the pk (primary key) value used to match the record
	                  in the table.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="storage_config_ref" use="required" />
	         <xsd:attribute ref="theme_name" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Is optional if the theme is provided by a parent action. 
	                  </xsd:documentation>
	               <xsd:documentation>
	                  Or set in a configuration file using the key 'default_theme_name'.
	                  i.e. default_theme_name=blue 
	                  </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="label_position" use="optional" default="left" />
	         <xsd:attribute ref="presentation_name" use="required" />
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="list">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Builds a presentation around the response from a search action.
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="field_list" minOccurs="0" maxOccurs="1"/>
	            <xsd:element ref="pager:link" minOccurs="0" maxOccurs="unbounded"/>
	            <xsd:element ref="pager:popup" minOccurs="0" maxOccurs="unbounded"/>
	            <!-- <xsd:element ref="pager:button" minOccurs="0" /> -->
	         </xsd:sequence>
	         <xsd:attribute ref="id" use="required" />
	         <xsd:attribute ref="visible" use="optional" />
	         <xsd:attribute ref="storage_config_ref" use="optional" />
	         <xsd:attribute ref="table_name" use="optional" />
	         <xsd:attribute ref="sql" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  An sql may be used in place of the generated sql.
	                  <p>
	                  The rules around field names must be applied for the presentation to work.
	                  </p>
	                  <p>
	                  The whereClause, orderBy are supported
	                  </p>
	                  <p>
	                  The from - to limits using the ListCP are also supported.
	                  </p>
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="join" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This join will be added to the select statement. It can be
	                  left join, right join, center join.
						</xsd:documentation>
	               <xsd:documentation>
	                  A left join can be used to retrieve data that is not in a table. Example:
	                  SELECT table1.*
	                  FROM table1
	                  LEFT JOIN table2
	                  ON table1.id = table2.id
	                  WHERE table2.id IS NULL;
						</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="where" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  A where is used to select the data returned from a select.
						</xsd:documentation>
	               <xsd:documentation>
	                  Do not provide the 'where' syntax for the where clause. Instead
	                  only provide the conditions of the where clause.
						</xsd:documentation>
	               <xsd:documentation>
	                  example:
	                  tb1.id=tb2.id
	                  or
	                  tb1.name like 'fred' 
						</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="order_by" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  An order_by is used to order the data returned from a select.
	                    </xsd:documentation>
	               <xsd:documentation>
	                  Do not provide the 'order by' syntax for the order by clause. Instead
	                  only provide the conditions of the order by clause.
	                    </xsd:documentation>
	               <xsd:documentation>
	                  example:
	                  TB_FIELD DESC
	                  or
	                  TB_FIELD, TB_DATE DESC 
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="group_by" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  A  group_by is used to group the data returned from a select.
	               </xsd:documentation>
	               <xsd:documentation>
	                  Do not provide the 'group by' syntax for the group by clause. Instead
	                  only provide the field names.
	               </xsd:documentation>
	               <xsd:documentation>
	                  example:
	                  TB_FIELD
	                  or
	                  TB_FIELD, TB_DATE 
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="theme_name" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Is optional if the theme is provided by a parent action. 
			            </xsd:documentation>
	               <xsd:documentation>
	                  Or set in a configuration file using the key 'default_theme_name'.
	                  i.e. default_theme_name=blue 
			            </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="width" use="optional" />
	         <xsd:attribute ref="title" use="optional" />
	         <xsd:attribute name="header_align" type="alignment_options" use="optional" default="center">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The header alignment setting, this will be
	                  applied to all headers in the table list.
	                  Options are center, left, right.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="rows" type="xsd:int" use="optional" default="10">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The number of table rows to display on screen.
						</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="page" type="xsd:int" use="optional" default="10">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The page number to retrieve from the table. The page is a value
	                  of rows * page and references the first row to return form the query.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="row_height" type="xsd:int" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The height of each row in the list presentation.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="presentation_form" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The file name of a presentation form that each row will be drawn into.
	                    </xsd:documentation>
	               <xsd:documentation>
	                  Each row from the query will be drawn into this form.
	                  <br />
	                  Use the ${...} markers to insert the fields from the query into the form.
	                  <br />
	               </xsd:documentation>
	               <xsd:documentation>
	                  As an example to insert the value for field table.fieldName into the form
	                  use ${row:table.fieldName}.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="listcp">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
					A List Control Panel.  Provides the next, prev, first, last and page links.
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="search" minOccurs="0" />
	            <xsd:element ref="list" minOccurs="1" />
	         </xsd:sequence>
	         <xsd:attribute ref="id" use="required" />
	         <xsd:attribute name="href" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The href we call when any of the control panel
	                  links are clicked.
	                  <p>
	                  Additional details are added to the link such as
	                  the limit range, or page numner.
	                  </p>
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="ajax_load" type="xsd:boolean" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  We can use this to have the control panel load and repopulate the display using ajax
	                  in place of doing a complete page reload.
	                  <p>
	                  If using this feature you should build the listcp element in its own file
	                  and import this into the page where it will be displayed.  Then set the href
	                  link to point to the listcp file so it will only bring back the information
	                  from the listcp and not the whole page.
	                  </p>
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="theme_name" use="optional" />
	         <xsd:attribute name="method" type="method_options" use="optional" default="post">
	            <xsd:annotation>
	               <xsd:documentation>
	                  If used, apply this method value to the form
	                  value. Options are post or get.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="display_as" type="link_display_options" use="optional" default="link">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Displays a link as a 'link' or as a 'button'.
	                  The selected theme for a link = INPUT_LINK and for a
	                  button = INPUT_BUTTON.
	                </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="title" use="optional" />
	         <xsd:attribute ref="visible" use="optional" />
	         <xsd:attribute ref="width" use="optional" />
	         <xsd:attribute ref="storage_config_ref" use="optional" />
	         <xsd:attribute ref="table_name" use="optional" />
	         <xsd:attribute name="control_panel_position" type="position_options" use="optional" default="bottom" />
	         <xsd:attribute name="script_before" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This script will be called before the popup is opened.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="script_after" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This script will be called after the popup is opened.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <!-- <xsd:attribute name="slider_size" type="xsd:integer" use="required"> <xsd:annotation> <xsd:documentation> This 
	            the size of the slider in pixels. It is used to set the length or height of the slider, depending on the position of the 
	            slider as set by the control_panel_position. </xsd:documentation> <xsd:documentation> If the control_panel_position is set 
	            top or bottom then the size is the length of the slider. If the control_panel_position is set left or right it is the height 
	            of the slider. </xsd:documentation> </xsd:annotation> </xsd:attribute> -->
	         <!-- <xsd:attribute name="form_id" type="xsd:string" use="optional"> <xsd:annotation> <xsd:documentation> This is 
	            used if there are any buttons within the control panel. </xsd:documentation> <xsd:documentation> When set it should contain 
	            the name of the html form that you want to pull the inputs from when submitting the page. </xsd:documentation> </xsd:annotation> 
	            </xsd:attribute> -->
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="query">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	                A query is used to retrieve information from the database in
	                xml.  The creation of the query file is based on the query.xsd
	                schema. @see xmlactions.org/schema/query.xsd.
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="query_xml_file_name" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the name of xml query definition file. The content
	                  of the file are validated against the query.xsd. 
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="key" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  When a query is retrieved it is stored into the execContext
	                  using this key. When required for mapping or for code calls
	                  it can be retrieved from the execContext using get{${key}); 
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="storage_config_ref" use="required" />
	         <xsd:attribute name="path" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Additional path, prepended to the page.
	               </xsd:documentation>
	               <xsd:documentation>
	                  If this is not set then the default will be the Web Root.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="field">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Specifies a data source field.  A field is a database table field.  
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="pager:link" minOccurs="0" maxOccurs="unbounded">
	               <xsd:annotation>
	                  <xsd:documentation>
	                     If a link is required for a field it can be configured here.
		                    </xsd:documentation>
	               </xsd:annotation>
	            </xsd:element>
	            <xsd:element ref="field" minOccurs="0" maxOccurs="unbounded">
	               <xsd:annotation>
	                  <xsd:documentation>
	                     If a field should control other fields for reloading or
	                     population then add the field or list of fields here.
		              </xsd:documentation>
	               </xsd:annotation>
	            </xsd:element>
	            <xsd:element ref="pager:code" minOccurs="0" maxOccurs="1">
	               <xsd:annotation>
	                  <xsd:documentation>
	                     <p>
	                     <b>A</b>dd code to process / access the data from this field. Usefull
	                     if you need to do a lookup / replacement.
	                     </p>
	                  </xsd:documentation>
	               </xsd:annotation>
	            </xsd:element>
	            <xsd:element ref="populator_sql" minOccurs="0" maxOccurs="1">
	               <xsd:annotation>
	                  <xsd:documentation>
	                     <p>
	                     Used if we want to pre-populate a list as a select option.
	                     </p>
	                     <p>
	                     Note, you may select either an populator_sql or a populator_code
	                     but not both.
	                     </p>
	                   </xsd:documentation>
	               </xsd:annotation>
	            </xsd:element>
	            <xsd:element ref="populator_code" minOccurs="0" maxOccurs="1">
	               <xsd:annotation>
	                  <xsd:documentation>
	                     <p>
	                     Used if we want to pre-populate a list as a select option.
	                     </p>
	                     <p>
	                     Note, you may select either an populator_sql or a populator_code
	                     but not both.
	                     </p>
	                   </xsd:documentation>
	               </xsd:annotation>
	            </xsd:element>
	            <xsd:element ref="attributes" minOccurs="0" maxOccurs="1">
	               <xsd:annotation>
	                  <xsd:documentation>
	                     <p>
	                     These can be used to provide additional attributes to the drawing
	                     element for this field.  These are treated as html attributes
	                     and are added to the drawing element.
	                     </p>
	                     <p>
	                     As an example, if you are creating an edit form and this field
	                     represents a text input, then the attributes will be added to
	                     the input element.
	                     </p>
	                   </xsd:documentation>
	               </xsd:annotation>
	            </xsd:element>
	         </xsd:sequence>
	         <xsd:attribute name="name" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the name of the field that matches the
	                  corresponding field in
	                  the table.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="x" use="optional" />
	         <xsd:attribute ref="y" use="optional" />
	         <xsd:attribute ref="width" use="optional" />
	         <xsd:attribute ref="height" use="optional" />
	         <xsd:attribute name="align" type="xsd:string" use="optional" default="center">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The alignment of the field, when shown in a
	                  table.
	                  options are left, center, right, justify
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="valign" type="xsd:string" use="optional" default="center">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The vertical alignment of the field, when shown in a
	                  table.
	                  options are left, center, right, justify
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <!-- deprecated
	         <xsd:attribute name="display" type="search_display_options" use="optional" default="default">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Options to pre-populate the data and show as a select list box.
	                    </xsd:documentation>
	               <xsd:documentation>
	                  If set to select then an sql_ref may be used to populate the dropdown list. @see sql_ref.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	          -->
	         <xsd:attribute ref="tooltip" use="optional" />
	         <xsd:attribute name="function" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  If we want to call a function in the sql.
	                    </xsd:documentation>
	               <xsd:documentation>
	                  Will overwrite any functions set in the storage definition.
	                    </xsd:documentation>
	               <xsd:documentation>
	                  The function uses the '%s' String.format syntax to have
	                  the field name replaced in the function.
	                    </xsd:documentation>
	               <xsd:documentation>
	                  example to_char(%s, 'DD/MM/RRRR HH24:MI.SS')
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="direction" type="direction_options" use="optional" default="down" />
	         <xsd:attribute name="css" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This css is a reference to one or more css elements. If this is
	                  set the content will be appened to any generated css.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="onclick" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Script to be run when an element is clicked.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="onchanged" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Script to be run when this field changes
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="onselect" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Script to be run when this field is selected
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="onfocus" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Script to be run when this field gets focus
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="prefix" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  prepend this value to the current value when editing
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="postfix" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  append this value to the current value when editing
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="snippet_ref" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Use this to have the field drawn into a drawing template.
	                  <p>
	                     A drawing template is a pre-constructed html snippet that
	                     is used to present field data on screen.
	                  </p>
	                  <p>
	                     <b>html snippet replacement markers</b>
	                     <br />
	                     The html snippet can provide replacement markers for these items
	                     <li>label/header - ${label}</li>
	                     <li>value - ${value}</li>
	                     <li>name - ${name}</li>
	                     <li>id - ${id}</li>
	                  </p>
	                  <p>
	                     The replacement markers will be substutited when the snippet is draw
	                     onto the page.
	                  </p>
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         
	      </xsd:complexType>
	   </xsd:element>
	   
	   <xsd:element name="field_code">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Specifies a data source field.  A field is a database table field.  
	            ]]></xsd:documentation>
	         <xsd:documentation><![CDATA[
	            Document not up to date.  Needs to be validated against the field_code action  
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="pager:code" minOccurs="0" maxOccurs="1">
	               <xsd:annotation>
	                  <xsd:documentation>
	                     <p>
	                     <b>A</b>dd code to process / access the data from this field. Usefull
	                     if you need to do a lookup / replacement.
	                     </p>
	                  </xsd:documentation>
	               </xsd:annotation>
	            </xsd:element>
	            <xsd:element ref="attributes" minOccurs="0" maxOccurs="1">
	               <xsd:annotation>
	                  <xsd:documentation>
	                     <p>
	                     These can be used to provide additional attributes to the drawing
	                     element for this field.  These are treated as html attributes
	                     and are added to the drawing element.
	                     </p>
	                     <p>
	                     As an example, if you are creating an edit form and this field
	                     represents a text input, then the attributes will be added to
	                     the input element.
	                     </p>
	                   </xsd:documentation>
	               </xsd:annotation>
	            </xsd:element>
	         </xsd:sequence>
	         <xsd:attribute name="name" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the name of the field that matches the
	                  corresponding field in
	                  the table.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="x" use="optional" />
	         <xsd:attribute ref="y" use="optional" />
	         <xsd:attribute ref="width" use="optional" />
	         <xsd:attribute ref="height" use="optional" />
	         <xsd:attribute name="align" type="xsd:string" use="optional" default="center">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The alignment of the field, when shown in a
	                  table.
	                  options are left, center, right, justify
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="valign" type="xsd:string" use="optional" default="center">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The vertical alignment of the field, when shown in a
	                  table.
	                  options are left, center, right, justify
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="tooltip" use="optional" />
	         <xsd:attribute name="direction" type="direction_options" use="optional" default="down" />
	         <xsd:attribute name="css" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This css is a reference to one or more css elements. If this is
	                  set the content will be appened to any generated css.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="onclick" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Script to be run when an element is clicked.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="onchanged" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Script to be run when this field changes
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="onselect" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Script to be run when this field is selected
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="onfocus" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Script to be run when this field gets focus
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="snippet_ref" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Use this to have the field drawn into a drawing template.
	                  <p>
	                     A drawing template is a pre-constructed html snippet that
	                     is used to present field data on screen.
	                  </p>
	                  <p>
	                     <b>html snippet replacement markers</b>
	                     <br />
	                     The html snippet can provide replacement markers for these items
	                     <li>label/header - ${label}</li>
	                     <li>value - ${value}</li>
	                     <li>name - ${name}</li>
	                     <li>id - ${id}</li>
	                  </p>
	                  <p>
	                     The replacement markers will be substutited when the snippet is draw
	                     onto the page.
	                  </p>
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="field_raw">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Specifies a data source field.  This is part of a field_list and provides a means for inserting raw input to an list.  
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <!-- 
	            <xsd:simpleContent/>
	            -->
	         </xsd:sequence>
	         <xsd:attribute ref="header_name" use="required" />
	      </xsd:complexType>
	   </xsd:element>
	   
	   <xsd:element name="field_hide">
	      <xsd:annotation>
	         <xsd:documentation>
	            Add a hidden input to a form for an Add Action.
	            </xsd:documentation>
	         <xsd:documentation>
	            Use this to add hidden inputs to an Add Action that will pass these inputs to the insert method.
	            </xsd:documentation>
	         <xsd:documentation>
	            Substitution markers may be used in the field content to dynamically populate the fields.
	            </xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="name" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the name of the field that matches the
	                  corresponding field in the table.
	                    </xsd:documentation>
	               <xsd:documentation>
	                  The name may include the table name or alias using the "table.fieldname" format.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="value" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The value to be inserted into the table may be stored here.
	                    </xsd:documentation>
	               <xsd:documentation><![CDATA[
							If the value is not stored here then it must be stored as the content of the element.
							i.e. <field_hide name="xyz">The value to store in the database</field_name>
	                    ]]></xsd:documentation>
	               <xsd:documentation>
	                  Substitution markers may be used in the field content to dynamically populate the fields.
			            </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	
	   <xsd:element name="field_list">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Specifies a list of data source fields.  
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:choice>
	            <xsd:element ref="field" minOccurs="0" />
	            <xsd:element ref="add_record_link" minOccurs="0" />
	            <xsd:element ref="delete_record_link" minOccurs="0" />
	            <xsd:element ref="update_record_link" minOccurs="0" />
	            <xsd:element ref="field_code" minOccurs="0" maxOccurs="unbounded" />
	            <xsd:element ref="field_hide" minOccurs="0" maxOccurs="unbounded" />
	            <xsd:element ref="field_raw" minOccurs="0" maxOccurs="unbounded"/>
	            <xsd:element ref="pager:link" minOccurs="0" />
	            <xsd:element ref="pager:popup" minOccurs="0" />
	         </xsd:choice>
	      </xsd:complexType>
	   </xsd:element>
	
	
	   <xsd:element name="insert_record">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Inserts a record into the database
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="populator_sql">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Used to build a dropdown list for search, edit and add.
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="ref" use="required">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
			            This is a reference to the sql that is used for population.
			            ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="default_value" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
			            Which row to select as the default.
			            ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="populator_code">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Used to build a dropdown list for search, edit and add.
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="pager:code">
	               <xsd:annotation>
	                  <xsd:documentation>
	                     <p>
	                     This is a reference to the code action that will be used to 
	                     build the dropdown list.
	                     </p>
	                     <p>
	                     The code must return an xml string containing a "root"
	                     element that contains one or more child elements. The child
	                     element must have 2 or 3 attributes. The first attribute is 
	                     ignored as this is commonly used as a row index.
	                     </p>
	                     <p>
	                     If 2 attributes are used the the 2nd is both the label and
	                     the value.
	                     </p>
	                     <p>
	                     If 3 attributes are used the the 2nd is value and the 3rd is 
	                     the label.
	                     </p>
	                     <p>
	                     Example:<br/>
	                     &lt;root&gt;
	                      &lt;any_name index="1" value="1" label="fred" /&gt;
	                      &lt;any_name index="2" value="12" label="barney"/&gt;
	                      &lt;any_name index="3" value="123" label="wilma"/&gt;
	                     &lt;/root&gt;                  
	                     </p>
	                  </xsd:documentation>
	               </xsd:annotation>
	            </xsd:element>
	         </xsd:sequence>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="pre_processes">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            A list of Java Code Calls. This is called before the processing has completed for this action. 
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="pager:code" minOccurs="0" maxOccurs="unbounded" />
	         </xsd:sequence>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="post_processes">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            A list of Java Code Calls. This is called after the processing has completed for this action. 
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="pager:code" minOccurs="0" maxOccurs="unbounded" />
	         </xsd:sequence>
	      </xsd:complexType>
	   </xsd:element>
	
	
	   <xsd:element name="processor">
	      <xsd:annotation>
	         <xsd:documentation>
	            Invoke Java code. 
	            </xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="call" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
						Full path, class and method name.
						]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
	                    Use param to pass parameters to the code call.
	                    ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="attributes">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            This is a container element for attributes. Any attributes that are contained
	            within this element will be used by the parent owner of the attributes, such as 
	            a field element. 
	         ]]></xsd:documentation>
	      </xsd:annotation>
	   </xsd:element>
	
	
	</xsd:schema>
  \endcode

*/