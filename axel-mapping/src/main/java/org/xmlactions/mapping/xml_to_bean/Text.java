package org.xmlactions.mapping.xml_to_bean;

import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class Text extends BaseAction {

    private String name;
    @Override
    public String execute(IExecContext execContext) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
