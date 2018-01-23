import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Queue;
import java.util.LinkedList;

public class PointSET {
    
    private SET<Point2D> dataSet;
    
    public PointSET() {
        this.dataSet = new SET<Point2D>();
    } // construct an empty set of points 
    
    public boolean isEmpty() {
        return dataSet.isEmpty();
    } // is the set empty? 
    
    public int size() {
        return dataSet.size();
    } // number of points in the set 
    
    public void insert(Point2D p) {
        if (!dataSet.contains(p)) {
            dataSet.add(p);
        }    
    } // add the point to the set (if it is not already in the set)
    
    public boolean contains(Point2D p) {
        return dataSet.contains(p);
    } // does the set contain point p? 
    
    public void draw() {
        for (Point2D a : dataSet) {
            a.draw();
        }
    } // draw all points to standard draw 
    
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> result = new LinkedList<Point2D>();
        for (Point2D a : dataSet) {
            if (rect.contains(a)) {
                result.add(a);
            }
        }
        return result;
    }  // all points that are inside the rectangle (or on the boundary) 
    
    public Point2D nearest(Point2D p) {
        double dist = 0;
        Point2D neighbor = null;
        int i = 0;
        for (Point2D a : dataSet) {
            if (i == 0) {
                dist = p.distanceTo(a);
                neighbor = a;
            }
            double temp = p.distanceTo(a);
            if (temp < dist) {
                dist = temp;
                neighbor = a;
        }
            i++;
      }
        return neighbor;
    } // a nearest neighbor in the set to point p; null if the set is empty 

    // public static void main(String[] args) // unit testing of the methods (optional) 
}
