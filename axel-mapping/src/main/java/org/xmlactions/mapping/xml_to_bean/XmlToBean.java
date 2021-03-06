package org.xmlactions.mapping.xml_to_bean;


import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.mapping.Populator;

public class XmlToBean extends BaseAction {
	
	private static Logger log = LoggerFactory.getLogger(XmlToBean.class);
	
	private List<Bean> beans;
	
	private List<Populator> populators;

	public String execute(IExecContext execContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Bean getBean(String elementName) {
		for (Bean bean : getBeans()) {
			for (Creator creator : bean.getCreators()) {
				if (elementName.equals(creator.getElement())) {
					return bean;
				}
			}
		}
		throw new IllegalArgumentException("No matching bean found for [" + elementName + "]");
	}

	public List<Bean> getBeans() {
		if (beans == null) {
			beans = new ArrayList<Bean>();
		}
		return beans;
	}
	public void setBeans(List<Bean> beans) {
		this.beans = beans;
	}
	
	public void setBean(Bean bean) {
		getBeans().add(bean);
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
}
