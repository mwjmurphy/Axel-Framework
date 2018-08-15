package org.xmlactions.mapping.xml_to_bean;


import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.xml.XMLAttribute;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.mapping.KeyValue;
import org.xmlactions.mapping.Populator;


public class PopulateClassFromXml
{
    private static final Logger log = LoggerFactory.getLogger(PopulateClassFromXml.class);
    private boolean strict = false;
    /**
     * Will ignore a conversion to a decimal, bigint if the conversion caused a
     * ConversionException and the value is empty.
     */
    private boolean ignoreErrorWithEmptyValues = true;

    private XmlToBean xmlToBean;

    private static String actionPropertiesFilename = "/config/xml_to_bean.properties";

    public Object mapXmlToBean(String definitionXmlFile, String xml) {
        try {
        	IExecContext execContext = new NoPersistenceExecContext(null, null);
        	execContext.addActions(getActionProperties());
            XmlToBean xmlToBean = MapXmlToBeanUtils.createMappingBean(execContext, definitionXmlFile);
            return mapXmlToBean(xmlToBean,xml);
        } catch (Exception e) {
        	throw new IllegalArgumentException(e.getMessage() + "\nUsing Mapping File [" + definitionXmlFile + "]", e);
        }
    }


    public Object mapXmlToBean(XmlToBean xmlToBean, String xml) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException,
            IllegalArgumentException, NoSuchFieldException {
        XMLObject xo = new XMLObject().mapXMLCharToXMLObject(xml);
        return mapXmlToBean(xmlToBean, xo);
    }

    /**
     * 
     * @param map
     *            - mappings of xml entity names to classes and bean properties
     *            to class type handlers.
     * @param xo
     *            - the xml
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws SecurityException
     */
    public Object mapXmlToBean(XmlToBean xmlToBean, XMLObject xo) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            SecurityException, IllegalArgumentException, NoSuchFieldException {
        this.xmlToBean = xmlToBean;
        log.warn("xo.getContent():" + xo.getContent());
        Bean bean = xmlToBean.getBean(xo.getElementName());
        Object object = bean.getClassAsObject();
        if (bean.getText() != null) {
            setProperty(bean, object, bean.getText().getName(), xo.getContent(), xo);
        }
        for (XMLAttribute att : xo.getAttributes()) {
            setProperty(bean, object, att.getKey(), att.getValue(), xo);
        }
        for (XMLObject child : xo.getChildren()) {
            createAction(bean, object, child);
        }

        return object;
    }

    public Object createAction(Bean parentBean, Object parentObject, XMLObject xo) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            SecurityException, IllegalArgumentException, NoSuchFieldException {
    	try {
    		Bean childBean;
    		childBean = xmlToBean.getBean(xo.getElementName());
			Object object = null;
    		if (childBean.getClas().equals(String.class.getCanonicalName())) {
    			object = xo.getContent(); 
    		} else {
    			object = childBean.getClassAsObject();
    		}
            setProperty(parentBean, parentObject, xo.getElementName(), object, xo);
            if (childBean.getText() != null) {
                setProperty(childBean, object, childBean.getText().getName(), xo.getContent(), xo);
            }
	        for (XMLAttribute att : xo.getAttributes()) {
                setProperty(childBean, object, att.getKey(), att.getValue(), xo);
	        }
	        for (XMLObject child : xo.getChildren()) {
	            // createAction(execContext, child, actionMapName);
	            createAction(childBean, object, child);
	        }
	        return object;
    	} catch (Exception ex){
    		throw new IllegalArgumentException(ex.getMessage()+"\nwhile processing:" + xo.mapXMLObject2XML(xo), ex);
    	}
    }




    public void setProperty(Bean bean, Object object, String propertyName, Object value, XMLObject xo)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException,
            InstantiationException, SecurityException, IllegalArgumentException, NoSuchFieldException {
        Property property = Property.getProperty(bean.getProperties(), propertyName);
        String fieldName;
        if (property == null) {
            fieldName = propertyName;
            setProperty(object, propertyName, value, fieldName, xo);
        } else {
            fieldName = property.getName();
            if (property.getPopulator() != null || property.getPopulator_ref() != null) {
                // we have a populator.
                Object populatorObject = property.getPopulator(xmlToBean);
                if (populatorObject instanceof Populator) {
                    Populator populator = (Populator) populatorObject;
                    useAction(populator.getClas(), populator.getKeyvalues(), object, fieldName, value, xo);
                } else {
                    useAction("" + populatorObject, null, object, fieldName, value, xo);
                }
            } else {
                setProperty(object, propertyName, value, fieldName, xo);
            }
        }
    }

