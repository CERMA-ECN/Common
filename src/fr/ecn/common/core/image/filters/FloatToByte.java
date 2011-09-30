package fr.ecn.common.core.image.filters;

import fr.ecn.common.core.image.ByteImage;
import fr.ecn.common.core.image.FloatImage;

/**
 * @author jerome
 *
 */
public class FloatToByte {
	
	public ByteImage convert(FloatImage image) {
        ByteImage rgb = new ByteImage(image.getWidth(), image.getHeight());
        float[] sourceData = image.getData();
        byte[] resultData = rgb.getData();
        
        //Finding min and max
        float min = Float.POSITIVE_INFINITY;
        float max = Float.NEGATIVE_INFINITY;
        
        for (int i=0; i<image.getWidth() * image.getHeight(); i++) {
			if (sourceData[i] > max) {
				max = sourceData[i];
			}
			if (sourceData[i] < min) {
				min = sourceData[i];
			}
        }
        
        for (int i=0; i<image.getWidth() * image.getHeight(); i++) {
        	float value = sourceData[i];
        	
        	value -= min;
        	value *= 255f/(max-min);
        	
        	if (value < 0) {
        		value = 0;
        	}
        	if (value > 255) {
        		value = 255;
        	}
        	
			resultData[i] = (byte) value;
        }
        
        return rgb;
	}

}
