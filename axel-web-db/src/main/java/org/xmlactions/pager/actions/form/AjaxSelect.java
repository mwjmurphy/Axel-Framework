package org.xmlactions.pager.actions.form;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.DBConfigException;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.pager.actions.form.FormDrawing;
import org.xmlactions.pager.actions.form.IStorageFormAction;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlSelect;
import org.xmlactions.web.PagerWebConst;


/**
 * Builds a html select with options.
 * <p>
 * Requires the following parameters<br/>
 * ClientParamNames.STORAGE_CONFIG_REF<br/>
 * ClientParamNames.TABLE_NAME_MAP_ENTRY<br/>
 * ClientParamNames.FIELD_NAME_MAP_ENTRY<br/>
 * ClientParamNames.THEME_NAME_MAP_ENTRY<br/>
 * ClientParamNames.PARENT_TABLE_AND_PK_NAME<br/>
 * ClientParamNames.LABEL_POSITION_MAP_ENTRY<br/>
 * </p>
 * <p>
 * returns an html select for insertion or update into the browser dom.
 * </p>
 * @author Mike Murphy
 *
 */
public class AjaxSelect extends SelectForm implements FormDrawing, IStorageFormAction {

	private static final Logger logger = LoggerFactory.getLogger(AjaxSelect.class);

	private String field_name;

	private String parent_table_and_pk_name;





	public String execute(IExecContext execContext) throws Exception
	{

		String result = "OK";
		try {
			result = buildSelect(execContext);
		} catch (Exception ex) {
			result = "EX:" + ex.getMessage();
			logger.error(ex.getMessage(), ex);
		}
		return result;
	}


	public String buildSelect(IExecContext execContext) throws Exception {
		setExecContext(execContext);
		getHttpRequestParams(execContext);
		this.validateStorage("pager:AjaxSelect");

        setStorageConfig((StorageConfig) execContext.get(getStorage_config_ref(execContext)));
		Validate.notNull(getStorageConfig(), "No [" + StorageConfig.class.getName() + "] found in ExecContext ["
                + getStorage_config_ref(execContext) + "]");
		StorageContainer storageContainer = getStorageConfig().getStorageContainer();
		Storage storage = storageContainer.getStorage();

		setDatabase(storage.getDatabase(getStorageConfig().getDatabaseName()));

		setTable(getDatabase().getTable(getTable_name()));

		setTheme(execContext.getThemes().getTheme(getTheme_name(execContext)));

		HtmlSelect htmlSelect;
		try {
			htmlSelect = buildSearchPresentation(execContext, getDatabase(), getTable(), getTheme(execContext), getField_name(), getParentTableAndPkName());
		} catch (DBConfigException ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
		return htmlSelect.toString();

	}
	private HtmlSelect buildSearchPresentation(IExecContext execContext, Database database, Table table, Theme theme, String fieldName, String parentTableAndPkName)
	throws DBConfigException, DBSQLException
	{
		StorageContainer storageContainer = getStorageConfig().getStorageContainer();
		Storage storage = storageContainer.getStorage();
		DBConnector dbConnector = getStorageConfig().getDbConnector();
		String sqlQuery = null;
		Connection connection;
		try {
			// connection = dataSource.create().getConnection();
			connection = dbConnector.getConnection(execContext);
		} catch (Throwable e) {
			throw new IllegalArgumentException("Cannot get database connection using storage_config_ref:"
                    + getStorage_config_ref(execContext), e);
		}
        Validate.notNull(connection, "No Connection for data_source [" + getStorage_config_ref(execContext)
                + "] in ExecContext");

		Field field = new Field();
		field.setName(fieldName);
		HtmlSelect htmlSelect = null;
		try {
			logger.debug("field:" + field.getName());
			htmlSelect = buildSearchPresentationFromField(execContext, connection, storage, field, theme, getLabel_position(), parentTableAndPkName);
		} catch (DBConfigException ex) {
			throw new IllegalArgumentException("Unable to build query for [" + getTable_name() + "]\n"
					+ ex.getMessage(), ex);
		} catch (SQLException ex) {
			throw new IllegalArgumentException("Query Error for [" + sqlQuery + "]\n" + ex.getMessage(), ex);
		} finally {
			DBConnector.closeQuietly(connection);
		}
		return htmlSelect;
	}


	
    public void validateStorage(String errMsg) {
        Validate.notEmpty(getStorage_config_ref(), ClientParamNames.STORAGE_CONFIG_REF
                + " has not been set - " + errMsg);
		Validate.notEmpty(getTable_name(), ClientParamNames.TABLE_NAME_MAP_ENTRY + " has not been set - " + errMsg);
		Validate.notEmpty(getField_name(), ClientParamNames.FIELD_NAME_MAP_ENTRY + " has not been set - " + errMsg);
	}

	public IExecContext getExecContext() {
		return getExecContext();
	}


	public List<HtmlInput> getHiddenFields() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getField_name() {
		return field_name;
	}

	public void getHttpRequestParams(IExecContext execContext) {
		this.setStorage_config_ref(PagerWebConst.getRequestParameter(execContext, ClientParamNames.STORAGE_CONFIG_REF));
		this.setTable_name(PagerWebConst.getRequestParameter(execContext, ClientParamNames.TABLE_NAME_MAP_ENTRY));
		this.setField_name(PagerWebConst.getRequestParameter(execContext, ClientParamNames.FIELD_NAME_MAP_ENTRY));
		this.setTheme_name(PagerWebConst.getRequestParameter(execContext, ClientParamNames.THEME_NAME_MAP_ENTRY));
		this.setParentTableAndPkName(PagerWebConst.getRequestParameter(execContext, ClientParamNames.PARENT_TABLE_AND_PK_NAME));
		this.setLabel_position(PagerWebConst.getRequestParameter(execContext, ClientParamNames.LABEL_POSITION_MAP_ENTRY));
		
        logger.debug("storage_config_ref:" + getStorage_config_ref(execContext) +
				"\ntable_name:" + getTable_name() +
				"\nfield_name:" + getField_name() +
				"\ntheme_name:" + getTable_name() +
				"\nparentTableAndPkName:" + getParentTableAndPkName() +
				"\nlabel_position:" + getLabel_position());
	}



	public void setParentTableAndPkName(String parentTableAndPkName) {
		this.parent_table_and_pk_name = parentTableAndPkName;
	}


	public String getParentTableAndPkName() {
		return parent_table_and_pk_name;
	}

}
