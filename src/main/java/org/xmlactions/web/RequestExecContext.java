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
