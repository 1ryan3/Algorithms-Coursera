/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> pointSet;


    public PointSET() {
        pointSet = new SET<>();
    }                              // construct an empty set of points

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }                    // is the set empty?

    public int size() {
        return pointSet.size();
    }                        // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        pointSet.add(p);
    }             // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return pointSet.contains(p);
    }           // does the set contain point p?

    public void draw() {
        for (Point2D p : pointSet) {
            p.draw();
        }
    }                        // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        SET<Point2D> temp = new SET<>();
        double x = 0;
        double y = 0;
        for (Point2D p2 : pointSet) {
            x = p2.x();
            y = p2.y();
            if ((x <= rect.xmax() && x >= rect.xmin()) && (y <= rect.ymax() && y >= rect.ymin())) {
                temp.add(p2);
            }
        }
        return temp;
    }            // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D temp = null;
        double min = Double.POSITIVE_INFINITY;

        for (Point2D p2 : pointSet) {
            if (p.distanceSquaredTo(p2) < min) {
                min = p.distanceSquaredTo(p2);
                temp = p2;
            }
        }
        return temp;
    }            // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET testSet = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            testSet.insert(p);
        }

        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        testSet.draw();

    }                 // unit testing of the methods (optional)

}
