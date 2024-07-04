package cosc202.andie;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * <p>
 * This class implements the ImageOperation interface and applies Image embossing to a BufferedImage.
 * </p>
 *
 * <p>
 *      Image embossing is when an images pixel is replaced with a light or dark color depening on the boundries of an input image
 * </p>
 *
 *
 * @see java.awt.image.ConvolveOp
 * @see ImageOperation
 * @see BufferedImage
 * @author Angus Lyall
 * @version 1.0
*/
public class EmbossFilter implements ImageOperation, java.io.Serializable{
    /**
    * The direction of the Emboss filter. This currently is not implmented but will allow for a larger emboss matrix
    */
    private int direction;
    /**The radius of the emboss filter. */
    private int radius;

    /**
     * Constructor that sets the radius of the Emboss filter.
     * @param direction the direction the emboss is applied in
     * @param radius the radius of the filter
    */

    EmbossFilter(int direction, int radius){
        this.direction = direction;
        this.radius = radius;
    }

    /**
    * Default constructor that creates a Emboss Filter with a radius of 1 and a direction of 0
    */
    EmbossFilter() {
        this.direction = 0;
        this.radius = 1;
    }

    /**
     * Applies the Emboss filter to a BufferedImage.
     *
     * @param input the BufferedImage to apply the filter to
     * @return a new BufferedImage with the applied Emboss filter and adjusted rgb values
    */
    public BufferedImage apply(BufferedImage input) {
        BufferedImage embossedImage = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB); // creates a new image
        float[] embossMatrix = createMatrix(radius,direction ); // Gets a matrix based on direction and radius to use in the emboss kernal

        Kernel embossKernel = new Kernel((2*radius +1), (2*radius +1 ), embossMatrix); // creates a 3x3 kernal with hard coded matrix
        ConvolveOp convOp = new ConvolveOp(embossKernel); // Creates a convolution operation that applies the emboss kernal

        // Had an issue with the image RGB values being diffrent depending on the image so setting it all to the same type by redrawing the image
        BufferedImage AdjustedInput = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);
        AdjustedInput.getGraphics().drawImage(input, 0, 0, null);

        //Create an instance of the class that creates image with border
        FilterBorder borderedImage = new FilterBorder(AdjustedInput, radius);
        //Applies convolution to bordered image
        BufferedImage output = convOp.filter(borderedImage.applyBorder(), null);
        //Crops image back to original size
        embossedImage = output.getSubimage(2, 2, AdjustedInput.getWidth(), AdjustedInput.getHeight());

        for (int y = 0; y < AdjustedInput.getHeight(); y++) { // Loops though by height then width
            for (int x = 0; x < AdjustedInput.getWidth(); x++) {
                int rgb = embossedImage.getRGB(x, y); // gets the rgb values of the filtered image
                int gray = (rgb >> 16) & 0xFF;  // the values for gray
                int adjustedValue = gray  + 127; // sets the middle point from lab book (127 or 128) and adds the grey value
                adjustedValue = Math.min(Math.max(adjustedValue, 0), 255); // checks that the adjusted rgb is still within 0-255
                int adjustedRGB = (adjustedValue << 16) | (adjustedValue << 8) | adjustedValue; // Sets the adjusted rgb to be applied
                embossedImage.setRGB(x, y, adjustedRGB); // applys the adjusted rgb to the embossed image output
            }
        }

        return embossedImage; // returns the embossed and color adjusted image
    }


     /**
     * Create a matrix for the emboss kernal
     * This method works by treating the float array as a grid similar but the opposite to the Gaussian
     * It uses trigonometry to work out the grid coords of a line at a set angle
     * There is a loop which follows the line and sets the array coords worked out with (x * size + y) to 1.0f
     * until it then reaches the center point called maxSize (I reused maxSize since its the same as center value)
     * It then changes the matrixValue from 1.0f to -1.0f this gives the diffrent colors for the lines
     * @param size the grid size based on radius of the filter
     * @param direction the angle for the emboss
     * @return a float[] array used by the emboss kernal
    */
    public static float[] createMatrix(int size , int direction){
        //ref for array largert then 3x3: https://stackoverflow.com/questions/61297368/what-is-the-5x5-equivalent-of-the-3x3-emboss-kernel
        // and https://en.wikipedia.org/wiki/Image_embossing (example of 5x5 kernals)
        float[] returnMatrix = new float[(2*size+1) * (2*size+1)]; // initalises matrix for returning
        size = (2*size + 1);
        float matrixValue = 1.0f; // sets the matrix value to 1.0f used for the kernal later on
        int maxSize = (size/2); // the size is divied by two and since its an int it floor rounds
        double angleRads = Math.toRadians(direction); // converts the degrees to rads
        int dx,dy = 0; // initialise dx and dy since its used in the switch so It becomes local if I dont do it here
        switch (direction){
            case 0: // if the angle is equal to 0,90,180 or 270 its gotta be treated specially sinces its along the axis lines so switch is like if elseif but looks cool
                dx = 1;
                dy = 0;
                break;
            case 90:
                dx = 0;
                dy = 1;
                break;
            case 180:
                dx = -1;
                dy = 0;
                break;
            case 270:
                dx = 0;
                dy = -1;
                break;
            default:
                dy = (int) Math.round(Math.sin(angleRads) * maxSize); // this uses math I didn't think I would again outside of highschool
                dx = (int) Math.round(Math.cos(angleRads) * maxSize); // basiclly this represents the gradient of the curve in our case line

        }

        for(int i = -maxSize; i <= maxSize; i++){ // loop from -maxSize to maxSize adding i + 1 each time
            int x = maxSize + i * dx; // this works out the x pos based on the gradient
            int y = maxSize + i * dy; // sames as above but for y pos
            if (0 <= x && x < size && 0 <= y && y < size) { // this just checks that its inside the grid or values will keep going
                if(x == maxSize && y == maxSize){ // this checks if it is the center point
                    matrixValue = -1.0f; // once it hits the center the emboss needs to be negative
                    continue;
                }
                returnMatrix[x * size + y] = matrixValue; // sets the index from the coords we worked out to the matrixvalue which is -1 or +1

                // the reason its negivtive or plus one is to give the contrast for the lines to sink or pop out.
                // The equation for the grid convertion for both guassaian and this was from https://www.youtube.com/watch?v=I2_xT1joq2U
            }
        }
        return returnMatrix; // returns the completed matrix.
    }

}




