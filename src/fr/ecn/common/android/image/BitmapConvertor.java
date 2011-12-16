package fr.ecn.common.android.image;

import android.graphics.Bitmap;

import fr.ecn.common.core.image.ColorImage;

/**
 * @author jerome
 *
 * A class that contains methods to get an image from a bitmap and a bitmap from an image
 */
public class BitmapConvertor {

	public static ColorImage bitmapToImage(Bitmap bitmap) {
        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();
        
        ColorImage image = new ColorImage(width, height);
        bitmap.getPixels(image.getData(), 0, width, 0, 0, width, height);
        
        return image;
	}
	
	public static Bitmap imageToBitmap(ColorImage image) {
		return Bitmap.createBitmap(image.getData(), image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
	}
	
}
