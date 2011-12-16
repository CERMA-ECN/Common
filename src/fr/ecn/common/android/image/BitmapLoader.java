package fr.ecn.common.android.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapLoader {
	
	public static final int maxDim = 1000;
	
	/**
	 * Load a bitmap and resize it if its height or width is greater than maxDim
	 * 
	 * @param path
	 * @param maxDim
	 * @return an object that contain the resized bitmap and the scale factor
	 */
	public static ResizedBitmap loadResized(String path, int maxDim) {
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		
		int height = bitmap.getHeight();
		int width  = bitmap.getWidth();
		
		if (height <= maxDim && width <= maxDim) {
			return new ResizedBitmap(bitmap, 1);
		} else {
			float scale = Math.min((float)maxDim / height, (float)maxDim / width);
			
			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			
			Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			
			//Recycle bitmap to free memory
			bitmap.recycle();
			
			return new ResizedBitmap(resizedBitmap, scale);
		}
	}
	
	public static class ResizedBitmap {
		public Bitmap bitmap;
		public float scale;
		/**
		 * @param bitmap
		 * @param scale
		 */
		public ResizedBitmap(Bitmap bitmap, float scale) {
			super();
			this.bitmap = bitmap;
			this.scale = scale;
		}
	}
	
}
