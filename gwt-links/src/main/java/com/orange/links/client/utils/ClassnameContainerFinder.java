package com.orange.links.client.utils;

import com.google.gwt.dom.client.Element;

public class ClassnameContainerFinder implements IContainerFinder {

    private static final String PARENT_CLASS_NAME = "dragdrop-boundary";

    public boolean isContainer(Element element) {
        return element.getAttribute("class").contains(PARENT_CLASS_NAME);
    }

}