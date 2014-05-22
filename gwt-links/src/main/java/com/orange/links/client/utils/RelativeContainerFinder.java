package com.orange.links.client.utils;

import com.google.gwt.dom.client.Element;

public class RelativeContainerFinder implements IContainerFinder {

    public boolean isContainer(Element element) {
        return "relative".equals(element.getStyle().getProperty("position"));
    }

}
