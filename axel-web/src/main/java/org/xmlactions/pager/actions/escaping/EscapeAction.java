package org.xmlactions.pager.actions.escaping;

import java.util.regex.Matcher;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.mapping.BeanToXmlAction;

public class EscapeAction extends CommonFormFields {

    private static Logger log = LoggerFactory.getLogger(BeanToXmlAction.class);
    
    private static final String actionName = "escape";

	/** Reference to the data we want to un-escape */
    private String ref_key;

    /** Which type of un-escaping to we want to do */
    private String format = "pre";

    /** Where we store the result of the unescaping - may be null. */
    private String key;


    public String execute(IExecContext execContext) throws Exception {

        validate(execContext);

        String data = process(execContext);
        if (StringUtils.isNotEmpty(getKey())) {
        	execContext.put(getKey(), data);
        	return "";
        } else {
        	return data;
        }
    }

    public void validate(IExecContext execContext) {
    	// if there is concen
        if (StringUtils.isEmpty(getRef_key()) && StringUtils.isEmpty(getContent())) {
            throw new IllegalArgumentException("Missing ref_key attribute in " + actionName + ". Either set a reference key in the execContext that contains the data or add the content to the element.");
        }
        if (StringUtils.isEmpty(getFormat())) {
            throw new IllegalArgumentException("Missing format attribute in " + actionName + ". This is required and must be set to one of 'html', 'xml', 'java', 'javascript', 'csv'");
        }
    }


    private String process(IExecContext execContext) throws Exception {
    	String escapedData = null;
    	String data = null;
    	if (StringUtils.isEmpty(getRef_key())) {
    		// data = new Action().processPage(execContext, getContent());
    		data = getContent();
    		this.clearActions();	// dont process any child actions
    	} else { 
    		data = execContext.getString(getRef_key());
    	}
    	if (StringUtils.isEmpty(data)) {
            throw new IllegalArgumentException("The ref_key [" + getRef_key() + "] does not contain any value in the execContext in " + actionName + " or set the content in the action");
    	}
    	if (getFormat().equals("pre")) {	// presentation
    		escapedData = presentationEscape(data);
    	} else if (getFormat().equals("html")) {
        	escapedData = StringEscapeUtils.escapeHtml(data);
    	} else if (getFormat().equals("xml")) {
    		escapedData = StringEscapeUtils.escapeXml(data);
    	} else if (getFormat().equals("java")) {
    		escapedData = StringEscapeUtils.escapeJava(data);
    	} else if (getFormat().equals("javascript")) {
    		escapedData = StringEscapeUtils.escapeJavaScript(data);
    	} else if (getFormat().equals("csv")) {
    		escapedData = StringEscapeUtils.escapeCsv(data);
    	}
        return escapedData;
    }

    public static String presentationEscape(String data) {
    	data = data.replace("<", "&lt;");
    	data = data.replace(">", "&gt;");
    	data = data.replace("$", "&dollar;");
    	return data;
    }

    public static String jsonEscape(String data) {
    	data = data.replace("\"", "\"");
    	return data;
    }


    public String getRef_key() {
		return ref_key;
	}

	public void setRef_key(String ref_key) {
		this.ref_key = ref_key;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


}