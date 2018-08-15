
package org.xmlactions.pager.actions.form;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.common.util.RioStringUtils;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.common.xml.XMLAttribute;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.DBConfigException;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.DBUtils;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Password;
import org.xmlactions.db.actions.Sql;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.db.sql.BuildSelect;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.FormDrawing;
import org.xmlactions.pager.actions.form.IStorageFormAction;
import org.xmlactions.pager.actions.form.Link;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.drawing.DrawFormFields;
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
import org.xmlactions.pager.drawing.IDrawField;
import org.xmlactions.pager.drawing.html.DrawDBHTMLHelper;
import org.xmlactions.pager.drawing.html.DrawHtmlField;
import org.xmlactions.web.PagerWebConst;


/**
 * Builds an edit record presentation.
 * <p>
 * The edit fields come from the database schema.
 * </p>
 * <p>
 * parameters / attributes will build the html attributes needed for display.
 * </p>
 * 
 * @author mike
 * 
 */
public class ViewForm extends DrawFormFields implements FormDrawing, IStorageFormAction // extends StorageBaseFormAction
{

	private static final Logger log = LoggerFactory.getLogger(ViewForm.class);

	private static final String XML_ROOT_NAME = "root";

	private IExecContext execContext;
	
	private String row_map_name = "row";

	private FieldList field_list;

	/** The value that must match the pk value for the record we want to view. */
	private String pk_value;

	/** An option to present the edit form on a presentation page. */
	private String presentation_form;

	private String[] hiddenFields = { ClientParamNames.TABLE_NAME_MAP_ENTRY, ClientParamNames.STORAGE_CONFIG_REF,
			ClientParamNames.PK_VALUE, ClientParamNames.UNIQUE_ID };

	public String execute(IExecContext execContext) throws Exception
	{

		this.execContext = execContext;

		this.validateStorage("pager:view");

        StorageConfig storageConfig = (StorageConfig) execContext.get(getStorage_config_ref(execContext));
		Validate.notNull(storageConfig, "No [" + StorageConfig.class.getName() + "] found in ExecContext ["
                + getStorage_config_ref(execContext) + "]");
		StorageContainer storageContainer = storageConfig.getStorageContainer();
		Storage storage = storageContainer.getStorage();

		Database database = storage.getDatabase(storageConfig.getDatabaseName());

		Table table = database.getTable(getTable_name());

		log.debug("Table.name:" + table.getName() + " getTable_name():" + getTable_name());
		Validate.notEmpty(getPk_value(), "The pk_value is not set, this must be configured in the action");

		Theme theme = execContext.getThemes().getTheme(getTheme_name(execContext));
		setTheme(theme);

		setPk_value(StrSubstitutor.replace(getPk_value(), execContext));
		log.debug(toString());
		Html output;
		try {
			output = buildPresentation(execContext, storageConfig, database, table, theme);
		} catch (DBConfigException ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}

		return output.toString();
	}

