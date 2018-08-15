
package org.xmlactions.pager.actions.form.processor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.CodeAction;
import org.xmlactions.pager.actions.Param;

/**
 * Converts a pre_processor or post_processor to a CodeAction and executes the call.
 * @author mike.murphy
 *
 */
public class CodeProcessor
{

	private static Logger log = LoggerFactory.getLogger(CodeProcessor.class);

	private String call; // the package and method to call
	private Map<String,String> params;

	public String callCode(IExecContext execContext) throws Exception
	{
        CodeAction codeAction = new CodeAction();
        codeAction.setCall(call);
        if (params != null) {
	        for (int index = 1; ; index++) {
	        	String value = (String)params.get("" + index);
	        	if (value != null) {
		        	Param param = new Param();
		        	param.setValue(value);
		        	codeAction.setParam(param);
	        	} else {
	        		break;
	        	}
	        }
        }
        return codeAction.execute(execContext);
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("code call [" + getCall() + "]");
		return sb.toString();
	}

	public void setCall(String call)
	{
		this.call = call;
	}

	public String getCall()
	{
		return call;
	}
	
	public void addParam(String key, String value) {
		if (params == null) {
			params = new HashMap<String,String>();
		}
		params.put(key,value);
	}

}
