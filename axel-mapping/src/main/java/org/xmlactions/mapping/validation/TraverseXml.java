package org.xmlactions.mapping.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;


public class TraverseXml {

    public Element getSnippet(Element root, String fullPath) {

        String[] paths = getPaths(fullPath);

        Element parent = root;
        
        if (!match(paths[0], parent.getName())) {
            throw new IllegalArgumentException("No Element Snippet found for [" + fullPath + "]");
        }

        for (int index = 1; index < paths.length; index++) {
            String path = paths[index];
            List<Element> children = getElements(parent, path);
            if (children.size() > 0) {
                parent = children.get(0);
            } else {
                throw new IllegalArgumentException("No Element Snippet found for [" + fullPath + "]");
            }
        }
        return parent;
    }

    public List<Element> getSnippets(Element root, String fullPath) {

        String[] paths = getPaths(fullPath);

        Element parent = root;

        if (!match(paths[0], parent.getName())) {
            throw new IllegalArgumentException("No Element Snippets found for [" + fullPath + "]");
        }

        List<Element> list = new ArrayList<Element>();

        boolean firstProcessed = false;
        for (int index = 1; index < paths.length; index++) {
            String path = paths[index];
            if (firstProcessed == false) {
                List<Element> children = getElements(parent, path);
                list.addAll(children);
                // list.addAll(parent.elements(path));
                firstProcessed = true;
            } else {
                list = getSnippets(list, path);
            }
        }
        return list;
    }

    private List<Element> getSnippets(List<Element> roots, String path) {

        List<Element> list = new ArrayList<Element>();
        for (Element root : roots) {
            list.addAll(root.elements(path));
        }
        return list;
    }

    private List<Element> getElements(Element parent, String regex) {
        List<Element> list = new ArrayList<Element>();

        List<Element> children = parent.elements();
        for (Element child : children) {
            if (match(regex, child.getName())) {
                list.add(child);
            }
        }
        return list;
    }

    private boolean match(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(text);
        
        return matcher.find();

    }

    private String [] getPaths(String fullPath) {
        if (StringUtils.isEmpty(fullPath)) {
            throw new IllegalArgumentException("Empty Element Path");
        }
        String[] paths = fullPath.split("/");
        if (paths.length <= 0) {
            throw new IllegalArgumentException("Invalid or Empty Element Path");
        }
        return paths;
    }

}
