package org.xmlactions.pager.actions.form;
/**
\page action_listcp Action List Control Panel
\anchor listcp

\tableofcontents

A list control panel action manages traversing information retrieved from a database.  It can contain a list
presentation, a search form and a traverse panel allowing forward and backward through the data.   

\section action_listcp_properties List Control Panel Properties
Action:<strong>listcp</strong>

<table border="0">
	<tr>
		<td colspan="2"><hr/></td>
	</tr>
	
	<tr>
	 	<td><strong>Elements</strong></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
	 		<strong>Description</strong>  
		</td>
	 </tr>
	<tr>
		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>\ref search<br/><small>- optional</small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			Specifies a search form used to filter the data returned by the list.  
		</td>
	</tr>
	<tr>
		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>\ref list<br/><small>- required</small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			Retrieves information from a database and presents it on screen.
		</td>
	</tr>
	<tr>
		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>\ref popup<br/><small>- optional</small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
           Specifies one or more popup windows that is populated from a server page uri.
		</td>
	</tr>
	<tr>
		<td colspan="2"><hr/></td>
	</tr>
	
	<tr>
	 	<td><strong>Attributes</strong></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
	 		<strong>Description</strong>  
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>\ref schema_pager_attributes_id<br/><small><i>- required</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			A unique id for this list control panel.  If the id is not unique and more than one list control panel is used on a page then there may be conflicts between both list control panels using the same id. 
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>href<br/><small><i>- required</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			The href that gets called when any of the control panel links are clicked.<br/>

			Additional details are added to the link such as the limit range, or page number.
		</td>
	</tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>ajax_load<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			We can use this to have the control panel load and repopulate the display using ajax in place of doing a complete page reload.<br/>

			If using this feature you should build the listcp element in its own file and import this into the page where it will be displayed.  Then set the href
            link to point to the listcp file so it will only bring back the information from the listcp and not the whole page.
		</td>
	</tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>\ref schema_pager_attributes_theme_name<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			The name of a theme that we want to apply to this control panel.<br/>
			
			This is an optional attribute.  If not set then the theme will be that set by the property 'default_theme_name'.<br/>
			i.e. default_theme_name=blue 
		</td>
	</tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>method<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			If used, apply this method value to the form method.<br/>
			Options are post or get.
		</td>
	</tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>\ref schema_pager_attributes_display_as<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			Displays a link as a 'link' or as a 'button'.<br/>
			
			The selected theme for a link = INPUT_LINK and for a button = INPUT_BUTTON.
		</td>
	</tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>\ref schema_pager_attributes_title<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			Title displayed for this form. If this is empty	no title will be displayed.
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>\ref schema_pager_attributes_visible<br/><small><i>- optional<br/>true | false</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			If set true (default) than the list is shown on screen. If set false then the list is hidden until the user selects the show button/link. 
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>\ref schema_pager_attributes_width<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			The display width of the form, when shown in a table. The width can also be an equation or a percentage - note the	xsd:string
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>\ref schema_pager_attributes_storage_config_ref<br/><small><i>- optional<br/></i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			If set true (default) than the list is shown on screen. If set false then the list is hidden until the user selects the show button/link. 
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>\ref schema_pager_attributes_table_name<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			If set true (default) than the list is shown on screen. If set false then the list is hidden until the user selects the show button/link. 
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td><strike>control_panel_position</strike><br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			 <strike>Set the position of the control panel.</strike> Now deprecated and not supported. 
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>script_before<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			This script will be called before the list is processed.<br/> 
			This is a javascript and can be a script call such as "alert('Hello World!!!')" or "var x = 1; x=x+1" 
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>script_after<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			This script will be called after the list is processed.<br/>
			This is a javascript and can be a script call such as "alert('Hello World!!!')" or "var x = 1; x=x+1;"
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

 </table>

 \section action_listcp_example List Control Panel Example

 An example listcp might look like
 \code{.xml}
   <db:listcp id="my_listcp"
              href="${project.app.name}/www/maps/google/my_listcp.axel"
              storage_config_ref="${persistence:storage}"
              table_name="my_table"
              title="My List Control Panel"
              ajax_load="true">
      <db:search id="my_listcp_search" visible="false">
         <db:field_list>
            <db:field name="my_table.user_name"/>
         </db:field_list>
      </db:search>
      <db:list id="my_list"
               rows="10"
               row_height="20px"
               order_by="my_list.user_name desc">
         <db:field_list>
            <db:field name="my_table.user_name"/>
            <db:field name="my_table.user_phone" tooltip="The users phone no." />
         </db:field_list>
      </db:list>               
   </db:listcp>
 \endcode


 \see
  \ref action_list<br/>
  \ref axel_actions_list<br/>
  \ref org_xmlactons_action_actions
 


*/

