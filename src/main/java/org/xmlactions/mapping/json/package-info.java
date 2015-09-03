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

package org.xmlactions.mapping.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.xmlactions.common.io.ResourceUtils;
/**
 \page mapping_json_to_map Mapping JSON To HashMap
 
 \section mapping_json_intro Introduction
 
 This utility class is used to convert selected content of a JSON object to a HashMap.  The map
 can then be used to provide data for a StrSubstution on a page.
  
 Using the following JSON example
 \code
 {

	"att1":"This is att 1",	
	"att2":"This is att 2",	
	"root":{
		"root_att1":"This is root.att 1",	
		"root_att2":"This is root.att 2",	
		"vessel_gear_types_category":[
			{
				"image_url":"images/gears/surrounding_nets.png",
				"title_text":"Surrounding",
				"gear_name":"Surrounding",
				"tooltipid":"surrounding_nets_tooltip",
				"data_filter":".surrounding_nets"
			},
			{
				"image_url":"images/gears/seine_nets.jpg",
				"title_text":"Seine",
				"gear_name":"Seine",
				"tooltipid":"seine_nets_tooltip",
				"data_filter":".seine_nets"
			}
			]
	}
 }
 \endcode
 
 If we call
 \code
 	Map<String, Object> map = JSONUtils.toMap(jsonObject, "root/vessel_gear_types_category", 0);
 \endcode
 The returned map will contain
 <ul>
 	<li>row_index=1</li>
 	<li>att1=This is att 1</li>
 	<li>att2=This is att 2</li>
 	<li>data_filter=.surrounding_nets</li>
 	<li>gear_name=Surrounding</li>
 	<li>image_url=images/gears/surrounding_nets.png</li>
 	<li>title_text=Surrounding</li>
 	<li>tooltipid=surrounding_nets_tooltip</li>
 	<li>root/root_att1=This is root.att 1</li>
 	<li>root/root_att2=This is root.att 2</li>
 	<li>root/vessel_gear_types_category/data_filter=.surrounding_nets</li>
 	<li>root/vessel_gear_types_category/gear_name=Surrounding</li>
 	<li>root/vessel_gear_types_category/image_url=images/gears/surrounding_nets.png</li>
 	<li>root/vessel_gear_types_category/title_text=Surrounding</li>
 	<li>root/vessel_gear_types_category/tooltipid=surrounding_nets_tooltip</li>
 </ul>
 <b>What happend?</b>
 
 We called JSONUtils.toMap with the following parameters
 	-# A jsonObject that was constructed from the example json shown above.
 	-# A path set to "root/vessel_gear_types_category"
 	-# An index set to 0 (use the first iteration)
 
 The method then
 	-# Searched for the path with an index of 0
 	-# Found the matching path and extracted all values to a map
 	-# In the process it also included all values found in each sub path element to the map
 	
 <b>Why the index?</b>
 
 	The index is used to extract data for a specific iteration of the path. If we continuously call 
 	toMap incrementing the index as we go the method will return null when the index is &gt;= the iterator,
 	meaning that all the data for that path has been extracted.
 	
 
 \section mapping_json_exampel Example
 This is an example of the code in use
 \code
	@Test
	public void testJsonToMap2() throws JSONException, IOException {
		String jsonString = ResourceUtils.loadFile("/json/sample/root.json");
		JSONObject jsonObject = new JSONObject(jsonString);
		for (int i = 0 ; ; i++) {
			Map<String, Object> map = JSONUtils.toMap(jsonObject, "root/vessel_gear_types_category", i);
			if (map != null) {
				logger.debug("index:" + i);
				dumpMap(map);
			} else {
				// completed iteration through all path elements
				break;
			}
		}
		logger.debug(jsonObject.toString());
	}
	
	private void dumpMap(Map<String, Object> map) {
		Set set = map.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			Object obj = iterator.next();
			logger.debug("obj:" + obj);
		}
	}
 \endcode 
 */

