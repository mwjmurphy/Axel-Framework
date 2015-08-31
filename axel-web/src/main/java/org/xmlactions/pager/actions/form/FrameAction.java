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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.ReplacementHandlerAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.Html;
import org.xmlactions.pager.actions.form.templates.HtmlInput;


/**
 * Builds a record search presentation.
 * <p>
 * The search fields come from the database schema.
 * </p>
 * <p>
 * parameters / attributes will build the html attributes needed for display.
 * </p>
 * 
 * @author mike
 * 
 * 
 */
public class FrameAction extends CommonFormFields implements FormDrawing, IStorageFormAction, ReplacementHandlerAction // extends StorageBaseFormAction
{

	private static final Logger LOG = LoggerFactory.getLogger(FrameAction.class);

	/** The replacement marker key in the frame page, this is where the frame inner content is stored. */
	private String key;
	
	/** Name of file we want to load */
	private String frame_name;

	/** Where the web pages are stored */
	private String path;

	private IExecContext execContext;
	
	private String frameContent;
	
	private String namespace;

	/** When adding pages we may want to remove the outer html element. */
	private boolean remove_html = true;	// true or false, yes or no.

	public String execute(IExecContext execContext) throws Exception
	{
		validateAndSetup(execContext);

		if (path == null) {
			path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
		}
		if (namespace == null) {
			namespace = (String) execContext.get(ActionConst.PAGE_NAMESPACE_BEAN_REF);
			if (namespace == null || namespace.trim().length() == 0) {
				namespace = new String(ActionConst.DEFAULT_PAGER_NAMESPACE[0]);
			}
		}
		Action action = new Action(path, frame_name, namespace);
		String frame = action.processPage(execContext);

		/*
		HashMap <String,String>map = new HashMap<String,String>();
		if (StringUtils.isNotEmpty(getId())) {
			map.put("id", getId());
		}
		if (StringUtils.isNotEmpty(getWidth())) {
			map.put("width", getWidth());
		}
		if (StringUtils.isNotEmpty(getHeight())) {
			map.put("height", getHeight());
		}
		if (StringUtils.isNotEmpty(getX())) {
			map.put("x", getX());
		}
		if (StringUtils.isNotEmpty(getY())) {
			map.put("y", getY());
		}
		if (StringUtils.isNotEmpty(getPosition())) {
			map.put("position", getPosition());
		}
		frameContent = StrSubstitutor.replace(frame, map);
		*/
		if (isRemove_html()) {
			frame = Html.removeOuterHtml(frame);
		}
		
		frameContent = frame;
		return getContent();
	}

	public void validateAndSetup(IExecContext execContext)
	{

		this.execContext = execContext;
		if (StringUtils.isEmpty(getFrame_name())) {
			throw new IllegalArgumentException("Missing frame_name attribute");
		}
	}


	public IExecContext getExecContext()
	{

		return this.execContext;
	}

	public List<HtmlInput> getHiddenFields()
	{

		java.util.List<HtmlInput> inputs = new ArrayList<HtmlInput>();

		return inputs;
	}


	public void validateStorage(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	public void setFrame_name(String frame_name) {
		this.frame_name = frame_name;
	}

	public String getFrame_name() {
		return frame_name;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public String getFrameContent() {
		return frameContent;
	}
	
	public void setFrameContent(String frameContent) {
		this.frameContent = frameContent;
	}
	
	/**
	 * @return the remove_html
	 */
	public boolean isRemove_html() {
		return remove_html;
	}

	/**
	 * @param remove_html the remove_html to set
	 */
	public void setRemove_html(boolean remove_html) {
		this.remove_html = remove_html;
	}

	public Object getReplacementData(IExecContext execContext, Object innerContent) {
		HashMap <String,String>map = new HashMap<String,String>();
		map.put(getKey(), "" + innerContent);
		return StrSubstitutor.replace(getFrameContent(), map);
	}

}