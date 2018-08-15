package org.xmlactions.pager.actions.form;

/**
 \page action_list Action List
 \anchor list
 
 \tableofcontents

 A list action retrieves information from a database and presents it on screen.

 \section action_list_properties List Properties
 Action:<strong>list</strong>

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
	 	<td>field_list<br/><small>- required</small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			Specifies a list of data source fields.  
		</td>
	</tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	<tr>
	 	<td>link<br/><small>- optional</small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			Specifies one or more href links.
		</td>
	</tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	<tr>
	 	<td>pager:popup<br/><small>- optional</small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
            Specifies one or more popup windows that is populated from a server page uri.
		</td>
	</tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	<tr>
	 	<td>form<br/><small>- optional</small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			If this is set then the content of this form (which is html) is used to build the presentation of the data for this action.
        	This "form" element takes precedence over the "presentation_form" attribute. 
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
			A unique id for this list.  If the id is not unique and more than one list is used on a page then there may be conflicts between both lists using the same id. 
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
	 	<td>\ref schema_pager_attributes_storage_config_ref<br/><small><i>- optional<br/></i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
	 		This is the bean reference to the StorageConfig to use for this action.
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	<tr>
	 	<td>\ref schema_pager_attributes_table_name<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			This is the name of the table in the database.
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	<tr>
	 	<td>\ref schema_pager_attributes_sql<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			This is an sql statement or a reference to a stored sql statement. 
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>join<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			This join will be added to the select statement. It can be left join, right join, center join.<br/>
			
			A left join can be used to retrieve data that is not in a table.<br/>
			Example:<br/>
			&nbsp;&nbsp;&nbsp;SELECT table1.*<br/>
            &nbsp;&nbsp;&nbsp;FROM table1<br/>
            &nbsp;&nbsp;&nbsp;LEFT JOIN table2<br/>
            &nbsp;&nbsp;&nbsp;ON table1.id = table2.id<br/>
            &nbsp;&nbsp;&nbsp;WHERE table2.id IS NULL;<br/>
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>
	
	<tr>
	 	<td>where<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			A where is used to select the data returned from a select.<br/>

			Do not provide the 'where' syntax for the where clause. Instead only provide the conditions of the where clause.<br/>

            Example:<br/>
			&nbsp;&nbsp;&nbsp;tb1.id=tb2.id<br/>
            &nbsp;&nbsp;&nbsp;or<br/>
            &nbsp;&nbsp;&nbsp;tb1.name like 'fred' 
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>order_by<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			An order_by is used to order the data returned from a select.<br/>

			Do not provide the 'order by' syntax for the order by clause. Instead only provide the conditions of the order by clause.<br/>

			Example:<br/>
			&nbsp;&nbsp;&nbsp;TB_FIELD DESC<br/>
            &nbsp;&nbsp;&nbsp;or<br/>
			&nbsp;&nbsp;&nbsp;TB_FIELD, TB_DATE DESC 
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>group_by<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			A  group_by is used to group the data returned from a select.<br/>

			Do not provide the 'group by' syntax for the group by clause. Instead only provide the field names.<br/>

			Example:<br/>
			&nbsp;&nbsp;&nbsp;TB_FIELD<br/>
			&nbsp;&nbsp;&nbsp;or<br/>
			&nbsp;&nbsp;&nbsp;TB_FIELD, TB_DATE 
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>\ref schema_pager_attributes_theme_name<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			Is optional if the theme is provided by a parent action.<br/> 

			Or set in a configuration file using the key 'default_theme_name'.<br/>
			i.e. default_theme_name=blue 
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
	 	<td>\ref schema_pager_attributes_title<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			Title displayed for this form. If this is empty	no title will be displayed.
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>header_align<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			The header alignment setting, this will be applied to all headers in the table list.<br/>
			Options are center, left, right.
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>rows<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			The number of table rows to display on screen.
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>page<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			The page number to retrieve from the table. The page is a value of rows * page and references the first row to return form the query.
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>row_height<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
                  The height of each row in the list presentation.
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

	<tr>
	 	<td>\ref schema_pager_attributes_presentation_form<br/><small><i>- optional</i></small></td>
		<td style="padding-left:10px;border-left:1px solid #a4bcea;">
			The file name of a presentation form to draw into.<br/>
		</td>
	 </tr>
	<tr>
 		<td colspan="2" height="6px"/>
	</tr>

</table>

 \section action_list_example List Example

 An example list might look like
 \code{.xml}
	<db:list order_by="fe.endpoint_id" width="800px" row_height="30px" rows="5" table_name="fe">
		<db:field_list>
			<db:field name="fe.endpoint_id" />
			<db:field name="fe.url" />
			<db:field name="fe.enabled" />
			<db:field name="fe.down_until" />
		</db:field_list>
	</db:list>
 \endcode

 \see
  \ref action_listcp<br/>
  \ref axel_actions_list<br/>
  \ref org_xmlactons_action_actions
 
*/


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.text.StrSubstitutor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceCommon;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.number.IntegerUtils;
import org.xmlactions.common.text.XmlCData;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.common.xml.XMLAttribute;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.DBConfigException;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.DbSpecific;
import org.xmlactions.db.actions.Sql;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.db.query.QueryBuilder;
import org.xmlactions.db.sql.BuildSelect;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;
import org.xmlactions.mapping.json.JSONUtils;
import org.xmlactions.pager.actions.PopupAction;
import org.xmlactions.pager.actions.SelfDraw;
import org.xmlactions.pager.actions.form.drawing.DrawFormFields;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTh;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.actions.formatter.PreFormatter;
import org.xmlactions.pager.config.PagerConstants;
import org.xmlactions.pager.drawing.IDrawField;
import org.xmlactions.pager.drawing.IDrawParams;
import org.xmlactions.pager.drawing.html.TextHtml;
import org.xmlactions.web.PagerWebConst;


/**
 * Builds a html list display from a query.
 * <p>
 * parameters / attributes will build the the html attributes needed for display.
 * </p>
 * 
 * @author mike
 * 
 */
