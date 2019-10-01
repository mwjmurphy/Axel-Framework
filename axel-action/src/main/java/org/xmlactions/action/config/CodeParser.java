package org.xmlactions.action.config;

import java.util.ArrayList;
import java.util.List;

public class CodeParser {

	/**
	 * ${code:org.xmlactions.action.config.TestCodeParser.buildName('fred','Flinstone')}
	 * @param code
	 * @throws Exception 
	 */
	public Object parseCode(IExecContext execContext, String code) {
		CodeAction codeAction = new CodeAction();
		codeAction.setCall(getCodePart(code));
		List<Param> params = getParams(execContext, code);
		codeAction.setParams(params);
		Object result;
		try {
			result = codeAction.execute(execContext);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Unable to parse code:[" + code + "]",ex);
		}
		return result;
	}
	
	private void validate(String code) {
		if (code == null) {
			throw new IllegalArgumentException("MalFormed code call null input");
		}
		int indexFrom = code.indexOf('(');
		if (indexFrom < 1) {
			throw new IllegalArgumentException("MalFormed code call [" + code + "] missing '('");
		}
		int indexTo = code.indexOf(')');
		if (indexTo < 1) {
			throw new IllegalArgumentException("MalFormed code call [" + code + "] missing ')'");
		}
		if (indexFrom > indexTo) {
			throw new IllegalArgumentException("MalFormed code call [" + code + "] misplaced character ')' before '('");
		}

	}
	
	private String getCodePart(String code) {
		int index = code.indexOf('(');
		if (index > 0) {
			// extract the code path.class.method
			String cc = code.substring(0,index).trim();
			return cc;
		} else {
			throw new IllegalArgumentException("MalFormed code call [" + code + "] missing '('");
		}
	}

	private List<Param> getParams(IExecContext execContext, String code) {
		List<Param> list = new ArrayList<Param>();
		int indexFrom = code.indexOf('(');
		int indexTo = code.indexOf(')');
		String ps = code.substring(indexFrom+1, indexTo).trim();
		if (ps.length() > 0) {
			String [] params = ps.split(",");
			for (String param : params) {
				Param p = Param.buildParam(execContext, param.trim());
				list.add(p);
			}
		}
		return list;
	}

}
