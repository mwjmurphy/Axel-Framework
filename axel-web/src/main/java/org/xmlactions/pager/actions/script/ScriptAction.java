
package org.xmlactions.pager.actions.script;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.scripting.Scripting;
import org.xmlactions.common.text.XmlCData;

/**
 * Takes 2 parameters
 * 1) content of the script action element which is the script to execute
 * 1) attribute key to store result if there is any back into execContext - Optional
 * @author mike.murphy
 *
 */
public class ScriptAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(ScriptAction.class);

	private String key;					// if we want to put the result back into the execContext

	public String execute(IExecContext execContext) throws Exception
	{
		Object result = Scripting.getInstance().evaluate(execContext.replace(XmlCData.removeCData(getContent())));
		if (StringUtils.isBlank(getKey())) {
			return result.toString();
		} else {
			execContext.put(getKey(), result);
			return "";
		}
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
}
