package current_package;

import com.codenvy.editor.api.editor.elements.AbstractLink;
import com.codenvy.editor.api.editor.elements.Shape;
import com.google.gwt.xml.client.Node;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class connectionName extends AbstractLink {

    public connectionName() {
        this(null, null);
    }

    public connectionName(@Nullable String source, @Nullable String target) {
        super(source, target, "connectionName", new ArrayList<String>(), new ArrayList<String>());
    }

}