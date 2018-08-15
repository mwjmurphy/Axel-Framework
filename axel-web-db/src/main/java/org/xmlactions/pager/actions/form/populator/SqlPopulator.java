package org.xmlactions.pager.actions.form.populator;


import java.sql.Connection;


import java.util.List;

import org.apache.commons.lang.Validate;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Sql;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.db.sql.select.SqlField;

public class SqlPopulator extends BaseAction implements Populator {

    /** The population source ref such as an sql or a static data ref */
    private String ref;
    
    
    private String default_value;

    public String execute(IExecContext execContext) throws Exception {
        return null;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public String executePopulator(IExecContext execContext, StorageConfig storageConfig, Database database, Connection connection) throws DBSQLException {
    	List<SqlField>params = null;
        String sqlQuery = null;

        String dbSpecificName = storageConfig.getDbSpecificName();
        Sql sql = database.getSql(dbSpecificName, getRef());
        if (sql != null) {
            sqlQuery = sql.getSql();
            params = sql.getListOfParams(execContext);
            Validate.notEmpty(sqlQuery, "No SQL found for [" + getRef() + "]");
            sqlQuery = execContext.replace(sqlQuery);
        }
        DBSQL dbSQL = new DBSQL();
        dbSQL.setIncludeIndex(false);
        XMLObject xo = dbSQL.query2XMLObject(connection, sqlQuery, "root", null, null, params);
        return xo.mapXMLObject2XML(xo);
    }

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}
}