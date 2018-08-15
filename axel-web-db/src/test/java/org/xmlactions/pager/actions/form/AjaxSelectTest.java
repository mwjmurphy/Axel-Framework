package org.xmlactions.pager.actions.form;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.ExecContext;

public class AjaxSelectTest extends SpringTestCaseSetup {
	
	private static final Logger logger = LoggerFactory.getLogger(AjaxSelectTest.class);
	
	private AjaxSelect ajaxSelect;
	
	protected void localSetup() {
		ajaxSelect = new AjaxSelect();
		ExecContext ec = (ExecContext)getExecContext();
		ec.put("request:" + ClientParamNames.STORAGE_CONFIG_REF, "storageConfig");
		ec.put("request:" + ClientParamNames.TABLE_NAME_MAP_ENTRY, "tb_linked_child");
		ec.put("request:" + ClientParamNames.FIELD_NAME_MAP_ENTRY, "description");
		ec.put("request:" + ClientParamNames.THEME_NAME_MAP_ENTRY, "riostl");
		ec.put("request:" + ClientParamNames.LABEL_POSITION_MAP_ENTRY, "top");
	}
	
	public void testSetup() {
		assertNotNull(getExecContext());
	}

	public void testValidation() throws Exception {
		ajaxSelect.getHttpRequestParams(getExecContext());
        assertNotNull(ajaxSelect.getStorage_config_ref(getExecContext()));
		assertNotNull(ajaxSelect.getTable_name());
		assertNotNull(ajaxSelect.getField_name());
		assertNotNull(ajaxSelect.getLabel_position());
	}
	
	public void testExecute() throws Exception {
		String response = ajaxSelect.execute(getExecContext());
		assertNotNull(response);
		logger.debug("response:" + response);
	}
	
}