import java.util.ArrayList;



import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.number.IntegerUtils;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.FormDrawing;
import org.xmlactions.pager.actions.form.IForm;
import org.xmlactions.pager.actions.form.IStorageFormAction;
import org.xmlactions.pager.actions.form.PageConstant;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlA;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlEvents;
import org.xmlactions.pager.actions.form.templates.HtmlForm;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTh;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.config.PagerConstants;


/**
 * List Control Panel
 * <p>
 * Presents the control options for a list. These are next, prev, first, last
 * and page.
 * </p>
 * <p>
 * It may also control the sort order. Lets see how it goes first. We might make
 * a separate control for managing the sort order.
 * </p>
 * 
 * @author mike
 * 
 */
public class ListCP extends CommonFormFields implements IForm, FormDrawing, IStorageFormAction
{

    private static final int default_rows = 20;
	// public class ListCP extends StorageBaseFormAction implements IForm
	
	private String form_id;

	private static final Logger log = LoggerFactory.getLogger(ListCP.class);

	private String control_panel_position;

	private String script_before, script_after;

	//private int slider_size;

	/** May have a search */
	private Search search;

	/** Should contain an inner / child List */
	private List list;


	/** If set we make this the form.method, either post or get */
	private String method;

	/**
	 * We can use this to have the control panel load and repopulate the display using ajax
	 * in place of doing a complete page reload.
	 * <p>
	 * If using this feature you should build the listcp element in its own file
	 * and import this into the page where it will be displayed.  Then set the href
	 * link to point to the listcp file so it will only bring back the information
	 * from the listcp and not the whole page.
	 * </p>
	 */
	private boolean ajax_load = false;	// default value
	
	/**
	 * This will decide if the control panel should show the pagination navigator or not.
	 * If not then the developers provides their own.
	 */
	private boolean show_pagination = true;	// default value
	
	// ===
	// Locally Populated Variables
	// ===
	IExecContext execContext;

