/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points){
        checkNull(points);
        Arrays.sort(points);
        checkDuplicate(points);
        int pLength = points.length;
        List<LineSegment> segmentList = new ArrayList<>();
        for (int i = 0; i < pLength - 3; i++) {
            //double slop1 = points[i].slopeTo(points[i+1]);
            for (int j = i+1; j < pLength - 2; j++) {
                double slop1 = points[i].slopeTo(points[j]);
                for (int k = j+1; k < pLength - 1; k++) {
                    double slop2 = points[j].slopeTo(points[k]);
                    if (slop1 == slop2){
                        for (int l = k+1; l < pLength; l++) {
                            double slop3 = points[k].slopeTo(points[l]);
                            if (slop2 == slop3){
                                segmentList.add(new LineSegment(points[i],points[l]));
                            }
                        }
                    }
                }
            }
        }
        segments = segmentList.toArray(new LineSegment[0]);
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
    public int numberOfSegments(){
        return segments.length;
    }

    public LineSegment[] segments(){
        LineSegment[] copy = new LineSegment[segments.length];
        System.arraycopy(segments, 0, copy, 0, segments.length);
        return copy;
    }
}
