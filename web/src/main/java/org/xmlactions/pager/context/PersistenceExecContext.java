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

package org.xmlactions.pager.context;


import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;



import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.ExecContext;

@SuppressWarnings("serial")
public class PersistenceExecContext extends ExecContext
{

	private static final Logger log = LoggerFactory.getLogger(PersistenceExecContext.class);

	HttpSession session;

	public PersistenceExecContext(List<Object> actionMaps, List<Object> localMaps, List<Object> themes) {
		super(actionMaps, localMaps, themes);
	}

	public PersistenceExecContext(List<Object> actionMaps, List<Object> localMaps) {
		super(actionMaps, localMaps, null);
	}

	public void setSession(HttpSession session)
	{

		this.session = session;
	}

	/**
	 * Load all persistence values from httpSession to execContext
	 * 
	 * @param execContext
	 * @param session
	 */
	@SuppressWarnings("unchecked")
	public void loadFromPersistence()
	{
		Enumeration<String> enumeration = session.getAttributeNames();
		if (log.isDebugEnabled()) {
			log.debug("loadFromPersistence.session:");
			log.debug("isNew:" + session.isNew());
			log.debug("getId:" + session.getId());
		}
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			Object value = session.getAttribute(key);
			persist(key, value);
			log.debug("loadFromPersistence key:" + key + " value:" + value);
		}
	}

	/**
	 * Save all persistence values from execContext to httpSession
	 * 
	 */
	public void saveToPersistence()
	{

		Map<String, Object> persistenceMap = getPersistenceMap();
		for (String key : persistenceMap.keySet()) {
			Object value = persistenceMap.get(key);
			session.setAttribute(key, value);
			log.debug("saveToPersistence key:" + key + " value:" + value);
		}
	}

    /**
     * Clear all persistent variables from execContext and HttpSession
     */
	public void reset()
	{
        Map<String, Object> map = getPersistenceMap();
        for (String key : map.keySet()) {
            session.removeAttribute(key);
            if (log.isDebugEnabled()) {
                log.debug("Session attribute [" + key + "] cleared.");
            }
        }
        getPersistenceMap().clear();
        log.debug("persistenceMap cleared");
	}

}
