package org.xmlactions.action.actions.code.execute;

import java.util.ArrayList;
import java.util.List;

public class CodeParams {

	private List<CodeParam> codeParams;
	
	public CodeParams() {
		codeParams = new ArrayList<CodeParam>();
	}
	
	public void addAll(List<String> inputParams ) {
		for (String param : inputParams) {
			String value;
			String type;
			int partIndex = param.indexOf(":");
			if (partIndex > 0) {
				value = param.substring(partIndex+1);
				type = param.substring(0, partIndex);
			} else {
				value = param;
				if (isDecimal(value)) {
					type = "decimal";
				} else if (isNumeric(value) ) {
					type = "integer";
				} else {
					type = "string";
				}
			}
			add(value, type);
		}
	}
	
	private void add(String value, String type) {
		CodeParam cp = new CodeParam();
		if ("string".equalsIgnoreCase(type)) {
			cp.setValueAsString(value);
		} else if("integer".equalsIgnoreCase(type)) {
			cp.setValueAsInt(value);
		} else if("decimal".equalsIgnoreCase(type)) {
			cp.setValueAsDecimal(value);
		} else if("long".equalsIgnoreCase(type)) {
			cp.setValueAsLong(value);
		} else {
			cp.setValueAsString(value);
		}
		codeParams.add(cp);
	}
	
	private boolean isDecimal(String value) {
		if (isNumeric(value)) {
			if (value.indexOf(".") >= 0) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean isNumeric(final String str) {

        if (str == null || str.length() == 0) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;

    }
	
	public List<CodeParam> getCodeParams() {
		return codeParams;
	}


} 
