
package org.xmlactions.pager.actions.form;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.XmlCData;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.common.util.RioStringUtils;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.DBConfigException;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Sql;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.db.sql.select.SqlField;
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
import org.xmlactions.pager.config.PagerConstants;
import org.xmlactions.pager.drawing.IDrawField;
import org.xmlactions.pager.drawing.html.DrawDBHTMLHelper;
import org.xmlactions.pager.drawing.html.DrawHtmlField;
import org.xmlactions.pager.drawing.html.SelectHtml;
import org.xmlactions.web.PagerWebConst;


/**
 * Builds an add record presentation that allows the user to enter details for the new record.
 * <p>
 * The add fields come from the database schema.
 * </p>
 * <p>
 * parameters / attributes will build the html attributes needed for display.
 * </p>
 * 
 * @author mike
 * 
 */
public class Add extends DrawFormFields implements FormDrawing, IStorageFormAction
{

	public void setExecContext(IExecContext execContext) {
		this.execContext = execContext;
	}

	private static final Logger log = LoggerFactory.getLogger(Add.class);
	
	private String row_map_name = "row";

	private FieldList field_list;

	/** An option to present the add form on a presentation page. */
	private String presentation_form;

	/** Can include a form element for build the presentation form inside this element. this takes precedence of the presentation_form attribute */
    private PresentationFormAction form;
    
	
    private PreProcesses pre_processes;
    private PostProcesses post_processes;

	private IExecContext execContext;

	private StorageConfig storageConfig;

	private Database database;

	private Table table;

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
//	private boolean enforce_concurrency = true;
	

