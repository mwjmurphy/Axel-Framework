package org.xmlactions.pager.actions.mapping;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceCommon;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.text.Html;
import org.xmlactions.common.text.XmlCData;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.mapping.json.GsonUtils;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.PresentationFormAction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class JSONToPresentationAction extends CommonFormFields {

    private static Logger log = LoggerFactory.getLogger(JSONToPresentationAction.class);
    
    private static final String actionName = "map_json_to_presentation";
    
    private static final String default_json_path = "/";
    
    private enum http_request_keys {
    	json_data,	// this is the json data or a key to get the json from the ExecContext (if used)
    	json_filename,	// this is a file name to the json data (if used)
    	presentation_form,
    	form,	// a form element
    	json_path,
    	row_map_name
    }

    /** this is the json data or a key to get the json from the ExecContext (if used) */
    private String json_data;

    /** this is a file name to the json data (if used) */
    private String json_filename;

    /** This is a path to the data we want to loop through */
    private String json_path;

    /** The presentation form that we map the json into. This is usually an html form with replacement markers*/
    private String presentation_form;
    
	/** Can include a form element for build the presentation form inside this element. this takes precedence of the presentation_form attribute */
    private PresentationFormAction form;

    private String path;

    private String row_map_name="row";


	public String execute(IExecContext execContext) throws Exception {

        validate(execContext);

        String output = processMapping(execContext);
        return output;
    }

    public void validate(IExecContext execContext) {
        if (StringUtils.isEmpty(getJson_data()) && StringUtils.isEmpty(getJson_filename())) {
            throw new IllegalArgumentException("You must set either the " + http_request_keys.json_data + " or the " + http_request_keys.json_filename + " attribute in " + actionName);
        }
//        if (StringUtils.isEmpty(getJson_path(execContext))) {
//            throw new IllegalArgumentException("Missing " + http_request_keys.json_path + " attribute in " + actionName);
//        }
        if (StringUtils.isEmpty(getPresentation_form()) && getForm() == null) {
            throw new IllegalArgumentException("You must set either the attribute " + http_request_keys.presentation_form + " or the element " + http_request_keys.form + " in " + actionName);
        }
        if (path == null) {
            path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
        }
    }


    private String processMapping(IExecContext execContext) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NestedActionException, BadXMLException {

    	Gson gson = new Gson();
    	JsonElement jsonElement = null;
    	String presentationForm = null;
    	String data = null;

    	try {
	        if (StringUtils.isNotEmpty(getJson_filename())) {
	        	String fileName = ResourceCommon.buildFileName(path, execContext.replace(getJson_filename()));
		        data = ResourceUtils.loadFile(fileName);
		        jsonElement = gson.fromJson(data, JsonElement.class);
	        } else {
	        	data = execContext.replace(getJson_data(execContext));
		        jsonElement = gson.fromJson(data, JsonElement.class);
	        }
    	} catch (Exception ex) {
			throw new IllegalArgumentException("Unable to get data  for " + getJson_filename() + " or " + getJson_data() + " for json[" + data + "]", ex);
		}

    	if (getForm() != null) {
    		presentationForm = XmlCData.removeCData(getForm().getContent());
    	} else {
    		try {
	    	    String name = execContext.replace(getPresentation_form());
		        String fileName = ResourceCommon.buildFileName(path, name);
		        presentationForm = ResourceUtils.loadFile(fileName);
		    } catch (IOException ex) {
				throw new IllegalArgumentException("Unable to load " + getPresentation_form(), ex);
			}
    	}
    	StringBuilder sb = new StringBuilder();
    	int rowCount = 0;
		for (rowCount = 0 ; ; rowCount++) {
			try {
				Object o = GsonUtils.toMap(jsonElement, getJson_path(execContext), rowCount);
				if (o == null) {
					break;
				} else {
					if (o instanceof Map) {
						Map<String, Object> map = (Map<String, Object>)o;
						execContext.addNamedMap(getRow_map_name(), map);
					} else {
						execContext.put(getRow_map_name(), o);
					}
					String form = copyForm(presentationForm);
					String populatedForm = "";
					if (o instanceof Map) {
						populatedForm = StrSubstitutor.replace(form, (Map<String, Object>)o);
					} else {
						populatedForm = form;
					}
					populatedForm = new Action().processPage(execContext, populatedForm);
					populatedForm = execContext.replace(populatedForm);
					populatedForm = new Html().removeOuterJsonOrXmlOrHtml(populatedForm);
					if (StringUtils.isNotBlank(populatedForm)) {
						sb.append(populatedForm);
					}
				}
			} catch (Exception ex) {
				log.info(ex.getMessage());
				break;
			}
		}
		execContext.put(ActionConst.ROW_TOTAL_COUNT, rowCount);
		return sb.toString();
    }

	private String copyForm(String form) {
		StringBuilder sb = new StringBuilder(form);
		return sb.toString();
	}


	/**
	 * @return the presentation_form
	 */
	public String getPresentation_form() {
		return presentation_form;
	}

	/**
	 * @param presentation_form the presentation_form to set
	 */
	public void setPresentation_form(String presentation_form) {
		this.presentation_form = presentation_form;
	}

	public String getJson_path() {
		if (json_path == null || json_path.length() == 0) {
			return default_json_path;
		}
		return json_path;
	}

	public String getJson_path(IExecContext execContext) {
		// log.info("json_path:" + execContext.replace(json_path));
		if (json_path == null || json_path.length() == 0) {
			return default_json_path;
		}
		return execContext.replace(json_path);
	}

	public void setJson_path(String json_path) {
		this.json_path = json_path;
	}

	/**
	 * @return the json_data
	 */
	public String getJson_data() {
		return json_data;
	}

	public String getJson_data(IExecContext execContext) {
		if (json_data == null || json_data.length() == 0) {
			return json_data;
		}
		return execContext.replace(json_data);
	}

	/**
	 * @param json_data the json_data to set
	 */
	public void setJson_data(String json_data) {
		this.json_data = json_data;
	}

	/**
	 * @return the json_filename
	 */
	public String getJson_filename() {
		return json_filename;
	}

	/**
	 * @param json_filename the json_filename to set
	 */
	public void setJson_filename(String json_filename) {
		this.json_filename = json_filename;
	}

	public PresentationFormAction getForm() {
		return form;
	}

	public void setForm(PresentationFormAction form) {
		this.form = form;
	}

	/**
	 * @return the row_map_name
	 */
	public String getRow_map_name() {
		return row_map_name;
	}

	/**
	 * @param row_map_name the row_map_name to set
	 */
	public void setRow_map_name(String row_map_name) {
		this.row_map_name = row_map_name;
	}

}