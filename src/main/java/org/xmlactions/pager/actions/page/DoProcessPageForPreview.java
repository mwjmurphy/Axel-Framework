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


package org.xmlactions.pager.actions.page;


import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.web.PagerWebConst;


/**
 * Saves a presentation page to the server
 * <p>
 * The parameters are contained in the execContext named map 'request'. These
 * are retrieved from the http request.
 * </p>
 */
public class DoProcessPageForPreview extends BaseAction
{

	private final static Logger log = LoggerFactory.getLogger(DoProcessPageForPreview.class);

	public String execute(IExecContext execContext) throws Exception
	{

		Map<String, Object> requestMap = execContext.getNamedMap(PagerWebConst.REQUEST);
		Validate.notNull(requestMap, "Missing [" + PagerWebConst.REQUEST + "] named map from the execContext");
		Map<String, String> map = new HashMap<String, String>();
		String pageContent = null;
		for (String key : requestMap.keySet()) {
			log.debug("key [" + key + "] value [" + requestMap.get(key) + "]");
			if (key.equals(ClientParamNames.PAGE_CONTENT)) {
				pageContent = (String) requestMap.get(key);
			} else {
				map.put(key, (String) requestMap.get(key));
			}
		}
		Validate.notEmpty(pageContent, "[" + ClientParamNames.PAGE_CONTENT + "] not found in [" + PagerWebConst.REQUEST
				+ "] named map from the execContext");
		log.debug("save page - content:"
				+ (pageContent.length() > 40 ? pageContent.substring(0, 40) + "..." : pageContent));

		String pagePreview = URLDecoder.decode(pageContent, "UTF-8");
		log.debug("pagePreview:" + pagePreview);
		Action action = new Action("", "", (String) execContext.get(ActionConst.PAGE_NAMESPACE_BEAN_REF));
		String page = action.processPage(execContext, pagePreview);
		return page;
	}
}
