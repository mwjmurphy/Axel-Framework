package org.xmlactions.action;

import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class DirectAjaxCodeCallAction extends BaseAction {

	private static final String returnThisJsonData = "{\n" +
	"'pages':[\n" +
	"	{\n" +
	"		'title' : 'This is the first page',\n" +
	"		'detail' : 'It shows some information about the first page',\n" +
	"		'img' : 'first_page_image.png'\n" +
	"	},\n" +
	"	{\n" +
	"		'title' : 'This is the second page',\n" +
	"		'detail' : 'It shows some information about the second page',\n" +
	"		'img' : 'second_page_image.png'\n" +
	"	},\n" +
	"	{\n" +
	"		'title' : 'This is the third page',\n" +
	"		'detail' : 'It shows some information about the third page',\n" +
	"		'img' : 'third_page_image.png'\n" +
	"	},\n" +
	"	{\n" +
	"		'title' : 'This is the fourth page',\n" +
	"		'detail' : 'It shows some information about the fourth page',\n" +
	"		'img' : 'fourth_page_image.png'\n" +
	"	},\n" +
	"	{\n" +
	"		'title' : 'This is the fifth page',\n" +
	"		'detail' : 'It shows some information about the fifth page',\n" +
	"		'img' : 'fifth_page_image.png'\n" +
	"	},\n" +
	"	{\n" +
	"		'title' : 'This is the sixth page',\n" +
	"		'detail' : 'It shows some information about the sixth page',\n" +
	"		'img' : 'sixth_page_image.png'\n" +
	"	}\n" +
	"]\n" +	
"}";
	
	@Override
	public String execute(IExecContext execContext) throws Exception {
		return returnThisJsonData;
	}
}
