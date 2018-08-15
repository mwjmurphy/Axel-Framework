
package org.xmlactions.db.actions;


import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;


public class Binary extends CommonStorageField
{
	
	/*
	 *	<p>
	 * 		Sets the acceptable binary pattern for true/false, yes/no, Y/N, 1/0
     *  </p>
     *  <p>
     *		Note the false value must be first followed by the seperator / followed
	 *		by the true value.
	 *	</p>
     *  <p>
     *  	The first entry in the pattern is always considered the true
     *      value and the second entry in the pattern is always considered
     *      the false value.
     *   </p>
     *   <p>
     *   	The seperator between the true and false value is the / character.
     *   </p>
     *   <p>
     *   	Any leading or trailing whitespace is removed.
     *   </p> 
     *   <p>
     *   	Example:<br/>
     *      <ul>
     *      	<li>Y/N</li>
     *          <li>true/false</li>
     *          <li>1/0</li>
     *          <li>X/Y</li>
     *       </ul>
     *    </p>
     *    <p>
     *    	This is an optional attribute and if not used the default pattern
     *      is true/false
     *     </p>
	 */
	private String pattern;
	
	private String splitChar = "/";

	public String execute(IExecContext execContext) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String toString(int indent)
	{

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append(' ');
		}
		sb.append("Binary:" + getName());
		return sb.toString();
	}

	public String validate(String value)
	{

		String error = null;
		if (StringUtils.isEmpty(value) && this.isMandatory()) {
			error = "Missing Value";
		}
		return buildErrorString(error);
	}
	
	private boolean isPatternValid(String pattern) {
		boolean isValid = false;
		if (StringUtils.isNotEmpty(pattern)) {
			String [] split = pattern.split(splitChar);
			if (split.length == 2) {
				if (StringUtils.isNotEmpty(split[0])) {
					if (StringUtils.isNotEmpty(split[1])) {
						isValid = true;		// valid because we have a 2 sided split
					}
				}
			}
		} else {
			isValid = true;	// valid because we dont have a pattern
		}
		return isValid;
	}
	
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return this.pattern;
	}
	
	public boolean isTrue(String value) {
		boolean isTrue = false;
		if (value.equals("1") ||
			value.equalsIgnoreCase("true") ||
			value.equalsIgnoreCase("y") ||
			value.equalsIgnoreCase("yes")) {
			isTrue = true;
		} else if(value.equals("0") ||
				  value.equalsIgnoreCase("false") || 
			      value.equalsIgnoreCase("n") || 
				  value.equalsIgnoreCase("no")) { 
			
		} else if (StringUtils.isNotEmpty(value)) {
			if (isPatternValid(getPattern())) {
				if (StringUtils.isNotEmpty(this.pattern)) {
					String [] split = this.pattern.split("/");
					if (value.equalsIgnoreCase(split[1])) {
						isTrue = true;
					}
				}
			} else {
				isTrue = BooleanUtils.toBoolean(value);
			}
		}
		return isTrue;
	}
	
	public String getTrueValue() {
		String trueValue = "true";
		if (isPatternValid(getPattern())) {
			if (StringUtils.isNotEmpty(this.pattern)) {
				String [] split = this.pattern.split("/");
				trueValue = split[1];
			}
		}
		return trueValue;
	}
	public String getFalseValue() {
		String falseValue = "false";
		if (isPatternValid(getPattern())) {
			if (StringUtils.isNotEmpty(this.pattern)) {
				String [] split = this.pattern.split("/");
				falseValue = split[0];
			}
		}
		return falseValue;
	}
}
