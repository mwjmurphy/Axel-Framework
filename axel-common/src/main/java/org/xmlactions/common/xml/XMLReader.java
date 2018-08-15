package org.xmlactions.common.xml;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.system.JS;


public class XMLReader {
	private static Logger log = LoggerFactory.getLogger(XMLReader.class);
	byte[] buffer;
	int bufferSize;

	public boolean debug = false;
	int curPos = 0;
	int markPos = 0;
	protected boolean ReadAll = false; // if true include cdata and comments in
	// read()
	StringBuffer error;

	public XMLReader() {
		super();
		error = new StringBuffer();
		curPos = 0;
		markPos = 0;
		bufferSize = 0;
		buffer = null;
	}

	public XMLReader(byte[] buffer) {
		super();
		error = new StringBuffer();
		if (buffer == null || buffer.length <= 0) {
			org.apache.commons.lang.Validate.notEmpty("", "buffer is empty");
			bufferSize = 0;
			log.warn("XMLReader: buffer is null or zero length"
					+ JS.getCurrentStack_static());
			error.append("XMLReader: buffer is null or zero length ("
					+ JS.getCurrentMethodName_static() + ") ");
			return;
		}
		curPos = 0;
		markPos = 0;
		this.buffer = buffer;
		this.bufferSize = buffer.length;
		/*
		 * this.buffer = new byte[buffer.length]; this.bufferSize =
		 * buffer.length; try { copy(buffer, this.buffer); } catch (Exception
		 * ex) { error.append(ex.getMessage()); // Not much I need to do about
		 * it here as both buffers must be the same size //
		 * System.out.println(e.getMessage()); }
		 */

	}

	public String getErrorMessage() {
		if (error.length() <= 0) {
			return (null);
		}
		return (error.toString());
	}

	public void clearErrorMessage() {
		error = new StringBuffer();
	}

	public int getCurPos() {
		return (curPos);
	}

	public void setCurPos(int pos) {
		curPos = pos;
	}

	public void reset() {
		curPos = 0;
	}

	private void setMark() {
		markPos = curPos;
	}

	private void resetToMark() {
		curPos = markPos;
	}

	private void copy(byte[] from, byte[] to) {
		if (from.length > to.length) {
			// throw new
			// Exception("the buffer to copy to is smaller than the buffer to copy from");
			// log.error("the buffer to copy to is smaller than the buffer to copy from");
			error
					.append("the buffer to copy to is smaller than the buffer to copy from");
		}
		for (int iLoop = 0; iLoop < to.length; iLoop++) {
			to[iLoop] = from[iLoop];
		}
	}

	private void copy(byte[] from, byte[] to, int start) {
		int iLoop = 0;
		try {
			for (iLoop = 0; iLoop < to.length; iLoop++) {
				byte b = from[iLoop + start];
				to[iLoop] = b;
				// to[iLoop] = from[iLoop + start];
			}
		} catch (Exception ex) {
			// throw e;
			error.append("copy" + "\n       start:" + start + "\n from.length:"
					+ from.length + "\n   to.length:" + to.length
					+ "\n       iLoop:" + iLoop + "\n iLoop+start:"
					+ (iLoop + start) + "\n   from data:" + new String(from)
					+ "\n     to data:" + new String(to) + "\n"
					+ ex.getMessage());
		}
	}

	private void copy(byte[] from, byte[] to, int start, int offset, int size) {
		int iLoop = 0;
		try {
			for (iLoop = 0; iLoop < size; iLoop++) {
				byte b = from[iLoop + start];
				to[iLoop + offset] = b;
				// to[iLoop] = from[iLoop + start];
			}
		} catch (Exception ex) {
			// throw e;
			error.append("copy" + "\n        from:" + new String(from)
					+ "\n       start:" + start + "\n from.length:"
					+ from.length + "\n   to.length:" + to.length
					+ "\n          to:" + new String(to) + "\n       iLoop:"
					+ iLoop + "\n" + ex.getMessage());
		}
	}

	/**
	 * readByte is the only place where we read a byte from the buffer
	 */
	private byte readByte(int index) throws EndOfBufferException // end of
	// buffer
	{
		if (index >= bufferSize) {
			EndOfBufferException e = new EndOfBufferException("End of buffer");
			// log.error(e);
			throw e;
		}
		return (buffer[index]);
	}

