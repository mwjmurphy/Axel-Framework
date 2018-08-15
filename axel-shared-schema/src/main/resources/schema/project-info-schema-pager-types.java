/**
 @author mike.murphy
  
 \page schema_pager_types_xsd AXEL Pager Types Schema
 
 \tableofcontents

 \section schema_pager_types_xsd Pager Attributes

 This schema defines the types used by other AXEL schemas

 Schema:<strong>pager_types.xsd</strong>
 
  \code{.xml}
	<?xml version="1.0" encoding="UTF-8"?>
	<xsd:schema 
	   xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	   attributeFormDefault="unqualified"
	   elementFormDefault="unqualified">
	
	
	    <xsd:simpleType name="debug_options">
	        <xsd:restriction base="xsd:string">
	            <xsd:enumeration value="none" />
	            <xsd:enumeration value="debug" />
	            <xsd:enumeration value="info" />
	            <xsd:enumeration value="warn" />
	            <xsd:enumeration value="error" />
	        </xsd:restriction>
	    </xsd:simpleType>
	
	    <xsd:simpleType name="popup_display_options">
	        <xsd:restriction base="xsd:string">
	            <xsd:enumeration value="self">
	               <xsd:annotation>
	                  <xsd:documentation>The popup display will be drawn by the popup action.</xsd:documentation>
	               </xsd:annotation>
	            </xsd:enumeration>
	            <xsd:enumeration value="iframe">
	               <xsd:annotation>
	                  <xsd:documentation>The popup will be an iframe.</xsd:documentation>
	               </xsd:annotation>
	            </xsd:enumeration>
	            <xsd:enumeration value="other" >
	               <xsd:annotation>
	                  <xsd:documentation>The popup display will be drawn into a dom element that already exists on the page.</xsd:documentation>
	               </xsd:annotation>
	            </xsd:enumeration>
	        </xsd:restriction>
	    </xsd:simpleType>
	
	
		<xsd:simpleType name="method_options">
			<xsd:restriction base="xsd:string">
				<xsd:enumeration value="post" />
				<xsd:enumeration value="get" />
			</xsd:restriction>
		</xsd:simpleType>
	
		<xsd:simpleType name="link_display_options">
			<xsd:restriction base="xsd:string">
				<xsd:enumeration value="link" />
				<xsd:enumeration value="button" />
				<xsd:enumeration value="menu" />
			</xsd:restriction>
		</xsd:simpleType>
	
		<xsd:simpleType name="true_false_options">
			<xsd:restriction base="xsd:string">
				<xsd:enumeration value="true" />
				<xsd:enumeration value="false" />
			</xsd:restriction>
		</xsd:simpleType>
	
		<xsd:simpleType name="left_right_options">
			<xsd:restriction base="xsd:string">
				<xsd:enumeration value="left" />
				<xsd:enumeration value="right" />
			</xsd:restriction>
		</xsd:simpleType>
	
	   <xsd:simpleType name="label_position_options">
	      <xsd:restriction base="xsd:string">
	         <xsd:enumeration value="left" />
	         <xsd:enumeration value="above" />
	      </xsd:restriction>
	   </xsd:simpleType>
	
		<xsd:simpleType name="alignment_options">
			<xsd:restriction base="xsd:string">
				<xsd:enumeration value="left" />
				<xsd:enumeration value="center" />
				<xsd:enumeration value="right" />
			</xsd:restriction>
		</xsd:simpleType>
	
		<xsd:simpleType name="valignment_options">
			<xsd:restriction base="xsd:string">
				<xsd:enumeration value="top" />
				<xsd:enumeration value="middle" />
				<xsd:enumeration value="bottom" />
			</xsd:restriction>
		</xsd:simpleType>
	
	   <xsd:simpleType name="direction_options">
	      <xsd:restriction base="xsd:string">
	         <xsd:enumeration value="down" />
	         <xsd:enumeration value="across" />
	      </xsd:restriction>
	   </xsd:simpleType>
	
		<xsd:simpleType name="position_options">
			<xsd:restriction base="xsd:string">
				<xsd:enumeration value="top">
					<xsd:annotation>
						<xsd:documentation>
							Position this object (maybe the List Control
							Panel) at the top
			            </xsd:documentation>
					</xsd:annotation>
				</xsd:enumeration>
				<xsd:enumeration value="bottom">
					<xsd:annotation>
						<xsd:documentation>
							Position this object (maybe the List Control
							Panel) at the bottom
			            </xsd:documentation>
					</xsd:annotation>
				</xsd:enumeration>
				<xsd:enumeration value="left">
					<xsd:annotation>
						<xsd:documentation>
							Position this object (maybe the List Control
							Panel) on the left
			            </xsd:documentation>
					</xsd:annotation>
				</xsd:enumeration>
				<xsd:enumeration value="right">
					<xsd:annotation>
						<xsd:documentation>
							Position this object (maybe the List Control
							Panel) on the right
			            </xsd:documentation>
					</xsd:annotation>
				</xsd:enumeration>
			</xsd:restriction>
		</xsd:simpleType>
	
		<xsd:simpleType name="position">
			<xsd:annotation>
				<xsd:documentation>Sets the drawing position, similar to the 'div
					position'</xsd:documentation>
			</xsd:annotation>
			<xsd:restriction base="xsd:string">
				<xsd:enumeration value="absolute">
					<xsd:annotation>
						<xsd:documentation>
							Set the position from the previous positional
							object drawn on the page.
			            </xsd:documentation>
					</xsd:annotation>
				</xsd:enumeration>
				<xsd:enumeration value="relative">
					<xsd:annotation>
						<xsd:documentation>
							Set the position from the current location.
						</xsd:documentation>
					</xsd:annotation>
				</xsd:enumeration>
			</xsd:restriction>
		</xsd:simpleType>
	
		<xsd:simpleType name="search_display_options">
			<xsd:restriction base="xsd:string">
				<xsd:enumeration value="default" />
				<xsd:enumeration value="select" />
			</xsd:restriction>
		</xsd:simpleType>
	
		<xsd:simpleType name="list_populator_types">
			<xsd:annotation>
				<xsd:documentation><![CDATA[
	            These are the available types of list population.
	            ]]></xsd:documentation>
			</xsd:annotation>
			<xsd:restriction base="xsd:string">
				<xsd:enumeration value="sql_ref" />
			</xsd:restriction>
		</xsd:simpleType>
	
	   <xsd:simpleType name="param_converter_types">
	      <xsd:restriction base="xsd:string">
	         <xsd:enumeration value="boolean" />
	         <xsd:enumeration value="byte" />
	         <xsd:enumeration value="short" />
	         <xsd:enumeration value="int" />
	         <xsd:enumeration value="long" />
	         <xsd:enumeration value="float" />
	         <xsd:enumeration value="double" />
	         <xsd:enumeration value="char" />
	         <xsd:enumeration value="String" />
	         <!-- 
	         <xsd:enumeration value="Object" />
	          -->
	      </xsd:restriction>
	   </xsd:simpleType>
	
	</xsd:schema>
  \endcode

*/