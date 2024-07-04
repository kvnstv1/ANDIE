package cosc202.andie;

import java.awt.image.*;


/**
 * <p>
 * This class implements the ImageOperation interface and applies a Sobel filter to a BufferedImage.
 * </p>
 *
 * <p>
 *   A Sobel filter is apllied by creating a kernal with one side being positive and the other being negitive
 *   The kernal is made up of the values 0.5, 1, 0.5 whichs gives the pressed in effect and the other side having the contrast of the pulled out effect

 * </p>
 *
 *
 * @see java.awt.image.ConvolveOp
 * @see ImageOperation
 * @see BufferedImage
 * @author Angus Lyall
 * @version 1.0
*/
public class SobelFilter implements ImageOperation, java.io.Serializable {
    /**
     * The Direction of the sobel filter.
    */
    private boolean dir;


    /**
     * Constructor that sets the direction of the sobel filter.
     * @param direction of the sobel
    */

    SobelFilter(boolean dir){
        this.dir = dir;
    }
    /**
     * Default constructor that creates a sobel filter with a direction set to vertical
    */
    SobelFilter() {
        this(false);
    }
    /**
     * Applies the sobel filter to Buffered Image
     *
     * @param input the BufferedImage to apply the filter to
     * @return a new BufferedImage with the applied sobel filter
    */
    public BufferedImage apply(BufferedImage input) {
        BufferedImage sobelImage = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);
        float[] sobelMatrix = createMatrix(); // Gets a matrix based on direction and radius to use in the sobel kernal
        Kernel sobelKernel = new Kernel(3, 3, sobelMatrix); // creates a 3x3 kernal with hard coded matrix
        ConvolveOp convOp = new ConvolveOp(sobelKernel); // Creates a convolution operation that applies the sobel kernal

        // Had an issue with the image RGB values being diffrent depending on the image so setting it all to the same type by redrawing the image
        BufferedImage AdjustedInput = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);
        AdjustedInput.getGraphics().drawImage(input, 0, 0, null);

        //Create an instance of the class that creates image with border
        FilterBorder borderedImage = new FilterBorder(AdjustedInput, 2);
        //Applies convolution to bordered image
        BufferedImage output = convOp.filter(borderedImage.applyBorder(), null);
        //Crops image back to original size
        sobelImage = output.getSubimage(2, 2, AdjustedInput.getWidth(), AdjustedInput.getHeight());

        for (int y = 0; y < AdjustedInput.getHeight(); y++) { // Loops though by height then width
            for (int x = 0; x < AdjustedInput.getWidth(); x++) {
                int rgb = sobelImage.getRGB(x, y); // gets the rgb values of the filtered image
                int gray = (rgb >> 16) & 0xFF;  // the values for gray
                int adjustedValue = gray  + 127; // sets the middle point from lab book (127 or 128) and adds the grey value
                adjustedValue = Math.min(Math.max(adjustedValue, 0), 255); // checks that the adjusted rgb is still within 0-255
                int adjustedRGB = (adjustedValue << 16) | (adjustedValue << 8) | adjustedValue; // Sets the adjusted rgb to be applied
                sobelImage.setRGB(x, y, adjustedRGB); // applys the adjusted rgb to the soble image output
            }
        }

        return sobelImage; // returns the sobel and color adjusted image
    }
    /**A method that creates a matrix
     * @return the float array
     */
    public float[] createMatrix(){
        if(this.dir){
            float[] SobelMatrix = {
                -0.5f, -1.0f, -0.5f,
                0.0f,  0.0f, 0.0f,
                0.5f,  1.0f, 0.5f
            };
            return SobelMatrix;
        }else{
            float[] SobelMatrix = {
                -0.5f, 0.0f, 0.5f,
                -1.0f,  0.0f, 1.0f,
                -0.5f,  0.0f, 0.5f
            };
            return SobelMatrix;
        }

    }
}




