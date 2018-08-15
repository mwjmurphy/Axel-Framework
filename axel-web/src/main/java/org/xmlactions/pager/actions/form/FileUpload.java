
package org.xmlactions.pager.actions.form;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.form.templates.HtmlBr;
import org.xmlactions.pager.actions.form.templates.HtmlForm;
import org.xmlactions.pager.actions.form.templates.HtmlIFrame;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.config.PagerConstants;
import org.xmlactions.web.conceal.HtmlRequestMapper;


public class FileUpload extends CommonFormFields
{

	private String path = "";
	
	private String field_name;	// (required for db storage) this is the name of the table.field that will be 
								// used to store the content of the file into.
	private String field_file_name;	// (optional) this is the name of the table.field that will be used to store the file name.
	private String field_fk_ref;	// (optional) this is the name of the table.field that will be used to store a foreign table reference key.
	private String field_fk_ref_value;	// (optional) this is the value for the field_fk_ref

	private String javascript = "<script type=\"text/javascript\">\n" // 
			+ "function init() {\n" //
			+ "	document.getElementById('file_upload_form').onsubmit=function() {\n" //
			+ "		document.getElementById('file_upload_form').target = 'upload_target';\n" //
			+ "	}\n" + "}\n" //
			+ "window.onload=init;\n" //
			+ "</script>";//

	public String execute(IExecContext execContext) throws Exception
	{

		validate(execContext);

		// TODO Auto-generated method stub
		return buildPresentation(execContext);
	}

	private void validate(IExecContext execContext)
	{

		//if (StringUtils.isEmpty(getPath())) {
		//	throw new IllegalArgumentException("Missing path attribute");
		//}
		int count = 0;
		if (StringUtils.isNotEmpty(getTable_name())) {
			count++;
		}
		if (StringUtils.isNotEmpty(getField_name())) {
			count++;
		}
		if (count > 0 && count < 2 ) {
			throw new IllegalArgumentException("To store a file into a database all the required attributes (table_name and field_name) must be set. Or to store the file on the server file system remove the database storage attributes.");
		}
		setTheme( execContext.getThemes().getTheme(getTheme_name(execContext)));

	}

	private String buildPresentation(IExecContext execContext)
	{

		StringBuilder sb = new StringBuilder();
		sb.append(this.javascript);
		HtmlForm form = new HtmlForm();
		form.setId(getId());
		form.setMethod("post");
		form.setEnctype("multipart/form-data");
		form.setTarget("upload_target");
		//form.setAction("uploader.xhtml");
		form.setAction("");
		form.setAction("do_upload.ajax");
		
		// form.setOnSubmit("return showValidationErrors(doUpload('" + getId() + "'));");
		
		// Hidden Inputs
		HtmlInput input = new HtmlInput();
		input.setName(HtmlRequestMapper.UPLOAD_FILE_PATH);
		input.setType("hidden");
		input.setValue(getPath());
		form.addChild(input);
		
		if (StringUtils.isNotEmpty(getStorage_config_ref())) {
			input = new HtmlInput();
			input.setName(HtmlRequestMapper.UPLOAD_STORAGE_CONFIG_REF);
			input.setType("hidden");
			input.setValue(getStorage_config_ref());
			form.addChild(input);
		}
		
		if (StringUtils.isNotEmpty(getTable_name())) {
			input = new HtmlInput();
			input.setName(HtmlRequestMapper.UPLOAD_TABLE_NAME);
			input.setType("hidden");
			input.setValue(getTable_name());
			form.addChild(input);
		}
		
		if (StringUtils.isNotEmpty(getField_name())) {
			input = new HtmlInput();
			input.setName(HtmlRequestMapper.UPLOAD_FIELD_NAME);
			input.setType("hidden");
			input.setValue(getField_name());
			form.addChild(input);
		}
		
		if (StringUtils.isNotEmpty(getField_file_name())) {
			input = new HtmlInput();
			input.setName(HtmlRequestMapper.UPLOAD_FIELD_FILE_NAME);
			input.setType("hidden");
			input.setValue(getField_file_name());
			form.addChild(input);
		}
		
		if (StringUtils.isNotEmpty(getField_fk_ref())) {
			input = new HtmlInput();
			input.setName(HtmlRequestMapper.UPLOAD_FIELD_FK_REF);
			input.setType("hidden");
			input.setValue(getField_fk_ref());
			form.addChild(input);
		}
		
		if (StringUtils.isNotEmpty(getField_fk_ref_value())) {
			input = new HtmlInput();
			input.setName(HtmlRequestMapper.UPLOAD_FIELD_FK_REF_VALUE);
			input.setType("hidden");
			input.setValue(execContext.replace(getField_fk_ref_value()));
			form.addChild(input);
		}
		
		if (StringUtils.isNotEmpty(getContent())) {
			form.setContent(getContent());
		}

		input = new HtmlInput();
		input.setName("upload_file_data");
		input.setId("file");
		//input.setSize("27");
		input.setType("file");
		input.setTitle(getTooltip());
		form.addChild(input);

		input = new HtmlInput();
		input.setName("action");
		input.setType("submit");
		input.setValue(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_KEY_UPLOAD));
		input.setTitle(getTooltip());
		input.setStyle("padding-left:3px;padding-right:3px; padding-top:2px; padding-bottom:2px");
		form.addChild(input);

		form.addChild(new HtmlBr());
		HtmlIFrame iframe = new HtmlIFrame();
		iframe.setId("upload_target");
		iframe.setName("upload_target");
		iframe.setSrc("");
		iframe.setStyle("width: 200px; height: 30px; border: 0px solid #fff; display:block;");
		iframe.setOnLoad("showValidation(frames['upload_target'].document.getElementsByTagName('body')[0].innerHTML);");
		iframe.setContent("filler");
		form.addChild(iframe);

		return (form.toString());
	}

	public void setPath(String path)
	{

		this.path = path;
	}

	public String getPath()
	{

		return path;
	}

	public void setField_name(String field) {
		this.field_name = field;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_file_name(String field_file_name) {
		this.field_file_name = field_file_name;
	}

	public String getField_file_name() {
		return field_file_name;
	}

	public void setField_fk_ref(String field_fk_ref) {
		this.field_fk_ref = field_fk_ref;
	}

	public String getField_fk_ref() {
		return field_fk_ref;
	}

	public void setField_fk_ref_value(String field_fk_ref_value) {
		this.field_fk_ref_value = field_fk_ref_value;
	}

	public String getField_fk_ref_value() {
		return field_fk_ref_value;
	}

}
