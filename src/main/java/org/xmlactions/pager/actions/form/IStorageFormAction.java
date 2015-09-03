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

public interface IStorageFormAction
{

	/**
	 * Gets the first found storage_ref from this bean or it's parents.
	 * 
	 * @return the value or null if not found/set. public String getStorage_ref();
	 * 
	 *         public void setStorage_ref(String storage_ref);
	 */

	/**
	 * Gets the first found table_name from this bean or it's parents.
	 * 
	 * @return the value or null if not found/set.
	 */
	public String getTable_name();

	public void setTable_name(String table_name);

	// public void setDatabase_name(String database_name);

	/**
	 * Gets the first found database_name from this bean or it's parents.
	 * 
	 * @return the value or null if not found/set.
	 */
	// public String getDatabase_name();

	// public void setData_source_ref(String data_source_ref);

	/**
	 * Gets the first found data_source_ref from this bean or it's parents.
	 * 
	 * @return the value or null if not found/set.
	 */
	// public String getData_source_ref();

	/**
	 * User provides a full sql that is used to query, insert or update directly to the database.
	 * <p>
	 * The sql will be used in place of the db functionality.
	 * </p>
	 */
	public String getSql();

	public void setSql(String sql);

	/**
	 * Validates that the data_source_ref, storage_ref and table_name are set.
	 * 
	 * @param errMsg
	 */
	public void validateStorage(String errMsg);
}