	public String execute(IExecContext execContext) throws Exception
	{

		this.execContext = execContext;

		this.validateStorage("pager:add");

        storageConfig = (StorageConfig) execContext.get(getStorage_config_ref(execContext));
		Validate.notNull(storageConfig, "No [" + StorageConfig.class.getName() + "] found in ExecContext ["
                + getStorage_config_ref(execContext) + "]");
		StorageContainer storageContainer = storageConfig.getStorageContainer();
		Storage storage = storageContainer.getStorage();

		database = storage.getDatabase(storageConfig.getDatabaseName());

		table = database.getTable(getTable_name());

		setTheme(execContext.getThemes().getTheme(getTheme_name(execContext)));

		Html output;
		try {
			output = buildInsertPresentation(execContext, database, table, getTheme(execContext));
		} catch (DBConfigException ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
		
		rememberTheseValues(execContext);
		rememberPreAndPostProcesses();
		execContext.put(IExecContext.PERSISTENCE_MAP + ":" + VersionNumberConcurrency.buildVersionNumberKeyWithPkValue(getId(), null), rememberMap);

		return output.toString();
	}

	private Html buildInsertPresentation(IExecContext execContext, Database database, Table table, Theme theme)
			throws DBConfigException, Exception
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

        HtmlDiv mainDiv = new HtmlDiv(theme);
       	HtmlForm htmlForm = new HtmlForm();
       	htmlForm.setOnSubmit("return(showValidationErrors(saveRecord('" + getId() + "')));");
	        
		HtmlInput formSubmit = new HtmlInput();
        formSubmit.setClazz(theme.getValue(ThemeConst.INPUT_TEXT.toString()));
        formSubmit.setType("submit");
        formSubmit.setValue(PagerConstants.LANG_KEY_GO);
        formSubmit.setStyle("position:absolute;left:-5000px");
        htmlForm.addChild(formSubmit);
        htmlForm.addChild(mainDiv);

        mainDiv.setId(getId());
        // ===
        // Add Any Hidden Fields
        // ===
        for (HtmlInput input : getHiddenFields()) {
            mainDiv.addChild(input);
        }

        
		HtmlTable htmlTable = null;
		HtmlTable root = null;
        if (StringUtils.isEmpty(getPresentation_form()) && getForm() == null) {
			//root = FormDrawingUtils.startFrame(this);
			root = startFrame(theme);
			mainDiv.addChild(root);
			HtmlTr tr = root.addTr(theme);
			HtmlTd td = tr.addTd(theme);
			htmlTable = td.addTable();
			//htmlTable.setClazz(theme.getValue(ThemeConst.ADDEDIT_BORDER.toString()));
        } else {
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
                    if (field.getPost_format() != null) {
                    	rememberMap.put(ClientParamNames.TABLE_FIELD_NAME + "." + tableFieldName + ".post_format", field.getPost_format());
//                		HtmlInput input = new HtmlInput();
//                		input.setName(tableFieldName + ".post_format");
//                		input.setValue(field.getPost_format());
//                		input.setType("hidden");
//                    	mainDiv.addChild(input);
                    }
                    CommonStorageField storageField;
                    if (Table.isTableAndFieldName(field.getName())) {
                        storageField = database.getStorageField(field.getName());
                    } else {
                        storageField = table.getFieldFromTableAndFieldName(Table.buildTableAndFieldName(getTable_name(), field.getName()));
                    }
                    if (!(storageField instanceof PK)) {
                        // if (!(storageField instanceof PK || storageField instanceof Password)) {
                        Object result = buildAddFieldPresentation(connection,
                                database,
                                table,
                                field,
                                storageConfig.getDbSpecificName(),
                                theme);
                        if (result instanceof HtmlTr[]) {
                        	HtmlTr[] trs = (HtmlTr[])result;
	                        if (trs != null) {
		                        if (StringUtils.isNotEmpty(getPresentation_form()) || getForm() != null) {
			                        Html input = trs[0].getChildren().get(1).getChildren().get(0);
			                        Table fieldTable = (Table)storageField.getParent();
			                        String selectIdentifier = fieldTable.buildSqlFieldName(storageField);
			                    	addToExecContext(execContext, selectIdentifier, input);
		                        } else {
			                        for (HtmlTr htmlTr : trs) {
			                            htmlTable.addChild(htmlTr);
			                        }
		                        }
	                        }
                        }
                    }
                } else if (baseAction instanceof FieldHide) {
                	FieldHide field = (FieldHide)baseAction;
                	Html hiddenInput = field.buildHiddenInput(execContext);
                    if (StringUtils.isNotEmpty(getPresentation_form()) || getForm() != null) {
                    	mainDiv.addChild(hiddenInput);
                    } else {
                    	mainDiv.addChild(hiddenInput);
                    }
                    if (field.getPost_format() != null) {
                    	rememberMap.put(ClientParamNames.TABLE_FIELD_NAME + "." + field.getName() + ".post_format", field.getPost_format());
//                		HtmlInput input = new HtmlInput();
//                		input.setName(field.getName() + ".post_format");
//                		input.setValue(field.getPost_format());
//                		input.setType("hidden");
//                    	mainDiv.addChild(input);
                    }
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
        if (StringUtils.isEmpty(getPresentation_form()) && getForm() == null && foundSubmitLink == false) {
			Validate.isTrue(false, "No 'submit' link has been set for this Add action.");
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
			log.debug("newPage:" + newPage);

			DrawDBHTMLHelper.addLinksAndButtonsToExecContext(execContext, this, theme, "right");

			mainDiv.setContent(newPage);
			
		} else {
			root.addChild(DrawDBHTMLHelper.buildLinksAndButtons(execContext, this, theme, "right"));
		}		

        if (StringUtils.isEmpty(getPresentation_form()) && getForm() == null) {
        	return htmlForm;
        } else {
        	return mainDiv;
        }
	}

	private Html[] buildPresentation(IExecContext execContext, CommonFormFields formField, CommonStorageField storageField, String value, Theme theme) {
		return displayForAdd(execContext, getRow_map_name(), formField, storageField, value, theme);
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
	
    private Html[] old_buildPresentation(IExecContext execContext, CommonFormFields field, CommonStorageField storageField, String value, Theme theme)
	{

		Html[] output;
		
		if (storageField instanceof IDrawField) {
            IDrawField iDrawField = (IDrawField) storageField;
            output = iDrawField.displayForAdd(value, theme);
		} else {
			throw new IllegalArgumentException("Unknown Storage Field Type [" + storageField.getClass().getName()
					+ "], unable to build presentation.");
		}
		return output;
	}
	
    private Html[] buildAddFieldPresentation(Connection connection, Database database, Table table, Field field,
            String dbSpecificName, Theme theme) throws DBSQLException, DBConfigException {
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
                    params = sql.getListOfParams(execContext);
                    Validate.notEmpty(sqlQuery, "No SQL found for sql_populator [" + sqlPopulator.getRef() + "]");
                    sqlQuery = execContext.replace(sqlQuery);
                }
            } else {
                throw new IllegalArgumentException("Unknown populator type [" + populator.getClass().getName() + "]"); 
            }
            String selectedValue = PagerWebConst.buildRequestKey(selectIdentifier);
            selectedValue = RioStringUtils.convertToString(execContext.get(selectedValue));
            DBSQL dbSQL = new DBSQL();
            dbSQL.setIncludeIndex(false);
            // dbSQL.setIncludeIndex(false);
            XMLObject xo = dbSQL.query2XMLObject(connection, sqlQuery, "root", null, null, params);
            field.setPopulatorXml(xo.mapXMLObject2XML(xo));
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
            //if (StringUtils.isNotEmpty(getPresentation_form())) {
            //	addToExecContext(execContext, parentTableAndPkName, htmlSelect);
            //} else {
                if (storageField instanceof SelectHtml) {
                	trs = buildPresentation(execContext, field, storageField, keyPreviousValue, theme);
                } else {
                    trs = DrawHtmlField.displayForSelect(execContext,
                            storageField,
                            getLabel_position(),
                            theme,
                            htmlSelect);
                }
                //if (StringUtils.isNotEmpty(getPresentation_form())) {
                //	addToExecContext(execContext, parentTableAndPkName, trs);
                //	trs = null;
                //}
            //}
        } else {
            //if (StringUtils.isNotEmpty(getPresentation_form())) {
            //	Html html = null;
        	//	if (storageField instanceof IDrawField) {
        	//		html = ((IDrawField) storageField).buildUpdateHtml(keyPreviousValue, theme);
        	//	} else {
        	//		throw new IllegalArgumentException("Unknown Storage Field Type [" + storageField.getClass().getName()
        	//				+ "], unable to build presentation.");
        	//	}
            //   Table fieldTable = (Table)storageField.getParent();
            //  String selectIdentifier = fieldTable.buildSqlFieldName(storageField);
            //	addToExecContext(execContext, selectIdentifier, html);
            //	
            //} else {           
            	trs = buildPresentation(execContext, field, storageField, keyPreviousValue, theme);
            //}
        	
        }
        return trs;
	}

