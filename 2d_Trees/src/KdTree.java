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

    private boolean contains(Point2D p, Node2D current, boolean isVertical, boolean insert) {
        if (current.point.x() == p.x()
         && current.point.y() == p.y()) {
            return true;
        }

        if (isVertical) {
            if (p.x() < current.point.x()) {
                if (current.lt == null) {
                    if (insert) {
                        current.lt = new Node2D(p);
                        size++;
                    }
                } else {
                    contains(p, current.lt, HORIZONTAL, insert);
                }
            } else {
                if (current.rt == null) {
                    if (insert) {
                        current.rt = new Node2D(p);
                        size++;
                    }
                } else {
                    contains(p, current.rt, HORIZONTAL, insert);
                }
            }
        } else {
            if(p.y() < current.point.y()) {
                if (current.lt == null) {
                    if (insert) {
                        current.lt = new Node2D(p);
                        size++;
                    }
                } else {
                    contains(p, current.lt, VERTICAL, insert);
                }
            } else {
                if (current.rt == null) {
                    if (insert) {
                        current.rt = new Node2D(p);
                        size++;
                    }
                } else {
                    contains(p, current.rt, VERTICAL, insert);
                }
            }
        }

        return false;
    }

    public void insert(Point2D p) {
    // add the point p to the set (if it is not already in the set)
        if (root == null) {
            root = new Node2D(p);
            size++;
        } else {
            contains(p, root, VERTICAL, true);
        }

    }

    public boolean contains(Point2D p) {
    // does the set contain the point p?
        return root != null && contains(p, root, VERTICAL, false);
    }

    private void draw(Node2D current) {
        if (current != null) {
            draw(current.lt);
            current.point.draw();
            draw(current.rt);
        }
    }

    public void draw() {
    // draw all of the points to standard draw
        draw(root);
    }

    private void range(RectHV rect, Node2D current,
                       boolean isVertical, SET<Point2D> points) {
        if (current != null) {
            if (isVertical) {
                if (rect.xmax() < current.point.x()) {
                    range(rect, current.lt, HORIZONTAL, points);
                } else if (rect.xmin() > current.point.x()) {
                    range(rect, current.rt, HORIZONTAL, points);
                } else {
                    points.add(current.point);
                    range(rect, current.lt, HORIZONTAL, points);
                    range(rect, current.rt, HORIZONTAL, points);
                }
            } else {
                if (rect.ymax() < current.point.y()) {
                    range(rect, current.lt, VERTICAL, points);
                } else if (rect.ymin() > current.point.y()) {
                    range(rect, current.rt, VERTICAL, points);
                } else {
                    points.add(current.point);
                    range(rect, current.lt, VERTICAL, points);
                    range(rect, current.rt, VERTICAL, points);
                }
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
    // all points in the set that are inside the rectangle
        SET<Point2D> points = new SET<Point2D>();
        range(rect, root, VERTICAL, points);
        return points;
    }

    private Point2D nearest(Point2D p, Node2D current, boolean isVertical) {
        if (current == null) return null;
    }

    public Point2D nearest(Point2D p) {
    // a nearest neighbor in the set to p; null if set is empty
        return nearest(p, root, VERTICAL);


    }
}