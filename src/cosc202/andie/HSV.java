package cosc202.andie;

import java.awt.image.*;
import javax.swing.*;
import java.awt.Color;
import java.util.Arrays;

/**
 * <p>
 * ImageOperation to edit an image in HSV, making cool changes to colours in the image.
 * </p>
 *
 * <p>
 * The image will be rendered in HSV temporarily and then converted back to RGB when the user clicks OK.
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
public class HSV implements ImageOperation, java.io.Serializable {
    /**The value of the hue */
    float hue;
    /**The value of the saturation */
    float saturation;
    /**The value of the brightness */
    float brightness;

 /**
     * <p>
     * Create a new HSV operation.
     * </p>
     */
    HSV(float hu, float sa, float br) {
        this.hue = hu;
        this.saturation = sa;
        this.brightness = br;

    }

    /**
     * <p>
     * Edit an image in HSV mode
     * </p>
     *
     * <p>
     * The HSV scale is a more artist-oriented colour profile and makes trivial to make quick changes to the colours in an image. For 
     * ease-of-use with other methods in this project, the colour will be converted back to RGB in the end.
     * </p>
     *
     * @param input The image to be converted to greyscale
     * @return The resulting greyscale image.
     */
    public BufferedImage apply(BufferedImage input) {

        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);


        for (int y = 0; y < output.getHeight(); ++y) {
            for (int x = 0; x < output.getWidth(); ++x) {
                int argb = output.getRGB(x, y);
                /*
                 * >> sets shifted-in bits to match the sign (high order) bit
                 * >>> sets shifted-in bits to zero always
                 */
                int a = (argb & 0xFF000000) >>> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                float[] hsb = new float[3];
                hsb = Color.RGBtoHSB(r,g,b, hsb);

                if(hue!=0){
                    hsb[0] = hsb[0] + hsb[0]*hue;
                    if(hsb[0]>1){
                        hsb[0] = 1;
                    }
                    else if(hsb[0]<0){
                        hsb[0]=0;
                    }
                    }
                    if(saturation!=0){
                    hsb[1] = hsb[1] + hsb[1]*saturation;
                    if(hsb[1] < 0){
                        hsb[1] = 0;
                        }
                        else if(hsb[1]>1){
                            hsb[1] = 1; 
                    }
                    }
                    if(brightness!=0){
                    hsb[2] = hsb[2] + hsb[2]*brightness;
                    hsb[2] = Math.min(1.0f, hsb[2]);
                    hsb[2] = Math.max(0,hsb[2]);
                    }
                
                

                int col = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);

                output.setRGB(x, y, col);
            }
        }

        return output;

    }

    /**
     * <p>
     * Edit an image in HSV mode forthe preview pane
     * </p>
     *
     * <p>
     * The HSV scale is a more artist-oriented colour profile and makes trivial to make quick changes to the colours in an image. For 
     * ease-of-use with other methods in this project, the colour will be converted back to RGB in the end.
     * </p>
     *
     * @param input The image to be converted to greyscale
     * @param h the hue
     * @param s the saturation
     * @param v the Value
     * @return The resulting greyscale image.
     */
    public static BufferedImage applyToPreview(BufferedImage input, float h, float s, float v) {

        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);

        for (int y = 0; y < output.getHeight(); ++y) {
            for (int x = 0; x < output.getWidth(); ++x) {
                int argb = output.getRGB(x, y);
                /*
                 * >> sets shifted-in bits to match the sign (high order) bit
                 * >>> sets shifted-in bits to zero always
                 */
                int a = (argb & 0xFF000000) >>> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                float[] hsb = new float[3];
                hsb = Color.RGBtoHSB(r,g,b, hsb);
                if(h!=0){
                    hsb[0] = hsb[0] + hsb[0]*h;
                    if(hsb[0]>1){
                        hsb[0] = 1;
                    }
                    else if(hsb[0]<0){
                        hsb[0]=0;
                    }
                    }
                    if(s!=0){
                    hsb[1] = hsb[1] + hsb[1]*s;
                    if(hsb[1] < 0){
                        hsb[1] = 0;
                        }
                        else if(hsb[1]>1){
                            hsb[1] = 1; 
                    }
                    }
                    if(v!=0){
                    hsb[2] = hsb[2] + hsb[2]*v;
                    hsb[2] = Math.min(1.0f, hsb[2]);
                    hsb[2] = Math.max(0,hsb[2]);
                    }

                int col = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);

                output.setRGB(x, y, col);
            }
        }

        return output;

    }


}
