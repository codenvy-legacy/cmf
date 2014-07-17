package current_package;

import com.codenvy.editor.api.editor.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.editor.api.editor.propertytypes.PropertyTypeManager;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import main_package.client.elements.elementName;

public class elementNamePropertiesPanelPresenter extends AbstractPropertiesPanel<elementName>
        implements elementNamePropertiesPanelView.ActionDelegate {

    @Inject
    public elementNamePropertiesPanelPresenter(elementNamePropertiesPanelView view, PropertyTypeManager propertyTypeManager) {
        super(view, propertyTypeManager);
    }

property_change_methods    @Override
    public void go(AcceptsOneWidget container) {
        super.go(container);

initialize_property_fields    }

}