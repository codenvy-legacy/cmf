package com.orange.links.client.shapes;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;
import com.orange.links.client.canvas.DiagramCanvas;
import com.orange.links.client.utils.Couple;
import com.orange.links.client.utils.Direction;
import com.orange.links.client.utils.LinksClientBundle;

public class FunctionShape extends AbstractShape {

    private int      selectableAreaRadius         = 8;
    private CssColor highlightSelectableAreaColor = CssColor.make("#DDDDDD");

    Point centerW;
    Point centerN;
    Point centerS;
    Point centerE;

    public FunctionShape(DiagramController controller, Widget widget) {
        super(controller, widget);
    }

    @Override
    public void setSynchronized(boolean sync) {
        if (allowSync && !sync) {
            centerW = null;
            centerN = null;
            centerS = null;
            centerE = null;
        }
        super.setSynchronized(sync);
    }

    public boolean isMouseNearSelectableArea(Point mousePoint) {
        return getSelectableArea(mousePoint) != null;
    }

    public void highlightSelectableArea(Point mousePoint) {
        // Mouse Point
        Couple<Direction, Point> couple = getSelectableArea(mousePoint);
        Direction direction = couple.getFirstArg();
        Point closestSelectablePoint = couple.getSecondArg();
        if (closestSelectablePoint != null) {
            DiagramCanvas canvas = controller.getDiagramCanvas();
            canvas.beginPath();
            canvas.arc(closestSelectablePoint.getLeft(),
                       closestSelectablePoint.getTop(),
                       selectableAreaRadius, direction.getAngle() - Math.PI / 2,
                       direction.getAngle() + Math.PI / 2,
                       true);
            canvas.setStrokeStyle(highlightSelectableAreaColor);
            canvas.stroke();
            canvas.setFillStyle(highlightSelectableAreaColor);
            canvas.fill();
            canvas.closePath();
        }
    }

    public Couple<Direction, Point> getSelectableArea(Point p) {
        // Center of the selectable areas
        if (centerW == null || !isSynchronized()) {
            centerW = Point.make().x(getLeft()).y(getTop() + getHeight() / 2);
            centerN = Point.make().x(getLeft() + getWidth() / 2).y(getTop());
            centerS = Point.make().x(getLeft() + getWidth() / 2).y(getTop() + getHeight() - 1);
            centerE = Point.make().x(getLeft() + getWidth() - 1).y(getTop() + getHeight() / 2);

            setSynchronized(true);
        }

        if (p.distance(centerW) <= selectableAreaRadius) {
            return new Couple<>(Direction.W, centerW);
        } else if (p.distance(centerN) <= selectableAreaRadius) {
            return new Couple<>(Direction.N, centerN);
        } else if (p.distance(centerS) <= selectableAreaRadius) {
            return new Couple<>(Direction.S, centerS);
        } else if (p.distance(centerE) <= selectableAreaRadius) {
            return new Couple<>(Direction.E, centerE);
        }
        return null;
    }

    @Override
    public void drawHighlight() {
        widget.addStyleName(LinksClientBundle.INSTANCE.css().translucide());
        setSynchronized(true);
    }

    @Override
    public void draw() {
        widget.removeStyleName(LinksClientBundle.INSTANCE.css().translucide());
        setSynchronized(true);
    }

}
