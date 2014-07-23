/**
 * Created by Qubo Song on 7/23/2014.
 */
public class KdTree {

    SET<Point2D> points;

    public KdTree() {
    // construct an empty set of points
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
    // is the set empty?
        return points.isEmpty();
    }

    public int size() {
    // number of points in the set
        return points.size();
    }

    public void insert(Point2D p) {
    // add the point p to the set (if it is not already in the set)
        if (!points.contains(p)) points.add(p);
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