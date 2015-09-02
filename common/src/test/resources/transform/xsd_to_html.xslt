<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:pager="http://www.riostl.com/schema/pager" 
	>

    <!--
     encoding="UTF-8"
      encoding="ISO-8859-1"
      -->
	<xsl:output method="html" version="1.0" encoding="ISO-8859-1"
		indent="yes" doctype-public="-//W3C//DTD XHTML 1.1//EN"
		doctype-system="http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd" />

	<xsl:template match="xs:schema">
		<xsl:if test="@id">
			<xsl:value-of select="@id" />
			<xsl:text> </xsl:text>
		</xsl:if>
		<a name="top"></a>
	    <p align="center" style="color:white">xsd documentation</p>
		<p align="left" style="color:navy">index</p>
		<br/>
		<p class="content indent">
			<xsl:if test="xs:element">
					<a href="#XSD Elements">Element List</a><br/>
					<a href="#XSD Element Description">Element Description</a><br/>
			</xsl:if>
			<xsl:if test="xs:attribute">
				<a href="#XSD Attributes">Attributes</a><br/>
			</xsl:if>
			<xsl:if test="xs:simpleType">
				<a href="#XSD Simple Types">Simple Types</a><br/>
			</xsl:if>
		</p>
		<xsl:if test="xs:element">
		    <p class="line"></p>
			<a name="XSD Elements" />
		    <p align="center" style="color:white">xsd elements</p>
			<table border="1" cellspacing="0" cellpadding="0" bordercolor="#DDDEFF">
				<tr>
					<th class="th">Element</th>
					<th class="th">Description</th>
				</tr>
				<xsl:for-each select="xs:element">
					<xsl:sort select="@name"/>
					<xsl:apply-templates select="." mode="parent-reference" />
				</xsl:for-each>
			</table>
			<a href="#top">top</a>
			<br/>
		    <p class="line"></p>
			<a name="XSD Element Description" />
		    <p align="center" style="color:white">xsd element description</p>
			<xsl:apply-templates select="xs:element" mode="element-detail" />
		</xsl:if>
		<xsl:if test="xs:attribute">
		    <p class="line"></p>
			<a name="XSD Attributes" />
		    <p align="center" style="color:white">xsd attributes</p>
			<table border="1" cellspacing="0" cellpadding="0" bordercolor="#DDDEFF">
				<tr>
					<th class="th">Name</th>
					<th class="th">Description</th>
				</tr>
				<xsl:for-each select="xs:attribute">
					<xsl:sort select="@name"/>
					<xsl:apply-templates select="." mode="table" />
				</xsl:for-each>
			</table>
			<a href="#top">top</a>
		</xsl:if>
		<xsl:if test="xs:simpleType">
		    <p class="line"></p>
			<a name="XSD Simple Types" />
		    <p align="center" style="color:white">xsd simple types</p>
			<table border="1" cellspacing="0" cellpadding="0" bordercolor="#DDDEFF">
				<tr>
					<th class="th">Name</th>
					<th class="th">Description</th>
				</tr>
				<xsl:for-each select="xs:simpleType">
					<xsl:sort select="@name"/>
					<xsl:apply-templates select="."	mode="table" />
				</xsl:for-each>
			</table>
			<a href="#top">top</a>
		</xsl:if>
	</xsl:template>

	<!-- Draw the elements in a list -->
	<xsl:template match="xs:element" mode="parent-reference">
		<tr>
			<td valign="top">
				<xsl:element name="a">
					<xsl:attribute name="href">#<xsl:value-of
						select="@name" /></xsl:attribute>
					<xsl:value-of select="@name" />
				</xsl:element>
			</td>
			<td>
				<xsl:apply-templates select="xs:annotation/xs:documentation" />
			</td>
		</tr>
	</xsl:template>

	<!-- Draw each element detail -->
	<xsl:template match="xs:element" mode="element-detail">

		<xsl:element name="a">
			<xsl:attribute name="name"><xsl:value-of select="@name" /></xsl:attribute>
		</xsl:element>

	    <p align="left" style="color:white"><xsl:value-of select="@name" /></p>
	
		<xsl:apply-templates select="xs:annotation/xs:documentation" />
	
		<xsl:if test="descendant::xs:attribute">
			<p align="left" style="color:navy">attributes</p>
			<table border="1" cellspacing="0" cellpadding="0" bordercolor="#DDDEFF">
				<tr>
					<th width="10%" class="th">Name</th>
					<th width="10%" class="th">Ref</th>
					<th width="80%" class="th">Description</th>
				</tr>
				<xsl:apply-templates select="descendant::xs:attribute" mode="attribute" />
			</table>
		</xsl:if>
	
		<xsl:if test="descendant::xs:element">
			<p align="left" style="color:navy">children</p>
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#DDDEFF">
				<tr>
					<th width="10%" class="th">Name</th>
					<th width="90%" class="th">Description</th>
				</tr>
				<xsl:apply-templates select="descendant::xs:element" mode="child" />
			</table>
		</xsl:if>
	
		<xsl:variable name="name">
			<xsl:value-of select="@name" />
		</xsl:variable>
		<xsl:if test="/xs:schema/xs:element//xs:element[substring-after(@ref,':')=$name]">
			<p align="left" style="color:navy">parents</p>
			<table  border="1" cellspacing="0" cellpadding="0" bordercolor="#DDDEFF">
				<tr>
					<th width="15%" class="th">Parent Ref</th>
					<th width="85%" class="th">Description</th>
				</tr>
				<xsl:apply-templates
					select="/xs:schema/xs:element//xs:element[substring-after(@ref,':')=$name]"
					mode="parent-link" />
			</table>
		</xsl:if>

		<a href="#top">top</a>

		<br/>
		<br/>
		<br/>
	</xsl:template>

	<!-- element.attribute -->
	<xsl:template match="xs:attribute" mode="attribute">
		<tr>
			<td class="td">
				<xsl:element name="a">
					<xsl:attribute name="name">
						<xsl:if test="@name">
		                	<xsl:value-of select="concat(ancestor::xs:element/@name,'.',@name)" />
		                </xsl:if>
					</xsl:attribute>
				</xsl:element>
				<p class="content indent">
				<xsl:if test="@name"><xsl:value-of select="@name"/></xsl:if>
				</p>
			</td>
			<td>
				<xsl:if test="@ref">
					<xsl:element name="a">
						<xsl:attribute name="href">#<xsl:value-of select="@ref" /></xsl:attribute>
						<xsl:value-of select="@ref" />
					</xsl:element>
				</xsl:if>
			</td>
			<td>
				<xsl:if test="xs:annotation/xs:documentation">
					<p>
						<xsl:apply-templates select="xs:annotation/xs:documentation" />
					</p>
				</xsl:if>
				<xsl:variable name="ref">
					<xsl:value-of select="@ref" />
				</xsl:variable>
				<xsl:if test="//xs:attribute[@name=$ref]/xs:annotation/xs:documentation">
					<p>
						<xsl:value-of select="//xs:attribute[@name=$ref]/xs:annotation/xs:documentation"/>
					</p>
				</xsl:if>
			</td>
		</tr>
	</xsl:template>

	<xsl:template match="xs:element" mode="parent-link">
		<tr>
			<td>
				<xsl:element name="a">
					<xsl:attribute name="href">#<xsl:value-of select="ancestor::xs:element/@name" /></xsl:attribute>
					<xsl:value-of select="ancestor::xs:element/@name" />
				</xsl:element>
			</td>
		</tr>
	</xsl:template>

	<!-- element.child -->
	<xsl:template match="xs:element" mode="child">
		<xsl:variable name="ref">
			<xsl:if test="contains(@ref,':')">
				<xsl:value-of select="substring-after(@ref,':')" />
			</xsl:if>
			<xsl:if test="not(contains(@ref,':'))">
				<xsl:value-of select="@ref" />
			</xsl:if>
		</xsl:variable>
		<tr>
			<td>
				<xsl:element name="a">
					<xsl:attribute name="href">#<xsl:value-of select="$ref"/></xsl:attribute>
					<xsl:value-of select="$ref" />
				</xsl:element>
			</td>
			<td>
				<xsl:if test="not(@minOccurs=0)">
					<font color="maroon">Mandatory</font>
					<br />
				</xsl:if>
				<xsl:if test="xs:annotation/xs:documentation">
					<p>
						<xsl:apply-templates select="xs:annotation/xs:documentation" />
					</p>
				</xsl:if>
				<xsl:if test="//xs:element[@name=$ref]/xs:annotation/xs:documentation">
					<p>
						<xsl:value-of select="//xs:element[@name=$ref]/xs:annotation/xs:documentation"/>
					</p>
				</xsl:if>
			</td>
		</tr>
	</xsl:template>

	<!--
		Process the documentation for an element or an attribute - it don't
		matter which.
	-->
	<xsl:template match="xs:documentation">
		<xsl:if test=".">
			<p>
			<xsl:copy-of select="node()" />
			<font color="green">
				<xsl:if test="../../@type">
					<br />
					type:<xsl:value-of select="../../@type" />,
				</xsl:if>
				<!-- 
				 use:<xsl:if test="../../@use='required'">mandatory</xsl:if><xsl:if test="not(../../@use='required')">optional</xsl:if>
				 -->
				<xsl:apply-templates select="../../xs:simpleType" mode="value" />
			</font>
			</p>
		</xsl:if>
	</xsl:template>

	<xsl:template match="xs:attribute" mode="table">
		<tr>
			<td>
				<xsl:element name="a">
					<xsl:attribute name="name"><xsl:value-of select="@name" /></xsl:attribute>
					<p class="content indent">
						<xsl:value-of select="@name" />
					</p>
				</xsl:element>
			</td>
			<td>
				<p>
					<xsl:apply-templates select="." mode="value" />
				</p>
			</td>
		</tr>
	</xsl:template>
	<!--
		Document a simple type with name in and value description in a row of
		a table.
	-->
	<xsl:template match="xs:simpleType" mode="table">
		<tr>
			<td>
				<p class="content indent">
					<xsl:value-of select="@name" />
				</p>
			</td>
			<td>
				<p>
					<xsl:apply-templates select="." mode="value" />
				</p>
			</td>
		</tr>
	</xsl:template>

	<!-- Document the value description a simple type . -->
	<xsl:template match="xs:simpleType" mode="value">

		<xsl:if test="xs:restriction/@base">
			<br />
			Value is of type:
			<xsl:value-of select="xs:restriction/@base" />
		</xsl:if>
		<xsl:if test="xs:restriction/xs:enumeration/@value">
			<br />
			Value must be one of:
			<br />
			<table  border="1" cellspacing="0" cellpadding="0" bordercolor="#DDDEFF">
				<tr>
					<th width="15%" class="th">Option</th>
					<th width="85%" class="th">Description</th>
				</tr>
				<xsl:for-each select="xs:restriction/xs:enumeration">
					<tr>
						<td>
							<p>
								<xsl:value-of select="@value" />
							</p>
						</td>
						<td>
							<p>
								<xsl:value-of select="xs:annotation/xs:documentation" />
							</p>
						</td>
					</tr>
				</xsl:for-each>
			</table>
		</xsl:if>
		<xsl:if test="xs:restriction/xs:length/@value">
			<br />
			Value must be of length:
			<code>
				<xsl:value-of select="xs:restriction/xs:length/@value" />
			</code>
		</xsl:if>
		<xsl:if test="xs:restriction/xs:minLength/@value">
			<br />Length
			of value must be &gt;=
			<code>
				<xsl:value-of select="xs:restriction/xs:minLength/@value" />
			</code>
		</xsl:if>
		<xsl:if test="xs:restriction/xs:maxLength/@value">
			<br />Length
			of value must be &lt;=
			<code>
				<xsl:value-of select="xs:restriction/xs:maxLength/@value" />
			</code>
		</xsl:if>
		<xsl:if test="xs:restriction/xs:pattern/@value">
			<br />
			Value must match pattern:
			<code>
				<xsl:value-of select="xs:restriction/xs:pattern/@value" />
			</code>
		</xsl:if>

	</xsl:template>


</xsl:stylesheet>