public class List extends DrawFormFields implements FormDrawing, IStorageFormAction
{
	/** It is possible to override the setting for the following list attributes by passing the values in the browser request parameters. */
	public enum request_parameters {
		where,		// - A where is used to select the data returned from a select.
					// Do not provide the 'where' syntax for the where clause. Instead only provide the conditions of the where clause.
					// Example: tb1.id=tb2.id or tb1.name like 'fred'
		order_by,	// -	An order_by is used to order the data returned from a select.
					// Do not provide the 'order by' syntax for the order by clause. Instead only provide the conditions of the order by clause.
					// example: TB_FIELD DESC or TB_FIELD, TB_DATE DESC
		group_by,	// -	A group_by is used to group the data returned from a select.
					// Do not provide the 'group by' syntax for the group by clause. Instead only provide the field names.
		   			// example: TB_FIELD or TB_FIELD, TB_DATE
		rows,		// - The number of table rows to display on screen.
		page		// - The page number to retrieve from the table. The page is a value of rows * page and references the first row to return form the query.
	}
	public enum format {
		html,
		json,
		xml
	}

	private static final Logger log = LoggerFactory.getLogger(List.class);

	private static final String DEFAULT_PRESENTATION_OPTION = "row";

	private static final String ALIGN = "center";

	private static final int ROWS_DEFAULT_VALUE = 10;

	private String row_map_name="row";
	
	private FieldList field_list;


	/**
	 * number of rows to return for a query
	 */
	private String rows;

    /**
     * If set true (default) an index column will be added at the beginning of
     * the row display.
     */
    private boolean row_index = true;

	/**
	 * page to start the query. the actual value is page * rows + page
	 */
	private int page;

	private String header_align;

	private String row_height;

	private String join;

	private String where;

    private String order_by;

    private String group_by;

	/**
	 * total rows returned for count(*) of query. This will only have a valid value if there was one or more rows
	 * returned for the query.
	 */
	private int totalRows;
	
	/** An option to present each row on a presentation page. */
	private String presentation_form;

	/** Can include a form element for build the presentation form inside this element. this takes precedence of the presentation_form attribute */
	private PresentationFormAction form;

	private String output_format = List.format.html.toString();

	/** If this is set true then the outer html element is removed from the presentation form. */
	private boolean remove_html = true;	// true or false, yes or no.
	
	/** If this is set with a value then the result of the list is stored in the execContext with this key */
	private String key;

	// ===
	// Locally Populated Variables
	// ===
	IExecContext execContext;

	private StorageConfig storageConfig;

	private Database database;

	private Table table;

	private String sqlQuery;

	private java.util.List<SqlField> sqlParams = new ArrayList<SqlField>();
	
	/**
	 *  These get set if a total_record_count_sql and total_record_count_field attriutes are set in the storage.database.db_specific element.
	 */
	private String total_record_count_sql = null, total_record_count_field = null;


