package org.xmlactions.pager.actions.form;


import java.util.ArrayList;
import java.util.List;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.CodeAction;


public class PreProcessor extends BaseFormAction {

	private List<CodeAction> codeActions = new ArrayList<CodeAction>();

	public String execute(IExecContext arg0) throws Exception
	{

		// TODO Auto-generated method stub
		return null;
	}

	public void setCode(CodeAction codeAction) {
		codeActions.add(codeAction);
	}
	public CodeAction getCode(CodeAction codeAction) {
		return codeActions.get(codeActions.size()-1);
	}
	public List<CodeAction> getCodeActions() {
		return codeActions;
	}
}
