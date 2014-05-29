package com.orange.links.client.utils;

import com.orange.links.client.shapes.Point;

public class Segment {

    private Point startPoint;
    private Point endPoint;

    public static Segment make() {
        return new Segment();
    }

    private Segment() {
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Segment startPoint(Point point1) {
        this.startPoint = point1;
        return this;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public Segment endPoint(Point point2) {
        this.endPoint = point2;
        return this;
    }

    @Override
    public String toString() {
        return startPoint + " , " + endPoint;
    }

    public static double length(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point2.getLeft() - point1.getLeft(), 2) + Math.pow(point2.getTop() - point1.getTop(), 2));
    }

    public double length() {
        return length(startPoint, endPoint);
    }

    public void translate(int left, int top) {
        startPoint.translate(left, top);
        endPoint.translate(left, top);
    }

    public static Point middle(Point point1, Point point2) {
        return Point.make()
                    .x((point2.getLeft() + point1.getLeft()) / 2)
                    .y((point2.getTop() + point1.getTop()) / 2);
    }

    public Point middle() {
        return middle(startPoint, endPoint);
    }

    public double getAngleWithTop() {
        double linkAngle = Math.acos(
                (endPoint.getLeft() - startPoint.getLeft()) /
                Math.sqrt(Math.pow(endPoint.getLeft() - startPoint.getLeft(), 2) + Math.pow(endPoint.getTop() - startPoint.getTop(), 2))
                                    );

        if (endPoint.getTop() < startPoint.getTop()) {
            linkAngle = linkAngle * -1;
        }

        return linkAngle;
    }

    public static double getСosine(Point point1, Point point2) {
        double hypotenuseLength = length(point1, point2);
        double adjacentLength = length(
                Point.make()
                     .x(point1.getLeft())
                     .y(point2.getTop()),
                point1
                                      );

        return adjacentLength / hypotenuseLength;
    }

    public double getСosine() {
        return getСosine(startPoint, endPoint);
    }

}