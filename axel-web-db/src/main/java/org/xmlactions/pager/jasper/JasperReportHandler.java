package org.xmlactions.pager.jasper;


import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.db.DBUtils;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.web.HttpParam;
import org.xmlactions.web.PagerWebConst;

public class JasperReportHandler extends BaseAction {

	private static final Logger logger = LoggerFactory.getLogger(JasperReportHandler.class);

	@Override
	public String execute(IExecContext execContext) throws Exception {

		List<HttpParam> params = (List<HttpParam>)execContext.get(PagerWebConst.REQUEST_LIST);

		Validate.notNull(params, "Missing [" + PagerWebConst.REQUEST_LIST + "] from the execContext");

		String jasperFileName = null;
		String storage_config_ref = null;
		String outputFileName = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (HttpParam param : params) {
			if (param.getKey().equalsIgnoreCase("jasperFileName")) {
				jasperFileName = (String)param.getValue();
			} else if (param.getKey().equals(ClientParamNames.STORAGE_CONFIG_REF)) {
				storage_config_ref = (String) param.getValue();
			} else if (param.getKey().equals("outputFileName")) {
				outputFileName = (String) param.getValue();
			} else {
				map.put(param.getKey(), (String) param.getValue());
			}
		}
		Validate.notNull(jasperFileName, "Missing [jasperFileName] from the httpRequest");
		String path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
		File file = new File(path, jasperFileName);
		if (!file.exists() || file.isDirectory()) {
			Validate.notNull(null, "File [" + file.getAbsolutePath() + "] not found.");
		}

		Validate.notNull(outputFileName, "Missing [outputFileName] from the httpRequest");

		Validate.notNull(storage_config_ref, "Missing [" + ClientParamNames.STORAGE_CONFIG_REF + "] from the httpRequest");

        StorageConfig storageConfig = (StorageConfig) execContext.get(storage_config_ref);
        Validate.notNull(storageConfig, "No [" + StorageConfig.class.getName() + "] found in ExecContext ["
                + storage_config_ref + "]");
        
        ReportGenerator reportGenerator = new ReportGenerator();
        Connection conn = null;
        try {
        	conn = storageConfig.getDbConnector().getConnection(execContext);
        	byte [] image = reportGenerator.generateJasperToPdf(conn, map, file.getAbsolutePath());
        	execContext.put("image", image);
        	execContext.put("outputFileName", outputFileName);
        } finally {
        	DBUtils.closeQuietly(conn);
        }
		
		// TODO Auto-generated method stub
		return null;
	}
}
