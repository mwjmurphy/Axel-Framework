package org.xmlactions.db.storedproc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

public class JdbcParamString implements JdbcParam {
    
    private String name;
    private String value;
    JdbcParamString(String name, String value) {
    }


    public void addToCallableStatement(CallableStatement cs) throws SQLException {
        cs.setString(getName(), getValue());
    }

    public void registerOutParameter(CallableStatement cs) throws SQLException {
        cs.registerOutParameter(getName(), Types.VARCHAR);
    }

    public void addToMap(CallableStatement cs, Map<String, Object> map) throws SQLException {
        map.put(getName(), cs.getString(getName()));
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
