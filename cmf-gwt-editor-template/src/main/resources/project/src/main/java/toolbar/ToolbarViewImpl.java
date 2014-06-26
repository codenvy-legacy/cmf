package current_package;

import main_package.client.EditorResources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ToolbarViewImpl extends ToolbarView {

    interface ToolbarViewImplUiBinder extends UiBinder<Widget, ToolbarViewImpl> {
    }

    private static final int ITEM_HEIGHT = 37;

ui_fields    @UiField
    DockLayoutPanel mainPanel;

    private final Map<String, PushButton> buttons;

    @Inject
    public ToolbarViewImpl(ToolbarViewImplUiBinder ourUiBinder, EditorResources resources) {
        buttons = new LinkedHashMap<>();

fields_initialize
bind_element_buttons
        widget = ourUiBinder.createAndBindUi(this);
    }

    /** {@inheritDoc} */
    @Override
    public void showButtons(@Nonnull Set<String> components) {
        mainPanel.clear();

        for (Map.Entry<String, PushButton> entry : buttons.entrySet()) {
            String elementName = entry.getKey();
            PushButton button = entry.getValue();

            if (components.contains(elementName)) {
                mainPanel.addNorth(button, ITEM_HEIGHT);
            }
        }

add_connection_button    }

action_delegates}