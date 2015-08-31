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


import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.mapping.Populator;

public class Property extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(Property.class);
	
	private String name;
	private String populator;
	private String populator_ref;
	private List<PropertyAlias> propertyAliases;

	public String execute(IExecContext execContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPopulator(String populator) {
		this.populator = populator;
	}

	public String getPopulator() {
		return populator;
	}

    public Object getPopulator(XmlToBean xmlToBean) {
		if (populator == null) {
			if (populator_ref != null) {
				Populator populator = xmlToBean.getPopulator(populator_ref);
				if (populator == null) {
					throw new IllegalArgumentException("No populator_ref has been set for [" + populator_ref + "]");
				}
                return populator;
			} else {
				throw new IllegalArgumentException("No populator has been set for property [" + name + "]");
			}
		} else {
			return populator;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPopulator_ref(String populator_ref) {
		this.populator_ref = populator_ref;
	}

	public String getPopulator_ref() {
		return populator_ref;
	}

	public void setPropertyAliases(List<PropertyAlias> propertyAliases) {
		this.propertyAliases = propertyAliases;
	}

	public List<PropertyAlias> getPropertyAliases() {
		if (propertyAliases == null) {
			propertyAliases = new ArrayList<PropertyAlias>();
		}
		return propertyAliases;
	}
	
	public void setAlias(PropertyAlias propertyAlias) {
		getPropertyAliases().add(propertyAlias);
	}

	public static Property getProperty(List<Property> list, String propertyName) {
		for (Property property : list) {
			if (propertyName.equals(property.getName())) {
				return property;
			}
		}
		for (Property property : list) {
			for (PropertyAlias alias : property.getPropertyAliases()) {
				if (propertyName.equals(alias.getName())) {
					return property;
				}
			}
		}
		return null;
	}


}
