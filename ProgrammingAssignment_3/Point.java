/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER;

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
        this.SLOPE_ORDER = new BySlope();
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that == null)
            throw new java.lang.NullPointerException();

        // Degenerate segment
        if (this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY;

        // Horizontal line
        if (this.y == that.y)
            return (double) 0d;

        // Vertical line
        if (this.x == that.x)
            return Double.POSITIVE_INFINITY;

        return (double) (that.y - this.y) / (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that == null)
            throw new java.lang.NullPointerException();

        if (this.x == that.x && this.y == that.y)
            return 0;
        else if (this.y < that.y || (this.y == that.y && this.x < that.x))
            return -1;
        else
            return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p = new Point(4, 4);
        Point q = new Point(4, 6);
        Point r = new Point(4, 4);

        System.out.println(p.SLOPE_ORDER.compare(q, r));
        System.out.println(p.slopeTo(r));
        System.out.println(p.slopeTo(r));
    }

    private class BySlope implements Comparator<Point> {
        public int compare(Point v, Point w) {
            if (v == null || w == null)
                throw new java.lang.NullPointerException();

            double vd = Point.this.slopeTo(v);
            double wd = Point.this.slopeTo(w);

            if (vd == wd)       return 0;
            else if (vd < wd)   return -1;
            else                return 1;
        }
    }
}
