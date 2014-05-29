package com.orange.links.segment.tests;

import junit.framework.TestCase;

import com.orange.links.client.shapes.Point;
import com.orange.links.client.utils.Segment;

public class SegmentTest extends TestCase {

    public void testAngle() {
        Point p0 = Point.make().x(0).y(0);
        Point p1 = Point.make().x(5).y(0);
        Point p2 = Point.make().x(0).y(5);

        assertEquals(0.0, Segment.make().startPoint(p0).endPoint(p1).getAngleWithTop());
        assertEquals(Math.PI / 2, Segment.make().startPoint(p0).endPoint(p2).getAngleWithTop());
    }

}
