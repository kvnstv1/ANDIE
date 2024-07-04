package cosc202.andie;

import java.awt.image.*;

/**
 * ImageFlipVertical implements ImageOperation interface and provides
 * functionality to flip an image vertically.
 *
 * The flip operation flips the image along a vertical axis, specifically the
 * halfway point of the image.
 * 
 * @author Kevin Steve Sathyanath
 * @version 1.0
 */
public class ImageFlipVertical implements ImageOperation, java.io.Serializable {

    /*
     * Create a new ImageFlipHorizontal operation
     */
    ImageFlipVertical() {

    }

    /**
     * <p>
     * Apply horizontal flip to an image.
     * </p>
     *
     * <p>
     * Flips the image along a vertical axis along the halfway point of
     * the image.
     * </p>
     *
     * @param input The image to be flipped
     * @return The resulting greyscale image.
     */
    public BufferedImage apply(BufferedImage input) {

        for (int x = 0; x < input.getWidth(); x++) { // Go along y-axis

            for (int y = 0; y < input.getHeight() / 2; y++) { // Go along x-axis to halfway point.

                int argb = input.getRGB(x, y);
                int argbComplement = input.getRGB(x, input.getHeight() - (y + 1));
                input.setRGB(x, input.getHeight() - (y + 1), argb);
                input.setRGB(x, y, argbComplement);

            }
        }

        return input;

    }

}
