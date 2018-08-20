import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;


public class FastCollinearPoints {

    private ArrayList<LineSegment> Segs;
    private Point[] copy;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        Segs = new ArrayList<LineSegment>();

        checkNull(points);
        copy = new Point[points.length];
        for (int i =0;i<points.length;i++){
            copy[i] = points[i];
        }
        checkDuplicate(points);

        for (int i = 0;i < copy.length; i++){
            Point[] newcopy = new Point[copy.length-i-1];
            double[] slopes = new double[copy.length-i-1];

            int idx = 0;
            for (int j = i + 1; j < copy.length; j++) {
                slopes[idx] = copy[i].slopeTo(copy[j]);
                newcopy[idx] = copy[j];
                idx++;
            }
            Arrays.sort(slopes);
            Arrays.sort(newcopy, copy[i].slopeOrder());

            int nEqual = 0;
            for (int k = 0; k < slopes.length-1 && slopes[k]==slopes[k+1]; k++) {
                nEqual++;
            }
            if (nEqual >= 3) {
                i += nEqual;
                Segs.add(new LineSegment(copy[i], copy[nEqual]));
            }
        }
    }

    // the number of line segments
    public int numberOfSegments(){
        return Segs.size();
    }

    // the line segments
    public LineSegment[] segments(){
        return Segs.toArray(new LineSegment[numberOfSegments()]);
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

    //unit test
    public static void main(String[] args){
        //test using sample data from input8.txt
        Point[] ps = {new Point(10000,0), new Point(0,10000), new Point(3000,7000),
                new Point(7000, 3000), new Point(20000,21000), new Point(3000,4000),
                new Point(14000,15000), new Point(6000,7000)};

        Point[] p_arr2 = {new Point(19000,10000), new Point(18000,10000), new Point(32000,10000),
                new Point(21000, 10000), new Point(1234,5678), new Point(14000,10000)};


        // Testing segments() and numberOfSegments using ps now
        StdOut.println("Test using 2nd Point array");
        BruteCollinearPoints collinear = new BruteCollinearPoints(p_arr2);
        StdOut.println(collinear.numberOfSegments());

        for (LineSegment ls: collinear.segments()){
            StdOut.println(ls.toString());
        }
    }
}
