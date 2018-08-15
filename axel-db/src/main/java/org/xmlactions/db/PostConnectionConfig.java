package org.xmlactions.db;

import java.sql.Connection;

import org.xmlactions.action.config.IExecContext;

public interface PostConnectionConfig {
	  public void configConnection(IExecContext execContext, Connection connection);
}