	/**
	 * Read a byte skipping any <code><!-- comments --></code> or any
	 * <code><![CDATA[...]]></code>
	 * 
	 * @return byte read or null
	 */
	public byte read() throws EndOfBufferException, BadXMLException, Exception {
		while (true) {
			byte b = readByte(curPos++);
			if (b == '<' && ReadAll == false) {
				// now see if we skip a comment
				if (readByte(curPos) == '!' && readByte(curPos + 1) == '-'
						&& readByte(curPos + 2) == '-') {
					// log.info("skipping comment");
					int errorStart = curPos;
					// Start of comment. now move to end of comment "-->"
					// System.out.println("start comment");
					curPos = curPos + 3;
					boolean endComment = false;
					while (endComment == false) {
						try {
							for (int iLoop = curPos; endComment == false; iLoop++) {
								if (readByte(iLoop) == '-'
										&& readByte(iLoop + 1) == '-'
										&& readByte(iLoop + 2) == '>') {
									// System.out.println("end comment");
									// End of comment
									curPos = iLoop + 3;
									endComment = true;
									// log.info("end of comment:" +
									// (char)buffer[curPos-3] +
									// (char)buffer[curPos-2] +
									// (char)buffer[curPos-1] +
									// (char)buffer[curPos]);
								}
							}
						} catch (Exception e) {
							// Oops bad xml, can't find end of comment
							throw new BadXMLException(
									"bad XML, no end of comment for comment starting at position "
											+ errorStart);
						}
					}
				}
				// now see if we skip a CDATA
				else if (readByte(curPos) == '!' && readByte(curPos + 1) == '['
						&& readByte(curPos + 2) == 'C'
						&& readByte(curPos + 3) == 'D'
						&& readByte(curPos + 4) == 'A'
						&& readByte(curPos + 5) == 'T'
						&& readByte(curPos + 6) == 'A'
						&& readByte(curPos + 7) == '[') {
					// log.info("skipping comment");
					int errorStart = curPos;
					// Start of comment. now move to end of comment "-->"
					// System.out.println("start comment");
					curPos = curPos + 8;
					boolean endCDATA = false;
					while (endCDATA == false) {
						try {
							for (int iLoop = curPos; endCDATA == false; iLoop++) {
								if (readByte(iLoop) == ']'
										&& readByte(iLoop + 1) == ']'
										&& readByte(iLoop + 2) == '>') {
									// System.out.println("end comment");
									// End of comment
									curPos = iLoop + 3;
									endCDATA = true;
									// log.info("end of comment:" +
									// (char)buffer[curPos-3] +
									// (char)buffer[curPos-2] +
									// (char)buffer[curPos-1] +
									// (char)buffer[curPos]);
								}
							}
						} catch (Exception e) {
							// Oops bad xml, can't find end of comment
							throw new BadXMLException(
									"bad XML, no end of CDATA for CDATA starting at position "
											+ errorStart);
						}
					}
				} else {
					return (b);
				}
			} else {
				return (b);
			}
		}
	}

	/**
	 * find any byte value
	 * 
	 * @param b
	 *            = byte value to find
	 * @return index where byte value found or -1 if not found
	 */
	private int find(byte b) {
		try {
			// System.out.print("find:" + (char)b + ", curPos = " + curPos);
			while (read() != b) {
				;
			}
			// System.out.println(" ::: found:" + (char)b + ", curPos = " +
			// curPos);
			return (curPos - 1);
		} catch (EndOfBufferException e) {
			return (-1); // not found
		} catch (Exception ex) {
			error.append(ex.getMessage());
			return (-1); // not found
		}
	}

	/**
	 * find any byte value
	 * 
	 * @param b
	 *            = byte value to find
	 * @return index where byte value found or -1 if not found
	 */
	private int find(byte[] b) {
		try {
			// System.out.print("find:" + (char)b + ", curPos = " + curPos);
			for (int iLoop = 0; iLoop < b.length; iLoop++) {
				if (read() != b[iLoop]) {
					iLoop = -1; // back to beginning
				}
			}
			// System.out.println(" ::: found:" + (char)b + ", curPos = " +
			// curPos);
			return (curPos - b.length);
		} catch (EndOfBufferException e) {
			return (-1); // not found
		} catch (Exception ex) {
			error.append(ex.getMessage());
			return (-1); // not found
		}
	}

