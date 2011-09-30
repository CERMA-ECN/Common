package fr.ecn.common.core.image.filters;

import fr.ecn.common.core.image.ByteImage;

public class ByteConvolve {
	
	protected float[] kernel;
	protected int kernelWidth;
	protected int kernelHeight;
	
	protected int kernelCenterX;
	protected int kernelCenterY;

	/**
	 * @param kernel
	 * @param kernelWidth
	 * @param kernelHeight
	 */
	public ByteConvolve(float[] kernel, int kernelWidth, int kernelHeight) {
		super();
		this.kernel = kernel;
		this.kernelWidth = kernelWidth;
		this.kernelHeight = kernelHeight;
		
		//Compute kernel center
		this.kernelCenterX = (kernelWidth - 1)/2;
		this.kernelCenterY = (kernelHeight - 1)/2;
	}
	
	public ByteImage convolve(ByteImage imageInput) {
		
		int width  = imageInput.getWidth();
		int height = imageInput.getHeight();
		byte[] grayInput = imageInput.getData();
		
		ByteImage imageResult = new ByteImage(width, height);
		byte[] grayOutput = imageResult.getData();
		
		for (int i=0;i<width;i++) {
			for (int j=0;j<height;j++) {
				float value = 0;
				for (int k=0;k<kernelWidth;k++) {
					for (int l=0;l<kernelHeight;l++) {
						int x = i + k - kernelCenterX;
						if (x<0) {
							x = 0;
						}
						if (x>width-1) {
							x = width - 1;
						}
						
						int y = j + l - kernelCenterY;
						if (y<0) {
							y = 0;
						}
						if (y>height-1) {
							y = height - 1;
						}
						
						value += kernel[l*kernelWidth+k]*grayInput[y*width+x];
					}
				}
				grayOutput[j*width+i] = (byte) value;
			}
		}
		
		return imageResult;
	}

}
