package org.xmlactions.db.actions;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.XmlCData;
import org.xmlactions.db.sql.select.SqlField;

public class Sql extends BaseAction {

    private String name;
    private String sql;
    private String params;
    //List<SqlField> params

    public String execute(IExecContext execContext) throws Exception {

        return null;
    }
    
    public List<SqlField> getListOfParams(IExecContext execContext) {
    	String paramparts = params;
    	List<SqlField> list = new ArrayList<SqlField>();
    	if (StringUtils.isNotBlank(paramparts)) {
    		if (execContext != null) {
    			paramparts = execContext.replace(paramparts);
    		}
	    	String [] parts = paramparts.split(",");
	    	for (String part : parts) {
	    		part = StringEscapeUtils.unescapeHtml(part);
	    		int index = part.indexOf('=');
				String key;
				String value;
	    		if (index > 0) {
	    			key = part.substring(0, index).trim();
	    			if (key.length() < part.length()) {
	    				value = part.substring(index+1);
	    			} else {
	    				value = null;
	    			}
	    		} else {
	    			key = "";
	    			value = part;
	    		}
				SqlField sqlField = new SqlField(key);
				sqlField.setValue(value);
				CommonStorageField commonStorageField = new Text();
				sqlField.setCommonStorageField(commonStorageField);
				list.add(sqlField);
	    	}
    	}
    	return list;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        if (StringUtils.isEmpty(sql)) {
            if (StringUtils.isEmpty(getContent())) {
                throw new IllegalArgumentException("The sql attribute has not been set or is content of the sql element set.");
            }
            return XmlCData.removeCData(getContent());
        }
        return sql;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
