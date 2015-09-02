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

package org.xmlactions.mapping;


import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class Populator extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(Populator.class);
	
	private String id;
	private String clas;

    private List<KeyValue> keyvalues;

	public String execute(IExecContext execContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setClas(String clas) {
		this.clas = clas;
	}

	public String getClas() {
		return clas;
	}

    public void setKeyvalue(KeyValue keyValue) {
        if (keyvalues == null) {
            keyvalues = new ArrayList<KeyValue>();
        }
        keyvalues.add(keyValue);
    }

    public void setKeyvalues(List<KeyValue> keyValues) {
        this.keyvalues = keyValues;
    }

    public List<KeyValue> getKeyvalues() {
        return keyvalues;
    }

}
