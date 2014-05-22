package com.orange.links.client.utils;

import com.orange.links.client.exception.DiagramViewNotDisplayedException;
import com.orange.links.client.shapes.Point;
import com.orange.links.client.shapes.Shape;

import java.util.ArrayList;
import java.util.List;

public class SegmentPath {

    private Shape       startShape;
    private Shape       endShape;
    private List<Point> pointList;

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
            Segment startSegment
                    = ConnectionUtils.computeSegment(this.startShape, pointList.get(1));
            pointList.set(0, startSegment.getP1());
            Segment endSegment
                    = ConnectionUtils.computeSegment(pointList.get(pointList.size() - 2), this.endShape);
            pointList.set(pointList.size() - 1, endSegment.getP2());
        } else {
            // There is only one segment
            Segment s = ConnectionUtils.computeSegment(this.startShape, this.endShape);
            if (s == null)
                throw new DiagramViewNotDisplayedException();
            pointList.set(0, s.getP1());
            pointList.set(1, s.getP2());
        }
    }

    public Segment getMiddleSegment() {
        int size = pointList.size();
        Point p1 = pointList.get(Math.round(size / 2 - 1));
        Point p2 = pointList.get(Math.round(size / 2));
        Segment s = new Segment(p1, p2);
        return s;
    }

    public Point getFirstPoint() {
        return pointList.get(0);
    }

    public Point getLastPoint() {
        return pointList.get(pointList.size() - 1);
    }

    public Segment asStraightPath() {
        return ConnectionUtils.computeSegment(this.startShape, this.endShape);
    }

    public List<Point> getPath() {
        return pointList;
    }

    public List<Point> getPathWithoutExtremities() {
        if (pointList.size() > 2)
            return pointList.subList(1, pointList.size() - 1);
        return new ArrayList<Point>();
    }

    public void straightPath() {
        Segment s = ConnectionUtils.computeSegment(this.startShape, this.endShape);
        pointList = new ArrayList<Point>();
        if (s == null)
            throw new DiagramViewNotDisplayedException();
        pointList.add(s.getP1());
        pointList.add(s.getP2());
    }
}
