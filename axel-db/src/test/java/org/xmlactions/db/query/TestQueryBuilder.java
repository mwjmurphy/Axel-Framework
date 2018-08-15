package org.xmlactions.db.query;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.xmlactions.action.Action;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.UtilsTestHelper;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.env.EnvironmentAccess;
import org.xmlactions.db.query.Query;
import org.xmlactions.db.query.QueryBuilder;
import org.xmlactions.db.sql.mysql.MySqlSelectQuery;

import junit.framework.TestCase;

import org.xmlactions.mapping.xml_to_bean.XmlToBean;


public class TestQueryBuilder {

	private XmlToBean xmlToBean;
	
	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
	}
	

	@Test
    public void testLoadAndPopulate() throws Exception {
        Action action = new Action();
        String page = Action.loadPage("src/test/resources", "db/storage.xml");
        BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
        assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

        assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
                actions[0] instanceof Storage);

        IExecContext execContext = UtilsTestHelper.getExecContext();
        Storage storage = (Storage) actions[0];
        StorageConfig storageConfig = new StorageConfig();
        StorageContainer storageContainer = new StorageContainer("/db/storage.xml", execContext);
        storageConfig.setDatabaseName("test");
        storageConfig.setSqlBuilder(new MySqlSelectQuery());
        
        storageConfig.setStorageContainer(storageContainer);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("address.id", "16");
        execContext.addNamedMap("row", map);
        //StorageConfig storageConfig = (StorageConfig) execContext.get("storageConfig");

        QueryBuilder qb = new QueryBuilder();
        Query query = qb.loadQuery("/config/query/query.xml");
        XMLObject xo = qb.buildQuery(UtilsTestHelper.getExecContext(), storageConfig, query);
        String xml = xo.mapXMLObject2XML(xo);
        assertNotNull(query);
    }
}
