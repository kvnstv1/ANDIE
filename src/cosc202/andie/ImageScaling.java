package cosc202.andie;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * ImageScaling class implements the ImageOperation interface and provides
 * functionality to scale an image by a given percentage.
 */
public class ImageScaling implements ImageOperation, java.io.Serializable {
  /** The percent the the image is scaled by which is given by the user */
  private double scalePercentage;
  // private int heightPer;

  /**
   * <p>
   * Constructs a new ImageScaling object.
   * </p>
   *
   * @param scalePercentage The percentage to scale the image by.
   */
  public ImageScaling(double scalePercentage) {
    this.scalePercentage = scalePercentage;
    // this.widthPer = widthPer;

  }

  /**
   * <p>
   * Scales the image by the given percentage.
   * </p>
   *
   * @param input The image to scale.
   * @return The scaled image.
   */
  public BufferedImage apply(BufferedImage input) {

    int oriWidth = input.getWidth();
    int oriHeight = input.getHeight();

    Image inp = (Image) input;

    int height = (int) (oriHeight * scalePercentage);
    int width = (int) (oriWidth * scalePercentage);

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


  /**A static method that scales an object for the previewPanel.
   * This code is compied from Yuxing's work above and modified slightly
   * @since 07/05/2024
   * @param input buffered image
   * @return bufferedImage that is properly scaled
   **/
  public static BufferedImage applyToPreview(BufferedImage input) {

    int oriWidth = input.getWidth();
    int oriHeight = input.getHeight();
    double scaleX = 500/oriWidth;
    double scaleY = 300/oriHeight;
    //double scale = scaleX<scaleY?scaleX:scaleY;
    double scale =0.5;

    if(oriWidth<500 || oriHeight<300){
      scale = 1;
    }

    Image inp = (Image) input;

    int height = (int) (oriHeight * scale);
    int width = (int) (oriWidth * scale);

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
