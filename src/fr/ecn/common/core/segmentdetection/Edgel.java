package fr.ecn.common.core.segmentdetection;

public class Edgel {
	
	public int x;
	
	public int y;
	
	/**
	 * angle of the normal vector in range  [-PI/2;PI/2[
	 */
	public double theta;
	
	/**
	 * @param x
	 * @param y
	 * @param angle
	 */
	public Edgel(int x, int y, double theta) {
		super();
		this.x = x;
		this.y = y;
		this.theta = theta;
	}
	
}
