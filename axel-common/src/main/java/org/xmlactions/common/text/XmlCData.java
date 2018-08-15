package org.xmlactions.common.text;


public class XmlCData {

	private static final String startCdataPattern = "<![CDATA[";
	private static final String endCdataPattern = "]]>";
	
	/**
	 * This will remove a enveloping CDATA from an XML content. The inner content of the CDATA is returned.
	 * 
	 * If no CDATA start and end is found then the content is untouched.
	 * 
	 * @param content
	 * @return the inner content of the CDATA element.
	 */
	public static String removeCData(String content) {
		
		if (content != null) {
			int indexOf = content.indexOf(startCdataPattern);
			if (indexOf >= 0) {
				String trimmed = content.substring(0,indexOf).trim();
				if (trimmed.length() == 0) {
					content = content.trim();
					int start = content.indexOf(startCdataPattern) + startCdataPattern.length();
					int end = content.lastIndexOf(endCdataPattern);
					if (end >= 0) {
						trimmed = content.substring(end).trim();
						if (trimmed.length() ==  endCdataPattern.length()) {
							return content.substring(start,end);
						}
					}
				}
			}
		}
		return content;
	}

	/**
	 * This will remove a enveloping CDATA from an XML content. The inner content of the CDATA is returned.
	 * 
	 * If no CDATA start and end is found then the content is untouched.
	 * 
	 * @param content
	 * @return the inner content of the CDATA element.
	 */
	public static String removeAllCData(String content) {
		
		if (content != null) {
			content = content.replaceAll("<!\\[CDATA\\[", "");
			content = content.replaceAll(endCdataPattern, "");
		}
		return content;
	}

	public static String _removeCData(String content) {
		if (content != null) {
			// String startCdataPattern = "<![cdata[";
			String startCdataPattern = "<!\\[cdata\\[";
			String endCdataPattern = "]]>";
			content = content.replaceAll(startCdataPattern, "");
			content = content.replaceAll(startCdataPattern.toUpperCase(), "");
			content = content.replaceAll(endCdataPattern, "");
//			String cdata = content.toLowerCase();
//			int startCdataIndex = cdata.indexOf(startCdataPattern);
//			if (startCdataIndex >= 0) {
//				int indexFrom = startCdataIndex + startCdataPattern.length()-1;
//				if (indexFrom >= 0) {
//					int indexTo = cdata.lastIndexOf(endCdataPattern);
//					if (indexTo >= 0) {
//						content = content.substring(indexFrom+1, indexTo);
//					}
//				}
//			}
		}
		return content;
	}



}
