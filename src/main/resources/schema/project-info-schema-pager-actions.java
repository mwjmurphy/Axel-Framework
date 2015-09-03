/**
 @author mike.murphy
  
 \page schema_pager_actions_xsd AXEL Pager Actions Schema
 
 \tableofcontents

 \section schema_pager_db_actions_xsd Pager Actions

 Definition of the core actions currently supported by AXEL
 
 Schema:<strong>pager_actions.xsd</strong>
 
  \code{.xml}
	<?xml version="1.0" encoding="UTF-8"?>
	<xsd:schema
	   xmlns="http://www.xmlactions.org/pager_actions"
	   xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	   targetNamespace="http://www.xmlactions.org/pager_actions"
	   attributeFormDefault="unqualified"
	   elementFormDefault="unqualified"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	>
	   <xsd:include schemaLocation="pager_types.xsd"/>
	   <xsd:include schemaLocation="pager_attributes.xsd" />
	
	   <xsd:element name="code">
	      <xsd:annotation><xsd:documentation><![CDATA[
	         <p>
	            Invoke Java code from the web page. The response, if any will replace the code element in the
	            page.
	         </p>
	      ]]></xsd:documentation></xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="param" minOccurs="0" />
	         </xsd:sequence>
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
	
	   <xsd:element name="param">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
				Provides parameters for some of the actions such as code or transform 
				]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="value" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                  This is either<br/>
	                  <ul/>
	                     <li/> The key used to retrieve the value from the execContext
	                     <li/> The value for the parameter.
	                  </ul>
					]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="type" type="param_converter_types" use="optional" default="String">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                  Use this attribute to force a conversion on the parameter.  This may be required
	                  if the parameter type does not match that of the code parameter. 
	               ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="key" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Not required for code actions. 
	               </xsd:documentation>
	               <xsd:documentation>
	                  Required only if mapping the params to a map, will be used as
	                  the key in the map.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="insert">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Insert files into a page. 
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="page" type="xsd:string" use="required">
	            <xsd:annotation><xsd:documentation><![CDATA[
	               <p>
	                  The name of the file to insert.
	                  <br/>
	                  The name of the file may also include the path location
	                  relative to the web root.
	               </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="path" type="xsd:string" use="optional">
	            <xsd:annotation><xsd:documentation><![CDATA[
	               <p>           
	                  Additional path, prepended to the page.
	                  <br/>
	                  May you this as the path location relative to the web root.
	               </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="namespace" type="xsd:string" use="optional" default="pager">
	            <xsd:annotation><xsd:documentation><![CDATA[
	               <p>
	                  Namespace used with pager action commands. e.g
	                  <pager:action...>
	               </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="insert_into">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Insert the current processing page into another page.
	            <p>
	            	This is used to call a page and have that page inserted
	            	into a wrapper page.
	            </p> 
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="page" type="xsd:string" use="required">
	            <xsd:annotation><xsd:documentation><![CDATA[
	               <p>
	                  The name of the page file that the current page will be
	                  inserted into.
	                  <br/>
	                  The name of the file may also include the path location
	                  relative to the web root.
	               </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="path" type="xsd:string" use="optional">
	            <xsd:annotation><xsd:documentation><![CDATA[
	               <p>           
	                  Additional path, prepended to the page.
	                  <br/>
	                  May you this as the path location relative to the web root.
	               </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="namespace" type="xsd:string" use="optional" default="pager">
	            <xsd:annotation><xsd:documentation><![CDATA[
	               <p>
	                  Namespace used with pager action commands. e.g
	                  <pager:action...>
	               </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="key" type="xsd:string" use="required">
	            <xsd:annotation><xsd:documentation><![CDATA[
	               <p>
	                  Replacement Marker Key that matches the replacement marker
	                  on the page.
	               </p>
	               <p>
	               	  The content of the current page will be replaced in
	               	  this page using this replacement market.
	               </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="echo">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Echos content to page 
	            ]]></xsd:documentation>
	      </xsd:annotation>
	   </xsd:element>
	
	   <xsd:element name="if">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Conditional expression evaluation if true executes enclosing actions.
	            The if action may also include elseif and else actions.
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:annotation>
	               <xsd:documentation>
	                  List of actions that are executed if the expression evaluates to true.
	                    </xsd:documentation>
	            </xsd:annotation>
	            <xsd:group ref="actions" />
	         </xsd:sequence>
	         <xsd:attribute ref="expression" use="required" />
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="elseif">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Conditional elseif expression evaluation if true executes enclosing actions.
	            The elseif action is part of the if, elseif, else actions.
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:annotation>
	               <xsd:documentation>
	                  List of actions that are executed if the expression evaluates to true.
	                    </xsd:documentation>
	            </xsd:annotation>
	            <xsd:group ref="actions" />
	         </xsd:sequence>
	         <xsd:attribute ref="expression" use="required" />
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="else">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Conditional elseif expression evaluation if true executes enclosing actions.
	            The else action is the latter part of the if, elseif, else actions.
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:annotation>
	               <xsd:documentation>
	                  List of actions that are executed if neither the if or any of
	                  the elseif/s evaluates to true.
	                    </xsd:documentation>
	            </xsd:annotation>
	            <xsd:group ref="actions" />
	         </xsd:sequence>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="put">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Put a variable into a map.  Can be retrieved with ${key}.
	            The element content is the value for the variable.
	            ]]></xsd:documentation>
	         <xsd:documentation>
	            <br />
	            example:
	            <br />
	            &lt;pager:put key="age"&gt;35&lt;/pager:put&gt;
	         </xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="key" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The key / name for the variable.
					</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="get">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Get a variable from the execContext map.
	            ]]></xsd:documentation>
	         <xsd:documentation>
	            <br />
	            example:
	            <br />
	            &lt;pager:get key="age"/&gt;
	         </xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="key" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  <p>
	                  The key / name for the variable to get from the execContext.
	                  </p>
	                  <p>
	                  Note. key may also contain prefidex map or lang reference i.e. lang:key
	                  </p>
	            </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	
	   <xsd:element name="reset">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Displays a reset form.  Used to logout by removing all persistence data.
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="link" minOccurs="0" />
	            <xsd:element ref="button" minOccurs="0" />
	         </xsd:sequence>
	         <xsd:attribute ref="id" use="required" />
	         <xsd:attribute ref="title" use="optional" />
	         <xsd:attribute ref="visible" use="optional" />
	         <xsd:attribute ref="theme_name" use="optional" />
	      </xsd:complexType>
	   </xsd:element>
	   
	   
	   <xsd:element name="link">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Specifies a href link.  
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="id" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>The id is used only if the link should be stored in the execContext when displaying a List view.</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="name" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>This is the name of the link to be displayed on the form. examples "Submit" or "Cancel".</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="href" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>This is the href that gets called for this link.</xsd:documentation>
	               <xsd:documentation>If set to "javascript:submitLinkWithParams('id','page?params');" All input and option fields will be included in the page request.</xsd:documentation>
	               <xsd:documentation>
	                  This attribute may be set to "" if the submit attribute is set "true", this will cause the containing action class such as add to hide the add from after a successfull submit.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="target" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>This is the href target that the linked page will open in.</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="display_as" type="link_display_options" use="optional" default="link">
	            <xsd:annotation>
	               <xsd:documentation>Displays a link as a 'link' or as a 'button'. The selected theme for a link = INPUT_LINK and for a button = INPUT_BUTTON.</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="submit" type="true_false_options" use="optional" default="false">
	            <xsd:annotation>
	               <xsd:documentation>Set this optional attribute to "true" if this link is to service an ajax.submission for 'add', 'update' or 'delete'.</xsd:documentation>
	               <xsd:documentation>If no links have this attribute set for one of the ajax.submission calls then an exception will be thrown.</xsd:documentation>
	               <xsd:documentation>If more than one link have this attribute set for one of the ajax.submission calls then the first one in the list is selected.</xsd:documentation>
	               <xsd:documentation>The uri attribute will be ignored of this attribute is set true.</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="actionScript" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>This will be the javascript that gets called if this is the submit link.</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="remove_crlf" type="xsd:boolean" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>If this is set true then the actionScript will have any \n \r replaced with &lt;br/&gt;</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="header" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>Only used if this link is used as part of a list</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="tooltip" use="optional" />
	         <xsd:attribute name="width" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>Use this to specify the width of the text if using a button or a href</xsd:documentation>
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
	      </xsd:complexType>
	   </xsd:element>
	   <xsd:element name="button">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Specifies an input button.  
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="name" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the name of the button to be displayed
	                  on the form.
	                  Something like Submit or Cancel.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="uri" type="xsd:integer" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the uri (page) that gets called for the
	                  button. If this
	                  is not set it will use it's parent (ListCP) setting
	                  for URI.
	                  All input and option fields will be included in the
	                  page
	                  request.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	
	   <xsd:element name="file_upload">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Presentation for a file upload.
	            <p>
	            Content will be displayed, which allows for passing extra
	            parameters inside the file_upload element.
	            </p>  
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="id" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The id is a unique identifier on the page, that is used
	                  to identify this file upload form.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="theme_name" use="optional" />
	         <xsd:attribute name="path" id="path" type="xsd:string" use="optional">
	            <xsd:annotation><xsd:documentation><![CDATA[
	                  <p>           
	                  Relative path to storage location where file is stored.
	                  <br/>
	                  Use . if you want the file stored on the root.
	                  </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="tooltip" use="optional" />
	         <xsd:attribute name="field_name" type="xsd:string" use="optional">
	            <xsd:annotation><xsd:documentation><![CDATA[
	   	           <p>
	                 required for db storage
	               </p>
	               <p>
	                  This is the name of the table.field that will be 
	                   used to store the content of the file into.
	               </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="field_file_name" type="xsd:string" use="optional">
	            <xsd:annotation><xsd:documentation><![CDATA[
	               <p>
	                  This is the name of the table.field that will be 
	                  used to store the file name.
	               </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="field_fk_ref" type="xsd:string" use="optional">
	            <xsd:annotation><xsd:documentation><![CDATA[
	               <p>
	                  This is the name of the table.field that will be used to store 
	                  a foreign table reference key.
	               </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="field_fk_ref_value" type="xsd:string" use="optional">
	            <xsd:annotation><xsd:documentation><![CDATA[
	               <p>
	                  This is the value for the field_fk_ref.
	               </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	
	   <xsd:element name="edit_page">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Displays an edit form that allows editing of a server file.
	            The form must include one link that is has it's submit set to true.
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="link" minOccurs="0" />
	         </xsd:sequence>
	         <xsd:attribute ref="id" use="required" />
	         <xsd:attribute name="page_name" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the name of the file to edit.
	                  <xsd:documentation>
	                  </xsd:documentation>
	                  When the form is displayed the file is loaded
	                  from the server and presented for editing.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="visible" use="optional" />
	         <xsd:attribute name="display_as" type="link_display_options" use="optional" default="link">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Displays a link as a 'link' or as a 'button'.
	                  The
	                  selected theme for a link = INPUT_LINK and for a
	                  button =
	                  INPUT_BUTTON.
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="theme_name" use="optional" />
	         <xsd:attribute ref="width" use="required" />
	         <xsd:attribute ref="height" use="required" />
	         <xsd:attribute name="cols" type="xsd:int" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The number of colums to display for the editable
	                  text area.
	                  If this is not set the number of cols will be the width
	                  / 5
						</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="rows" type="xsd:int" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The number of rows to display for the editable
	                  text area.
	                  If this is not set the number of rows will be the height
	                  / 12
						</xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="edit_tab_name" type="xsd:string" use="optional" default="html">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The form presents three tabs / links.
	                  This is the
	                  presentation name for the raw html edit tab. 
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="preview_tab_name" type="xsd:string" use="optional" default="preview">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The form presents three tabs / links.
	                  This is the
	                  preview name for the edit tab. 
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="rte_tab_name" type="xsd:string" use="optional" default="edit">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The form presents three tabs / links.
	                  This is the presentation name for the rich text edit tab. 
	                    </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="frame">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Inserts a frame file into the page.
	            ]]></xsd:documentation>
	         <xsd:documentation><![CDATA[
	            The inner content of the frame element will be injected into
	            the frame at the ${replacement marker}.
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute ref="id" use="optional" />
	         <xsd:attribute name="key" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                  This is the name of the replacement marker in the frame file.   
			       ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="frame_name" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                  The file name of the frame. 
	                  ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="path" type="xsd:string" use="optional">
	            <xsd:annotation><xsd:documentation><![CDATA[
	                  <p>           
	                  Relative path to storage location where file is stored.
	                  <br/>
	                  Use . if you want the file stored on the root.
	                  </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="file_viewer">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Display a file from the server in the html page.
	            ]]></xsd:documentation>
	         <xsd:documentation><![CDATA[
	            There are options to show line numbers and escape the characters in the file so
	            that xml can be displayed on the html page. 
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="file_name" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
			            The name of the xml file to display in the file viewer.
	                    <br/>
	                    To get the content from a stored value in the execContext
	                    use the ref attribute instead.
			            ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="path" type="xsd:string" use="optional">
	            <xsd:annotation><xsd:documentation><![CDATA[
	                  <p>           
	                  Relative path to storage location where file is stored.
	                  <br/>
	                  Use . if you want the file stored on the root.
	                  </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="ref" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                    The reference key used to get the content as a String from the execContext. 
	                    ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
	                    To get the content from a file use the file_name attribute instead.
	                    ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="show_line_nos" type="xsd:string" use="optional" default="true">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
			            Show the line numbers in the display. 
			            ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
			            true = show the line numbers. 
			            ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
			            false = dont show the line numbers. 
			            ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="show_header" type="xsd:string" use="optional" default="true">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                  Show the header with the presentation of the file content. 
	                  ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
	                  true = show the header. 
	                  ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
	                  false = dont show the header. 
	                  ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="escape_content" type="xsd:string" use="optional" default="true">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
			            This option will replace the file content with html escape characters.
			            ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
			            Html escape characters are the &lt;, &gt; and &amp;.  When these are escaped
			            they are converted from &lt; to &amp;lt;, &gt; to &amp;gt; and &amp; to &amp;amp;
			            before they are sent back to the browser.  The browser then converts these back
			            to their un-escaped form and displays them on screen.    
			            ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
			            true = replace file content with html escape characters.
			            ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
			            false = dont replace file content with html escape characters.
			            ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="transform">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Transform an xml document using an xsd style sheet.
	            ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:sequence>
	            <xsd:element ref="param" minOccurs="0">
	               <xsd:annotation>
	                  <xsd:documentation><![CDATA[
	                     Parameters if used will be passed to the transformer.  Making them available
	                     for the transformation. 
	                     ]]></xsd:documentation>
	               </xsd:annotation>
	            </xsd:element>
	         </xsd:sequence>
	         <xsd:attribute name="xslt_file_name" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
			            The name of the transformation file that will perform the transformation. 
			            ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
			            Multiple transformation files may be combined by appending one after the other using a ; seperator.  Usefull if you
			            need to show the content of an include.  
			            ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="xml_file_name" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
			            The name of the xml file to transform. 
			            ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
			            Multiple xml files may be combined by appending one after the other using a ; seperator.  Usefull if you
			            need to show the content of an include. 
			            ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
	                    Instead of using an xml file a xml string may be referenced by using
	                    the xml_ref attribute.
	                    ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="xml_ref" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                    The key used to get the xml as a String from the execContext. 
	                    ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
	                    Instead of using an xml_ref an xml file name may be used by setting
	                    the name of the file in the xml_file_name attribute.
	                    ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="key" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                    If this is set than the transformed output will be placed
	                    into the execContex with this key.  Can be retrieved from the 
	                    execContext using the same key.    
	                    ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="transformer_factory" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                  If this is set than this factory will be used to perform the transformation.    
	               ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
	                  xalan transformer factory = org.apache.xalan.processor.TransformerFactoryImpl     
	               ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="highlight">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Highlights xml and xml type files (such as html, xsd, xslt) for presentation in html format.
	          ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="file_name" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                  The name of the xml style file highlight.
	                  <br/>
	                  To get the content from a stored value in the execContext
	                  use the ref attribute instead.
	                ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="path" type="xsd:string" use="optional">
	            <xsd:annotation><xsd:documentation><![CDATA[
	               <p>           
	               Relative path to storage location where file is stored.
	               <br/>
	               Use . if you want the file stored on the root.
	               </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="ref" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                    The reference key used to get the content as a String from the execContext. 
	                    ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
	                    To get the content from a file use the file_name attribute instead.
	                    ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="key" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                    If this is set than the transformed output will be placed
	                    into the execContex with this key.  Can be retrieved from the 
	                    execContext using the same key.    
	                    ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="attribute_per_line" type="xsd:boolean" use="optional" default="false">
	            <xsd:annotation><xsd:documentation><![CDATA[
	               <p>
	               Allows the highlighter to show each attribute on a new line.
	               </p>
	               <p>
	                  set true to show each attribute on a new line
	               </p>
	               <p>
	                  set false to show all attributes on the same line
	               </p>
	            ]]></xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="debug">
	      <xsd:annotation><xsd:documentation>
	         Display debug information on the presentation page.
	         <br/>
	         The debug works similar to log4j in that there are selectable
	         display levels such as debug, error...
	      </xsd:documentation></xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="level" type="debug_options" use="required">
	            <xsd:annotation><xsd:documentation>
	               level at which the debug information will be displayed
	            </xsd:documentation></xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="navigator">
	      <xsd:annotation>
	         <xsd:documentation>
	            Builds a navigation display from an xml that conforms to the navigator.xsd schema.
	         </xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute ref="id" use="required" />
	         <xsd:attribute ref="theme_name" use="optional" />
	         <xsd:attribute name="navigator_xml_file_name" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
			            The name of the xml file to build the navigator. 
			            ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
	                    Instead of using an xml file a xml string may be referenced by using
	                    the xml_ref attribute.
	                    ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="xml_ref" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                    The key used to get the xml as a String from the execContext. 
	                    ]]></xsd:documentation>
	               <xsd:documentation><![CDATA[
	                    Instead of using an xml_ref an xml file name may be used by setting
	                    the name of the file in the xml_file_name attribute.
	                    ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="key" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation><![CDATA[
	                    If this is set than the produced navigator html will be placed
	                    into the execContex with this key.  Can be retrieved from the 
	                    execContext using the same key.    
	                    ]]></xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="popup">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Builds a popup window that is populated from a server page uri.<br/>
	            example &lt;popup id="xxx" uri="page.uhtml"/&gt;
	         ]]></xsd:documentation>
	         <xsd:documentation><![CDATA[
	            Notes:
	            <p>
	               if the popup is an iframe then the target for the submit link will be set to
	               this popup.iframe.id.  Provided that the target has not been set manually.
	            </p>
	         ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:choice>
	            <xsd:element ref="link" minOccurs="0" maxOccurs="unbounded"/>
	         </xsd:choice>
	         <xsd:attribute ref="id" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The id is used to reference the displaying element such as a div.
	                  The displaying element must be already setup on the page so that
	                  the popup can find it using getElementById and once found set its
	                  innerHTML with the content of the information retrieved from
	                  the url; 
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="display" type="popup_display_options" use="optional" default="self" >
	            <xsd:annotation>
	               <xsd:documentation>
	                  Select if the popup should be displayed by the popup or if it should be drawn
	                  in a dom element that already exists in the page.
	                  <br/>
	                  Note that the position, x and y attributes only apply if this set to "self"
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="href" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the href (page) that gets called for this link.
	               </xsd:documentation>
	               <xsd:documentation>
	                  If set to "javascript:submitLinkWithParams('id','page?params');"
	                  All input and option fields will be included in the page request.
	               </xsd:documentation>
	               <xsd:documentation>
	                  This attribute may be set to "" if the submit attribute
	                  is set "true", this will cause the containing action class
	                  such as add to hide the add form after a successfull submit.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="header_name" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  If this popup is displayed as part of a list than set
	                  this to be the column header name.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
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
	         <xsd:attribute name="position" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  If this is set then it is used as the value for the html
	                  style position value; i.e. position:absolute;
	                  <br/>
	                  This compliments the x and y attribues and will have no
	                  purpose unless the x or y value are set.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="zindex" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  If this is set in than it's value will be stored
	                  into the style as the z-index. Which determines
	                  the layer that applied to this.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="x" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  If this is set then the left position of the popup will be set to
	                  this value.  The popup will also have it's position set to absolute.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="y" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  If this is set then the top position of the popup will be set to
	                  this value.  The popup will also have it's position set to absolute.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="width" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  If this is set and an iframe is selected then this will be the width of the iframe.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="height" type="xsd:string" use="optional">
	            <xsd:annotation>
	               <xsd:documentation>
	                  If this is set and an iframe is selected then this will be the height of the iframe.
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
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="menu">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Builds a popup window that is populated from a server page uri.<br/>
	            example &lt;popup id="xxx" uri="page.uhtml"/&gt;
	         ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="menu_file" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The name of the menu file that the menu is built from. The menu file
	                  is an xml file that conforms to the schema menu.xsd. 
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute ref="theme_name" use="optional" />
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="map_xml_to_bean">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Maps an xml to a java bean.
	         ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="map_file_name" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                   The xml mapping file that conforms to xml_to_bean.xsd
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="xml_key" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the key used to retrieve the xml from the execContext.
	                  This is the xml that will be mapped to an Object using the
	                  map_file_name mapping file.
	                  <br/>
	                  <i>or may use xml_file_name instead</i>
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="xml_file_name" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the file containing the xml that will be mapped to 
	                  an Object using the map_file_name mapping file.
	                  <br/>
	                  <i>or may use xml_key instead</i>
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="key" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Ths is the key used to store the resultant mapped object into
	                  the execContext
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="map_bean_to_xml">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Maps an a java bean to xml.
	         ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute name="map_file_name" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                   The xml mapping file that conforms to bean_to_xml.xsd
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="bean_key" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  This is the key used to retrieve the bean from the execContext.
	                  This is the object that will be mapped to xml using the
	                  map_file_name mapping file.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="key" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Ths is the key used to store the resultant xml into
	                  the execContext
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:element name="submit_form">
	      <xsd:annotation>
	         <xsd:documentation><![CDATA[
	            Builds a form for submission to the server.
	            <p>
	            The form will contain a code call and a page. The actions
	            of the submission will first make the code call and then
	            process the page, which will be returned for display.
	            </p>
	            <p>
	            If there is an error or an exception in the code call than
	            this is presented to the user and the new page will not
	            be called.
	            </p>
	         ]]></xsd:documentation>
	      </xsd:annotation>
	      <xsd:complexType>
	         <xsd:attribute ref="id" use="required" />
	         <xsd:attribute name="call" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  Full path, class and method name of the code to call/execute.
	                  <p>
	                  A response from the code call should contain one of<br/>
	                  OK: for ok to process to page<br/>
	                  EX: for an exception. This will stop proceeding to the page entry<br/>
	                  ER: for an error. May enter additional information for highlighting the error fields.
	                  </p>
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	         <xsd:attribute name="page" type="xsd:string" use="required">
	            <xsd:annotation>
	               <xsd:documentation>
	                  The name of the page to process for presentation.
	               </xsd:documentation>
	            </xsd:annotation>
	         </xsd:attribute>
	      </xsd:complexType>
	   </xsd:element>
	
	   <xsd:group name="actions">
	      <xsd:annotation>
	         <xsd:documentation>
	            Implies an inclusion for the list of action
	            elements, which may be any element.
	            </xsd:documentation>
	      </xsd:annotation>
	      <xsd:sequence>
	         <xsd:choice minOccurs="0" maxOccurs="unbounded">
	            <xsd:any namespace="##other" processContents="strict" minOccurs="0" maxOccurs="unbounded" />
	         </xsd:choice>
	      </xsd:sequence>
	   </xsd:group>
	
	</xsd:schema>  
  \endcode

*/