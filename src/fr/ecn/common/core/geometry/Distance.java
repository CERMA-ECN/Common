package fr.ecn.common.core.geometry;

/**
 * @author jerome
 *
 * A class to compute distances between different objects.
 */
public class Distance {
	
	public static double distance(Point p1, Point p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return Math.sqrt(dx * dx + dy * dy);
	}

	public static double distance(Point p, Line l) {
		return Math.abs(l.a*p.x - p.y + l.b)/Math.sqrt(l.a*l.a + 1);
	}
}
