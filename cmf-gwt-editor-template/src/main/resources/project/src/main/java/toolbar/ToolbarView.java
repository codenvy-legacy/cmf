package current_package;

import com.codenvy.editor.api.mvp.AbstractView;
import com.google.inject.ImplementedBy;

@ImplementedBy(ToolbarViewImpl.class)
public abstract class ToolbarView extends AbstractView<ToolbarView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

action_delegates    }

}