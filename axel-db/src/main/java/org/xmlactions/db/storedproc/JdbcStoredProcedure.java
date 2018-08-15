package org.xmlactions.db.storedproc;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.db.DBUtils;

public class JdbcStoredProcedure {
    
    private static final Logger LOG = LoggerFactory.getLogger(JdbcStoredProcedure.class);

    /**
     * 
     * @param ds
     *            - DataSource
     * @param sqlCall
     *            - the stored procedure to call including the ? placements
     * @param inParams
     *            - a list of input parameters
     * @param outParams
     *            - a list of output parameters
     * @return a map containing the results of the input parameters.
     * @throws SQLException
     */
    public Map<String, Object> call(DataSource ds, String sqlCall, List<JdbcParam> inParams, List<JdbcParam> outParams)
            throws SQLException {

        Connection conn = null;
        CallableStatement cstmt = null;
        try {
            conn = ds.getConnection();
            /*
             * DatabaseMetaData dbmd = conn.getMetaData(); if
             * (dbmd.supportsNamedParameters() == false) { throw new
             * IllegalArgumentException
             * ("NAMED PARAMETERS FOR CALLABLE STATEMENTS ARE NOT SUPPORTED"); }
             */

            cstmt = conn.prepareCall(sqlCall);
            for (JdbcParam param : inParams) {
                param.addToCallableStatement(cstmt);
            }

            for (JdbcParam param : outParams) {
                param.registerOutParameter(cstmt);
            }

            cstmt.execute();

            Map<String, Object> map = new HashMap<String, Object>();
            for (JdbcParam param : outParams) {
                param.addToMap(cstmt, map);
            }
            return map;
        } finally {
            DBUtils.closeQuietly(cstmt);
            DBUtils.closeQuietly(conn);
        }
        
    }
}
