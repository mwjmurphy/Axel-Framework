package org.xmlactions.mapping.bean_to_xml;


import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.Element;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.mapping.Populator;

public class BeanToXml extends BaseAction {

	private static Logger log = LoggerFactory.getLogger(BeanToXml.class);
	
	public static String MAP_OBJECT_REF = "map_classes";

	private List<Bean> beans;
	
    private List<Populator> populators;

    private List<Namespace> namespaces;

	public String execute(IExecContext execContext) throws Exception {
		Object mapClasses = execContext.get(MAP_OBJECT_REF);
		Validate.notNull(mapClasses, "Missing '" + MAP_OBJECT_REF + "' from ExecContext");
		String className = mapClasses.getClass().getName();
		Bean b = findBeanOfClass(className);
		Validate.notNull(b, "No bean found matching clas:" + className);
		log.debug("className:" + className);
		Element element = b.processBean(this, mapClasses);
		return element.asXML();
	}
	
	public List<Bean> getBeans() {
		if (this.beans == null) {
			this.beans = new ArrayList<Bean>();
		}
		return beans;
	}

	public void setBeans(List<Bean> beans) {
		this.beans = beans;
	}

	public void setBean(Bean bean) {
		getBeans().add(bean);
	}
	
	public Bean findBeanOfClass(String className) {
		for (Bean bean : getBeans()) {
			if (className.equals(bean.getClas())) {
				log.debug("found bean with class name:" + bean.getClas());
			}
			return bean;
		}
		return null;
	}

	public Bean findBeanByName(String name) {
		for (Bean bean : getBeans()) {
			if (name.equals(bean.getId())) {
				log.debug("found bean with name:" + bean.getId());
				return bean;
			}
		}
		return null;
	}

    public void setPopulators(List<Populator> populators) {
        this.populators = populators;
    }

    public List<Populator> getPopulators() {
        if (populators == null) {
            populators = new ArrayList<Populator>();
        }
        return populators;
    }

    public void setPopulator(Populator populator) {
        getPopulators().add(populator);
    }

    public Populator getPopulator(String id) {
        for (Populator populator : getPopulators()) {
            if (id.equals(populator.getId())) {
                return populator;
            }
        }
        return null;
    }

    public void setNamespacess(List<Namespace> namespaces) {
        this.namespaces = namespaces;
    }

    public List<Namespace> getNamespaces() {
        if (namespaces == null) {
            namespaces = new ArrayList<Namespace>();
        }
        return namespaces;
    }

    public void setNamespace(Namespace namespace) {
        getNamespaces().add(namespace);
    }
    public Namespace getNamespace() {
    	if (getNamespaces().size() > 0) {
    		return getNamespaces().get(getNamespaces().size()-1);
    	}
    	return null;
    }
}
