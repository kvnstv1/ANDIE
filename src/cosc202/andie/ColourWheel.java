package cosc202.andie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;



/**
 * <p>
 * A support class to pick a colour
 * </p>
 *
 * <p>
 * This support class enables the user to pick a colour to use on the drawing tool.
 * </p>
 *

 *
 *
 * @author Kevin Steve Sathyanath
 * @version 1.0
 */
public class ColourWheel {

    /**The chosen colour */
    private static Color chosenColour;
    /**A data field for the Colour chooser */
    public static JColorChooser chooser;
    /**A temporary variable to hold the colour */
    public static Color a;

    /**The constructor
     * @author Kevin Steve Sathyanath
     */
    public ColourWheel(){

    }

    /**A method to pick a new colour with JColourChooser
     * @author Kevin Steve Sathyanath
     * @version 1.0
     */
    public static void pickColour(){

        try{
        JFrame colorFrame = new JFrame();
        colorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        colorFrame.setSize(500, 500);

        JPanel panel = new JPanel();
        JColorChooser chooser = new JColorChooser();

        ActionListener okListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Color a = chooser.getColor();
                if(a!=null){
                    chosenColour = a;
                }
            }

        };

        ActionListener cancelListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent f){
                colorFrame.dispose();
            }
        };
        try{
        a = JColorChooser.showDialog(colorFrame, Andie.bundle.getString("PickAColour"), Color.BLACK, true);
        }catch(HeadlessException h){
            System.out.println(h);
        }

        if(a!=null){
            chosenColour = a;
        }
     } catch (Exception err) {
            if (err instanceof NullPointerException) {
                JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Error"), JOptionPane.WARNING_MESSAGE);
            }
        } //End of catch

    } //Method of pickColour



    /**A getter method for the class that allows the program to get the colour the user has chosen.
     * @return The chosen colour
    */
    public static Color getChosenColour(){
        return chosenColour;
    }



}
