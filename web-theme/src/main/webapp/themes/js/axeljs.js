var AxelJS= new AxelJavascript();

function AxelJavascript () {
}

AxelJavascript.prototype.setInnerHtmlById = function (id, html) {
	var object = document.getElementById(id);
	if (object) {
		object.innerHTML = html;
	}
};

AxelJavascript.prototype.setValueById = function (id, value) {
	var object = document.getElementById(id);
	
	if (object) {
		object.value = value;
	}
};

AxelJavascript.prototype.logger = function() {
	var level = 0;	// values 1 to 5 where 1 = debug, 2 = info, 3 = warn, 4 = error, 5 = severe
	return {
		setLevel : function(l) {
			level = l;
		},
		log : function(msg) {
			if (level <= 1 && window['console']) {
				console.log(msg);
			}
		},
		debug : function(msg) {
			if (level <= 1 && window['console']) {
				console.log(msg);
			}
		},
		info : function(msg) {
			if (this.level <= 2 && window['console']) {
				console.log(msg);
			}
		},
		warn : function(msg) {
			if (this.level <= 3 && window['console']) {
				console.log(msg);
			}
		},
		error : function(msg) {
			if (this.level <= 4 && window['console']) {
				console.log(msg);
			}
		},
		severe : function(msg) {
			if (this.level <= 5 && window['console']) {
				console.log(msg);
			}
		}
	};
}();


function _escape(text) {
	return encodeURIComponent(text);
	// return escape(text);
}
function captureInputs() {
	var inputs = document.getElementsByTagName('input');
	var inputParams = "";
	var prefix = "";
	for (var x = 0; x < inputs.length; x++) {
		if (inputs[x].name && inputs[x].value) {
			inputParams += prefix + inputs[x].name + "=" + _escape(inputs[x].value);
			prefix = "&";
		}
	}
	return inputParams;
}

function captureInputsFromElement(id)
{
	var element = document.getElementById(id);
	return captureElementInputs( element);
}
function captureElementInputs(element)
{
	if (!element) return "";
	
	var inputs = element.getElementsByTagName("input");
	var inputParams = "";
	var prefix = "";
	for (var x = 0; x < inputs.length; x++) {
		if (inputs[x].name && inputs[x].disabled == false) {
		//if (inputs[x].name && inputs[x].disabled == false && inputs[x].readOnly != true) {
			var capture = true;
			if ((inputs[x].type == "checkbox" || inputs[x].type == "radio") && inputs[x].checked != true) {
				capture = false;
			}
			if (capture == true) {
				inputParams += prefix + inputs[x].name + "=" + _escape(inputs[x].value);
				prefix = "&";
			}
		}
	}
	var inputs = element.getElementsByTagName("textarea");
	for (x = 0; x < inputs.length; x++) {
		//if (inputs[x].name && inputs[x].disabled == false && inputs[x].readOnly != true) {
		if (inputs[x].name && inputs[x].disabled == false) {
			inputParams += prefix + inputs[x].name + "=" + _escape(inputs[x].value);
			prefix = "&";
		}
	}

	var inputs = element.getElementsByTagName("select");
	for (var x = 0; x < inputs.length; x++) {
		// if (inputs[x].name && inputs[x].disabled == false && inputs[x].readOnly != true) {
		if (inputs[x].name && inputs[x].disabled == false) {
			inputParams += prefix + inputs[x].name + "=" + _escape(inputs[x].value);
			prefix = "&";
		}
	}
	return inputParams;
}


/** Capture selected inputs from the page. */
function captureSelectedInputs(inputNames) {
	var names = inputNames.split(",");
	var inputParams = "";
	var prefix = "";
	for ( var i = 0; i < names.length; i++) {
		var inputs = document.getElementsByName(names[i]);
		for (var x = 0; x < inputs.length; x++) {
			if (inputs[x].name) {
				inputParams += prefix + inputs[x].name + "=" + _escape(inputs[x].value);
				prefix = "&";
			}
		}
	}
	return inputParams;
}

