package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Soft blur filter.
 * </p>
 *
 * <p>
 * This filter blurs an image by mixing a little bit of neighbouring pixels, to a lesser extent of the mean filter
 * Implimented by using a convoultion.
 * </p>
 *
 * @author Emma
 * @version 1.0
 */
public class SoftBlurFilter implements ImageOperation, java.io.Serializable{

    /**
     * <p>
     * Create a Soft blur filter operation
     * </p>
     */
    SoftBlurFilter() {

    }

    /**
     * <p>
     * Applys soft blur filter to an image
     * </p>
     *
     * <p>
     * The soft blur filter is applied via convolution
     * </p>
     *
     * @param input The image to apply the soft blur filter to.
     */
    public BufferedImage apply (BufferedImage input) {
        // Values for the kernal
        float [] array = {0, 1/8.0f, 0, 1/8.0f, 1/2.0f, 1/8.0f, 0, 1/8.0f, 0};
        // Make filter from 3x3 array
        Kernel kernel = new Kernel(3, 3, array);
        // Apply as a convolution
        ConvolveOp convOp = new ConvolveOp(kernel);

        //Create an instance of the class that creates image with border
        FilterBorder borderedImage = new FilterBorder(input, 1);

        //Applies convolution to bordered image
        BufferedImage output = convOp.filter(borderedImage.applyBorder(), null);

        //Crops image back to original size
        output = output.getSubimage(1, 1, input.getWidth(), input.getHeight());

        return output;
    }
}
