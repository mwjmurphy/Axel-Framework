package org.xmlactions.pager.actions.form.populator;

import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.CodeAction;


/**
 * Populates search, add, edit and list displays.
 * @author mike.murphy
 *
 */
public class CodePopulator extends BaseAction implements Populator {

	CodeAction code;

	public CodeAction getCode() {
		return code;
	}

	public void setCode(CodeAction code) {
		this.code = code;
	}

	@Override
	public String execute(IExecContext execContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
