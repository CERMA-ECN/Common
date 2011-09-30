package fr.ecn.common.core.segmentdetection;

import fr.ecn.common.core.image.ByteImage;
import fr.ecn.common.core.image.filters.ByteConvolve;

/**
 * Class implementing the useful tools for ImageSegment class.
 *
 * @author Leo COLLET, Cedric TELEGONE, Ecole Centrale Nantes
 * @version 1.0
 */

public class Utils {

	/**
	 * X and Y gradients computation
	 *
	 * @param 	image	the ImagePlus object which gradients must be computed
	 * @return			an ImagePlus array of length 2 containing the X and Y gradient images of image
	 */
	public static ByteImage[] getGradients(ByteImage image){
		/**
		 *  Initialize gradient matrixes
		 */
		ByteImage gradXimage = null;
		ByteImage gradYimage = null;
		/**
		 *  Initialize normalized convolution kernels
		 */
		//float coeff = (float) 0.5;
//		float coeff = 0.3333f;
		/*float[] kerX = {0, 0, 0, -coeff, 0, coeff, 0, 0, 0};
		float[] kerY = {0, -coeff, 0, 0, 0, 0, 0, coeff, 0};*/
		
		float[] ker = {-1, 0, 1};
		
//		float[] kerX = {-coeff, 0, coeff, -coeff, 0, coeff, -coeff, 0, coeff};
		ByteConvolve convolverX = new ByteConvolve(ker, 3, 1);
		gradXimage = convolverX.convolve(image);
		
//		float[] kerY = {-coeff, -coeff, -coeff, 0, 0, 0, coeff, coeff, coeff};
		ByteConvolve convolverY = new ByteConvolve(ker, 1, 3);
		gradYimage = convolverY.convolve(image);
		
		ByteImage[] result = {
				gradXimage,
				gradYimage,
		};
		
		return result;
	}
}
