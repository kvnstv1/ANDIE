package cosc202.andie;

import java.awt.image.BufferedImage;

/**This class is written to implement the Brightness and Contrast operation in ANDIE. 
 * @author Kevin Steve Sathyanath
 * date: 19/04/2024
 */

public class BrightnessAndContrast implements ImageOperation, java.io.Serializable {

    /**The maximum value */
    private static final int MAX = 255;     //The maximum 255 on the RGB scale, used for readability
    /**The minimum value */
    private static final int MIN = 0;      //The minimum 0 on the RGB scale, used for readability
    /**The value of the brightness */
    private int b; //Brightness value
    /**The value of the contrast */
    private int c; //Contrast value

    /**
    * Default constructor for BrightnessAndContrast
    */    
    public BrightnessAndContrast(){ //Default constructor
    }

    /**
     * Constructor that sets the brightness and contrast that a user has input.
     * @param brightness the brightness value
     * @param contrast  the contrast value
    */
    public BrightnessAndContrast(int brightness, int contrast){
        this.b = brightness;
        this.c = contrast;
    }

    /**This method must be implemented when the class implements ImageOperation. It will apply the output of the equation()
     * here while taking care of the bounds.
     * @return output: A BufferedImage that has the B and C operation applied to it.
     * @param input A BufferedImage that is to be operated on. 
     */
    public BufferedImage apply(BufferedImage input){

        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB); 
        
        int nr,ng,nb; //New values after applying the equation.
        int argb; //Resolved value of the pixel
        int a,r,g,b; //Broken down values or each pixel

        for(int i=0; i<output.getHeight(); i++){
            for(int j=0; j<output.getWidth(); j++){

                argb = input.getRGB(j,i);
                a = (argb & 0xFF000000) >> 24;
                r = (argb & 0x00FF0000) >> 16;
                g = (argb & 0x0000FF00) >> 8;
                b = (argb & 0x000000FF);

                nr = equation(r);
                ng = equation(g);
                nb = equation(b);

                argb = (a << 24) | (nr << 16) | (ng << 8) | nb;
                output.setRGB(j,i, argb);
            }
        }


        return output;
    }//End of apply().

    /**A function to apply the equation given in the lab book.
     * @param v The parameter representing the RGB value of the pixel. 
     * @return The output integer from applying the equation using in. 
     */
    public int equation(int v){

        int vOut = 0;
        vOut = (int)((1 + (c/100.0))*(v-127.5)+(127.5)*(1+(b/100.0))); //Explicity typecasting since pixel value cannot be a float.
        if(vOut>MAX) {vOut = MAX;}
        if(vOut<MIN) {vOut = MIN;}
        return vOut;

    }// End of equation()



    /**A function to apply the equation given in the lab book.
     * @param v The parameter representing the RGB value of the pixel. 
     * @param br The parameter passed to represent a change in brightness.
     * @param cr The parameter passed to represent a change in contrast.
     * @return vOut the output integer from applying the equation using in. 
     */
    public static int equation(int v, int br, int cr){

        int vOut = 0;
        vOut = (int)((1 + (cr/100.0))*(v-127.5)+(127.5)*(1+(br/100.0))); //Explicity typecasting since pixel value cannot be a float.
        if(vOut>MAX) {vOut = MAX;}
        if(vOut<MIN) {vOut = MIN;}
        return vOut;

    }// End of equation()


    /**This method is the same as apply() above, but it is to be exclusively for the previewPanel.
     * @param input A BufferedImage that is to be operated on.
     * @param br The parameter passed to represent a change in brightness.
     * @param cr The parameter passed to represent a change in contrast.
     * @return output A BufferedImage that has the B and C operation applied to it.
     */
    public static BufferedImage applyToPreview(BufferedImage input, int br, int cr){

        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB); 
        
        int nr,ng,nb; //New values after applying the equation.
        int argb; //Resolved value of the pixel
        int a,r,g,b; //Broken down values or each pixel

        for(int i=0; i<output.getHeight(); i++){
            for(int j=0; j<output.getWidth(); j++){

                argb = input.getRGB(j,i);
                a = (argb & 0xFF000000) >> 24;
                r = (argb & 0x00FF0000) >> 16;
                g = (argb & 0x0000FF00) >> 8;
                b = (argb & 0x000000FF);

                nr = equation(r,br,cr);
                ng = equation(g,br,cr);
                nb = equation(b,br,cr);

                argb = (a << 24) | (nr << 16) | (ng << 8) | nb;
                output.setRGB(j,i, argb);
            }
        }


        return output;
    }//End of apply().

    
} //End of class
