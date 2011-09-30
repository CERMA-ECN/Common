package fr.ecn.common.core.segmentdetection;

import java.util.LinkedList;
import java.util.List;

import fr.ecn.common.core.geometry.Distance;
import fr.ecn.common.core.geometry.Line;
import fr.ecn.common.core.geometry.Point;

public class RansacLineDetection {
	
	private List<Line> lines = new LinkedList<Line>();
	
	public RansacLineDetection(List<Edgel> edgels, double stopThreshold, int maxIterations, double maxDistance, int minPointNumber) {
		//Number of points that can be left unfit
		int maxReaminingEdgelsCount = (int) ((double)edgels.size() * stopThreshold);
		
		while(edgels.size() > maxReaminingEdgelsCount) {
			Line bestLine = null;
			List<Edgel> bestPoints = null;
			double bestError = Double.POSITIVE_INFINITY;
			
			for (int i=0 ; i<maxIterations; i++) {
				int i1, i2;
				
				do {
					i1 = (int) (Math.random()*edgels.size());
					i2 = (int) (Math.random()*edgels.size());
				} while (i1 == i2);
				
				Edgel e1 = edgels.get(i1);
				Edgel e2 = edgels.get(i2);
				
				Line currentLine = new Line(new Point(e1.x, e1.y), new Point(e2.x, e2.y));
				
				List<Edgel> currentEdgels = new LinkedList<Edgel>();
				currentEdgels.add(e1);
				currentEdgels.add(e2);
				
				for (Edgel edgel : edgels) {
					if (edgel == e1 || edgel == e2)
						continue;
					
					if (Distance.distance(new Point(edgel.x, edgel.y), currentLine) < maxDistance) {
						currentEdgels.add(edgel);
					}
				}
				
				if (currentEdgels.size() > minPointNumber) {
					Segment line = new Segment();
					for (Edgel e : currentEdgels) {
						line.addPoint(new Point(e.x, e.y));
					}
					
					double error = line.computeStartEndPoints();
					
					if (bestPoints == null || currentEdgels.size() > bestPoints.size()) {
//					if (error < bestError) {
						bestLine   = new Line(line.getStartPoint(), line.getEndPoint());
						bestError  = error;
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
	
	protected Line lineFromEdgel(Edgel edgel) {
		
	}

	public List<Line> getLines() {
		return lines;
	}
}
