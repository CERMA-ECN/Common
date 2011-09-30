package fr.ecn.common.core.image.filters;

import fr.ecn.common.core.image.ByteImage;
import fr.ecn.common.core.image.ColorImage;
import fr.ecn.common.core.image.utils.Color;

public class ColorToByte {
	
	public ByteImage convert(ColorImage image) {
        int[] rgbData = image.getData();
        ByteImage gray = new ByteImage(image.getWidth(), image.getHeight());
        byte[] grayData = gray.getData();
        
        for (int i=0; i<image.getWidth() * image.getHeight(); i++) {
            /* get individual r, g, and b values, unmasking them from the
             * ARGB word. 
             */
            int r = Color.red(rgbData[i]);
            int g = Color.green(rgbData[i]);
            int b = Color.blue(rgbData[i]);
            /* average the values to get the grayvalue
             */
            grayData[i] = (byte)((r + g + b) / 3);
        }
        
        return gray;
	}
}
