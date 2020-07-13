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
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node left;
        private Node right;
    }

    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }                            // construct an empty set of points

    public boolean isEmpty() {
        return root == null;
    }                      // is the set empty?

    public int size() {
        return size;
    }                   // number of points in the set

    public void insert(Point2D p) {
        if (isEmpty()) {
            root = new Node();
            root.p = p;
            root.left = null;
            root.right = null;
            root.rect = new RectHV(0, 0, 1, 1);
        }
        else root = nodeInsert(root, p, true, null);
        size++;
    }              // add the point to the set (if it is not already in the set)

    private Node nodeInsert(Node curr, Point2D p, boolean orientation, Node prev) {

        if (curr == null) { // end state
            curr = new Node();
            curr.p = p;
            curr.left = null;
            curr.right = null;

            if (!orientation) {
                if (prev.p.x() > p.x()) {
                    curr.rect = new RectHV(prev.rect.xmin(), prev.rect.ymin(), prev.p.x(),
                                           prev.rect.ymax());
                }
                else {
                    curr.rect = new RectHV(prev.p.x(), prev.rect.ymin(), prev.rect.xmax(),
                                           prev.rect.ymax());
                }
            }
            else {
                if (prev.p.y() > p.y()) {
                    curr.rect = new RectHV(prev.rect.xmin(), prev.rect.ymin(), prev.rect.xmax(),
                                           prev.p.y());
                }
                else {
                    curr.rect = new RectHV(prev.rect.xmin(), prev.p.y(), prev.rect.xmax(),
                                           prev.rect.ymax());
                }
            }

            return curr;
        }

        if (orientation) {


            if (curr.p.x() > p.x()) { // go left
                curr.left = nodeInsert(curr.left, p, false, curr);
            }
            else { // go right
                curr.right = nodeInsert(curr.right, p, false, curr);
            }

        }
        else {

            if (curr.p.y() > p.y()) { // go left
                curr.left = nodeInsert(curr.left, p, true, curr);
            }
            else { // go right
                curr.right = nodeInsert(curr.right, p, true, curr);
            }

        }
        return curr;
    }

    public boolean contains(Point2D p) {
        if (isEmpty()) return false;
        else return containsNode(root, p, true);
    }           // does the set contain point p?

    private boolean containsNode(Node curr, Point2D p, boolean orientation) {
        boolean x = false;
        if (curr != null) {
            if (curr.p.equals(p)) return true;
            else if (orientation) { // x-coordinate comparison (prev. was y or @ root)
                if (curr.p.x() > p.x()) { // go left
                    x = containsNode(curr.left, p, false);
                }
                else { // go right
                    x = containsNode(curr.right, p, false);
                }
            }
            else { // y-coordinate comparison (prev. was x)
                if (curr.p.y() > p.y()) { // go left
                    x = containsNode(curr.left, p, true);
                }
                else { // go right
                    x = containsNode(curr.right, p, true);
                }
            }
        }
        return x;
    }

    public void draw() {
        if (root == null) throw new IllegalArgumentException();
        else {
            drawNodes(root, true);
        }
    }                // draw all points to standard draw

    private void drawNodes(Node curr, boolean orientation) {
        if (curr == null) return;
        else {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            curr.p.draw();

            StdDraw.setPenRadius(0.003);
            if (orientation) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(curr.p.x(), curr.rect.ymax(), curr.p.x(), curr.rect.ymin());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(curr.rect.xmax(), curr.p.y(), curr.rect.xmin(), curr.p.y());
            }


            drawNodes(curr.left, !orientation);
            drawNodes(curr.right, !orientation);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> temp = new SET<>();
        return temp;
    }          // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        Point2D temp = null;
        return temp;
    }          // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree testSet = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            testSet.insert(p);
            testSet.contains(p);
            if (testSet.contains(p)) StdOut.println("Added!\n");
            else StdOut.printf("Failed to add point %f %f\n", x, y);
        }
        testSet.draw();
    }
}
