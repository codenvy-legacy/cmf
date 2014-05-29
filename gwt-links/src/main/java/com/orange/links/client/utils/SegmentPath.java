package com.orange.links.client.utils;

import com.orange.links.client.exception.DiagramViewNotDisplayedException;
import com.orange.links.client.shapes.Point;
import com.orange.links.client.shapes.Shape;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SegmentPath {

    private final Shape       startShape;
    private final Shape       endShape;
    private       List<Point> pointList;

    public SegmentPath(Shape startShape, Shape endShape) throws DiagramViewNotDisplayedException {
        this.startShape = startShape;
        this.endShape = endShape;

        straightPath();
    }

    public void add(Point insertPoint, Point startPoint, Point endPoint) {
        int insertPosition;
        for (int i = 0; i < pointList.size(); i++) {
            if (endPoint.equals(pointList.get(i))) {
                insertPosition = i;
                pointList.add(insertPosition, insertPoint);
                break;
            }
        }
    }

    public void update() throws DiagramViewNotDisplayedException {
        if (pointList.size() > 2) {
            Segment startSegment = ConnectionUtils.computeSegment(startShape, pointList.get(1));
            pointList.set(0, startSegment.getStartPoint());

            Segment endSegment = ConnectionUtils.computeSegment(pointList.get(pointList.size() - 2), endShape);
            pointList.set(pointList.size() - 1, endSegment.getEndPoint());
        } else {
            // There is only one segment
            Segment s = ConnectionUtils.computeSegment(startShape, endShape);
            if (s == null) {
                throw new DiagramViewNotDisplayedException();
            }

            pointList.set(0, s.getStartPoint());
            pointList.set(1, s.getEndPoint());
        }
    }

    public Segment getMiddleSegment() {
        int size = pointList.size();
        Point point1 = pointList.get(Math.round(size / 2 - 1));
        Point point2 = pointList.get(Math.round(size / 2));

        return Segment.make().startPoint(point1).endPoint(point2);
    }

    public Point getFirstPoint() {
        return pointList.get(0);
    }

    public Point getLastPoint() {
        return pointList.get(pointList.size() - 1);
    }

    public Segment asStraightPath() {
        return ConnectionUtils.computeSegment(startShape, endShape);
    }

    public List<Point> getPath() {
        return pointList;
    }

    public List<Point> getPathWithoutExtremities() {
        if (pointList.size() > 2) {
            return pointList.subList(1, pointList.size() - 1);
        }

        return Collections.emptyList();
    }

    public void straightPath() {
        Segment s = ConnectionUtils.computeSegment(startShape, endShape);

        if (s == null) {
            throw new DiagramViewNotDisplayedException();
        }

        pointList = Arrays.asList(s.getStartPoint(), s.getEndPoint());
    }
}
