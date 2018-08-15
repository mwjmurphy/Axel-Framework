package org.xmlactions.pager.actions.form;
/**
\page action_search Action Search
\anchor search

\tableofcontents

Search is part of the list control panel and filters the results of a query using the selected fields. The results of the search filtering is displayed by the \ref list 

\section action_search_properties Search Properties
Action:<strong>search</strong>

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
	 	<td>\ref schema_pager_db_actions_field_list<br/><small>- optional</small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			Specifies a list of data source fields.  
		</td>
	</tr>
	<tr>
		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>\ref schema_pager_actions_link<br/><small>- optional</small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			Specifies a href link.
		</td>
	</tr>
	<tr>
		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>\ref schema_pager_actions_button<br/><small>- optional</small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
           Specifies an input button.
		</td>
	</tr>
	<tr>
		<td colspan="2"><hr/></td>
	</tr>
	
	<tr>
	 	<td>\ref schema_pager_actions_popup<br/><small>- optional</small></td>
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
			A unique id for this searcg.  If the id is not unique and more than one searcg is used on a page then there may be conflicts between both searches using the same id. 
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>\ref schema_pager_attributes_title<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			If this is used then the title will appear when the search form is displayed.  If it's not set then no title will be shown.
		</td>
	</tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>\ref schema_pager_attributes_visible<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			If set to "false" then the form when be displayed when the Search option is selected.
		</td>
	</tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>\ref schema_pager_attributes_table_name<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			Is optional and may be provided by a parent action.<br/>
			If it's not set here or in a parent then an error will be displayed.  
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
	 	<td>\ref schema_pager_attributes_label_position<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			Selects how to display the label and field.
		</td>
	</tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

 </table>


 \section action_search_example Search Example

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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.common.util.RioStringUtils;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.DBConfigException;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.pager.actions.CodeAction;
import org.xmlactions.pager.actions.SelfDraw;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.FormDrawing;
import org.xmlactions.pager.actions.form.IStorageFormAction;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.drawing.DrawFormFields;
import org.xmlactions.pager.actions.form.populator.CodePopulator;
import org.xmlactions.pager.actions.form.populator.Populator;
import org.xmlactions.pager.actions.form.populator.SqlPopulator;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlEnum;
import org.xmlactions.pager.actions.form.templates.HtmlForm;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlSelect;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTh;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.config.PagerConstants;
import org.xmlactions.pager.drawing.html.DrawDBHTMLHelper;
import org.xmlactions.pager.drawing.html.DrawHtmlField;
import org.xmlactions.web.PagerWebConst;

/**
 * Builds a record search presentation.
 * <p>
 * The search fields come from the database schema.
 * </p>
 * <p>
 * parameters / attributes will build the html attributes needed for display.
 * </p>
 * 
 * @author mike
 * 
 * 
 */
public class Search extends DrawFormFields implements FormDrawing, IStorageFormAction // extends StorageBaseFormAction
{

	private static final Logger LOG = LoggerFactory.getLogger(Search.class);

	private String row_map_name = "row";
	
	private FieldList field_list;

	private IExecContext execContext;

	private StorageConfig storageConfig;

	private Database database;

	private Table table;

	public String execute(IExecContext execContext) throws Exception {

		this.execContext = execContext;

		this.validateStorage("pager:search");

        storageConfig = (StorageConfig) execContext.get(getStorage_config_ref(execContext));
        Validate.notNull(storageConfig, "No [" + StorageConfig.class.getName() + "] found in ExecContext ["
                + getStorage_config_ref(execContext) + "]");
		StorageContainer storageContainer = storageConfig.getStorageContainer();
		Storage storage = storageContainer.getStorage();

		database = storage.getDatabase(storageConfig.getDatabaseName());

		table = database.getTable(getTable_name());

		Theme theme = execContext.getThemes().getTheme(getTheme_name(execContext));
		setTheme(theme);

		Html output;
		try {
			output = buildSearchPresentation(execContext, database, table, theme);

		} catch (DBConfigException ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
			// Validate.isTrue(false, ex.getMessage());
		}
		return output.toString();
	}

