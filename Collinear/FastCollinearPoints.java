import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        segments = new ArrayList<>();
        if (points == null) throw new IllegalArgumentException();

        //     Point[] temp = new Point[points.length];
        ArrayList<Point> slopeSorted = new ArrayList<>();

        Collections.addAll(slopeSorted, points);

        if (slopeSorted.contains(null)) throw new IllegalArgumentException();


        for (int x = 0; x < points.length; x++) {
            slopeSorted.sort(Comparator.naturalOrder());
            slopeSorted.sort(points[x].slopeOrder());
       /*     StdOut.println("\nRun #: " + (x + 1) + " slopeSorted by point: " + slopeSorted.get(0));
            for (int z = 0; z < slopeSorted.size(); z++) {
                StdOut.println(
                        "Point " + z + " value: " + slopeSorted.get(z) + " has slope " + slopeSorted
                                .get(0).slopeTo(
                                        slopeSorted.get(z)));
            } */
            int t = 0;
            int s = 0;
            Point start = slopeSorted.get(0);
            Point prev = start;
            for (Point n : slopeSorted) {
                if (t != 0 && start.compareTo(n) == 0) {
                    throw new IllegalArgumentException();
                }
                t = 1;
                if (start.slopeOrder().compare(prev, n) == 0) {
                    if (start.compareTo(prev) > 0) continue;
                    prev = n;
                    s++;
                    if (s >= 3 && slopeSorted.get(slopeSorted.size() - 1).compareTo(n) == 0) {
                        segments.add(new LineSegment(start, prev));
                    }
                }
                else {
                    if (s >= 3) {
                        if (start.compareTo(prev) > 0) continue;
                        segments.add(new LineSegment(start, prev));
                    }
                    s = 1;
                    prev = n;
                }
            }
           /* temp[0] = slopeSorted.get(1);
            if (start.compareTo(slopeSorted.get(1)) == 0) {
                throw new IllegalArgumentException();
            }
            for (int y = 2; y < slopeSorted.size(); y++) {
                if (start.compareTo(slopeSorted.get(y)) == 0) {
                    throw new IllegalArgumentException();
                }
                if (start.slopeOrder().compare(temp[t], slopeSorted.get(y)) == 0) {
                    if (start.compareTo(temp[t]) > 0) {
                        continue;
                    }
                    temp[++t] = slopeSorted.get(y);
                    if ((y == slopeSorted.size() - 1) && t >= 2) {
                        segments.add(new LineSegment(start, temp[t]));
                        // StdOut.println(
                        //         "End of array add " + start.toString() + " " + temp[t].toString());
                    }
                }
                else {
                    // StdOut.println("t is.." + t);
                    if (t >= 2) {
                        if (start.compareTo(temp[t]) > 0) {
                            continue;
                        }
                        segments.add(new LineSegment(start, temp[t]));
                        //  StdOut.println(
                        //          "End of cluster add " + start.toString() + " " + temp[t]
                        //                  .toString());
                    }
                    t = 0;
                    temp[t] = slopeSorted.get(y);
                }
            } */
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println("\n\n Results: ");
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
