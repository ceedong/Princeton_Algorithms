import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Queue;
import java.util.LinkedList;

public class KdTree {
    
    private Node root;
    private int size;
    
    // defne the class Node
    private static class Node {
        
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean isVertical; // to record the height
        
        public Node(Point2D p, boolean isVertical) {
            this.p = p;
            this.isVertical = isVertical;
        }
        
    }
    
    public KdTree() {
        this.root = null;
        this.size = 0;
    } // construct an empty set of points
    
    public boolean isEmpty() {
        return size == 0;
    } // is the set empty?
    
    public int size() {
        return size;
    } // number of points in the set
    
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        if (root == null) {
            Node curNode = new Node(p, true);
            curNode.rect = new RectHV(0, 0, 1, 1);
            root = curNode;
            size++;
        }
        
        if (!contains(p)) {
            insert(p, root);
            size++;
        }
        
    } // add the point to the set (if it is not already in the set)
    
    private void insert(Point2D p, Node root) {
        Node curNode = root;
        
        
        
        if (curNode.isVertical == true) {
            
            if (curNode.p.x() <= p.x()) {
                if (curNode.rt == null) {
                    Node newNode = new Node(p, false);
                    newNode.rect = new RectHV(curNode.p.x(), curNode.rect.ymin(), curNode.rect.xmax(), curNode.rect.ymax());
                    curNode.rt = newNode;
                    
                }
                else insert(p, curNode.rt);
            }
            
            if (curNode.p.x() > p.x()) {
                if (curNode.lb == null) {
                    Node newNode = new Node(p, false);
                    newNode.rect = new RectHV(curNode.rect.xmin(), curNode.rect.ymin(), curNode.p.x(), curNode.rect.ymax());
                    curNode.lb = newNode;
                    
                }
                else insert(p, curNode.lb);
            }
            
            
        }
        else {
            
            if (curNode.p.y() <= p.y()) {
                if (curNode.rt == null) {
                    Node newNode = new Node(p, true);
                    newNode.rect = new RectHV(curNode.rect.xmin(), curNode.p.y(), curNode.rect.xmax(), curNode.rect.ymax());
                    curNode.rt = newNode;
                    
                }
                else insert(p, curNode.rt);
            }
            if (curNode.p.y() > p.y()) {
                if (curNode.lb == null) {
                    Node newNode = new Node(p, true);
                    newNode.rect = new RectHV(curNode.rect.xmin(), curNode.rect.ymin(), curNode.rect.xmax(), curNode.p.y());
                    curNode.lb = newNode;
                    
                }
                else insert(p, curNode.lb);
            }
            
        }
        
    }
    
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        return contains(p, root);
    } // does the set contain point p?
    
    private boolean contains(Point2D p, Node root) {
        Node curNode = root;
        if (curNode == null) return false;
        if (curNode.p.equals(p)) return true;
        if (curNode.isVertical == true) {
            if (curNode.p.x() <= p.x()) {
                if (curNode.rt != null && contains(p, root.rt)) return true;
            }
            else if (curNode.lb != null && contains(p, root.lb)) return true;
            
        }
        else {
            if (curNode.p.y() <= p.y()) {
                if (curNode.rt != null && contains(p, root.rt)) return true;
            }
            else if (curNode.lb != null && contains(p, root.lb)) return true;
        }
        return false;
    }
    
    public void draw() {
        if (root == null) return;
        draw(root);
    } // draw all points to standard draw
    
    private void draw(Node root) {
        if (root.isVertical == true) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.002);
            StdDraw.line(root.p.x(), root.rect.ymin(), root.p.x(), root.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.002);
            StdDraw.line(root.rect.xmin(), root.p.y(), root.rect.xmax(), root.p.y());
        }
        
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        root.p.draw();
        
        // recursively draw the left and right nodes:
        if (root.lb != null) {
            draw(root.lb);
        }
        
        if (root.rt != null) {
            draw(root.rt);
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        
        if (rect == null) throw new IllegalArgumentException();
        
        Queue<Point2D> result = new LinkedList<>();
        
        if (root != null) range(rect, result, root);
        
        return result;
        
    } // all points that are inside the rectangle (or on the boundary)
    
    private void range(RectHV rect, Queue<Point2D> result, Node node) {
        if (node.rect.intersects(rect)) {
            if (rect.contains(node.p)) result.add(node.p);
        }
        
        if (node.lb != null) range(rect, result, node.lb);
        if (node.rt != null) range(rect, result, node.rt);
        
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        
        Point2D result = nearest(p, root.p, root);
        
        return result;
    } // a nearest neighbor in the set to point p; null if the set is empty
    
    private Point2D nearest(Point2D p, Point2D closest, Node curNode) {
        if (curNode == null) return closest;
        
        if (curNode.rect.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
            if (curNode.p.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) closest = curNode.p;
        }
        
        if (curNode.isVertical == true) {
            closest = nearest(p, closest, curNode.lb);
            closest = nearest(p, closest, curNode.rt);
        }
        else {
            closest = nearest(p, closest, curNode.rt);
            closest = nearest(p, closest, curNode.lb);
        }
        
        return closest;
    }
    
    public static void main(String[] args) {}                 // unit testing of the methods (optional)
}
