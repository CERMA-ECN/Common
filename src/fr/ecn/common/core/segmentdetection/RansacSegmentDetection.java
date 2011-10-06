package fr.ecn.common.core.segmentdetection;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import fr.ecn.common.core.geometry.Distance;
import fr.ecn.common.core.geometry.Line;
import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.core.geometry.Segment;

public class RansacSegmentDetection {
	
	private List<Segment> segments = new LinkedList<Segment>();
	
	public RansacSegmentDetection(List<Edgel> edgels, double stopThreshold, int maxIterations, double maxDistance, double maxAngle, int minPointNumber) {
		//Number of points that can be left unfit
		int maxReaminingEdgelsCount = (int) ((double)edgels.size() * stopThreshold);
		
		while(edgels.size() > maxReaminingEdgelsCount) {
//			Segment bestLine = null;
			List<Edgel> bestPoints = null;
//			double bestError = Double.POSITIVE_INFINITY;
			
			for (int i=0 ; i<maxIterations; i++) {
				
				Edgel e = edgels.get((int) (Math.random()*edgels.size()));
				
				Line currentLine = this.lineFromEdgel(e);
				
				List<Edgel> currentEdgels = new LinkedList<Edgel>();
				currentEdgels.add(e);
				
				for (Edgel edgel : edgels) {
					if (edgel == e)
						continue;
					
					if (Distance.distance(new Point(edgel.x, edgel.y), currentLine) < maxDistance
							&& Math.abs(edgel.theta - e.theta) < maxAngle) {
						currentEdgels.add(edgel);
					}
				}
				
				if (currentEdgels.size() > minPointNumber) {
//					Segment line = new Segment();
//					for (Edgel edgel : currentEdgels) {
//						line.addPoint(new Point(edgel.x, edgel.y));
//					}
//					
//					double error = line.computeStartEndPoints();
					
					if (bestPoints == null || currentEdgels.size() > bestPoints.size()) {
//					if (error < bestError) {
//						bestLine   = this.linReg(currentEdgels);
//						bestError  = error;
						bestPoints = currentEdgels;
					}
				}
			}
			
			if (bestPoints != null) {
				//Cut this line into segments
				int Xmin = Integer.MAX_VALUE;
				int Xmax = Integer.MIN_VALUE;
				int Ymin = Integer.MAX_VALUE;
				int Ymax = Integer.MIN_VALUE;
				
				for (Edgel e : edgels) {
					if (e.x < Xmin)
						Xmin = e.x;
					if (e.x > Xmax)
						Xmax = e.x;
					if (e.y < Ymin)
						Ymin = e.y;
					if (e.y > Ymax)
						Ymax = e.y;
				}
				
				Edgel[] sortedEdgels = bestPoints.toArray(new Edgel[0]);
				if (Xmax - Xmin > Ymax - Ymin) {
					Arrays.sort(sortedEdgels, new Comparator<Edgel>() {
						public int compare(Edgel e1, Edgel e2) {
							return  e1.x - e2.x;
						}
					});
				} else {
					Arrays.sort(sortedEdgels, new Comparator<Edgel>() {
						public int compare(Edgel e1, Edgel e2) {
							return  e1.y - e2.y;
						}
					});
				}
					
				Edgel lastEdgel = null;
				List<Edgel> currentEdgels = null;
				for (Edgel edgel : sortedEdgels) {
					if (lastEdgel == null || Distance.distance(new Point(lastEdgel.x, lastEdgel.y), new Point(edgel.x, edgel.y)) > 7.5) {
						if (currentEdgels != null && currentEdgels.size() > 10) {
							this.segments.add(this.linReg(currentEdgels));
						}
						
						currentEdgels = new LinkedList<Edgel>();
					}
					
					currentEdgels.add(edgel);
					lastEdgel = edgel;
				}
				
//				this.segments.add(bestLine);
				edgels.removeAll(bestPoints);
			}
		}
		
	}

	private Line lineFromEdgel(Edgel edgel) {
		double a = Math.tan(edgel.theta + Math.PI/2);
		double b = edgel.y - a*edgel.x;
		
		return new Line(a, b);
	}
	
	private Segment linReg(List<Edgel> edgels) {
		/**
		 * Linear regression coefficients
		 * a, b
		 * y = a*x + b
		 */
		double a, b;

		/**
		 * 'a' and 'b' coefficients are computed this way:
		 * s = {(Xi,Yi)}, i=1..n
		 * X = mean(Xi); Y = mean(Yi)
		 * num = sum[(Xi - X)*(Yi-Y)], i=1..n
		 * den = sum[(Xi - X)^2], i=1..n
		 * a = num / den
		 * b = Y - a*X
		 */
		double num 	= 0;
		double den 	= 0;
		double X	= 0;
		double Y	= 0;

		/**
		 * To get integer values for start point and end point, we keep in memory Xmin and Xmax.
		 * Startpoint will be (Xmin, a*Xmin + b)
		 * Endpoint will be (Xmax, a*Xmax + b)
		 */
		int Xmin = Integer.MAX_VALUE;
		int Xmax = Integer.MIN_VALUE;
		int Ymin = Integer.MAX_VALUE;
		int Ymax = Integer.MIN_VALUE;

		/**
		 * Calculate X and Y
		 */
		for (Edgel e : edgels){
			X = X + e.x;
			Y = Y + e.y;
			if (e.x < Xmin){
				Xmin = e.x;
			} else if (e.x > Xmax){
				Xmax = e.x;
			}
			
			if (e.y < Ymin)
				Ymin = e.y;
			if (e.y > Ymax)
				Ymax = e.y;
		}
		X = X / edgels.size();
		Y = Y / edgels.size();

		/**
		 * Calculate num and den
		 */
		for (Edgel e : edgels){
			num += (e.x - X) * (e.y - Y);
			den += (e.x - X) * (e.x - X);
		}
		
		/**
		 * Calculate a and b
		 */
		a = num / den;
		b = Y - (a * X);

		/**
		 * Calculate and modify start and end points
		 */
		Point p1;
		Point p2;
		if (Xmax == Xmin) {
			//Vertical line
			p1 = new Point(Xmin, Ymin);
			p2 = new Point(Xmin, Ymax);
		} else {
			if (Xmax - Xmin > Ymax - Ymin) {
				p1 = new Point(Xmin, a*Xmin + b);
				p2 = new Point(Xmax, a*Xmax + b);
			} else {
				p1 = new Point((Ymin-b)/a, Ymin);
				p2 = new Point((Ymax-b)/a, Ymax);
			}
		}
		
		return new Segment(p1, p2);
	}

	public List<Segment> getSegments() {
		return segments;
	}
}
