package org.xmlactions.pager.actions.mapping;


import java.io.File;
import java.io.IOException;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.mapping.bean_to_xml.PopulateXmlFromClass;
import org.xmlactions.pager.actions.TransformAction;
import org.xmlactions.pager.actions.form.CommonFormFields;

public class BeanToXmlAction extends CommonFormFields {

    private static Logger log = LoggerFactory.getLogger(BeanToXmlAction.class);
    
    private static final String actionName = "map_bean_to_xml";

    /** The xml mapping file that conforms to xml_to_bean.xsd */
    private String map_file_name;

    /**
     * key to execContext.get("bean") to be mapped
     */
    private String bean_key;

    /** Where we store the result of the query. */
    private String key;

    String path;


    public String execute(IExecContext execContext) throws Exception {

        validate(execContext);

        String xml = processMapping(execContext);
        execContext.put(getKey(), xml);
        return "";
    }

    public void validate(IExecContext execContext) {
        if (StringUtils.isEmpty(getMap_file_name())) {
            throw new IllegalArgumentException("Missing map_file_name attribute in " + actionName);
        }
        if (StringUtils.isEmpty(getKey())) {
            throw new IllegalArgumentException("Missing key attribute in " + actionName);
        }
        if (StringUtils.isEmpty(getBean_key())) {
            throw new IllegalArgumentException("Missing bean_key attribute in " + actionName);
        }
        if (path == null) {
            path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
        }
    }


    private String processMapping(IExecContext execContext) throws IOException {

        File file = new File(path, execContext.replace(getMap_file_name()));
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("Missing or invalid file name [" + file.getAbsolutePath()
                    + "] for map_file_name attribute [" + getMap_file_name() + "]");
        }
        String definitionXmlFile = file.getAbsolutePath();
        
        Object bean = execContext.get(getBean_key());
        if (bean == null) {
            throw new IllegalArgumentException("The [" + getBean_key() + "] does not contain any Object.");
        }
        String xml = PopulateXmlFromClass.mapBeanToXml(bean, definitionXmlFile);
        return xml;
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

    public void setBean_key(String bean_key) {
        this.bean_key = bean_key;
    }

    public String getBean_key() {
        return bean_key;
    }


}