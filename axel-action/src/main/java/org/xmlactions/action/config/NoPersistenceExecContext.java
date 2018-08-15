
package org.xmlactions.action.config;

import java.util.List;


/**
 * Mostly used for unit tests.  Allows for creating of an IExecContext without the need for a persistence layer.
 * @author mike.murphy
 *
 */
@SuppressWarnings("serial")
public class NoPersistenceExecContext extends ExecContext
{
	public NoPersistenceExecContext(List<Object> actionMaps, List<Object> localMaps, List<Object> themes) {
		super(actionMaps, localMaps, themes);
	}

	public NoPersistenceExecContext(List<Object> actionMaps, List<Object> localMaps) {
		super(actionMaps, localMaps);
	}

	public void loadFromPersistence()
	{

		// TODO Auto-generated method stub

	}

	public void reset()
	{

		// TODO Auto-generated method stub

	}

	public void saveToPersistence()
	{

		// TODO Auto-generated method stub

	}

}
