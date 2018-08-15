package org.xmlactions.common.tracer;

/**
 * Use this to capture the log information from classes and methods that support tracing.
 * <p>
 * Currently this is supported by the database inserts, deleted, updates and selects.
 * </p>
 * 
 * @author mike.murphy
 *
 */
public interface Tracer {

	/**
	 * saves the traceData to a source such as a log file or a database table.
	 * 
	 * @param className - the name of the class that is calling the trace
	 * @param workerId - this is the identify of the worker
	 * @param traceData - this is the data to be logged
	 * 
	 */
	public void saveTrace(String className, String workerId, String traceData);
	
}
