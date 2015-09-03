/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.xmlactions.pager.actions;
/**
 \page action_code_action Action Code

 A code action invokes java code during the construction of the web page on the server. The response, if any will replace the code element on the web page.

  Action:<strong>code</strong>
 
 <table border="0">
	<tr>
  		<td colspan="2"><hr/></td>
	</tr>
	<tr>
	 	<td><strong>Elements</strong></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
	 		<strong>Description</strong>  
		<td>
	 </tr>
	<tr>
  		<td colspan="2" height="6px"/>
	</tr>
	<tr>
	 	<td>param<br/><small>- optional</small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			One or more param elements may be added to the action.  These will be added in order to the method invocation. See \ref action_param
		<td>
	 </tr>
	<tr>
  		<td colspan="2"><hr/></td>
	</tr>
	<tr>
	 	<td><strong>Attributes</strong></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
	 		<strong>Description</strong>  
		<td>
	 </tr>
	<tr>
  		<td colspan="2" height="6px"/>
	</tr>
	<tr>
	 	<td>call<br/><small><i>- required</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			The full package, class and method name. As an example "org.xmlactions.utils.Class.methodName"
		<td>
	 </tr>
	<tr>
  		<td colspan="2"><hr/></td>
	</tr>
 </table>


 An example of how it looks on the web page
 \code
 <axel:code call="org.xmlactions.utils.Class.method"/>
 \endcode
 When this call is invoked the response from the call replaces the code action element.  If the response was "Hello World!!!" then 
 \code
 <axel:code call="org.xmlactions.utils.Class.method"/>
 \endcode
 becomes <br/>
 "Hello World!!!"
 
 
 The action will also accept parameters. As an example
 \code
 <axel:code call="org.xmlactions.utils.Class.methodName">
    <axel:param value="1" type="int"/>
    <axel:param value="Zoo" type="String"/>
 </axel:code>
 \endcode
 This call will invoke
 \code
 org.xmlactions.utils.Class.methodName(int i, String s)
 \endcode
 
 \see \ref action_param
 */


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

}
