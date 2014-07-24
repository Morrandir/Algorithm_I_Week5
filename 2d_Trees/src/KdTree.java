import java.awt.geom.*;

/**
 * Created by Qubo Song on 7/23/2014.
 */

public class KdTree {

    private Node2D root;
    private int size;
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    private class Node2D {
        Point2D point;
        Node2D lt;
        Node2D rt;

        private Node2D(Point2D point) {
            this.point = point;
            lt = null;
            rt = null;
        }
    }

    public KdTree() {
    // construct an empty set of points
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
    // is the set empty?
        return size == 0;
    }

    public int size() {
    // number of points in the set
        return size;
    }

    private void insert(Point2D p, Node2D current, boolean isVertical) {
        if (current.point.x() == p.x()
         && current.point.y() == p.y()) {
            return;
        }

        if (isVertical) {
            if (p.x() < current.point.x()) {
                if (current.lt == null) {
                    current.lt = new Node2D(p);
                } else {
                    insert(p, current.lt, HORIZONTAL);
                }
            } else {
                if (current.rt == null) {
                    current.rt = new Node2D(p);
                } else {
                    insert(p, current.rt, HORIZONTAL);
                }
            }
        } else {
            if(p.y() < current.point.y()) {
                if (current.lt == null) {
                    current.lt = new Node2D(p);
                } else {
                    insert(p, current.lt, VERTICAL);
                }
            } else {
                if (current.rt == null) {
                    current.rt = new Node2D(p);
                } else {
                    insert(p, current.rt, VERTICAL);
                }
            }
        }
    }

    public void insert(Point2D p) {
    // add the point p to the set (if it is not already in the set)
        Node2D current = root;
        if (current == null) {
            current = new Node2D(p);
        } else {
            insert(p, current, VERTICAL);
        }

    }

    public boolean contains(Point2D p) {
    // does the set contain the point p?
        return points.contains(p);
    }

    public void draw() {
    // draw all of the points to standard draw
        for (Point2D point : points) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
    // all points in the set that are inside the rectangle

    }

    public Point2D nearest(Point2D p) {
    // a nearest neighbor in the set to p; null if set is empty

    }
}