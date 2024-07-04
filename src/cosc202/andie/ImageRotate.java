package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.*;

/**
 * <p>
 * Image Rotation to rotate the images orientation
 * </p>
 *
 * <p>
 * Image rotation transforms the image orientation.
 * It does this by taking the input angle (int deg) and then converts this to radians to work out the new image width and height
 * It then rotates and draws it using Java 2D graphics and then renders it.
 * Finally it returns the new buffered image with the roation applied.
 *
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 *
 * @author Angus Lyall
 * @version 1.0
 */
public class ImageRotate implements ImageOperation, java.io.Serializable {
    /** the angle of rotation in deg given by the user */
    private double deg;




    ImageRotate(double deg){
        this.deg = deg;
    }
    /**
     * <p>
     * Create a new ImageRotate operation.
     * </p>
     */
    ImageRotate() {}

    /**
     * <p>
     * Apply image rotate conversion to an image.
     * </p>
     * @param input The image to be rotated.
     * @return The resulting rotated image.
     */
    public BufferedImage apply(BufferedImage input) {

        double rad = Math.toRadians(deg); // converts the input degress to radians
        int newWidth = (int) Math.floor(input.getWidth() * Math.abs(Math.cos(rad)) + input.getHeight() * Math.abs(Math.sin(rad)));
        int newHeight = (int) Math.floor(input.getWidth() * Math.abs(Math.sin(rad)) + input.getHeight() * Math.abs(Math.cos(rad)));
        // Image rotation Caculation from NGLN (https://math.stackexchange.com/users/17336/ngln), Rotating a rectangle, URL (version: 2017-04-13): https://math.stackexchange.com/q/71069


        BufferedImage transformedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB); // creates a new image with a new w,h to adjust for transfomration
        Graphics2D graphics = transformedImg.createGraphics(); // creates a graphics object to redraw the image
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION , RenderingHints.VALUE_INTERPOLATION_BILINEAR); // Image was becoming pixalated due to Interpolation so hot fix this makes it blur more then pixalate when the pixels don't fully match
        graphics.translate((newWidth - input.getWidth()) /2 , (newHeight - input.getHeight()) /2);
        graphics.rotate(rad, input.getWidth()/2 , input.getHeight()/2);
        graphics.drawRenderedImage(input, null); // draws the image onto the canvas
        graphics.dispose(); // closes the graphics good practics and I belive it helps to stop memory leaks



        return transformedImg; // returns the transformed image no point returning input
    }

}
