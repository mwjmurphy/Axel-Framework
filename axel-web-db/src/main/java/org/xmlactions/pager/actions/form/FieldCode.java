
package org.xmlactions.pager.actions.form;


import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.Attributes;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.CodeAction;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.populator.CodePopulator;
import org.xmlactions.pager.actions.form.populator.Populator;
import org.xmlactions.pager.actions.form.populator.SqlPopulator;
import org.xmlactions.pager.drawing.IDrawParams;


/**
 * This class is used to present a field for view, add, edit using a code call to retrieve
 * or store the field data.<br/>
 * example:<br/>
 * <pre><br/>
 * 	&lt;field_list&gt;<br/>
 *     &lt;field_code&gt;<br/>
 *        &lt;code ...&gt;<br/>
 *        &lt;/code&gt;<br/>
 *     &lt;/field_code&gt;<br/>
 * </pre><br/>
 * 
 * 
 * @author mike
 * 
 */
public class FieldCode extends CommonFormFields implements IDrawParams
{
	private static final Logger log = LoggerFactory.getLogger(FieldCode.class);

    private CodeAction code;

	public String execute(IExecContext execContext)
	{
		return "";
	}

    public void setCode(CodeAction code) {
        this.code = code;
    }

    public CodeAction getCode() {
        return code;
    }
    
	public String toString() {
		return getName();
	}

}
