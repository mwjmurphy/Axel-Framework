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

package org.xmlactions.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletResponse;

public class PagerFilter implements javax.servlet.Filter {

    private static Logger logger = LoggerFactory.getLogger(PagerFilter.class);

    private static Map<String, String> mapRequestParams = new HashMap<String, String>();

    private FilterConfig filterConfig;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain chain) throws IOException,
            ServletException {
        logger.debug("PagerFilter.doFilter");

        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        PagerServlet pagerServlet = new PagerServlet();
        try {
            pagerServlet.init(filterConfig);
            if (req instanceof HttpServletRequest && rsp instanceof HttpServletResponse) {
                pagerServlet.setupExecContext((HttpServletRequest) req, (HttpServletResponse) rsp);
            }
            chain.doFilter(req, mockResponse);
            RequestExecContext.remove();
        } catch (FileUploadException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        } finally {
            String page = mockResponse.getContentAsString();
            pagerServlet.processPageFromFilter(req, rsp, page);
        }
    }

    @SuppressWarnings("unchecked")
    public void init(FilterConfig filterConfig) throws ServletException {

        logger.debug("PagerFilter.init");
        this.filterConfig = filterConfig;
        try {
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        Enumeration<String> enumeration = filterConfig.getInitParameterNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = filterConfig.getInitParameter(key);
            mapRequestParams.put(key, value);
        }
    }

}
