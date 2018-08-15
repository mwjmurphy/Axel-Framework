package org.xmlactions.db.storedproc;

import java.io.IOException;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.SQLException;

import org.springframework.jdbc.core.SqlReturnType;

/**
 * Reads a Clob when it's a out parameter for a Stored Procedure call.
 * The content of the Clob id converted to a String.
 *
 * @author mike.murphy
 *
 */
public class ClobReader implements SqlReturnType {

	public Object getTypeValue(CallableStatement cs, int paramIndex, int sqlType, String typeName) throws SQLException {
		try {
			final StringBuilder outputBuilder = new StringBuilder();
			final Clob aClob = cs.getClob(paramIndex);
			if (aClob != null) {
				final Reader clobReader = aClob.getCharacterStream();
				int length = (int) aClob.length();
				char[] inputBuffer = new char[1024];
				while ((length = clobReader.read(inputBuffer)) != -1) {
					outputBuilder.append(inputBuffer, 0, length);
				}
				return outputBuilder.toString();
			} else {
				return null;
			}
	    } catch (IOException e) {
	        throw new SQLException(e.toString());
	    }
	}
}
