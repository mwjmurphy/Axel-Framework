package org.xmlactions.web;

import org.xmlactions.action.config.IExecContext;

/**
 * This class maintains the IExecContext, making it available to any client code
 * that wants to get access to the IExecContext.
 * <p>
 * Call <code>RequestExecContext.get()</code> to get your IExecContext
 * </p>
 * 
 * @author Mike Murphy
 */
public class RequestExecContext {

    private static final ThreadLocal<IExecContext> ec = new ThreadLocal<IExecContext>();

    public static void set(IExecContext execContext) {
        ec.set(execContext);
    }
    
    public static void remove() {
        ec.remove();
    }

    public static IExecContext get() {
        IExecContext execContext = ec.get();
        if (execContext == null) {
            throw new IllegalArgumentException("IExecContext has not been set in " + RequestExecContext.class.getName());
        }
        return ec.get();
    }

}