	/**
	 * Draw the List Control Panel
	 */
	public String execute(IExecContext execContext) throws Exception
	{

		this.execContext = execContext;
		validateParams(this.getClass().getName());

		// we dont try to validate the storage requirements here as they may be
		// set in child elements.
		// this.validateStorage("pager:listcp");

		String id = (String) execContext.get("request:" + PageConstant.ID);
		String page = (String) execContext.get("request:" + PageConstant.buildWithID(id, PageConstant.PAGE.toString()));
		page = "" + IntegerUtils.createInteger(page, 1);
		String rows = (String) execContext.get("request:" + PageConstant.buildWithID(id, PageConstant.ROWS.toString()));
		if (StringUtils.isEmpty(rows)) {
			if (getList().getRowsAsInt(execContext) >0) {
				rows =  "" + getList().getRowsAsInt(execContext);
			}
		}
		if (StringUtils.isEmpty(rows)) {
			rows = execContext.getString(IExecContext.DEFAULT_ROWS);
		}
		int numRowsPerPage = Integer.parseInt(rows);
		int currentPage = Integer.parseInt(page);
		int totalRows = 0;
		String search = "";
		if (getSearch() != null) {

			log.debug("Appending Search to CP. table_name:" + getSearch().getTable_name());

			search = getSearch().execute(execContext);
			totalRows = getList().getTotalRows();
		}
		String list = "";
		if (getList() != null) {

			log.debug("Appending List to CP. table_name:" + getList().getTable_name() + "\nrows:" + rows);

			getList().setRows("" + numRowsPerPage);
			getList().setPage(currentPage);
			list = getList().execute(execContext);
			totalRows = getList().getTotalRows();
		}
		

		Theme theme = execContext.getThemes().getTheme(getTheme_name(execContext));
		setTheme(theme);

		HtmlForm htmlForm = new HtmlForm();
		htmlForm.setOnSubmit(getSubmitString(getId(), getHref()));
		String _uri = getUri() == null ? "" : getUri();
		htmlForm.setOnKeyPress("if (isEnterKey(event) == true) { " + getSubmitString(getId(), _uri) + "}" + ";return true;");

		//HtmlInput input = new HtmlInput();
		//if (Link.DISPLAY_AS_BUTTON.equals(getDisplay_as())) {
		//	input.setClazz(theme.getValue(ThemeConst.INPUT_LINK_BUTTON.toString()));
		//} else if (Link.DISPLAY_AS_MENU.equals(getDisplay_as())) {
		//	input.setClazz(theme.getValue(ThemeConst.INPUT_LINK_MENU.toString()));
		//} else {
		//	input.setClazz(theme.getValue(ThemeConst.INPUT_LINK.toString()));
		//}
		//input.setType("submit");
		//input.setValue(PagerConstants.LANG_KEY_GO);
		//input.setStyle("position:absolute;left:-1000px");
		//htmlForm.addChild(input);

		HtmlTable htmlTable = startFrame(theme);
		htmlForm.addChild(htmlTable);

		htmlTable.setClazz(theme.getValue(ThemeConst.LISTCP_TABLE.toString()));

		HtmlTr tr;
		HtmlTd td;
		if (getSearch() != null) {
			tr = htmlTable.addTr();
			tr.setClazz(theme.getValue(ThemeConst.LISTCP_TR.toString()));
			td = tr.addTd();
			td.setClazz(theme.getValue(ThemeConst.LISTCP_TD.toString()));
			td.setContent(search);
		}

		tr = htmlTable.addTr();
		tr.setClazz(theme.getValue(ThemeConst.LISTCP_TR.toString()));
		td = tr.addTd();
		td.setClazz(theme.getValue(ThemeConst.LISTCP_TD.toString()));
		td.setContent(list);

		tr = htmlTable.addTr();
		tr.setClazz(theme.getValue(ThemeConst.LISTCP_TR.toString()));
		td = tr.addTd();
		td.setClazz(theme.getValue(ThemeConst.LISTCP_TD.toString()));
		// td.setContent(buildCP(execContext, theme, totalRows, numRowsPerPage, page, rows));

		if (isShow_pagination()) {
			tr = htmlTable.addTr();
			tr.setClazz(theme.getValue(ThemeConst.LISTCP_TR.toString()));
			td = tr.addTd();
			td.setAlign("center");
			td.setClazz(theme.getValue(ThemeConst.LISTCP_TD.toString()));
			td.addChild(buildCP(execContext, theme, totalRows, numRowsPerPage, page, rows));
		}

		if ((getLinks() != null && getLinks().size() > 0) || (getButtons() != null && getButtons().size() > 0)) {
			HtmlTable tb = td.addTable();
			// tb.setClazz(theme.getValue(ThemeConst.LISTCP_BORDER.toString()));
			tr = tb.addTr();
			tr.setClazz(theme.getValue(ThemeConst.LISTCP_TR.toString()));
			td = tr.addTd();
			td.setClazz(theme.getValue(ThemeConst.LISTCP_TD.toString()));
			for (Html htmlA : buildLinks(execContext, getLinks(), theme)) {
				td.addChild(htmlA);
			}
			for (HtmlInput htmlInput : buildButtons(execContext, getButtons(), theme)) {
				td.addChild(htmlInput);
			}
		}
        return htmlForm.toString();
	}

