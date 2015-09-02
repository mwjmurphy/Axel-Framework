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
