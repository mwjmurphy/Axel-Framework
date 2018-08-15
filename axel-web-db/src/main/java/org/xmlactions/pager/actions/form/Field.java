
package org.xmlactions.pager.actions.form;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.CodeAction;
import org.xmlactions.pager.actions.form.populator.CodePopulator;
import org.xmlactions.pager.actions.form.populator.Populator;
import org.xmlactions.pager.actions.form.populator.SqlPopulator;
import org.xmlactions.pager.drawing.IDrawParams;


/**
 * Display information for a field (retrieved from data)
 * <p>
 * A Field compliments the drawing elements by inserting a field inside the drawing element. As an example:<br/>
 * <code>
 *  &lt;pager:list storage_ref="pager.storage" table_name="tb_project" theme_name="riostl" data_source_ref="pager.dataSource"&gt;
 *    &lt;pager:<b>field</b> name="id" link="search.xml"/&gt;
 *    &lt;pager:<b>field</b> name="description"/&gt;
 *    &lt;pager:link name="cancel" uri="index.xhtml"/&gt;
 *    &lt;pager:link name="submit" uri="search.xhtml"/&gt;
 *  &lt;/pager:list&gt;
 * </code>
 * </p>
 * 
 * @author mike
 * 
 */
public class Field extends CommonFormFields implements IDrawParams
{

	private static final Logger log = LoggerFactory.getLogger(Field.class);

	/**
	 * If we want this field to control child fields we add them to this list
	 */
	private List<Field>controllableFields = new ArrayList<Field>();
	
    private CodeAction code;

    private Populator populator;
    
	private String pre_format;
	private String post_format;

    
    
    
	public String execute(IExecContext execContext)
	{
		return "";
	}


	public void setField(Field controlField) {
		this.controllableFields.add(controlField);
	}

	public Field getField() {
		if (getControllableFields().size() > 0) {
			return getControllableFields().get(getControllableFields().size()-1);
		}
		return null;
	}

	public void setControllableFields(List<Field> controlFields) {
		this.controllableFields = controlFields;
	}

	public List<Field> getControllableFields() {
		return controllableFields;
	}

    public void setCode(CodeAction code) {
        this.code = code;
    }

    public CodeAction getCode() {
        return code;
    }
    
    public void setPopulator_sql(SqlPopulator populator) {
    	setPopulator((Populator)populator);
    }
    public SqlPopulator getPopulator_sql() {
    	return (SqlPopulator)populator;
    }

    public void setPopulator_code(CodePopulator populator) {
    	setPopulator((Populator)populator);
    }
    public CodePopulator getPopulator_code() {
    	return (CodePopulator)populator;
    }

	public void setPopulator(Populator populator) {
		this.populator = populator;
	}

	public Populator getPopulator() {
		return populator;
	}
	
	public String toString() {
		return getName();
	}

	/**
	 * @return the pre_format
	 */
	public String getPre_format() {
		return pre_format;
	}

	/**
	 * @param pre_format the pre_format to set
	 */
	public void setPre_format(String pre_format) {
		this.pre_format = pre_format;
	}

	/**
	 * @return the post_format
	 */
	public String getPost_format() {
		return post_format;
	}

	/**
	 * @param post_format the post_format to set
	 */
	public void setPost_format(String post_format) {
		this.post_format = post_format;
	}
}