	private Html buildSearchPresentation(IExecContext execContext,
			Database database, Table table, Theme theme)
			throws DBConfigException, Exception {
		StorageContainer storageContainer = storageConfig.getStorageContainer();
		Storage storage = storageContainer.getStorage();
		DBConnector dbConnector = storageConfig.getDbConnector();
		Connection connection;
		try {
			// connection = dataSource.create().getConnection();
			connection = dbConnector.getConnection(execContext);
		} catch (Throwable e) {
			throw new IllegalArgumentException(
					"Cannot get database connection using storage_config_ref:"
                    + getStorage_config_ref(execContext), e);
		}
        Validate.notNull(connection, "No Connection for data_source [" + getStorage_config_ref(execContext)
                + "] in ExecContext");

		String displayId = getId() + ".display";
        HtmlForm htmlForm = new HtmlForm();
        String _uri = getUri() == null ? "" : getUri();
        htmlForm.setOnSubmit("submitLinkWithParams('" + getId() + "','" + _uri + "');" + "return false;");
		HtmlTable root = startFrame(theme, displayId);
		htmlForm.addChild(root);
		
		HtmlInput formSubmit = new HtmlInput();
        formSubmit.setClazz(theme.getValue(ThemeConst.INPUT_TEXT.toString()));
        formSubmit.setType("submit");
        formSubmit.setValue(PagerConstants.LANG_KEY_GO);
        formSubmit.setStyle("position:absolute;left:-1000px");
        htmlForm.addChild(formSubmit);

		HtmlTr tr = root.addTr(theme, ThemeConst.SEARCH_TR);
		HtmlTd td = tr.addTd(theme, ThemeConst.SEARCH_TD);
		td.setColspan("2");
		HtmlDiv div = td.addDiv(theme);

		div.setId(displayId);
		if (isVisible()) {
			div.setStyle("display:block");
		} else {
			div.setStyle("display:none");
		}

		HtmlTable htmlTable = div.addTable(theme, ThemeConst.SEARCH_BORDER);

		try {
			for (BaseAction baseAction : getField_list().getFields()) {
				if (baseAction instanceof Field) {
					Field field = (Field) baseAction;
					LOG.debug("field:" + field.getName());
					buildSearchPresentationFromField(connection, storage,
							field, theme, htmlTable, null);
					if (field.getControllableFields().size() > 0) {
						// get parent table and pk. use this to build where
						// clause for children.
						String parentTableAndPkName = Edit.getTableAndPkName(
								database, getTable_name(), field.getName());
						for (Field child : field.getControllableFields()) {
							LOG.debug("field.child:" + child.getName());
							tr.addChild(buildHiddenInput(
									ClientParamNames.FIELD_NAME_MAP_ENTRY,
									child.getName()));
							buildSearchPresentationFromField(connection,
									storage, child, theme, htmlTable,
									parentTableAndPkName);
						}
					}
				} else if (baseAction instanceof SelfDraw) {
					HtmlTr [] trs = displaySelfDraw(execContext, (SelfDraw) baseAction, theme);
					for (Html html : trs) {
						htmlTable.addChild(html);
					}
				}
			}
		} catch (DBConfigException ex) {
			throw new IllegalArgumentException("Unable to build query for ["
					+ getTable_name() + "]\n" + ex.getMessage(), ex);
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		} finally {
			DBConnector.closeQuietly(connection);
		}
		if (getLinks().size() > 0) {
			root.addChild(DrawDBHTMLHelper.buildLinksAndButtons(execContext,
					this, theme, "right"));
		}
		return htmlForm;
	}

	private Html[] buildPresentation(IExecContext execContext, CommonFormFields formField, CommonStorageField storageField, String value, Theme theme) {
		return displayForSearch(execContext, getRow_map_name(), formField, storageField, value, theme);
	}