	private Html buildPresentation(IExecContext execContext, StorageConfig storageConfig, Database database,
			Table table,
			Theme theme)
			throws DBConfigException, IOException, NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{
        DBConnector dbConnector = storageConfig.getDbConnector();
        Connection connection;
        try {
            // connection = dataSource.create().getConnection();
            connection = dbConnector.getConnection(execContext);
        } catch (Throwable e) {
            throw new IllegalArgumentException("Cannot get database connection using storage_config_ref:"
                    + getStorage_config_ref(execContext), e);
        }

        List<CommonStorageField> storageFields = getField_list().getStorageFields(database, table);

		XMLObject xmlObject = retrieveRecord(storageConfig, database, table, storageFields, execContext);
		log.debug("xmlObject:" + xmlObject.mapXMLObject2XML(xmlObject, true));
		
		StringBuilder sb = new StringBuilder();

        HtmlForm htmlForm = new HtmlForm();
        htmlForm.setOnSubmit("return(showValidationErrors(updateRecord('" + getId() + "')));");

        // ===
        // build hidden submit field, this will trigger when user presses an enter key in an input field.
        // ===
		HtmlInput formSubmit = new HtmlInput();
        if (Link.DISPLAY_AS_BUTTON.equals(getDisplay_as())) {
            formSubmit.setClazz(theme.getValue(ThemeConst.INPUT_LINK_BUTTON.toString()));
        } else if (Link.DISPLAY_AS_MENU.equals(getDisplay_as())) {
            formSubmit.setClazz(theme.getValue(ThemeConst.INPUT_LINK_MENU.toString()));
        } else {
            formSubmit.setClazz(theme.getValue(ThemeConst.INPUT_LINK.toString()));
        }
        formSubmit.setType("submit");
        formSubmit.setValue(PagerConstants.LANG_KEY_GO);
        formSubmit.setStyle("position:absolute;left:-1000px");
        htmlForm.addChild(formSubmit);
        

        HtmlDiv div = new HtmlDiv(theme);
        htmlForm.addChild(div);
        div.setId(getId());
        
		HtmlTable htmlTable = null;
		HtmlTable root = null;
        if (StringUtils.isEmpty(getPresentation_form())) {
			root = startFrame(theme);
			div.addChild(root);
			HtmlTr tr = root.addTr(theme);
			HtmlTd td = tr.addTd(theme);
			htmlTable = td.addTable();
			htmlTable.setClazz(theme.getValue(ThemeConst.ADDEDIT_BORDER.toString()));
        } else {
            // ===
            // Add Any Hidden Fields
            // ===
            for (HtmlInput input : getHiddenFields()) {
                htmlForm.addChild(input);
            }
        }
        try {
            for (BaseAction baseAction : getField_list().getFields()) {
                if (baseAction instanceof Field) {
                    Field field = (Field) baseAction;
                    CommonStorageField storageField;
                    if (Table.isTableAndFieldName(field.getName())) {
                        storageField = database.getStorageField(field.getName());
                    } else {
                        storageField = table.getFieldFromTableAndFieldName(Table.buildTableAndFieldName(getTable_name(),
                                field.getName()));
                    }
                    //if (!(storageField instanceof PK || storageField instanceof Password)) {
                    if (!(storageField instanceof Password)) {
                        String currentValue = getRecordValue(xmlObject, DrawDBHTMLHelper.buildName(storageField));
                        Html[] result = buildViewFieldPresentation(connection,
                                database,
                                table,
                                field,
                                storageConfig.getDbSpecificName(),
                                theme,
                                currentValue);
                        if (result != null) {
	                        for (Html html : result) {
	                            htmlTable.addChild(html);
	                        }
                        }
                    }
                } else if (baseAction instanceof FieldHide) {
                    CommonStorageField storageField;
                    FieldHide field = (FieldHide) baseAction;
                    if (Table.isTableAndFieldName(field.getName())) {
                        storageField = database.getStorageField(field.getName());
                    } else {
                        storageField = table.getFieldFromTableAndFieldName(Table.buildTableAndFieldName(getTable_name(),
                                field.getName()));
                    }
                    String currentValue = getRecordValue(xmlObject, DrawDBHTMLHelper.buildName(storageField));
                	Html hiddenInput = ((FieldHide)baseAction).buildHiddenInput(execContext, currentValue);
                    if (StringUtils.isNotEmpty(getPresentation_form())) {
                    	div.addChild(hiddenInput);
                    } else {
                    	root.addChild(hiddenInput);
                    }
                }
            }
        } catch (DBConfigException ex) {
            throw new IllegalArgumentException("Unable to build query for [" + getTable_name() + "]\n"
                    + ex.getMessage(), ex);
        } catch (DBSQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        } finally {
            DBConnector.closeQuietly(connection);
        }

		for (Link link : getLinks()) {
			if (link.isSubmit()) {
				link.setActionScript("return(showValidationErrors(updateRecord('" + getId() + "')));");
				if (StringUtils.isEmpty(link.getUri())) {
					link.setUri("javascript:hide('" + getId() + "');");
				} else {
					htmlForm.setAction(link.getUri());
				}
			}
		}
		
		if (StringUtils.isNotEmpty(getPresentation_form())) {
	        String path = execContext.getString(ActionConst.WEB_REAL_PATH_BEAN_REF);
			Action action = new Action(path, getPresentation_form(), "pager");
			String newPage = action.processPage(execContext);

			DrawDBHTMLHelper.addLinksAndButtonsToExecContext(execContext, this, theme, "right");

			div.setContent(newPage);
			
		} else {
			root.addChild(DrawDBHTMLHelper.buildLinksAndButtons(execContext, this, theme, "right"));
		}
		return htmlForm;
	}

