package org.xmlactions.pager.actions.mapping;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.web.RequestExecContext;

public class TestJSONGetAction {

	private static Logger logger = LoggerFactory.getLogger(TestJSONGetAction.class);

	private static IExecContext execContext;

	private static final String[] configFiles = { ActionConst.SPRING_STARTUP_CONFIG,
			"/config/spring/test-spring-pager-web-startup.xml" };

	@Before
	public void setUp() {

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);

			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			RequestExecContext.set(execContext);
		}
	}
	@Test
	public void testJSONFormat() {
		String jsonString = "{\"name\":\"Fred\"}";
		JSONObject jsonObject = new JSONObject(jsonString);
		
		jsonString = "{ \"data\": [{\"name\":\"Fred\"}]}";
		jsonObject = new JSONObject(jsonString);
		
	}
	@Test
	public void testFromFile() throws Exception {
		String jsonString = ResourceUtils.loadFile("/org/xmlactions/pager/actions/mapping/data.json");
		JSONGetAction jsonGetAction = new JSONGetAction();
		jsonGetAction.setJson_data(jsonString);
		jsonGetAction.setJson_path("root_att");
		jsonGetAction.setIndex(0);
		jsonGetAction.execute(execContext);
		assertEquals("The Root Attribute", execContext.get("row:root_att"));
		assertEquals("The Root Attribute", execContext.get("root_att"));

		jsonGetAction.setJson_path("vessel_gear_types_category/image_url");
		jsonGetAction.execute(execContext);
		assertEquals("images/gears/surrounding_nets.png", execContext.get("row:image_url"));
		assertEquals("images/gears/surrounding_nets.png", execContext.get("image_url"));

		jsonGetAction.setIndex(1);
		jsonGetAction.execute(execContext);
		assertEquals("images/gears/surrounding_nets.png", execContext.get("row:image_url"));
		assertEquals("images/gears/surrounding_nets.png", execContext.get("image_url"));
		
	}
	@Test
	public void testFromHotelFile() throws Exception {
		String jsonString = ResourceUtils.loadFile("/org/xmlactions/pager/actions/mapping/hotel.json");
		JSONGetAction jsonGetAction = new JSONGetAction();
		jsonGetAction.setJson_data(jsonString);
		jsonGetAction.setJson_path("location/coordinates");
		jsonGetAction.setIndex(0);
		jsonGetAction.execute(execContext);
		assertEquals("13.894", execContext.get("coordinates"));
		assertEquals("13.894", execContext.get("row:coordinates"));
		
		jsonGetAction.setIndex(1);
		jsonGetAction.setRow_map_name("fred");
		jsonGetAction.execute(execContext);
		assertEquals("40.6972", execContext.get("coordinates"));
		assertEquals("40.6972", execContext.get("fred:coordinates"));

		jsonGetAction.setIndex(-1);
		jsonGetAction.setRow_map_name("fred");
		jsonGetAction.execute(execContext);
		assertEquals("[13.894,40.6972]", execContext.get("coordinates"));
		assertEquals("[13.894,40.6972]", execContext.get("fred:coordinates"));
		
	}
}
