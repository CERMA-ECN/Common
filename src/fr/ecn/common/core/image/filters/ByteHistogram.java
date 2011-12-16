package fr.ecn.common.core.image.filters;

import fr.ecn.common.core.image.ByteImage;

public class ByteHistogram {

	/**
	 * @param image
	 * @return the histogram of the given image
	 */
	public int[] histogram(ByteImage image) {
		int[] result = new int[256];
		for (int i = 0; i < 256; i++) {
			result[i] = 0;
		}
		byte[] data = image.getData();
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				result[data[i * image.getWidth() + j] - Byte.MIN_VALUE]++;
			}
		}
		return result;
	}
	
}
