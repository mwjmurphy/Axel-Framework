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

package org.xmlactions.common.xml;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.system.JS;


/**
 * 
 * @author MichaelMurphy
 * 
 */
public class XMLReaderChar {
	private static Logger log = LoggerFactory.getLogger(XMLReader.class);

	public char buffer[];
	public int bufferSize = 0;

	public boolean debug = false;
	int curPos = 0;
	int markPos = 0;
	protected boolean ReadAll = false; // if true include cdata and comments in
	// read()
	StringBuffer error;

	public XMLReaderChar() {
		super();
		error = new StringBuffer();
		curPos = 0;
		markPos = 0;
		bufferSize = 0;
		buffer = null;
	}

	public XMLReaderChar(char[] buffer) {
		super();
		error = new StringBuffer();
		if (buffer == null || buffer.length <= 0) {
			org.apache.commons.lang.Validate.notEmpty("", "buffer is empty");
			bufferSize = 0;
			log.warn("XMLReaderChar: buffer is null or zero length"
					+ JS.getCurrentStack_static());
			error.append("XMLReaderChar: buffer is null or zero length ("
					+ JS.getCurrentMethodName_static() + ") ");
			return;
		}
		curPos = 0;
		markPos = 0;
		this.buffer = buffer;
		this.bufferSize = buffer.length;
		/*
		 * this.buffer = new char[buffer.length]; this.bufferSize =
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

	private void copy(char[] from, char[] to) {
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

	private void copy(char[] from, char[] to, int start) {
		int iLoop = 0;
		try {
			for (iLoop = 0; iLoop < to.length; iLoop++) {
				char b = from[iLoop + start];
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

	private void copy(char[] from, char[] to, int start, int offset, int size) {
		int iLoop = 0;
		try {
			for (iLoop = 0; iLoop < size; iLoop++) {
				char b = from[iLoop + start];
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
	 * readChar is the only place where we read a char from the buffer
	 */
	private char readChar(int index) throws EndOfBufferException // end of
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
	 * Read a char skipping any <code><!-- comments --></code> or any
	 * <code><![CDATA[...]]></code>
	 * 
	 * @return char read or null
	 */
	public char read() throws EndOfBufferException, BadXMLException, Exception {
		while (true) {
			char b = readChar(curPos++);
			if (b == '<' && ReadAll == false) {
				// now see if we skip a comment
				if (readChar(curPos) == '!' && readChar(curPos + 1) == '-'
						&& readChar(curPos + 2) == '-') {
					// log.info("skipping comment");
					int errorStart = curPos;
					// Start of comment. now move to end of comment "-->"
					// System.out.println("start comment");
					curPos = curPos + 3;
					boolean endComment = false;
					while (endComment == false) {
						try {
							for (int iLoop = curPos; endComment == false; iLoop++) {
								if (readChar(iLoop) == '-'
										&& readChar(iLoop + 1) == '-'
										&& readChar(iLoop + 2) == '>') {
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
							throw new BadXMLException("bad XML, no end of comment for comment starting at position " + errorStart);
						}
					}
				}
				// now see if we skip a CDATA
				else if (readChar(curPos) == '!' && readChar(curPos + 1) == '['
						&& readChar(curPos + 2) == 'C'
						&& readChar(curPos + 3) == 'D'
						&& readChar(curPos + 4) == 'A'
						&& readChar(curPos + 5) == 'T'
						&& readChar(curPos + 6) == 'A'
						&& readChar(curPos + 7) == '[') {
					// log.info("skipping comment");
					int errorStart = curPos;
					// Start of comment. now move to end of comment "-->"
					// System.out.println("start comment");
					curPos = curPos + 8;
					boolean endCDATA = false;
					while (endCDATA == false) {
						try {
							for (int iLoop = curPos; endCDATA == false; iLoop++) {
								if (readChar(iLoop) == ']'
										&& readChar(iLoop + 1) == ']'
										&& readChar(iLoop + 2) == '>') {
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
							throw new BadXMLException("bad XML, no end of CDATA for CDATA starting at position " + errorStart);
						}
					}
				} else if (readChar(curPos) == '!' || readChar(curPos) == '?') {
					// skip a declaration or doctype
					curPos++;
					continue;
				} else {
					return (b);
				}
			} else {
				return (b);
			}
		}
	}

	/**
	 * find any char value
	 * 
	 * @param b
	 *            = char value to find
	 * @return index where char value found or -1 if not found
	 */
	private int find(char b) {
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
	 * find any char value
	 * 
	 * @param b
	 *            = char value to find
	 * @return index where char value found or -1 if not found
	 */
	private int find(char[] b) {
		try {
			// System.out.print("find:" + (char)b + ", curPos = " + curPos);
			for (int iLoop = 0; iLoop < b.length; iLoop++) {
				if (read() != b[iLoop]) {
					iLoop = -1; // back to beginning
				}
			}
			// System.out.println(" ::: found:" + (char)b + ", curPos = " + curPos);
			return (curPos - b.length);
		} catch (EndOfBufferException e) {
			return (-1); // not found
		} catch (Exception ex) {
			error.append(ex.getMessage());
			return (-1); // not found
		}
	}

	private int find(char b, char c) {
		// System.out.print("find:" + (char)b + ", curPos = " + curPos);
		boolean found = false;
		char a;
		while (true) {
			try {
				a = read();
				// log.debug("DELETE ME I AM A PERFORMANCE ISSUE: find:" + a);
				if (a == b) {
					return (1); // found 1st char
				} else if (a == c) {
					return (2); // found 2nd char
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
		if (find('<') != -1) {
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
					// log.debug("DELETE ME I AM A PERFORMANCE ISSUE:opens=" + opens + ":find start curPos:" + curPos + ":[" + buffer[curPos-1] + buffer[curPos] + buffer[curPos+1] + buffer[curPos+2] + ']');

					int found = find('<', '/');
					if (found == -1) {
						return (-1);
					}
					char b = read();
					// log.info("MMDEBUG:(1)found = " + found + " @ " + curPos +
					// ", next char = " + (char)b + " opens=" + opens + " " +
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
							if (isAlphaChar(b)) {
								// log.debug("DELETE ME I AM A PERFORMANCE ISSUE:opens=" + opens + ": find isAlphaChar('" + b + "')");	// curPos:" + curPos + ":[" + buffer[curPos-1] + buffer[curPos] + buffer[curPos+1] + buffer[curPos+2] + ']');
								opens++;
							} else {
								// log.debug("DELETE ME I AM A PERFORMANCE ISSUE:opens=" + opens + ":! isAlphaChar('" + b + "')");	// curPos:" + curPos + ":[" + buffer[curPos-1] + buffer[curPos] + buffer[curPos+1] + buffer[curPos+2] + ']');
							}
						}
					} else if (found == 2) {
						// System.out.println("found '/'");
						// found '/', see if next char is >
						if (b == '>') {
							// System.out.println("found '>'");
							// curPos = curPos -2;
							if (--opens <= 0) {
								return (2); // found '/>' terminating element
							}
						}
					}
				} else {
					if (find('<') == -1) {
						return (-1);
					}
					char b = read();
					// log.info("MMDEBUG:(3)found = '<' @ " + curPos +
					// ", next char = " + (char)b + " opens=" + opens + " " +
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
			char b;
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
			char b;
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
			char b;
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
			char b;
			do {
				b = read();
			} while (isWhiteSpace(b) == false);
			curPos--;
		} catch (Exception ex) {
			error.append(ex);
		}
	}

	/**
	 * Compare xml from current position to the param char[] to
	 * 
	 * @param to
	 *            is what we want to compare the xml buffer to.
	 * @return true if equals or false if not
	 */
	protected boolean equals(char[] to) throws Exception {
		return (equals(to, false));
	}

	/**
	 * Compare xml from current position to the param char[] to
	 * 
	 * @param to
	 *            is what we want to compare the xml buffer to.
	 * @param terminator
	 *            if set true will force a check for a whitespace or = character
	 *            at the end of the match.
	 * @return true if equals or false if not
	 */
	protected boolean equals(char[] to, boolean terminator) throws Exception {
		for (int iLoop = 0; iLoop < to.length; iLoop++) {
			if (read() != to[iLoop]) {
				return (false);
			}

		}
		if (terminator == true) {
			setMark();
			char b = read();
			resetToMark();
			if (isWhiteSpace(b) || b == '=' || b == '>' || b == '/') {
				return (true);
			} else {
				return (false);
			}
		}
		return (true);
	}

	/**
	 * Compare xml from current position to the param char[] to
	 * 
	 * @param to
	 *            is what we want to compare the xml buffer to.
	 * @param terminator
	 *            if set true will force a check for a whitespace or = character
	 *            at the end of the match.
	 * @return true if equals or false if not
	 */
	protected boolean nsEquals(char[] to, boolean terminator) throws Exception {
		if (to.length==0) {
			return true;	// process none namespace'd elements
		}
		for (int iLoop = 0; iLoop < to.length; iLoop++) {
			if (read() != to[iLoop]) {
				return (false);
			}

		}
		if (terminator == true) {
			setMark();
			char b = read();
			resetToMark();
			if (b == ':') {
				return (true);
			} else {
				return (false);
			}
		}
		return (true);
	}

	/**
	 * Checks if a char is a whitespace.
	 * <p>
	 * whitespace = ' ' || 0x09 || 0x0a || 0x0d
	 * </p>
	 * @param b - the char to check
	 * @return true if char is a whitespace else false if not
	 */
	public boolean isWhiteSpace(char b) {
		// TAB LF CR
		if (b == ' ' || b == 0x09 || b == 0x0a || b == 0x0d) {
			return (true);
		}
		return (false);
	}

	/**
	 * Checks if a char is valid alpha or underscore 
	 * <p>
	 * alpha = '_' || a-z|| A-Z
	 * </p>
	 * @param b - the char to check
	 * @return true if char is a alpha else false if not
	 */
	public boolean isAlphaChar(char b) {
		// _ or A-Z or a_z
		if	(b == '_' ||
			(b >= 'a' && b <= 'z') ||
			(b >= 'A' && b <= '|')) {
			return (true);
		}
		return (false);
	}

	/**
	 * @param b
	 *            is the xml character we want to check
	 * @return true if character is a valid XML name character
	 */
	public boolean isXMLNameChar(char b) {
		if (isWhiteSpace(b) == true) {
			return (false); // not a valid xml name
		}
		switch (b) {
		case '|':
		case '&':
		case '<':
		case '>':
		case '/':
			// case ':':
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
	 * find a matching NameSpace from the current position in the xml, return
	 * it's starting position index
	 * 
	 * @return -1 of not found
	 */
	public int getNSStart(char[] nameSpace) {
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

				if (nsEquals(nameSpace, true) == true) {
					// skip past ":name"
					char c;
					while ((c = read()) != '>') {
						if (isWhiteSpace(c)) {
							break;
						}
					}
					return (startPos);
				}
			}
		} catch (Exception ex) {
			error.append("Cant find node start for nameSpace ["
					+ new String(nameSpace) + "]\n" + this.toString() + "\n"
					+ ex.getMessage());
			return (-1);
		}
	}

	/**
	 * find a matching NameSpace from the current position in the xml, return
	 * it's starting position index
	 * 
	 * @return -1 of not found
	 */
	public int getNSStart(char[][] nameSpaces) {
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

				int pos = curPos;
				for (char [] nameSpace : nameSpaces) {
					curPos = pos;
					if (nsEquals(nameSpace, true) == true) {
						// skip past ":name"
						char c;
						while ((c = read()) != '>') {
							if (isWhiteSpace(c)) {
								break;
							}
						}
						return (startPos);
					}
				}
			}
		} catch (Exception ex) {
			StringBuilder nsList = new StringBuilder();
			for (char [] nameSpace:nameSpaces) {
				nsList.append(new String(nameSpace));
			}
			error.append("Cant find node start for nameSpaces ["
					+ nsList.toString() + "]\n" + this.toString() + "\n"
					+ ex.getMessage());
			return (-1);
		}
	}

	/**
	 * find a matching element from the current position in the xml, return it's
	 * starting position index
	 * 
	 * @return -1 of not found
	 */
	public int getNodeStart(char[] nodeName) {
		try {
			int startPos;
			while (true) {
				startPos = findStartElement();

				if (startPos == -1) {
					// log.warn("Cant find node start for " + new String
					// (nodeName) + "\n" + this.toString());
					return (-1);
				}

				// skipWhiteSpace();
				
				// log.debug("DELETE ME I AM A PERFORMANCE ISSUE:" + buffer[curPos] + buffer[curPos+1]);

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
	public int getNodeEnd(char[] nodeName) {
		/**
		 * missing catch for />
		 */
		boolean findBoth = true;
		try {
			while (true) {
				int found = findEndElement(findBoth);

				// System.out.println("findBoth set to false");
				findBoth = false;
				if (found == 1) { // element ends as '</'
					skipWhiteSpace();
					if (equals(nodeName, true)) {
						skipWhiteSpace();
						if (read() != '>') {
							// throw new Exception("terminating '>' for " + new String(nodeName) + " missing");
							error.append("terminating '>' for "	+ new String(nodeName) + " missing");
							return (-1);
						}
						return (curPos);
					}
				} else if (found == 2) { // element ends as '/>'
					return (curPos);
				} else { // found == 0
					// throw new Exception("end element for " + new String(nodeName) + " not found");
					error.append("end element for " + new String(nodeName) + " not found");
					return (-1);
				}
			}
		} catch (Exception ex) {
			// throw new Exception("end element for " + new String(nodeName) + " not found", e);
			error.append("end element for '" + new String(nodeName) 	+ "' not found" + ":" + ex.getMessage());
			return (-1);
		}
	}

	/**
	 * find a matching element end from the current position in the xml, return
	 * it's ending position index including the >
	 * 
	 * @return -1 if not found
	 */
	public int getNSEnd(char[] nodeName) {
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
					if (nsEquals(nodeName, true)) {
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
	public XMLReaderChar getNode(String nodeName) {
		char[] name = nodeName.toCharArray();
		int startPos = getNodeStart(name);
		if (startPos == -1) {
			return (null);
		}
		int endPos = getNodeEnd(name);
		if (endPos == -1) {
			return (null);
		}

		char element[] = new char[endPos - startPos];
		copy(buffer, element, startPos);
		// System.out.println("start = " + startPos + ", end = " + endPos);
		// System.out.println("element = " + new String(element));

		XMLReaderChar xmlReader = new XMLReaderChar(element);
		if (debug == true) {
			log.debug(JS.getCurrentMethodName_static() + " xml:" + xmlReader);
		}
		return (xmlReader);
	}

	/**
	 * find a matching element from the current position in the xml, return it's
	 * body from element start including < to element end.
	 */
	public XMLReaderChar getNextNodeWithNS(String nameSpace) {
		char[] name = nameSpace.toCharArray();
		int startPos = getNSStart(name);
		char element[] = new char[curPos - startPos - 2];
		copy(buffer, element, startPos + 1);
		if (startPos == -1) {
			return (null);
		}
		int endPos = getNodeEnd(element);
		if (endPos == -1) {
			return (null);
		}

		element = new char[endPos - startPos];
		copy(buffer, element, startPos);
		// log.debug("found ns:node[" + new String(element) + "] size=" +
		// element.length);
		// System.out.println("start = " + startPos + ", end = " + endPos);
		// System.out.println("element = " + new String(element));

		XMLReaderChar xmlReader = new XMLReaderChar(element);
		if (debug == true) {
			log.debug(JS.getCurrentMethodName_static() + " xml:" + xmlReader);
		}
		return (xmlReader);
	}

	/**
	 * find a matching element from the current position in the xml, return it's
	 * body from element start including < to element end.
	 */
	public XMLParserChar getNextNodeWithNS(char nameSpaces[][]) {
		int startPos = getNSStart(nameSpaces);
		char element[] = new char[curPos - startPos - 2];
		copy(buffer, element, startPos + 1);
		if (startPos == -1) {
			return (null);
		}
		int endPos = getNodeEnd(element);
		if (endPos == -1) {
			return (null);
		}

		element = new char[endPos - startPos];
		copy(buffer, element, startPos);
		// log.debug("found ns:node[" + new String(element) + "] size=" +
		// element.length);
		// System.out.println("start = " + startPos + ", end = " + endPos);
		// System.out.println("element = " + new String(element));

		XMLParserChar xmlReader = new XMLParserChar(element);
		if (debug == true) {
			log.debug(JS.getCurrentMethodName_static() + " xml:" + xmlReader);
		}
		return (xmlReader);
	}

	public String getNodeAsString(String nodeName) {
		XMLReaderChar xmlReader = getNode(nodeName);
		if (xmlReader != null) {
			return (xmlReader.toString());
		}
		return (null);
	}

	public String getNextNodeWithNSAsString(String nameSpace) {
		XMLReaderChar xmlReader = getNextNodeWithNS(nameSpace);
		if (xmlReader != null) {
			return xmlReader.toString();
		}
		return (null);
	}

	public String getNextNodeWithNSAsString(char [][] nameSpaces) {
		XMLReaderChar xmlReader = getNextNodeWithNS(nameSpaces);
		if (xmlReader != null) {
			return xmlReader.toString();
		}
		return (null);
	}

	/**
	 * find a matching element from the current position in the xml, return it's
	 * body from element start including < to element end.
	 * 
	 * @return element or -1 of not found
	 */
	public char[] getElement(char[] elementName) {
		int startPos = getNodeStart(elementName);
		int endPos = getNodeEnd(elementName);
		if (startPos == -1 || endPos == -1) {
			return (null);
		}
		char element[] = new char[endPos - startPos];
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
	public char[] getElement(char[] elementName, int index) {
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
	public char[] getInnerElement(char[] elementName) {
		curPos++;
		int startPos = getNodeStart(elementName);
		int endPos = getNodeEnd(elementName);
		if (startPos == -1 || endPos == -1) {
			curPos--;
			return (null);
		}
		char element[] = new char[endPos - startPos];
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
	public char[] getInnerElement(char[] elementName, int index) {
		for (int iLoop = 0; iLoop < (index - 1); iLoop++) {
			getInnerElement(elementName); // Skip these element
		}
		return (getInnerElement(elementName));
	}

	public String getAttributeValue(String attributeName) {
		char[] b = getAttributeValueAsChar(attributeName);
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
			char b = read();
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
						sb.append(b);
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
		char[] b = getAttributeValueAsChar(attributeName, false);
		if (b != null) {
			return (new String(b));
		}
		return (null);
	}

	public int getIntAttributeValue(String attributeName) throws Exception {
		char[] b = getAttributeValueAsChar(attributeName);
		if (b != null) {
			return (Integer.parseInt(new String(b)));
		}
		throw (new Exception("Attribute not found for " + attributeName));
	}

	public char[] getAttributeValueAsChar(String attributeName) {
		return (getAttributeValueAsChar(attributeName, true));
	}

	public char[] getAttributeValueAsChar(String attributeName, boolean reset) {
		int rememberCurPos = curPos;
		char[] attName = attributeName.toCharArray();
		try {
			if (reset == true) {
				curPos = 0;
			}
			setMark();
			// We can only search until we find the first ending > of the
			// element.
			int startPos = curPos;
			int endPos = 0;
			char b;

			try {
				while ((b = read()) != '>') {
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
					while ((b = read()) != '"') {
						;
					}
					if (curPos > endPos) {
						return (null); // no ending " for attribute value
					}
					endPos = curPos - 1;
					char buf[] = new char[endPos - startPos];
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
		char[] b = getElementAttributeAsChar(elementName, elementAttributeName);
		if (b != null) {
			return (new String(b));
		}
		return (null);
	}

	public int getIntElementAttribute(String elementName,
			String elementAttributeName) throws Exception {
		char[] b = getElementAttributeAsChar(elementName, elementAttributeName);
		if (b != null) {
			return (Integer.parseInt(new String(b)));
		}
		throw (new Exception("Element Attribute not found for " + elementName
				+ ":" + elementAttributeName));
	}

	public char[] getElementAttributeAsChar(String elementName,
			String elementAttributeName) {
		curPos = 0; // emm watch this.
		XMLReaderChar xmlElement = getNode(elementName);
		// System.out.println("element:" + xmlElement.toString());
		if (xmlElement != null) {
			char[] attValue = xmlElement
					.getAttributeValueAsChar(elementAttributeName);
			return (attValue);
		}
		return (null);
	}

	public String getContentAsString() {
		char[] b = getContent();
		if (b != null) {
			return (new String(b));
		}
		return (null);
	}

	public String getContentAsString(String elementName) {
		char[] b = getContent(elementName);
		if (b != null) {
			return (new String(b));
		}
		return (null);
	}

	/**
	 * This will get all the content contained within an element. It's not great
	 * as it will only go as far as the start of the 1st element contained inside
	 * the content.
	 * i.e. <h>this is part of the content<p>this is p content</p> this part is lost</h>
	 * Only the "this is part of the content" is retrieved.
	 * 
	 * @return the contained content else null if there isn't any.
	 */
	public char[] getContent() {
		if (buffer[buffer.length-2] == '/') {
			// element terminates as "/>" so has no content
			return null;
		}
		// log.info("element:" + this.toString());
		int startPos = find('>');
		if (startPos != -1) {
			startPos++;
			boolean contentFound = false; 
			while(contentFound==false) {
				int endPos = find('<');
				if (endPos != -1) {
					// ignore spurious < as there not end or start of elements
					if (isAlphaChar(buffer[endPos+1]) || buffer[endPos+1] == '/') {	// <element - an element name or </
						char[] content = new char[endPos - startPos];
						copy(buffer, content, startPos);
						return (content);
					}
				} else {
					break;
				}
			}
		}
		return (null);
	}

	public char[] getContent(String elementName) {
		char[] end = { '<', '/' };
		XMLReaderChar reader = getNode(elementName);
		// MM 5 Dec 2005, if element doesn't exist this will be null.
		if (reader == null) {
			return (null);
		}
		// log.info("content:" + reader.toString());
		// log.info("element:" + this.toString());
		// log.info("getContent '" + reader.toString() + "'");
		int startPos = reader.find('>');
		if (startPos != -1) {
			// log.info("startPos=" + startPos);
			startPos++;
			// @todo manage CDATA
			int endPos = reader.find(end);
			// log.info("endPos=" + endPos);
			if (endPos != -1) {
				// log.info("startPos=" + startPos + ", endPos=" + endPos);
				char[] content = new char[endPos - startPos];
				copy(reader.buffer, content, startPos);
				reader = new XMLReaderChar(content);
				// log.info("\r\nreader1:" + reader.toString());
				reader = new XMLReaderChar(reader.replaceCDATA());
				// log.info("\r\nreader2:" + reader.toString());
				// reader = new XMLReader(reader.replaceAmps());
				// log.info("\r\nreader3:" + reader.toString());
				// String s = new String(content);
				// log.info("\r\ns:" + s);
				// return(s.trim().getChars());
				return (reader.buffer);
			}
		}
		return (null);
	}

	/**
	 * This will replace any and all CDATA sections within the reader.
	 * 
	 * @return the new content with the CDATA sections replaced.
	 */
	public char[] replaceCDATA() {
		ReadAll = true;
		// <![CDATA[...]]>
		char[] cdataBegin = { '<', '!', '[', 'C', 'D', 'A', 'T', 'A', '[' };
		char[] cdataEnd = { ']', ']', '>' };
		char[] content = new char[bufferSize];

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
			char newcontent[] = new char[contentIndex];
			copy(content, newcontent, 0, 0, contentIndex);
			return (newcontent);
		}
		return (content);
	}

	/*
	 * public char [] replaceCDATA(XMLReader reader) { reader.ReadAll = true; //
	 * <![CDATA[...]]> char [] cdataBegin =
	 * {'<','!','[','C','D','A','T','A','['}; char [] cdataEnd = {']',']','>'};
	 * char [] content = new char[reader.bufferSize];
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

	public char[] replaceAmps() {
		char[] content = new char[bufferSize - curPos];
		int contentIndex = 0;
		char b, b2;
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

		XMLReaderChar xmlReader = new XMLReaderChar((new String(sb))
				.toCharArray());
		try {
			char[] b;
			b = xmlReader.getInnerElement("root".toCharArray());
			log.info("1->" + new String(b));
			XMLReaderChar r2 = new XMLReaderChar(b);
			b = r2.getInnerElement("root".toCharArray());
			log.info("2->" + new String(b));
			XMLReaderChar r3 = new XMLReaderChar(b);
			b = r3.getInnerElement("root".toCharArray());
			log.info("3->" + new String(b));
			// b = xmlReader.getInnerElement("root".getChars());
			// log.info("3->" + new String (b));
			System.exit(1);
		}
		/*
		 * try { char [] b; b = xmlReader.getContent("element"); log.info("'" +
		 * new String(b) + "'"); }
		 */
		catch (Exception ex) {
			log.error("Exception:" + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public String toString() {
		return new String(buffer);
	}

	/**
	 * Gets the inner content (if any) between the node start and node end
	 * <p>
	 * Will not alter position markers
	 * </p>
	 * 
	 * @param content
	 *            with start and end node
	 * 
	 * @return the inner content(if any) or an empty string if none
	 */
	public static String getInnerContent(String content) {
		int start = content.indexOf('>');
		if (start < 0 || start >= content.length()) {
			return "";
		}
		int end = content.lastIndexOf('<');
		if (end <= 0) {
			return "";
		}
		return (content.substring(start + 1, end));
	}

	public static int getElementNameLen(String content) {
		int start = content.indexOf('>');
		if (start < 0 || start >= content.length()) {
			return 0;
		}
		return (start + 1);
	}
}
