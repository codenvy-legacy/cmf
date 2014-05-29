package com.orange.links.client.utils;

import com.orange.links.client.shapes.Point;
import com.orange.links.client.shapes.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rectangle {

    private Point cornerTopLeft;
    private Point cornerTopRight;
    private Point cornerBottomLeft;
    private Point cornerBottomRight;

    public static Rectangle make() {
        return new Rectangle();
    }

    private Rectangle() {
    }

    public Rectangle shape(Shape shape) {
        this.cornerTopLeft = Point.make().x(shape.getLeft()).y(shape.getTop());
        this.cornerTopRight = Point.make().x(shape.getLeft() + shape.getWidth()).y(shape.getTop());
        this.cornerBottomLeft = Point.make().x(shape.getLeft()).y(shape.getTop() + shape.getHeight());
        this.cornerBottomRight = Point.make().x(shape.getLeft() + shape.getWidth()).y(shape.getTop() + shape.getHeight());

        return this;
    }

    public Point getCornerTopLeft() {
        return cornerTopLeft;
    }

    public Rectangle cornerTopLeft(Point point) {
        this.cornerTopLeft = point;
        return this;
    }

    public Point getCornerTopRight() {
        return cornerTopRight;
    }

    public Rectangle cornerTopRight(Point point) {
        this.cornerTopRight = point;
        return this;
    }

    public Point getCornerBottomLeft() {
        return cornerBottomLeft;
    }

    public Rectangle cornerBottomLeft(Point point) {
        this.cornerBottomLeft = point;
        return this;
    }

    public Point getCornerBottomRight() {
        return cornerBottomRight;
    }

    public Rectangle cornerBottomRight(Point point) {
        this.cornerBottomRight = point;
        return this;
    }

    public Point getCenter() {
        return Segment.middle(cornerTopLeft, cornerBottomRight);
    }

    public List<Point> getSideCenters() {
        List<Point> points = new ArrayList<>();

        points.add(Segment.middle(cornerTopLeft, cornerTopRight));
        points.add(Segment.middle(cornerTopRight, cornerBottomRight));
        points.add(Segment.middle(cornerBottomRight, cornerBottomLeft));
        points.add(Segment.middle(cornerBottomLeft, cornerTopLeft));

        return points;
    }

    public List<Point> getCornerPoints() {
        return Arrays.asList(cornerTopLeft, cornerTopRight, cornerBottomRight, cornerBottomLeft);
    }

    public Segment getBorderLeft() {
        return Segment.make().startPoint(cornerTopLeft).endPoint(cornerBottomLeft);
    }

    public Segment getBorderRight() {
        return Segment.make().startPoint(cornerTopRight).endPoint(cornerBottomRight);
    }

    public Segment getBorderTop() {
        return Segment.make().startPoint(cornerTopLeft).endPoint(cornerTopRight);
    }

    public Segment getBorderBottom() {
        return Segment.make().startPoint(cornerBottomLeft).endPoint(cornerBottomRight);
    }

    public void translate(int left, int top) {
        cornerTopLeft.translate(left, top);
        cornerTopRight.translate(left, top);
        cornerBottomLeft.translate(left, top);
        cornerBottomRight.translate(left, top);
    }

    public boolean isInside(Point p) {
        return p.getLeft() >= getLeft() &&
               p.getTop() >= getTop() &&
               p.getLeft() <= getLeft() + getWidth() &&
               p.getTop() <= getTop() + getHeight();
    }

    public int getLeft() {
        return cornerTopLeft.getLeft();
    }

    public int getTop() {
        return cornerTopLeft.getTop();
    }

    public int getWidth() {
        return cornerTopRight.getLeft() - cornerTopLeft.getLeft();
    }

    public int getHeight() {
        return cornerBottomLeft.getTop() - cornerTopLeft.getTop();
    }

    @Override
    public String toString() {
        return "[ top : " + getTop() + " | left : " + getLeft() + " | width : " + getWidth() + " | height : " + getHeight() + "]";
    }
}
