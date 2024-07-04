package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to rotate an image by 4 common angles.
 * </p>
 *
 * <p>
 * Rotates the image. Not rocket science. But it took ages to get it to work anyway.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class RotateImageStrictAction implements ImageOperation, java.io.Serializable {
    /** the angle of rotation in deg given by the user */
    private int deg;




/**
 * Créer un nouveau Rotate Image operation avec la degré
 * @param deg the input degree
 */
    RotateImageStrictAction(double deg){
        this.deg = (int)deg;
    }
    /**
     * <p>
     * Create a new Rotate Image operation.
     * </p>
     */
    RotateImageStrictAction() {}

    /**
     * <p>
     * Rotates the image.
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

        graphics.setPaint(new Color(233,233,233));
        graphics.fillRect(0,0,transformedImg.getWidth(), transformedImg.getHeight());

        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION , RenderingHints.VALUE_INTERPOLATION_BILINEAR); // Image was becoming pixalated due to Interpolation so hot fix this makes it blur more then pixalate when the pixels don't fully match
        //if(attempted==1){
        //graphics.translate((newWidth - input.getWidth())/2 , (newHeight - input.getHeight())/2);
        //graphics.translate((radius*Math.cos(Math.toRadians(countDegrees))-radius), radius - (radius*Math.sin(Math.toRadians(countDegrees))));
        graphics.translate((newWidth - input.getWidth()) /2 , (newHeight - input.getHeight()) /2);
        //}
        graphics.rotate(rad, input.getWidth()/2 , input.getHeight()/2);

        graphics.drawRenderedImage(input, null); // draws the image onto the canvas
        //System.out.println(transformedImg.getTileGridXOffset()+ ", "+transformedImg.getTileGridYOffset());
        //graphics.translate(-(newWidth - input.getWidth())/2 , -(newHeight - input.getHeight())/2);
        //System.out.println(transformedImg.getMinX() + "," + transformedImg.getMinY());

        graphics.dispose(); // closes the graphics good practics and I belive it helps to stop memory leaks



        return transformedImg; // returns the transformed image no point returning input
    }

}
