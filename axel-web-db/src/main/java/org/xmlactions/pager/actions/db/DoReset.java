
package org.xmlactions.pager.actions.db;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

/**
 * Performs a reset.
 */
public class DoReset extends BaseAction
{

	private final static Logger log = LoggerFactory.getLogger(DoReset.class);

	public String execute(IExecContext execContext) throws Exception
	{

		String result = "OK";
		try {
            execContext.reset();
			//throw new IllegalArgumentException("FIX execContext.reset - should remove all persistence links and data");
			//execContext.reset();
		} catch (Exception ex) {
			result = "EX:" + ex.getMessage();
		}
		return result;
	}

}
