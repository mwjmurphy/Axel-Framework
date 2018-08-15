package org.xmlactions.db.exception;

@SuppressWarnings("serial")
public class DBSQLException extends Exception {

    public DBSQLException() {
        super();
    }

    public DBSQLException(String msg) {
        super(msg);
    }

    public DBSQLException(String msg, Throwable t) {
        super(msg, t);
    }
}