    /**
     * 
     * @param execContext
     * @param totalRows
     *            available in db
     * @param numRowsPerPage
     *            number of rows per page
     * @param page
     *            the current page number
     * @param rows
     *            that we now want per page
     * @return
     */
    private HtmlDiv buildCP(IExecContext execContext, Theme theme, int totalRows, int numRowsPerPage, String page,
            String rows) {
    	
        HtmlDiv htmlDiv = new HtmlDiv();
        String _uri = getUri() == null ? "" : getUri();
        htmlDiv.setOnKeyPress("if (isEnterKey(event) == true) { " + getSubmitString(getId(), _uri) + "}" + ";return true;");
        HtmlTable rootTable = new HtmlTable();
        htmlDiv.addChild(rootTable);
    	

        int totalPages = totalRows / numRowsPerPage;
        if (totalRows % numRowsPerPage > 0) {
            totalPages++;
        }
        int pageInt = NumberUtils.createInteger(page);

        // pageHolder is the element id for the page number input. we use this
        // to get the current value and change it.
        String pageHolder = PageConstant.buildWithID(getId(), PageConstant.PAGE.toString());

        /*
        <table border = "1" cellpadding = "0" cellspacing = "0">
           <tr class = "cp_bg">
              <td class = "cp_start" onclick = "submitLink(':w1.uhtml:services.uhtml')"></td>
              <td class = "cp_reverse" onclick = "submitLink(':w1.uhtml:services.uhtml')"></td>
              <td class = "cp_fg">
                 Page
                 <input type = "text" value = "1" size = "4"/>
                 / 100
              </td>
              <td class = "cp_forward" onclick = "submitLink(':w1.uhtml:services.uhtml')"></td>
              <td class = "cp_end" onclick = "submitLink(':w1.uhtml:services.uhtml')"></td>
              <td class = "cp_go" onclick = "submitLink(':w1.uhtml:services.uhtml')"></td>
           </tr>
        </table>
        */
        rootTable.setClazz(theme.getValue(ThemeConst.LISTCP_TABLE.toString()) + " "
                + theme.getValue(ThemeConst.MEDIA_BORDER.toString()));
        rootTable.setWidth("100%");
        HtmlTr tr = rootTable.addTr();
        tr.setClazz(theme.getValue(ThemeConst.MEDIA_BACKGROUND.toString()) + " "
                + theme.getValue(ThemeConst.MEDIA_FOREGROUND.toString()));
        HtmlTd td = tr.addTd();
        td.setAlign("center");

        HtmlTable table = td.addTable();
        table.setClazz(theme.getValue(ThemeConst.LISTCP_TABLE.toString()) + " "
                + theme.getValue(ThemeConst.MEDIA_BORDER.toString()));
        tr = table.addTr();
        tr.setClazz(theme.getValue(ThemeConst.MEDIA_FOREGROUND.toString()));

        // start/first
        td = tr.addTd();
        td.setClazz(theme.getValue(ThemeConst.MEDIA_START.toString()));
        td.setOnClick("page_first('" + pageHolder + "');" + getSubmitString(getId(), getHref()) + "return false;");
        td.setTitle(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_KEY_FIRST));

