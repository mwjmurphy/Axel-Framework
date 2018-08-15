
package org.xmlactions.pager.actions.highlighter;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.common.xml.XMLAttribute;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.FormDrawing;
import org.xmlactions.pager.actions.form.IStorageFormAction;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlPre;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTr;


/**
 * Builds a syntax highlighter for xml, xsd and html.
 * 
 * @author mike
 */
public class HighlighterAction extends CommonFormFields implements FormDrawing, IStorageFormAction // extends StorageBaseFormAction
{

	private static final Logger LOG = LoggerFactory.getLogger(HighlighterAction.class);

	/** Name of file we want to load */
	private String file_name;

	/** Where the files pages are stored */
	private String path;
	
	private String ref;	// if this is set then get the content from the execContext.
	
	private String key;	// if this is set then store the output into the execContext with this key.
	
	private boolean attribute_per_line = false;

	private IExecContext execContext;

	private String elementNameHighlight;;
	private String elementAttributeHighlight;
	private String elementContentHighlight;
	

	public String execute(IExecContext execContext) throws Exception
	{
		validateAndSetup(execContext);

		String page = loadPage();
	
		page = buildPresentation(page);
		if (getKey() != null) {
			execContext.put(getKey(), page);
			return "";
		} else {
			return page;
		}
	}

	private String buildPresentation(String page) {
		
		if (StringUtils.isEmpty(page))
			return "";
		
		Theme theme = getTheme(execContext);
		elementNameHighlight = "<span class=\"" + theme.getValue(ThemeConst.HIGHLIGHT_XML_ELEMENT.toString()) + "\">";
		elementAttributeHighlight = "<span class=\"" + theme.getValue(ThemeConst.HIGHLIGHT_XML_ATTRIBUTE.toString()) + "\">";
		elementContentHighlight = "<span class=\"" + theme.getValue(ThemeConst.HIGHLIGHT_XML_CONTENT.toString()) + "\">";
		
		StringBuilder sb = new StringBuilder();
		XMLObject xo = new XMLObject().mapXMLCharToXMLObject(page);
		
		highlight(sb, "", xo);
		
		return sb.toString();
	}
	
	private String
		LN = "\n",
		CRLN = "\r\n",
		LT = "&lt;",
		GT = "&gt;",
		SLASH="/",
		END_SPAN = "</span>",
		INDENT_SPACES = "   ",
		SPACE = " ";
	
	private boolean hasContent(XMLObject xo) {
		if (xo.getContent() == null) {
			return false;
		}
		return ! StringUtils.isWhitespace(xo.getContent());
	}

	private String cleanContent(String content) {
		// replace any <!CDATA[[ with &lt;!CDATA[[
		// replace any ]]> with ]]&gt;
		content = content.replace("<!", "&lt;!");
		content = content.replace("]]>", "]]&gt");
		return content;
	}
	private void highlight(StringBuilder sb, String indent, XMLObject xo) {
		
		boolean attributesPerLine = isAttribute_per_line();
		sb.append(indent);
		highlightElementNameStart(sb, xo);

		if (xo.getAttributeValue("xmlns") != null) {
			attributesPerLine = true;
		}
		for (XMLAttribute child : xo.getAttributes()) {
			highlightElementAttribute(sb, child, indent+INDENT_SPACES, attributesPerLine);
		}
		if (xo.getChildCount() > 0 || hasContent(xo) ) {
			sb.append(GT);
		}
		for (XMLObject child : xo.getChildren()) {
			sb.append(LN);
			highlight(sb, indent+INDENT_SPACES, child);
		}
		if (hasContent(xo)) {
			String content = cleanContent(xo.getContent().trim());
			sb.append(content);
		}
		highlightElementNameEnd(sb, xo, indent);
	}
	
	private void highlightElementNameStart(StringBuilder sb, XMLObject xo) {
		sb.append(LT);
		sb.append(elementNameHighlight);
		sb.append(xo.getName());
		sb.append(END_SPAN);
	}
	private void highlightElementNameEnd(StringBuilder sb, XMLObject xo, String indent) {
		if (xo.getChildCount() > 0 || hasContent(xo)) {
			if (!hasContent(xo)) {
				sb.append(CRLN);
				sb.append(indent);
			}
			sb.append(LT);
			sb.append(SLASH);
			sb.append(elementNameHighlight);
			sb.append(xo.getElementName());
			sb.append(END_SPAN);
			sb.append(GT);
		} else {
			sb.append(SLASH);
			sb.append(GT);
		}
	}
	
	private void highlightElementAttribute(StringBuilder sb, XMLAttribute xo, String indent, boolean attributesPerLine) {
		if (attributesPerLine) {
			sb.append(CRLN);
			sb.append(indent);
		} else {
			sb.append(SPACE);
		}
		sb.append(elementAttributeHighlight);
		sb.append(xo.getKey());
		sb.append(END_SPAN);
		sb.append("=");
		sb.append(elementContentHighlight);
		sb.append("\"");
		sb.append(xo.getValueAsString());
		sb.append("\"");
		sb.append(END_SPAN);
	}
	
	private String loadPage() throws IOException {
		String page = null;
		if (getRef() != null) {
			page = execContext.getString(getRef());
		} else {
			if (path == null) {
				path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
			}
			String fileName = StrSubstitutor.replace(getFile_name(), execContext);
			if (path == null) {
				path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
			}
			if (path != null) {
				path = StrSubstitutor.replace(getPath(), execContext);
			}
			try {
				page = Action.loadPage(path, fileName);
			} catch (IllegalArgumentException ex) {
				// try a load a page from a url
				InputStream is = null;
				try {
					URL url = new URL(fileName);
					if (url == null) {
						throw ex;
					}
					is = url.openStream();
					// emm we may have an inputsteam
					char[] content = IOUtils.toCharArray(is);
					page = new String(content);
				} catch (Exception ignoreme) {
					throw ex;
				}
			}
		}
		return page;
	}

	public void validateAndSetup(IExecContext execContext)
	{

		this.execContext = execContext;
		if (StringUtils.isEmpty(getFile_name()) && StringUtils.isEmpty(getRef())) {
			throw new IllegalArgumentException("Missing file_name or ref attribute. One of these must be set for the viewer to know where to get the viewing content from.");
		}
		setTheme(execContext.getThemes().getTheme(getTheme_name(execContext)));
	}


	public IExecContext getExecContext()
	{

		return this.execContext;
	}

	public List<HtmlInput> getHiddenFields()
	{

		java.util.List<HtmlInput> inputs = new ArrayList<HtmlInput>();

		return inputs;
	}


	public void validateStorage(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getRef() {
		return ref;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setAttribute_per_line(boolean attribute_per_line) {
		this.attribute_per_line = attribute_per_line;
	}

	public boolean isAttribute_per_line() {
		return attribute_per_line;
	}

}