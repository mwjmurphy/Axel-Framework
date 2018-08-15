
package org.xmlactions.pager.actions.form;


import java.util.ArrayList;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.CodeAction;



public class PostProcesses extends BaseFormAction
{

	java.util.List<CodeAction> processors = new ArrayList<CodeAction>();

	/**
	 * Add a processor to the list
	 * 
	 * @param processor
	 */
	public void setProcessor(CodeAction processor)
	{

		this.processors.add(processor);
	}

	/**
	 * @return last processes in list or null if list contains no processes
	 */
	public CodeAction getProcessor()
	{

		if (processors.size() > 0) {
			return processors.get(processors.size() - 1);
		}
		return null;
	}

	/**
	 * @return list of processors
	 */
	public java.util.List<CodeAction> getProcessors()
	{

		return processors;
	}

    public void setCode(CodeAction processor) {
        setProcessor(processor);
    }

    /**
     * @return last processes in list or null if list contains no processes
     */
    public CodeAction getCode() {
        return getProcessor();
    }

	public String execute(IExecContext arg0) throws Exception
	{

		// TODO Auto-generated method stub
		return null;
	}
}