	private int find(byte b, byte c) {
		// System.out.print("find:" + (char)b + ", curPos = " + curPos);
		boolean found = false;
		byte a;
		while (true) {
			try {
				a = read();
				if (a == b) {
					return (1); // found 1st byte
				} else if (a == c) {
					return (2); // found 2nd byte
				}
			} catch (EndOfBufferException e) {
				return (-1); // not found
			} catch (Exception ex) {
				error.append(ex);
				return (-1); // not found
			}
		}
	}

/**
    * Find '<'
    * @todo allow cdata section <![CDATA[...]]>
    * @return index where '<' found or -1 if not found
    */
	public int findStartElement() {
		if (find((byte) '<') != -1) {
			return (curPos - 1);
		}
		return (-1);// not found
	}

	/**
	 * Find '</' or '/>
	 * 
	 * @return -1 for not found | 1 for found '</' | 2 for found '/>'
	 */
	// static int i = -1;
	public int findEndElement(boolean both) {
		int opens = 1;
		// i++;
		// log.info("MMDEBUG[" + i +"]:" + ((new
		// String(this.buffer).substring(curPos, this.bufferSize))));
		try {
			while (true) {
				if (both == true) {
					// System.out.println("find both");
					int found = find((byte) '<', (byte) '/');
					if (found == -1) {
						return (-1);
					}
					byte b = read();
					// log.info("MMDEBUG:(1)found = " + found + " @ " + curPos +
					// ", next byte = " + (char)b + " opens=" + opens + " " +
					// ((new String(this.buffer).substring(curPos,
					// this.bufferSize))));
					if (found == 1) {
						// both = false;
						// found <
						// System.out.println("found '<'");
						if (b == '/') {
							// System.out.println("read = '/'");
							// curPos = curPos -1;
							if (--opens <= 0) {
								return (1); // found </ terminating element
							}
						} else {
							opens++;
						}
					} else if (found == 2) {
						// System.out.println("found '/'");
						// found '/', see if next byte is >
						if (b == '>') {
							// System.out.println("found '>'");
							// curPos = curPos -2;
							if (--opens <= 0) {
								return (2); // found '/>' terminating element
							}
						}
					}
				} else {
					if (find((byte) '<') == -1) {
						return (-1);
					}
					byte b = read();
					// log.info("MMDEBUG:(3)found = '<' @ " + curPos +
					// ", next byte = " + (char)b + " opens=" + opens + " " +
					// ((new String(this.buffer).substring(curPos,
					// this.bufferSize))));
					// found <
					if (b == '/') {
						// System.out.println("read = '/'");
						// curPos = curPos -1;
						if (--opens <= 0) {
							return (1); // found </ terminating element
						}
					} else {
						opens++;
						// System.out.println("missing '/'");
					}
				}
			}
		} catch (Exception ex) {
			error.append(ex);
			return (-1);
		}
	}

	/**
	 * skip over valid xml name characters for now these will be anything but
	 * whitespace | & < > / ;
	 */
	public void skipXMLName() {

		try {
			boolean done = false;
			byte b;
			while (true) {
				b = read();
				if (isXMLNameChar(b) == false) {
					curPos--;
					return;
				}
			}
		} catch (Exception ex) {
			error.append(ex);
		}
	}

	/**
	 * skip over non xml name characters for now these will be anything but
	 * whitespace | & < > / ;
	 */
	public void skipNonXMLName() {

		try {
			boolean done = false;
			byte b;
			while (true) {
				b = read();
				if (isXMLNameChar(b) == true) {
					curPos--;
					return;
				}
			}
		} catch (Exception ex) {
			error.append(ex);
		}
	}

	/**
	 * skip over white space including ' ' 0x0a and 0x0d
	 */
	public void skipWhiteSpace() {
		try {
			byte b;
			do {
				b = read();
			} while (b == ' ' || b == 0x09 || b == 0x0a || b == 0x0d);
			curPos--;
		} catch (Exception ex) {
			error.append(ex);
		}
	}

