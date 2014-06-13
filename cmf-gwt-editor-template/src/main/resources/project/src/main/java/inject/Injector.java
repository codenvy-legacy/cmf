package current_package;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import main_package.client.editor_name;

@GinModules(GinModule.class)
public interface Injector extends Ginjector {

    editor_name getEditor();

}
