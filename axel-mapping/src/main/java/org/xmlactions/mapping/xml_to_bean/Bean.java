package org.xmlactions.mapping.xml_to_bean;


import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.ClassUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class Bean extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(Bean.class);
	
	private String element;
	private String clas;	
	
	private List<Creator> creators;
	private List<Property> properties;
    private Text text;

	public String execute(IExecContext execContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setClas(String name) {
		this.clas = name;
	}

	public String getClas() {
		return clas;
	}

	public List<Creator> getCreators() {
		if (creators == null) {
			creators = new ArrayList<Creator>();
		}
		return creators;
	}
	public void setCreators(List<Creator> creators) {
		this.creators = creators;
	}
	
	public void setCreator(Creator creator) {
		getCreators().add(creator);
	}

	public List<Property> getProperties() {
		if (properties == null) {
			properties = new ArrayList<Property>();
		}
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
	public void setProperty(Property property) {
		getProperties().add(property);
	}
	
	public Class<?> getAsClass() throws ClassNotFoundException {
		Class<?> _clas = ClassUtils.getClass(clas);
		return _clas;
	}
	
	public Object getClassAsObject() throws ClassNotFoundException, InstantiationException, IllegalAccessException  { 
		Object bean = null;
		Class<?> _clas = getAsClass();
		bean = _clas.newInstance();
		return bean;
	}

	public void setElement(String element) {
		this.element = element;
		Creator creator = new Creator();
		creator.setElement(element);
		setCreator(creator);
	}

	public String getElement() {
		return element;
	}

    public void setText(Text text) {
        this.text = text;
    }

    public Text getText() {
        return text;
    }


}
