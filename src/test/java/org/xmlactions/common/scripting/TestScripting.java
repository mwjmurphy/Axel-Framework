
package org.xmlactions.common.scripting;

import junit.framework.TestCase;

import org.apache.bsf.BSFException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.scripting.Scripting;
import org.xmlactions.common.system.JS;

public class TestScripting extends TestCase
{

	private static final Logger log = LoggerFactory.getLogger(TestScripting.class);

	/*
    public void testJS() throws ScriptException
	{

		ScriptEngine engine = Scripting.getJSEngine();
		Object result = engine.eval("escape('<b>');");
		log.debug("result:" + result);

		result = engine.eval("unescape('%3Cb%3E');");
		log.debug("result:" + result);
	}
	*/


    public void testScripting() throws BSFException {
        String code = "java.lang.System.out.println(\"" + JS.getCurrentMethodName() + " JavaScript/Rhino was here!\")";
        Scripting.getInstance().execute(code);
        Object output = Scripting.getInstance().evaluate(code);
        log.debug("output:" + output);

        Object result = Scripting.getInstance().evaluate("escape('<b>');");
        log.debug("result:" + result);

        result = Scripting.getInstance().evaluate("unescape('%3Cb%3E');");
        log.debug("result:" + result);

        result = Scripting.getInstance().evaluate(" var x = 100; 1 + 1 * x");
        log.debug("result:[" + result + "]");

    }
}
