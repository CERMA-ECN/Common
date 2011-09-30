/**
 * 
 */
package fr.ecn.common.core.image;

import fr.ecn.common.core.image.utils.Color;

/**
 * @author jerome
 * 
 */
public class ColorImage extends Image {

	/**
	 * A pointer to the image data
	 */
	private final int[] pixels;

	/**
	 * Creates a new instance of RgbImage
	 * 
	 * @param cWidth the image width
	 * @param cHeight the image height
	 */
	public ColorImage(int cWidth, int cHeight) {
		super(cWidth, cHeight);
		this.pixels = new int[getWidth() * getHeight()];
	}

	public ColorImage(int cWidth, int cHeight, int[] rnData) {
		super(cWidth, cHeight);
		this.pixels = rnData;
	}

	/**
	 * Creates a new instance of RgbImage, assigning a constant value
	 * 
	 * @param cWidth the image width
	 * @param cHeight the image height
	 * @param bR the red color value to be assigned.
	 * @param bG the green color value to be assigned.
	 * @param bB the blue color value to be assigned.
	 */
	public ColorImage(int cWidth, int cHeight, byte bR, byte bG, byte bB) {
		this(cWidth, cHeight, Color.rgb(bR, bG, bB));
	}

	/**
	 * Creates a new instance of RgbImage, assigning a constant value
	 * 
	 * @param cWidth the image width
	 * @param cHeight the image height
	 * @param nRgb the packed RGB value to assign
	 */
	public ColorImage(int cWidth, int cHeight, int nRgb) {
		super(cWidth, cHeight);
		this.pixels = new int[getWidth() * getHeight()];
		for (int i = 0; i < this.getWidth() * this.getHeight(); i++) {
			this.pixels[i] = nRgb;
		}
	}

	/**
	 * Creates a shallow copy of this image
	 * 
	 * @return the image copy.
	 */
	public Object clone() {
		ColorImage image = new ColorImage(getWidth(), getHeight());
		System.arraycopy(this.getData(), 0, image.getData(), 0, getWidth() * getHeight());
		return image;
	}

	/**
	 * Get a pointer to the image data.
	 * 
	 * @return the data pointer.
	 */
	public int[] getData() {
		return this.pixels;
	}
    
    public int getPixel(int x, int y) {
    	return this.pixels[y*getWidth()+x];
    }
    
    public void setPixel(int x, int y, int value) {
    	this.pixels[y*getWidth()+x] = value;
    }

}
