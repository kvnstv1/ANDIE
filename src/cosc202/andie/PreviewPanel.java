package cosc202.andie;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;


/**
 * <p>
 * A class that provides common behaviour to implement in the preview panel for all methods that need them.
 * </p>
 * */
public class PreviewPanel extends JPanel{

    /**A method that makes a well-sized container to hold preview images and make things look fancy and bougie.
     * @author Kevin Steve Sathyanath
     * @param i The BufferedImage
     * @return the JPanel in question
     */
    public static JPanel makePanel(BufferedImage i){

        JPanel j = new JPanel();
        int oriWidth = i.getWidth();
        int oriHeight = i.getHeight();
        int width=0; 
        int height=0;

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

          j.setPreferredSize(new Dimension(width,height));
          
        return j;
    }
}