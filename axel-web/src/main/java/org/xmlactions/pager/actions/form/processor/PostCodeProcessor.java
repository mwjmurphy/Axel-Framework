package org.xmlactions.pager.actions.form.processor;


import java.util.HashMap;
import java.util.Map;

import org.xmlactions.pager.actions.form.ClientParamNames;


public class PostCodeProcessor {

    private Map<String, CodeProcessor> postProcessorsMap = new HashMap<String,CodeProcessor>();
    
    /**
     * 
     * @param key post_processor.1.1 post_processor.1 is the map name and 1 is the parameter name 
     * @param value the value for either the map name or the param name
     */
    public void addProcessorParam(String key, String value) {
    	String mapName;
    	String paramName;
    	int index2 = key.indexOf('.',  ClientParamNames.POST_PROCESSOR.length()+ 1);
    	if (index2 > -1) {
    		mapName = key.substring(0,index2);
        	paramName = key.substring(index2+1); 
    	} else {
    		mapName = key;
    		paramName = null;
    	}
    	CodeProcessor codeProcessor = postProcessorsMap.get(mapName);
    	if (codeProcessor == null) {
    		codeProcessor = new CodeProcessor();
    		postProcessorsMap.put(mapName, codeProcessor);
    	} 
    	if (paramName != null) {
    		codeProcessor.addParam(paramName, value);
    	} else {
    		codeProcessor.setCall(value);
    	}
    }
    
    /** Needed for unit tests */    
    public Map<String, CodeProcessor> getProcessorsMap() {
    	return postProcessorsMap;
    }


}