	/**
	 * skip over non white space including ' ' 0x0a and 0x0d
	 */
	public void skipNonWhiteSpace() {
		try {
			byte b;
			do {
				b = read();
			} while (isWhiteSpace(b) == false);
			curPos--;
		} catch (Exception ex) {
			error.append(ex);
		}
	}

	/**
	 * Compare xml from current position to the param byte[] to
	 * 
	 * @param to
	 *            is what we want to compare the xml buffer to.
	 * @return true if equals or false if not
	 */
	protected boolean equals(byte[] to) throws Exception {
		return (equals(to, false));
	}

	/**
	 * Compare xml from current position to the param byte[] to
	 * 
	 * @param to
	 *            is what we want to compare the xml buffer to.
	 * @param terminator
	 *            if set true will force a check for a whitespace or = character
	 *            at the end of the match.
	 * @return true if equals or false if not
	 */
	protected boolean equals(byte[] to, boolean terminator) throws Exception {
		for (int iLoop = 0; iLoop < to.length; iLoop++) {
			if (read() != to[iLoop]) {
				return (false);
			}

		}
		if (terminator == true) {
			setMark();
			byte b = read();
			resetToMark();
			if (isWhiteSpace(b) || b == '=' || b == '>' || b == '/') {
				return (true);
			} else {
				return (false);
			}
		}
		return (true);
	}

	public static boolean isWhiteSpace(byte b) {
		// TAB LF CR
		if (b == ' ' || b == 0x09 || b == 0x0a || b == 0x0d) {
			return (true);
		}
		return (false);
	}

	/**
	 * @param b
	 *            is the xml character we want to check
	 * @return true if character is a valid XML name character
	 */
	public boolean isXMLNameChar(byte b) {
		if (isWhiteSpace(b) == true) {
			return (false); // not a valid xml name
		}
		switch (b) {
		case '|':
		case '&':
		case '<':
		case '>':
		case '/':
		case ':':
		case ';':
		case '"':
		case '?':
		case '*':
		case '=':
			return (false);
		}
		return (true);
	}

	/**
	 * find a matching element from the current position in the xml, return it's
	 * starting position index
	 * 
	 * @return -1 of not found
	 */
	public int getNodeStart(byte[] nodeName) {
		try {
			int startPos;
			while (true) {
				startPos = findStartElement();

				if (startPos == -1) {
					// log.warn("Cant find node start for " + new String
					// (nodeName) + "\n" + this.toString());
					return (-1);
				}

				skipWhiteSpace();

				if (equals(nodeName, true) == true) {
					return (startPos);
				}
			}
		} catch (Exception ex) {
			error.append("Cant find node start for " + new String(nodeName)
					+ "\n" + this.toString() + "\n" + ex.getMessage());
			return (-1);
		}
	}

	/**
	 * find a matching element end from the current position in the xml, return
	 * it's ending position index including the >
	 * 
	 * @return -1 if not found
	 */
	public int getNodeEnd(byte[] nodeName) {
		/**
		 * missing catch for />
		 */
		boolean findBoth = true;
		try {
			while (true) {
				int found = findEndElement(findBoth);

				// System.out.println("findBoth set to false");
				findBoth = false;
				if (found == 1) // element ends as '</'
				{
					skipWhiteSpace();
					if (equals(nodeName, true)) {
						skipWhiteSpace();
						if (read() != '>') {
							// throw new Exception("terminating '>' for " + new
							// String(nodeName) + " missing");
							error.append("terminating '>' for "
									+ new String(nodeName) + " missing");
							return (-1);
						}
						return (curPos);
					}
				} else if (found == 2) // element ends as '/>'
				{
					return (curPos);
				} else // found == 0
				{
					// throw new Exception("end element for " + new
					// String(nodeName) + " not found");
					error.append("end element for " + new String(nodeName)
							+ " not found");
					return (-1);
				}
			}
		} catch (Exception ex) {
			// throw new Exception("end element for " + new String(nodeName) +
			// " not found", e);
			error.append("end element for '" + new String(nodeName)
					+ "' not found" + ":" + ex.getMessage());
			return (-1);
		}
	}

