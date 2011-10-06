package fr.ecn.common.core.image.utils;

/**
 * A class that contains useful statics methods to manipulate colors stored as int
 * 
 * @author jerome
 *
 */
public class Color {
	
	public static final int BLACK = 0xFF000000;
	public static final int DKGRAY = 0xFF444444;
	public static final int GRAY = 0xFF888888;
	public static final int LTGRAY = 0xFFCCCCCC;
	public static final int WHITE = 0xFFFFFFFF;
	public static final int RED = 0xFFFF0000;
	public static final int GREEN = 0xFF00FF00;
	public static final int BLUE = 0xFF0000FF;
	public static final int YELLOW = 0xFFFFFF00;
	public static final int CYAN = 0xFF00FFFF;
	public static final int MAGENTA = 0xFFFF00FF;
	public static final int TRANSPARENT = 0;
	
	/**
	* Return a color-int from red, green, blue components.
	* The alpha component is implicity 255 (fully opaque).
	* These component values should be [0..255], but there is no
	* range check performed, so if they are out of range, the
	* returned color is undefined.
	* @param red Red component [0..255] of the color
	* @param green Green component [0..255] of the color
	* @param blue Blue component [0..255] of the color
	*/
	public static int rgb(int red, int green, int blue) {
		return (0xFF << 24) | (red << 16) | (green << 8) | blue;
	}

	/**
	 * Return the red component of a color int. This is the same as saying
	 * (color >> 16) & 0xFF
	 */
	public static int red(int color) {
		return toSignedByte((byte) ((color >> 16) & 0xFF));
	}

	/**
	 * Return the green component of a color int. This is the same as saying
	 * (color >> 8) & 0xFF
	 */
	public static int green(int color) {
		return toSignedByte((byte) ((color >> 8) & 0xFF));
	}

	/**
	 * Return the blue component of a color int. This is the same as saying
	 * color & 0xFF
	 */
	public static int blue(int color) {
		return toSignedByte((byte) (color & 0xFF));
	}
	
    /**
     * Converts from an unsigned bit field (as stored in an ARGB word
     * to a signed byte value (that we can do computation on).
     * @return the signed byte value
     * @param b the unsigned byte value.
     */
    public static byte toSignedByte(byte b) {
        return (byte) (b + Byte.MIN_VALUE);
    }
	
}
