
package org.xmlactions.db.actions;


import java.util.ArrayList;
import java.util.List;

import org.xmlactions.action.config.IExecContext;


public abstract class CommonStorageField extends BaseStorageField
{

	
    /**
     * If this field was found by using a FK reference then this is the
     * FK that was used to find it.
     */
    private FK refFk;
    
	/**
	 * Name of the field, matches the field name in the database table.
	 */
	private String name;

	/**
	 * Alias name of the field, use this to reference the field rather than the name alias this can be more accurate alias a
	 * reference than the field name.
	 */
	private String alias;

    /**
     * If we want to call a function from the sql. The function must use the
     * ${p1} syntax to have the field name replaced in the
     * function. Like to_char(${p1}, 'DD/MM/RRRR HH24:MI.SS')
     */
    private String function_ref;
    
    /**
     * This is used to set the data / time / datetime format before saving to the db.
     * It can be any java date/time formatter.
     */
    private String date_format;
    
    /**
     * If we want to validate the value before storing we can use this regex.
     */
    private String regex;

	/**
	 * The length of the field, matches the varchar or date size.
	 */
	private int length;

	private String presentation_name;

	private int presentation_width;

	private int presentation_height;

	private boolean editable = true; // default value

	private boolean mandatory = false; // default value

	private boolean unique = false; // default value

	private String tooltip;
	
	private String query_sql;	// we can override the generated query sql by putting a handmade sql here.
	
	private String pattern;	// html5 pattern attribute for an input
	
	private String placeholder;	// html5 placeholder attribute for an input
	
	/**
	 * These Links back to FKs are build by Storage.completeStructure.
	 */
	private List<CommonStorageField> backLinkedFks;


	public int getLength()
	{

		return length;
	}

	public void setLength(int length)
	{

		this.length = length;
	}

	public String getName()
	{

		return name;
	}

	public String getName(IExecContext execContext)
	{

		return execContext.replace(name);
	}

	public void setName(String name)
	{

		this.name = name;
	}

	public String getPresentation_name()
	{

		return presentation_name;
	}

	public String getPresentation_name(IExecContext execContext)
	{

		return execContext.replace(presentation_name);
	}

	public void setPresentation_name(String presentationName)
	{

		presentation_name = presentationName;
	}

	public int getPresentation_width()
	{

		return presentation_width;
	}

	public void setPresentation_width(int presentationWidth)
	{

		presentation_width = presentationWidth;
	}

	public int getPresentation_height()
	{

		return presentation_height;
	}

	public void setPresentation_height(int presentationHeight)
	{

		presentation_height = presentationHeight;
	}

	
	public boolean isEditable()
	{

		return editable;
	}

	public void setEditable(boolean editable)
	{

		this.editable = editable;
	}

	public boolean isMandatory()
	{

		return mandatory;
	}

	public void setMandatory(boolean mandatory)
	{

		this.mandatory = mandatory;
	}

	public String getTooltip()
	{

		return tooltip;
	}

	public String getTooltip(IExecContext execContext)
	{

		return execContext.replace(tooltip);
	}

	public void setTooltip(String tooltip)
	{

		this.tooltip = tooltip;
	}

	public void setUnique(boolean unique)
	{

		this.unique = unique;
	}

	public boolean isUnique()
	{

		return unique;
	}

	public void setAlias(String alias)
	{

		this.alias = alias;
	}

	/**
	 * Will return the alias if set else will return the name.
	 * 
	 * @return alias if set else will return name.
	 */
	public String getAlias()
	{

		return alias != null ? alias : name;
	}

    public void setFunction_ref(String function_ref) {
        this.function_ref = function_ref;
    }

    public String getFunction_ref() {
        return function_ref;
    }

	public void addBackLinkedFk(CommonStorageField backLinkedFk) {
		
		getBackLinkedFks().add(backLinkedFk);
	}

	public List<CommonStorageField> getBackLinkedFks() {
		if (backLinkedFks == null) {
			backLinkedFks = new ArrayList<CommonStorageField>();
		}
		return backLinkedFks;
	}

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public void setRefFk(FK refFk) {
        this.refFk = refFk;
    }

    public FK getRefFk() {
        return refFk;
    }

	public String getQuery_sql() {
		return query_sql;
	}

	public void setQuery_sql(String query_sql) {
		this.query_sql = query_sql;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getDate_format() {
		return date_format;
	}

	public void setDate_format(String date_format) {
		this.date_format = date_format;
	}

}
