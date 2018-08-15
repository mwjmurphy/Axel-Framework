package org.xmlactions.pager.actions;
/**
 \page action_transform Action Transform
 
 A transform action will transform an XML document using an XSLT style sheet.
 
 Action:<strong>transform</strong>
 
 <table border="0">
	<tr>
  		<td colspan="2"><hr/></td>
	</tr>
	<tr>
	 	<td><strong>Elements</strong></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
	 		<strong>Description</strong>  
		<td>
	 </tr>
	<tr>
  		<td colspan="2" height="6px"/>
	</tr>
	<tr>
	 	<td>param<br/><small>- optional</small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			One or more param elements may be added to the action.  These will be passed to the transformer as a HashMap containing key - value pairs.  see \ref action_param
		<td>
	 </tr>
	<tr>
  		<td colspan="2"><hr/></td>
	</tr>
	<tr>
	 	<td><strong>Attributes</strong></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
	 		<strong>Description</strong>  
		<td>
	 </tr>
	<tr>
  		<td colspan="2" height="6px"/>
	</tr>
	<tr>
	 	<td>xslt_file_name<br/><small><i>- required</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
	 		The name of the transformation file that will perform the transformation.<br/>
	 		Multiple transformation files may be combined by appending one after the other using a ; seperator.  Usefull if you need to show the content of an include.  
		<td>
	 </tr>
	<tr>
  		<td colspan="2" height="6px"/>
	</tr>
	<tr>
		<td>xml_file_name<br/><small><i>- optional<br/>- use this or the xml_ref attribute</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			The name of the xml file to transform.<br/> 
			Multiple xml files may be combined by appending one after the other using a ; seperator.  <small>Usefull if you need to show the content of an include.</small><br/> 
			Instead of using an xml file a xml string may be referenced by using the xml_ref attribute.
		<td>
	</tr>
	<tr>
  		<td colspan="2" height="6px"/>
	</tr>
	<tr>
	 	<td>xml_ref<br/><small><i>- optional<br/>- use this or the xml_file_name attribute</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			The key used to get the xml as a String from the execContext.<br/> 
			Instead of using an xml_ref an xml file name may be used by setting the name of the file in the xml_file_name attribute.
		<td>
	</tr>
	<tr>
  		<td colspan="2" height="6px"/>
	</tr>
	<tr>
	 	<td>key<br><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			If this is set than the transformed output will be placed into the execContext with this key.
			Can be retrieved from the execContext using the same key.    
		<td>
	</tr>
	<tr>
  		<td colspan="2" height="6px"/>
	</tr>
	<tr>
	 	<td>transformer_factory<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
	 		If this is set than this factory will be used to perform the transformation.    
	        The xalan transformer factory = org.apache.xalan.processor.TransformerFactoryImpl     
		<td>
	</tr>
 </table>
 
 An example of how it looks on the web page before construction
 \code
 	<axel:transform xslt_file_name="xslt/mytransformer.xslt" xml_file_name="xml/myxml.xml"/>
 \endcode
 In this example the "xslt/mytransformer.xslt" and the "xml/myxml.xml" are located relative to the web root context.
 
 After the transformation is invoked the response from the call replaces the transform action element.
 
*/


import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.Transform;

