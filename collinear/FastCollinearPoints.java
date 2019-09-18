/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;


public class FastCollinearPoints {
    private final LineSegment[] lineSegment;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        Arrays.sort(points);
        checkDuplicate(points);

        Point[] sortedBySlop = points.clone();
        int pLength = points.length;

        LinkedList<LineSegment> longestLine = new LinkedList<>();
        for (int i = 0; i < pLength; i++) {

            Point originP = points[i];
            Arrays.sort(sortedBySlop, originP.slopeOrder());

            int j = i + 1;
            while (j < pLength){
                LinkedList<Point> pointsOnSegment = new LinkedList<>();
                pointsOnSegment.add(sortedBySlop[j]);
                while (sortedBySlop[j] == sortedBySlop[j+1] && j < pLength){
                    pointsOnSegment.add(sortedBySlop[j++]);
                }
                if (pointsOnSegment.size() >= 2){
                    longestLine.add(new LineSegment(pointsOnSegment.getFirst(), pointsOnSegment.getLast()));
                }
            }
        }
        lineSegment = longestLine.toArray(new LineSegment[0]);
    }

    private void checkNull(Point[] points){
        if (points == null) throw new IllegalArgumentException("Array points is null");
        for (int i=0; i<points.length;i++) {
            if (points[i] == null) throw new IllegalArgumentException(i+"th element in points is null");
        }
    }

    private void checkDuplicate(Point[] points){
        for (int i = 0; i < points.length-1; i++) {
            if (points[i].compareTo(points[i+1]) == 0) throw new IllegalArgumentException("Found dublicate");
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegment.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegment;
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
