package current_package

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.orange.links.client.utils.LinksClientBundle;
import main_package.inject.Injector;

public class EditorEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        LinksClientBundle.INSTANCE.css().ensureInjected();

        Injector injector = GWT.create(Injector.class);
        editor_name editor = injector.getEditor();

        SimpleLayoutPanel mainPanel = new SimpleLayoutPanel();
        editor.go(mainPanel);

        RootLayoutPanel.get().add(mainPanel);
    }

}
