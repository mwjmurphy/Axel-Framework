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


package org.xmlactions.pager.actions.form;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * Manages all properties required for accessing storage
 * 
 * @author mike
 * 
 */
public abstract class StorageBaseFormAction extends BaseFormAction
{

	/** Reference to the StorageConfig */
	private String storage_config_ref;

	/** The data storage table */
	private String table_name;

	/**
	 * Gets the first found storage_ref from this bean or it's parents.
	 * 
	 * @return the value or null if not found/set.
	 */
	public String getStorage_config_ref()
	{

		if (StringUtils.isEmpty(storage_config_ref)) {
			return this.getFirstValueFound("storage_config_ref");
		}
		return storage_config_ref;
	}

	public void setStorage_config_ref(String storage_config_ref)
	{

		this.storage_config_ref = storage_config_ref;
	}

	/**
	 * Gets the first found table_name from this bean or it's parents.
	 * 
	 * @return the value or null if not found/set.
	 */
	public String getTable_name()
	{

		if (StringUtils.isEmpty(table_name)) {

			return this.getFirstValueFound("table_name");
		}
		return table_name;
	}

	public void setTable_name(String table_name)
	{

		this.table_name = table_name;
	}




	/**
	 * Validates that the data_source_ref, storage_ref and table_name are set.
	 * 
	 * @param errMsg
	 */
	public void validateStorage(String errMsg)
	{
		Validate.notEmpty(getStorage_config_ref(), "storage_config_ref has not been set - " + errMsg);
		Validate.notEmpty(getTable_name(), "table_name has not been set - " + errMsg);
	}
}