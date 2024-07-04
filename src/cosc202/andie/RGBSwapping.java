package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * This class is designed for swapping the RGB channels' position
 * based on the user's taste.
 * It's quite easy to understand, changed the code based on the greyscale class.
 *
 * @author The Greatest Eden
 * @version 1.0
 * date 10 Mar 2024
 */

public class RGBSwapping implements ImageOperation, java.io.Serializable{

    /** The red component of the original color. */
    private int R = 0;
    /** The green component of the original color. */
    private int G = 0;
    /** The blue component of the original color. */
    private int B = 0;
    /** The red component of the new color. */
    private int newR = 0;
    /** The green component of the new color. */
    private int newG = 0;
    /** The blue component of the new color. */
    private int newB = 0;

    /**
     * <p>
     * Create a new RGBSwapping operation, default constructor.
     * </p>
     */
    RGBSwapping(int R, int G, int B) {
        this.R = R;
        this.G = G;
        this.B = B;

    }

    /**
     * The main apply function.
     * @param input The image to be converted to an image with different order of RGB channels.
     * @return The resulting RGBSwapped image.
     */
    public BufferedImage apply(BufferedImage input) {

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                if (R == 1) newR = r;
                else if (R == 2) newG = r;
                else if (R == 3) newB = r;

                if (G == 1) newR = g;
                else if (G == 2) newG = g;
                else if (G == 3) newB = g;

                if (B == 1) newR = b;
                else if (B == 2) newG = b;
                else if (B == 3) newB = b;

                argb = (a << 24) | (newR << 16) | (newG << 8) | newB;
                input.setRGB(x, y, argb);
            }
        }
        return input;
    }

}
