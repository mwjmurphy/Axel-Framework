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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class HttpAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(HttpAction.class);

	private String method; // the http methid to call such as get or port
	
	private String href; // the http address to call without parameters such as http://google.com
	
	private String key;	// use this if we want to store the result of the http call to exec

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
		String error = "";
		validate();
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> response = null;
		if (getMethod().equalsIgnoreCase("get")) {
			String url = execContext.replace(getHref()) + buildParamsForGet(execContext);
			try {
				response = restTemplate.getForEntity(url, String.class);
			} catch (Exception ex) {
				error = buildError(ex, url);
			}
		} else if (getMethod().equalsIgnoreCase("post")) {
			String url = execContext.replace(getHref());
			try {
				Map<String,String> map = buildParamsForPost(execContext);
				HttpEntity<Map<String,String>> entity = new HttpEntity<Map<String,String>>(map);
				response = restTemplate.postForEntity(url, entity, String.class);
			} catch (Exception ex) {
				error = buildError(ex, url);
			}
		} else {
			throw new IllegalArgumentException("Unsupported Method [" + getMethod() + "]");
		}
		if (response.getStatusCodeValue() != 200) {
			throw new IllegalArgumentException("Http Request [" + getHref() + "] faied with error code [" + response.getStatusCodeValue() + ":" + response.getStatusCode());
		}
		if (error.length() > 0) {
			return error;
		}
		if (getKey() != null) {
			execContext.put(key, response.getBody());
			return "";
		} else {
			return response.getBody();
		}
	}
	
	private String buildError(Exception ex, String uri) {
		return "{\"error\" : { \"message\" : \"" + ex.getMessage() + "\", \"address\" : \"" + uri + "\"}}";
	}
	
	private void validate() {
		if (StringUtils.isBlank(getHref())) {
			throw new IllegalArgumentException("Http Request missing Href Parameter");
		}
		
		if (StringUtils.isBlank(getMethod())) {
			setMethod("get");
		}
	}
	
	private String buildParamsForGet(IExecContext execContext) {
		StringBuffer sb = new StringBuffer();
		String lead = "?";
		if (getParams() != null) {
			for (Param param : getParams()) {
				String [] parts = execContext.replace(param.getValue()).split("=");
				if (parts.length > 1) {
					sb.append(lead + parts[0] + "=" + parts[1]);
				} else {
					sb.append(lead + param.getName() + "=" + parts[0]);
				}
				lead = "&";
			}
		}
		
		return sb.toString();
		
	}

	private Map<String,String> buildParamsForPost(IExecContext execContext) {
		Map<String,String> map = new HashMap<String,String>();
		if (getParams() != null) {
			for (Param param : getParams()) {
				String [] parts = execContext.replace(param.getValue()).split("=");
				if (parts.length > 1) {
					map.put(parts[0], "" + parts[1]);
				} else {
					map.put(param.getName(), "" + parts[0]);
				}
			}
		}
		return map;
	}

	public String toString()
	{

		StringBuilder sb = new StringBuilder();
		sb.append("http call [" + getHref()+ "]");
		for (Param param : getParams()) {
			sb.append("\n param value [" + param.getValue() + "]");
		}
		return sb.toString();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	

}