    private Html[] buildViewFieldPresentation(Connection connection, Database database, Table table, Field field,
            String dbSpecificName, Theme theme, String currentValue) throws DBSQLException, DBConfigException {
        CommonStorageField storageField;


        if (Table.isTableAndFieldName(field.getName())) {
            storageField = database.getStorageField(field.getName());
        } else {
            storageField = table.getFieldFromTableAndFieldName(Table.buildTableAndFieldName(getTable_name(),
                    field.getName()));
        }
        String keyPreviousValue = PagerWebConst.buildRequestKey(field.getName());
        keyPreviousValue = RioStringUtils.convertToString(execContext.get(keyPreviousValue));

        Html[] trs = null;

        if (field.getPopulator() != null) {
            Table parentTable = (Table)storageField.getParent();
            
            String parentTableAndPkName = ViewForm.getTableAndPkName(database, parentTable.getName(), field.getName());
            String sqlQuery = null;
            String valueAttributeName = null;
            String idAttributeName = null;
            String selectIdentifier = null;
    
            idAttributeName = valueAttributeName;
            Populator populator = field.getPopulator();
            String defaultValue = null;
        	List<SqlField>params = null;
            if (populator instanceof SqlPopulator) {
            	SqlPopulator sqlPopulator = (SqlPopulator)populator;
            	defaultValue = execContext.replace(sqlPopulator.getDefault_value());
                Sql sql = database.getSql(dbSpecificName, sqlPopulator.getRef());
                if (sql != null) {
                    Table fieldTable = (Table)storageField.getParent();
                    selectIdentifier = fieldTable.buildSqlFieldName(storageField);
                    sqlQuery = sql.getSql();
                    Validate.notEmpty(sqlQuery, "No SQL found for sql_populator [" + sqlPopulator.getRef() + "]");
                    sqlQuery = execContext.replace(sqlQuery);
                    params = sql.getListOfParams(execContext);
                }
            } else {
                throw new IllegalArgumentException("Unknown populator type [" + populator.getClass().getName() + "]"); 
            }
            String selectedValue = PagerWebConst.buildRequestKey(selectIdentifier);
            selectedValue = RioStringUtils.convertToString(execContext.get(selectedValue));
            DBSQL dbSQL = new DBSQL();
            XMLObject xo = dbSQL.query2XMLObject(connection, sqlQuery, "root", null, null, params);
            if (StringUtils.isEmpty(selectedValue) ) {
            	selectedValue = currentValue;
            }
            	
            log.debug("\nkeyPreviousValue:" + keyPreviousValue + " sqlQuery:" + sqlQuery + "\nxo:" + xo.mapXMLObject2XML(xo));
            if (StringUtils.isEmpty(selectedValue) && StringUtils.isNotEmpty(defaultValue)) {
            	selectedValue = defaultValue;
            }
            HtmlSelect htmlSelect = SelectForm.buildSelectionPresentation(execContext, storageField, getLabel_position(), keyPreviousValue, theme, xo, idAttributeName, valueAttributeName, selectIdentifier, selectedValue);
            
            if (field.getControllableFields()!= null && field.getControllableFields().size() > 0) {
                String childId = ViewForm.getChildPkId(database, table, field.getControllableFields().get(0));
                htmlSelect.put(HtmlEnum.onchange.getAttributeName(), "populateSelect('" + childId + "', buildSelect('" + getId() + "'));");
            }
            
            // if we have a presentation_form we only add the select to execContext.row map
            if (StringUtils.isNotEmpty(getPresentation_form())) {
            	addToExecContext(execContext, parentTableAndPkName, htmlSelect);
            } else {           
            	trs = DrawHtmlField.displayForSelect(execContext, storageField, getLabel_position(), theme, htmlSelect);
            }
        } else {
            if (StringUtils.isNotEmpty(getPresentation_form())) {
            	Html html = null;
        		if (storageField instanceof IDrawField) {
        			html = ((IDrawField) storageField).buildViewHtml(currentValue, theme);
        		} else {
        			throw new IllegalArgumentException("Unknown Storage Field Type [" + storageField.getClass().getName()
        					+ "], unable to build presentation.");
        		}
                Table fieldTable = (Table)storageField.getParent();
                String selectIdentifier = fieldTable.buildSqlFieldName(storageField);
            	addToExecContext(execContext, selectIdentifier, html);
            	
            } else {           
            	trs = buildPresentation(execContext, field, storageField, currentValue, theme);
            }
        }
        return trs;
	}