    private void setProperty(Object object, String propertyName, Object value, String fieldName, XMLObject xo)
            throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException {
        PropertyDescriptor pd = null;
        try {
            pd = findPropertyDescriptor(object, fieldName);
            if (pd != null) {
                BeanUtils.setProperty(object, fieldName, value);
            } else {
                Class<?> c = object.getClass();
                Field field = c.getDeclaredField(fieldName);
                ConvertUtilsBean cub = new ConvertUtilsBean();
                Object converted = cub.convert(value, field.getType());
                field.set(object, converted);
            }
        } catch (ConversionException ex) {
            if (ignoreErrorWithEmptyValues == true && (value == null || ("" + value).length() == 0)) {
                // carry on processing, ignore because we have an empty value
            } else {
                throw new ConversionException("Unable to set property [" + propertyName + "] on bean ["
                        + object.getClass().getName() + "] with value of [" + value + "] error:" + ex.getMessage(), ex);
            }
        } catch (NoSuchFieldException ex) {
            if (strict == true) {
                throw ex;
            } else {
                // log.warn(object.getClass().getName() +
                // " has no such field [" + propertyName + "]:" +
                // ex.getMessage());
            }
        } catch (IllegalAccessException ex) {
            throw new IllegalAccessException("Unable to set property [" + propertyName + "] on bean ["
                    + object.getClass().getName() + "] with value of [" + value + "] error:" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            if (pd != null) {
                try {
                    // try and match up a populator.
                    Class<?> obj = pd.getPropertyType();
                    log.debug("obj:" + obj);
                    if (obj != null) {
                        if (List.class.getName().equals(obj.getName())) {
                            useAction(PopulatorArrayList.class.getName(), null, object, fieldName, value, xo);
                        }
                    }
                } catch (Exception ex_ignore) {
                    // ignore this, we'll throw the original exception
                    throw ex;
                }
            } else {
                throw ex;
            }
        }

    }

    public static PropertyDescriptor findPropertyDescriptor(Object object, String name) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        PropertyDescriptor pd = null;
        PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(object);
        for (PropertyDescriptor p : pds) {
            if (p.getName().equals(name)) {
                pd = p;
                break;
            }
        }
        if (pd != null) {
            log.debug("PropertyDescriptor [" + name + "] - " + " Name:" + pd.getName() + " DisplayName:"
                    + pd.getDisplayName() + " ShortDescription:" + pd.getShortDescription() + " PropertyType:"
                    + pd.getPropertyType() + " ReadMethod:" + pd.getReadMethod() + " WriteMethod:"
                    + pd.getWriteMethod() + " Value:" + pd.getValue(name));
            // } else {
            // log.error("PropertyDescriptor [" + name +
            // "] -  not found in class [" + object.getClass().getName() + "]");
        }
        return pd;

    }

    private void useAction(String actionName, List<KeyValue> keyvalues, Object object, String propertyName,
            Object value, XMLObject xo) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, SecurityException, IllegalArgumentException,
            NoSuchFieldException {
        log.debug(actionName + " - " + object + propertyName + " - " + value);

        Class clas = Class.forName(actionName);
        Object p = clas.newInstance();
        if (!(p instanceof PopulateClassFromXmlInterface)) {
            throw new InstantiationException(actionName + " does not implement "
                    + PopulateClassFromXmlInterface.class.getSimpleName());
        }
        PopulateClassFromXmlInterface pc = (PopulateClassFromXmlInterface) p;
        pc.performAction(keyvalues, object, propertyName, value, xo);

    }

    private static Properties getActionProperties() {
		try {
			InputStream is = ResourceUtils.getResourceURL(actionPropertiesFilename).openStream();
			Properties actionProperties = new Properties();
			actionProperties.load(is);
			is.close();
			return actionProperties;
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
}