	private void buildSearchPresentationFromField(Connection connection,
			Storage storage, Field field, Theme theme, HtmlTable htmlTable,
			String parentTableAndPkName) throws DBConfigException, Exception {

		CommonStorageField storageField;

		if (Table.isTableAndFieldName(field.getName())) {
			storageField = database.getStorageField(field.getName());
		} else {
			storageField = table.getFieldFromTableAndFieldName(Table
					.buildTableAndFieldName(getTable_name(), field.getName()));
		}
		String keyPreviousValue = PagerWebConst
				.buildRequestKey(field.getName());
		keyPreviousValue = RioStringUtils.convertToString(execContext
				.get(keyPreviousValue));

		Html[] trs;

		if (field.getPopulator() != null) {
			trs = processPopulator(field.getPopulator(), connection, field,
					storageField, keyPreviousValue, theme, parentTableAndPkName);
		} else {
        	trs = buildPresentation(execContext, field, storageField, keyPreviousValue, theme);
			//trs = buildPresentation(table, storageField, keyPreviousValue,
			//		theme, field.getName());
		}
		for (Html html : trs) {
			htmlTable.addChild(html);
		}
	}

	private HtmlTr[] processPopulator(Populator populator,
			Connection connection, Field field,
			CommonStorageField storageField, String keyPreviousValue,
			Theme theme, String parentTableAndPkName) throws Exception {
		if (populator instanceof SqlPopulator) {
			return processSqlPopulator((SqlPopulator) populator, connection,
					field, storageField, keyPreviousValue, theme,
					parentTableAndPkName);
		} else if (populator instanceof CodePopulator) {
			return processCodePopulator((CodePopulator) populator, connection,
					field, storageField, keyPreviousValue, theme,
					parentTableAndPkName);
		} else {
			throw new IllegalArgumentException(
					"The type attribute has not been set in the ListPopulator for field ["
							+ field.getName() + "]");
		}
	}

	private HtmlTr[] processCodePopulator(CodePopulator codePopulator,
			Connection connection, Field field,
			CommonStorageField storageField, String keyPreviousValue,
			Theme theme, String parentTableAndPkName) throws Exception {

		HtmlTr[] trs;

		if (codePopulator.getCode() == null) {
			throw new IllegalArgumentException(
					"The code_populator does not have the required code element set for field ["
							+ field.getName() + "]");
		}
		CodeAction codeAction = codePopulator.getCode();
		String valueAttributeName = null;
		String idAttributeName = null;
		String selectIdentifier = null;

		Table table = (Table) storageField.getParent();
		selectIdentifier = Table.buildTableAndFieldName(table.getAlias(),
				storageField.getName());

		idAttributeName = valueAttributeName;
		String selectedValue = PagerWebConst.buildRequestKey(selectIdentifier);
		selectedValue = RioStringUtils.convertToString(execContext
				.get(selectedValue));
		String XOXml = codeAction.execute(execContext);
		XMLObject xo = new XMLObject().mapXMLCharToXMLObject(XOXml);
		HtmlSelect htmlSelect = SelectForm.buildSelectionPresentation(
				execContext, storageField, getLabel_position(),
				keyPreviousValue, theme, xo, idAttributeName,
				valueAttributeName, selectIdentifier, selectedValue);

		if (field.getControllableFields() != null
				&& field.getControllableFields().size() > 0) {
			String childId = Edit.getChildPkId(database, table, field
					.getControllableFields().get(0));
			htmlSelect.put(HtmlEnum.onchange.getAttributeName(),
					"populateSelect('" + childId + "', buildSelect('" + getId()
							+ "'));");
		}

		trs = DrawHtmlField.displayForSelect(execContext, storageField,
				getLabel_position(), theme, htmlSelect);
		if (trs.length > 0) {
			if (StringUtils.isNotEmpty(parentTableAndPkName)) {
				HtmlInput htmlInput = buildHiddenInput(
						ClientParamNames.PARENT_TABLE_AND_PK_NAME,
						parentTableAndPkName);
				trs[0].addChild(htmlInput);
			}
		}
		return trs;
	}

