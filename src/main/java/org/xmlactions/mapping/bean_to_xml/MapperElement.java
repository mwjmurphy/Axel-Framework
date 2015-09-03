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

package org.xmlactions.mapping.bean_to_xml;


import java.util.List;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.Element;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.mapping.KeyValue;
import org.xmlactions.mapping.Populator;

public class MapperElement  extends BaseAction {

	private static final Logger log = LoggerFactory.getLogger(MapperElement.class);
	private static final String className = "element";
	
    private static String defaultListPopulator = PopulatorFromList.class.getName();
    private static String defaultArrayPopulator = PopulatorFromArray.class.getName();

	private String bean_ref;
	private String property;
	private String name;
    private String populator;
    private String populator_ref;
    private String prefix;
	
	public String execute(IExecContext execContext) throws Exception {
		return null;
	}
	
	public Element buildXml(BeanToXml beanToXml, Element parent, Object mapClasses) {
		log.debug(toString());
        Element element = null;
		try {
			Object property = PropertyUtils.getSimpleProperty(mapClasses, getProperty());
			if (property != null) {
				log.debug("property object = "+ property.getClass().getName());
				// see if we have a handler for this property
                Object populatorObject = getPopulatorQuietly(beanToXml);
                if (populatorObject != null) {
                    if (populatorObject instanceof Populator) {
                        Populator populator = (Populator) populatorObject;
                        element = useAction(beanToXml,
                                populator.getKeyvalues(),
                                populator.getClas(),
                                parent,
                                property,
                                getProperty());
                    } else {
                        element = useAction(beanToXml, null, "" + populatorObject, parent, property, getProperty());
                    }
                } else {
                    if (property instanceof List) {
                        element = useAction(beanToXml, null, defaultListPopulator, parent, property, getProperty());
                    } else if (property instanceof Object[]) {
                        element = useAction(beanToXml, null, defaultArrayPopulator, parent, property, getProperty());
                    } else {
                        Bean bean = beanToXml.findBeanByName(this.getBean_ref());
                        element = bean.processBean(beanToXml, parent, property, getPrefix(), getName());
                    }
                }
			}
		}
		catch (NullPointerException ex) {
			log.error("NullPointerException:"+ toString());
		}
		catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage() + ":"+toString(), ex);
		}
		return element;
	}

	private String getName() {
		if (StringUtils.isEmpty(name)) {
			return getProperty();
		}
		return name;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBean_ref() {
		return bean_ref;
	}

	public void setBean_ref(String bean_ref) {
		this.bean_ref = bean_ref;
	}

	
	public String toString() {
		return className + " property [" + property + "] name [" + name + "] bean_ref [" + bean_ref + "]";
	}

    public void setPopulator_ref(String populator_ref) {
        this.populator_ref = populator_ref;
    }

    public String getPopulator_ref() {
        return populator_ref;
    }

    public Object getPopulator(BeanToXml beanToXml) {
        if (populator == null) {
            if (populator_ref != null) {
                Populator populator = beanToXml.getPopulator(populator_ref);
                if (populator == null) {
                    throw new IllegalArgumentException("No populator_ref has been set for [" + populator_ref + "]");
                }
                return populator;
            } else {
                throw new IllegalArgumentException("No populator has been set for property [" + name + "]");
            }
        } else {
            return populator;
        }
    }

    public void setPopulator(String polulator) {
        this.populator = populator;
    }

    public Object getPopulatorQuietly(BeanToXml beanToXml) {
        if (populator == null) {
            if (populator_ref != null) {
                Populator populator = beanToXml.getPopulator(populator_ref);
                if (populator == null) {
                    return null;
                }
                return populator;
            } else {
                return null;
            }
        } else {
            return populator;
        }
    }

    private Element useAction(BeanToXml beanToXml, List<KeyValue> keyvalues, String actionName, Element parent,
            Object object, String propertyName)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        log.debug(actionName + " - " + object + propertyName);

        Class clas = Class.forName(actionName);
        Object p = clas.newInstance();
        if (!(p instanceof PopulateXmlFromClassInterface)) {
            throw new InstantiationException(actionName + " does not implement "
                    + PopulateXmlFromClassInterface.class.getSimpleName());
        }
        PopulateXmlFromClassInterface pc = (PopulateXmlFromClassInterface) p;
        return pc.performElementAction(keyvalues, beanToXml, parent, object, prefix, name, getBean_ref());

    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

}