    public String execute(IExecContext execContext) throws DBSQLException, NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException, IOException
	{

		this.execContext = execContext;
		
		getRequestParameters(execContext);

		this.validateStorage(execContext, "pager:list");

		totalRows = 0; // we haven't gotten a result for the query yet

        storageConfig = (StorageConfig) execContext.get(getStorage_config_ref(execContext));
		Validate.notNull(storageConfig, "No [" + StorageConfig.class.getName() + "] found in ExecContext ["
                + getStorage_config_ref(execContext) + "]");
		StorageContainer storageContainer = storageConfig.getStorageContainer();

		DbSpecific dbSpecific = storageConfig.getDBSpecific();
		if (StringUtils.isNotBlank(dbSpecific.getTotal_record_count_sql()) && StringUtils.isNotBlank(dbSpecific.getTotal_record_count_field()) ) {
			total_record_count_sql = dbSpecific.getTotal_record_count_sql();
			total_record_count_field = dbSpecific.getTotal_record_count_field();
		}

		Storage storage = storageContainer.getStorage();

		database = storage.getDatabase(storageConfig.getDatabaseName());

		// ===
		// Can have a table or an sql
		// ===
		if (StringUtils.isEmpty(getTable_name())) {
			if (!StringUtils.isEmpty(execContext.getString(getSql()))) {
				sqlQuery = execContext.getString(getSql());
				sqlQuery = execContext.replace(sqlQuery);
			} else {
				String sqlRef = getSql();
				Sql s = database.getSql(storageConfig.getDbSpecificName(), sqlRef);
				sqlParams = s.getListOfParams(execContext);
				sqlQuery = execContext.replace(s.getSql());
			}
			sqlQuery = XmlCData.removeCData(sqlQuery);
		} else {
			table = database.getTable(getTable_name());
		}

		Theme theme = execContext.getThemes().getTheme(getTheme_name(execContext));
		setTheme(theme);

		DBConnector dbConnector = storageConfig.getDbConnector();

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

		String result = "";
		String sqlCount = null;
		XMLObject xo = null;
        String output = null;
        String manualSql = null;
		try {
			if (StringUtils.isNotEmpty(getSql())) {
				manualSql = dbSpecific.getSql(getSql()).getSql();
				sqlParams = dbSpecific.getSql(getSql()).getListOfParams(execContext);
			}
			if (StringUtils.isNotEmpty(getTable_name())) {
				buildFieldListIfNeeded(storageConfig, getTable_name(), getField_list());
                java.util.List<SqlField> sqlFields = FormUtils.buildTableAndFieldNamesAsList(table,
						getField_list().getFields());
				SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage,
						storageConfig.getDatabaseName(),
						getTable_name(),
                        sqlFields, storageConfig.getDbSpecificName());
				QueryBuilder.buildWhereClause(execContext, sqlFields, sqlSelectInputs);
				if (StringUtils.isEmpty(getWhere()) == false) {
					log.debug(" and " + getWhere());
					sqlSelectInputs.addWhereClause(getWhere());
				}
				
                if (StringUtils.isNotEmpty(getOrder_by())) {
				    sqlSelectInputs.addOrderByClause(order_by);
				}

                if (StringUtils.isNotEmpty(getGroup_by())) {
				    sqlSelectInputs.addGroupByClause(group_by);
				}

                ISqlSelectBuildQuery builder = storageConfig.getSqlBuilder();
              	builder.setSql(manualSql);

        		if (getParent() instanceof ListCP && StringUtils.isBlank(total_record_count_sql) && StringUtils.isBlank(total_record_count_field) ) {
        			sqlCount = sqlCount(builder.buildSelectQuery(execContext, sqlSelectInputs, sqlParams));
        			sqlParams = new ArrayList<SqlField>();
        		}

				int from = (this.page - 1) * getRowsAsInt(execContext);
				int to = getRowsAsInt(execContext) + from;
				from++;
				sqlSelectInputs.setLimitFrom("" + from);
				sqlSelectInputs.setLimitTo("" + to);
				log.debug("limitFrom:" + sqlSelectInputs.getLimitFrom() + " limitTo:" + sqlSelectInputs.getLimitTo());
                sqlQuery = builder.buildSelectQuery(execContext, sqlSelectInputs, sqlParams);

			} else if (getParent() instanceof ListCP) {
				sqlCount = sqlCount(sqlQuery);
			}
			log.debug(sqlQuery);

			DBSQL dbSQL = new DBSQL();

    		if (sqlCount != null) {
				totalRows = Integer.parseInt(DBSQL.queryOne(connection, sqlCount, sqlParams));
			} else {
				totalRows = 0;
			}
			xo = dbSQL.query2XMLObject(connection, sqlQuery, "root", null, null, sqlParams);
			if (log.isDebugEnabled()) {
				
				String string = xo.mapXMLObject2XML(xo, true);
				int length = string.length() > 1000 ? 1000 : string.length();
						
				log.debug("sql result [1000 chars]:" + string.substring(0,length));
			}
			
    		if (StringUtils.isNotBlank(total_record_count_sql) && StringUtils.isNotBlank(total_record_count_field) ) {
    			if (xo.getChildCount() > 0) {
    				Object obj = xo.getChild(0).getAttributeValueAsString(total_record_count_field);
    				if (obj != null) {
    					totalRows = Integer.parseInt(xo.getChild(0).getAttributeValueAsString(total_record_count_field));
    				}
    			}
    		} 
    		if (totalRows == 0 && xo != null){
    			totalRows =  Integer.parseInt(xo.getAttributeValueAsString(DBSQL.NUM_ROWS));
    		}
			log.debug("totalRows:" + totalRows);
			execContext.put(PagerConstants.ROW_TOTAL_COUNT, totalRows);
			PreFormatter.preFormatResult(execContext, xo, getField_list());
			if (getOutput_format().equals(format.xml.toString())) {
				output = xo.mapXMLObject2XML(xo);
				log.debug("xml output:" + output);
				if (log.isDebugEnabled()) {
					int length = output.length() > 1000 ? 1000 : output.length();
					log.debug("xml output [1000 chars]:" + output.substring(0, length));
				}
			} else if (getOutput_format().equals(format.json.toString())) {
				JSONObject jsonObject = JSONUtils.mapXmlToJson(xo.mapXMLObject2XML(xo), true);
				output = jsonObject.toString();
				if (log.isDebugEnabled()) {
					int length = output.length() > 1000 ? 1000 : output.length();
					log.debug("json output [1000 chars]:" + output.substring(0,length));
				}
			} else {
				// default is html
				output = buildPresentation(xo, theme, database, table, execContext);
			}

		} catch (DBConfigException ex) {
			throw new IllegalArgumentException("Unable to build query for [" + getTable_name() + "]\n"
					+ ex.getMessage(), ex);
        } catch (DBSQLException ex) {
			throw new IllegalArgumentException("Query Error for [" + sqlQuery + "]\n" + ex.getMessage(), ex);
		} finally {
			DBConnector.closeQuietly(connection);
		}
		
		execContext.put(getRow_map_name() + ":" + PagerConstants.ROW_TOTAL_COUNT , totalRows);
		
