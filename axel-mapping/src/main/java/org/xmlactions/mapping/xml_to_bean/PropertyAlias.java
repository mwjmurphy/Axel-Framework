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

package org.xmlactions.mapping.xml_to_bean;


import java.util.List;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class PropertyAlias extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(PropertyAlias.class);
	
	private String name;

	public String execute(IExecContext execContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	public PropertyAlias getPropertyAlias(List<PropertyAlias> list, String name) {
		for (PropertyAlias propertyAlias : list) {
			if (name.equals(propertyAlias.getName())) {
				return propertyAlias;
			}
		}
		return null;
	}
}
