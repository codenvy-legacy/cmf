package com.orange.links.client.utils;

import com.orange.links.client.shapes.Point;

public class MovablePoint extends Point {

    private Point point;

    public static MovablePoint make() {
        return new MovablePoint();
    }

    @Override
    public int getLeft() {
        return point == null ? super.left : point.getLeft();
    }

    @Override
    public MovablePoint x(int x) {
        this.left = x;
        return this;
    }

    @Override
    public int getTop() {
        return point == null ? super.top : point.getTop();
    }

    @Override
    public MovablePoint y(int y) {
        this.top = y;
        return this;
    }

    public void setTrackPoint(Point p) {
        this.point = p;
    }

    public void setFixed(boolean fixed) {
        left = getLeft();
        top = getTop();
        if (fixed && point != null) {
            point = null;
        }
    }

}
