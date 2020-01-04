package org.xmlactions.action.actions.code.parser;

import java.util.ArrayList;
import java.util.List;

import org.xmlactions.action.actions.code.execute.ExecuteCode;
import org.xmlactions.action.config.IExecContext;

public class ParserCode {
	

	private final IExecContext execContext;
	private final String code;
	private int position;
	private String assignmentName;
	private String actionName;
	private List<String> params;
	
	
	/**
	 * Basic rules
	 * <p>
	 * Code starts with a method call or a variable name
	 * 	<li>a method call is followed by a (
	 * 	<li>a variable name is followed by a = operator
	 * </p>
	 * 
	 * <p>
	 * Parameters are contained within brackets (...) and may have assignment operators such as string, int, dec
	 * </p>
	 * 
	 * <p>
	 * Each code ends with a ;
	 * </p>
	 * 
	 * <p>
	 * Examples:
	 *  <li>x = get('av12');
	 *  <li>x = get(string:'av12');
	 * </p>
	 *  
	 * @param code
	 * @throws Exception 
	 */
	public ParserCode(final IExecContext execContext, final String code) {
		this.execContext = execContext;
		this.code = code;
		this.position = 0;
		this.assignmentName = null;
		this.actionName = null;
		while (nextCode()) {
			executeCode();
			this.assignmentName = null;
			this.actionName = null;
			this.params.clear();
		}
	}
	
	private boolean executeCode() {
		Object result = execute(this.actionName, this.params);
		System.out.println("result:" + result);
		if (assignmentName != null) {
			// add key value to execContexts
			execContext.put(assignmentName, result);
		}
		return true;
	}
	
	private Object execute(String action, List<String> params) {
		String actionName = execContext.getAction("actions", action);
		if (actionName == null) {
			actionName = action;
		}
		ExecuteCode ec = new ExecuteCode(execContext);
		Object result = ec.executeWithParams(actionName, params);
		return result;
	}
	
	private boolean nextCode() {
		String word = findNextWord();
		if (word != null) {

			// find ( open bracket or = operator
			if (moveToNextNonWhiteSpace() == true) {
				char c = code.charAt(position++);
				if (c == '=') {
					// assignment operator
					this.assignmentName = word;
					nextCode();
				} else if (c == '(') {
					// open bracket
					this.actionName = word;
					getParams();
				} else {
					throw new CodeParserException("Invalid Syntax, was expecting '(' or '=' for [" + word + "] at position[" + position +"] code:[" + code + "]");
				}
			}
			return true;
		}
		return false;
	}
	
	private void getParams() {
		this.params = new ArrayList<String>();
		int start = position;
		if (findChar(')')) {
			int end = position;
			if (start < end) {
				String allParams = code.substring(start,end);
				String [] parts = allParams.split(",");
				for (String part : parts) {
					if (part.indexOf(':') > 0) {
						// we got an assignment operator
					} else {
						params.add(part);
					}
				}
			}
		} else {
			throw new CodeParserException("Invalid Syntax, missing closing ')' at position[" + position +"] code:[" + code + "]" );
		}
	}

	private boolean findChar(char x) {
		for (; position < code.length() ; position++) {
			char c = code.charAt(position);
			if (x == c) {
				return true;
			}
		}
		return false;
	}
	
	private String findNextWord() {
		if (moveToNextWord()) {
			int start = position;
			if (moveToNextNonWord()) {
				int end = position;
				String word = code.substring(start, end);
				return word;
			}
		}
		return null;
	}
	
	private boolean moveToNextNonWord() {
		for (; position < code.length() ; position++) {
			char c = code.charAt(position);
			boolean isWordChar = isWordChar(c);
			if (isWordChar == false) {
				return true;
			}
		}
		return false;
	}
	
	private boolean moveToNextWord() {
		for (; position < code.length() ; position++) {
			char c = code.charAt(position);
			boolean isWordChar = isWordChar(c);
			if (isWordChar == true) {
				return true; 
			}
		}
		return false;
	}
	
	private boolean moveToNextNonWhiteSpace() {
		for (; position < code.length() ; position++) {
			char c = code.charAt(position);
			boolean isWhiteSpace = isWhiteSpace(c);
			if (isWhiteSpace == false) {
				return true;
			}
		}
		return false;
	}
	
	private boolean moveToNextWhiteSpace() {
		for (; position < code.length() ; position++) {
			char c = code.charAt(position);
			boolean isWhiteSpace = isWhiteSpace(c);
			if (isWhiteSpace == true) {
				return true; 
			}
		}
		return false;
	}
	
	private boolean isWhiteSpace(char c) {
		if (c == ' ' ||
			c == '\n' ||
			c == '\r' ||
			c == '\t') {
			return true;
		}
		return false;		
	}

	private boolean isWordChar(char c) {
		if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c >= '9') || c == '_' || c == '.') {
			return true;
		}
		return false;		
	}

}
