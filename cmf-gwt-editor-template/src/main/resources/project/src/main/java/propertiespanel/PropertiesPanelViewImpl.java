package current_package;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

public class elementNamePropertiesPanelViewImpl extends elementNamePropertiesPanelView {

    interface elementNamePropertiesPanelViewImplUiBinder extends UiBinder<Widget, elementNamePropertiesPanelViewImpl> {
    }

ui_fields
    @Inject
    public elementNamePropertiesPanelViewImpl(elementNamePropertiesPanelViewImplUiBinder ourUiBinder) {
        widget = ourUiBinder.createAndBindUi(this);
    }

action_delegates}