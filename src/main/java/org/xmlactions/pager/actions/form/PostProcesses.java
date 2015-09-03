/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */


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
