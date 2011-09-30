package fr.ecn.common.core.segmentdetection;

import java.util.LinkedList;
import java.util.List;

import fr.ecn.common.core.geometry.Distance;
import fr.ecn.common.core.geometry.Line;
import fr.ecn.common.core.geometry.Point;

public class RansacLineDetection {
	
	private List<Line> lines = new LinkedList<Line>();
	
	public RansacLineDetection(List<Edgel> edgels, double stopThreshold, int maxIterations, double maxDistance, double maxAngle, int minPointNumber) {
		//Number of points that can be left unfit
		int maxReaminingEdgelsCount = (int) ((double)edgels.size() * stopThreshold);
		
		while(edgels.size() > maxReaminingEdgelsCount) {
			Line bestLine = null;
			List<Edgel> bestPoints = null;
			double bestError = Double.POSITIVE_INFINITY;
			
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
						bestLine   = this.linReg(currentEdgels);
//						bestError  = error;
						bestPoints = currentEdgels;
					}
				}
			}
			
			if (bestLine != null) {
				this.lines.add(bestLine);
				edgels.removeAll(bestPoints);
			}
		}
		
	}

	private Line lineFromEdgel(Edgel edgel) {
		double a = Math.tan(edgel.theta + Math.PI/2);
		double b = edgel.y - a*edgel.x;
		
		return new Line(a, b);
	}
	
	private Line linReg(List<Edgel> edgels) {
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
		}
		X = X / edgels.size();
		Y = Y / edgels.size();

		/**
		 * Calculate num and den
		 */
		for (Edgel e : edgels){
			num = num + (e.x - X) * (e.y - Y);
			den = den + (e.x - X) * (e.x - X);
		}

		/**
		 * Calculate a and b
		 */
		a = num / den;
		b = Y - (a * X);

		/**
		 * Calculate and modify start and end points
		 */
//		Point startPoint = new Point(Xmin, a*Xmin + b);
//		Point endPoint = new Point(Xmax, a*Xmax + b);
		
		return new Line(a, b);
	}

	public List<Line> getLines() {
		return lines;
	}
}
