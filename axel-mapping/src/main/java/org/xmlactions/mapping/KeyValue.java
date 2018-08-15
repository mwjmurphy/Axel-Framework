package org.xmlactions.mapping;

import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class KeyValue extends BaseAction {

    private String key;
    private String value;

    public String execute(IExecContext execContext) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