/**
 * @param id this is the parent element that contains the common and groups inputs
 * @param tagName the name of the elements containing the common and group inputs 
 * @param groupCommonName the name of the element that contains the common inputs
 * @param groupName the name of the elements that contains each of the groups inputs
 */
function captureGroups(id, tagName, groupCommonName, groupName) {
	var parent = document.getElementById(id);
	if (parent) {
		var groupInputs = new Array();
		var sharedInputs = "";
		var elements = parent.getElementsByTagName(tagName);
		for (var x = 0; x < elements.length ; x++) {
			var element = elements[x];
			if (element.id == groupName) {
				var inputs = captureElementInputs(element);
				groupInputs.push(inputs);
			} else if (element.id == groupCommonName) {
				sharedInputs = captureElementInputs(element);
			}
		}
		// now send them ajax style to the server.
		for (var x = 0 ; x < groupInputs.length; x++) {
			var inputs = sharedInputs + "&" + groupInputs[x];
			var result = insertRecord("",inputs);
			if (showValidationErrors(result) ==false) {
				return false;	// error saving
			}
		}
		return true;	// all done
	} else {
		alert ("no element found matching [" + id + "]");
	}
}

/** Append all page inputs to a uri (link). */
function appendInputs(uri) {
	var appendChar = "?";
	if (uri.indexOf("?") >= 0) {
		appendChar = "&";
	}
	return uri + appendChar + captureInputs();
}
/** Append select page inputs to a uri (link). */
function appendInputs(uri, inputParams) {
	var appendChar = "?";
	if (uri.indexOf("?") >= 0) {
		appendChar = "&";
	}
	return uri + appendChar + inputParams;
}


function submitParentLink(link) {
	window.parent.location.href=link;
}	
function submitLink(link) {
	window.location.href=link;
}	
function submitLinkWithParams(id, link) {
	var inputParams = captureInputsFromElement(id);
	var linkAndParams = appendInputs(link, inputParams);
	window.location.href=linkAndParams;
}
/**
 * Capture all inputs from the whole page and append these into the form as
 * hidden fields.
 * 
 * @param formId
 *            is the id of the form we want to put the inputs into
 */
function captureInputsToForm(formId) {
	var form = document.getElementById(formId);
	var inputs = document.getElementsByTagName('input');
	for (var x = inputs.length - 1; x >= 0; x--) {
		if (inputs[x].name && inputs[x].value) {
			var input = buildHiddenInput(inputs[x].name, inputs[x].value);
			form.appendChild(input);
		}
	}
}
/** Builds a hidden input element. */
function buildHiddenInput(key, value, form) {
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("name", key);
	input.setAttribute("value", value);
	if (form) {
		form.appendChild(input);
	}
	return input;
}

/**
 * Set the action (where the form goes)
 * 
 * @param formId
 *            is the id of the form we want to put the inputs into
 * @param link
 *            where the form should go
 */
function submitForm(formId, link) {
	var form = document.getElementById(formId);
	if (form) {
		if (link) {
			form.action = link;
		}
		form.submit();
	} else {
		log.debug("invalid formId:" + formId);
	}
}

/**
 * Show/Hide an element.
 * 
 * @param id
 *            is the id of the element we want to show hide
 * @param button
 *            is the button we want to change the text content of
 */
function showHide(id, button, showingText, hiddenText) {
	var ele = document.getElementById(id);
	var text = document.getElementById(button);
	if (text.innerHTML == showingText) {
		ele.style.display = "block";
		text.innerHTML = hiddenText;
	} else {
		ele.style.display = "none";
		text.innerHTML = showingText;
	}
}

/**
 * Toggle the status of a check box between checked and unchecked
 * 
 * @param id
 *            of the checkbox element
 */
function toggleCheck(id) {
    var element = document.getElementById(id);
    if (element.checked == true) {
    	element.checked=false;
    } else {
    	element.checked=true;
    }
}


/**
 * Changes the value for a check box from true/false/true
 * @param id
 * @param falseValue
 * @param trueValue
 */
