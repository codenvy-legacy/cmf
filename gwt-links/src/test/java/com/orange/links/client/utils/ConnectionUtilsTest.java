/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.orange.links.client.utils;

import com.orange.links.client.shapes.Point;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

/**
 * @author Andrey Plotnikov
 */
@RunWith(Parameterized.class)
public class ConnectionUtilsTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                // use case when element 2 in North West part
                new Object[]{
                        Rectangle.make()
                                 .cornerTopLeft(Point.make().x(50).y(50))
                                 .cornerTopRight(Point.make().x(100).y(50))
                                 .cornerBottomLeft(Point.make().x(50).y(100))
                                 .cornerBottomRight(Point.make().x(100).y(100)),
                        Point.make().x(125).y(100),
                        Point.make().x(100).y(75)
                },
                // use case when element 2 in North East part
                new Object[]{
                        Rectangle.make()
                                 .cornerTopLeft(Point.make().x(150).y(50))
                                 .cornerTopRight(Point.make().x(150).y(100))
                                 .cornerBottomLeft(Point.make().x(200).y(50))
                                 .cornerBottomRight(Point.make().x(200).y(100)),
                        Point.make().x(125).y(100),
                        Point.make().x(150).y(75)
                },
                // use case when element 2 in South East part
                new Object[]{
                        Rectangle.make()
                                 .cornerTopLeft(Point.make().x(150).y(150))
                                 .cornerTopRight(Point.make().x(200).y(150))
                                 .cornerBottomLeft(Point.make().x(150).y(200))
                                 .cornerBottomRight(Point.make().x(200).y(200)),
                        Point.make().x(150).y(125),
                        Point.make().x(175).y(150)
                },
                // TODO skip use case when connection should be created between corners of elements
                // use case when element 2 in South West part
//                new Object[]{
//                        Rectangle.make()
//                                 .cornerTopLeft(Point.make().x(50).y(150))
//                                 .cornerTopRight(Point.make().x(100).y(150))
//                                 .cornerBottomLeft(Point.make().x(50).y(200))
//                                 .cornerBottomRight(Point.make().x(100).y(200)),
//                        Point.make().x(100).y(150),
//                        Point.make().x(100).y(150)
//                },
                // use case when element 2 in North part
                new Object[]{
                        Rectangle.make()
                                 .cornerTopLeft(Point.make().x(100).y(50))
                                 .cornerTopRight(Point.make().x(150).y(50))
                                 .cornerBottomLeft(Point.make().x(100).y(100))
                                 .cornerBottomRight(Point.make().x(150).y(100)),
                        Point.make().x(125).y(100),
                        Point.make().x(125).y(100)
                },
                // use case when element 2 in West part
                new Object[]{
                        Rectangle.make()
                                 .cornerTopLeft(Point.make().x(50).y(100))
                                 .cornerTopRight(Point.make().x(100).y(100))
                                 .cornerBottomLeft(Point.make().x(50).y(150))
                                 .cornerBottomRight(Point.make().x(100).y(150)),
                        Point.make().x(100).y(125),
                        Point.make().x(100).y(125)
                },
                // use case when element 2 in East part
                new Object[]{
                        Rectangle.make()
                                 .cornerTopLeft(Point.make().x(150).y(100))
                                 .cornerTopRight(Point.make().x(200).y(100))
                                 .cornerBottomLeft(Point.make().x(150).y(150))
                                 .cornerBottomRight(Point.make().x(200).y(150)),
                        Point.make().x(150).y(125),
                        Point.make().x(150).y(125)
                },
                // use case when element 2 in South part
                new Object[]{
                        Rectangle.make()
                                 .cornerTopLeft(Point.make().x(100).y(150))
                                 .cornerTopRight(Point.make().x(150).y(150))
                                 .cornerBottomLeft(Point.make().x(100).y(200))
                                 .cornerBottomRight(Point.make().x(150).y(200)),
                        Point.make().x(125).y(150),
                        Point.make().x(125).y(150)
                }
                            );
    }

    private Rectangle element1;
    @Parameter(0)
    public  Rectangle element2;
    @Parameter(1)
    public  Point     startPoint;
    @Parameter(2)
    public  Point     endPoint;

    @Before
    public void setUp() throws Exception {
        element1 = Rectangle.make()
                            .cornerTopLeft(Point.make().x(100).y(100))
                            .cornerTopRight(Point.make().x(150).y(100))
                            .cornerBottomLeft(Point.make().x(100).y(150))
                            .cornerBottomRight(Point.make().x(150).y(150));
    }

    @Test
    public void segmentShouldBeCreated() throws Exception {
        Segment segment = ConnectionUtils.computeSegment(element1, element2);

        assertEquals(startPoint.getLeft(), segment.getStartPoint().getLeft());
        assertEquals(startPoint.getTop(), segment.getStartPoint().getTop());

        assertEquals(endPoint.getLeft(), segment.getEndPoint().getLeft());
        assertEquals(endPoint.getTop(), segment.getEndPoint().getTop());
    }

}