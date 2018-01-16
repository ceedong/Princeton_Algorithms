import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
public class FastCollinearPoints {
    private final LineSegment[] result;
    public FastCollinearPoints(Point[] points) {
        // first: check to see whether the points contain null elements or not
        checkPoints(points);
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        //       for (Point a: sortedPoints) {
        //           StdOut.print(a + "::::");
        //       }
        checkDuplicates(sortedPoints);
        
        int length = sortedPoints.length;
        List<LineSegment> list = new LinkedList<LineSegment>();
        
        for (int i = 0; i < length; i++) {
            Point p = sortedPoints[i];
            Point[] slopeArray = sortedPoints.clone();
            
            
            Arrays.sort(slopeArray, p.slopeOrder());
            //  the index must begin with 1, because the index 0 will be itself, which the slope is negative_infinity
            int index = 1;
            while (index < length) {
                LinkedList<Point> listToJudge = new LinkedList<Point>();
                double slopeToJudge = p.slopeTo(slopeArray[index]);
                do {
                    listToJudge.add(slopeArray[index++]);
                } while (index < length && p.slopeTo(slopeArray[index]) == slopeToJudge);
                
                if (listToJudge.size() >= 3 && p.compareTo(listToJudge.peek()) < 0) {
                    Point min = p;
                    Point max = listToJudge.removeLast();
                    list.add(new LineSegment(min, max));
                }
            }
        }
        result = list.toArray(new LineSegment[0]);
    }
    private void checkPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("The array is a null object");
        }
        for (Point a: points) {
            if (a == null) {
                throw new java.lang.IllegalArgumentException("The array contains null object");
            }
        }
    }
    
    private void checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length -1; i++) {
            if (points[i].compareTo(points[i +1]) == 0) {
                throw new java.lang.IllegalArgumentException("Duplicate elements are found!");
            }
        }
    }
    
    // finds all line segments containing 4 or more points
    public int numberOfSegments() {
        return result.length;
    }       // the number of line segments
    public LineSegment[] segments() {
        return result.clone();
    }               // the line segments
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
