package org.xmlactions.pager.actions.navigator;

public class Option {

    private String uri;
    private String label;
    private String tooltip;
    private String align = "left";

    public Option () {
    	int i = 1;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

	public void setAlign(String align) {
		this.align = align;
	}

	public String getAlign() {
		return align;
	}
}
