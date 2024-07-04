package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Sharpen (enhancing) filter.
 * </p>
 *
 * <p>
 * This filter is the reverse of a blur filter, by enhancing the differences between neighbouring values.
 * Implimented by using a convoultion.
 * </p>
 * @author Emma
 * @version 1.0
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable{

    /**
     * <p>
     * Create a Sharpen filter operation
     * </p>
     */
    SharpenFilter() {}

    /**
     * <p>
     * Applies sharpen filter to an image
     * </p>
     *
     * <p>
     * The sharpen filter is applied via convolution
     * </p>
     *
     * @param input The image to apply the Sharpen filter to.
     */
    public BufferedImage apply(BufferedImage input) {
        //Values for Kernal in an array
        float [] array = {0, (-(1/2.0f)), 0, (-(1/2.0f)), 3, (-(1/2.0f)), 0, (-(1/2.0f)), 0};
        //Make filter from array
        Kernel kernel = new Kernel(3, 3, array);
        //Applying as a convolution
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
