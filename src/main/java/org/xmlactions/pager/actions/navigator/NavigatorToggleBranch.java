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

package org.xmlactions.pager.actions.navigator;


import java.util.Map;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.web.PagerWebConst;

public class NavigatorToggleBranch extends BaseAction {

    private static final String NAV_BRANCH_ID = "nav.branch.id";
    private static final Logger logger = LoggerFactory.getLogger(NavigatorToggleBranch.class);

    public String execute(IExecContext execContext) throws Exception {

        Map<String, Object> request = execContext.getNamedMap(PagerWebConst.REQUEST);
        String id = (String) request.get(NAV_BRANCH_ID);
        id = execContext.getString("request:" + NAV_BRANCH_ID);
        return "OK:";
    }

    public void test() {

    }
}