function toggleTrueFalse (id, falseValue, trueValue) {
	var element = document.getElementById(id);
	var curValue = element.value;
	if (curValue == falseValue) {
		element.value=trueValue;
	} else {
		element.value=falseValue;
	}
}

/**
 * Change an element from shown to hidden and vice versa,
 * depending on it's current settings.
 * 
 * @param id
 *            of element to hide or show
 */
function toggleShowHide(id, target) {
    var element = document.getElementById(id);
    var value = element.style.display;
    if (!value) {
    	show(id);
    } else if (value == 'none') {
        show(id);
    } else {
    	hide(id);
    }
}


/**
 * Hide an element
 * 
 * @param id
 *            of element to hide
 */
function hide(id) {
	var ele = document.getElementById(id);
	if (! ele) {
		"no element defined for '" + id + "'";
	}
	ele.style.display = "none";
}
function hideParent(id) {
	var ele = parent.document.getElementById(id);
	if (! ele) {
		"no element defined for '" + id + "'";
	}
	ele.style.display = "none";
}
function hideall(names) {
	for (var i = 0 ; i < names.length; i++) {
		hide(names[i]);
	}
}
function show(id) {
	try {
		
		var ele = document.getElementById(id);
		if (! ele) {
			"no element defined for '" + id + "'";
		}
		ele.style.display = "block";
	} catch (err) {
		AxelJS.logger.error("EX:show(" + id + ") - " + err.message);
	}
}

/**
 * make an element visible or invisible
 * 
 * @param name
 *            of element to set visible or hidden
 * @param visibility can be 'hidden' or 'visible'
 */
function setVisibility(name, visibility) {
	var inputs = document.getElementsByName(name);
	if (inputs) {
		for (var i = 0; i < inputs.length; i++) {
			inputs[i].style.visibility = visibility;
		}
	}
}


/** Set the value of the page(pageHolderId) to 1 */
function page_first(pageHolderId) {
	var pageHolder = document.getElementById(pageHolderId);
	pageHolder.value = 1;
}
/** Decrement the value of the page(pageHolderId) */
function page_prev(pageHolderId) {
	var pageHolder = document.getElementById(pageHolderId);
	var value = pageHolder.value;
	value--;
	if (value < 1)
		value = 1;
	pageHolder.value = value;
}
/** Increment the value of the page(pageHolderId) */
function page_next(pageHolderId, max) {
	var pageHolder = document.getElementById(pageHolderId);
	var value = pageHolder.value;
	value++;
	if (value > max)
		value = max;
	pageHolder.value = value;
}
/** Change to the last page(pageHolderId) */
function page_last(pageHolderId, max) {
	var pageHolder = document.getElementById(pageHolderId);
	pageHolder.value = max;
}

/**
 * Used for asynchroneous ajax calls
 */
function AjaxObject(url, inputParams) {

	this.url = url;
	this.inputParams = inputParams;
	
	/**
	 * @param callback - the method that gets called one the response has been received
	 */
	this.makeGetAjaxASyncCall = function(callback) {
		var xmlHttp = getXMLHttp();
		xmlHttp.onreadystatechange=function() {
			if (xmlHttp.readyState==4 && xmlHttp.status==200) {
				callback(xmlHttp.responseText);
			}
		};	
		xmlHttp.open("GET", appendInputs(this.url, this.inputParams), true);
		xmlHttp.send(null);
	};
	/**
	 * @param callback - the method that gets called one the response has been received
	 */
	this.makePostAjaxASyncCall = function(callback) {
		var xmlHttp = getXMLHttp();
		xmlHttp.onreadystatechange=function() {
			if (xmlHttp.readyState==4 && xmlHttp.status==200) {
				callback(xmlHttp.responseText);
			}
		};	
		xmlHttp.open("POST", appendInputs(this.url, this.inputParams), true);
		xmlHttp.send(null);
	};
	return this;
}
/** get the xml http request object */
function getXMLHttp() {
	var xmlhttp = null;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		alert("Your browser does not support XMLHTTP!");
	}
	return xmlhttp;
}

