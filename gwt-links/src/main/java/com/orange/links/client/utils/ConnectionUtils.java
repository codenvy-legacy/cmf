package com.orange.links.client.utils;


import com.orange.links.client.shapes.Point;
import com.orange.links.client.shapes.Shape;

import java.util.List;

public class ConnectionUtils {

//    private static final double COSINE_15_ANGLE = 0.258819;
//    private static final double COSINE_75_ANGLE = 0.965626;

    public static Segment computeSegment(Shape shape1, Shape shape2) {
        return computeSegment(Rectangle.make().shape(shape1), Rectangle.make().shape(shape2));
    }

    public static Segment computeSegment(Rectangle sourceElement, Rectangle targetElement) {
        //TODO think about implementation of dedicated for mathematical operation
        Point center = Segment.middle(sourceElement.getCenter(), targetElement.getCenter());

        Point sourceElementPoint = findClosestPoint(center, sourceElement.getSideCenters());
        Point targetElementPoint = findClosestPoint(center, targetElement.getSideCenters());

// TODO skip use case when connection should be created between corners of elements
//        double сoefficient = Segment.getСosine(sourceElementPoint, targetElementPoint);
//
//        if (сoefficient <= COSINE_15_ANGLE || сoefficient >= COSINE_75_ANGLE) {
//            sourceElementPoint = findClosestPoint(center, sourceElement.getCornerPoints());
//            targetElementPoint = findClosestPoint(center, targetElement.getCornerPoints());
//        }

        return Segment.make().startPoint(sourceElementPoint).endPoint(targetElementPoint);
    }

    private static Point findClosestPoint(Point center, List<Point> points) {
        double minLineLength = 0;
        Point closestPoint = null;

        for (Point point : points) {
            double lineLength = Segment.length(center, point);

            if (closestPoint == null || minLineLength > lineLength) {
                closestPoint = point;
                minLineLength = lineLength;
            }
        }

        return closestPoint;
    }

    public static double distanceToSegment(Segment s, Point p) {
        return distanceToSegment(s.getStartPoint(), s.getEndPoint(), p);
    }

    public static double distanceToSegment(Point p1, Point p2, Point p3) {
        final double xDelta = p2.getLeft() - p1.getLeft();
        final double yDelta = p2.getTop() - p1.getTop();

        if ((xDelta == 0) && (yDelta == 0)) {
            throw new IllegalArgumentException("p1 and p2 cannot be the same point");
        }

        final double u =
                ((p3.getLeft() - p1.getLeft()) * xDelta + (p3.getTop() - p1.getTop()) * yDelta) / (xDelta * xDelta + yDelta * yDelta);

        final Point closestPoint;
        if (u < 0) {
            closestPoint = p1;
        } else if (u > 1) {
            closestPoint = p2;
        } else {
            closestPoint = Point.make()
                                .x(p1.getLeft() + (int)(u * xDelta))
                                .y(p1.getTop() + (int)(u * yDelta));
        }

        return closestPoint.distance(p3);
    }

    public static Point projectionOnSegment(Segment s, Point p) {
        return projectionOnSegment(s.getStartPoint(), s.getEndPoint(), p);
    }

    public static Point projectionOnSegment(Point p1, Point p2, Point p3) {
        final double xDelta = p2.getLeft() - p1.getLeft();
        final double yDelta = p2.getTop() - p1.getTop();

        if ((xDelta == 0) && (yDelta == 0)) {
            throw new IllegalArgumentException("p1 and p2 cannot be the same point");
        }

        final double u =
                ((p3.getLeft() - p1.getLeft()) * xDelta + (p3.getTop() - p1.getTop()) * yDelta) / (xDelta * xDelta + yDelta * yDelta);

        final Point closestPoint;
        if (u < 0) {
            closestPoint = p1;
        } else if (u > 1) {
            closestPoint = p2;
        } else {
            closestPoint = Point.make()
                                .x(p1.getLeft() + (int)(u * xDelta))
                                .y(p1.getTop() + (int)(u * yDelta));
        }

        return closestPoint;
    }

}