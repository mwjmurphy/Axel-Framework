package org.xmlactions.action.actions.code.execute;

@SuppressWarnings("serial")
public class ExecuteCodeException extends RuntimeException {

	public ExecuteCodeException(String errorMessage) {
		super(errorMessage);
	}

	public ExecuteCodeException(String errorMessage, Exception ex) {
		super(errorMessage, ex);
	}

}
