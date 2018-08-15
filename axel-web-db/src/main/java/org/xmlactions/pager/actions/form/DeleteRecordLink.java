
package org.xmlactions.pager.actions.form;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.Text;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.pager.actions.form.Link;
import org.xmlactions.pager.actions.form.StorageBaseFormAction;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlA;
import org.xmlactions.pager.actions.form.templates.HtmlBlank;
import org.xmlactions.pager.config.PagerConstants;


/**
 * Builds an add record presentation that allows the user to enter details for the new record.
 * <p>
 * The add fields come from the database schema.
 * </p>
 * <p>
 * parameters / attributes will build the html attributes needed for display.
 * </p>
 * 
 * @author mike
 * 
 */
public class DeleteRecordLink extends StorageBaseFormAction implements ILink
{

	private static final Logger log = LoggerFactory.getLogger(DeleteRecordLink.class);

	private String presentation_name;

	private String id;

	private String header_name;

	private String tooltip;

	private String href; // where the link goes to

	private String display_as; // can be either link or button, default = link.

	private String confirm_message;

	private String pk_value;
	
    private String is_allowed;	// if not set true than dont show.

	// ==> Image attributes start
	private String image;		// can also display an image as part of the link
	
	private String image_pos;	// if there is text this can be used to set the position of the image on the left or the right of the text.
	
	private String border;		// width of border if using an image
	
	private String image_width;	// may want to specify the width of the image
	
	private String image_height;	// may want to specify the height image
	// <== Image attributes end
    
	public String execute(IExecContext execContext)
	{

		this.validateStorage("pager:delete_record_link");

		return null;
	}

	public Html draw(IExecContext execContext, Theme theme)
	{

		String pkValue = getPk_value();
		if (StringUtils.isEmpty(pkValue)) {
			String fieldName = getPkFieldNameFromTable(execContext);
			// get the PK value from the xml
			pkValue = execContext.getString(PagerConstants.ROW_MAP_NAME + ":" + fieldName);
			// pkValue = row.getAttributeValueAsString(fieldName);
			if (StringUtils.isEmpty(pkValue)) {
				throw new IllegalArgumentException(String.format(execContext.getLocalizedString("config/lang/pager",
						"DeleteRecordLink.nopkvalue"), getTable_name(), fieldName));
			}
		} else {
			pkValue = execContext.replace(pkValue);
		}
		return buildLink(execContext, theme, getId(), pkValue);
	}

	private String getPkFieldNameFromTable(IExecContext execContext)
	{

        StorageConfig storageConfig = (StorageConfig) execContext.get(getStorage_config_ref());
		StorageContainer storageContainer = storageConfig.getStorageContainer();
		Storage storage = storageContainer.getStorage();
		Database database = storage.getDatabase(storageConfig.getDatabaseName());

		Table table = database.getTable(getTable_name());
		PK pk = table.getPk();
		Validate.notNull(pk, String.format(execContext.getLocalizedString("config/lang/pager",
				"DeleteRecordLink.nopkfield"), getTable_name()));
		return Table.buildTableAndFieldName(getTable_name(), pk.getName());
	}

	private Html buildLink(IExecContext execContext, Theme theme, String rowId, String pkValue)
	{

		String value = execContext.replace(getIs_allowed());
    	if (Text.isFalse(value)) {
    		return new HtmlBlank();
    	}
    	
    	String storageConfigRef = getStorage_config_ref();

		Link link = Link.newLink(this, getPresentation_name());

		link.setUri(getHref());
		link.setAction(null);
		Html htmlA = link.draw(execContext, theme);
		if (htmlA instanceof HtmlA) {
			String params = "";
			if (StringUtils.isNotEmpty(storageConfigRef)) {
				params = "'pk.value=" + pkValue + "&" + ClientParamNames.TABLE_NAME_MAP_ENTRY +"=" + getTable_name() + "&" + ClientParamNames.STORAGE_CONFIG_REF +"=" + storageConfigRef + "'";
			} else {
				params = "'pk.value=" + pkValue + "&" + ClientParamNames.TABLE_NAME_MAP_ENTRY +"=" + getTable_name() + "'";
				
			}
			if (StringUtils.isEmpty(getConfirm_message())) {
				((HtmlA)htmlA).setOnClick("return(showValidationErrors(deleteRecord('" + rowId + "'," + params + ")));");
			} else {
				((HtmlA)htmlA).setOnClick("return(showValidationErrors(deleteRecord('" + rowId + "'," + params + ",'" + StrSubstitutor.replace(getConfirm_message(), execContext) + "')));");
			}
		}
		return htmlA;
	}

	public void setHeader_name(String header_name)
	{

		this.header_name = header_name;
	}

	public String getHeader_name()
	{

		return header_name;
	}

	public void setTooltip(String tooltip)
	{

		this.tooltip = tooltip;
	}

	public String getTooltip()
	{

		return tooltip;
	}

	public void setUri(String uri)
	{

		this.href = uri;
	}

	public String getUri()
	{

		return href;
	}

	public void setHref(String href)
	{

		this.href = href;
	}

	public String getHref()
	{

		return href;
	}

	public void setDisplay_as(String display_as)
	{

		this.display_as = display_as;
	}

	public String getDisplay_as()
	{

		return display_as;
	}

	public String getId()
	{

		if (StringUtils.isEmpty(id)) {
			return this.getFirstValueFound("id");
		}
		return id;
	}

	public void setId(String id)
	{

		this.id = id;
	}

	public void setPk_value(String pk_value)
	{

		this.pk_value = pk_value;
	}

	public String getPk_value()
	{

		return pk_value;
	}

	public void setPresentation_name(String presentation_name)
	{

		this.presentation_name = presentation_name;
	}

	public String getPresentation_name()
	{

		return presentation_name;
	}

	public void setConfirm_message(String confirm_message)
	{

		this.confirm_message = confirm_message;
	}

	public String getConfirm_message()
	{

		return confirm_message;
	}

	public String getIs_allowed() {
		if (is_allowed == null) {
			return "true";
		}
		return is_allowed;
	}

	public void setIs_allowed(String is_allowed) {
		this.is_allowed = is_allowed;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getBorder() {
		return border;
	}

	public void setImage_pos(String image_pos) {
		this.image_pos = image_pos;
	}

	public String getImage_pos() {
		return image_pos;
	}

	public void setImage_width(String image_width) {
		this.image_width = image_width;
	}

	public String getImage_width() {
		return image_width;
	}

	public void setImage_height(String image_height) {
		this.image_height = image_height;
	}

	public String getImage_height() {
		return image_height;
	}

}