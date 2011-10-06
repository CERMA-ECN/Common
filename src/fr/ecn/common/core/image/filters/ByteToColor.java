package fr.ecn.common.core.image.filters;

import fr.ecn.common.core.image.ByteImage;
import fr.ecn.common.core.image.ColorImage;
import fr.ecn.common.core.image.utils.Color;

/**
 * @author jerome
 *
 */
public class ByteToColor {
	
	public ColorImage convert(ByteImage image) {
        ColorImage rgb = new ColorImage(image.getWidth(), image.getHeight());
        byte[] grayData = image.getData();
        int[] rgbData = rgb.getData();
        
        for (int i=0; i<image.getWidth() * image.getHeight(); i++) {
            /* Convert from signed byte value to unsigned byte for storage
             * in the RGB image.
             */
            int grayUnsigned = (grayData[i]) - Byte.MIN_VALUE;
            /* Create ARGB word */
			rgbData[i] = Color.rgb(grayUnsigned, grayUnsigned, grayUnsigned);
        }
        
        return rgb;
	}

}