	/**
	 * find a matching element from the current position in the xml, return it's
	 * body from element start including < to element end.
	 */
	public XMLReader getNode(String nodeName) {
		byte[] name = nodeName.getBytes();
		int startPos = getNodeStart(name);
		if (startPos == -1) {
			return (null);
		}
		int endPos = getNodeEnd(name);
		if (endPos == -1) {
			return (null);
		}

		byte element[] = new byte[endPos - startPos];
		copy(buffer, element, startPos);
		// System.out.println("start = " + startPos + ", end = " + endPos);
		// System.out.println("element = " + new String(element));

		XMLReader xmlReader = new XMLReader(element);
		if (debug == true) {
			log.debug(JS.getCurrentMethodName_static() + " xml:" + xmlReader);
		}
		return (xmlReader);
	}

	public String getNodeAsString(String nodeName) {
		XMLReader xmlReader = getNode(nodeName);
		if (xmlReader != null) {
			return (xmlReader.toString());
		}
		return (null);
	}

	/**
	 * find a matching element from the current position in the xml, return it's
	 * body from element start including < to element end.
	 * 
	 * @return element or -1 of not found
	 */
	public byte[] getElement(byte[] elementName) {
		int startPos = getNodeStart(elementName);
		int endPos = getNodeEnd(elementName);
		if (startPos == -1 || endPos == -1) {
			return (null);
		}
		byte element[] = new byte[endPos - startPos];
		copy(buffer, element, startPos);
		// System.out.println("start = " + startPos + ", end = " + endPos);
		// System.out.println("element = " + new String(element));

		if (debug == true) {
			log.debug(JS.getCurrentMethodName_static() + " xml:"
					+ new String(element));
		}
		return (element);
	}

	/**
	 * find a matching element from the current position in the xml, return it's
	 * body from element start including < to element end.
	 * 
	 * @return element or -1 of not found
	 */
	public byte[] getElement(byte[] elementName, int index) {
		for (int iLoop = 0; iLoop < (index - 1); iLoop++) {
			getElement(elementName); // Skip these element
		}
		return (getElement(elementName));
	}

	/**
	 * find a matching element from the current position in the xml, return it's
	 * body from element start including < to element end.
	 * 
	 * @return element or -1 of not found
	 */
	public byte[] getInnerElement(byte[] elementName) {
		curPos++;
		int startPos = getNodeStart(elementName);
		int endPos = getNodeEnd(elementName);
		if (startPos == -1 || endPos == -1) {
			curPos--;
			return (null);
		}
		byte element[] = new byte[endPos - startPos];
		copy(buffer, element, startPos);
		// System.out.println("start = " + startPos + ", end = " + endPos);
		// System.out.println("element = " + new String(element));
		curPos--;
		if (debug == true) {
			log.debug(JS.getCurrentMethodName_static() + " xml:"
					+ new String(element));
		}
		return (element);
	}

	/**
	 * find a matching element from the current position in the xml, return it's
	 * body from element start including < to element end.
	 * 
	 * @return element or -1 of not found
	 */
	public byte[] getInnerElement(byte[] elementName, int index) {
		for (int iLoop = 0; iLoop < (index - 1); iLoop++) {
			getInnerElement(elementName); // Skip these element
		}
		return (getInnerElement(elementName));
	}

	public String getAttributeValue(String attributeName) {
		byte[] b = getAttributeValueAsByte(attributeName);
		if (b != null) {
			return (new String(b));
		}
		return (null);
	}

	/**
	 * Get attribute value starting at curPos. curPos is the ending position of
	 * the attribute name. From curPos we look for an '=' followed by '"'
	 * skipping any whitespace in between.
	 * 
	 * @return the attribute value if found or null if not found
	 */
	public String getAttributeValue() {
		try {
			StringBuffer sb = new StringBuffer();
			// skip to =
			// slip to "
			// grab all to next "

			skipWhiteSpace();
			byte b = read();
			if (b == '=') {
				// now skip to '"'
				skipWhiteSpace();
				b = read();
				if (b == '"') {
					// grab all to next '"'
					while (true) {
						b = read();
						if (b == '"') {
							// ok we done, we got all we wanted
							return (sb.toString());
						}
						sb.append((char) b);
					}
				}
			}
			// throw new
			// Exception("Error in xml, cannot find value for attribute");
			return (null);
		} catch (Exception ex) {
			return (null);
		}
	}

	public String getNextAttributeValue(String attributeName) {
		byte[] b = getAttributeValueAsByte(attributeName, false);
		if (b != null) {
			return (new String(b));
		}
		return (null);
	}

