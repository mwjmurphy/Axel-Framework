package org.xmlactions.pager.actions.navigator;

import java.util.ArrayList;
import java.util.List;

public class Options {

    private String title;
    private String width;

    private List<Option> options = new ArrayList<Option>();

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOption(Option option) {
        options.add(option);
    }

    public Option getOption() {
        return options.get(options.size()-1);
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return width;
    }

}
