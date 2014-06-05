package current_package;

import main_package.client.EditorResources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ToolbarViewImpl extends ToolbarView {

    interface ToolbarViewImplUiBinder extends UiBinder<Widget, ToolbarViewImpl> {
    }

ui_fields
    @Inject
    public ToolbarViewImpl(ToolbarViewImplUiBinder ourUiBinder, EditorResources resources) {
fields_initialize
        widget = ourUiBinder.createAndBindUi(this);
    }

action_delegates}