	private HtmlTr[] processSqlPopulator(SqlPopulator sqlPopulator,
			Connection connection, Field field,
			CommonStorageField storageField, String keyPreviousValue,
			Theme theme, String parentTableAndPkName) throws DBSQLException {

        String xml = sqlPopulator.executePopulator(execContext, storageConfig, database, connection);
        XMLObject xo = new XMLObject().mapXMLCharToXMLObject(xml);

		HtmlTr[] trs;
		String sqlQuery = null;
		String valueAttributeName = null;
		String idAttributeName = null;
		String selectIdentifier = field.getName();

		idAttributeName = valueAttributeName;

		String selectedValue = PagerWebConst.buildRequestKey(selectIdentifier);
        selectedValue = RioStringUtils.convertToString(execContext.get(selectedValue));

        if (LOG.isDebugEnabled()) {
            LOG.debug("\nkeyPreviousValue:" + keyPreviousValue + " sqlQuery:" + sqlQuery + "\nxo:"
                    + xo.mapXMLObject2XML(xo));
        }
		HtmlSelect htmlSelect = SelectForm.buildSelectionPresentation(
				execContext, storageField, getLabel_position(),
				keyPreviousValue, theme, xo, idAttributeName,
				valueAttributeName, selectIdentifier, selectedValue);

		if (field.getControllableFields() != null
				&& field.getControllableFields().size() > 0) {
			String childId = Edit.getChildPkId(database, table, field
					.getControllableFields().get(0));
			htmlSelect.put(HtmlEnum.onchange.getAttributeName(),
					"populateSelect('" + childId + "', buildSelect('" + getId()
							+ "'));");
		}

		trs = DrawHtmlField.displayForSelect(execContext, storageField,
				getLabel_position(), theme, htmlSelect);
		if (trs.length > 0) {
			if (StringUtils.isNotEmpty(parentTableAndPkName)) {
				HtmlInput htmlInput = buildHiddenInput(
						ClientParamNames.PARENT_TABLE_AND_PK_NAME,
						parentTableAndPkName);
				trs[0].addChild(htmlInput);
			}
		}
		return trs;
	}



	/**
	 * get request value for parentTableAndPkName if they have already been set
	 * in the incoming request.
	 * 
	 * @param parentTableAndPkName
	 * @return value for parentTableAndPkName if found
	 * 
	 *         private String getParentWhereClauseValue(IExecContext
	 *         execContext, String parentTableAndPkName) {
	 * 
	 *         String value = null;
	 * 
	 *         value = (String)execContext.get(PagerWebConst.REQUEST + ":" +
	 *         parentTableAndPkName); if (StringUtils.isEmpty(value)) { value =
	 *         (String)execContext.get(PagerWebConst.REQUEST + ":" +
	 *         Table.getFieldName(parentTableAndPkName)); } LOG.debug("REQUEST:"
	 *         + parentTableAndPkName + " value:" + value);
	 * 
	 *         return value;
	 * 
	 *         }
	 */

	public String toString() {

		return "search storage_config_ref [" + getStorage_config_ref()
				+ "] table_name [" + getTable_name() + "]";
	}

	public void setField_list(FieldList field_list) {

		this.field_list = field_list;
	}

	public FieldList getField_list() {

		if (field_list == null) {
			return new FieldList();
		}
		return field_list;
	}

	public IExecContext getExecContext() {

		return this.execContext;
	}

	public List<HtmlInput> getHiddenFields() {

		java.util.List<HtmlInput> inputs = new ArrayList<HtmlInput>();

		// hidden fields
        inputs.add(buildHiddenInput(ClientParamNames.STORAGE_CONFIG_REF, getStorage_config_ref(execContext)));
		inputs.add(buildHiddenInput(ClientParamNames.TABLE_NAME_MAP_ENTRY,
				table.getName()));
		return inputs;
	}

	public void validateStorage(String errMsg) {

        Validate.notEmpty(getStorage_config_ref(execContext),
				ClientParamNames.STORAGE_CONFIG_REF + " has not been set - "
						+ errMsg);
		Validate.notEmpty(getTable_name(),
				ClientParamNames.TABLE_NAME_MAP_ENTRY + " has not been set - "
						+ errMsg);
	}

