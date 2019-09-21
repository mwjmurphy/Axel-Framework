package org.xmlactions.db.query;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.ActionWebConst;
import org.xmlactions.action.actions.ActionUtils;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.xml.XMLAttribute;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.DBUtils;
import org.xmlactions.db.actions.DbSpecific;
import org.xmlactions.db.actions.Sql;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.sql.BuildSelect;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;
import org.xmlactions.mapping.xml_to_bean.PopulateClassFromXml;

public class QueryBuilder {

    private static final Logger log = LoggerFactory.getLogger(QueryBuilder.class);

    private String mapperFileName = "/config/mapping/query_to_bean.xml";

    private Connection connection = null;

	/**
	 * Creates an IExecContext thats populated from
	 * /config/xml_to_bean.properties
	 * 
	 * @return
	 * @throws IOException
	 */
	private IExecContext getExecContext()
	{
		try {
			Properties props = new Properties();
			props.load(ResourceUtils.getResourceURL("/config/xml_to_bean.properties").openStream());
			List<Object> list = new ArrayList<Object>();
			list.add(props);
			IExecContext execContext = new NoPersistenceExecContext(list,null);
			return execContext;
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}

    /**
     * 
     * @param resourceName
     *            - can be a URL or a filename.
     * 
     * @return the populated query bean
     */
    public Query loadQuery(String resourceName) {
        String queryXml;
        try {
            PopulateClassFromXml pop = new PopulateClassFromXml();
            queryXml = ResourceUtils.loadFile(resourceName);
            Query query = (Query)pop.mapXmlToBean(mapperFileName, queryXml);
            return query;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    public XMLObject buildQuery(IExecContext execContext, StorageConfig storageConfig, Query query) {
    	
    	try {
	        if (connection == null) {
	            DBConnector dbConnector = storageConfig.getDbConnector();
				connection = dbConnector.getConnection(execContext);
	        }
        	XMLObject xo = loadFromDB(null, execContext, storageConfig, query);
        	return xo;
    	} catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        } finally {
        	DBUtils.closeQuietly(connection);
        }
        
    }

    private XMLObject loadFromDB(XMLObject xo, IExecContext execContext, StorageConfig storageConfig, Query query) {

        if (xo == null) {
            List<XMLObject> xos = loadFromDB(execContext, storageConfig, query, false);
            if (xos != null && xos.size() == 1) {
            	XMLObject root = xos.get(0);
            	if (root.getChildCount() > 1) {
            		for (XMLObject child : root.getChildren()) {
            			child.setElementName(root.getElementName());
            			child.setNameSpace(root.getNameSpace());
            		}
            		root.setElementName("root");
                    xo = root;
                    for (XMLObject child : xo.getChildren()) {
    	                if (query.getSubQueries() != null) {
    	                    for (Query subQuery : query.getSubQueries()) {
    	                        loadFromDB(child, execContext, storageConfig, subQuery);
    	                    }
    	                }
                    }
            	} else {
                    xo = root;
   	                if (query.getSubQueries() != null) {
   	                    for (Query subQuery : query.getSubQueries()) {
   	                        loadFromDB(xo, execContext, storageConfig, subQuery);
    	                }
                    }
            	}
            }
        } else {
            addRowToExecContext(execContext, xo);
            List<XMLObject> children = loadFromDB(execContext, storageConfig, query, true);
            if (children != null) {
            	for (XMLObject child : children) {
	                xo.addChild(child);
	                if (query.getSubQueries() != null) {
	                    for (Query subQuery : query.getSubQueries()) {
	                    	loadFromDB(child, execContext, storageConfig, subQuery);
	                    }
	                }
	            }
            }
        }
        if (xo != null) {
            if (log.isDebugEnabled()) {
                log.debug("\n" + xo.mapXMLObject2XML(xo));
            }
        }

        return xo;
    }
    
    private List<XMLObject> loadFromDB(IExecContext execContext, StorageConfig storageConfig, Query query, boolean removeRow) {
    	List<SqlField>params = new ArrayList<SqlField>();

        try {


            Validate.notEmpty(query.getTable_name(), "query does not have its table name set.");
            
            if (query.getRequires() != null) {
            	for (Requires requires : query.getRequires()) {
                    if (log.isDebugEnabled()) {
                        log.debug("requires value:" + requires.getValue() + " expression:" + requires.getExpression());
                    }
            		if (requires.getValue() != null) {
	            		Object output = execContext.replace(requires.getValue());
	            		if (output == null || StringUtils.isEmpty(output.toString())) {
	                        if (log.isDebugEnabled()) {
	                            log.debug("ignore requires value:" + requires.getValue());
	                        }
	            			return null;
	            		}
            		}
            		if (requires.getExpression() != null) {
            			if (! ActionUtils.evaluateExpression(execContext, requires.getExpression())) {
                            if (log.isDebugEnabled()) {
                                log.debug("ignore requires expression:" + requires.getExpression());
                            }
	            			return null;
	            		}
            		}
            	}
            }

            String sql = null;
            if (query.getFieldList() != null) {
                FieldList fieldList = query.getFieldList();
                Validate.notNull(fieldList, "query " + query.getTable_name() + " does not have a fieldList.");

                List<Field> fields = fieldList.getFields();
                Validate.notEmpty(fields, "query " + query.getTable_name() + " has no fields set in the fieldList.");

                List<SqlField> sqlFields = new ArrayList<SqlField>();
                for (Field field : fields) {
                    Validate.notEmpty(field.getName(), "query " + query.getTable_name()
                            + " has a field with an empty name.");
                    sqlFields.add(new SqlField(field.getName()));
                }
            
                SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storageConfig.getStorageContainer()
                        .getStorage(),
                        storageConfig.getDatabaseName(),
                        query.getTable_name(),
                        sqlFields,
                        storageConfig.getDbSpecificName());

                if (fieldList.getWhereClauses() != null) {
                    for (Where where : fieldList.getWhereClauses()) {
                        String whereClause = where.getClause();
                        if (StringUtils.isNotEmpty(whereClause)) {
                            whereClause = execContext.replace(whereClause);
                            sqlSelectInputs.addWhereClause(whereClause);
                        }
                    }
                }
                if (fieldList.getOrderBy() != null) {
                    for (Order order : fieldList.getOrderBy()) {
                        String orderBy = order.getBy();
                        if (StringUtils.isNotEmpty(orderBy)) {
                            orderBy = execContext.replace(orderBy);
                            sqlSelectInputs.addOrderByClause(orderBy);
                        }
                    }
                }
                if (fieldList.getLimit_from() != null) {
                	if (fieldList.getLimit_rows() != null) {
                		int from = Integer.parseInt(execContext.replace(fieldList.getLimit_from()));
                		int rows = Integer.parseInt(execContext.replace(fieldList.getLimit_rows()));
        				from = (from - 1) * rows;
        				int to = rows + from;
        				from++;
                    	sqlSelectInputs.setLimitFrom("" + from);
                    	sqlSelectInputs.setLimitTo("" + to);
                	} else {
                		sqlSelectInputs.setLimitFrom(execContext.replace(fieldList.getLimit_from()));
                	}
                }
                else if (fieldList.getLimit_rows() != null) {
                	sqlSelectInputs.setLimitTo(execContext.replace(fieldList.getLimit_rows()));
                }
                
				QueryBuilder.buildWhereClause(execContext, sqlFields, sqlSelectInputs);

                ISqlSelectBuildQuery builder = storageConfig.getSqlBuilder();
                sql = builder.buildSelectQuery(execContext, sqlSelectInputs, null);
            } else if (query.getSql() != null) {
            	SqlQuery sqlQuery = query.getSql();
            	if (sqlQuery.getSql_ref() != null) {
            		String sql_ref = execContext.replace(sqlQuery.getSql_ref());
            		DbSpecific dbSpecific = storageConfig.getStorageContainer().getStorage().getDatabase().getDbSpecific(storageConfig.getDbSpecificName());
            		Sql dbSql = dbSpecific.getSql(sql_ref);
            		params = dbSql.getListOfParams(execContext);
            		sql = execContext.replace(dbSql.getSql());
            	} else if (sqlQuery.getSql_query() != null) {
            		sql = execContext.replace(query.getSql().getSql_query());
            	}
                if (sqlQuery.getWhereClauses() != null) {
                	StringBuilder sb = new StringBuilder();
                    for (Where where : sqlQuery.getWhereClauses()) {
                        String whereClause = where.getClause();
                        if (StringUtils.isNotEmpty(whereClause)) {
                            whereClause = execContext.replace(whereClause);
                            if (sb.length() == 0) {
                            	sb.append("\n where ");
                            } else {
                            	sb.append("  and ");
                            }
                            sb.append(" " + whereClause + "\n");
                        }
                    }
                    if (sb.length() > 0) {
                    	sql += sb;
                    }
                }
                if (sqlQuery.getOrderBy() != null) {
                	StringBuilder sb = new StringBuilder();
                    for (Order order : sqlQuery.getOrderBy()) {
                        String orderBy = order.getBy();
                        if (StringUtils.isNotEmpty(orderBy)) {
                            orderBy = execContext.replace(orderBy);
                            if (sb.length() == 0) {
                            	sb.append("\n order by ");
                            } else {
                            	sb.append(", ");
                            }
                            sb.append(" " + orderBy + " ");
                        }
                    }
                    if (sb.length() > 0) {
                    	sql += sb;
                    }
                }
                if (sqlQuery.getGroupBy() != null) {
                	StringBuilder sb = new StringBuilder();
                    for (Group group : sqlQuery.getGroupBy()) {
                        String groupBy = group.getBy();
                        if (StringUtils.isNotEmpty(groupBy)) {
                            groupBy = execContext.replace(groupBy);
                            if (sb.length() == 0) {
                            	sb.append("\n group by ");
                            } else {
                            	sb.append(", ");
                            }
                            sb.append(" " + groupBy + " ");
                        }
                    }
                    if (sb.length() > 0) {
                    	sql += sb;
                    }
                }
            }

            if (log.isDebugEnabled()) {
            	log.debug("QueryBuilder.sql:" + sql);
            }
            String xml = new DBSQL().queryXMLShort(connection, sql, query.getTable_name(), params);
        
            XMLObject xmlObject = new XMLObject().mapXMLCharToXMLObject(xml);
            List<XMLObject> xos;
            if (removeRow == true) {
                xos = removeRow(xmlObject);
            } else {
                if (xmlObject.getChildCount() == 1) {
                    xos = removeRow(xmlObject);
                } else {
                    xos = new ArrayList<XMLObject>();
                    xos.add(xmlObject);
                }
            }
            return xos;

        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * 
     * @param execContext
     * @param storageConfig
     * @param sqlRef - a reference to an sql defined in the db_specific storage
     * @param removeRow - used to remove the row/s elements if needed.
     * @return the resultant xml object
     */
    public XMLObject loadFromDB(IExecContext execContext, StorageConfig storageConfig, String sqlRef, boolean removeRow) {
    	List<SqlField>params = new ArrayList<SqlField>();

        try {

            if (log.isDebugEnabled()) {
            	log.debug("QueryBuilder.sql:" + sqlRef);
            }
            
    		String sql_ref = execContext.replace(sqlRef);
    		DbSpecific dbSpecific = storageConfig.getStorageContainer().getStorage().getDatabase().getDbSpecific(storageConfig.getDbSpecificName());
    		Sql dbSql = dbSpecific.getSql(sql_ref);
    		params = dbSql.getListOfParams(execContext);
    		String rawSql = execContext.replace(dbSql.getSql());
    		return loadFromDBWithSql(execContext, storageConfig, rawSql, removeRow);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * 
     * @param execContext
     * @param storageConfig
     * @param sql - the query sql 
     * @param removeRow - used to remove the row/s elements if needed.
     * @return the resultant xml object
     */
    public XMLObject loadFromDBWithSql(IExecContext execContext, StorageConfig storageConfig, String sql, boolean removeRow) {
    	List<SqlField>params = new ArrayList<SqlField>();

        try {

            if (connection == null) {
	            DBConnector dbConnector = storageConfig.getDbConnector();
	            connection = dbConnector.getConnection(execContext);
            }

            String xml = new DBSQL().queryXMLShort(connection, sql, DBSQL.ROOT, params);
        
            XMLObject xmlObject = new XMLObject().mapXMLCharToXMLObject(xml);
            List<XMLObject> xos;
            if (removeRow == true) {
                xos = removeRow(xmlObject);
            } else {
                if (xmlObject.getChildCount() == 1) {
                    xos = removeRow(xmlObject);
                } else {
                    xos = new ArrayList<XMLObject>();
                    xos.add(xmlObject);
                }
            }

            XMLObject root = null;
            if (xos != null) {
            	if (xos.size() == 1) {
            		root = xos.get(0);
            	} else {
                	root = new XMLObject(DBSQL.ROOT);
                	root.addAttribute(DBSQL.NUM_ROWS, xos.size());
                	for (XMLObject child : xos) {
                		root.addChild(child);
                	}
            	}
            }
            return root;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        } finally {
       		DBConnector.closeQuietly(connection);
        }
    }


    private List<XMLObject> removeRow(XMLObject xo) {
    	String elementName = xo.getElementName();
    	for (XMLObject child : xo.getChildren()) {
    		child.setElementName(elementName);
    	}
    	// may need to figure out parent, for completeness
    	return xo.getChildren();
    }


    private void addRowToExecContext(IExecContext execContext, XMLObject row) {

        Map<String, Object> map = new HashMap<String, Object>();
        for (XMLAttribute att : row.getAttributes()) {
            map.put(att.getKey(), att.getValue());
        }
        execContext.addNamedMap(ActionConst.ROW_MAP_NAME, map);
    }

    public static void buildWhereClause(IExecContext execContext, java.util.List<SqlField> sqlFields,
			SqlSelectInputs sqlSelectInputs)
	{
    	
        for (SqlField sqlField : sqlFields) {
            String key = ActionWebConst.buildRequestKey(sqlField.getFieldName());
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
	}

}
