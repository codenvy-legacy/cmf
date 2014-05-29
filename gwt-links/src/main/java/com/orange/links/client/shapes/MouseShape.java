package com.orange.links.client.shapes;

public class MouseShape extends Point {

    private Point mousePoint;

    public static MouseShape make() {
        return new MouseShape();
    }

    @Override
    public int getLeft() {
        return mousePoint.getLeft();
    }

    @Override
    public int getTop() {
        return mousePoint.getTop();
    }

    public MouseShape mousePoint(Point mousePoint) {
        x(mousePoint.getLeft()).y(mousePoint.getTop());
        this.mousePoint = mousePoint;
        return this;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }

}
