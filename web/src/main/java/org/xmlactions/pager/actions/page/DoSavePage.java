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


import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceCommon;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.web.PagerWebConst;


/**
 * Saves a presentation page to the server
 * <p>
 * The parameters are contained in the execContext named map 'request'. These
 * are retrieved from the http request.
 * </p>
 */
public class DoSavePage extends BaseAction
{

	private final static Logger log = LoggerFactory.getLogger(DoSavePage.class);

	public String execute(IExecContext execContext) throws Exception
	{

		log.debug("DoSavePage");

		String result = "OK";
		try {
			result = doSavePage(execContext);
			if (StringUtils.isEmpty(result)) {
				result = "OK:";
			} else {
				result = "ER:" + result;
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "EX:" + ex.getMessage();
		}
		return result;
	}

	private String doSavePage(IExecContext execContext) throws Exception
	{

		Map<String, Object> requestMap = execContext.getNamedMap(PagerWebConst.REQUEST);
		Validate.notNull(requestMap, "Missing [" + PagerWebConst.REQUEST + "] named map from the execContext");
		Map<String, String> map = new HashMap<String, String>();
		String pageName = null;
		String pageContent = null;
		for (String key : requestMap.keySet()) {
			log.debug("key [" + key + "] value [" + requestMap.get(key) + "]");
			if (key.equals(ClientParamNames.PAGE_NAME)) {
				pageName = (String) requestMap.get(key);
			} else if (key.equals(ClientParamNames.PAGE_CONTENT)) {
				pageContent = (String) requestMap.get(key);
			} else {
				map.put(key, (String) requestMap.get(key));
			}
		}
		Validate.notEmpty(pageName, "[" + ClientParamNames.PAGE_NAME + "] not found in [" + PagerWebConst.REQUEST
				+ "] named map from the execContext");
		Validate.notEmpty(pageContent, "[" + ClientParamNames.PAGE_CONTENT + "] not found in [" + PagerWebConst.REQUEST
				+ "] named map from the execContext");
		//log.debug("save page - name:" + pageName + "\ncontent:" + pageContent.substring(0, 40) + "...");
		savePage(execContext, pageName, pageContent);
		return "";
	}

	private void savePage(IExecContext execContext, String pageName, String content)
	{

		String rootPath = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
		Validate.notEmpty(rootPath, "Unable to locate the Web Root Path from the ExecContext using ["
				+ ActionConst.WEB_REAL_PATH_BEAN_REF + "]");
		String name = ResourceCommon.buildFileName(rootPath, pageName);
		File file = new File(name);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			IOUtils.write(content, fos);
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		} finally {
			IOUtils.closeQuietly(fos);
		}

	}
}
