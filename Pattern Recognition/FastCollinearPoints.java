import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> Segs = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        Point[] copy = new Point[points.length];
        for (int i =0;i<points.length;i++) {
            copy[i] = points[i];
        }
        checkDuplicate(copy);

        for (int i = 0;i < copy.length -1; i++) {
            Point[] ptsA = new Point[copy.length-i-1];
            double[] slopesB = new double[i];

            for (int k = 0; k < i; k++){
                slopesB[k] = copy[i].slopeTo(copy[k]);
            }

            for (int j = 0; j < copy.length-i-1; j++){
                ptsA[j] = copy[j+i+1];
            }

            // sort slopes by natural order
            Arrays.sort(slopesB);
            // sort newcopy by the slopes they make with ith point
            Arrays.sort(ptsA, copy[i].slopeOrder());
            addSegment(copy[i], slopesB, ptsA);
        }
    }


    private void addSegment(Point p, double[] slopesB, Point[] pointsA){
        int count = 1;
        double lastSlope = p.slopeTo(pointsA[0]);
        for (int i = 1; i < pointsA.length;i++){
            double slope = p.slopeTo(pointsA[i]);
            if (slope != lastSlope){
                if (count >= 3 && !BinarySearch(slope, slopesB)) {
                    Segs.add(new LineSegment(p, pointsA[i-1]));
                }
                count = 1;
            }else{
                count ++;
            }
            lastSlope = slope;
        }

        if(count >= 3 && !BinarySearch(lastSlope, slopesB)){
            Segs.add(new LineSegment(p, pointsA[pointsA.length-1]));
        }
    }

    private Boolean BinarySearch(double s, double[] slopes){
        int low = 0;
        int high = slopes.length -1;

        while(low <= high){
            int mid = low + (high - low)/2;
            if (s > slopes[mid]) low = mid + 1;
            else if (s < slopes[mid]) high = mid-1;
            else return true;
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
