/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class KdTree {
    public KdTree() {

    }                            // construct an empty set of points

    public boolean isEmpty() {
        return true;
    }                      // is the set empty?

    public int size() {
        return 0;
    }                   // number of points in the set

    public void insert(Point2D p) {

    }              // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        return false;
    }             // does the set contain point p?

    public void draw() {

    }                // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> temp = new SET<>();
        return temp;
    }          // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        Point2D temp = null;
        return temp;
    }          // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }
}
