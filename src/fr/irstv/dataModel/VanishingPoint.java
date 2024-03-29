 package fr.irstv.dataModel;

import java.util.LinkedList;


public class VanishingPoint extends DataPoint {
	
	/**
	 * @uml.property  name="segmentList"
	 */
	private LinkedList<Segment> segmentList;

	/**
	 * @uml.property   name="circleK"
	 * @uml.associationEnd   multiplicity="(1 1)" inverse="vanishingPoint:fr.irstv.dataModel.CircleK"
	 */
	private CircleK circleK = null;

	public VanishingPoint(int dim) {
		super(dim);
		this.segmentList = new LinkedList<Segment>();
	}

	public VanishingPoint(DataPoint copyMe) {
		super(copyMe);
		this.segmentList = new LinkedList<Segment>();
	}

	/**
	 * Constructeur avec une linkedlist de segments.
	 */
	public VanishingPoint(int dim, LinkedList<Segment> newSegmentList){
		super(dim);
		this.segmentList = newSegmentList;
	}

	/**
	 * Getter of the property <tt>segmentList</tt>
	 * @return  Returns the segmentList.
	 * @uml.property  name="segmentList"
	 */
	public LinkedList<Segment> getSegmentList() {
		return segmentList;
	}

	/**
	 * Setter of the property <tt>segmentList</tt>
	 * @param segmentList  The segmentList to set.
	 * @uml.property  name="segmentList"
	 */
	public void setSegmentList(LinkedList<Segment> segmentList) {
		this.segmentList = segmentList;
	}

	/**
	 * Getter of the property <tt>circleK</tt>
	 * @return  Returns the circleK.
	 * @uml.property  name="circleK"
	 */
	public CircleK getCircleK() {
		return circleK;
	}

	/**
	 * Setter of the property <tt>circleK</tt>
	 * @param circleK  The circleK to set.
	 * @uml.property  name="circleK"
	 */
	public void setCircleK(CircleK circleK) {
		this.circleK = circleK;
	}


}
