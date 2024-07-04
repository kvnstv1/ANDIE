package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Mean (simple blur) filter.
 * </p>
 *
 * <p>
 * A Mean filter blurs an image by replacing each pixel by the average of the
 * pixels in a surrounding neighbourhood, and can be implemented by a convolution.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 *
 * @see java.awt.image.ConvolveOp
 * @author Steven Mills
 * @version 1.0
 */
public class MeanFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Mean filter with the given size.
     * </p>
     *
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     *
     * @param radius The radius of the newly constructed MeanFilter
     */
    MeanFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Mean filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Mean filter has radius 1.
     * </p>
     *
     * @see MeanFilter(int)
     */
    MeanFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Mean filter to an image.
     * </p>
     *
     * <p>
     * As with many filters, the Mean filter is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.
     * Larger radii lead to stronger blurring.
     * </p>
     *
     * @param input The image to apply the Mean filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        if(radius == 0){
            return input;
        }
        int size = (2*radius+1) * (2*radius+1);
        float [] array = new float[size];
        Arrays.fill(array, 1.0f/size);

        Kernel kernel = new Kernel(2*radius+1, 2*radius+1, array);
        ConvolveOp convOp = new ConvolveOp(kernel);

        //Create an instance of the class that creates image with border
        FilterBorder borderedImage = new FilterBorder(input, radius);

        //Applies convolution to bordered image
        BufferedImage output = convOp.filter(borderedImage.applyBorder(), null);

        //Crops image back to original size
        output = output.getSubimage(radius, radius, input.getWidth(), input.getHeight());

        return output;
    }


    /**A method that does the same as the one above, but is used for preview panels only.
     * @author Emma Boult
     * @param input The incoming image
     * @param rad The radius
     * @return The output image
     */
    public static BufferedImage applyToPreview(BufferedImage input, int rad) {
        if(rad == 0){
            return input;
        }
        int size = (2*rad+1) * (2*rad+1);
        float [] array = new float[size];
        Arrays.fill(array, 1.0f/size);

        Kernel kernel = new Kernel(2*rad+1, 2*rad+1, array);
        ConvolveOp convOp = new ConvolveOp(kernel);

        //Create an instance of the class that creates image with border
        FilterBorder borderedImage = new FilterBorder(input, rad);

        //Applies convolution to bordered image
        BufferedImage output = convOp.filter(borderedImage.applyBorder(), null);

        //Crops image back to original size
        output = output.getSubimage(rad, rad, input.getWidth(), input.getHeight());

        return output;
    }


}
