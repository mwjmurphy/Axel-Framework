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

public class MapperAttribute extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(MapperAttribute.class);
    private static final String className = "attribute";

    private String name;
    private String property;
    private String populator;
    private String populator_ref;
    private String prefix;

    public String execute(IExecContext execContext) throws Exception {
        return null;
    }

    public Element buildXml(BeanToXml beanToXml, Element parent, Object mapClasses) {
        try {
            Object property = PropertyUtils.getSimpleProperty(mapClasses, getProperty());
            Object populatorObject = getPopulator(beanToXml);
            if (populatorObject != null) {
                if (populatorObject instanceof Populator) {
                    Populator populator = (Populator) populatorObject;
                    Element element = useAction(beanToXml,
                            populator.getKeyvalues(),
                            populator.getClas(),
                            parent,
                            property,
                            getProperty());
                } else {
                    Element element = useAction(beanToXml, null, "" + populatorObject, parent, property, getProperty());
                }

            } else {
               	BeanToXmlUtils.addAttribute(parent, getPrefix(), getName(), property.toString());
            }
        } catch (NullPointerException ex) {
            log.error("NullPointerException:" + toString());
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage() + ":" + toString(), ex);
        }
        return parent;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getProperty() {
        if (StringUtils.isEmpty(property)) {
            return name;
        }
        return property;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        if (StringUtils.isEmpty(name)) {
            return property;
        }
        return name;
    }

    public String toString() {
        return className + " name [" + name + "] property [" + property + "]";
    }

    public void setPopulator(String populator) {
        this.populator = populator;
    }

    public String getPopulator() {
        return populator;
    }

    public void setPopulator_ref(String populator_ref) {
        this.populator_ref = populator_ref;
    }

    public String getPopulator_ref() {
        return populator_ref;
    }

    public Object getPopulator(BeanToXml beanToXml) {
        if (StringUtils.isEmpty(populator) && StringUtils.isEmpty(populator_ref)) {
            return null;
        }
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

    private Element useAction(BeanToXml beanToXml, List<KeyValue> keyvalues, String actionName, Element parent,
            Object object, String propertyName) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        log.debug(actionName + " - " + object + propertyName);

        Class clas = Class.forName(actionName);
        Object p = clas.newInstance();
        if (!(p instanceof PopulateXmlFromClassInterface)) {
            throw new InstantiationException(actionName + " does not implement "
                    + PopulateXmlFromClassInterface.class.getSimpleName());
        }
        PopulateXmlFromClassInterface pc = (PopulateXmlFromClassInterface) p;
        return pc.performAttributeAction(keyvalues, beanToXml, parent, object, prefix, name, null);

    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
