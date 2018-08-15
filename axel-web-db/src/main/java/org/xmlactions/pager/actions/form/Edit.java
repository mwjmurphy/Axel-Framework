package org.xmlactions.pager.actions.form;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.XmlCData;
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
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;
import org.xmlactions.db.sql.select.VersionNumberConcurrency;
import org.xmlactions.pager.actions.CodeAction;
import org.xmlactions.pager.actions.Param;
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
import org.xmlactions.pager.actions.formatter.PreFormatter;
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
public class Edit extends DrawFormFields implements FormDrawing, IStorageFormAction // extends StorageBaseFormAction
{
	private static final Logger log = LoggerFactory.getLogger(Edit.class);
	
	private static final String XML_ROOT_NAME = "root";

	private IExecContext execContext;
	
	private String row_map_name = "row";
	
	private Map<String, String>rememberMap = new HashMap<String, String>();

	/**
	 * The "enforce_concurrency" will switch on (default) or off the enforcement of concurrency.
	 * 
	 * Concurrency is used when editing a record.  Its purpose is to stop two people from editing the same
	 * record concurrently.
	 * 
	 * Concurrency works in conjunction with the table definition of a version number.  A version_number
	 * field must be declared in the table storage definition.  @See storage.xsd
	 *
	 * When concurrency is applied each record read from the table/s will also include the version_number
	 * value.  This is added to the edit form as a hidden input and when the form is submitted to the server
	 * the version_number is included in the update. If the version_number has changed on the row to be updated
	 * then an error is thrown back to the browser.  The user then needs to refresh the page and reload the
	 * values for the form from the database.
	 */               	  
	private boolean enforce_concurrency = true;
	
	private FieldList field_list;

	/** The value that must match the pk value for the record we want to edit. */
	private String pk_value;

	/**
	 * The display option to show the label above the field or left of the field
	 */
	private String label_position;

	/** An option to present the edit form on a presentation page. */
	private String presentation_form;

	/** Can include a form element for build the presentation form inside this element. this takes precedence of the presentation_form attribute */
	private PresentationFormAction form;

	private PreProcesses pre_processes;
	private PostProcesses post_processes;
	