	/*
	 * private BuildSelect _buildSelect(String tableName, String pkName, String
	 * valueName) { BuildSelect buildSelect = new BuildSelect();
	 * buildSelect.setStorage(storageConfig.getStorageContainer().getStorage());
	 * buildSelect.setTableName(tableName); List<String> tableAndFieldNames =
	 * new ArrayList<String>();
	 * 
	 * tableAndFieldNames.add(Table.buildTableAndFieldName(tableName, pkName));
	 * tableAndFieldNames.add(Table.buildTableAndFieldName(tableName,
	 * valueName)); buildSelect.setTableAndFieldNames(tableAndFieldNames);
	 * 
	 * return buildSelect;
	 * 
	 * }
	 */

	/**
	 * Gets the PK from the table referenced by fieldName
	 * 
	 * @param fieldName
	 * @return
	 */
	private String getTableAndPkName(String fieldName) {
		String tableAndFieldName = null;
		if (Table.isTableAndFieldName(fieldName)) {
			String tableName = Table.getTableName(fieldName);
			Table table = database.getTable(tableName);
			PK pk = table.getPk();
			if (pk == null) {
				throw new IllegalArgumentException(
						"Table ["
								+ tableName
								+ "] does not have a PK. A PK is required when using the Select option from a Search using the field ["
								+ fieldName + "]");
			}
			tableAndFieldName = Table.buildTableAndFieldName(tableName,
					pk.getName());
		} else {
			Table table = database.getTable(getTable_name());
			PK pk = table.getPk();
			if (pk == null) {
				throw new IllegalArgumentException(
						"Table ["
								+ getTable_name()
								+ "] does not have a PK. A PK is required when using the Select option from a Search using the field ["
								+ fieldName + "]");
			}
			tableAndFieldName = Table.buildTableAndFieldName(getTable_name(),
					pk.getName());
		}
		return tableAndFieldName;
	}

	public static PK getPK(String fieldName, String tableName,
			Database database, Table table) {
		PK pk;
		if (Table.isTableAndFieldName(fieldName)) {
			tableName = Table.getTableName(fieldName);
			pk = database.getTable(tableName).getPk();
		} else {
			pk = table.getPk();
		}
		if (pk == null) {
			throw new IllegalArgumentException(
					"Table ["
							+ tableName
							+ "] does not have a PK. A PK is required when using the Select option from a Search using the field ["
							+ fieldName + "]");
		}
		return pk;
	}

	/**
	 * Starts a drawing frame (table) , adding width, any hidden fields and
	 * title.
	 * 
	 * @param form
	 * @return
	 */
	private HtmlTable startFrame(Theme theme, String displayId) {

		HtmlTable table = new HtmlTable();
		// ===
		// build table with headers
		// ===
		table.setId(getId());
		
		table.setClazz(theme.getValue(ThemeConst.SEARCH_BORDER.toString()));
		//if (isVisible()) {
		//	table.setClazz(theme.getValue(ThemeConst.SEARCH_BORDER.toString()));
		//} else {
		//	table.setClazz(theme.getValue(ThemeConst.SEARCH_BORDER.toString(),"hide"));
		//}
		
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
			String minmaxClassName = theme.getValue(ThemeConst.WIN_MINMAX.toString());

			HtmlTr tr = table.addTr();
			tr.setOnClick("toggleShowHide('" + displayId + "');");
			HtmlTh th = tr.addTh();
			tr.setClazz(theme.getValue(ThemeConst.SEARCH_BORDER.toString()) + " " + theme.getValue(ThemeConst.SEARCH_TITLE.toString()));
			th.setContent(getTitle());
			th = tr.addTh();
			th.setClazz(minmaxClassName);
			th.setAlign("right");

		}
		return table;

	}
	
    private HtmlTr[] displaySelfDraw(IExecContext execContext, SelfDraw selfDraw, Theme theme) {

		HtmlTr[] trs;
		trs = new HtmlTr[1];
		HtmlTr tr = new HtmlTr(theme);
		trs[0] = tr;
		
		HtmlTd td = tr.addTd(theme);
		td.setContent(selfDraw.drawHeader(execContext));
		
		td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
		
		td = tr.addTd(theme);
		td.setAlign("left");
		td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
		td.addChild(selfDraw.drawHtml(execContext));
		return trs;
	}

	public String getRow_map_name() {
		return row_map_name;
	}

	public void setRow_map_name(String row_map_name) {
		this.row_map_name = row_map_name;
	}


}
