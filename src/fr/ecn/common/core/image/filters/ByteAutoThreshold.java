package fr.ecn.common.core.image.filters;

import fr.ecn.common.core.image.ByteImage;

public class ByteAutoThreshold {
	
	protected boolean bWithin;

	/**
	 * @param bWithin
	 */
	public ByteAutoThreshold(boolean bWithin) {
		super();
		this.bWithin = bWithin;
	}

	public ByteImage threshold(ByteImage imageInput) {
		int threshold = this.getAutoThreshold(imageInput);

		int cWidth = imageInput.getWidth();
		int cHeight = imageInput.getHeight();
		byte[] grayInput = imageInput.getData();

		ByteImage imageResult = new ByteImage(cWidth, cHeight);
		byte[] grayOutput = imageResult.getData();
		
		for (int i = 0; i < grayInput.length; i++) {
			grayOutput[i] = (((grayInput[i]) < threshold) == this.bWithin) ? Byte.MAX_VALUE
					: Byte.MIN_VALUE;
		}

		return imageResult;
	}

	/**
	 * Returns a pixel value (threshold) that can be used to divide the image
	 * into objects and background.
	 * 
	 * @param image
	 * @return
	 */
    private int getAutoThreshold(ByteImage image) {
    	int[] histogram = new ByteHistogram().histogram(image);
        int level;
        int maxValue = histogram.length - 1;
        double result, sum1, sum2, sum3, sum4;
        
		//int count0 = histogram[0];
        histogram[0] = 0; //set to zero so erased areas aren't included
		//int countMax = histogram[maxValue];
        histogram[maxValue] = 0;
        int min = 0;
        while ((histogram[min]==0) && (min<maxValue))
            min++;
        int max = maxValue;
        while ((histogram[max]==0) && (max>0))
            max--;
        if (min>=max) {
			//histogram[0]= count0; histogram[maxValue]=countMax;
            level = histogram.length/2;
            return level;
        }
        
        int movingIndex = min;
        do {
            sum1=sum2=sum3=sum4=0.0;
            for (int i=min; i<=movingIndex; i++) {
                sum1 += (double)i*histogram[i];
                sum2 += histogram[i];
            }
            for (int i=(movingIndex+1); i<=max; i++) {
                sum3 += (double)i*histogram[i];
                sum4 += histogram[i];
            }           
            result = (sum1/sum2 + sum3/sum4)/2.0;
            movingIndex++;
        } while ((movingIndex+1)<=result && movingIndex<max-1);
        
		//histogram[0]= count0; histogram[maxValue]=countMax;
        level = (int)Math.round(result);
        return level;
    }
	
}
