package cosc202.andie;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * 
 * 
 * <p>
 * ImageOperation to apply a image resize.
 * </p>
 * 
 * <p>
 * The image resize operation takes and image and resize
 * on a regular image with the new height and width
 * </p>
 * 
 
 * 
 *
 * @author YUXING ZHANG 
 * @version 1.0
 */
 

public class ImageResize implements ImageOperation, java.io.Serializable {
  /** the input width to resize too */
  private int width;
  /** the input height to resize too */
  private int height;

  /**
   *  Construct an ImageResize with no parameters.
   */
  public ImageResize() {
    // Nothing here!
  };

  /**
   *  Construct an ImageResize with the given parameters.
   * @param height the height to be resized to.
   * @param width the width to be resized to.
   */
  public ImageResize(int height, int width) {

    this.height = height;
    this.width = width;

  }

  /**
   *  Apply a resize to the given image.
   *
   *  @param input The image to resize.
   *  @return The resulting image.
   */
  @Override
  public BufferedImage apply(BufferedImage input) {

    int oriWidth = input.getWidth();
    int oriHeight = input.getHeight();
    Image inp = (Image) input;

    BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    Image scaled;
    // = inp.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);

    if (oriWidth > width && oriHeight > height) {
      scaled = inp.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
    } else if (oriWidth < width && oriHeight < height) {
      scaled = inp.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    } else {
      scaled = inp.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
    }

    resultImage.getGraphics().drawImage(scaled, 0, 0, null);

    // Graphics2D g2d = resultImage.createGraphics();
    // g2d.drawImage(temp, 0, 0, null);
    // g2d.dispose();

    return resultImage;

  }

  /**A method that resizes an image to fit a previewPanel.
   * Modified yuxing's code slightly.
   * @param input the BufferedImage
   * @return the resultant bufferedImage.
   */
  public static BufferedImage applyToPreview(BufferedImage input) {
    int width=0; 
    int height=0;
    int oriWidth = input.getWidth();
    int oriHeight = input.getHeight();
    if(oriWidth < oriHeight*1.1){
      width = 300;
      height = 300;
    }
    else if(oriWidth>oriHeight){
       height = 300;
       width = 500;
    }
    else{
      height = 500;
      width = 300;
    }
    Image inp = (Image) input;

    BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    Image scaled;
    // = inp.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);

    if (oriWidth > width && oriHeight > height) {
      scaled = inp.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
    } else if (oriWidth < width && oriHeight < height) {
      scaled = inp.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    } else {
      scaled = inp.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
    }

    resultImage.getGraphics().drawImage(scaled, 0, 0, null);

    // Graphics2D g2d = resultImage.createGraphics();
    // g2d.drawImage(temp, 0, 0, null);
    // g2d.dispose();

    return resultImage;

  }
}