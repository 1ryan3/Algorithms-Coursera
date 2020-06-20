import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        ArrayList<Point> sorted = new ArrayList<>();
        segments = new ArrayList<>();
        Collections.addAll(sorted, points);

        if (sorted.contains(null)) throw new IllegalArgumentException();
        sorted.sort(Comparator.naturalOrder());


        for (int i = 0; i < sorted.size(); i++) {
            for (int j = i + 1; j < sorted.size(); j++) {
                if (sorted.get(i).compareTo(sorted.get(j)) == 0)
                    throw new IllegalArgumentException();

                for (int k = j + 1; k < sorted.size(); k++) {
                    if (sorted.get(i).compareTo(sorted.get(k)) == 0)
                        throw new IllegalArgumentException();
                    if (sorted.get(j).compareTo(sorted.get(k)) == 0)
                        throw new IllegalArgumentException();

                    for (int m = k + 1; m < sorted.size(); m++) {
                        if (sorted.get(i).compareTo(sorted.get(m)) == 0)
                            throw new IllegalArgumentException();
                        if (sorted.get(j).compareTo(sorted.get(m)) == 0)
                            throw new IllegalArgumentException();
                        if (sorted.get(k).compareTo(sorted.get(m)) == 0)
                            throw new IllegalArgumentException();

                        if (sorted.get(i).slopeOrder().compare(sorted.get(j), sorted.get(k)) == 0
                                && sorted.get(i).slopeOrder().compare(sorted.get(k), sorted.get(m))
                                == 0) {
                            segments.add(new LineSegment(sorted.get(i), sorted.get(m)));
                        }
                    }
                }
            }
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
   /*     In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n + 1];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        points[0] = points[n - 1];

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

       */
    }
}
