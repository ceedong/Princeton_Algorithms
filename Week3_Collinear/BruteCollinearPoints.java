import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.LinkedList;
import java.util.List;
public class BruteCollinearPoints {
    private int segNum = 0;
    private List<LineSegment> listResult = new LinkedList<LineSegment>();
    public BruteCollinearPoints(Point[] points) {
        int length = points.length;
        for (int p = 0; p < length; p++) {
            for (int q = p + 1; q < length; q++) {
                for (int r = q + 1; r < length; r++) {
                    for (int s = r + 1; s < length; s++) {
                        if ((points[p].slopeTo(points[q]) == points[p].slopeTo(points[r])) && (points[p].slopeTo(points[r]) == points[p].slopeTo(points[s]))){
                            StdOut.println("1." + points[p]);
                            StdOut.println("2." + points[q] + points[p].slopeTo(points[q]));
                            StdOut.println("3." + points[r] + points[p].slopeTo(points[r]));
                            StdOut.println("4." + points[s] + points[p].slopeTo(points[s]));
                            segNum++;
                            Point min = points[p];
                            Point max = points[p];
                            Point[] array = new Point[4];
                            array[0] = points[p];
                            array[1] = points[q];
                            array[2] = points[r];
                            array[3] = points[s];
                            for (int i = 0 ; i < 4; i++) {
                                if (array[i].compareTo(min) == -1) {
                                    min = array[i];
                                }
                                if (array[i].compareTo(max) == 1) {
                                    max = array[i];
                                }
                            }
                            StdOut.println("The min segment is" + min);
                            StdOut.println("The max segment is" + max);
                            // whether the given linesegment exists or not
                            int flag = 0;
                            for (LineSegment a : listResult) {
                                StdOut.println("--------"+a);
                                if (a.toString().equals(new LineSegment(min, max).toString()) || a.toString().equals(new LineSegment(max, min).toString())) {
                                    flag = 1;
                                    StdOut.println("%%%%%%%%"+a);
                                }
                            }
                            if (flag == 0) {
                                listResult.add(new LineSegment(min, max));
                                StdOut.println("The added item is " + new LineSegment(min, max));
                            }
                        }
                    }
                }
            }
        }
    }   // finds all line segments containing 4 points
    public int numberOfSegments() {
        return segNum;
    }       // the number of line segments
    public LineSegment[] segments() {
        int length = listResult.size();
        LineSegment[] result = new LineSegment[length];
        int i = 0;
        for (LineSegment a : listResult) {
            result[i++] = a;
        }
        return result;
    }               // the line segments
    
    // read the n points from a file
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
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}