function makePostAjaxASyncCall(ajaxObject) {
	var xmlHttp = getXMLHttp();
	xmlHttp.onreadystatechange=function() {
		if (xmlHttp.readyState==4 && xmlHttp.status==200) {
			ajaxObject.responseText = xmlHttp.responseText;
			ajaxObject.callback(ajaxObject);
		}
	};	
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", ajaxObject.inputParams.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.send(ajaxObject.inputParams);
}

function makeAjaxSyncCall(url, inputParams, callback) {
	var xmlHttp = getXMLHttp();
	xmlHttp.open("GET", appendInputs(url, inputParams), false);
	xmlHttp.send(null);
	if (callback != undefined) {
		callback(xmlHttp.responseText);
	}
	return xmlHttp.responseText;
}

function makePostAjaxSyncCall(url, inputParams) {
	var xmlHttp = getXMLHttp();
	xmlHttp.open("POST", url, false);
	xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", inputParams.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.send(inputParams);
	return xmlHttp.responseText;
}

function populateIdFromAjaxLoad(id, url, inputParams, scriptBefore, scriptAfter) {
	if (scriptBefore) {
		eval(scriptBefore);
	}
	var response = makePostAjaxSyncCall(url, inputParams);
	var object = document.getElementById(id);
	if (object != null) {
		object.innerHTML = response;
		show(id);
	} else {
		var object = document.getElementById(id+"_axel");
		if (object != null)  {
			object.innerHTML = response;
			show(id+"_axel");
		}
	}
	if (scriptAfter) {
		eval(scriptAfter);
	}
}

function populateSelect(id, xml) {
	var selects = document.getElementsByName('tb_linked_child.id');
	selects[0].outerHTML = xml;
}
function buildSelect(id, inputParams) {
	if (isEmpty(inputParams)) {
		inputParams = captureInputsFromElement(id);
	}
	return makeAjaxSyncCall("build_select.ajax", inputParams);
}
function insertRecord(id, inputParams, callback) {
	if (isEmpty(inputParams)) {
		inputParams = captureInputsFromElement(id);
	}
	return makeAjaxSyncCall("insert_record.ajax", inputParams, callback);
}
function deleteRecord(id, inputParams, confirmMsg) {

	if (isEmpty (confirmMsg) == false) {
		if ( confirm(confirmMsg) == false) {
			return "OK:Record not deleted";
		}
	}
	if (isEmpty(inputParams)) {
		inputParams = captureInputsFromElement(id);
	}
	else
	{
		var ip = captureInputsFromElement(id);
		if (! isEmpty(ip) ) {
			inputParams =  ip + '&' + inputParams;
		}
	}
	return makeAjaxSyncCall("delete_record.ajax", inputParams);
}

function updateRecord(id, inputParams, confirmMsg, callback) {

	if (isEmpty (confirmMsg) == false) {
		if ( confirm(confirmMsg) == false) {
			return "OK:Record not updated";
		}
	}
	if (isEmpty(inputParams)) {
		inputParams = captureInputsFromElement(id);
	}
	else
	{
		var ip = captureInputsFromElement(id);
		if (! isEmpty(ip) ) {
			inputParams =  ip + '&' + inputParams;
		}
	}
	return makeAjaxSyncCall("update_record.ajax", inputParams, callback);
}

function saveRecord(id, inputParams, confirmMsg, callback) {

	if (isEmpty (confirmMsg) == false) {
		if ( confirm(confirmMsg) == false) {
			return "OK:Record not updated";
		}
	}
	if (isEmpty(inputParams)) {
		inputParams = captureInputsFromElement(id);
	}
	else
	{
		var ip = captureInputsFromElement(id);
		if (! isEmpty(ip) ) {
			inputParams =  ip + '&' + inputParams;
		}
	}
	return makeAjaxSyncCall("save_record.ajax", inputParams, callback);
}