	public String execute(IExecContext execContext) throws Exception
	{

		this.execContext = execContext;

		this.validateStorage("pager:edit");

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
		
		rememberTheseValues(execContext);
		rememberPreAndPostProcesses();
		execContext.put(IExecContext.PERSISTENCE_MAP + ":" + VersionNumberConcurrency.buildVersionNumberKeyWithPkValue(getId(), getPk_value()), rememberMap);

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
		
		PreFormatter.preFormatResult(execContext, xmlObject, getField_list());
		log.debug("xmlObject:" + xmlObject.mapXMLObject2XML(xmlObject, true));
		
		StringBuilder sb = new StringBuilder();

        HtmlForm htmlForm = new HtmlForm();
        htmlForm.setId(getId());
       	htmlForm.setOnSubmit("return(showValidationErrors(saveRecord('" + getId() + "')));");

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
        formSubmit.setStyle("position:absolute;left:-5000px");
        htmlForm.addChild(formSubmit);
        

        HtmlDiv mainDiv = new HtmlDiv(theme);
        htmlForm.addChild(mainDiv);
        mainDiv.setId(getId());
        
		HtmlTable htmlTable = null;
		HtmlTable root = null;
//      if (StringUtils.isEmpty(getPresentation_form()) && getForm() == null) {
		{
			root = startFrame(theme);
			mainDiv.addChild(root);
			if (StringUtils.isEmpty(getPresentation_form()) && getForm() == null) {
				HtmlTr tr = root.addTr(theme);
				HtmlTd td = tr.addTd(theme);
				htmlTable = td.addTable();
				// htmlTable.setClazz(theme.getValue(ThemeConst.ADDEDIT_BORDER.toString()));
			} else {
				htmlTable = new HtmlTable();	// dummy htmlTable, we draw framework presentation into this.
			}
			
		}
        try {
            for (BaseAction baseAction : getField_list().getFields()) {
                if (baseAction instanceof Field) {
                    Field field = (Field) baseAction;
                    String tableFieldName = null;
                    if (Table.isTableAndFieldName(field.getName())) {
                    	tableFieldName = field.getName();
                    } else {
                    	tableFieldName = Table.buildTableAndFieldName(getTable_name(), field.getName());
                    }
                    CommonStorageField storageField;
                    if (Table.isTableAndFieldName(field.getName())) {
                        storageField = database.getStorageField(field.getName());
                    } else {
                        storageField = table.getFieldFromTableAndFieldName(Table.buildTableAndFieldName(getTable_name(),
                                field.getName()));
                    }
                    //if (!(storageField instanceof PK || storageField instanceof Password)) {
                    if (storageField instanceof Password) {
                        String currentValue = null;
                        addToExecContext(execContext, field.getName(),  currentValue);
                        Object result = buildEditFieldPresentation(connection,
                                database,
                                table,
                                field,
                                storageConfig.getDbSpecificName(),
                                theme,
                                currentValue);
                        if (result instanceof HtmlTr[]) {
                        	HtmlTr[] trs = (HtmlTr[])result;
	                        for (HtmlTr htmlTr : trs) {
	                            htmlTable.addChild(htmlTr);
	                        }
                        }
                    } else {
                        String currentValue = getRecordValue(xmlObject, field.getName());
                        if (field.getPost_format() != null) {
                        	rememberMap.put(ClientParamNames.TABLE_FIELD_NAME + "." + tableFieldName + ".post_format", field.getPost_format());
//                    		HtmlInput input = new HtmlInput();
//                    		input.setName(tableFieldName + ".post_format");
//                    		input.setValue(field.getPost_format());
//                    		input.setType("hidden");
//							mainDiv.addChild(input);
                        }
                        if (field.getPrefix()!=null) {
                        	currentValue = field.getPrefix() + currentValue;
                        }
                        if (field.getPostfix()!=null) {
                        	currentValue = currentValue + field.getPostfix();
                        }
                        addToExecContext(execContext, field.getName(),  currentValue);
                        Object result = buildEditFieldPresentation(connection,
                                database,
                                table,
                                field,
                                storageConfig.getDbSpecificName(),
                                theme,
                                currentValue);
                        if (result instanceof HtmlTr[]) {
                        	HtmlTr[] trs = (HtmlTr[])result;
	                        for (HtmlTr htmlTr : trs) {
	                            htmlTable.addChild(htmlTr);
	                        }
                        }
                    }
                } else if (baseAction instanceof FieldHide) {
                    CommonStorageField storageField;
                    FieldHide field = (FieldHide) baseAction;
                    String tableFieldName = null;
                    if (Table.isTableAndFieldName(field.getName())) {
                    	tableFieldName = field.getName();
                    } else {
                    	tableFieldName = Table.buildTableAndFieldName(getTable_name(), field.getName());
                    }
                    if (Table.isTableAndFieldName(field.getName())) {
                        storageField = database.getStorageField(field.getName());
                    } else {
                        storageField = table.getFieldFromTableAndFieldName(Table.buildTableAndFieldName(getTable_name(),
                                field.getName()));
                    }
                    // String currentValue = getRecordValue(xmlObject, DrawDBHTMLHelper.buildName(storageField));
                    String currentValue = getRecordValue(xmlObject, field.getName());
                    if (field.getPost_format() != null) {
                    	rememberMap.put(ClientParamNames.TABLE_FIELD_NAME + "." + tableFieldName + ".post_format", field.getPost_format());
//                		HtmlInput input = new HtmlInput();
//                		input.setName(tableFieldName + ".post_format");
//                		input.setValue(field.getPost_format());
//                		input.setType("hidden");
//						mainDiv.addChild(input);
                    }
                	mainDiv.addChild(((FieldHide)baseAction).buildHiddenInput(execContext, currentValue));
                	// hidden field needs to add extra parameters.
                    DrawFormFields.addToExecContext(execContext, getRow_map_name(), field.getName(),  currentValue, DrawFormFields.getPresentation_name(field, storageField), execContext.replace(getTooltip(field, storageField)));
                } else if (baseAction instanceof FieldCode) {
                	
                	HtmlTr [] trs = buildFieldCodePresentation((FieldCode) baseAction, theme);
                    for (HtmlTr htmlTr : trs) {
                        htmlTable.addChild(htmlTr);
                    }
                }

            }
        } catch (DBConfigException ex) {
            throw new IllegalArgumentException("Unable to build query for [" + getTable_name() + "]\n"
                    + ex.getMessage(), ex);
        } catch (DBSQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        } finally {
            DBConnector.closeQuietly(connection);
        }

		boolean foundSubmitLink = false;
		for (Link link : getLinks()) {
			if (link.isSubmit()) {
				if (StringUtils.isBlank(link.getActionScript())) {
					// if an action test is not configured then use this one.
					link.setActionScript("return(showValidationErrors(saveRecord('" + getId() + "')));");
				}
				foundSubmitLink = true;
				if (StringUtils.isEmpty(link.getUri())) {
					link.setUri("javascript:hide('" + getId() + "');");
				} else {
					htmlForm.setAction(link.getUri());
				}
			}
		}
		// if we dont have a presentation_form and we don't have a form then we must have a submit link
        if (StringUtils.isEmpty(getPresentation_form()) && getForm() == null && foundSubmitLink == false) {
			Validate.isTrue(false, "No 'submit' link has been set for this action.");
		}
		if (getForm() != null) {

	        String newPage = new Action().processPage(execContext, XmlCData.removeCData(getForm().getContent()));
			log.debug("newPage:" + newPage);
			DrawDBHTMLHelper.addLinksAndButtonsToExecContext(execContext, this, theme, "right");

			mainDiv.setContent(newPage);
			
		} else if (StringUtils.isNotEmpty(getPresentation_form())) {
			String path = execContext.getString(ActionConst.WEB_REAL_PATH_BEAN_REF);
			Action action = new Action(path, getPresentation_form(), "pager");
			String newPage = action.processPage(execContext);
			newPage = new org.xmlactions.common.text.Html().removeOuterJsonOrXmlOrHtml(newPage);

			DrawDBHTMLHelper.addLinksAndButtonsToExecContext(execContext, this, theme, "right");

			mainDiv.setContent(newPage);
				
		} else {
			HtmlTr tr = DrawDBHTMLHelper.buildLinksAndButtons(execContext, this, theme, "right");
	        for (BaseAction baseAction : getField_list().getFields()) {
	        	if (baseAction instanceof DeleteRecordLink && StringUtils.isNotEmpty(((DeleteRecordLink)baseAction).getId())) {
	                Html htmlA = ((DeleteRecordLink) baseAction).draw(execContext, theme);
	                HtmlTd td = tr.addTd(theme);
	                td.setClazz(theme.getValue(ThemeConst.LIST_TD.toString()));
	                td.addChild(htmlA);
	        	}
	        }
			
			root.addChild(tr);
		}

		// ===
        // Add Any Hidden Fields
        // ===
        for (HtmlInput input : getHiddenFields()) {
            mainDiv.addChild(input);
        }
        // ===
        // Add Field PK (Primary Key values);
        // ===
//        Set<Entry<String,String>> entrySet = hiddenInputs.entrySet();
//        Iterator<Entry<String,String>> iterator = entrySet.iterator();
//        while(iterator.hasNext()) {
//        	Entry<String,String>entry = iterator.next();
//        	HtmlInput input = buildHiddenInput(entry.getKey(), entry.getValue());
//        	mainDiv.addChild(input);
//        	addToExecContext(execContext, entry.getKey(), entry.getValue());
//        }


		
        if (StringUtils.isEmpty(getPresentation_form()) && getForm() == null) {
        	return htmlForm;
        } else {
        	return mainDiv;
        }
	}
	

