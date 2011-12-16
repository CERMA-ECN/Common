/**
 * 
 */
package fr.ecn.common.core.imageinfos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.ecn.common.core.geometry.Geometry;
import fr.ecn.common.core.geometry.Line;
import fr.ecn.common.core.geometry.Point;

/**
 * A class to store information about a face
 * 
 * @author jerome
 *
 */
public class Face implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected List<Point> points;
	
	protected boolean partial;
	protected boolean notReal;

	public Face() {
		this(false, false);
	}

	/**
	 * @param partial
	 * @param notreal
	 */
	public Face(boolean partial, boolean notReal) {
		super();
		this.points = new ArrayList<Point>();
		this.partial = partial;
		this.notReal = notReal;
	}

	/**
	 * @return true if this face is a partial face
	 */
	public boolean isPartial() {
		return partial;
	}

	/**
	 * @return true if this face is not a "real" face
	 */
	public boolean isNotReal() {
		return notReal;
	}

	/**
	 * @return the points
	 */
	public List<Point> getPoints() {
		return points;
	}
	
	/**
	 * Generate a real face based on this face :
	 *  - If this face is a full one, it just return the face
	 *  - If this face is a partial face it generate the missing point
	 * 
	 * @return the real face corresponding to this face
	 */
	public Face getRealFace() {
		if (this.isPartial()) {
			Face face = new Face(false, this.isNotReal());
			
			int size = this.points.size();
			
			//Add all points expect first and last
			for (int i=1; i<size-1;i++) {
				face.getPoints().add(this.points.get(i));
			}
			
			//Add the intersection of the first and last segment
			Line line1 = new Line(this.points.get(0), this.points.get(1));
			Line line2 = new Line(this.points.get(size-1), this.points.get(size-2));
			face.getPoints().add(Geometry.intersection(line1, line2));
			
			return face;
		} else {
			return this;
		}
	}
}
