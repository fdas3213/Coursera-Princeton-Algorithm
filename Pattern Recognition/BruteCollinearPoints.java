import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Merge;
import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> Segs;
    private Point[] copy;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        copy = new Point[points.length];
        for (int i =0;i<points.length;i++){
            copy[i] = points[i];
        }
        checkDuplicate(copy);
        Segs = new ArrayList<LineSegment>();

        for (int i = 0; i < copy.length - 3; i ++){
            for (int j = i+ 1; j < copy.length; j++){
                for (int k = j + 1; k < copy.length; k++){
                    if (copy[i].slopeTo(copy[j]) == copy[i].slopeTo(copy[k])) {
                        for (int l = k + 1; l < copy.length; l++) {
                            if (copy[i].slopeTo(copy[j]) == copy[i].slopeTo(copy[l])) {
                                Segs.add(new LineSegment(copy[i], copy[l]));
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkNull(Point[] points){
        if (points == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }

        for(Point p:points){
            if (p == null){
                throw new IllegalArgumentException("Some element is null");
            }
        }
    }

    private void checkDuplicate(Point[] points){
        Merge.sort(points);
        for (int i = 1; i <points.length; i++){
            if (points[i].slopeTo(points[i-1]) == Double.NEGATIVE_INFINITY){
                throw new IllegalArgumentException("Duplicate points found");
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return Segs.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return Segs.toArray(new LineSegment[numberOfSegments()]);
    }

    //unit test
    public static void main(String[] args){
        //test using sample data from input8.txt
        Point[] ps = {new Point(10000,0), new Point(0,10000), new Point(3000,7000),
                    new Point(7000, 3000), new Point(20000,21000), new Point(3000,4000),
                    new Point(14000,15000), new Point(6000,7000)};

        // Testing segments() and numberOfSegments using ps now
        StdOut.println("Test using first Point array");
        BruteCollinearPoints collinear = new BruteCollinearPoints(ps);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment ls: collinear.segments()){
            StdOut.println(ls.toString());
        }
    }
}