    private Html[] buildEditFieldPresentation(Connection connection, Database database, Table table, Field field,
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
            
            String parentTableAndPkName = Edit.getTableAndPkName(database, parentTable.getName(), field.getName());
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
            dbSQL.setIncludeIndex(false);
            XMLObject xo = dbSQL.query2XMLObject(connection, sqlQuery, "root", null, null, params);
            if (StringUtils.isEmpty(selectedValue) ) {
            	selectedValue = currentValue;
            }
            	
            log.debug("\nkeyPreviousValue:" + keyPreviousValue + " sqlQuery:" + sqlQuery + "\nxo:" + xo.mapXMLObject2XML(xo));
            if (StringUtils.isEmpty(selectedValue) && StringUtils.isNotEmpty(defaultValue)) {
            	selectedValue = defaultValue;
            }
            HtmlSelect htmlSelect = SelectForm.buildSelectionPresentation(execContext, storageField, getLabel_position(), keyPreviousValue, theme, xo, idAttributeName, valueAttributeName, selectIdentifier, selectedValue);
            DrawFormFields.addAttributes(htmlSelect, field);
            
            if (field.getControllableFields()!= null && field.getControllableFields().size() > 0) {
                String childId = Edit.getChildPkId(database, table, field.getControllableFields().get(0));
                htmlSelect.put(HtmlEnum.onchange.getAttributeName(), "populateSelect('" + childId + "', buildSelect('" + getId() + "'));");
            }
            
            // if we have a presentation_form we only add the select to execContext.row map
            if (StringUtils.isNotEmpty(getPresentation_form()) || getForm() != null) {
            	addToExecContext(execContext, selectIdentifier, htmlSelect);
            } else {           
            	trs = DrawHtmlField.displayForSelect(execContext, storageField, getLabel_position(), theme, htmlSelect);
            }
        } else {
            if (StringUtils.isNotEmpty(getPresentation_form()) || getForm() != null) {
            	Html html = null;
        		if (storageField instanceof IDrawField) {
        			//html = ((IDrawField) storageField).buildUpdateHtml(currentValue, theme);
        			html = buildInputForEdit(execContext, getRow_map_name(), field, storageField, currentValue, theme);
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
    
    private HtmlTr[] buildFieldCodePresentation(FieldCode fieldCode, Theme theme) throws Exception {
    
    	HtmlTr [] trs = new HtmlTr[1];
	    if(fieldCode.getCode() == null) {
			throw new IllegalArgumentException("Missing code element from field_code[" + fieldCode.getName() + "]");
	    }
	    String currentValue = fieldCode.getCode().execute(execContext);
	    HtmlTr tr = new HtmlTr(theme);
	    HtmlTd td = buildTdLabel(theme, false, false, fieldCode.getName(), fieldCode.getTooltip()); 
	    tr.addChild(td);
	    td = new HtmlTd(theme);
	    td.setContent(currentValue);
        td.setTitle(fieldCode.getTooltip());
		td.setAlign("left");
		td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
		td.setHeight(fieldCode.getHeight());
		tr.addChild(td);
	    
	    trs[0] = tr;
	    
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
	 * Get the value for a column named by the tableAndFieldName
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

		java.util.List<SqlField> sqlFields = FormUtils.buildTableAndFieldNamesAsList(table,	getField_list().getFields());

		List<SqlField> includedVersionNumbers = null;
		if (this.isEnforce_concurrency()) {
			includedVersionNumbers = includeVersionNumbers(database, sqlFields, execContext);
		}
		rememberMap.put(ClientParamNames.ENFORCE_CONCURRENCY, "" + isEnforce_concurrency());

		// make sure we have the pk for the named table
		boolean haveNamedTable = false;
		for (SqlField sqlField : sqlFields) {
			Table fieldTable = (Table)sqlField.getCommonStorageField().getParent();
			if (table.getName().equals(fieldTable.getName())) {
				haveNamedTable = true;
				break;	// we got the table
			}
		}
		
		// we didn't have the named table pk so add it
		if (haveNamedTable == false) {
			PK pk = table.getPk();
			SqlField sqlField = new SqlField(Table.buildTableAndFieldName(table.getAlias(), pk.getName()), Table.buildTableAndFieldName(table.getAlias(), pk.getAlias()));
			sqlField.setCommonStorageField(pk);
			sqlFields.add(0, sqlField);
		}
		
		//for (CommonStorageField storageField : storageFields) {
		//	// TODO Skip Passwords, we can never retrieve a password
		//	if ((storageField instanceof Password) == false) {
		//	    sqlFields.add(new SqlField(DrawDBHTMLHelper.buildName(storageField)));
		//	}
		//}

		String whereClause = DrawDBHTMLHelper.buildName(table.getPk()) + " = "
				+ getStrSubPk_value(execContext);

		SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storageConfig.getStorageContainer().getStorage(), database.getName(), table.getName(), sqlFields, storageConfig.getDbSpecificName());
		// addMissingRootTable(sqlSelectInputs, table);
		addMissingPKs(sqlSelectInputs);
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

		// getPKsAndValues()
		{
			if (xmlObject.getChildCount() > 0) {
				XMLObject row = xmlObject.getChild(0);
				for (ISqlTable iSqlTable : sqlSelectInputs.getSqlTables()) {
					for (SqlField sqlField : iSqlTable.getFields()) {
						if (sqlField.getCommonStorageField() instanceof PK) {
							String value = row.getAttributeValueAsString(sqlField.getAliasOrFieldName());
							log.debug("PK:" + sqlField.getAliasOrFieldName() + "=" + value);
							if (! StringUtils.isEmpty(value)) {	// dont store a PK value if its not set.
								//hiddenInputs.put(sqlField.getAliasOrFieldName(), value);
								rememberMap.put(ClientParamNames.TABLE_FIELD_NAME + "." + sqlField.getAliasOrFieldName(), value);
							}
						}
					}
				}
			}
		}
		if (this.isEnforce_concurrency()) {
			rememberVersionNumbers(execContext, includedVersionNumbers, xmlObject);	// extract the version number values and store to session persistence.
		}
		return xmlObject;
	}
	
	private void addMissingPKs(SqlSelectInputs sqlSelectInputs) {
		for (ISqlTable sqlTable : sqlSelectInputs.getSqlTables()) {
			boolean havePK = false;
			for (SqlField sqlField : sqlTable.getFields()) {
				if (sqlField.getCommonStorageField() instanceof PK) {
					havePK = true;
				}
			}
			if (havePK == false) {
				Table table = sqlTable.getTable();
				PK pk = table.getPk();
				String name = Table.buildTableAndFieldName(table.getAlias(), pk.getAlias());
				SqlField sqlField = new SqlField(Table.buildTableAndFieldName(table.getAlias(), pk.getName()), Table.buildTableAndFieldName(table.getAlias(), pk.getAlias()));
				sqlField.setCommonStorageField(pk);
				sqlTable.addField(sqlField);
			}
		}
	}
	
	private List<SqlField> includeVersionNumbers(Database database, java.util.List<SqlField> sqlFields, IExecContext execContext)
	{
		List<Table>tables = new ArrayList<Table>();

		List<SqlField> includedVersionNumbers = new ArrayList<SqlField>();

		// get list of tables.
		for (SqlField sqlField : sqlFields) {
			addIfNew(tables, (Table)sqlField.getCommonStorageField().getParent());
		}
		
		for (Table table : tables) {
			SqlField sqlField = null;
			CommonStorageField commonStorageField = null;
			boolean added = false;
			if (table.getUpdate_field_version_num() != null) {
				commonStorageField = table.getField(table.getUpdate_field_version_num());
				sqlField = new SqlField(Table.buildTableAndFieldName(table.getAlias(), commonStorageField.getName()), Table.buildTableAndFieldName(table.getAlias(), commonStorageField.getAlias()));
				added = addNewSqlField(sqlFields, sqlField);
			} else if (database.getUpdate_field_version_num() != null) {
				commonStorageField = table.getField(database.getUpdate_field_version_num());
				sqlField = new SqlField(Table.buildTableAndFieldName(table.getAlias(), commonStorageField.getName()), Table.buildTableAndFieldName(table.getAlias(), commonStorageField.getAlias()));
				added = addNewSqlField(sqlFields, sqlField);
			}
			if (added) {
				sqlField.setCommonStorageField(commonStorageField);
				// remember these so we can persist their values in the session for later retrieval if the user commits any changes from the browser. 
				includedVersionNumbers.add(sqlField);
			}
		}
		return includedVersionNumbers;
	}
	
	private boolean addNewSqlField(List<SqlField> sqlFields, SqlField newSqlField) {
		for (SqlField sqlField : sqlFields) {
			if (sqlField.getFieldName().equals(newSqlField.getFieldName())) {
				return false;	// not added already exists
			}
		}
		sqlFields.add(newSqlField);
		return true;	// added
	}
	
	private void addIfNew(List<Table>tables, Table newTable) {
		for (Table table : tables) {
			if (table.getName().equals(newTable.getName())) {
				return;
			}
		}
		tables.add(newTable);
	}
	
	
	/**
	 * Get the version no's and store them in the session so we can use them on a Save
	 * @param includedVersionNumbers - this is the list of version numbers that were added to the sql
	 * @param xmlObject - this is the resultant data from the sql call. We extract the includedVersionNumbers from this xml.
	 */
	private void rememberVersionNumbers(IExecContext execContext, List<SqlField> includedVersionNumbers, XMLObject xmlObject) {
		
		for (XMLObject childRow : xmlObject.getChildren()) {
			for (SqlField sqlField : includedVersionNumbers) {
				String key = sqlField.getFieldAlias();
				String value = childRow.getAttributeValueAsString(key);
				if (value != null) {
					rememberMap.put(ClientParamNames.TABLE_FIELD_NAME + "." + key, value);
				}
			}
		}
	}

	/**
	 * Store mandatory fields in the session so we can use them on a Save
	 */
	private void rememberTheseValues(IExecContext execContext) {
		
		rememberMap.put(ClientParamNames.STORAGE_CONFIG_REF, getStorage_config_ref(execContext));
		rememberMap.put(ClientParamNames.TABLE_NAME_MAP_ENTRY, getTable_name());
		rememberMap.put(ClientParamNames.PK_VALUE, getPk_value());
		
	}


//	private void addMissingRootTable(SqlSelectInputs sqlSelectInputs, Table table) {
//		for (ISqlTable sqlTable : sqlSelectInputs.getSqlTables()) {
//			if (table.getName().equals(sqlTable.getTableName())) {
//				return;	// root table is in the list
//			}
//		}
//		SqlTable sqlTable = new SqlTable(table);
//		sqlSelectInputs.addSqlTable(0, sqlTable);
//	}

	private Html[] buildPresentation(IExecContext execContext, CommonFormFields formField, CommonStorageField storageField, String value, Theme theme) {
		return displayForEdit(execContext, getRow_map_name(), formField, storageField, value, theme);
	}
	private Html[] old_buildPresentation(Table table, CommonStorageField storageField, String value, Theme theme)
	{

		Html[] output;
		if (storageField instanceof IDrawField) {
			output = ((IDrawField) storageField).displayForUpdate(value, theme);
		} else {
			throw new IllegalArgumentException("Unknown Storage Field Type [" + storageField.getClass().getName()
					+ "], unable to build presentation.");
		}
		return output;
	}


	public String toString()
	{

		return "edit storage_config_ref [" + getStorage_config_ref() + "] table_name [" + getTable_name()
				+ "] pk_value ["
				+ getPk_value() + "]";
	}

	public void setLabel_position(String label_position)
	{

		this.label_position = label_position;
	}

	public String getLabel_position()
	{

		return label_position;
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
        inputs.add(buildHiddenInput(ClientParamNames.UNIQUE_ID, VersionNumberConcurrency.buildVersionNumberKeyWithPkValue(getId(), getPk_value())));
        return inputs;
	}

	public void rememberPreAndPostProcesses()
	{

        if (getPre_processes() != null) {
        	int keyIndex = 1;
        	for (CodeAction processor : getPre_processes().getProcessors()) {
        		String key =  ClientParamNames.PRE_PROCESSOR + "." + keyIndex++;
        		rememberMap.put(key, processor.getCall());
                addToExecContext(execContext, key, processor.getCall());
                int paramIndex = 1;
                for (Param param : processor.getParams()) {
                	String paramKey = key + "." + paramIndex++;
                	rememberMap.put(paramKey, param.getValue());
                    addToExecContext(execContext, paramKey, param.getValue());
                }
        	}
        }
        if (getPost_processes() != null) {
        	int keyIndex = 1;
        	for (CodeAction processor : getPost_processes().getProcessors()) {
        		String key =  ClientParamNames.POST_PROCESSOR + "." + keyIndex++;
                rememberMap.put(key, processor.getCall());
                addToExecContext(execContext, key, processor.getCall());
                int paramIndex = 1;
                for (Param param : processor.getParams()) {
                	String paramKey = key + "." + paramIndex++;
                	rememberMap.put(paramKey, param.getValue());
                    addToExecContext(execContext, paramKey, param.getValue());
                }
        	}
        }
	}

	public void validateStorage(String errMsg)
	{
        Validate.notEmpty(getId(), "id has not been set - " + errMsg);
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

	/*
    public void setPre_processor(String pre_processor) {
		this.pre_processor = pre_processor;
	}

    public String getPre_processor() {
		return pre_processor;
	}

    public void setPost_processor(String post_processor) {
		this.post_processor = post_processor;
	}

    public String getPost_processor() {
		return post_processor;
	}
	*/
	public void setPre_processes(PreProcesses pre_processes) {
		this.pre_processes = pre_processes;
	}

	public PreProcesses getPre_processes() {
		return pre_processes;
	}

	public void setPost_processes(PostProcesses post_processes) {
		this.post_processes = post_processes;
	}

	public PostProcesses getPost_processes() {
		return post_processes;
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
		//table.setId(getId());
//		if (isVisible()) {
//			table.setClazz(theme.getValue(ThemeConst.ADDEDIT_BORDER.toString()));
//		} else {
//			table.setClazz(theme.getValue(ThemeConst.ADDEDIT_BORDER.toString(),
//					"hide"));
//		}
		if (! isVisible()) {
			table.setClazz("hide");
		}
		if (StringUtils.isNotEmpty(getWidth())) {
			table.setWidth(getWidth());
		}

		// ===
		// Add Any Hidden Fields
		// ===
		//for (HtmlInput input : getHiddenFields()) {
		//	table.addChild(input);
		//}
		// ===
		// Add Frame Title
		// ===
		if (StringUtils.isNotEmpty(getTitle())) {
			HtmlTr tr = table.addTr();
			HtmlTh th = tr.addTh();
			// tr.setClazz(theme.getValue(ThemeConst.ADDEDIT_BORDER.toString()));
			th.setClazz(theme.getValue(ThemeConst.ADDEDIT_TITLE.toString()));
			th.setContent(getTitle());
		}
		return table;

	}

	/**
	 * @return the form
	 */
	public PresentationFormAction getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(PresentationFormAction form) {
		this.form = form;
	}

	public String getRow_map_name() {
		return row_map_name;
	}

	public void setRow_map_name(String row_map_name) {
		this.row_map_name = row_map_name;
	}

	public boolean isEnforce_concurrency() {
		return enforce_concurrency;
	}

	public void setEnforce_concurrency(boolean enforce_concurrency) {
		this.enforce_concurrency = enforce_concurrency;
	}



}