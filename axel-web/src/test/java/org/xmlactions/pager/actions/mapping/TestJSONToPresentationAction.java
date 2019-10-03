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
}
