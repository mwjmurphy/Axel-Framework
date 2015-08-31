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

/*
 * Copyright (C) 2003, Mike Murphy <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
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

import java.util.List;


/**
 * Mostly used for unit tests.  Allows for creating of an IExecContext without the need for a persistence layer.
 * @author mike.murphy
 *
 */
@SuppressWarnings("serial")
public class NoPersistenceExecContext extends ExecContext
{
	public NoPersistenceExecContext(List<Object> actionMaps, List<Object> localMaps, List<Object> themes) {
		super(actionMaps, localMaps, themes);
	}

	public NoPersistenceExecContext(List<Object> actionMaps, List<Object> localMaps) {
		super(actionMaps, localMaps);
	}

	public void loadFromPersistence()
	{

		// TODO Auto-generated method stub

	}

	public void reset()
	{

		// TODO Auto-generated method stub

	}

	public void saveToPersistence()
	{

		// TODO Auto-generated method stub

	}

}
