/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */


package org.xmlactions.pager.actions.form;


import org.apache.bsf.BSFException;
import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.actions.ActionUtils;
import org.xmlactions.action.actions.Attributes;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.XmlCData;
import org.xmlactions.common.theme.Theme;

public abstract class CommonFormFields extends BaseFormAction
{

	/** If we want to do positional drawing we may need to set the z-index */
	private String zindex;
	/**
	 * The presentation x position. Same usage as html in that it can represent percentage, px and any other html
	 * applicable format.
	 */
	private String x;

	/**
	 * The presentation y position. Same usage as html in that it can represent percentage, px and any other html
	 * applicable format.
	 */
	private String y;

	/**
	 * The display width of the form, when shown in a table. The width can also be an equation or a percentage
	 */
	private String width;

	/**
	 * The presentation height. Same usage as html in that it can represent ercentage, px and any other html applicable
	 * format.
	 */
	private String height;

	private String align;

    private String valign;

    /** across or down - used for displaying checkboxes or radio buttons */
    private String direction;

	/**
	 * Used to identify this "add" form if more than one are set on a page. This is can then be used to show or hide
	 * this form using the pager.js
	 */
	private String id;

	private String name;

	/**
	 * Title displayed for this form. If this is empty no title will be displayed.
	 */
	private String title;

	/**
	 * Adds a tooltip to the link when displayed.
	 */
	private String tooltip;

	/**
	 * Set "false" to hide the form when displayed, will need to call javascript show("id") to display the form. Default
	 * value = "true"
	 */
	private boolean visible = true;
	
    /**
     * If this is set, use it to populate lists, dropdowns, checkboxes etc.
     */
    private String populatorXml;

    /**
     * Reference to css. as class for html
     */
    private String css;
	


	/**
	 * This is the bean reference to the StorageConfig to use for this action.
	 * The StorageConfig is pre-configured and stored in the execution context.
	 * The StorageConfig contains the dbConnector, storageContainer and the databaseName. 
	 */ 
    private String storage_config_ref;

	private String onclick;
	private String onchange;
	private String onselect;
	private String onfocus;
	
    /** This is a reference to a drawing snippet */
    private String snippet_ref;

    /** We can use this to add any attributes when drawing the form field. */
    private Attributes attributes;


	private String prefix;
	private String postfix;
	

	/**
	 * This is the bean reference to the storage we want to use. A storage is a pre-loaded definition such as a database
	 * that includes the meta-data. i.e. db_layout.xml. The storage_ref is used to retrieve the data source from the
	 * execContext. This is an optional setting as it may retrieve the value from a parent element such as "listcp".
	 * <p>
	 * Will traverse upwards looking for a value.
	 * </p>
	 * private String storage_ref;
	 */

	/**
	 * This is the name of data source that will provide the database connection. This is a reference to a spring
	 * configured bean. The data source is usually configured on the application/web server, if not it can be setup from
	 * a spring configuration.
	 * 
	 * @see myDataSource configured in the spring-pager-web-startup.xml
	 * 
	 *      <p>
	 *      Will traverse upwards looking for a value.
	 *      </p>
	 *      private String data_source_ref;
	 */


	/**
	 * This is the name of the database in the data storage. If no database_name is entered then the first one in the
	 * storage definition is used. The table_name attribute is contained in this database. This is an optional setting
	 * as it may retrieve the value from a parent element such as "listcp".
	 * <p>
	 * Will traverse upwards looking for a value.
	 * </p>
	 * private String database_name;
	 */

	/**
	 * This is the name of the table in the data source table to search. To limit the fields required for the search the
	 * fields may also be specified in this action. This is an optional setting as it may retrieve the value from a
	 * parent element such as "listcp".
	 * 
	 * <p>
	 * Will traverse upwards looking for a value.
	 * </p>
	 */
	private String table_name;

	/**
	 * User provides a full sql that is used to query, insert or update directly to the database.
	 * <p>
	 * The sql will be used in place of the db functionality.
	 * </p>
	 */
	private String sql;


	/**
	 * This is the theme name, reference to a pre-loaded theme property file that has the attribute settings for drawing
	 * this theme.
	 * 
	 * @see theme.doc or theme.txt
	 */
	private String theme_name;

