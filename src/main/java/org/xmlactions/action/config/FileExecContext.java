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


package org.xmlactions.action.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class FileExecContext extends ExecContext {

	private static final Logger log = LoggerFactory.getLogger(FileExecContext.class);

	String fileName;

	public FileExecContext(List<Object> actionMaps, List<Object> localMaps, List<Object> themes) {
		super(actionMaps, localMaps, themes);
	}

	public FileExecContext(List<Object> actionMaps, List<Object> localMaps, String fileName)
	{
		super(actionMaps, localMaps);
		this.fileName = fileName;
	}

	public void loadFromPersistence()
	{

		// Read properties file.
		Properties properties = new Properties();
		File file = new File(fileName);
		if (file.exists()) {
			try {
				properties.load(new FileInputStream(fileName));
				for (Object key : properties.keySet()) {
					Object value = properties.get(key);
					this.persist((String) key, value);
					log.debug("loadFromPersistence key:" + key + " value:" + value);
				}

			} catch (Exception ex) {
				throw new IllegalArgumentException(ex.getMessage(), ex);
			}
		} else {
			log.warn("No Persistence File Found For [" + fileName + "]");
		}
	}

	public void saveToPersistence()
	{

		log.debug("saving persistence to:" + new File(fileName).getAbsolutePath());
		Properties properties = new Properties();

		// Write properties file.
		try {
			Map<String, Object> persistenceMap = getPersistenceMap();
			for (Object key : persistenceMap.keySet()) {
				Object value = persistenceMap.get(key);
				properties.put(key, value);
				log.debug("saveToPersistence key:" + key + " value:" + value);
			}
			properties.store(new FileOutputStream(fileName), null);
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}

    public void reset() {
        getPersistenceMap().clear();
        log.debug("persistenceMap cleared");
    }

}
