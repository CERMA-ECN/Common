package fr.ecn.common.core.geometry;

public class Segment {
	
	protected Point p1;
	protected Point p2;
	
	/**
	 * @param p1
	 * @param p2
	 */
	public Segment(Point p1, Point p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
	}

	/**
	 * @return the p1
	 */
	public Point getP1() {
		return p1;
	}

	/**
	 * @return the p2
	 */
	public Point getP2() {
		return p2;
	}
	
	/**
	 * @return the line passing through the 2 points of this segment
	 */
	public Line getLine() {
		return new Line(this.p1, this.p2);
	}
	
	public double getXMin() {
		return Math.min(p1.getX(), p2.getX());
	}
	
	public double getXMax() {
		return Math.max(p1.getX(), p2.getX());
	}
}