	/**
	 * 1. If the user has set a theme_name we use this else
	 * 2. If there is a IExecContext.SELECTED_THEME_NAME set in the confuration we will use this
	 * 3. If there is a IExecContext.DEFAULT_THEME_NAME set in the confuration we will use this
	 * 4. If all else fails we use the Theme.DEFAULT_THEME_NAME 
	 * @param execContext
	 * @return
	 */
	public String getTheme_name(IExecContext execContext)
	{
		String tn = theme_name;
		// See if the user has set a theme name
		if (StringUtils.isEmpty(theme_name)) {
			tn = this.getFirstValueFound("theme_name");
		}
		if (tn != null) {
			// see if a StrSubstution is required for the user theme_name
			tn=execContext.replace(tn);
		} else {
			tn = execContext.getString(IExecContext.SELECTED_THEME_NAME);
			if (tn == null) {
				// see if the configuration has a default theme name
				tn = execContext.getString(IExecContext.DEFAULT_THEME_NAME);
				if (tn == null) {
					// return the Theme.DEFAULT_THEME_NAME
					tn = Theme.DEFAULT_THEME_NAME;
				}
			}
		}
		return tn;
	}

	public String getTheme_name()
	{
		if (StringUtils.isEmpty(theme_name)) {
			return this.getFirstValueFound("theme_name");
		}
		return theme_name;
	}

	public void setTheme_name(String themeName)
	{

		theme_name = themeName;
	}

	/** Gets set when we get the theme from theme_name */
	private Theme theme;

	public void setTheme(Theme theme)
	{

		this.theme = theme;
	}

	public Theme getTheme()
	{
		if (theme == null) {
			throw new IllegalArgumentException("Theme not set");
		}
		return theme;
	}

	public Theme getTheme(IExecContext execContext)
	{
		if (theme == null) {
			String themeName = execContext.getString(execContext.DEFAULT_THEME_NAME);
			// fix code - can't declare another Theme here as it wont get returned.
			// code was Theme theme = execContext.getThemes().getTheme(themeName);
			theme = execContext.getThemes().getTheme(themeName);
			if (theme == null) {
				throw new IllegalArgumentException("Theme not set, and no default theme found for [" + themeName + "]");
			}
		}
		return theme;
	}

	/**
	 * Selects how to display the label and field.
	 * <p>
	 * left = label displayed to left of field.<br/>
	 * above = label displayed above field.<br/>
	 * </p>
	 */
	private String label_position;

	/**
	 * This is the presentation name for the link
	 */
	private String presentation_name;

	/**
	 * This is the presentation name for the link/field_code header
	 */
	private String header_name;

	/**
	 * evaluates to true then enclosing actions are executed. The expression may contain parameter references using the
	 * replacement markers. e.g. ${session:key}. The characters &lt;, &gt; and &amp; must be used in place of their
	 * replacement characters.
	 */
	private String expression;

	/**
	 * This is the uri (page) that gets called for this link. All input and option fields will be included in the page
	 * request. This attribute may be set to "" if the submit attribute is set "true", this will cause the containing
	 * action class such as add to hide the add from after a successfull submit.
	 */
	private String uri;
	private String href;

	/**
	 * Displays a link as a 'link' or as a 'button'. The selected theme for a link = INPUT_LINK and for a button =
	 * INPUT_BUTTON.
	 */
	private String display_as;

	/**
	 * Gets the first found storage_ref from this bean or it's parents.
	 * 
	 * @return the value or null if not found/set.
	 */
	// public String getStorage_ref()
	// {
	//
	// if (StringUtils.isEmpty(storage_ref)) {
	// return this.getFirstValueFound("storage_ref");
	// }
	// return storage_ref;
	// }
	//
	// public void setStorage_ref(String storage_ref)
	// {
	//
	// this.storage_ref = storage_ref;
	// }

    /**
     * Gets the first found table_name from this bean or it's parents.
     * 
     * @return the value or null if not found/set.
     */
    public String getTable_name() {

        if (StringUtils.isEmpty(table_name)) {

            return this.getFirstValueFound("table_name");
        }
        return table_name;
    }

    public void setTable_name(String table_name) {

        this.table_name = table_name;
    }

	/**
	 * User provides a full sql that is used to query, insert or update directly to the database.
	 * <p>
	 * The sql will be used in place of the db functionality.
	 * </p>
	 */
	public String getSql()
	{
		return XmlCData.removeCData(sql);
	}

	public void setSql(String sql)
	{
		this.sql = sql;
	}

	// /**
	// * Gets the first found database_name from this bean or it's parents.
	// *
	// * @return the value or null if not found/set.
	// */
	// public String getDatabase_name()
	// {
	//
	// if (StringUtils.isEmpty(database_name)) {
	//
	// return this.getFirstValueFound("database_name");
	// }
	// return database_name;
	// }
	// public void setDatabase_name(String database_name)
	// {
	//
	// this.database_name = database_name;
	// }


