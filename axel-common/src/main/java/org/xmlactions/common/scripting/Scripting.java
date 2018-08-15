
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
