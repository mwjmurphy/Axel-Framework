package org.xmlactions.pager.actions.navigator;


import java.io.File;
import java.io.IOException;


import org.apache.commons.lang.Validate;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.web.RequestExecContext;

public class Navigator {

    private IExecContext execContext;

    private String id; // This is a unique identifier for each navigator.

    public Navigator() {
        setExecContext(RequestExecContext.get());
    }

    public Navigator(String id) {
        setId(id);
        setExecContext(RequestExecContext.get());
    }

    public Navigator(String id, IExecContext execContext) {
        setId(id);
        setExecContext(execContext);
    }

    public String buildHtml(String id, Theme theme) throws IOException {
        this.setId(id);
        String realPath = getExecContext().getString(ActionConst.WEB_REAL_PATH_BEAN_REF);
        File file = new File(realPath, "sample_nav" + id + ".xml");
        String xml = ResourceUtils.loadFile(file.getAbsolutePath());
        NavBar root = mapXmlToBean(xml);
        return root.buildHtml(execContext, theme).toString();
    }

    public String buildHtml(String id, String themeName) throws IOException {
        this.setId(id);
        Theme theme = getExecContext().getThemes().getTheme(themeName);
        Validate.notNull(theme, "No theme found for [" + themeName + "] in ExecContext");
        String realPath = getExecContext().getString(ActionConst.WEB_REAL_PATH_BEAN_REF);
        File file = new File(realPath, "sample_nav" + id + ".xml");
        String xml = ResourceUtils.loadFile(file.getAbsolutePath());
        NavBar root = mapXmlToBean(xml);
        return root.buildHtml(execContext, theme).toString();
    }

    public String buildHtml(IExecContext execContext, Theme theme, String xml) {
        NavBar root = mapXmlToBean(xml);
        return root.buildHtml(execContext, theme).toString();
    }
    public NavBar mapXmlToBean(String xml) {
        MapXmlToBean mapXmlToBean = new MapXmlToBean("/config/mapping/nav_xml_to_bean.xml");
        return (NavBar) mapXmlToBean.map(xml);
    }

    public String mapBeanToXml(NavBar root) {
        return MapBeanToXml.map(root, "/config/mapping/nav_bean_to_xml.xml");
    }
    
    public Html buildHtml(NavBar root, Theme theme) {
        Html div = root.buildHtml(execContext, theme);        
        return div;
    }

    public void setExecContext(IExecContext execContext) {
        this.execContext = execContext;
    }

    public IExecContext getExecContext() {
        return execContext;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
