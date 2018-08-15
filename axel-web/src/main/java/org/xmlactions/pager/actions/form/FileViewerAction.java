
package org.xmlactions.pager.actions.form;


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
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlPre;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTr;


/**
 * Gets a file and presents it in a table, a row for each line, can have line numbers also.
 * 
 * @author mike
 */
public class FileViewerAction extends CommonFormFields implements FormDrawing, IStorageFormAction // extends StorageBaseFormAction
{

	private static final Logger LOG = LoggerFactory.getLogger(FileViewerAction.class);

	/** Name of file we want to load */
	private String file_name;

	/** Where the files pages are stored */
	private String path;
	
	private String ref;	// if this is set then get the content from the execContext.
	
	
	private boolean show_line_nos = true;
	private boolean escape_content = true;
	private boolean show_header = true;
	/** Will format xml content for presentation with line feeds and spaces */
	private boolean format_xml_content = false;
	
	private IExecContext execContext;

	public String execute(IExecContext execContext) throws Exception
	{
		validateAndSetup(execContext);

		String page = loadPage();
	
		page = buildPresentation(page);
		return page;
	}
	
	private String buildPresentation(String page) {
		
		if (StringUtils.isEmpty(page))
			return "";

		if (isFormat_xml_content() == true) {
			try {
				page = StringEscapeUtils.unescapeHtml(page);
				LOG.debug("formatting xml object:" + page);
				XMLObject xo = new XMLObject().mapXMLCharToXMLObject(page);
				page = xo.mapXMLObject2XML(xo, true);
				// page = xo.mapXMLObject2HTML();
			} catch (Exception ex) {
				// can't format this content so leave it as it is.
				LOG.error(ex.getMessage(), ex);
			}
		}
		
		if (isEscape_content() == true) {
			// html escape the content, so it appears as is on the browser page
			page = StringEscapeUtils.escapeHtml(page);
		}

		String [] rows = page.split("\n");
		Theme theme = getTheme(execContext);
		HtmlTable table = new HtmlTable(theme, ThemeConst.BORDER.toString());
		if (StringUtils.isNotEmpty(getWidth())) {
			table.setWidth(getWidth());
		}
		
		HtmlTr tr;
		if(isShow_header()) {
			tr = table.addTr();
			HtmlTd header = tr.addTd();
			header.setColspan("2");
			String name;
			if (getFile_name() != null) {
				name = getFile_name();
			} else {
				name = getRef();
			}
			header.setContent(buildHeader(theme, name, page.length(), rows.length).toString());
		}

		tr = table.addTr();
		tr.setVAlign("top");
		HtmlTd lineNosTd = null;
		HtmlPre lineNosPre = null;
		if (isShow_line_nos() == true) {
			lineNosTd = tr.addTd();
			lineNosTd.setAlign("right");
			//lineNosPre = lineNosTd.addPre();
			//lineNosPre.setClazz(theme.getValue(ThemeConst.FILE_VIEWER_LINE_NO.toString()));
			lineNosTd.setClazz(theme.getValue(ThemeConst.FILE_VIEWER_LINE_NO.toString()));
		}
		HtmlTd contentTd = tr.addTd();
		contentTd.setClazz(theme.getValue(ThemeConst.FILE_VIEWER_CONTENT.toString()));
		contentTd.setAlign("left");
		HtmlPre contentPre = contentTd.addPre();
		contentPre.setClazz(theme.getValue(ThemeConst.FILE_VIEWER_CONTENT.toString()));
		
		StringBuilder lineNosSb = new StringBuilder();
		StringBuilder contentSb = new StringBuilder();
		int lineNo = 1;
		for (String row : rows) {
			if (isShow_line_nos() == true) {
				lineNosSb.append("" + lineNo++);
				lineNosSb.append("<br/>");
			}
			contentSb.append(row);
			int lnIndex = row.indexOf("\n");
			int crIndex = row.indexOf("\r");
			if (crIndex < 0 ) {
				contentSb.append("<br/>");
			}
		}
		if (isShow_line_nos() == true) {
			lineNosTd.setContent(lineNosSb.toString());
		}
		
		contentPre.setContent(contentSb.toString());
		
		return table.toString();
	}
	
	public boolean isFormat_xml_content() {
		return format_xml_content;
	}

	public void setFormat_xml_content(boolean format_xml_content) {
		this.format_xml_content = format_xml_content;
	}

	private Html buildHeader(Theme theme, String title, int length, int rows) {
		HtmlTable table = new HtmlTable();
		table.setClazz(theme.getValue(ThemeConst.TABLE.toString()) + " " + theme.getValue(ThemeConst.FILE_VIEWER_HEADER.toString()));
		if (StringUtils.isNotEmpty(getWidth())) {
			table.setWidth(getWidth());
		} else {
			table.setWidth("100%");
		}
		HtmlTr tr = table.addTr();
		tr.setClazz(theme.getValue(ThemeConst.FILE_VIEWER_HEADER.toString()));
		HtmlTd td = tr.addTd();
		td.setWidth("5px");	// spacer at start
		td = tr.addTd();	
		td.setContent(title);
		td = tr.addTd();
		td.setContent("|");
		td.setWidth("10px");
		td.setAlign("center");
		td = tr.addTd();
		td.setContent("size:" + length);
		td = tr.addTd();
		td.setWidth("10px");
		td.setAlign("center");
		td.setContent("|");
		td = tr.addTd();
		td.setContent("lines:" + rows);
		td = tr.addTd();	
		td.setWidth("5px");//spacer at end
		return table;
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
					if (url != null) {
						is = url.openStream();
						// emm we may have an inputsteam
						char[] content = IOUtils.toCharArray(is);
						page = new String(content);
					} else {
						throw ex;
					}
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

	public void setShow_line_nos(boolean show_line_nos) {
		this.show_line_nos = show_line_nos;
	}

	public boolean isShow_line_nos() {
		return show_line_nos;
	}

	public void setEscape_content(boolean escape_content) {
		this.escape_content = escape_content;
	}

	public boolean isEscape_content() {
		return escape_content;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getRef() {
		return ref;
	}

	public void setShow_header(boolean show_header) {
		this.show_header = show_header;
	}

	public boolean isShow_header() {
		return show_header;
	}

}