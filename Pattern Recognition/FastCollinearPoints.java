import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> Segs = new ArrayList<>();
    private double[] slopeArray;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // initialize slopArray with -infinity
        slopeArray = new double[points.length];
        for(int i = 0;i<points.length;i++) {
            slopeArray[i] = Double.NEGATIVE_INFINITY;
        }

        checkNull(points);
        Point[] copy = new Point[points.length];
        for (int i =0;i<points.length;i++) {
            copy[i] = points[i];
        }
        checkDuplicate(copy);

        for (int i = 0;i < copy.length -1; i++) {
            Point[] Pts = new Point[copy.length-i-1];
            double[] slopes = new double[copy.length-i-1];

            int idx = 0;
            for (int j = i + 1; j < copy.length; j++) {
                slopes[idx] = copy[i].slopeTo(copy[j]);
                Pts[idx] = copy[j];
                idx++;
            }

            // sort slopes by natural order
            Arrays.sort(slopes);
            // sort newcopy by the slopes they make with ith point
            Arrays.sort(Pts, copy[i].slopeOrder());
            addSegment(copy[i], slopes, Pts);
        }
    }

    private void addSegment(Point p, double[] slopes, Point[] pts) {
        int nEqual = 1;
        double sameSlope = slopes[0];
        for (int i = 1; i<slopes.length;i++) {
            if (sameSlope != slopes[i]) {
                sameSlope = slopes[i];
            }else {
                nEqual++;
            }
        }

        if (nEqual >= 3 && !checkDupSlope(slopeArray, sameSlope)) {
            slopeArray[numberOfSegments()] = sameSlope;
            if (nEqual == slopes.length) Segs.add(new LineSegment(p, pts[nEqual-1]));
            else Segs.add(new LineSegment(p, pts[nEqual]));
        }
    }

    private Boolean checkDupSlope(double[] slopes, double slope) {
        for (int i = 0;i < slopes.length;i++) {
            if (slopes[i] == slope) return true;
        }
        return false;
    }

    // the number of line segments
    public int numberOfSegments() {
        return Segs.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return Segs.toArray(new LineSegment[numberOfSegments()]);
    }

    private void checkNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }

        for(Point p:points) {
            if (p == null) {
                throw new IllegalArgumentException("Some element is null");
            }
        }
    }

    private void checkDuplicate(Point[] points) {
        Arrays.sort(points);
        for (int i = 1; i <points.length; i++) {
            if (points[i].slopeTo(points[i-1]) == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException("Duplicate points found");
            }
        }
    }

    //unit test
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