function doLogin(id) {
	var inputParams = captureInputsFromElement(id);
	return makeAjaxSyncCall("do_login.ajax", inputParams);
}
function doSavePage(id) {
	synchronizeRteWithHtml(id);
	var inputParams = captureInputsFromElement(id);
	return makePostAjaxSyncCall("do_save_page.ajax", inputParams);
}
function reset() {
	return makeAjaxSyncCall("do_reset.ajax", "none");
}
function processEmailCallback(inputParams) {
	return makeAjaxSyncCall("process_email_callback.ajax", inputParams);
}
function processCodeCall(inputParams) {
	return makeAjaxSyncCall("code_call_handler.ajax", inputParams);
}

var previousErrorList;
function highlightErrors(errors, className) {
	var errorList = errors.substring(("ER:").length).split("&");
	for ( var i = 0; i < errorList.length; i++) {
		var keycode = errorList[i].split("=");
		var inputs = document.getElementsByName(keycode[0]);
		for ( var x = 0; x < inputs.length; x++) {
			inputs[x].className = className;
		}
	}
}

function isEmpty(txt) {
	if (txt != 'undefined' && txt != null
			&& txt.length > 0) {
		return false;
	}
	return true;
}

function showValidationErrors(errors) {
	if (previousErrorList != 'undefined' && previousErrorList != null
			&& previousErrorList.length > 0) {
		highlightErrors(previousErrorList, "editbox");
	}
	previousErrorList = "";
	if (typeof errors != 'undefined') {
		if (errors.search("EX:") == 0) {
			alert("Error:" + errors.substr(3));
		} else if (errors.search("ER:") == 0) {
			highlightErrors(errors, "editbox_invalid");
			previousErrorList = errors;
			alert("The high-lighted areas contain errors or are not completed correctly");
		} else if (errors.search("OK:") == 0) {
			return true;
		} else if (errors.search("NT:") == 0) {
			alert(errors.substr(3));
			return true;
		} else {
			alert("The response was [" + errors + "]. Try using a respons of 'OK:' !!!");
		}
		return false;
	}
	return true;
}

function toHex(str) {
	var hex = "";
	for (var i = 0 ; i < str.length;i++) {
		hex += str.charCodeAt(i).toString(16);
	}
	return hex;
}

function do_preview_tab_select(id) {
	synchronizeRteWithHtml(id);
	var inputParams = captureInputsFromElement(id);
	var content = makePostAjaxSyncCall("do_process_page_for_preview.ajax", inputParams);
	var preview = document.getElementById(id+"_preview");
	preview.innerHTML = content;
	show(id+"_preview");
	hide(id+"_html_div");
	hide(id+"_rte_div");
}
function do_edit_tab_select(id){
	synchronizeRteWithHtml(id);
	hide(id+"_preview");
	show(id+"_html_div");
	hide(id+"_rte_div");
}
function do_rte_tab_select(id){
	synchronizeHtmlWithRte(id);
	hide(id+"_preview");
	hide(id+"_html_div");
	show(id+"_rte_div");
}
function do_add_page(id){
	synchronizeRteWithHtml(id);
	var pageName = prompt("Enter new page name:", "");
	var pageNames = document.getElementsByName("page.name");
	for (var x = 0; x < pageNames.length; x++) {
		pageNames[x].value = pageName;
	}
}

function synchronizeRteWithHtml(id) {
	var ele = document.getElementById(id+"_rte_div");
	if (ele.style.display != "none") {
		var content = CKEDITOR.instances.edit_page_rte.getData();
		// var content = document.getElementById(id+"_rte").value;
		document.getElementById(id+"_html").value = content;
	}
}
function synchronizeHtmlWithRte(id) {
	var ele = document.getElementById(id+"_rte_div");
	if (ele.style.display == "none") {
		var content = document.getElementById(id+"_html").value;
		// document.getElementById(id+"_rte").value = content;
		CKEDITOR.instances.edit_page_rte.setData(content);
	}
}

function isEnterKey(event) {
	if (!event) event = window.event;   // resolve event instance
    if (event.keyCode == '13'){
    	return true;
    }
    return false;	
}

function reloadPreviousPage() {
	var supportsReferrer = document.referrer ? true : false;
	if (supportsReferrer == true) {
		var url = document.referrer;
		submitLink(url);
	} else {
		window.location.reload(history.go(-1));
	}
}
