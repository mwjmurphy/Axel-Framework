package org.xmlactions.action.config;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class CodeAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(CodeAction.class);

	private String call; // the package and method to call
	
	private String key;	// use this if we want to store the result of the code call to exec

	private List<Param> params = new ArrayList<Param>();

	public List<Param> getParams()
	{

		return params;
	}

	public void setParams(List<Param> params)
	{

		this.params = params;
	}

	public void setParam(Param param)
	{

		params.add((Param) param);
	}

	/**
	 * gets the last param in the list or null if none found.
	 * 
	 * @return
	 */
	public Param getParam()
	{

		if (params.size() == 0) {
			return null;
		}
		return params.get(params.size() - 1);
	}

	public void setChild(Param param)
	{

		params.add(param);
	}

	public void _setChild(BaseAction param)
	{

		Validate.isTrue(param instanceof Param, "Parameter must be a " + Param.class.getName());
		params.add((Param) param);
	}

	public String execute(IExecContext execContext) throws Exception
	{

		String methodName = getMethod();
		String clas = getClas();
		String className = (String) execContext.getAction(null,clas);
		if (className == null) {
			className = clas;
		}
		Validate.notEmpty(methodName);
		Validate.notEmpty(className, "no property value set for [" + clas + "]");
		Class<?> _clas = ClassUtils.getClass(className);
		Object bean = _clas.newInstance();

		Object[] objs = new Object[getParams().size()];
		for (int i = 0; i < getParams().size(); i++) {
			objs[i] = getParams().get(i).getResolvedValue(execContext);
		}
		try {
			Object obj = MethodUtils.invokeMethod(bean, methodName, objs);
			if (getKey() != null) {
				execContext.put(getKey(),  obj);
				obj = "";
			}
			return (obj != null ? obj.toString() : null);
		} catch (Exception ex) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < getParams().size(); i++) {
				sb.append("\nParam[" + i + "] '" + getParams().get(i).getValue() + "' = " + objs[i]);
			}
			String params = sb.toString();
			if (StringUtils.isEmpty(params)) {
				params = "no params";
			}
			throw new Exception("Unable to invoke [" + clas + "." + methodName + "]" + params, ex);
		}
	}

	public String toString()
	{

		StringBuilder sb = new StringBuilder();
		sb.append("code call [" + getCall() + "]");
		for (Param param : getParams()) {
			sb.append("\n param value [" + param.getValue() + "]");
		}
		return sb.toString();
	}

	public String getClas()
	{

		Validate.notNull(getCall());
		int pos = getCall().lastIndexOf('.');
		if (pos >= 0) {
			return getCall().substring(0, pos);
		}
		throw new NoSuchMethodError("Unable to find method in [" + getCall() + "]");
	}

	public String getMethod()
	{

		Validate.notNull(getCall(), "Missing method name for call attribute");
		int pos = getCall().lastIndexOf('.');
		if (pos >= 0) {
			return getCall().substring(pos + 1);
		}
		throw new NoSuchMethodError("Unable to find method in [" + getCall() + "]");
	}

	public void setCall(String call)
	{

		this.call = call;
	}

	public String getCall()
	{

		return call;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
