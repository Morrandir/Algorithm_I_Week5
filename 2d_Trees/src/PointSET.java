/**
 * Created by Qubo Song on 7/23/2014.
 */
public class PointSET {

    SET<Point2D> points;

    public PointSET() {
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
        points.add(p);
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
        SET<Point2D> pointsInside = new SET<Point2D>();

        for (Point2D point : points) {
            if (point.x() >= rect.xmin()
             && point.x() <= rect.xmax()
             && point.y() >= rect.ymin()
             && point.y() <= rect.ymax()) {
                pointsInside.add(point);
            }
        }

        return pointsInside;
    }

    public Point2D nearest(Point2D p) {
    // a nearest neighbor in the set to p; null if set is empty
        Point2D nearest = new Point2D(Double.POSITIVE_INFINITY,
                                      Double.POSITIVE_INFINITY);

        for(Point2D point : points) {
            if (point.distanceTo(p) < nearest.distanceTo(p)) {
                nearest = point;
            }
        }

        return nearest;
    }
}