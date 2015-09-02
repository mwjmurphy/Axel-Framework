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

package org.xmlactions.pager.actions.form.processor;


import java.util.HashMap;
import java.util.Map;

import org.xmlactions.pager.actions.form.ClientParamNames;


/**
 * Code Processor that gets executed before submit
 * @author mike.murphy
 *
 */
public class PreCodeProcessor {
	
	private Map<String, CodeProcessor> preProcessorsMap = new HashMap<String,CodeProcessor>();

    /**
     * 
     * @param key pre_processor.1.1 pre_processor.1 is the map name and 1 is the parameter name 
     * @param value the value for either the map name or the param name
     */
    public void addProcessorParam(String key, String value) {
    	String mapName;
    	String paramName;
    	int index2 = key.indexOf('.',  ClientParamNames.PRE_PROCESSOR.length()+ 1);
    	if (index2 > -1) {
    		mapName = key.substring(0,index2);
        	paramName = key.substring(index2+1); 
    	} else {
    		mapName = key;
    		paramName = null;
    	}
    	CodeProcessor codeProcessor = preProcessorsMap.get(mapName);
    	if (codeProcessor == null) {
    		codeProcessor = new CodeProcessor();
    		preProcessorsMap.put(mapName, codeProcessor);
    	}
    	if (paramName != null) {
    		codeProcessor.addParam(paramName, value);
    	} else {
    		codeProcessor.setCall(value);
    	}
    }


    /** Needed for unit tests */    
    public Map<String, CodeProcessor> getProcessorsMap() {
    	return preProcessorsMap;
    }

}
