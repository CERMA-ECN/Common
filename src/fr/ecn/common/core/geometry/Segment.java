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
	 * @param p1 the p1 to set
	 */
	public void setP1(Point p1) {
		this.p1 = p1;
	}

	/**
	 * @return the p2
	 */
	public Point getP2() {
		return p2;
	}

	/**
	 * @param p2 the p2 to set
	 */
	public void setP2(Point p2) {
		this.p2 = p2;
	}
	
}
