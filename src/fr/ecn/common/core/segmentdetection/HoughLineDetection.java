package fr.ecn.common.core.segmentdetection;

import java.util.LinkedList;
import java.util.List;

import fr.ecn.common.core.geometry.Line;
import fr.ecn.common.core.image.Image;

public class HoughLineDetection {
	
	protected List<Line> lines = new LinkedList<Line>();
	
	public HoughLineDetection(Image image, List<Edgel> edgels, int threshold) {
		//Creating accumulator array
		int sizeTheta = 200;
		int sizeR     = 200;
		
		//TODO: Better computation of maxR and minR ?
		double maxR = image.getHeight() + image.getWidth();
		
		int[][] array = new int[sizeTheta][sizeR];
		
		for (Edgel edgel : edgels) {
			double theta = edgel.theta;
			double r     = edgel.x*Math.cos(theta) + edgel.y*Math.sin(theta);
			
			int iTheta = (int) ((theta/Math.PI + 0.5)*sizeTheta);
			int iR     = (int) ((r/(maxR*2)+0.5)*sizeR);
			
			//Vote !
			array[iTheta][iR]++;
		}
		
		{
			int[][] smoothedArray = new int[sizeTheta][sizeR];

			for (int iTheta=0; iTheta<sizeTheta; iTheta++) {
				for (int iR=0; iR<sizeR; iR++) {
					smoothedArray[iTheta][iR] += array[(iTheta + sizeTheta - 1)%sizeTheta][iR];
					
					smoothedArray[iTheta][iR] += array[(iTheta + 1)%sizeTheta][iR];
					
					if (iR > 0)
						smoothedArray[iTheta][iR] += array[iTheta][iR-1];
					
					if (iR < sizeTheta-1)
						smoothedArray[iTheta][iR] += array[iTheta][iR+1];
					
					smoothedArray[iTheta][iR] /= 5;
				}
			}
			
			array = smoothedArray;
		}
		
		
		//Finding peeks
		for (int iTheta=0; iTheta<sizeTheta; iTheta++) {
			for (int iR=0; iR<sizeR; iR++) {
				if (array[iTheta][iR] < threshold)
					continue;
				
				if (array[(iTheta + sizeTheta - 1)%sizeTheta][iR] > array[iTheta][iR])
					continue;
				
				if (array[(iTheta + 1)%sizeTheta][iR] > array[iTheta][iR])
					continue;
				
				if (iR > 0 && array[iTheta][iR-1] > array[iTheta][iR])
					continue;
				
				if (iR < sizeTheta-1 && array[iTheta][iR+1] > array[iTheta][iR])
					continue;
				
				//Add line to the list
				double theta = (((double)iTheta)/sizeTheta - 0.5) * Math.PI;
				double r     = (((double)iR)/sizeR - 0.5) * maxR*2;
				
				this.lines.add(new Line(-Math.cos(theta)/Math.sin(theta), r/Math.sin(theta)));
			}
		}
	}

	/**
	 * @return the lines
	 */
	public List<Line> getLines() {
		return lines;
	}
	
}
