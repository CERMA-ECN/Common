package fr.ecn.common.core.geometry;

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
}
