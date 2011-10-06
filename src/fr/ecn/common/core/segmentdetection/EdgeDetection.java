package fr.ecn.common.core.segmentdetection;

import java.util.LinkedList;
import java.util.List;

import fr.ecn.common.core.image.ByteImage;
import fr.ecn.common.core.image.FloatImage;
import fr.ecn.common.core.image.Image;
import fr.ecn.common.core.image.utils.ImageConvertor;

public class EdgeDetection {
	
	private List<Edgel> edgels = new LinkedList<Edgel>();

	public EdgeDetection(Image image, float threshold) {
		//Convert image to ByteImage
		ByteImage byteImage = ImageConvertor.toByte(image);
		
		//Process CannyDeriche filter
		FloatImage imageCanny = new CannyDericheFilter().processDeriche(byteImage, 0.5f);
		
		//Process gradients
		//TODO: Use gradients from cannyDeriche
		ByteImage[] gradients = Utils.getGradients(byteImage);
		
		for (int i = 0; i < imageCanny.getWidth(); i++) {
			for (int j = 0; j < imageCanny.getHeight(); j++) {
				if (imageCanny.getPixel(i, j) > threshold) {
					double vX = gradients[0].getPixel(i, j);
					double vY = gradients[1].getPixel(i, j);
					
					double theta = (vX == 0 ? -Math.PI/2 : Math.atan(vY/vX));
					
					this.edgels.add(new Edgel(i, j, theta));
				}
			}
		}
	}

	/**
	 * @return the edgels
	 */
	public List<Edgel> getEdgels() {
		return edgels;
	}
	
}
