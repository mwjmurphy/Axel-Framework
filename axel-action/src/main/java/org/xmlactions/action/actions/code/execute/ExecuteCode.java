package org.xmlactions.action.actions.code.execute;

import java.util.List;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.xmlactions.action.config.IExecContext;

public class ExecuteCode {

	private final IExecContext execContext;
	
	public ExecuteCode(final IExecContext execContext) {

		this.execContext = execContext;

	}

	public Object executeWithParams(String actionName, List<String> params) {
		CodeParams codeParams = new CodeParams();
		codeParams.addAll(params);
		try {
			return execute(actionName, codeParams.getCodeParams());
		} catch (Exception e) {
			throw new ExecuteCodeException(e.getMessage(), e);
		}
	}
	
	private Object execute(String actionName, List<CodeParam> params) throws Exception {
		String methodName = getMethod(actionName);
		String clas = getClas(actionName);
		String className = (String) execContext.getAction(null, clas);
		if (className == null) {
			className = clas;
		}
		Validate.notEmpty(methodName);
		Validate.notEmpty(className, "no property value set for [" + clas + "]");
		Class<?> _clas = ClassUtils.getClass(className);
		Object bean = _clas.newInstance();

		Object[] objs = new Object[params.size()];
		for (int i = 0; i < params.size(); i++) {
			objs[i] = params.get(i).getResolvedValue(execContext);
		}
		try {
			Object obj = MethodUtils.invokeMethod(bean, methodName, objs);
			return (obj != null ? obj.toString() : null);
		} catch (Exception ex) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < params.size(); i++) {
				sb.append("\nParam[" + i + "] '" + params.get(i).getValue() + "' = " + objs[i]);
			}
			String sparams = sb.toString();
			if (StringUtils.isEmpty(sparams)) {
				sparams = "no params";
			}
			throw new Exception("Unable to invoke [" + clas + "." + methodName + "]" + sparams, ex);
		}
	}
	
	public String getClas(String actionName)
	{

		int pos = actionName.lastIndexOf('.');
		if (pos >= 0) {
			return actionName.substring(0, pos);
		}
		throw new NoSuchMethodError("Unable to find method in [" + actionName + "]");
	}


	
	public String getMethod(String actionName) {

		int pos = actionName.lastIndexOf('.');
		if (pos >= 0) {
			return actionName.substring(pos + 1);
		}
		throw new NoSuchMethodError("Unable to find method in [" + actionName + "]");
	}


}
