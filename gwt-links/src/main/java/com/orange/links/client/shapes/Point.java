package com.orange.links.client.shapes;

import com.orange.links.client.connection.Connection;
import com.orange.links.client.utils.Direction;
import com.orange.links.client.utils.Rectangle;

public class Point implements Shape {

    protected int       left;
    protected int       top;
    private   Direction direction;

    public static Point make() {
        return new Point();
    }

    protected Point() {
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public Point x(int x) {
        this.left = x;
        return this;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public Point y(int y) {
        this.top = y;
        return this;
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public int getTop() {
        return top;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }

    public double distance(Point other) {
        return Math.sqrt((getLeft() - other.getLeft()) * (getLeft() - other.getLeft()) +
                         (getTop() - other.getTop()) * (getTop() - other.getTop()));
    }

    public Point move(Direction dir, int distance) {
        if (dir == Direction.S) {
            return make().x(left).y(top + distance);
        } else if (dir == Direction.SE) {
            return make().x(left + (int)Math.cos(distance)).y(top - (int)Math.sin(distance));
        } else if (dir == Direction.SW) {
            return make().x(left - (int)Math.cos(distance)).y(top - (int)Math.sin(distance));
        } else if (dir == Direction.N) {
            return make().x(left).y(top - distance);
        } else if (dir == Direction.NE) {
            return make().x(left + (int)Math.cos(distance)).y(top + (int)Math.sin(distance));
        } else if (dir == Direction.NW) {
            return make().x(left - (int)Math.cos(distance)).y(top + (int)Math.sin(distance));
        } else if (dir == Direction.W) {
            return make().x(left - distance).y(top);
        } else if (dir == Direction.E) {
            return make().x(left + distance).y(top);
        } else {
            throw new IllegalStateException();
        }
    }

    public Point move(Point vector) {
        return make().x(left + vector.left).y(top + vector.top);
    }

    public Point negative() {
        return make().x(-left).y(-top);
    }

    public static boolean equals(Point p1, Point p2) {
        return p1.left == p2.left && p1.top == p2.top;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void translate(int left, int top) {
        this.left += left;
        this.top += top;
    }

    public boolean equals(Point p) {
        return p.getLeft() == left && p.getTop() == top;
    }

    public boolean isInside(Shape s) {
        Rectangle r = Rectangle.make().shape(s);
        return getLeft() >= r.getLeft() && getTop() >= r.getTop() && getLeft() <= r.getLeft() + r.getWidth()
               && getTop() <= r.getTop() + r.getHeight();
    }

    @Override
    public String toString() {
        return "[ " + left + " ; " + top + " ]";
    }

    @Override
    public boolean addConnection(Connection connection) {
        return false;
    }

    @Override
    public boolean removeConnection(Connection connection) {
        return false;
    }

    @Override
    public void setSynchronized(boolean sync) {
    }

    @Override
    public boolean isSynchronized() {
        return true;
    }

    @Override
    public void setAllowSynchronized(boolean allowSynchronized) {
    }

    @Override
    public boolean allowSynchronized() {
        return true;
    }

    @Override
    public void draw() {
    }

    @Override
    public void drawHighlight() {
    }

}