	public int getIntAttributeValue(String attributeName) throws Exception {
		byte[] b = getAttributeValueAsByte(attributeName);
		if (b != null) {
			return (Integer.parseInt(new String(b)));
		}
		throw (new Exception("Attribute not found for " + attributeName));
	}

	public byte[] getAttributeValueAsByte(String attributeName) {
		return (getAttributeValueAsByte(attributeName, true));
	}

	public byte[] getAttributeValueAsByte(String attributeName, boolean reset) {
		int rememberCurPos = curPos;
		byte[] attName = attributeName.getBytes();
		try {
			if (reset == true) {
				curPos = 0;
			}
			setMark();
			// We can only search until we find the first ending > of the
			// element.
			int startPos = curPos;
			int endPos = 0;
			byte b;

			try {
				while ((b = read()) != (byte) '>') {
					;
				}
				endPos = curPos;
				resetToMark();
			} catch (Exception ex) {
				// throw new
				// Exception("Exception:Element ending '>' not found ", e);
				error.append("Exception:Element ending '>' not found - "
						+ ex.getMessage());
			}

			// System.out.println("find attribute " + new String(attributeName)
			// + ", between " + startPos + " and " + endPos);

			boolean wasSpace = true;
			int matchLoop = 0;
			for (int iLoop = 0; iLoop < endPos - startPos; matchLoop++, iLoop++) {
				b = read();
				if (b != attName[matchLoop]) {
					// Log.getInstance().debug(JS.getCurrentMethodName_static()
					// + " don't got:" + matchLoop);
					matchLoop = -1;
					wasSpace = isWhiteSpace(b);
				}
				if (matchLoop == 0 && wasSpace == false) {
					matchLoop = -1;
				}
				if (matchLoop + 1 == attName.length) {
					// found AttributeName
					// now get Attribute Value
					skipWhiteSpace();
					if (curPos >= endPos) {
						return (null); // no attribute value
					}
					if (read() != '=') {
						// return(null); // no = for attribute value
						matchLoop = -1; // haven't found = so continue
						continue;
					}
					skipWhiteSpace();
					if (curPos >= endPos) {
						return (null); // no attribute value
					}
					if (read() != '"') {
						return (null); // no " for start of attribute value
					}
					startPos = curPos;
					while ((b = read()) != (byte) '"') {
						;
					}
					if (curPos > endPos) {
						return (null); // no ending " for attribute value
					}
					endPos = curPos - 1;
					byte buf[] = new byte[endPos - startPos];
					copy(buffer, buf, startPos);
					return (buf);
				}
			}
			curPos = rememberCurPos;
			return (null);
		} catch (Exception ex) {
			error.append(ex.getMessage());
			return (null);
			// throw e;
		}
	}

	public String getElementAttribute(String elementName,
			String elementAttributeName) {
		byte[] b = getElementAttributeAsByte(elementName, elementAttributeName);
		if (b != null) {
			return (new String(b));
		}
		return (null);
	}

	public int getIntElementAttribute(String elementName,
			String elementAttributeName) throws Exception {
		byte[] b = getElementAttributeAsByte(elementName, elementAttributeName);
		if (b != null) {
			return (Integer.parseInt(new String(b)));
		}
		throw (new Exception("Element Attribute not found for " + elementName
				+ ":" + elementAttributeName));
	}

	public byte[] getElementAttributeAsByte(String elementName,
			String elementAttributeName) {
		curPos = 0; // emm watch this.
		XMLReader xmlElement = getNode(elementName);
		// System.out.println("element:" + xmlElement.toString());
		if (xmlElement != null) {
			byte[] attValue = xmlElement
					.getAttributeValueAsByte(elementAttributeName);
			return (attValue);
		}
		return (null);
	}

	public String getContentAsString() {
		byte[] b = getContent();
		if (b != null) {
			return (new String(b));
		}
		return (null);
	}

	public String getContentAsString(String elementName) {
		byte[] b = getContent(elementName);
		if (b != null) {
			return (new String(b));
		}
		return (null);
	}

