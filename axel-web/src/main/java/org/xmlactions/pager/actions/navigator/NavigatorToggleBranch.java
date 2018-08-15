package org.xmlactions.pager.actions.navigator;


import java.util.Map;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.web.PagerWebConst;

public class NavigatorToggleBranch extends BaseAction {

    private static final String NAV_BRANCH_ID = "nav.branch.id";
    private static final Logger logger = LoggerFactory.getLogger(NavigatorToggleBranch.class);

    public String execute(IExecContext execContext) throws Exception {

        Map<String, Object> request = execContext.getNamedMap(PagerWebConst.REQUEST);
        String id = (String) request.get(NAV_BRANCH_ID);
        id = execContext.getString("request:" + NAV_BRANCH_ID);
        return "OK:";
    }

    public void test() {

    }
}
