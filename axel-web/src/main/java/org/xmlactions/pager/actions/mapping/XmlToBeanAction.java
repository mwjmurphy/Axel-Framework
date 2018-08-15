package org.xmlactions.pager.actions.mapping;


import java.io.File;
import java.io.IOException;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.mapping.xml_to_bean.PopulateClassFromXml;
import org.xmlactions.pager.actions.TransformAction;
import org.xmlactions.pager.actions.form.CommonFormFields;

public class XmlToBeanAction extends CommonFormFields {

    private static Logger log = LoggerFactory.getLogger(XmlToBeanAction.class);

    private static final String actionName = "map_xml_to_bean";

    /** The xml mapping file that conforms to xml_to_bean.xsd */
    private String map_file_name;

    /**
     * key to execContext.get("xml") to be mapped or may use xml_file_name
     * instead
     */
    private String xml_key;

    /**
     * xml file name to be mapped or may use xml_key instead
     */
    private String xml_file_name;


    /** Where we store the result of the query. */
    private String key;

    String path;


    public String execute(IExecContext execContext) throws Exception {

        validate(execContext);

        Object xo = processMapping(execContext);
        execContext.put(getKey(), xo);
        return "";
    }

    public void validate(IExecContext execContext) {
        if (StringUtils.isEmpty(getMap_file_name())) {
            throw new IllegalArgumentException("Missing map_file_name attribute in " + actionName);
        }
        if (StringUtils.isEmpty(getKey())) {
            throw new IllegalArgumentException("Missing key attribute in " + actionName);
        }
        if (StringUtils.isEmpty(getXml_file_name()) && StringUtils.isEmpty(getXml_key())) {
            throw new IllegalArgumentException("Missing xml_file_name or xml_key, one of these must be set in "
                    + actionName);
        }
        if (path == null) {
            path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
        }
    }


    private Object processMapping(IExecContext execContext) throws IOException {

        File file = new File(path, execContext.replace(getMap_file_name()));
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("Missing or invalid file name [" + file.getAbsolutePath()
                    + "] for map_file_name attribute [" + getMap_file_name() + "]");
        }
        String definitionXmlFile = file.getAbsolutePath();
        
        String xml = null;
        if (StringUtils.isNotEmpty(getXml_file_name())) {
            file = new File(path, execContext.replace(getXml_file_name()));
            if (!file.exists() || file.isDirectory()) {
                throw new IllegalArgumentException("Missing or invalid file name [" + file.getAbsolutePath()
                        + "] for xml_file_name attribute [" + getXml_file_name() + "]");
            }
            xml = ResourceUtils.loadXMLFileWithISO(file.getAbsolutePath());
            if (StringUtils.isEmpty(xml)) {
                throw new IllegalArgumentException("The [" + file.getAbsolutePath() + "].[" + getXml_file_name()
                        + "] does not contain any xml.");
            }
        } else {
            xml = execContext.getString(getXml_key());
            if (StringUtils.isEmpty(xml)) {
                throw new IllegalArgumentException("The [" + getXml_key() + "] does not contain any xml.");
            }
        }
        
        PopulateClassFromXml mapper = new PopulateClassFromXml();
        Object object = mapper.mapXmlToBean(definitionXmlFile, xml);
        return object;
    }



    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setMap_file_name(String map_file_name) {
        this.map_file_name = map_file_name;
    }

    public String getMap_file_name() {
        return map_file_name;
    }


    public void setXml_key(String xml_key) {
        this.xml_key = xml_key;
    }

    public String getXml_key() {
        return xml_key;
    }

    public void setXml_file_name(String xml_file_name) {
        this.xml_file_name = xml_file_name;
    }

    public String getXml_file_name() {
        return xml_file_name;
    }

}