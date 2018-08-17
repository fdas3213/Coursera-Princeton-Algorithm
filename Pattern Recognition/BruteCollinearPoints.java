import edu.princeton.cs.algs4.StdOut;

import java.lang.*;
import java.util.Arrays;

public class BruteCollinearPoints {

    int nSeg = 0;
    private LineSegment[] Segs;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Argument to constructor is null");
        }
        Arrays.sort(points);
        if (points[0] == null){
            throw new IllegalArgumentException("first element is null");
        }
        for (int i = 1; i < points.length; i++){
            if (points[i] == null || points[i] == points[i-1]) {
                throw new IllegalArgumentException("some element is null or there is duplicate");
            }
        }

        for (int i = 0; i < points.length - 3; i ++){
            for (int j = i+ 1; j < points.length; j++){
                double slope_a = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length; k++){
                    double slope_b = points[i].slopeTo(points[k]);
                    StdOut.println("i: " + i + " j: " + j + " k: " + k);
                    if (slope_a != slope_b) continue;
                    for (int l = k + 1; l < points.length; l++){
                        double slope_c = points[i].slopeTo(points[l]);
                        StdOut.println("i: " + i + " j: " + j + " k: " + k + " l: "+ l);
                        if (slope_a == slope_c) {
                            Segs[nSeg++] = new LineSegment(points[i], points[l]);
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return nSeg;
    }

    // the line segments
    public LineSegment[] segments() {
        return Segs;
    }

    //unit test
    public static void main(String[] args){
        //test using sample data from input8.txt
        Point[] ps = {new Point(10000,0), new Point(0,10000), new Point(3000,7000),
                    new Point(7000, 3000), new Point(20000,21000), new Point(3000,4000),
                    new Point(14000,15000), new Point(6000,7000)};

        BruteCollinearPoints collinear = new BruteCollinearPoints(ps);
        StdOut.println(collinear.numberOfSegments());
    }
}
