package org.xmlactions.pager.actions.navigator;


import java.io.File;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.pager.actions.TransformAction;
import org.xmlactions.pager.actions.form.CommonFormFields;

public class NavigatorAction extends CommonFormFields {

    private static Logger log = LoggerFactory.getLogger(TransformAction.class);

    /** The xml mapping file that conforms to navigator.xsd */
    private String navigator_xml_file_name;

    /** An reference xml that conforms to navigator.xsd */
    private String xml_ref;


    /** Where we store the result of the navigator processing. If not
     * set we display instead */
    private String key;

    String path;


    public String execute(IExecContext execContext) throws Exception {

        validate(execContext);
        
		setTheme(execContext.getThemes().getTheme(getTheme_name(execContext)));

		Navigator navigator = new Navigator();
		String navXml= null;
        if (StringUtils.isNotEmpty(getXml_ref())) {
        	navXml = navigator.buildHtml(execContext, getTheme(execContext), execContext.getString(getXml_ref()));
        } else {
            File file = new File(path, execContext.replace(getNavigator_xml_file_name()));
            if (!file.exists() || file.isDirectory()) {
                throw new IllegalArgumentException("Missing or invalid file name [" + file.getAbsolutePath()
                        + "] for navigator_xml_file_name attribute [" + getNavigator_xml_file_name() + "]");
            }
            String fileXml = ResourceUtils.loadXMLFileWithISO(file.getAbsolutePath());
        	navXml = navigator.buildHtml(execContext, getTheme(execContext), fileXml);
        }
        if (StringUtils.isNotEmpty(getKey())) {
        	execContext.put(key, navXml);
            return "";
        } else {
        	return navXml;
        }
    }

    public void validate(IExecContext execContext) {
        if (StringUtils.isEmpty(getNavigator_xml_file_name()) && StringUtils.isEmpty(getXml_ref())) {
            throw new IllegalArgumentException("Missing navigator_xml_file_name and xml_ref attribute in tranform.  At least one of these attributes must be set.");
        }
        if (StringUtils.isNotEmpty(getNavigator_xml_file_name()) && StringUtils.isNotEmpty(getXml_ref())) {
            throw new IllegalArgumentException("Both the navigator_xml_file_name and xml_ref attribute are set. Only one of these attributes must be set, not both");
        }
        if (path == null) {
            path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
        }
        if (StringUtils.isEmpty(getId())){
            throw new IllegalArgumentException("Missing id attribute in navigator");
        }
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

	public void setNavigator_xml_file_name(String navigator_xml_file_name) {
		this.navigator_xml_file_name = navigator_xml_file_name;
	}

	public String getNavigator_xml_file_name() {
		return navigator_xml_file_name;
	}

	public void setXml_ref(String xml_ref) {
		this.xml_ref = xml_ref;
	}

	public String getXml_ref() {
		return xml_ref;
	}

}