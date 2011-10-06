package fr.ecn.common.core.segmentdetection;

import java.util.LinkedList;
import java.util.List;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.core.geometry.Segment;
import fr.ecn.common.core.image.Image;

public class SegmentDetection {

	private List<Segment> segments;

	@SuppressWarnings("unchecked")
	public SegmentDetection(List<Edgel> edgels, Image image, int num_dir) {
		List<Edgel>[] direction = (List<Edgel>[]) new List[num_dir];
		for (int i=0; i<num_dir; i++) {
			direction[i] = new LinkedList<Edgel>();
		}
		
		for (Edgel e : edgels) {
			/**
			 * Calculate angle value in [|0;num_dir - 1|]
			 */
			int angle = (int) Math.floor((e.theta / Math.PI + 0.5) * num_dir);
			/**
			 * Add point indexes(k), which is on an edge of direction k, into direction hashmap.
			 */
			direction[angle].add(e);// i = horizontal coordinate ; j = vertical coordinate
		}

		/**
		 * Initialize final segment map
		 */
		this.segments = new LinkedList<Segment>();
	
		/**
		 * Compute relevant information for each direction in [|0, num_dir-1|]
		 */
		int[][] dir_im;
		for (int k=0; k<num_dir; k++) {
			dir_im = new int[image.getHeight()][image.getWidth()];
	
			/**
			 * For direction k, find points on k-oriented edges
			 * Put 1 in matrix dir_im where a point is on a k-oriented edge
			 */
			for (Edgel e : direction[k]) {
				dir_im[e.y][e.x] = 1;
			}
			for (Edgel e : direction[(k+1)%num_dir]) {
				dir_im[e.y][e.x] = 1;
			}
			for (Edgel e : direction[(k-1+num_dir)%num_dir]) {
				dir_im[e.y][e.x] = 1;
			}
	
			/**
			 * Find 8-connected components in matrix dir_im
			 */
			ConnectedObjects c = LabelImage.labelImage(dir_im,num_dir); // Get the connected points
			
			/**
			 * Initialize classified segments array
			 */
			List<Edgel>[] classifiedEdgels = (List<Edgel>[]) new List[c.getNum_labels()];
			for (int id=0; id<classifiedEdgels.length; id++){
				classifiedEdgels[id] = new LinkedList<Edgel>();
			}
			/**
			 * Get the number of pixels in each edge
			 */
			for (Edgel e : direction[k]) {
				int id = c.getMatrix(e.y, e.x);
				classifiedEdgels[id].add(e);
			}

			double minLength = 0.025*Math.sqrt(image.getWidth()*image.getWidth() + image.getHeight()*image.getHeight());
			
			/**
			 * Add eligible edges to finalSegmentMap
			 */
			for (int id = 0; id < classifiedEdgels.length; id++) {
				if (classifiedEdgels[id].size() >= minLength) {
					this.addSegment(classifiedEdgels[id]);
				}
			}
		}
	}

	private void addSegment(List<Edgel> edgels) {

		double meanX = 0;
		double meanY = 0;
		int Xmin = edgels.get(0).getX();
		int Xmax = edgels.get(0).getX();
		int Ymin = edgels.get(0).getY();
		int Ymax = edgels.get(0).getY();

		for (int i=0; i<edgels.size(); i++){
			meanX += edgels.get(i).getX();
			meanY += edgels.get(i).getY();
			if (edgels.get(i).getX() < Xmin){
				Xmin = edgels.get(i).getX();
			} else if (edgels.get(i).getX() > Xmax){
				Xmax = edgels.get(i).getX();
			}
			if (edgels.get(i).getY() < Ymin){
				Ymin = edgels.get(i).getY();
			} else if (edgels.get(i).getY() > Ymax){
				Ymax = edgels.get(i).getY();
			}
		}
		meanX = meanX / edgels.size();
		meanY = meanY / edgels.size();

		double[][] M = new double[2][2];
		for (int i=0; i<edgels.size(); i++){
			M[0][0] = M[0][0] + (edgels.get(i).getX() - meanX)*(edgels.get(i).getX() - meanX);
			M[1][1] = M[1][1] + (edgels.get(i).getY() - meanY)*(edgels.get(i).getY() - meanY);
			M[0][1] = M[0][1] + (edgels.get(i).getX() - meanX)*(edgels.get(i).getY() - meanY);
			M[1][0] = M[1][0] + (edgels.get(i).getX() - meanX)*(edgels.get(i).getY() - meanY);
		}
		Matrix MM = new Matrix(M);
		EigenvalueDecomposition evd = new EigenvalueDecomposition(MM);
		Matrix lambda = evd.getD();
		Matrix v = evd.getV();

		double theta = Math.atan2(v.get(1, 1), v.get(0, 1));
		double conf;
		if (lambda.get(0,0) > 0){
			conf = lambda.get(1, 1) / lambda.get(0, 0);
		} else {
			conf = 100000;
		}
		
		if (conf >= 400){
			double r = Math.sqrt((Xmax - Xmin)*(Xmax - Xmin) + (Ymax - Ymin)*(Ymax - Ymin));
			
			Point startPoint = new Point(meanX - Math.cos(theta)*r/2, meanY - Math.sin(theta)*r/2);
			Point endPoint   = new Point(meanX + Math.cos(theta)*r/2, meanY + Math.sin(theta)*r/2);
			
			this.segments.add(new Segment(startPoint, endPoint));
		}
	}

	/**
	 * @return the segments
	 */
	public List<Segment> getSegments() {
		return segments;
	}
}
