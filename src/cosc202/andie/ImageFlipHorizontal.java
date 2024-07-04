package cosc202.andie;

import java.awt.image.*;

/**
 * ImageFlipHorizontal implements ImageOperation interface and provides
 * functionality to flip an image horizontally.
 *
 * The flip operation flips the image along a horizontal axis, specifically the
 * halfway point of the image.
 * 
 * @author Kevin Steve Sathyanath
 * @version 1.0
 */
public class ImageFlipHorizontal implements ImageOperation, java.io.Serializable {

    /*
     * Create a new ImageFlipHorizontal operation
     */
    ImageFlipHorizontal() {

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

        for (int y = 0; y < input.getHeight(); y++) { // Go along y-axis

            for (int x = 0; x < input.getWidth() / 2; x++) { // Go along x-axis to halfway point.

                int argb = input.getRGB(x, y);
                int argbComplement = input.getRGB(input.getWidth() - (x + 1), y);
                input.setRGB(input.getWidth() - (x + 1), y, argb);
                input.setRGB(x, y, argbComplement);

            }
        }

        return input;

    }

}
