package org.xmlactions.db.storedproc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Map;

public interface JdbcParam {

    public void addToCallableStatement(CallableStatement cs) throws SQLException;

    public void registerOutParameter(CallableStatement cs) throws SQLException;

    public void addToMap(CallableStatement cs, Map<String, Object> map) throws SQLException;

}