    public static String getChildPkId(Database database, Table table, Field field) {
        PK pk = null;
        Table pkTable = table;
        if (Table.isTableAndFieldName(field.getName())) {
            String tableName = Table.getTableName(field.getName());
            pkTable = database.getTable(tableName);
            pk = pkTable.getPk();
            if (pk == null) {
                throw new IllegalArgumentException("Table ["
                        + tableName
                        + "] does not have a PK. A PK is required when using the Select option from a Search using the field ["
                        + field.getName() + "]");
            }
        } else {
            pk = table.getPk();
            if (pk == null) {
                throw new IllegalArgumentException("Table ["
                        + table.getName()
                        + "] does not have a PK. A PK is required when using the Select option from a Search using the field ["
                        + field.getName() + "]");
            }
        }
        return Table.buildTableAndFieldName(pkTable.getName(), pk.getName());
    }

    /**
     * Gets the PK from the table referenced by fieldName
     * 
     * @param database
     * @param fieldName
     * @return
     */
    public static String getTableAndPkName(Database database, String parentTableName, String fieldName) {
        String tableAndFieldName = null;
        if (Table.isTableAndFieldName(fieldName)) {
            String tableName = Table.getTableName(fieldName);
            Table table = database.getTable(tableName);
            PK pk = table.getPk();
            if (pk == null) {
                throw new IllegalArgumentException("Table ["
                        + tableName
                        + "] does not have a PK. A PK is required when using the Select option from a Search using the field ["
                        + fieldName + "]");
            }
            tableAndFieldName = Table.buildTableAndFieldName(tableName, pk.getName());
        } else {
            Table table = database.getTable(parentTableName);
            PK pk = table.getPk();
            if (pk == null) {
                throw new IllegalArgumentException("Table ["
                        + parentTableName
                        + "] does not have a PK. A PK is required when using the Select option from a Search using the field ["
                        + fieldName + "]");
            }
            tableAndFieldName = Table.buildTableAndFieldName(parentTableName, pk.getName());
        }
        return tableAndFieldName;
    }

	/**
	 * Get the value for a colum named by the tableAndFieldName
	 * 
	 */
	private String getRecordValue(XMLObject xmlObject, String tableAndFieldName)
	{

		log.debug("getRecordValue for " + tableAndFieldName);

		List<XMLObject> children = xmlObject.getChildren();
		if (children.size() > 0) {
			XMLObject child = children.get(0);
			log.debug("child.getElementName():" + child.getElementName() + " child.getName():" + child.getName());
			for (XMLAttribute attribute : child.getAttributes()) {
				log.debug("attribute key:" + attribute.getKey() + " value:" + attribute.getValueAsString());
			}
			return child.getAttributeValueAsString(tableAndFieldName);
		}
		return "";
	}

	/**
	 * @param database
	 * @param table
	 * @param storageFields
	 * @param execContext
	 * @return XMLObject of the record content
	 * @throws DBConfigException
	 */
	private XMLObject retrieveRecord(StorageConfig storageConfig, Database database, Table table,
			List<CommonStorageField> storageFields,
			IExecContext execContext) throws DBConfigException
	{

		// List<SqlField> sqlFields = new ArrayList<SqlField>();
		// buildFieldListIfNeeded(storageConfig, getTable_name(), getField_list());

		java.util.List<SqlField> sqlFields = FormUtils.buildTableAndFieldNamesAsList(table,
				getField_list().getFields());
		
		//for (CommonStorageField storageField : storageFields) {
		//	// TODO Skip Passwords, we can never retrieve a password
		//	if ((storageField instanceof Password) == false) {
		//	    sqlFields.add(new SqlField(DrawDBHTMLHelper.buildName(storageField)));
		//	}
		//}

		String whereClause = DrawDBHTMLHelper.buildName(table.getPk()) + " = "
				+ getStrSubPk_value(execContext);

		SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storageConfig.getStorageContainer().getStorage(), database.getName(), table.getName(), sqlFields, storageConfig.getDbSpecificName());
		sqlSelectInputs.addWhereClause(whereClause);
		ISqlSelectBuildQuery build = storageConfig.getSqlBuilder();
        String selectSql = build.buildSelectQuery(getExecContext(), sqlSelectInputs, null);
		log.debug("\nselectSql:" + selectSql);

