/**
 * Created by Qubo Song on 7/23/2014.
 */

public class KdTree {

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;
    private static final boolean LEFT = true;
    private static final boolean RIGHT = false;
    private Node2D root;
    private int size;


    private class Node2D {
        private Point2D point;
        private RectHV rect;
        private Node2D lt;
        private Node2D rt;

        private Node2D(Point2D point) {
            this.point = point;
            this.rect = new RectHV(0, 0, 1, 1);
            lt = new Node2D(this, LEFT, VERTICAL);
            rt = new Node2D(this, RIGHT, VERTICAL);
        }

        public Node2D(Node2D parent, boolean isLeftTree, boolean isVertical) {
            this.point = null;
            if (isVertical) {
                if (isLeftTree) {
                    this.rect = new RectHV(parent.rect.xmin(),
                                           parent.rect.ymin(),
                                           parent.point.x(),
                                           parent.rect.ymax());
                } else {
                    this.rect = new RectHV(parent.point.x(),
                                           parent.rect.ymin(),
                                           parent.rect.xmax(),
                                           parent.rect.ymax());
                }
            } else {
                if (isLeftTree) {
                    this.rect = new RectHV(parent.rect.xmin(),
                                           parent.rect.ymin(),
                                           parent.rect.xmax(),
                                           parent.point.y());
                } else {
                    this.rect = new RectHV(parent.rect.xmin(),
                                           parent.point.y(),
                                           parent.rect.xmax(),
                                           parent.rect.ymax());
                }
            }
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

    private boolean contains(Point2D p, Node2D current,
                             boolean isVertical, boolean insert) {
        if (current.point.x() == p.x()
         && current.point.y() == p.y()) {
            return true;
        }

        if (isVertical) {
            if (p.x() < current.point.x()) {
                if (current.lt.point == null) {
                    if (insert) {
                        current.lt.point = p;
                        current.lt.lt = new Node2D(current.lt, LEFT, HORIZONTAL);
                        current.lt.rt = new Node2D(current.lt, RIGHT, HORIZONTAL);
                        size++;
                    }
                } else {
                    return contains(p, current.lt, HORIZONTAL, insert);
                }
            } else {
                if (current.rt.point == null) {
                    if (insert) {
                        current.rt.point = p;
                        current.rt.lt = new Node2D(current.rt, LEFT, HORIZONTAL);
                        current.rt.rt = new Node2D(current.rt, RIGHT, HORIZONTAL);
                        size++;
                    }
                } else {
                    return contains(p, current.rt, HORIZONTAL, insert);
                }
            }
        } else {
            if (p.y() < current.point.y()) {
                if (current.lt.point == null) {
                    if (insert) {
                        current.lt.point = p;
                        current.lt.lt = new Node2D(current.lt, LEFT, VERTICAL);
                        current.lt.rt = new Node2D(current.lt, RIGHT, VERTICAL);
                        size++;
                    }
                } else {
                    return contains(p, current.lt, VERTICAL, insert);
                }
            } else {
                if (current.rt.point == null) {
                    if (insert) {
                        current.rt.point = p;
                        current.rt.lt = new Node2D(current.rt, LEFT, VERTICAL);
                        current.rt.rt = new Node2D(current.rt, RIGHT, VERTICAL);
                        size++;
                    }
                } else {
                    return contains(p, current.rt, VERTICAL, insert);
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

    private void draw(Node2D current, boolean isVertical) {
        if (current != null) {
            draw(current.lt, !isVertical);
            draw(current.rt, !isVertical);
            if (isVertical) StdDraw.setPenColor(StdDraw.RED);
            else StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            current.rect.draw();
            if (current.point != null) {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(.01);
                current.point.draw();
            }

        }
    }

    public void draw() {
    // draw all of the points to standard draw
        draw(root.lt, VERTICAL);
        draw(root.rt, VERTICAL);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        root.point.draw();
        StdDraw.setPenRadius();
        root.rect.draw();
    }

    private void range(RectHV rect, Node2D current, SET<Point2D> points) {
        if (current != null && current.point != null) {
            if (rect.contains(current.point)) points.add(current.point);
            if (rect.intersects(current.lt.rect)) range(rect, current.lt, points);
            if (rect.intersects(current.rt.rect)) range(rect, current.rt, points);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
    // all points in the set that are inside the rectangle
        SET<Point2D> points = new SET<Point2D>();
        range(rect, root, points);
        return points;
    }

    private Node2D nearest(Point2D p, Node2D current, boolean isVertical) {

        Node2D nearest = current;
        Node2D temp;

        if (current == null) return null;

        if (current.lt.point == null && current.rt.point == null) return current;

        if (isVertical) {
            if (p.x() < current.point.x()) {
                if (current.lt.point != null) {
                    temp = nearest(p, current.lt, HORIZONTAL);
                    if (temp.point.distanceSquaredTo(p)
                            < nearest.point.distanceSquaredTo(p)) {
                        nearest = temp;
                    }
                }
                if (current.rt.point != null
                        && current.rt.rect.distanceSquaredTo(p)
                        < nearest.point.distanceSquaredTo(p)) {
                    temp = nearest(p, current.rt, HORIZONTAL);
                    if (temp.point.distanceSquaredTo(p)
                            < nearest.point.distanceSquaredTo(p)) {
                        nearest = temp;
                    }
                }
            } else {
                if (current.rt.point != null) {
                    temp = nearest(p, current.rt, HORIZONTAL);
                    if (temp.point.distanceSquaredTo(p)
                            < nearest.point.distanceSquaredTo(p)) {
                        nearest = temp;
                    }
                }
                if (current.lt.point != null && current.lt.rect.distanceSquaredTo(p)
                            < nearest.point.distanceSquaredTo(p)) {
                    temp = nearest(p, current.lt, HORIZONTAL);
                    if (temp.point.distanceSquaredTo(p)
                            < nearest.point.distanceSquaredTo(p)) {
                        nearest = temp;
                    }
                }
            }
        } else {
            if (p.y() < current.point.y()) {
                if (current.lt.point != null) {
                    temp = nearest(p, current.lt, VERTICAL);
                    if (temp.point.distanceSquaredTo(p)
                            < nearest.point.distanceSquaredTo(p)) {
                        nearest = temp;
                    }
                }
                if (current.rt.point != null) {
                    if (current.rt.rect.distanceSquaredTo(p)
                            < nearest.point.distanceSquaredTo(p)) {
                        temp = nearest(p, current.rt, VERTICAL);
                        if (temp.point.distanceSquaredTo(p)
                                < nearest.point.distanceSquaredTo(p)) {
                            nearest = temp;
                        }
                    }
                }
            } else {
                if (current.rt.point != null) {
                    temp = nearest(p, current.rt, VERTICAL);
                    if (temp.point.distanceSquaredTo(p)
                            < nearest.point.distanceSquaredTo(p)) {
                        nearest = temp;
                    }
                }
                if (current.lt.point != null) {
                    if (current.lt.rect.distanceSquaredTo(p)
                            < nearest.point.distanceSquaredTo(p)) {
                        temp = nearest(p, current.lt, VERTICAL);
                        if (temp.point.distanceSquaredTo(p)
                                < nearest.point.distanceSquaredTo(p)) {
                            nearest = temp;
                        }
                    }
                }
            }
        }

        return nearest;
    }


    public Point2D nearest(Point2D p) {
    // a nearest neighbor in the set to p; null if set is empty
        Node2D current = root;
        current = nearest(p, current, VERTICAL);

        if (current == null)
            return null;

        return current.point;
    }
}