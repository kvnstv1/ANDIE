package cosc202.andie;

import java.awt.image.*;


/**
 * <p>
 * This class implements the ImageOperation interface and applies a Gaussian blur filter to a BufferedImage.
 * </p>
 *
 * <p>
 *   A Gaussian blur filter applies a Gaussian distribution to soften and blur an image. It achieves this by
 *   replacing each pixel with a weighted average of itself and its surrounding pixels, with the weights based on a
 *   Gaussian function that is normally distributed. This distribution gives more weight to pixels closer to the center, resulting in a smooth blur.
 * </p>
 *
 *
 * @see java.awt.image.ConvolveOp
 * @see ImageOperation
 * @see BufferedImage
 * @author Angus Lyall
 * @version 1.0
*/

public class GaussianFilter implements ImageOperation, java.io.Serializable {
    /**
     * The radius of the Gaussian filter. This determines the size of the mask and the strength of the blur.
    */
    private int radius;


    /**
     * Constructor that sets the radius of the Gaussian filter.
     * @param radius the radius of the filter
    */

    GaussianFilter(int radius){
        this.radius = radius;
    }
    /**
     * Default constructor that creates a GaussianFilter with a radius of 2.
    */
    GaussianFilter() {
        this(2);
    }
    /**
     * Applies the Gaussian blur filter to a BufferedImage.
     *
     * @param input the BufferedImage to apply the filter to
     * @return a new BufferedImage with the applied Gaussian blur
    */
    public BufferedImage apply(BufferedImage input) {
        if(radius ==0){
            return input;
        }
        int size = (2*radius+1) * (2*radius+1);
        float [] array = new float[size];
        double sum =0;
        for(int i = 0; i < size ; i++){
            String[] posString = getpos(i,(2*radius+1)).split(",");
            int x = Integer.parseInt(posString[0]);
            int y = Integer.parseInt(posString[1]);
            double result = GaussianEquation(x,y, (double)radius / 3);
            sum = sum + result;
            array[i] = (float)result;
         }
        for(int i = 0; i < size ; i++){
             array[i] = array[i]/ (float)sum;
         }

        Kernel kernel = new Kernel(2*radius+1, 2*radius+1, array);
        ConvolveOp convOp = new ConvolveOp(kernel);

        //Create an instance of the class that creates image with border
        FilterBorder borderedImage = new FilterBorder(input, radius);

        //Applies convolution to bordered image
        BufferedImage output = convOp.filter(borderedImage.applyBorder(), null);

        //Crops image back to original size
        output = output.getSubimage(radius, radius, input.getWidth(), input.getHeight());

        return output;
    }

    /**
     * Calculates the value at a specific position in the Gaussian filter mask.
     *
     * @param x the x coordinate in the mask
     * @param y the y coordinate in the mask
     * @param sd the standard deviation of the Gaussian distribution (controls the blur strength)
     * @return the value at the specified position in the mask
    */
    public static double GaussianEquation(int x , int y, double sd){
        double sdPow = Math.pow(sd, 2);
        double l1 = 1/(2 * Math.PI * sdPow);
        double el1 = Math.pow(x, 2) + Math.pow(y, 2);
        double e = Math.pow(Math.E, -(el1 / (2 * sdPow)));

        return l1 * e;
    }

    /**
     * Calculates the relative position (x, y)
     * basicilly converts the index of an array to grid coords centered around the middle of the grid / array
     * @param num the one-dimensional index in the filter array
     * @param height the height of the Gaussian filter mask
     * @return a String containing the x and y coordinates separated by a comma (",")
    */
    public static String getpos(int num , int height){
        int center = (height) / 2;
        int x = num % height -center;
        int y = center - num / height;
        return x +","+ y;
    }



    /**A method that does the same as above for the preview panel
     * @author Angus Lyall
     * @param input the BufferedImage input
     * @param rad the radius
     * @return the BufferedImage output for the preview pane
     */
    public static BufferedImage applyToPreview(BufferedImage input, int rad) {
        if(rad == 0){
            return input;
        }
        int size = (2*rad+1) * (2*rad+1);
        float [] array = new float[size];
        double sum =0;
        for(int i = 0; i < size ; i++){
            String[] posString = getpos(i,(2*rad+1)).split(",");
            int x = Integer.parseInt(posString[0]);
            int y = Integer.parseInt(posString[1]);
            double result = GaussianEquation(x,y, (double)rad / 3);
            sum = sum + result;
            array[i] = (float)result;
         }
        for(int i = 0; i < size ; i++){
             array[i] = array[i]/ (float)sum;
         }

        Kernel kernel = new Kernel(2*rad+1, 2*rad+1, array);
        ConvolveOp convOp = new ConvolveOp(kernel);

        //Create an instance of the class that creates image with border
        FilterBorder borderedImage = new FilterBorder(input, rad);

        //Applies convolution to bordered image
        BufferedImage output = convOp.filter(borderedImage.applyBorder(), null);

        //Crops image back to original size
        output = output.getSubimage(rad, rad, input.getWidth(), input.getHeight());

        return output;
    }



}




