package org.xmlactions.action;

@SuppressWarnings("serial")
public class NestedActionException extends Exception {

	/**
	 * Constructs a new exception with null as its detail message. 
	 */
	public NestedActionException()
	{
		super();
	}
	

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message
	 */
	public NestedActionException(String message)
	{
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 *  
	 * @param message
	 * @param cause
	 */
	public NestedActionException(String message, Throwable cause)
    {
    	super(message, cause);
    }
    
    /**
     * Constructs a new exception with the specified cause and a detail message 
     * of (cause==null ? null : cause.toString()) (which typically contains the 
     * class and detail message of cause). 
     * 
     * @param cause
     */
	public NestedActionException(Throwable cause)
	{
		super(cause);
	}
}
