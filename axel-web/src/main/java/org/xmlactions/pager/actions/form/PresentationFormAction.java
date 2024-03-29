
package org.xmlactions.pager.actions.form;


import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class PresentationFormAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(PresentationFormAction.class);

	public String execute(IExecContext execContext)
	{

		return StrSubstitutor.replace(getContent(), execContext);
	}

	public String toString()
	{

		return "presentation_form [" + getContent() + "]";
	}

}