//	public String toString()
//	{
//
//        return "add storage_config_ref [" + getStorage_config_ref(execContext) + "] table_name [" + getTable_name() + "]";
//	}

	public FieldList getField_list()
	{

		if (field_list == null) {
			return new FieldList();
		}

		return field_list;
	}

	public void setField_list(FieldList fieldList)
	{

		field_list = fieldList;
	}

	public List<HtmlInput> getHiddenFields()
	{
		java.util.List<HtmlInput> inputs = new ArrayList<HtmlInput>();
        inputs.add(buildHiddenInput(ClientParamNames.UNIQUE_ID, VersionNumberConcurrency.buildVersionNumberKeyWithPkValue(getId(), null)));
        return inputs;
	}


	public void rememberPreAndPostProcesses()
	{

        if (getPre_processes() != null) {
            int keyIndex = 1;
            for (CodeAction processor : getPre_processes().getProcessors()) {
                String key = ClientParamNames.PRE_PROCESSOR + "." + keyIndex++;
        		rememberMap.put(key, processor.getCall());
                int paramIndex = 1;
                for (Param param : processor.getParams()) {
                    String paramKey = key + "." + paramIndex++;
                	rememberMap.put(paramKey, param.getValue());
                }
            }
        }

        if (getPost_processes() != null) {
            int keyIndex = 1;
            for (CodeAction processor : getPost_processes().getProcessors()) {
                String key = ClientParamNames.POST_PROCESSOR + "." + keyIndex++;
                rememberMap.put(key, processor.getCall());
                int paramIndex = 1;
                for (Param param : processor.getParams()) {
                    String paramKey = key + "." + paramIndex++;
                	rememberMap.put(paramKey, param.getValue());
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
		// rememberMap.put(ClientParamNames.PK_VALUE, getPk_value());
	}

	public IExecContext getExecContext()
	{

		return this.execContext;
	}

	public void validateStorage(String errMsg)
	{
        Validate.notEmpty(getId(), "id has not been set - " + errMsg);
        Validate.notEmpty(getStorage_config_ref(execContext), "storage_config_ref has not been set - " + errMsg);
		Validate.notEmpty(getTable_name(), "table_name has not been set - " + errMsg);
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

	public PresentationFormAction getForm() {
		return form;
	}

	public void setForm(PresentationFormAction form) {
		this.form = form;
	}

	public String getRow_map_name() {
		return row_map_name;
	}

	public void setRow_map_name(String row_map_name) {
		this.row_map_name = row_map_name;
	}

}