		if (StringUtils.isNotBlank(getKey())) {
			if (log.isDebugEnabled()) {
				int length = output.length() > 1000 ? 1000 : output.length();
				log.debug("execContext.put(" + getKey() + "," + output.substring(0,length) + ");");
			}
			execContext.put(getKey(), output);
			output = "";
		}
		return output;
	}

	private void buildFieldListIfNeeded(StorageConfig storageConfig, String tableName,
			FieldList fieldList)
	{
		Database db = storageConfig.getStorageContainer().getStorage().getDatabase(storageConfig.getDatabaseName());
		boolean haveField = false;
		for (BaseAction field : fieldList.getFields()) {
			if (field instanceof Field) {
				haveField = true;
				break;
			}
		}
		if (haveField == false) {
			Table table = db.getTable(tableName);
			for (CommonStorageField dbField : table.getFields()) {
				Field field = new Field();
				field.setName(dbField.getName());
				fieldList.setField(field);
			}
		}
		if (StringUtils.isNotBlank(total_record_count_sql) && StringUtils.isNotBlank(total_record_count_field) ) {
			Field field = new Field();
			field.setName(total_record_count_field);
			field.setSql(total_record_count_sql);
			fieldList.setField(field);
		}
		
	}
	

	private String sqlCount(String sql)
	{

		return "select count(*) from (" + sql + ") tb";
	}

    private String buildWhereClause(IExecContext execContext, java.util.List<SqlField> sqlFields,
			SqlSelectInputs sqlSelectInputs)
	{
    	
		StringBuilder sb = new StringBuilder();
        for (SqlField sqlField : sqlFields) {
            String key = PagerWebConst.buildRequestKey(sqlField.getFieldName());
			String value = (String) execContext.get(key);
			if (!StringUtils.isEmpty(value)) {
				if (value.toLowerCase().startsWith("between ")) {
                    log.debug(" and " + sqlField.getFieldName() + " " + value);
                    sqlSelectInputs.addWhereClause(sqlField.getFieldName() + " " + value);
				} else {
					char c = value.charAt(0);
					if (c == '=' || c == '>' || c == '<' || c == '!') {
	                    log.debug(" and " + sqlField.getFieldName() + " " + value);
	                    sqlSelectInputs.addWhereClause(sqlField.getFieldName() + " " + value);
					} else if (c == '\'') {
	                    log.debug(" and " + sqlField.getFieldName() + " like " + value);
	                    sqlSelectInputs.addWhereClause(sqlField.getFieldName() + " like " + value);
					} else {
	                    log.debug(" and " + sqlField.getFieldName() + " like '" + value + "'");
	                    sqlSelectInputs.addWhereClause(sqlField.getFieldName() + " like '" + value + "'");
					}
				}
			}
		}
		if (StringUtils.isEmpty(getWhere()) == false) {
			log.debug(" and " + getWhere());
			sqlSelectInputs.addWhereClause(getWhere());
		}
		return sb.toString();

	}

	/**
	 * total rows returned for count(*) of query. This will only have a valid value if there was one or more rows
	 * returned for the query.
	 */
	public int getTotalRows()
	{

		return totalRows;
	}

	public void setTotalRows(int totalRows)
	{

		this.totalRows = totalRows;
	}

	private String buildPresentation(XMLObject xo, Theme theme, Database database, Table table,
			IExecContext execContext) throws DBConfigException, NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException, IOException
	{

		if (getForm() != null) {
			String output = buildPresentationIntoForm(xo, theme, database, table, execContext, XmlCData.removeCData(getForm().getContent()));
			return output;
		} else if (StringUtils.isNotEmpty(getPresentation_form())) {
			String output = buildPresentationIntoFormFromFile(xo, theme, database, table, execContext, getPresentation_form());
			if (isRemove_html()) {
				output = org.xmlactions.common.text.Html.removeOuterHtml(output);
			}
			return output;
		} else {
	        HtmlTable htmlTable = startFrame(theme);
	        if (! (this.getParent() instanceof ListCP)) {
	        	htmlTable.setClazz(ThemeConst.LIST_TABLE.toString());
	        }
	
			int numRows = Integer.parseInt(xo.getAttributeValueAsString(DBSQL.NUM_ROWS));
			if (numRows > 0) {
	
				//if (isPresentationRow()) {
					HtmlTr trh = drawHeaders(theme);
					htmlTable.addChild(trh);
				//}
				for (int i = 0; i < numRows; i++) {
	
					XMLObject row = xo.getChild(i);
					addRowToExecContext(execContext, row);
					//if (isPresentationRow()) {
						HtmlTr tr = drawRowPresentationAsRow(execContext, row, i, theme);
						htmlTable.addChild(tr);
					//} else {
					//	HtmlTr tr = drawRowPresentationAsBox(row, i);
					//	htmlTable.addChild(tr);
					//}
	
				}
			}

            // Map<String, Object> map = execContext.getNamedMap(PagerConstants.ROW_MAP_NAME);
			HtmlTr tr = htmlTable.addTr();
			HtmlTd td = tr.addTd();
			td.setColspan("100");
	        HtmlTable linksTable = td.addTable();
	        HtmlTr linksTr = linksTable.addTr();
			Html[] As = buildLinks(execContext, getLinks(), theme);
			for (Html a : As) {
				HtmlTd linkTd = linksTr.addTd();
				linkTd.addChild(a);
			}
			for (BaseAction baseAction : getActions()) {
				if (baseAction instanceof SelfDraw) {
					Html selfDraw = (((SelfDraw)baseAction).drawHtml(execContext));
					HtmlTd linkTd = linksTr.addTd();
					linkTd.addChild(selfDraw);
				}
			}
			return htmlTable.toString();
		}
	}

	private HtmlTr drawRowPresentationAsRow(IExecContext execContext, XMLObject row, int rowIndex, Theme theme)
	{

		HtmlTr tr = drawRowTheme(rowIndex, theme);
        if (row_index) {
            CommonStorageField field = buildRowIndexField();
            HtmlTd td = new HtmlTd(theme, ThemeConst.LIST_TD);
            td.setContent("" + ((rowIndex + 1) + ((page - 1) * getRowsAsInt(execContext))));
            tr.addChild(td);
        }
        for (BaseAction action : getField_list().getFields()) {
            drawAction(action, tr, row, rowIndex,theme);
        }
        for (BaseAction action : getField_list().getLinks()) {
            drawAction(action, tr, row, rowIndex, theme);
        }
		return tr;
	}


	private String drawRowPresentationAsForm(IExecContext execContext, String form, XMLObject row, int rowIndex, Theme theme) throws DBConfigException
	{
        int index = ((rowIndex + 1) + ((page - 1) * getRowsAsInt(execContext)));
		Map<String, String>map = new HashMap<String, String>();
		map.put(getRow_map_name() + ":row_index", "" + index);
        for (BaseAction action : getField_list().getFields()) {
        	Html html = drawActionForPresentationForm(map, action, row, rowIndex, theme);
        	if (html != null) {
	        	if (action instanceof Field) {
	        		map.put(getRow_map_name() + ":" + ((Field)action).getName(), html.toString());
	        	} else if (action instanceof DeleteRecordLink && StringUtils.isNotEmpty(((DeleteRecordLink)action).getId())) {
	        		map.put(getRow_map_name() + ":" + ((DeleteRecordLink)action).getId(), html.toString());
	        	} else if (action instanceof UpdateRecordLink && StringUtils.isNotEmpty(((UpdateRecordLink)action).getId())) {
	        		map.put(getRow_map_name() + ":" + ((UpdateRecordLink)action).getId(), html.toString());
	        	} else if (action instanceof AddRecordLink && StringUtils.isNotEmpty(((AddRecordLink)action).getId())) {
	        		map.put(getRow_map_name() + ":" + ((AddRecordLink)action).getId(), html.toString());
	        	} else if (action instanceof PopupAction) {
	        		String id = ((PopupAction) action).getId() != null ? ((PopupAction) action).getId() : ((PopupAction) action).getName(); 
	        		map.put(getRow_map_name() + ":" + id, html.toString());
	        	}
        	}
        }
        for (BaseAction action : getField_list().getLinks()) {
            Html html = drawActionForPresentationForm(map, action, row, rowIndex, theme);
            if (action instanceof Link) {
            	if (((Link)action).getId() != null) {
            		map.put(getRow_map_name() + ":" + ((Link)action).getId(), html.toString());
            	} else {
            		map.put(getRow_map_name() + ":" + ((Link)action).getName(), html.toString());
            	}
            }
        }
        form = StrSubstitutor.replace(form, map);
        form = execContext.replace(form);
		return form;
	}

    private void drawAction(BaseAction action, HtmlTr tr, XMLObject row, int rowIndex, Theme theme) {
        if (action instanceof Field) {
            Field field = (Field) action;
			// skip TOTALRECORDSCOUNT as this was added by the framework to include it in each row of the result set.
			if (StringUtils.isNotBlank(total_record_count_field) && ((Field) action).getName().equals(total_record_count_field) ) {
				// not to do here
			} else {
	            String tableAndFieldName;
	            if (field.getName().indexOf('.') > 0) {
	                tableAndFieldName = field.getName();
	            } else {
	                tableAndFieldName = Table.buildTableAndFieldName(getTable_name(), ((Field) action).getName());
	            }
	            CommonStorageField storageField = (CommonStorageField) database.getStorageField(tableAndFieldName);
	            Table table = (Table) storageField .getParent();
	            tableAndFieldName = table.buildTableAndFieldName(field.getName());
	            Object value = row.getAttributeValue(tableAndFieldName);
	            //Html html = drawField.displayForList(execContext, field, (String) value, theme);
	            Html [] html = displayForList(execContext, getRow_map_name(), field, storageField, (String)value, theme);
	            //HtmlTd td = drawRowValue(field, html, theme);
	            HtmlTd td = (HtmlTd)html[0];
	            
	            //if (field.getTooltip() != null) {
	            //    td.setTitle(execContext.replace(field.getTooltip()));
	            //}
	            tr.addChild(td);
			}

        } else if (action instanceof AddRecordLink) {
            Html htmlA = ((AddRecordLink) action).draw(execContext, theme);
            HtmlTd td = tr.addTd();
            td.setClazz(theme.getValue(ThemeConst.LIST_TD.toString()));
            td.addChild(htmlA);
        } else if (action instanceof DeleteRecordLink) {
            Html htmlA = ((DeleteRecordLink) action).draw(execContext, theme);
            HtmlTd td = tr.addTd();
            td.setClazz(theme.getValue(ThemeConst.LIST_TD.toString()));
            td.addChild(htmlA);
        } else if (action instanceof UpdateRecordLink) {
            Html htmlA = ((UpdateRecordLink) action).draw(execContext, theme);
            HtmlTd td = tr.addTd();
            td.setClazz(theme.getValue(ThemeConst.LIST_TD.toString()));
            td.addChild(htmlA);
        } else if (action instanceof Link) {
            Link link = (Link) action;
            Html html = link.draw(execContext, theme);
            HtmlTd td = tr.addTd();
            td.setClazz(theme.getValue(ThemeConst.LIST_TD.toString()));
            td.addChild(html);
        } else if (action instanceof SelfDraw) {
	        HtmlTd td = tr.addTd();
	        td.addChild(((SelfDraw)action).drawHtml(execContext));
	    }
    }

    private Html drawActionForPresentationForm(Map<String, String>map, BaseAction action, XMLObject row, int rowIndex, Theme theme) throws DBConfigException {
    	Html html = null;
        if (action instanceof Field) {
        	Field field = (Field) action;
        	if (field.getParent() == null) {
        		// dont draw as this field is not described in the storage definition and is probably the count(*) field.
        	} else {
	            String tableAndFieldName;
	            if (field.getName().indexOf('.') > 0) {
	                tableAndFieldName = field.getName();
	            } else {
	                tableAndFieldName = Table.buildTableAndFieldName(getTable_name(), ((Field) action).getName());
	            }
	
	            IDrawField drawField = (IDrawField) database.getStorageField(tableAndFieldName);
	            Table table = (Table) drawField.getParent();
	            tableAndFieldName = table.buildTableAndFieldName(field.getName());
	            Object value = row.getAttributeValue(tableAndFieldName);
	            html = drawField.displayForList(execContext, field, (String) value, theme);
	            if (html != null && field.getTooltip() != null) {
	                html.setTitle(execContext.replace(field.getTooltip()));
	            }
	            CommonStorageField storageField;
	            if (Table.isTableAndFieldName(field.getName())) {
	                storageField = database.getStorageField(field.getName());
	            } else {
	                storageField = table.getFieldFromTableAndFieldName(Table.buildTableAndFieldName(getTable_name(),
	                        field.getName()));
	            }
	
	            Html [] resultHtml = displayForList(execContext, getRow_map_name(), (CommonFormFields)field, storageField, (String)value, theme);
        	}

        } else if (action instanceof AddRecordLink) {
            html = ((AddRecordLink) action).draw(execContext, theme);
        } else if (action instanceof DeleteRecordLink) {
            html = ((DeleteRecordLink) action).draw(execContext, theme);
        } else if (action instanceof UpdateRecordLink) {
            html = ((UpdateRecordLink) action).draw(execContext, theme);
        } else if (action instanceof Link) {
            Link link = (Link) action;
            html = link.draw(execContext, theme);
        } else if (action instanceof PopupAction) {
        	PopupAction popup = (PopupAction) action;
        	html = popup.drawHtml(execContext);
        }
        return html;
    }

    private HtmlTr drawRowPresentationAsBox(XMLObject row, int rowIndex, Theme theme)
	{

		HtmlTr tr = drawRowTheme(rowIndex, theme);
		HtmlTd td = tr.addTd();
        td.setClazz(theme.getValue(ThemeConst.LIST_TD.toString()));
		td.setAlign("left");
		td.setValign("top");
		td.setWidth(this.getWidth());
		HtmlDiv div = new HtmlDiv();
		td.addChild(div);
		div.setStyle("position: relative");
		for (BaseAction action : getField_list().getFields()) {
			if (action instanceof Field) {
				Field field = (Field) action;
				String tableAndFieldName;
				if (field.getName().indexOf('.') > 0) {
					tableAndFieldName = field.getName();
				} else {
					tableAndFieldName = Table.buildTableAndFieldName(getTable_name(), ((Field) action).getName());
				}

				IDrawField drawField = (IDrawField) database.getStorageField(tableAndFieldName);
				Object value = row.getAttributeValue(tableAndFieldName.replace(Table.TABLE_FIELD_SEPERATOR,
						Table.TABLE_FIELD_AS_SEPERATOR));
				// sb.append(drawField.displayForList(field, (String) value, theme));
				div.addChild(drawDivValue(field, (String) value, theme));

			} else if (action instanceof AddRecordLink) {
				Html htmlA = (((AddRecordLink) action).draw(execContext, theme));
				div.addChild(htmlA);
			} else if (action instanceof DeleteRecordLink) {
				Html htmlA = (((DeleteRecordLink) action).draw(execContext, theme));
				div.addChild(htmlA);
			} else if (action instanceof UpdateRecordLink) {
				Html htmlA = (((UpdateRecordLink) action).draw(execContext, theme));
				div.addChild(htmlA);
			}
		}
		return tr;
	}

	private HtmlTr drawRowTheme(int rowIndex, Theme theme)
	{

		HtmlTr tr = new HtmlTr();
		// Set theme for rows based on odd or even factor
		if ((rowIndex & 1) == 1) { // odd
            tr.setClazz(theme.getValue(ThemeConst.LIST_ROW_ODD.toString()));
		} else { // even
            tr.setClazz(theme.getValue(ThemeConst.LIST_ROW_EVEN.toString()));
		}
		if (getRow_height() != null) {
			tr.setHeight(getRow_height());
		}
		return tr;
	}

	public HtmlDiv drawDivValue(IDrawParams params, String fieldPresentationData, Theme theme)
	{

		HtmlDiv div = new HtmlDiv();

		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotEmpty(params.getX()) || StringUtils.isNotEmpty(params.getY())) {
			sb.append("position: absolute;");
			// sb.append(" style=\"position: absolute;");
			if (StringUtils.isNotEmpty(params.getX())) {
				sb.append(" left:" + params.getX() + ";");
			}
			if (StringUtils.isNotEmpty(params.getY())) {
				sb.append(" top:" + params.getY() + ";");
			}
			div.setStyle(sb.toString());

		}
		if (StringUtils.isNotEmpty(params.getAlign())) {
			div.setAlign(params.getAlign());
		}
		if (StringUtils.isNotEmpty(params.getValign())) {
			div.setValign(params.getValign());
		}
		if (StringUtils.isNotEmpty(params.getWidth())) {
			div.setWidth(params.getWidth());
		}
		if (StringUtils.isNotEmpty(params.getHeight())) {
			div.setHeight(params.getHeight());
		}
		div.setContent(fieldPresentationData);
		return div;
	}

    public HtmlTd drawRowValue(IDrawParams params, Html [] fieldPresentationData, Theme theme)
	{

        HtmlTd td = new HtmlTd(theme, ThemeConst.LIST_TD);

		if (StringUtils.isNotEmpty(params.getAlign())) {
			td.setAlign(params.getAlign());
		}
		if (StringUtils.isNotEmpty(params.getValign())) {
			td.setValign(params.getValign());
		}
		if (StringUtils.isNotEmpty(params.getWidth())) {
			td.setWidth(params.getWidth());
		}
		if (StringUtils.isNotEmpty(params.getHeight())) {
			td.setHeight(params.getHeight());
		}
		for (Html html : fieldPresentationData) {
			td.addChild(html);
		}
		return td;
	}

    private CommonStorageField buildRowIndexField() {
        CommonStorageField field = new TextHtml();
        field.setPresentation_name("#");
        field.setName(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_KEY_INDEX));

        return field;
    }
	private HtmlTr drawHeaders(Theme theme)
	{

		// ===
		// draw the table headers
		// ===
		HtmlTr tr = new HtmlTr();
        tr.setClazz(theme.getValue(ThemeConst.LIST_ROW.toString()));
		HtmlTh th = null;

        if (row_index) {
            CommonStorageField field = buildRowIndexField();
            th = buildHeader(field, theme, getHeader_align());
            tr.addChild(th);
        }
		for (BaseAction action : getField_list().getFields()) {
			if (action instanceof Field) {
				// skip TOTALRECORDSCOUNT as this was added by the framework to include the it in each row of the result set.
				if (StringUtils.isNotBlank(total_record_count_field) && ((Field) action).getName().equals(total_record_count_field) ) {
					continue;
				}
				CommonStorageField field;
                if (Table.isTableAndFieldName(((Field) action).getName())) {
                    field = database.getStorageField(((Field) action).getName());
                } else {
                    field = (CommonStorageField) FormUtils.findMatchingField(table, ((Field) action).getName());
                }
                /*
				if (((Field) action).getName().indexOf('.') > 0) {
					field = (CommonStorageField) FormUtils.findMatchingField(database, Table
							.getTableName(((Field) action).getName()), Table.getFieldName(((Field) action).getName()));
				} else {
					field = (CommonStorageField) FormUtils.findMatchingField(table, ((Field) action).getName());
				}
				*/
				th = buildHeader(field, theme, getHeader_align());
				// sb.append(HeaderUtils.buildHeader(field, theme, getHeader_align()));
			} else if (action instanceof AddRecordLink) {
				th = buildHeader(null, ((AddRecordLink) action).getHeader_name(), theme, getHeader_align());
			} else if (action instanceof DeleteRecordLink) {
				th = buildHeader(null, ((DeleteRecordLink) action).getHeader_name(), theme, getHeader_align());
            } else if (action instanceof UpdateRecordLink) {
                th = buildHeader(null, ((UpdateRecordLink) action).getHeader_name(), theme, getHeader_align());
            } else if (action instanceof PopupAction) {
                PopupAction popup = (PopupAction) action;
                if (popup.getHeader_name() != null) {
                    th = buildHeader(null, ((PopupAction) action).getHeader_name(), theme, getHeader_align());
                }
            } else if (action instanceof SelfDraw) {
                SelfDraw selfDraw = (SelfDraw) action;
                if (selfDraw.drawHeader(execContext) != null) {
                    th = buildHeader(null, selfDraw.drawHeader(execContext), theme, getHeader_align());
                }
            } else if (action instanceof FieldHide) {
                th = null;
			} else {
				th = buildHeader(null, getName() , theme, getHeader_align());
			}
            if (th != null) {
                tr.addChild(th);
            }
		}
		for ( Link link : getField_list().getLinks()) {
			th = buildHeader(null, link.getHeader() , theme, getHeader_align());
			tr.addChild(th);
		}
		return tr;

	}

	public HtmlTh buildHeader(CommonStorageField field, Theme theme, String align)
	{

		String tooltip = "";
		if (!StringUtils.isBlank(field.getTooltip())) {
            tooltip += execContext.replace(field.getTooltip());
		}
		
		if (field.isMandatory() || field.isUnique()) {
			tooltip += " - This field is ";
			if (field.isMandatory() && field.isUnique()) {
				tooltip += "mandatory and unique";
			} else if (field.isMandatory()) {
				tooltip += "mandatory";
			} else if (field.isUnique()) {
				tooltip += "unique";
			}
		}

        String presentationName;

        if (field.getRefFk() != null && field.getRefFk().getPresentation_name() != null) {
            presentationName = field.getRefFk().getPresentation_name();
        } else {
            presentationName = field.getPresentation_name();
        }

        //presentationName = presentationName
		//		+ (field.isMandatory() == true ? "<small>*</small>" : "")
		//		+ (field.isUnique() == true ? "<small>*</small>" : "");
		return buildHeader(tooltip, presentationName, theme, align);
	}

	public HtmlTh buildHeader(String tooltip, String presentationName, Theme theme, String align)
	{

		HtmlTh th = new HtmlTh();
        th.setClazz(theme.getValue(ThemeConst.LIST_HEADER.toString()));
		th.setTitle(tooltip);
		th.setAlign(align);
		th.setContent(presentationName);
		return th;
	}

	private String buildPresentationIntoForm(XMLObject xo, Theme theme, Database database, Table table,
			IExecContext execContext, String presentationForm) throws DBConfigException, NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException, IOException
	{

		int numRows = Integer.parseInt(xo.getAttributeValueAsString(DBSQL.NUM_ROWS));
		StringBuilder sb = new StringBuilder();

		if (numRows > 0) {
			for (int i = 0; i < numRows; i++) {
				XMLObject row = xo.getChild(i);
				sb.append(addRow(execContext, row, presentationForm, i, theme));
			}
		} else {
			//XMLObject row = new XMLObject(); 
			//sb.append(addRow(execContext, row, presentationForm, 1, theme));
			//sb.append(presentationForm);
		}
		return sb.toString();

	}
	
	private String buildPresentationIntoFormFromFile(XMLObject xo, Theme theme, Database database, Table table,
			IExecContext execContext, String presentationFormFile) throws DBConfigException, NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException, IOException
	{

		String presentationForm;
		try {
            String path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
            String pageName = execContext.replace(presentationFormFile);
            String page = ResourceCommon.buildFileName(path, pageName);
            presentationForm = ResourceUtils.loadFile(page);
		} catch (IOException ex) {
			throw new IllegalArgumentException("Unable to load presentation_form [" + presentationFormFile + "]", ex);
		}
		return buildPresentationIntoForm(xo,theme, database, table, execContext, presentationForm);
	}
	
	private String addRow(IExecContext execContext, XMLObject row, String presentationForm, int rowNum, Theme theme) throws DBConfigException, IOException, NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException {
		
		addRowToExecContext(execContext, row);
		String duplicateForm = copyForm(presentationForm);
		String populatedForm = drawRowPresentationAsForm(execContext, duplicateForm, row, rowNum, theme);
		populatedForm = new Action().processPage(execContext, populatedForm);
		
		//return execContext.replace(populatedForm);
		return new org.xmlactions.common.text.Html().removeOuterHtml(populatedForm);

	}

	private void addRowToExecContext(IExecContext execContext, XMLObject row)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		for (XMLAttribute att : row.getAttributes()) {
			map.put(att.getKey(), att.getValue());
		}
		execContext.addNamedMap(getRow_map_name(), map);
	}
	
	private String copyForm(String form) {
		StringBuilder sb = new StringBuilder(form);
		return sb.toString();
	}

	public String toString()
	{
		if (execContext != null) {
			return "search storage_config_ref [" + getStorage_config_ref(execContext) + "] table_name [" + getTable_name() + "]";
		} else {
			return "storage_config_ref [" + getStorage_config_ref() + "] table_name [" + getTable_name() + "]";
		}
			
	}

	public void setRows(String rows)
	{

		this.rows = rows;
	}

	public int getRowsAsInt(IExecContext execContext) {
		try {
			return Integer.parseInt(getRows(execContext));
		} catch (Exception ex) {
			return ROWS_DEFAULT_VALUE;
		}
	}
	/** Number of rows to return for a query */
	public String getRows()
	{
		int value;
		try {
			value = Integer.parseInt(this.rows);
		} catch (Exception ex) {
			value = ROWS_DEFAULT_VALUE;
		}
		return "" + rows;
	}

	/** Number of rows to return for a query */
	public String getRows(IExecContext execContext)
	{
		String value;
		try {
			value = execContext.replace(this.rows);
		} catch (Exception ex) {
			value = "" + ROWS_DEFAULT_VALUE;
		}
		return "" + value;
	}

	public void setPage(int page)
	{

		this.page = page;
	}

	public int getPage()
	{

		return page;
	}

	public void setHeader_align(String header_align)
	{

		this.header_align = header_align;
	}

	public String getHeader_align()
	{

		if (StringUtils.isEmpty(header_align))
			return ALIGN;
		return header_align;
	}

	public void setField_list(FieldList field_list)
	{

		this.field_list = field_list;
	}

	public FieldList getField_list()
	{

		if (field_list == null) {
			field_list = new FieldList();
		}
		return field_list;
	}

	public void setWhere(String where)
	{

		this.where = where;
	}

	public String getWhere()
	{

		return where;
	}

	public void setJoin(String join)
	{

		this.join = join;
	}

	public String getJoin()
	{

		return join;
	}

	public void setRow_height(String row_height)
	{

		this.row_height = row_height;
	}

	public String getRow_height()
	{

		return row_height;
	}

	public java.util.List<HtmlInput> getHiddenFields()
	{

		java.util.List<HtmlInput> inputs = new ArrayList<HtmlInput>();

        inputs.add(buildHiddenInput(ClientParamNames.STORAGE_CONFIG_REF, getStorage_config_ref(execContext)));
		if (!StringUtils.isEmpty(getTable_name())) {
			inputs.add(buildHiddenInput(ClientParamNames.TABLE_NAME_MAP_ENTRY, table.getName()));
		}
		return inputs;
	}

	public IExecContext getExecContext()
	{

		return this.execContext;
	}

	/**
	 * Validates that the data_source_ref, storage_ref and table_name are set.
	 * 
	 * @param errMsg
	 */
	public void validateStorage(String errMsg)
	{
		validateStorage(this.getExecContext(), errMsg);
	}
	/**
	 * Validates that the data_source_ref, storage_ref and table_name are set.
	 * 
	 * @param errMsg
	 */
	public void validateStorage(IExecContext execContext, String errMsg)
	{

        Validate.notEmpty(getStorage_config_ref(execContext), "storage_config_ref has not been set - " + errMsg);
		if (StringUtils.isEmpty(getTable_name()) && StringUtils.isEmpty(getSql())) {
			throw new InvalidParameterException("table_name or sql has not been set - " + errMsg);
			//Validate.notEmpty(getTable_name(), "table_name has not been set - " + errMsg);
		}
		if (this.page <= 0) {
			this.page = 1;
		}
		if (getRowsAsInt(execContext) <= 0) {
			setRows("" + this.ROWS_DEFAULT_VALUE);
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
//        if (isVisible()) {
//            table.setClazz(theme.getValue(ThemeConst.LIST_BORDER.toString()));
//        } else {
//            table.setClazz(theme.getValue(ThemeConst.LIST_BORDER.toString(), "hide"));
//        }
        if (! isVisible()) {
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
            HtmlTh th = tr.addTh();
            // tr.setClazz(theme.getValue(ThemeConst.LIST_BORDER.toString()));
            th.setClazz(theme.getValue(ThemeConst.LIST_TITLE.toString()));
            th.setContent(getTitle());
            // FIXME - figure out what the colspan should be
            th.setColspan("100");
        }
        return table;

    }
    
    private void getRequestParameters(IExecContext execContext) {
    	String where  = execContext.getString("request:" + request_parameters.where.toString()); 
    	String orderby  = execContext.getString("request:" + request_parameters.order_by.toString()); 
    	String groupby  = execContext.getString("request:" + request_parameters.group_by.toString()); 
    	String page  = execContext.getString("request:" + request_parameters.page.toString()); 
    	String rows  = execContext.getString("request:" + request_parameters.rows.toString());
    	
    	if (StringUtils.isNotBlank(where)) {
    		setWhere(where);
    	}
    	if (StringUtils.isNotBlank(orderby)) {
    		setOrder_by(orderby);
    	}
    	if (StringUtils.isNotBlank(groupby)) {
    		setGroup_by(groupby);
    	}
    	if (StringUtils.isNotBlank(page)) {
    		if (IntegerUtils.isInteger(page)) {
    			setPage(Integer.parseInt(page));
    		}
    	}
    	if (StringUtils.isNotBlank(rows)) {
   			setRows(rows);
    	}
    }


    public boolean isRow_index() {
        return row_index;
    }

    public void setRow_index(boolean rowIndex) {
        row_index = rowIndex;
    }

    public void setOrder_by(String order_by) {
        this.order_by = order_by;
    }

    public String getOrder_by() {
        return order_by;
    }

    public void setGroup_by(String group_by) {
        this.group_by = group_by;
    }

    public String getGroup_by() {
        return group_by;
    }

	public void setPresentation_form(String presentation_form) {
		this.presentation_form = presentation_form;
	}

	public String getPresentation_form() {
		return presentation_form;
	}

	/**
	 * @return the output_format
	 */
	public String getOutput_format() {
		return output_format;
	}

	/**
	 * @param output_format the output_format to set
	 */
	public void setOutput_format(String output_format) {
		this.output_format = output_format;
	}

	public boolean isRemove_html() {
		return remove_html;
	}

	public void setRemove_html(boolean remove_html) {
		this.remove_html = remove_html;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public PresentationFormAction getForm() {
		return form;
	}

	public void setForm(PresentationFormAction form) {
		this.form = form;
	}

	/**
	 * @return the row_map_name
	 */
	public String getRow_map_name() {
		return row_map_name;
	}

	/**
	 * @param row_map_name the row_map_name to set
	 */
	public void setRow_map_name(String row_map_name) {
		this.row_map_name = row_map_name;
	}

}