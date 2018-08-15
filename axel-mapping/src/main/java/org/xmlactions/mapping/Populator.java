package org.xmlactions.mapping;


import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class Populator extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(Populator.class);
	
	private String id;
	private String clas;

    private List<KeyValue> keyvalues;

	public String execute(IExecContext execContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setClas(String clas) {
		this.clas = clas;
	}

	public String getClas() {
		return clas;
	}

    public void setKeyvalue(KeyValue keyValue) {
        if (keyvalues == null) {
            keyvalues = new ArrayList<KeyValue>();
        }
        keyvalues.add(keyValue);
    }

    public void setKeyvalues(List<KeyValue> keyValues) {
        this.keyvalues = keyValues;
    }

    public List<KeyValue> getKeyvalues() {
        return keyvalues;
    }

}
