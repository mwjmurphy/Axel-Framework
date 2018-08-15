
package org.xmlactions.pager.actions.ajax.handler;


import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.CodeAction;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.web.PagerWebConst;

/**
 * Process an Ajax Code Call
 * 
 * @param call
 *            - This is the code that will get called from the submit
 * 
 */
public class CodeCallHandler extends BaseAction
{

	private final static Logger log = LoggerFactory.getLogger(CodeCallHandler.class);

	public String execute(IExecContext execContext) throws Exception
	{
		String result = "OK:";
        String call = execContext.getString(PagerWebConst.REQUEST + ":" + ClientParamNames.CODE_CALL);
        Validate.notEmpty(call, "[" + ClientParamNames.CODE_CALL + "] not found in ["
                + PagerWebConst.REQUEST + "] named map from the execContext");

        CodeAction codeAction = new CodeAction();
        codeAction.setCall(call);
        try {
            result = codeAction.execute(execContext);
        } catch (Exception ex) {
        	result = "EX:" + ex.getMessage();
        }
        
        return result;
	}

}
