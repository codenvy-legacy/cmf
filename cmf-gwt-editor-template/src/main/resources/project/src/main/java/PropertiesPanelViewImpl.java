package current_package;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

public class element_namePropertiesPanelViewImpl extends element_namePropertiesPanelView {

    interface element_namePropertiesPanelViewImplUiBinder extends UiBinder<Widget, element_namePropertiesPanelViewImpl> {
    }

ui_fields
    @Inject
    public element_namePropertiesPanelViewImpl(element_namePropertiesPanelViewImplUiBinder ourUiBinder) {
        widget = ourUiBinder.createAndBindUi(this);
    }

action_delegates}