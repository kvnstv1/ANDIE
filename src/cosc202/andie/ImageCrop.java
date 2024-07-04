package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageCrop to crop an image based on the MouseSelection selection box bounds
 * </p>
 *
 * @author Emma
 * @version 2.0
 */
public class ImageCrop implements ImageOperation {
    private int x;
    private int y;
    private int width;
    private int height;

    /**
     * Constructs an ImageCrop object with the specified crop parameters.
     *
     * @param x      The x-coordinate of the top-left corner of the crop region.
     * @param y      The y-coordinate of the top-left corner of the crop region.
     * @param width  The width of the crop region.
     * @param height The height of the crop region.
     */
    public ImageCrop(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Performs the crop operation on the given BufferedImage.
     *
     * @param image The BufferedImage to be cropped.
     * @return The cropped BufferedImage.
     */
    @Override
    public BufferedImage apply(BufferedImage image) {
        return image.getSubimage(x, y, width, height);
    }

}
