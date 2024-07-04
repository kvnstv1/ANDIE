package cosc202.andie;

import java.awt.image.*;


/**
 * <p>
 * ImageOperation to convert an image from colour to sepia.
 * </p>
 *
 * <p>
 * The images produced by this operation look retro, if you're into that stuff.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author Kevin Steve Sathyanath
 * @version 1.0
 */
public class Sepia implements ImageOperation, java.io.Serializable { 

     /**
     * <p>
     * Create a new CovertToSepia operation.
     * </p>
     */
    Sepia() {

    }

    /**
     * <p>
     * Apply sepia conversion to an image.
     * </p>
     *
     * <p>
     * This code canibalizes code from convertyToGrey. It just manipulates the result of bitshift operation differently.
     * </p>
     *
     * @param input The image to be converted to sepia
     * @return The resulting sepia image.
     */
    public BufferedImage apply(BufferedImage input){

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

                int newRed = (int) (red * 0.393 + green * 0.769 + blue * 0.189);
                int newGreen = (int) (red * 0.349 + green * 0.686 + blue * 0.168);
                int newBlue = (int) (red * 0.272 + green * 0.534 + blue * 0.131);
                //Numbers provided by Copilot. I may experiment with them later. 

                //Making sure the numbers don't cross 255 or 0. 
                newRed = Math.min(255, Math.max(0, newRed));
                newGreen = Math.min(255, Math.max(0, newGreen));
                newBlue = Math.min(255, Math.max(0, newBlue));
                
                argb = (a << 24) | (newRed << 16) | (newGreen << 8) | newBlue;
                input.setRGB(x, y, argb);



            }
        }
        return input;
                
    }
                
}
