package com.orange.links.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface LinksClientBundle extends ClientBundle {

    interface LinksCssResource extends CssResource {

        @ClassName("links-connection-popup")
        String connectionPopup();

        @ClassName("links-connection-popup-item")
        String connectionPopupItem();

        @ClassName("links-highlight-widget")
        String highlightWidget();

        @ClassName("links-translucide")
        String translucide();

        @ClassName("links-connection-canvas")
        String connectionCanvas();

    }

    static final LinksClientBundle INSTANCE = GWT.create(LinksClientBundle.class);

    @Source("GwtLinksStyle.css")
    LinksCssResource css();

}