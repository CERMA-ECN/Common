package fr.ecn.common.core.geometry;

/**
 * @author jerome
 *
 */
public class Geometry {

	/**
	 * @param l1
	 * @param l2
	 * @return the intersection of l1 and l2
	 */
	public static Point intersection(Line l1, Line l2) {
		double x = (l2.b - l1.b) / (l1.a - l2.a);
		double y = l1.a * x + l1.b;
		return new Point(x, y);
	}

	/**
	 * @param s1
	 * @param s2
	 * @return the intersection of s1 and s2
	 */
	public static Point intersection(Segment s1, Segment s2) {
		Point p = intersection(s1.getLine(), s2.getLine());
		
		if (p.getX() > s1.getXMax() || p.getX() < s1.getXMin()
				|| p.getX() > s2.getXMax() || p.getX() < s2.getXMin()) {
			return null;		
		} else {
			return p;
		}
	}
}