	public byte[] getContent() {
		// log.info("element:" + this.toString());
		int startPos = find((byte) '>');
		if (startPos != -1) {
			startPos++;
			int endPos = find((byte) '<');
			if (endPos != -1) {
				byte[] content = new byte[endPos - startPos];
				copy(buffer, content, startPos);
				return (content);
			}
		}
		return (null);
	}

	public byte[] getContent(String elementName) {
		byte[] end = { '<', '/' };
		XMLReader reader = getNode(elementName);
		// MM 5 Dec 2005, if element doesn't exist this will be null.
		if (reader == null) {
			return (null);
		}
		// log.info("content:" + reader.toString());
		// log.info("element:" + this.toString());
		// log.info("getContent '" + reader.toString() + "'");
		int startPos = reader.find((byte) '>');
		if (startPos != -1) {
			// log.info("startPos=" + startPos);
			startPos++;
			// @todo manage CDATA
			int endPos = reader.find(end);
			// log.info("endPos=" + endPos);
			if (endPos != -1) {
				// log.info("startPos=" + startPos + ", endPos=" + endPos);
				byte[] content = new byte[endPos - startPos];
				copy(reader.buffer, content, startPos);
				reader = new XMLReader(content);
				// log.info("\r\nreader1:" + reader.toString());
				reader = new XMLReader(reader.replaceCDATA());
				// log.info("\r\nreader2:" + reader.toString());
				// reader = new XMLReader(reader.replaceAmps());
				// log.info("\r\nreader3:" + reader.toString());
				// String s = new String(content);
				// log.info("\r\ns:" + s);
				// return(s.trim().getBytes());
				return (reader.toString().trim().getBytes());
			}
		}
		return (null);
	}

	/**
	 * This will replace any and all CDATA sections within the reader.
	 * 
	 * @return the new content with the CDATA sections replaced.
	 */
	public byte[] replaceCDATA() {
		ReadAll = true;
		// <![CDATA[...]]>
		byte[] cdataBegin = { '<', '!', '[', 'C', 'D', 'A', 'T', 'A', '[' };
		byte[] cdataEnd = { ']', ']', '>' };
		byte[] content = new byte[bufferSize];

		int contentIndex = 0;
		int startPos;
		int lastStartPos = 0;
		while (true) {
			lastStartPos = curPos;
			startPos = find(cdataBegin);
			if (startPos >= 0) {
				// copy any data before <![CDATA[
				if (lastStartPos < startPos) {
					copy(buffer, content, lastStartPos, contentIndex, startPos
							- lastStartPos);
					contentIndex += (startPos - lastStartPos);
				}
				int endPos = find(cdataEnd);
				if (endPos >= startPos) {
					copy(buffer, content, startPos + 9, contentIndex, endPos
							- (startPos + 9));
					contentIndex += (endPos - (startPos + 9));
				} else {
					// can't find cdata end so copy remainder of data to content
					copy(buffer, content, startPos - 8, contentIndex,
							bufferSize - (startPos - 8));
					contentIndex += bufferSize - (startPos - 8);
				}
			} else {
				// can't find any cdata copy remainder of data to content
				copy(buffer, content, lastStartPos, contentIndex, bufferSize
						- lastStartPos);
				contentIndex += bufferSize - lastStartPos;
				break;
			}
		}
		ReadAll = false;
		if (contentIndex != bufferSize) {
			byte newcontent[] = new byte[contentIndex];
			copy(content, newcontent, 0, 0, contentIndex);
			return (newcontent);
		}
		return (content);
	}

	/*
	 * public byte [] replaceCDATA(XMLReader reader) { reader.ReadAll = true; //
	 * <![CDATA[...]]> byte [] cdataBegin =
	 * {'<','!','[','C','D','A','T','A','['}; byte [] cdataEnd = {']',']','>'};
	 * byte [] content = new byte[reader.bufferSize];
	 * 
	 * int contentIndex = 0; int startPos; int lastStartPos = 0; while(true) {
	 * lastStartPos = reader.curPos; startPos = reader.find(cdataBegin); if
	 * (startPos >= 0) { // copy any data before <![CDATA[ if (lastStartPos <
	 * startPos) { copy(reader.buffer, content, lastStartPos, contentIndex,
	 * startPos-lastStartPos); contentIndex += (startPos - lastStartPos); } int
	 * endPos = reader.find(cdataEnd); if (endPos >= startPos) {
	 * copy(reader.buffer, content, startPos+9, contentIndex, endPos -
	 * (startPos+9)); contentIndex += (endPos-(startPos+9)); } else { // can't
	 * find cdata end so copy remainder of data to content copy(reader.buffer,
	 * content, startPos-8 , contentIndex, reader.bufferSize-(startPos-8) ); } }
	 * else { // can't find any cdata copy remainder of data to content
	 * copy(reader.buffer, content, lastStartPos, contentIndex,
	 * reader.bufferSize-lastStartPos); break; } } reader.ReadAll = false;
	 * return(content); }
	 */

