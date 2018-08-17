import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private int x;
    private int y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        assert x >= 0 && x <= 32767 && y >= 0 && y <= 32767;

        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x,y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        assert that.x >= 0 && that.x <= 32767 && that.y >= 0 && that.y <= 32767;

        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    @Override
    public int compareTo(Point that){
        assert that.x >= 0 && that.x <= 32767 && that.y >= 0 && that.y <= 32767;

        if (this.y < that.y) return -1;
        else if (this.y > that.y) return 1;
        else if (this.x < that.x) return -1;
        else if (this.x > that.x) return 1;
        else return 0;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        assert that.x >= 0 && that.x <= 32767 && that.y >= 0 && that.y <= 32767;

        if (this.y == that.y && this.x != that.x) return (this.y - this.y)/this.y;
        else if (this.x == that.x && this.y != that.y) return Double.POSITIVE_INFINITY;
        else if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        else return (double)(that.y - this.y)/(that.x - this.x);
    }

    private class BySlope implements Comparator<Point> {

        public int compare(Point P1, Point P2) {
            double slope1 = slopeTo(P1);
            double slope2 = slopeTo(P2);
            if (slope1 < slope2) return -1;
            else if (slope1 > slope2) return 1;
            else return 0;
        }
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new BySlope();
    }

    // Unit test
    public static void main(String[] args){
        Point P = new Point(3,4);
        // P1 is the same as P, should return 0 when compareTo, and -inf when slopeto
        Point P1 = new Point(3,4);
        // P2 and P3 has same x coordinate, should return -inf when slopeto
        Point P2 = new Point(4,5);
        Point P3 = new Point(4,8);
        // P3 and P4 has same y coordinate, should return +inf when slopeto, and -1 when compareto
        Point P4 = new Point(6,8);

        StdOut.println("-----------Test compareTo()-------------");
        System.out.println("Compare P with P1 should return 0, and returns: " + P.compareTo(P1));
        System.out.println("Compare P1 with P2 should return -1, and returns: " + P1.compareTo(P2));
        System.out.println("Compare P4 with P3 should return 1, and returns: " + P4.compareTo(P3));
        StdOut.println();
        StdOut.println("-----------Test slopeTo()----------------");
        StdOut.println("Slope of P and P should return -infinity, and returns: " + P.slopeTo(P));
        StdOut.println("Slope of P and P1 should return -inifinity, and returns: " + P.slopeTo(P1));
        StdOut.println("Slope of P3 and P4 should return 0, and returns: " + P3.slopeTo(P4));
        StdOut.println("Slope of P2 and P3 should return +infinity, and returns: " + P2.slopeTo(P3));
        StdOut.println("Slope of P2 and P4 should return 1.5, and returns: " + P2.slopeTo(P4));

        StdOut.println();
        StdOut.println("-----------Test Comparator by slope----------------");
        Point[] P_arr = {P1,P2,P3,P4};
        for (Point p:P_arr){
            StdOut.println("slope between point" + p.toString() + "and P is: " + p.slopeTo(P));
        }

        Arrays.sort(P_arr, P.slopeOrder());
        StdOut.println("After sorting by slope, now the order is: ");
        for (Point p: P_arr){
            StdOut.println(p.toString());
        }
    }
}