		Connection connection = null;
		XMLObject xmlObject;
		try {
			connection = storageConfig.getDbConnector().getConnection(execContext);
			xmlObject = new DBSQL().query2XMLObject(connection, selectSql, XML_ROOT_NAME, null, null, null);
		} catch (Throwable e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		} finally {
			DBUtils.closeQuietly(connection);
		}
		return xmlObject;
	}

	private Html[] buildPresentation(IExecContext execContext, CommonFormFields formField, CommonStorageField storageField, String value, Theme theme)
	{
		return displayForView(execContext, getRow_map_name(), formField, storageField, value, theme);
	}


	public String toString()
	{

		return "edit storage_config_ref [" + getStorage_config_ref() + "] table_name [" + getTable_name()
				+ "] pk_value ["
				+ getPk_value() + "]";
	}

	public void setPk_value(String pk_value)
	{

		this.pk_value = pk_value;
	}

	public String getPk_value()
	{

		return pk_value;
	}

	public String getStrSubPk_value(IExecContext execContext)
	{

		return StrSubstitutor.replace(pk_value, execContext);
	}

	public void setField_list(FieldList field_list)
	{

		this.field_list = field_list;
	}

	public FieldList getField_list()
	{

		if (field_list == null) {
			return new FieldList();
		}
		return field_list;
	}

	public IExecContext getExecContext()
	{

		return execContext;
	}

	public List<HtmlInput> getHiddenFields()
	{

		java.util.List<HtmlInput> inputs = new ArrayList<HtmlInput>();

        inputs.add(buildHiddenInput(ClientParamNames.STORAGE_CONFIG_REF, getStorage_config_ref(execContext)));
		if (!StringUtils.isEmpty(getTable_name())) {
			inputs.add(buildHiddenInput(ClientParamNames.TABLE_NAME_MAP_ENTRY, getTable_name()));
		}
		inputs.add(buildHiddenInput(ClientParamNames.PK_VALUE, getStrSubPk_value(execContext)));

		
        return inputs;
	}

	public void validateStorage(String errMsg)
	{
        Validate.notEmpty(getStorage_config_ref(execContext), "storage_config_ref has not been set - " + errMsg);
		Validate.notEmpty(getTable_name(), "table_name has not been set - " + errMsg);
		Validate.notEmpty(getPk_value(), "pk_value has not been set - " + errMsg);
	}

	public void setPresentation_form(String presentation_form) {
		this.presentation_form = presentation_form;
	}

	public String getPresentation_form() {
		return presentation_form;
	}

	private void addToExecContext(IExecContext execContext, String key, Object value)
	{
		boolean newMap = false;
		log.debug("addToExecContext " + key + "=" + value);
		Map<String, Object> map = execContext.getNamedMap(PagerConstants.ROW_MAP_NAME);
		if (map == null) {
			map = new HashMap<String, Object>();
			newMap = true;
		}
		map.put(key, value);
		if (newMap == true) {
			execContext.addNamedMap(PagerConstants.ROW_MAP_NAME, map);
		}
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
		if (isVisible()) {
			table.setClazz(theme.getValue(ThemeConst.ADDEDIT_BORDER.toString()));
		} else {
			table.setClazz(theme.getValue(ThemeConst.ADDEDIT_BORDER.toString(),
					"hide"));
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
			HtmlTh th = tr.addTh();
			tr.setClazz(theme.getValue(ThemeConst.ADDEDIT_BORDER.toString()));
			th.setClazz(theme.getValue(ThemeConst.ADDEDIT_TITLE.toString()));
			th.setContent(getTitle());
		}
		return table;

	}

	public String getRow_map_name() {
		return row_map_name;
	}

	public void setRow_map_name(String row_map_name) {
		this.row_map_name = row_map_name;
	}

}