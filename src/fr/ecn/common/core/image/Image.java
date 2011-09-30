/**
 * 
 */
package fr.ecn.common.core.image;

/**
 * @author jerome
 *
 */
public abstract class Image {
	
	/**
	 * The image height.
	 */
	private final int height;
	/**
	 * The image width.
	 */
	private final int width;

	/**
	 * Creates a new instance of Image
	 * 
	 * @param width the image width
	 * @param height the image height
	 */
	public Image(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Returns the image height
	 * 
	 * @return the image height (rows)
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Returns the image width
	 * 
	 * @return the image width (columns)
	 */
	public int getWidth() {
		return this.width;
	}
    
    /**
     * Return a string describing the image.
     * @return the string.
     */
    public String toString()
    {
        return super.toString() + " (" + getWidth() + "x" + getHeight() + ")";
    }
	
}