	public byte[] replaceAmps() {
		byte[] content = new byte[bufferSize - curPos];
		int contentIndex = 0;
		byte b, b2;
		try {
			int lastCurPos = 0;
			while (true) {
				b = read();
				if (b == '&') // here we go
				{
					lastCurPos = curPos;
					try {
						while (true) {
							if (read() == 'a' && read() == 'm' && read() == 'p'
									&& read() == ';') {
								// got &amp;
								content[contentIndex++] = '&';
								break;
							}
							curPos = lastCurPos;
							if (read() == 'l' && read() == 't' && read() == ';') {
								// got &lt;
								content[contentIndex++] = '<';
								break;
							}
							curPos = lastCurPos;
							if (read() == 'g' && read() == 't' && read() == ';') {
								// got &gt;
								content[contentIndex++] = '>';
								break;
							}
							curPos = lastCurPos;
							content[contentIndex++] = b;
							break;
						}
					} catch (EndOfBufferException e) {
						// ignore cause we processed all data
						curPos = lastCurPos;
						content[contentIndex++] = b;
					}
				} else {
					content[contentIndex++] = b;
				}
			}
		} catch (EndOfBufferException e) {
			// ignore cause we processed all data
		} catch (Exception ex) {
			error.append("XMLReader.replaceAmps error " + ex.getMessage());
			return (null);
		}
		return (content);
	}

	/**
	 * Counts the number of nodes matching nodeName
	 * 
	 * @param nodeName
	 *            name of nodes we want to count
	 * @return number of nodes matching nodeName
	 */
	public int countNodes(String nodeName) {
		int mark = getCurPos();
		int iLoop = 0;
		while ((getNode(nodeName)) != null) {
			iLoop++;
		}
		setCurPos(mark);
		return (iLoop);
	}

	public static void main(String[] args) {
		log.info("XMLReader.main");

		StringBuffer sb = new StringBuffer();
		sb.append("<base>");
		sb.append("<root>1<![CDATA[the cdata section]]>");
		sb.append("<root>2");
		sb.append("<root value=\"here1\"><root>3.1</root></root>");
		sb.append("<root value=\"here1\"></root>");
		sb.append("<root value=\"here2\"/>");
		// sb.append("3</root>");
		sb.append("2</root>");
		sb.append("1</root>");
		sb.append("</base>");

		/*
		 * sb.append("<element value=\"none\">");
		 * sb.append("<><><>&lt;&gt;&amp;&77&&7");//sb.append(
		 * "+left outside cdata1-<![CDATA[inside cdata]]>-right outside cdata1+"
		 * );//sb.append(
		 * "+left outside cdata2-<![CDATA[if <= > >+]]>-right outside cdata2+");
		 * sb.append("</element>"); sb.append("</root>");
		 */

		XMLReader xmlReader = new XMLReader((new String(sb)).getBytes());
		try {
			byte[] b;
			b = xmlReader.getInnerElement("root".getBytes());
			log.info("1->" + new String(b));
			XMLReader r2 = new XMLReader(b);
			b = r2.getInnerElement("root".getBytes());
			log.info("2->" + new String(b));
			XMLReader r3 = new XMLReader(b);
			b = r3.getInnerElement("root".getBytes());
			log.info("3->" + new String(b));
			// b = xmlReader.getInnerElement("root".getBytes());
			// log.info("3->" + new String (b));
			System.exit(1);
		}
		/*
		 * try { byte [] b; b = xmlReader.getContent("element"); log.info("'" +
		 * new String(b) + "'"); }
		 */
		catch (Exception ex) {
			log.error("Exception:" + ex.getMessage());
			ex.printStackTrace();
		}
	}

}
