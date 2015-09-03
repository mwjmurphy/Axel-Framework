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


package org.xmlactions.common.scripting;

import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;

/**
 * 
 * @author MichaelMurphy
 */
public class Scripting {

    private static Scripting scripting = null;
    private BSFManager bsfManager;

    public static Scripting getInstance() {

        if (scripting == null) {

            scripting = new Scripting();
        }
        return scripting;
    }
    
    private Scripting () {
        bsfManager = new BSFManager();
    }

    /**
     * deprecated replaced with Apache Commons BSF
     * @return a Java V6 ScriptEngine
    public static ScriptEngine getJSEngine() {

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("js");
        return (engine);
    }
     */

    public Object evaluate(String code) throws BSFException {
    	try {
    		return bsfManager.eval("javascript", "debug infos", 0, 0, code);
    	} catch (Exception ex) {
    		throw new IllegalArgumentException("Error eval [" + code + "]", ex);
    	}
    }

    public void execute(String code) throws BSFException {
    	try {
	        bsfManager.exec("javascript", "debug infos", 0, 0, code);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Error exec [" + code + "]", ex);
		}
    }

}
