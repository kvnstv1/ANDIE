package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to invert an image's colours.
 * </p>
 *
 * <p>
 * The images produced by this operation are still technically colour images,
 * in that they have red, green, and blue values, but each pixel has the inverse of its value.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 *
 * @author Steven Mills, refactored by Yuxing and Kevin
 * @version 1.0
 */
public class ImageInvert implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Create a new ImageInvert operation.
     * </p>
     */
    ImageInvert() {

    }

    /**
     * <p>
     * Apply image invert conversion to an image.
     * </p>
     * @param input The image to be inverted.
     * @return The resulting inverted image.
     */
    public BufferedImage apply(BufferedImage input) {

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {


                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);


                //a = 255-a;
                r = 255-r;
                g = 255-g;
                b = 255-b;

                argb = (a << 24) | (r << 16) | (g << 8) | b;
                input.setRGB(x, y, argb);

            }
        }

        return input;
    }

}
