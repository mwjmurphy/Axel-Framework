package org.xmlactions.action.actions;

/**
 
 \page org_xmlactons_action_actions AXEL Actions
 
 \tableofcontents

 \section axel_actions Actions
 
 Actions provide the core functionality of AXEL.
 
 When an action is called from a web page the implemented <b>.execute(...)</b> method is invoked.  The response from an action if not null
 will replace the action content (syntax) on the web page.  If the response is null the action syntax is removed from the web page.
 
 As an example:
 
 The following excerpt from a web page shows how the echo action is invoked.
 \code{.xml}
 	<pager:echo>This is echo'ed on the web page</pager:echo>
 \endcode
 The content "This is echo'ed on the web page" will replace the "<pager:echo>This is echo'ed on the web page</pager:echo>"

 The following code is the actual EchoAction class that gets invoked from <pager:echo>
 \code {.java}
	package org.xmlactions.pager.actions;
	
	import org.apache.commons.lang.text.StrSubstitutor;
	import org.slf4j.Logger; import org.slf4j.LoggerFactory;
	import org.xmlactions.action.actions.BaseAction;
	import org.xmlactions.action.config.IExecContext;
	
	public class EchoAction extends BaseAction {
	
		private static Logger log = LoggerFactory.getLogger(EchoAction.class);
	
		// Invoked from AXEL Framework
		public String execute(IExecContext execContext) {
			return StrSubstitutor.replace(getContent(), execContext);
		}
	
		public String toString() {
			return "echo [" + getContent() + "]";
		}
	}
 \endcode
 
 What's happening in the above code:
  1. The execute action is invoked from the AXEL framework
  2. The content "This is echo'ed on the web page" is already populated into the BaseAction.content property and is available from the .getContent() method.
  3. The <i>return StrSubstitutor.replace(getContent(), execContext);</i> gets the content and performs a String Substitution replacing any patterns ${...} from the execContext and returns this value.
  4. AXEL then replaces the "<i><pager:echo>This is echo'ed on the web page</pager:echo></i>" with the returned value.
  
 \subsection axel_action_definition Defining AXEL Actions
 
  Page Actions are related to the code by using the action name such as <pager:<b>echo</b> and using this as the key to determine the Java code to invoke. in this case
  <b>echo=echo=org.xmlactions.pager.actions.EchoAction</b>.
  
  The actions are stored in an actions property file.  The following is the content of the config/pager/actions.properties which is configured in the \ref axel_web_server_spring_configuration.
 \code {.properties}
	##
	# action list
	## 
	debug=org.xmlactions.pager.actions.DebugAction
	echo=org.xmlactions.pager.actions.EchoAction
	code=org.xmlactions.pager.actions.CodeAction
	param=org.xmlactions.pager.actions.Param
	insert=org.xmlactions.pager.actions.InsertAction
	insert_into=org.xmlactions.pager.actions.InsertIntoAction
	if=org.xmlactions.pager.actions.IfAction
	elseif=org.xmlactions.pager.actions.ElseIfAction
	else=org.xmlactions.pager.actions.ElseAction
	get=org.xmlactions.pager.actions.GetAction
	put=org.xmlactions.pager.actions.PutAction
	menu=org.xmlactions.pager.actions.menu.MenuAction
	transform=org.xmlactions.pager.actions.TransformAction
	highlight=org.xmlactions.pager.actions.highlighter.HighlighterAction
	navigator=org.xmlactions.pager.actions.navigator.NavigatorAction
	popup=org.xmlactions.pager.actions.PopupAction
	
	# can store any named attributes here. Useful when we want a set of attributes to set into an action
	# such as an Html Action.
	attributes=org.xmlactions.action.actions.Attributes
	
	# Form Display Actions
	search=org.xmlactions.pager.actions.form.Search
	list=org.xmlactions.pager.actions.form.List
	add_record_link=org.xmlactions.pager.actions.form.AddRecordLink
	update_record_link=org.xmlactions.pager.actions.form.UpdateRecordLink
	delete_record_link=org.xmlactions.pager.actions.form.DeleteRecordLink
	listcp=org.xmlactions.pager.actions.form.ListCP
	view=org.xmlactions.pager.actions.form.ViewForm
	edit=org.xmlactions.pager.actions.form.Edit
	query=org.xmlactions.pager.actions.query.QueryAction
	add=org.xmlactions.pager.actions.form.Add
	field_list=org.xmlactions.pager.actions.form.FieldList
	field=org.xmlactions.pager.actions.form.Field
	field_hide=org.xmlactions.pager.actions.form.FieldHide
	field_code=org.xmlactions.pager.actions.form.FieldCode
	field_raw=org.xmlactions.pager.actions.form.FieldRaw
	# list_populator has been deprecated.
	list_populator=org.xmlactions.pager.actions.form.ListPopulator
	populator_sql=org.xmlactions.pager.actions.form.populator.SqlPopulator
	populator_code=org.xmlactions.pager.actions.form.populator.CodePopulator
	link=org.xmlactions.pager.actions.form.Link
	button=org.xmlactions.pager.actions.form.Button
	reset=org.xmlactions.pager.actions.form.Reset
	file_upload=org.xmlactions.pager.actions.form.FileUpload
	file_viewer=org.xmlactions.pager.actions.form.FileViewerAction
	frame=org.xmlactions.pager.actions.form.FrameAction
	post_processes=org.xmlactions.pager.actions.form.PostProcesses
	pre_processes=org.xmlactions.pager.actions.form.PreProcesses
	callback_phone=org.xmlactions.pager.actions.form.CallbackPhoneAction
	submit_form=org.xmlactions.pager.actions.submit.SubmitFormAction
	
	# DB Actions
	add_record=org.xmlactions.pager.actions.db.AddRecordAction
	
	# Form Submit Actions - from JavaScript
	insert_record=org.xmlactions.pager.actions.db.InsertRecord
	update_record=org.xmlactions.pager.actions.db.UpdateRecord
	delete_record=org.xmlactions.pager.actions.db.DeleteRecord
	do_reset=org.xmlactions.pager.actions.db.DoReset
	do_upload=org.xmlactions.pager.actions.db.DoUpload
	build_select=org.xmlactions.pager.actions.form.AjaxSelect
	process_email_callback=org.xmlactions.pager.actions.email.ProcessCallbackPhone
	code_call_handler=org.xmlactions.pager.actions.ajax.handler.CodeCallHandler
	
	# Page Edit actions
	edit_page=org.xmlactions.pager.actions.form.EditPage
	do_save_page=org.xmlactions.pager.actions.page.DoSavePage
	do_process_page_for_preview=org.xmlactions.pager.actions.page.DoProcessPageForPreview
	
	# Navigator actions
	nav_toggle_branch=org.xmlactions.pager.navigator.NavigatorToggleBranch
	
	# Mapping actions
	map_xml_to_bean=org.xmlactions.pager.actions.mapping.XmlToBeanAction
	map_bean_to_xml=org.xmlactions.pager.actions.mapping.BeanToXmlAction
	map_json_to_presentation=org.xmlactions.pager.actions.mapping.JSONToPresentationAction
	map_xml_to_json=org.xmlactions.pager.actions.mapping.XmlToJSONAction
	
	# Jasper Report Actions
	create_pdf=org.xmlactions.pager.jasper.JasperReportHandler

	# END
 \endcode
  
 \subsection axel_implement_own_action Implementing an Action
 
  It's quite easy to implement your own action. The steps are
  1. Create a class that implements the BaseAction
  2. Add your code to the execute method.
  3. Create a new actions property file and add your action key and the related code to execute. i.e. myaction=com.mybusiness.action.MyClass
  4. Add the new action file name to the Spring Configuration bean for pager.execContext constructor-arg 1. See \ref axel_web_server_spring_configuration
  5. Add the new action to a web page. i.e. <pager:myaction/>
  6. Test the web page by deploying it on a web server as described in \ref org_xmlactons_web_server_configuration

  Once you get this working you can expand the action capabilities by adding attributes and parameters.
  
  \see
   org.xmlactions.pager.actions.CodeAction for an Action that uses both attributes and parameters.<br/>
   \ref axel_actions_list for a list of AXEL Actions
    
 
*/
