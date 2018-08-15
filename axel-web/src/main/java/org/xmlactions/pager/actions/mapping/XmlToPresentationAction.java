package org.xmlactions.pager.actions.mapping;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

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
import org.xmlactions.common.xml.XMLAttribute;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.PresentationFormAction;

public class XmlToPresentationAction extends CommonFormFields {

    private static Logger log = LoggerFactory.getLogger(XmlToPresentationAction.class);
    
    private static final String actionName = "map_xml_to_presentation";
    
    private enum http_request_keys {
    	xml_data,		// this is the xml or a key to get the xml from the ExecContext (if used)
    	xml_filename,	// this is a file name to the xml (if used)
    	presentation_form,	// each loop gets a new presentation_form with the data injected.
    	form,	// a form element
    	xml_path,		// a path into the xml where we start pulling the information from
    	row_map_name
    }

    /** The xml data or a key to get the data from the execContext */
    private String xml_data;

    /** The name of a file that contains the data in xml format */
    private String xml_filename;

    /** This is a path to the data we want to loop through */
    private String xml_path;

    /** The presentation form that we map the xml into. This is usually an html form with replacement markers*/
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
        if (StringUtils.isEmpty(getXml_data()) && StringUtils.isEmpty(getXml_filename())) {
            throw new IllegalArgumentException("You must set either the " + http_request_keys.xml_data + " or the " + http_request_keys.xml_filename + " attribute in " + actionName);
        }
//        if (StringUtils.isEmpty(getXml_path())) {
//            throw new IllegalArgumentException("Missing " + http_request_keys.xml_path + " attribute in " + actionName);
//        }
        if (StringUtils.isEmpty(getPresentation_form()) && getForm() == null) {
            throw new IllegalArgumentException("You must set either the attribute " + http_request_keys.presentation_form + " or the element " + http_request_keys.form + " in " + actionName);
        }
        if (path == null) {
            path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
        }
    }


    private String processMapping(IExecContext execContext) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NestedActionException, BadXMLException {

    	String presentationForm = null;
    	String xml = null;
    	try {
	        if (StringUtils.isNotEmpty(getXml_filename())) {
	        	String fileName = ResourceCommon.buildFileName(path, execContext.replace(getXml_filename()));
	        	xml = ResourceUtils.loadFile(fileName);
	        } else {
	        	xml = execContext.replace(getXml_data());
	        }
    	} catch (IOException ex) {
			throw new IllegalArgumentException("Unable to load " + getXml_filename(), ex);
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
    	XMLObject root = new XMLObject().mapXMLCharToXMLObject(xml);
    	int rowCount = 0;
    	if (StringUtils.isBlank(getXml_path())) {
			String populatedForm = getData(execContext, root, rowCount, presentationForm);
			if (StringUtils.isNotBlank(populatedForm)) {
				sb.append(populatedForm);
			}
    	} else {
			for (rowCount = 0 ; ; rowCount++) {
				XMLObject child = root.findXMLObjectByPath(getXml_path(), rowCount);
				if (child != null) {
					String populatedForm = getData(execContext, child, rowCount, presentationForm);
					if (StringUtils.isNotBlank(populatedForm)) {
						sb.append(populatedForm);
					}
	
				} else {
					// completed iteration through all path elements
					break;
				}
			}
    	}
		execContext.put(ActionConst.ROW_TOTAL_COUNT, rowCount);

		return sb.toString();
    }
    
    private String getData(IExecContext execContext, XMLObject child, int rowCount, String presentationForm) throws IOException, NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("row_index", rowCount+1);
		if (StringUtils.isNotBlank(child.getContent())) {
			map.put(child.getElementName(), child.getContent());
		}
		for (XMLAttribute att : child.getAttributes()) {
			map.put(att.getKey(), att.getValue());
		}
		execContext.addNamedMap(getRow_map_name(), map);
		String form = copyForm(presentationForm);
        form = StrSubstitutor.replace(form, map);
		String populatedForm = new Action().processPage(execContext, form);
		populatedForm = execContext.replace(form);
    	
		populatedForm = new Html().removeOuterJsonOrXmlOrHtml(populatedForm);
		
		return populatedForm;
		
    }

	private String copyForm(String form) {
		StringBuilder sb = new StringBuilder(form);
		return sb.toString();
	}


    /**
	 * @return the xml_data which is the name of a file that contains the data in xml format
	 */
	public String getXml_data() {
		return xml_data;
	}

	/**
	 * @param xml_data the xml_data to set
	 */
	public void setXml_data(String xml_data) {
		this.xml_data = xml_data;
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

	public String getXml_path() {
		return xml_path;
	}

	public void setXml_path(String xml_path) {
		this.xml_path = xml_path;
	}

	/**
	 * @return the xml_filename
	 */
	public String getXml_filename() {
		return xml_filename;
	}

	/**
	 * @param xml_filename the xml_filename to set
	 */
	public void setXml_filename(String xml_filename) {
		this.xml_filename = xml_filename;
	}

	/**
	 * @return the form
	 */
	public PresentationFormAction getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
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