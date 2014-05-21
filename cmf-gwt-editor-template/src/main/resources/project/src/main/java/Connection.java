package current_package;

import com.codenvy.editor.api.editor.elements.AbstractLink;
import com.codenvy.editor.api.editor.elements.Shape;
import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class connection_name extends AbstractLink {

    public connection_name(Shape source, Shape target) {
        super(source, target, "connection_name", new ArrayList<String>());
    }

    @Override
    public void deserialize(@Nonnull String content) {

    }

    @Override
    public void deserialize(@Nonnull Node node) {

    }

    @Override
    public void applyProperty(Node node) {

    }

}