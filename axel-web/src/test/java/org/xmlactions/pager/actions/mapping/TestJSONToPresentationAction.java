package org.xmlactions.pager.actions.mapping;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Set;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.pager.actions.form.PresentationFormAction;
import org.xmlactions.pager.actions.mapping.BeanToXmlAction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TestJSONToPresentationAction extends TestCase {

    private static Logger logger = LoggerFactory.getLogger(TestJSONToPresentationAction.class);

    private static IExecContext execContext;

    private static final String[] configFiles = { ActionConst.SPRING_STARTUP_CONFIG,
            "/config/spring/test-spring-pager-web-startup.xml" };

    public void setUp() {

        if (execContext == null) {
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);

            execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
        }
    }

    public void testFromFile() throws Exception {
    	JSONToPresentationAction jsonToPresentationAction = new JSONToPresentationAction();
    	jsonToPresentationAction.setJson_path("vessel_gear_types_category");
    	jsonToPresentationAction.setJson_filename("/org/xmlactions/pager/actions/mapping/data.json");
    	jsonToPresentationAction.setPresentation_form("/org/xmlactions/pager/actions/mapping/json_presentation.html");
    	String output = jsonToPresentationAction.execute(execContext);
    	logger.debug(output);

    }

    public void testFromData() throws Exception {
    	JSONToPresentationAction jsonToPresentationAction = new JSONToPresentationAction();
    	String json = ResourceUtils.loadFile("/org/xmlactions/pager/actions/mapping/data.json");
    	jsonToPresentationAction.setJson_path("vessel_gear_types_category");
    	jsonToPresentationAction.setJson_data(json);
    	jsonToPresentationAction.setPresentation_form("/org/xmlactions/pager/actions/mapping/json_presentation.html");
    	String output = jsonToPresentationAction.execute(execContext);
    	logger.debug(output);
    }
    public void testFromDataToForm() throws Exception {
    	PresentationFormAction form = new PresentationFormAction();
    	form.setContent("This is the test json presentation form\n${row_index}	image_url=${image_url}\ntitle_text=${title_text}\ngear_name=${gear_name}\ntooltipid=${tooltipid}\ndata_filter=${data_filter}");
    	JSONToPresentationAction jsonToPresentationAction = new JSONToPresentationAction();
    	String json = ResourceUtils.loadFile("/org/xmlactions/pager/actions/mapping/data.json");
    	jsonToPresentationAction.setJson_path("vessel_gear_types_category");
    	jsonToPresentationAction.setJson_data(json);
    	jsonToPresentationAction.setForm(form);
    	String output = jsonToPresentationAction.execute(execContext);
    	logger.debug(output);
    }

    public void testFromDataFromHotelToForm() throws Exception {
    	PresentationFormAction form = new PresentationFormAction();
    	form.setContent("x:${row:coordinates}");
    	JSONToPresentationAction jsonToPresentationAction = new JSONToPresentationAction();
    	String json = ResourceUtils.loadFile("/org/xmlactions/pager/actions/mapping/hotel.json");
    	jsonToPresentationAction.setJson_path("location/coordinates");
    	jsonToPresentationAction.setJson_data(json);
    	jsonToPresentationAction.setForm(form);
    	String output = jsonToPresentationAction.execute(execContext);
    	assertEquals("x:13.894x:40.6972", output);
    	
    	jsonToPresentationAction.setJson_path("facilities/mostPopular/facilities");
    	form.setContent("icon:${icon} row:icon:${row:icon}\n");
    	output = jsonToPresentationAction.execute(execContext);
    	assertTrue(output.contains("e5e741d44369e3d606ca747f325cd309"));
    	assertTrue(output.contains("032da6956d5d6779d37b76b9b9e9b153"));
    	assertTrue(output.contains("e5e741d44369e3d606ca747f325cd309"));
    	String icon = "" + execContext.get("row:icon");
    	assertTrue(icon.contains("e5e741d44369e3d606ca747f325cd309"));
    	icon = "" + execContext.get("icon");
    	assertTrue(icon.contains("null"));
    			
		JSONGetAction jsonGetAction = new JSONGetAction();
		jsonGetAction.setJson_data("{\"url\":\"e5e741d44369e3d606ca747f325cd309.png\"}");
		jsonGetAction.setJson_path("url");
		jsonGetAction.setIndex(0);
		jsonGetAction.execute(execContext);
		String url = execContext.getString("row:url");
    	assertEquals(url, "e5e741d44369e3d606ca747f325cd309.png");
		url = execContext.getString("url");
    	assertEquals(url, "e5e741d44369e3d606ca747f325cd309.png");

    	logger.debug(output);
    }
  
    public void testArray() {
    	String json = "[{\"icon\":{\"url\":\"032da6956d5d6779d37b76b9b9e9b153.png\"},\"description\":\"Wi-Fi\",\"_id\":\"58e664a8241771003714ca71\",\"label\":\"Internet\"}]";
    	Gson gson = new Gson();
    	JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
    	boolean isArray = jsonElement.isJsonArray();
    	JsonArray jsonArray = jsonElement.getAsJsonArray();
    	for (JsonElement je : jsonArray) {
    		if (je.isJsonArray()) {
    			
            	// logger.debug("je:{}",je);
    			
    		} else if (je.isJsonObject()) {
    			JsonObject jo = je.getAsJsonObject();
    			Set<Entry<String, JsonElement>> set = jo.entrySet();
    			set.forEach(e -> logger.debug("e:{}", e));
    			Object o = jo.get("icon");
            	// logger.debug("o:{}",o);
    			
    		} else {
    			String s = je.getAsString();
            	// logger.debug("S:{}",s);
    		}
        	// logger.debug("o:{}", je);
    	}
    	String s = jsonElement.toString();
    	logger.debug("s:{}", s);
    	// JSONObject jsonObject = new JSONObject(json);
    }
    
    public void testArrayIndex() throws Exception {
    	String json = "{ \"hires_images\" : [{ \"url\" : \"27d1397488026ff5a35c624f14a22bd2_highRes_x1.jpg\" }, { \"url\" : \"2bcb11201d285c47e1a8a86d87c60b5d_highRes_x1.jpg\" }] }";
    	PresentationFormAction form = new PresentationFormAction();
    	form.setContent("img:url:${img:url}");
    	JSONToPresentationAction jsonToPresentationAction = new JSONToPresentationAction();
    	jsonToPresentationAction.setJson_path("hires_images");
    	jsonToPresentationAction.setJson_data(json);
    	jsonToPresentationAction.setRow_map_name("img");
    	jsonToPresentationAction.setForm(form);
    	String output = jsonToPresentationAction.execute(execContext);
    	assertTrue(output.contains("27d1397488026ff5a35c624f14a22bd2_highRes_x1"));
    	assertEquals("2bcb11201d285c47e1a8a86d87c60b5d_highRes_x1.jpg", execContext.getString("img:url"));
    	
    }
}