        // reverse/prev
        td = tr.addTd();
        td.setClazz(theme.getValue(ThemeConst.MEDIA_REVERSE.toString()));
        td.setOnClick("page_prev('" + pageHolder + "');" + getSubmitString(getId(), getHref()) + "return false;");
        td.setTitle(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_KEY_PREV));

        // page
        td = tr.addTd();
        td.setClazz(theme.getValue(ThemeConst.MEDIA_FOREGROUND.toString()));
        td.setContent("page:"
                + buildTextInput(theme,
                        "page",
                        PageConstant.buildWithID(getId(), PageConstant.PAGE.toString()),
                        page,
                        2,
                        8) + " / " + totalPages);

        // next/forward
        td = tr.addTd();
        td.setClazz(theme.getValue(ThemeConst.MEDIA_FORWARD.toString()));
        td.setOnClick("page_next('" + pageHolder + "','" + totalPages + "');" + getSubmitString(getId(), getHref()) + "return false;");
        td.setTitle(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_KEY_NEXT));

        // last/end
        td = tr.addTd();
        td.setClazz(theme.getValue(ThemeConst.MEDIA_END.toString()));
        td.setOnClick("page_last('" + pageHolder + "','" + totalPages + "');" + getSubmitString(getId(), getHref()) + "return false;");
        td.setTitle(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_KEY_LAST));

        // rows
        td = tr.addTd();
        td.setClazz(theme.getValue(ThemeConst.MEDIA_FOREGROUND.toString()));
        HtmlInput rowsHtmlInput = buildTextInput(theme,
                "rows",
                PageConstant.buildWithID(getId(), PageConstant.ROWS.toString()),
                rows,
                2,
                8);
        // rowsHtmlInput.setOnKeyPress("submitLinkWithParams('" + getId() +
        // "','" + getUri() + "');" + "return false;");
        td.setContent("rows:" + rowsHtmlInput);

        // go
        // last/end
        td = tr.addTd();
        td.setClazz(theme.getValue(ThemeConst.MEDIA_GO.toString()));
        td.setOnClick(getSubmitString(getId(), getHref()) + "return false;");
        td.setTitle(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_KEY_GO));

        // total records
        td = tr.addTd();
        td.setClazz(theme.getValue(ThemeConst.MEDIA_FOREGROUND.toString()));
        td.setContent("# " + totalRows);

        return htmlDiv;

    }

	public void setSearch(Search search)
	{

		this.search = search;
	}

	public Search getSearch()
	{

		return search;
	}

	public void setList(List list)
	{

		this.list = list;
	}

	public List getList()
	{

		return list;
	}

	public void setMethod(String method)
	{

		this.method = method;
	}

	public String getMethod()
	{

		return method == null ? "post" : method;
	}


	public IExecContext getExecContext()
	{

		return this.execContext;
	}

	public java.util.List<HtmlInput> getHiddenFields()
	{

		java.util.List<HtmlInput> inputs = new ArrayList<HtmlInput>();
		HtmlInput input = new HtmlInput();
		input.setType("hidden");
		input.setName(PageConstant.ID.toString());
		// input.setId(getId());
		input.setValue(getId());
		inputs.add(input);
		return inputs;
	}

	public void validateStorage(String errMsg)
	{

		// TODO Auto-generated method stub

	}

	public void validateParams(String errMsg)
	{

		//Validate.isTrue(getSlider_size() > 0, errMsg + " - Missing [slider_size] attribute");
		Validate.isTrue(getList() != null, errMsg + " - Missing [list] element");
	}

	public void setControl_panel_position(String control_panel_position)
	{

		this.control_panel_position = control_panel_position;
	}

	public String getControl_panel_position()
	{

		return control_panel_position;
	}

	//public void setSlider_size(int slider_size)
	//{
	//	this.slider_size = slider_size;
	//}

	//public int getSlider_size()
	//{
	//	return slider_size;
	//}

	public String getFormId() {
		return getForm_id();
	}

	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}

	public String getForm_id() {
		return form_id;
	}

    /**
     * Starts a drawing frame (table) , adding width, any hidden fields and
     * title.
     * 
     * @param form
     * @return
     */
    private HtmlTable startFrame(Theme theme) {

        HtmlTable table = new HtmlTable();
        // ===
        // build table with headers
        // ===
        table.setId(getId());
//        if (isVisible()) {
//            table.setClazz(theme.getValue(ThemeConst.LISTCP_BORDER.toString()));
//        } else {
//            table.setClazz(theme.getValue(ThemeConst.LISTCP_BORDER.toString(), "hide"));
//        }
        if (isVisible()) {
            table.setClazz("hide");
        }
        if (StringUtils.isNotEmpty(getWidth())) {
            table.setWidth(getWidth());
        }

        // ===
        // Add Any Hidden Fields
        // ===
        for (HtmlInput input : getHiddenFields()) {
            table.addChild(input);
        }
        // ===
        // Add Frame Title
        // ===
        if (StringUtils.isNotEmpty(getTitle())) {
            HtmlTr tr = table.addTr();
            tr.setClazz(theme.getValue(ThemeConst.LISTCP_TITLE.toString()));
            if (getSearch() != null) {
                String searchId = getSearch().getId();
                String displayId = searchId + ".display";
    			tr.setOnClick("show('" + displayId + "'); toggleShowHide('" + searchId + "');");
                HtmlTd td = tr.addTd();
                // td.setClazz(theme.getValue(ThemeConst.LISTCP_TITLE.toString()));
                HtmlTable innerTable = td.addTable();
                innerTable.setWidth("100%");// push the image across to the right.
                innerTable.setClazz(theme.getValue(ThemeConst.TABLE.toString()));
                HtmlTr innerTr = innerTable.addTr();
                HtmlTh th = innerTr.addTh();
                th.setWidth("100%");// push the image across to the right.
                //th.setClazz(theme.getValue(ThemeConst.LISTCP_TITLE.toString()));
                th.setContent(getTitle());
                th = innerTr.addTh();
                th.setAlign("right");
                th.setClazz(theme.getValue(ThemeConst.WIN_SEARCH.toString()) + " " + theme.getValue(ThemeConst.FLOAT_RIGHT.toString()));
            } else {
                HtmlTh th = tr.addTh();
            	// th.setClazz(theme.getValue(ThemeConst.LISTCP_TITLE.toString()));
            	th.setContent(getTitle());
            }
        }
        return table;

    }

	public boolean isAjax_load() {
		return ajax_load;
	}

	public void setAjax_load(boolean ajax_load) {
		this.ajax_load = ajax_load;
	}
	
	private String getSubmitString(String id, String href) {
        if (isAjax_load() == false) {
        	return "submitLinkWithParams('" + id + "','" + href + "');" + "return false;";
        } else {
        	return "populateIdFromAjaxLoad('" + id + "','" + href + "', captureInputsFromElement('" + id + "')," + getScript_before() + "," + getScript_after() + ");" + "return false;";
        }
	}

	public String getScript_before() {
		return script_before;
	}

	public void setScript_before(String script_before) {
		this.script_before = script_before;
	}

	public String getScript_after() {
		return script_after;
	}

	public void setScript_after(String script_after) {
		this.script_after = script_after;
	}

	public boolean isShow_pagination() {
		return show_pagination;
	}

	public void setShow_pagination(boolean show_pagination) {
		this.show_pagination = show_pagination;
	}

}
