
package org.xmlactions.pager.actions.mapping;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.mapping.json.JSONUtils;
import org.xmlactions.web.RequestExecContext;

public class JSONGetAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(JSONGetAction.class);

	private String json_data;
	private String json_path;
	private int index;
	
	private IExecContext execContext;
	
	public String execute(IExecContext execContext) throws Exception
	{
		this.execContext = execContext;
		return "" + JSONUtils.get(getJson_data(), getJson_path(), getIndex());
	}
	
	public String toString()
	{

		return "json_get [" + getJson_data() + "] [" + getJson_path() + "] [" + getIndex( )+ "]";
	}

	public String getJson_data() {
		if (execContext == null) { 
			execContext = RequestExecContext.get();
		}
		return execContext.replace(json_data);
	}

	public void setJson_data(String json_data) {
		this.json_data = json_data;
	}

	public String getJson_path() {
		return json_path;
	}

	public void setJson_path(String json_path) {
		this.json_path = json_path;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


}