	/**
	 * Gets the first found data_source_ref from this bean or it's parents.
	 * 
	 * @return the value or null if not found/set.
	 */
	// public String getData_source_ref()
	// {
	//
	// if (StringUtils.isEmpty(data_source_ref)) {
	// return this.getFirstValueFound("data_source_ref");
	// }
	// return data_source_ref;
	// }
	//
	// public void setData_source_ref(String data_source_ref)
	// {
	//
	// this.data_source_ref = data_source_ref;
	// }

	public String getX()
	{

		return x;
	}

	public String getX(IExecContext execContext) throws BSFException
	{

		if (x == null)
			return x;
		return ActionUtils.evaluateCalculation(execContext, x);
	}

	public void setX(String x)
	{

		this.x = x;
	}

	public String getY()
	{

		return y;
	}

	public String getY(IExecContext execContext) throws BSFException
	{

		if (y == null)
			return y;
		return ActionUtils.evaluateCalculation(execContext, y);
	}

	public void setY(String y)
	{

		this.y = y;
	}

	public String getWidth()
	{

		return width;
	}

	public String getWidth(IExecContext execContext) throws BSFException
	{

		if (width == null)
			return width;
		return ActionUtils.evaluateCalculation(execContext, width);
	}


	public void setWidth(String width)
	{

		this.width = width;
	}

	public String getHeight()
	{

		return height;
	}

	public void setHeight(String height)
	{

		this.height = height;
	}

	public String getId()
	{

		return id;
	}

	public void setId(String id)
	{

		this.id = id;
	}

	public String getTitle()
	{

		return title;
	}

	public void setTitle(String title)
	{

		this.title = title;
	}

	public String getTooltip()
	{

		return tooltip;
	}

	public void setTooltip(String tooltip)
	{

		this.tooltip = tooltip;
	}

	public boolean isVisible()
	{

		return visible;
	}

	public void setVisible(boolean visible)
	{

		this.visible = visible;
	}

	public String getLabel_position()
	{

		return label_position;
	}

	public void setLabel_position(String labelPosition)
	{

		label_position = labelPosition;
	}

	public String getPresentation_name()
	{

		return presentation_name;
	}

	public void setPresentation_name(String presentationName)
	{

		presentation_name = presentationName;
	}

	public String getHeader_name()
	{

		return header_name;
	}

	public void setHeader_name(String headerName)
	{

		header_name = headerName;
	}

	public String getExpression()
	{

		return expression;
	}

	public void setExpression(String expression)
	{

		this.expression = expression;
	}

	public String getUri()
	{

		return getHref();
	}

	public void setUri(String uri)
	{

		setHref(uri);
	}

	public String getHref()
	{
		return href == null ? "" : href;
	}

	public void setHref(String href)
	{

		this.href = href;
	}

	public String getDisplay_as()
	{

		return display_as;
	}

	public void setDisplay_as(String displayAs)
	{

		display_as = displayAs;
	}

	public void setAlign(String align)
	{

		this.align = align;
	}

	public String getAlign()
	{

		return align;
	}

	public void setValign(String valign)
	{

		this.valign = valign;
	}

	public String getValign()
	{

		return valign;
	}

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

	public void setName(String name)
	{

		this.name = name;
	}

	public String getName()
	{

		return name;
	}


	public void setStorage_config_ref(String storage_config_ref)
	{
		this.storage_config_ref = storage_config_ref;
	}

	public String getStorage_config_ref()
	{
		return storage_config_ref;
	}
	/**
	 * 1. If the user has set a storage_config_ref we use it else
	 * 2. If there is a IExecContext.DEFAULT_STORAGE_CONFIG_REF set in the configuration we will use it
	 * 3. We then see of a replace is required.
	 * @param execContext
	 * @return
	 */
	public String getStorage_config_ref(IExecContext execContext)
	{
		String sn = storage_config_ref;
		// See if the user has set a theme name
		if (StringUtils.isEmpty(storage_config_ref)) {
			sn = this.getFirstValueFound("storage_config_ref");
		}
		if (sn == null) {
			// see if the configuration has a default storage config
			sn = execContext.getString(IExecContext.DEFAULT_STORAGE_CONFIG_REF);
		}
		if (sn != null) {
			sn = execContext.replace(sn);
		}
		return sn;
	}

	public void setPopulatorXml(String populatorXml) {
		this.populatorXml = populatorXml;
	}

	public String getPopulatorXml() {
		return populatorXml;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getCss() {
		return css;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnselect() {
		return onselect;
	}

	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getOnfocus() {
		return onfocus;
	}
	public void setSnippet_ref(String snippet_ref) {
		this.snippet_ref = snippet_ref;
	}


	public String getSnippet_ref() {
		return snippet_ref;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setZindex(String zindex) {
		this.zindex = zindex;
	}

	public String getZindex() {
		return zindex;
	}


	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

}
