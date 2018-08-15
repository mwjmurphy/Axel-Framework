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