public class TransformAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(TransformAction.class);
	
	private String path;

	/** Name of file we want to load */
	private String xslt_file_name;


	/** Where the web pages are stored */
	private String xml_file_name;

    /** A reference to a XML String contained in the execContext */
    private String xml_ref;

    /**
     * Where we store the result of the transformation. If not set we display
     * instead
     */
    private String key;

    /**
     * Used to set a which transformed to use. xalan =
     * org.apache.xalan.processor.TransformerFactoryImpl
     */
    private String transformer_factory;

    private List<Param> params = new ArrayList<Param>();


	public String execute(IExecContext execContext) throws Exception
	{
		validate(execContext);
		Reader xmlInputStream; 
		if (StringUtils.isNotEmpty(getXml_ref()) ) {
            String input = execContext.getString(getXml_ref());
            Validate.notEmpty(input, "No value found in execContext for [" + getXml_ref() + "]");
            xmlInputStream = new StringReader(input);
		} else {
			xmlInputStream = getInputStream(execContext, execContext.replace(getXml_file_name()));
		}
        Reader xsltInputStream = getInputStream(execContext, execContext.replace(getXslt_file_name()));
        Map<String, Object> map = getMapperParameters(execContext);
        String output = Transform.transform(xsltInputStream, xmlInputStream, getTransformer_factory(), map);
		Action action = new Action();
		char [] [] ns = {"pager".toCharArray()};
		action.setNameSpaces(ns);
        if (log.isDebugEnabled()) {
            log.debug("transform output:\n" + output);
        }
		output = action.processPage(execContext, output);
        if (StringUtils.isNotEmpty(getKey())) {
            execContext.put(getKey(), output);
            return "";
        }
		return output;
	}
	
	public void validate(IExecContext execContext) {
        if (StringUtils.isEmpty(getXml_file_name()) && StringUtils.isEmpty(getXml_ref())) {
            throw new IllegalArgumentException("Missing xml_file_name and xml_ref attribute in tranform.  At least one of these attributes must be set.");
        }
        if (StringUtils.isNotEmpty(getXml_file_name()) && StringUtils.isNotEmpty(getXml_ref())) {
            throw new IllegalArgumentException("Both the xml_file_name and xml_ref attribute are set. Only one of these attributes must be set, not both");
        }
		if (StringUtils.isEmpty(getXslt_file_name())) {
			throw new IllegalArgumentException("Missing xslt_file_name attribute in tranform");
		}
		if (path == null) {
			path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
		}
	}

	public String getXslt_file_name() {
		return xslt_file_name;
	}

	public void setXslt_file_name(String xsltFileName) {
		xslt_file_name = xsltFileName;
	}

	public String getXml_file_name() {
		return xml_file_name;
	}

	public void setXml_file_name(String xmlFileName) {
		xml_file_name = xmlFileName;
	}
	
    private Reader getInputStream(IExecContext execContext, String fileName) {
		try {
            String xml = combineXml(fileName);
            Reader reader = new StringReader(xml);
            return reader;
		}
		catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}
	
	
	/**
	 * Combine two or more xml files.
	 * @param names a semi-colon seperated list of names
	 */
	public String combineXml(String names) {
		
		try {
			String []nameArray = names.split(";");
			if (nameArray.length > 1) {
				// more than one so need to combine.
				return combineXml(nameArray);
			} else {
				// only one.
				return Action.loadPage(path, names);
			}
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
		
		
	}
	public String combineXml(String [] names) {
		try {
			String mainXml = Action.loadPage(path, names[0]);
			
			Document doc = DocumentHelper.parseText(mainXml);
			Element mainSource = doc.getRootElement();
			for (int i = 1; i < names.length ; i++) {
				String childXml = Action.loadPage(path, names[i]);
				
				Document child = DocumentHelper.parseText(childXml);
				Element childSource = child.getRootElement();
				log.debug("combine " + names[0] + " with " + names[i]);
				mainSource = combineXml(mainSource, childSource);
			}
			return mainSource.asXML();
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
		
	}

	@SuppressWarnings("unchecked")
	private Element combineXml(Element mainSource, Element newSource)
	{
		for (Object obj : newSource.elements())
		{
			Element element = (Element) ((Element)obj).clone();
			mainSource.add(element);
			log.debug("element:" + element.asXML());
		}
		return mainSource;
	}

    public void setXml_ref(String xml_ref) {
        this.xml_ref = xml_ref;
    }

    public String getXml_ref() {
        return xml_ref;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setTransformer_factory(String transformer_factory) {
        this.transformer_factory = transformer_factory;
    }

    public String getTransformer_factory() {
        return transformer_factory;
    }

    public List<Param> getParams() {

        return params;
    }

    public void setParams(List<Param> params) {

        this.params = params;
    }

    public void setParam(Param param) {

        params.add((Param) param);
    }

    /**
     * gets the last param in the list or null if none found.
     * 
     * @return
     */
    public Param getParam() {

        if (params.size() == 0) {
            return null;
        }
        return params.get(params.size() - 1);
    }

    public void setChild(Param param) {

        params.add(param);
    }

    public void _setChild(BaseAction param) {

        Validate.isTrue(param instanceof Param, "Parameter must be a " + Param.class.getName());
        params.add((Param) param);
    }

    private Map<String, Object> getMapperParameters(IExecContext execContext) {
        if (getParams() != null && getParams().size() > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (Param param : getParams()) {
                String key;
                Object object;
                if (StringUtils.isEmpty(param.getKey())) {
                    object = param.getResolvedValue(execContext);
                    key = param.getValue();
                } else {
                    key = param.getKey();
                    object = param.getValue();
                }
                map.put(key, object);
            }
            return map;
        }
        return null;
    }

}
