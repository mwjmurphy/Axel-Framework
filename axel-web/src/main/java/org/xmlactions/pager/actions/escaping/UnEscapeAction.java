package org.xmlactions.pager.actions.escaping;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.mapping.BeanToXmlAction;

public class UnEscapeAction extends CommonFormFields {

    private static Logger log = LoggerFactory.getLogger(BeanToXmlAction.class);
    
    private static final String actionName = "unescape";

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
        if (StringUtils.isEmpty(getRef_key()) && StringUtils.isEmpty(getContent())) {
            throw new IllegalArgumentException("Missing ref_key attribute in " + actionName + ". Either set a reference key in the execContext that contains the data or add the content to the element.");
        }
        if (StringUtils.isEmpty(getFormat())) {
            throw new IllegalArgumentException("Missing format attribute in " + actionName + ". This is required and must be set to one of 'html', 'xml', 'java', 'javascript', 'csv'");
        }
    }


    private String process(IExecContext execContext) throws Exception {
    	String unescapedData = null;
    	String data = null;
    	if (StringUtils.isEmpty(getRef_key())) {
    		// data = new Action().processPage(execContext, getContent());
    		data = getContent();
    		this.clearActions();	// dont process any child actions
    	} else { 
    		data = execContext.getString(getRef_key());
    	}
    	if (getFormat().equals("pre")) {
    		unescapedData = presentationUnEscape(data);
    	} else if (getFormat().equals("html")) {
        	unescapedData = StringEscapeUtils.unescapeHtml(data);
    	} else if (getFormat().equals("xml")) {
    		unescapedData = StringEscapeUtils.unescapeXml(data);
    	} else if (getFormat().equals("java")) {
    		unescapedData = StringEscapeUtils.unescapeJava(data);
    	} else if (getFormat().equals("javascript")) {
    		unescapedData = StringEscapeUtils.unescapeJavaScript(data);
    	} else if (getFormat().equals("csv")) {
    		unescapedData = StringEscapeUtils.unescapeCsv(data);
    	}
        return unescapedData;
    }

    private String presentationUnEscape(String data) {
    	data = data.replace("&lt;", "<" );
    	data = data.replace("&gt;", ">");
    	data = data.replace("&dollar;", "$");
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