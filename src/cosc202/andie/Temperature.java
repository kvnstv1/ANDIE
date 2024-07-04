package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to change the 'temperature' of an image. You'll know it when you see it.
 * </p>
 *
 * <p>
 * The images produced by this operation are not hotter or colder than they were at the start, just a different colour.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class Temperature implements ImageOperation, java.io.Serializable {

    /**The degree to which temperature is changed. */
    private double dial;
        /**
         * <p>
         * Create a new Temperature operation.
         * </p>
         */
        Temperature(double dial) {
            this.dial = dial;
        }

        /**
     * <p>
     * Apply temperature conversion to an image.
     * </p>
     *
     * <p>
     * This is just an apply method like all the others. 
     * </p>
     *
     * @param input The image to be converted to greyscale
     * @return The resulting greyscale image.
     */
    public BufferedImage apply(BufferedImage input) {

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                /*
                 * >> sets shifted-in bits to match the sign (high order) bit
                 * >>> sets shifted-in bits to zero always
                 */
                int a = (argb & 0xFF000000) >>> 24;
                int red = (argb & 0x00FF0000) >> 16;
                int green = (argb & 0x0000FF00) >> 8;
                int blue = (argb & 0x000000FF);

                // Adjust red and blue channels
                red = (int) (red * dial);
                blue = (int) (blue / dial);

                // Ensure values are within valid range (0-255)
                red = Math.min(255, Math.max(0, red));
                blue = Math.min(255, Math.max(0, blue));

                argb = (a << 24) | (red << 16) | (green << 8) | blue;
                input.setRGB(x, y, argb);
            }
        }
        return input;
    }


    /**The same method as above, but static and with the dial given as a parameter for the preview panel.
     * @author Kevin Steve Sathyanath
     * @param input the BufferedImage
     * @param di the dial value
     * @return The resultant image.
     */
    public static BufferedImage applyToPreview(BufferedImage input, double di) {

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                /*
                 * >> sets shifted-in bits to match the sign (high order) bit
                 * >>> sets shifted-in bits to zero always
                 */
                int a = (argb & 0xFF000000) >>> 24;
                int red = (argb & 0x00FF0000) >> 16;
                int green = (argb & 0x0000FF00) >> 8;
                int blue = (argb & 0x000000FF);

                // Adjust red and blue channels
                red = (int) (red * di);
                blue = (int) (blue / di);

                // Ensure values are within valid range (0-255)
                red = Math.min(255, Math.max(0, red));
                blue = Math.min(255, Math.max(0, blue));

                argb = (a << 24) | (red << 16) | (green << 8) | blue;
                input.setRGB(x, y, argb);
            }
        }
        return input;
    }


    
}
