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

package org.xmlactions.action.actions;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

/**
 * Contains a list of the action classes.
 * <p>
 * This is a singleton bean configured in spring or application
 * 
 * @author MichaelMurphy
 * 
 */
public class ActionManager {

	private static final Map<String, String> actions = new HashMap<String, String>();

	/**
	 * Add a new map of actions to the actions.
	 * <p>
	 * This is considered a factory call from spring.
	 * 
	 * @param newActions
	 *            the new actions we want to add
	 * @return the ActionManager.
	 */
	public ActionManager addActionMap(Map<String, String> newActions) {
		Validate.notNull(newActions, "Null parameter passed to method addActionMap");
		actions.putAll(newActions);
		return this;
	}

	public Map<String, String> getActions() {

		return actions;
	}
}
